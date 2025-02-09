#include "Prototypes.h"

#include <math.h>
#include <stdlib.h>

double calcC(const double *A, int k, const double *B, const double *prevSquareB, int i, unit arrSize)
{
	return specialScalar(A, k, B, i, arrSize) / prevSquareB[i];
}

double specialScalar(const double *A, int k, const double *B, int j, unit arrSize)
{
	double result = 0;
	for (int i = 0; i < arrSize; i++)
	{
		result += A[i * arrSize + k] * B[j * arrSize + i];
	}
	return result;
}

void addTerms(double *summa, double scalar, const double *B, int j, unit arrSize)
{
	for (int i = 0; i < arrSize; i++)
	{
		summa[i] += scalar * B[j * arrSize + i];
	}
}

void insertVectorInQ(double *Q, int rowQ, const double *A, int colA, const double *summa, unit arrSize)
{
	for (int i = 0; i < arrSize; i++)
	{
		Q[rowQ * arrSize + i] = A[i * arrSize + colA] - summa[i];
	}
}

void insertNewScalarVectorB(double *prevSquareB, int indexInsert, const double *Q, int rowVector, unit arrSize)
{
	double res = 0;
	for (int i = 0; i < arrSize; i++)
	{
		res += Q[rowVector * arrSize + i] * Q[rowVector * arrSize + i];
	}
	prevSquareB[indexInsert] = res;
}

void normQ(double *Q, unit arrSize)
{
	for (int i = 0; i < arrSize; i++)
	{
		double summa = 0;
		for (int j = 0; j < arrSize; j++)
		{
			summa += Q[i * arrSize + j] * Q[i * arrSize + j];
		}
		summa = sqrt(summa);
		for (int j = 0; j < arrSize; j++)
		{
			Q[i * arrSize + j] /= summa;
		}
	}
}

double *calcQ(double *A, double *Q, double *prevSquareB, double *summa, unit arrSize)
{
	for (int k = 1; k < arrSize + 1; k++)
	{
		memset(summa, 0, sizeof(double) * arrSize);
		for (int i = 1; i < k; i++)
		{
			addTerms(summa, calcC(A, k - 1, Q, prevSquareB, i - 1, arrSize), Q, i - 1, arrSize);
		}
		insertVectorInQ(Q, k - 1, A, k - 1, summa, arrSize);
		insertNewScalarVectorB(prevSquareB, k - 1, Q, k - 1, arrSize);
	}
	normQ(Q, arrSize);
	return Q;
}

double *transpose(double *A, unit arrSize)
{
	for (int i = 0; i < arrSize; i++)
	{
		for (int j = i; j < arrSize; j++)
		{
			double temp = A[i * arrSize + j];
			A[i * arrSize + j] = A[j * arrSize + i];
			A[j * arrSize + i] = temp;
		}
	}
	return A;
}

void multiplyMatrix(const double *A, const double *B, double *result, unit arrSize)
{
	for (int i = 0; i < arrSize; i++)
	{
		for (int j = 0; j < arrSize; j++)
		{
			double res = 0;
			for (int k = 0; k < arrSize; k++)
			{
				res += A[i * arrSize + k] * B[k * arrSize + j];
			}
			result[i * arrSize + j] = res;
		}
	}
}

void MultiplyRQ(const double *A, const double *B, double *result, unit arrSize)
{
	for (int i = 0; i < arrSize; i++)
	{
		for (int j = 0; j < arrSize; j++)
		{
			double res = 0;
			for (int k = i; k < arrSize; k++)
			{
				res += A[i * arrSize + k] * B[k * arrSize + j];
			}
			result[i * arrSize + j] = res;
		}
	}
}

int isResult(double *A_new, unit arrSize)
{
	double eps = 1e-6;
	if (arrSize <= 2)
	{
		return 1;
	}
	for (int i = 1; i < arrSize; i++)
	{
		int j = i - 1;
		if (fabs(A_new[i * arrSize + j]) < eps)
		{
		}
		else if (i == 1)
		{
			if (fabs(A_new[(i + 1) * arrSize + (j + 1)]) < eps)
			{
			}
			else
			{
				return 0;
			}
		}
		else if (i + 1 < arrSize && fabs(A_new[(i - 1) * arrSize + (j - 1)]) < eps && fabs(A_new[(i + 1) * arrSize + (j + 1)]) < eps)
		{
		}
		else if (i == arrSize - 1)
		{
			if (fabs(A_new[(i - 1) * arrSize + (j - 1)]) < eps)
			{
			}
			else
			{
				return 0;
			}
		}
		else
		{
			return 0;
		}
	}
	return 1;
}

