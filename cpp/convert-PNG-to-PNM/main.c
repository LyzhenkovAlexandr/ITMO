#include "Prototypes.h"

uint createDecNumber(const unchar buffer[], int start)
{
	uint num = buffer[start] << 24 | buffer[start + 1] << 16 | buffer[start + 2] << 8 | buffer[start + 3];
	return num;
}

void checkSignature(Context *context)
{
	unchar buffer[8];
	unchar original[8] = { 0x89, 0x50, 0x4e, 0x47, 0xd, 0xa, 0x1a, 0xa };

	if (fread(buffer, sizeof(unchar), 8, context->inputFile) != 8)
	{
		release_context(context, ERROR_DATA_INVALID);
		return;
	}
	for (int i = 0; i < 8; i++)
	{
		if (original[i] != buffer[i])
		{
			release_context(context, ERROR_DATA_INVALID);
			return;
		}
	}
}

void checkIHDR(Context *context)
{
	unchar buffer[25];
	unchar original[] = { 0x00, 0x00, 0x00, 0x0d, 0x49, 0x48, 0x44, 0x52 };
	unchar end[] = { 0x08, 0x00, 0x00, 0x00, 0x00 };

	if (fread(buffer, sizeof(unchar), 25, context->inputFile) != 25)
	{
		release_context(context, ERROR_DATA_INVALID);
		return;
	}
	for (int i = 0; i < 8; i++)
	{
		if (original[i] != buffer[i])
		{
			release_context(context, ERROR_DATA_INVALID);
			return;
		}
	}
	context->width = createDecNumber(buffer, 8);
	context->height = createDecNumber(buffer, 12);

	for (int i = 16; i < 21; i++)
	{
		unchar byte = buffer[i];
		if (i == 17)
		{
			if (byte == 0x00 || byte == 0x02 || byte == 0x03)
			{
				context->typeColor = (int)byte;
			}
			else
			{
				release_context(context, ERROR_DATA_INVALID);
				return;
			}
		}
		else
		{
			if (end[i - 16] != byte)
			{
				release_context(context, ERROR_DATA_INVALID);
				return;
			}
		}
	}
}

void filterSub(Context *context, uLong index)
{
	unchar *buff = context->result;
	for (uLong i = 3; i < context->width * context->countChannel; i++)
	{
		buff[i + index] += buff[i - 3 + index];
	}
}

void filterUp(Context *context, uLong index)
{
	if (index == 1)
	{
		return;
	}
	unchar *buff = context->result;
	uLong prev = context->width * context->countChannel + 1;
	for (uLong i = 0; i < context->width * context->countChannel; i++)
	{
		buff[i + index] += buff[i + index - prev];
	}
}

void filterAverage(Context *context, uLong index)
{
	unchar *buff = context->result;
	uLong prev = context->width * context->countChannel + 1;
	for (uLong i = 0; i < context->width * context->countChannel; i++)
	{
		if (index == 1 && i < 3)
		{
		}
		else if (i < 3)
		{
			buff[i + index] -= buff[i + index - prev] / 2;
		}
		else if (index == 1)
		{
			buff[i + index] -= buff[i + index - 3] / 2;
		}
		else
		{
			buff[i + index] -= (buff[i + index - 3] + buff[i + index - prev]) / 2;
		}
	}
}

void filterPaeth(Context *context, uLong index)
{
	unchar *buff = context->result;
	uLong prev = context->width * context->countChannel + 1;
	for (int i = 0; i < context->width * context->countChannel; i++)
	{
		int a = 0;
		int b = 0;
		int c = 0;
		if (index == 1 && i < 3)
		{
		}
		else if (i < 3)
		{
			b = buff[i + index - prev];
		}
		else if (index == 1)
		{
			a = buff[i + index - 3];
		}
		else
		{
			a = buff[i + index - 3];
			b = buff[i + index - prev];
			c = buff[i + index - prev - 3];
		}
		int p = a + b - c;

		int pa = abs(p - a);
		int pb = abs(p - b);
		int pc = abs(p - c);

		int Pr;
		if (pa <= pb && pa <= pc)
		{
			Pr = a;
		}
		else if (pb <= pc)
		{
			Pr = b;
		}
		else
		{
			Pr = c;
		}
		buff[i + index] += Pr;
	}
}

void decodingFilters(Context *context)
{
	uLong index = 0;
	uLong step = context->width * context->countChannel + 1;
	unchar *buff = context->result;
	while (index < context->lenResult)
	{
		if (buff[index] == 0x00)
		{
		}
		else if (buff[index] == 0x01)
		{
			filterSub(context, index + 1);
		}
		else if (buff[index] == 0x02)
		{
			filterUp(context, index + 1);
		}
		else if (buff[index] == 0x03)
		{
			filterAverage(context, index + 1);
		}
		else if (buff[index] == 0x04)
		{
			filterPaeth(context, index + 1);
		}
		else
		{
			release_context(context, ERROR_DATA_INVALID);
			return;
		}
		index += step;
	}
}

