package com.maths;

public final class LinearAlgebra {
    public static double[][] transposeMatrix(double[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        double[][] transposedMatrix = new double[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposedMatrix[j][i] = matrix[i][j];
            }
        }

        return transposedMatrix;
    }

    public static double[][] matrixMultiplication(double[][] matrixA, double[][] matrixB) {
        int aRows = matrixA.length;
        int aCols = matrixA[0].length;
        int bRows = matrixB.length;
        int bCols = matrixB[0].length;

        if (aCols != bRows) {
            throw new IllegalArgumentException("Matrix dimensions are not compatible for multiplication");
        }

        double[][] result = new double[aRows][bCols];

        for (int i = 0; i < aRows; i++) {
            for (int j = 0; j < bCols; j++) {
                for (int k = 0; k < aCols; k++) {
                    result[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }

        return result;
    }

    public static double[] matrixVectorMultiplication(double[][] matrixA, double[] vectorB) {
        int aRows = matrixA.length;
        int aCols = matrixA[0].length;
        int bLength = vectorB.length;

        if (aCols != bLength) {
            throw new IllegalArgumentException("Matrix dimensions are not compatible for multiplication");
        }

        double[] result = new double[aRows];

        for (int i = 0; i < aRows; i++) {
            for (int j = 0; j < bLength; j++) {
                result[i] += matrixA[i][j] * vectorB[j];
            }
        }

        return result;
    }


    public static double[][] inverseMatrix(double[][] matrix) {
        int n = matrix.length;

        double[][] identity = new double[n][n];
        for (int i = 0; i < n; i++) {
            identity[i][i] = 1;
        }

        // Gauss-Jordan elimination
        for (int i = 0; i < n; i++) {
            double pivot = matrix[i][i];
            if (pivot == 0) {
                throw new IllegalArgumentException("Matrix is not invertible");
            }

            for (int j = 0; j < n; j++) {
                matrix[i][j] /= pivot;
                identity[i][j] /= pivot;
            }

            for (int j = 0; j < n; j++) {
                if (i != j) {
                    double factor = matrix[j][i];
                    for (int k = 0; k < n; k++) {
                        matrix[j][k] -= factor * matrix[i][k];
                        identity[j][k] -= factor * identity[i][k];
                    }
                }
            }
        }

        return identity;
    }







}