void printNumbers(double *A, Context *context, unit arrSize)
{
	double eps = 1e-6;
	if (arrSize == 1)
	{
		fprintf(context->outputFile, "%g\n", A[0]);
	}
	else if (arrSize == 2)
	{
		printSquareMinor(A[0], A[1], A[arrSize], A[arrSize + 1], context);
	}
	else
	{
		int ago = -1;
		for (int i = 1; i < arrSize; i++)
		{
			int j = i - 1;
			if (i == arrSize - 1)
			{
				if (fabs(A[i * arrSize + j]) < eps)
				{
					if (ago != i - 1)
					{
						fprintf(context->outputFile, "%g\n", A[(i - 1) * arrSize + j]);
					}
					fprintf(context->outputFile, "%g\n", A[i * arrSize + (j + 1)]);
				}
				else
				{
					printSquareMinor(A[(i - 1) * arrSize + j], A[(i - 1) * arrSize + (j + 1)], A[i * arrSize + j], A[i * arrSize + (j + 1)], context);
				}
			}
			else
			{
				if (fabs(A[i * arrSize + j]) < eps)
				{
					if (ago != i - 1)
					{
						fprintf(context->outputFile, "%g\n", A[(i - 1) * arrSize + j]);
					}
				}
				else
				{
					ago = i;
					printSquareMinor(A[(i - 1) * arrSize + j], A[(i - 1) * arrSize + (j + 1)], A[i * arrSize + j], A[i * arrSize + (j + 1)], context);
				}
			}
		}
	}
}

void printSquareMinor(double x, double c, double d, double y, Context *context)
{
	double D = x * x - 2 * x * y + y * y + 4 * c * d;
	if (D < 0)
	{
		fprintf(context->outputFile, "%g%s%g%s\n", (x + y) * 0.5, " +", sqrt(-D) * 0.5, "i");
		fprintf(context->outputFile, "%g%s%g%s\n", (x + y) * 0.5, " -", sqrt(-D) * 0.5, "i");
	}
	else
	{
		fprintf(context->outputFile, "%g\n", (x + y) * 0.5 + sqrt(D) * 0.5);
		fprintf(context->outputFile, "%g\n", (x + y) * 0.5 - sqrt(D) * 0.5);
	}
}

void calcNumbers(double *A, Context *context, unit arrSize)
{
	calcHolder(A, context, arrSize);
	if (context->codeError != SUCCESS)
		return;
	double *R = (double *)malloc(sizeof(double) * arrSize * arrSize);
	if (R == NULL)
	{
		release_context(context, ERROR_OUT_OF_MEMORY);
		return;
	}
	double *Q_help = (double *)malloc(sizeof(double) * arrSize * arrSize);
	if (Q_help == NULL)
	{
		free(R);
		release_context(context, ERROR_OUT_OF_MEMORY);
		return;
	}
	double *prevSquareB = (double *)malloc(sizeof(double) * arrSize);
	if (prevSquareB == NULL)
	{
		free(R);
		free(Q_help);
		release_context(context, ERROR_OUT_OF_MEMORY);
		return;
	}
	double *summa = (double *)malloc(sizeof(double) * arrSize);
	if (summa == NULL)
	{
		free(R);
		free(Q_help);
		free(prevSquareB);
		release_context(context, ERROR_OUT_OF_MEMORY);
		return;
	}
	do
	{
		double *Q_trans = calcQ(A, Q_help, prevSquareB, summa, arrSize);
		multiplyMatrix(Q_trans, A, R, arrSize);
		double *Q = transpose(Q_trans, arrSize);
		MultiplyRQ(R, Q, A, arrSize);
	} while (!isResult(A, arrSize));
	free(R);
	free(Q_help);
	free(prevSquareB);
	free(summa);
}

void fillSpecialZero(double *A, int index, unit arrSize)
{
	for (int i = 0; i < index; i++)
	{
		for (int j = 0; j < arrSize; j++)
		{
			A[i * arrSize + j] = 0;
			A[j * arrSize + i] = 0;
		}
	}
}

