#ifndef CONVERTORPNG_PROTOTYPES_H
#define CONVERTORPNG_PROTOTYPES_H
#include "return_codes.h"

#if defined(ZLIB)
#include <zlib.h>
#elif defined(LIBDEFLATE)
#include <libdeflate.h>
#elif defined(ISAL)
#include <include/igzip_lib.h>
#else
#error("Not supported lib")
#endif

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
typedef unsigned int uint;
typedef unsigned char unchar;
typedef unsigned long uLong;
typedef struct
{
	int codeError;
	FILE *inputFile;
	FILE *outputFile;
	uint width;
	uint height;
	int countChannel;
	int typeColor;
	int variantP6;
	int wasPLTE;
	int wasIDAT;
	uLong insert;
	uLong lenResult;
	uLong sizeBuffer;
	unchar *buffer;
	unchar *result;
	unchar *palette;
	unchar remember[4];
	unchar nameChunk[4];
} Context;
uint createDecNumber(const unchar buffer[], int start);
void checkSignature(Context *context);
void checkIHDR(Context *context);
void filterSub(Context *context, uLong index);
void filterUp(Context *context, uLong index);
void filterAverage(Context *context, uLong index);
void filterPaeth(Context *context, uLong index);
void decodingFilters(Context *context);
void writeResult(Context *context);
void parsePLTE(Context *context);
void parseIDAT(Context *context);
void parseTrash(Context *context);
void parseIEND(Context *context);
int checkName(Context *context);
void parseChunks(Context *context);
void unCompress(Context *context);
void decodingPNG(Context *context);
int release_context(Context *context, int errorCode);
const char *error_message_table[] = { [SUCCESS] = "", [ERROR_CANNOT_OPEN_FILE] = "Cannot open file", [ERROR_OUT_OF_MEMORY] = "Out of memory", [ERROR_DATA_INVALID] = "Invalid data", [ERROR_PARAMETER_INVALID] = "Expected 3 arguments to command line, but found" };
const int channels[] = { 1, -1, 3, 1 };
#endif