void writeResult(Context *context)
{
	if (context->typeColor == 0 || context->typeColor == 2)
	{
		if (context->typeColor == 0)
		{
			fprintf(context->outputFile, "P5\n%d %d\n255\n", context->width, context->height);
		}
		else
		{
			fprintf(context->outputFile, "P6\n%d %d\n255\n", context->width, context->height);
		}
		uint step = context->width * context->countChannel + 1;
		uint i = 0;
		while (i < (context->width * context->countChannel + 1) * context->height)
		{
			fwrite(&context->result[i + 1], sizeof(unchar), context->width * context->countChannel, context->outputFile);
			i += step;
		}
	}
	else
	{
		uint step = context->width * context->countChannel + 1;
		if (context->variantP6)
		{
			fprintf(context->outputFile, "P6\n%d %d\n255\n", context->width, context->height);
			for (int i = 0; i < (context->width * context->countChannel + 1) * context->height; i++)
			{
				if (i % step != 0)
				{
					unchar byte = context->result[i];
					fwrite(&context->palette[byte * 3], sizeof(unchar), 3, context->outputFile);
				}
			}
		}
		else
		{
			fprintf(context->outputFile, "P5\n%d %d\n255\n", context->width, context->height);
			for (int i = 0; i < (context->width * context->countChannel + 1) * context->height; i++)
			{
				if (i % step != 0)
				{
					unchar byte = context->result[i];
					fwrite(&context->palette[byte * 3], sizeof(unchar), 1, context->outputFile);
				}
			}
		}
	}
}

void parsePLTE(Context *context)
{
	uint len = createDecNumber(context->remember, 0);
	if (len % 3 != 0 || len > 768 || len < 3)
	{
		release_context(context, ERROR_DATA_INVALID);
		return;
	}
	context->palette = (unchar *)malloc(sizeof(unchar) * len);
	if (context->palette == NULL)
	{
		release_context(context, ERROR_OUT_OF_MEMORY);
		return;
	}
	if (fread(context->palette, sizeof(unchar), len, context->inputFile) != len)
	{
		release_context(context, ERROR_DATA_INVALID);
		return;
	}
	for (int i = 0; i < len; i += 3)
	{
		if (context->palette[i] != context->palette[i + 1] || context->palette[i] != context->palette[i + 2] ||
			context->palette[i + 1] != context->palette[i + 2])
		{
			context->variantP6 = 1;
			break;
		}
	}
	if (fseek(context->inputFile, 4, SEEK_CUR) != 0)
	{
		release_context(context, ERROR_DATA_INVALID);
		return;
	}
}

void parseIDAT(Context *context)
{
	uint len = createDecNumber(context->remember, 0);
	if (context->sizeBuffer - context->insert < len)
	{
		unchar *temp = (unchar *)realloc(context->buffer, context->sizeBuffer * 2);
		if (temp == NULL)
		{
			release_context(context, ERROR_OUT_OF_MEMORY);
			return;
		}
		context->buffer = temp;
		context->sizeBuffer *= 2;
	}
	if (fread(&context->buffer[context->insert], sizeof(unchar), len, context->inputFile) != len)
	{
		release_context(context, ERROR_DATA_INVALID);
		return;
	}
	context->insert += len;
	if (fseek(context->inputFile, 4, SEEK_CUR) != 0)
	{
		release_context(context, ERROR_DATA_INVALID);
		return;
	}
}

void parseTrash(Context *context)
{
	uint len = createDecNumber(context->remember, 0);
	if (fseek(context->inputFile, len + 4, SEEK_CUR) != 0)
	{
		release_context(context, ERROR_DATA_INVALID);
		return;
	}
}

void parseIEND(Context *context)
{
	uint len = createDecNumber(context->remember, 0);
	if (len != 0)
	{
		release_context(context, ERROR_DATA_INVALID);
		return;
	}
	unchar temp[4];
	if (fread(temp, sizeof(unchar), 5, context->inputFile) != 4)
	{
		release_context(context, ERROR_DATA_INVALID);
		return;
	}
}

int checkName(Context *context)
{
	if (fread(context->nameChunk, sizeof(unchar), 4, context->inputFile) != 4)
	{
		release_context(context, ERROR_DATA_INVALID);
		return -1;
	}
	unchar *buff = context->nameChunk;
	if (buff[0] == 'P' && buff[1] == 'L' && buff[2] == 'T' && buff[3] == 'E')
	{
		if (context->typeColor == 0 || context->wasIDAT != 0 || context->wasPLTE == 1)
		{
			release_context(context, ERROR_DATA_INVALID);
			return -1;
		}
		context->wasPLTE = 1;
		return 0;
	}
	else if (buff[0] == 'I' && buff[1] == 'D' && buff[2] == 'A' && buff[3] == 'T')
	{
		if (context->wasIDAT == -1)
		{
			release_context(context, ERROR_DATA_INVALID);
			return -1;
		}
		context->wasIDAT = 1;
		return 1;
	}
	else if (buff[0] == 'I' && buff[1] == 'E' && buff[2] == 'N' && buff[3] == 'D')
	{
		if (context->wasIDAT == 0)
		{
			release_context(context, ERROR_DATA_INVALID);
			return -1;
		}
		return 2;
	}
	else
	{
		if (context->wasIDAT == 1)
		{
			context->wasIDAT = -1;
		}
		return 3;
	}
}