double *holder(const double *A, double *H, double *vector, int index, unit arrSize)
{
	fillSpecialZero(H, index, arrSize);
	for (int i = 0; i < arrSize; i++)
	{
		H[i * arrSize + i] = 1;
	}

	for (int i = index; i < arrSize; i++)
	{
		vector[i] = A[i * arrSize + (index - 1)];
	}

	double summaSquare = 0;
	for (int i = index; i < arrSize; i++)
	{
		summaSquare += vector[i] * vector[i];
	}
	double len = sqrt(summaSquare);
	summaSquare -= vector[index] * vector[index];
	if (summaSquare == 0)
	{
		return H;
	}
	vector[index] += len;
	summaSquare += vector[index] * vector[index];
	double cnst = 2 / summaSquare;

	for (int i = index; i < arrSize; i++)
	{
		for (int j = index; j < arrSize; j++)
		{
			double res = cnst * vector[i] * vector[j];
			H[i * arrSize + j] = i == j ? 1 - res : -res;
		}
	}
	return H;
}

void multiplyMatrixLeft(const double *A, const double *B, double *result, int index, unit arrSize)
{
	for (int i = 0; i < index; i++)
	{
		for (int j = 0; j < arrSize; j++)
		{
			result[i * arrSize + j] = B[i * arrSize + j];
		}
	}
	for (int i = index; i < arrSize; i++)
	{
		for (int j = 0; j < arrSize; j++)
		{
			double ans = 0;
			for (int k = index; k < arrSize; k++)
			{
				ans += A[i * arrSize + k] * B[k * arrSize + j];
			}
			result[i * arrSize + j] = ans;
		}
	}
}

void multiplyMatrixRight(const double *A, const double *B, double *result, int index, unit arrSize)
{
	for (int i = 0; i < arrSize; i++)
	{
		for (int j = 0; j < index; j++)
		{
			result[i * arrSize + j] = A[i * arrSize + j];
		}
	}
	for (int i = 0; i < arrSize; i++)
	{
		for (int j = index; j < arrSize; j++)
		{
			double ans = 0;
			for (int k = index; k < arrSize; k++)
			{
				ans += A[i * arrSize + k] * B[k * arrSize + j];
			}
			result[i * arrSize + j] = ans;
		}
	}
}

void calcHolder(double *A, Context *context, unit arrSize)
{
	double *H_help = (double *)malloc(sizeof(double) * arrSize * arrSize);
	if (H_help == NULL)
	{
		release_context(context, ERROR_OUT_OF_MEMORY);
		return;
	}
	double *vector = (double *)malloc(sizeof(double) * arrSize);
	if (vector == NULL)
	{
		free(H_help);
		release_context(context, ERROR_OUT_OF_MEMORY);
		return;
	}
	double *result = (double *)malloc(sizeof(double) * arrSize * arrSize);
	if (result == NULL)
	{
		free(H_help);
		free(vector);
		release_context(context, ERROR_OUT_OF_MEMORY);
		return;
	}
	for (int index = 1; index < arrSize - 1; index++)
	{
		double *H = holder(A, H_help, vector, index, arrSize);
		multiplyMatrixLeft(H, A, result, index, arrSize);
		multiplyMatrixRight(result, H, A, index, arrSize);
	}
	free(H_help);
	free(vector);
	free(result);
}

int release_context(Context *context, int errorCode)
{
	if (context->A != NULL)
	{
		free(context->A);
		context->A = NULL;
	}
	if (context->outputFile)
	{
		fclose(context->outputFile);
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
	Context context[1] = { SUCCESS, NULL, NULL };
	FILE *inputFile = fopen(argv[1], "r");
	if (inputFile == NULL)
	{
		fprintf(stderr, "%s\n", error_message_table[ERROR_CANNOT_OPEN_FILE]);
		return ERROR_CANNOT_OPEN_FILE;
	}
	unit arrSize;
	fscanf(inputFile, "%d\n", &arrSize);

	context->A = (double *)malloc(sizeof(double) * arrSize * arrSize);
	if (context->A == NULL)
	{
		fclose(inputFile);
		fprintf(stderr, "%s\n", error_message_table[ERROR_OUT_OF_MEMORY]);
		return ERROR_OUT_OF_MEMORY;
	}

	for (int i = 0; i < arrSize; i++)
	{
		for (int j = 0; j < arrSize; j++)
		{
			fscanf(inputFile, "%lf ", &(context->A[i * arrSize + j]));
		}
	}
	fclose(inputFile);
	calcNumbers(context->A, context, arrSize);
	if (context->codeError != SUCCESS)
	{
		return release_context(context, SUCCESS);
	}

	context->outputFile = fopen(argv[2], "w");
	if (context->outputFile == NULL)
	{
		free(context->A);
		fprintf(stderr, "%s\n", error_message_table[ERROR_CANNOT_OPEN_FILE]);
		return ERROR_CANNOT_OPEN_FILE;
	}
	printNumbers(context->A, context, arrSize);

	return release_context(context, SUCCESS);
}