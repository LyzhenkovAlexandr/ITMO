#ifndef EIGENVALUES_PROTOTYPES_H
#define EIGENVALUES_PROTOTYPES_H
#include "return_codes.h"

#include <stdio.h>
#include <string.h>
typedef unsigned int unit;
typedef struct
{
	int codeError;
	FILE *outputFile;
	double *A;
} Context;
char *error_message_table[] = { [SUCCESS] = "", [ERROR_CANNOT_OPEN_FILE] = "Cannot open file", [ERROR_OUT_OF_MEMORY] = "Out of memory", [ERROR_DATA_INVALID] = "Invalid data", [ERROR_PARAMETER_INVALID] = "Expected 3 arguments to command line, but found" };
double calcC(const double *A, int k, const double *B, const double *prevSquareB, int i, unit arrSize);
double specialScalar(const double *A, int k, const double *B, int j, unit arrSize);
void addTerms(double *a, double scalar, const double *B, int j, unit arrSize);
void insertVectorInQ(double *Q, int rowQ, const double *A, int colA, const double *summa, unit arrSize);
void insertNewScalarVectorB(double *prevSquareB, int indexInsert, const double *Q, int rowVector, unit arrSize);
void normQ(double *Q, unit arrSize);
double *calcQ(double *A, double *Q, double *prevSquareB, double *summa, unit arrSize);
double *transpose(double *A, unit arrSize);
void multiplyMatrix(const double *A, const double *B, double *result, unit arrSize);
void MultiplyRQ(const double *A, const double *B, double *result, unit arrSize);
int isResult(double *A_new, unit arrSize);
void printNumbers(double *A, Context *context, unit arrSize);
void printSquareMinor(double x, double c, double d, double y, Context *context);
void calcNumbers(double *A, Context *context, unit arrSize);
void fillSpecialZero(double *A, int index, unit arrSize);
double *holder(const double *A, double *H, double *vector, int index, unit arrSize);
void multiplyMatrixLeft(const double *A, const double *B, double *result, int index, unit arrSize);
void multiplyMatrixRight(const double *A, const double *B, double *result, int index, unit arrSize);
void calcHolder(double *A, Context *context, unit arrSize);
int release_context(Context *context, int errorCode);
#endif