void parseChunks(Context *context)
{
	while (1)
	{
		if (fread(context->remember, sizeof(unchar), 4, context->inputFile) != 4)
		{
			release_context(context, ERROR_DATA_INVALID);
			return;
		}
		int variant = checkName(context);
		if (variant == -1)
		{
			return;
		}
		if (variant == 0)
		{
			parsePLTE(context);
			if (context->codeError != SUCCESS)
			{
				return;
			}
		}
		else if (variant == 1)
		{
			parseIDAT(context);
			if (context->codeError != SUCCESS)
			{
				return;
			}
		}
		else if (variant == 2)
		{
			parseIEND(context);
			return;
		}
		else
		{
			parseTrash(context);
			if (context->codeError != SUCCESS)
			{
				return;
			}
		}
	}
}

void unCompress(Context *context)
{
	context->lenResult = sizeof(unchar) * (context->width * context->countChannel + 1) * context->height;
	context->result = (unchar *)malloc(context->lenResult);
	if (context->result == NULL)
	{
		release_context(context, ERROR_OUT_OF_MEMORY);
		return;
	}
#if defined(ZLIB)
	int ret = uncompress(context->result, &context->lenResult, context->buffer, context->insert);
	if (ret != Z_OK)
	{
		release_context(context, ERROR_DATA_INVALID);
		return;
	}
#elif defined(LIBDEFLATE)
	struct libdeflate_decompressor *d = libdeflate_alloc_decompressor();
	int actual_uncompressed_size =
		(libdeflate_zlib_decompress(d, context->buffer, context->insert, context->result, context->lenResult, NULL));
	libdeflate_free_decompressor(d);
	if (actual_uncompressed_size != LIBDEFLATE_SUCCESS)
	{
		release_context(context, ERROR_DATA_INVALID);
		return;
	}
#elif defined(ISAL)
	struct inflate_state state;
	isal_inflate_init(&state);
	state.next_in = context->buffer;
	state.avail_in = context->insert;
	state.next_out = context->result;
	state.avail_out = context->lenResult;
	state.crc_flag = ISAL_ZLIB;

	if (isal_inflate(&state) != ISAL_DECOMP_OK)
	{
		release_context(context, ERROR_DATA_INVALID);
		return;
	}
#endif
}

void decodingPNG(Context *context)
{
	checkSignature(context);
	if (context->codeError != SUCCESS)
	{
		return;
	}
	checkIHDR(context);
	if (context->codeError != SUCCESS)
	{
		return;
	}
	context->countChannel = channels[context->typeColor];
	context->sizeBuffer = sizeof(unchar) * context->width * context->height;
	context->buffer = (unchar *)malloc(context->sizeBuffer);
	if (context->buffer == NULL)
	{
		release_context(context, ERROR_OUT_OF_MEMORY);
		return;
	}
	parseChunks(context);
	if (context->codeError != SUCCESS)
	{
		return;
	}
	unCompress(context);
	if (context->codeError != SUCCESS)
	{
		return;
	}
	decodingFilters(context);
	if (context->codeError != SUCCESS)
	{
		return;
	}
}

int release_context(Context *context, int errorCode)
{
	if (context->inputFile != NULL)
	{
		fclose(context->inputFile);
		context->inputFile = NULL;
	}
	if (context->outputFile != NULL)
	{
		fclose(context->outputFile);
		context->outputFile = NULL;
	}
	if (context->result != NULL)
	{
		free(context->result);
		context->result = NULL;
	}
	if (context->buffer != NULL)
	{
		free(context->buffer);
		context->buffer = NULL;
	}
	if (context->palette != NULL)
	{
		free(context->palette);
		context->palette = NULL;
	}
	if (errorCode != SUCCESS)
	{
		context->codeError = errorCode;
		fprintf(stderr, "%s\n", error_message_table[errorCode]);
	}
	return context->codeError;
}

int main(int argc, char *argv[])
{
	if (argc != 3)
	{
		fprintf(stderr, "%s %d\n", error_message_table[ERROR_PARAMETER_INVALID], argc);
		return ERROR_PARAMETER_INVALID;
	}
	Context context[1] = { SUCCESS, NULL, NULL, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL };
	context->inputFile = fopen(argv[1], "rb");
	if (context->inputFile == NULL)
	{
		fprintf(stderr, "%s\n", error_message_table[ERROR_CANNOT_OPEN_FILE]);
		return ERROR_CANNOT_OPEN_FILE;
	}

	decodingPNG(context);
	if (context->codeError != SUCCESS)
	{
		return release_context(context, SUCCESS);
	}

	context->outputFile = fopen(argv[2], "wb");
	if (context->outputFile == NULL)
	{
		release_context(context, ERROR_CANNOT_OPEN_FILE);
		return ERROR_CANNOT_OPEN_FILE;
	}
	writeResult(context);

	return release_context(context, SUCCESS);
}