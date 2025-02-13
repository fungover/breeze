package org.fungover.breeze.matrix;

/**
 * The Matrix4x4 class represents a 4x4 matrix and provides methods for various matrix operations such as multiplication,
 * transpose, determinant, cofactor, and inverse. It is primarily used in 3D graphics, physics calculations, and transformations.
 * The matrix is stored as a 2D array of floats, and operations like multiplication and inversion are implemented to
 * perform calculations using standard linear algebra methods.
 *
 * Methods:
 * - identity4x4: Returns the identity matrix for 4x4 matrices.
 * - multiply4x4: Multiplies the current matrix with another 4x4 matrix.
 * - determinant4x4: Calculates the determinant of the 4x4 matrix.
 * - cofactor4x4: Computes the cofactor of a specific element in the matrix.
 * - transpose: Transposes the matrix (flips rows and columns).
 * - inverse: Calculates the inverse of the matrix, if it is invertible.
 * - printMatrix: Prints the matrix to the console in a formatted way.
 *
 * Exception Handling:
 * - Throws IllegalArgumentException if a non-4x4 matrix is provided.
 * - Throws ArithmeticException if the matrix is singular (determinant is zero) and cannot be inverted.
 */

public class Matrix4x4 {

    private final float[][] matrix;

    public Matrix4x4(float[][] values) {
        if (values.length != 4 || values[0].length != 4) {
            throw new IllegalArgumentException("Matrix must be 4x4.");
        }
        this.matrix = values;
    }

    public float[][] getMatrix() {
        return matrix;
    }

    public static Matrix4x4 identity4x4() {
        return new Matrix4x4(new float[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        });
    }
    // Multiply matrix
    public Matrix4x4 multiply4x4(Matrix4x4 other) {
        float[][] result = new float[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                float sum = 0;
                for (int k = 0; k < 4; k++) {
                    sum += matrix[i][k] * other.matrix[k][j];
                }
                result[i][j] = sum;
            }
        }
        return new Matrix4x4(result);
    }
    // Determinant matrix
    public float determinant4x4() {
        float det = 0;
        for (int col = 0; col < 4; col++) {
            det += (Math.pow(-1, col) * matrix[0][col] * cofactor4x4(0, col));
        }
        return det;
    }

public float cofactor4x4(int row, int col) {
    float[][] minor = new float[3][3]; // 3x3 matrix for the cofactor
    int minorRow = 0;

    // Skip the row and column of the element at (row, col)
    for (int i = 0; i < 4; i++) {
        if (i == row) continue; // Skip the row
        int minorCol = 0;
        for (int j = 0; j < 4; j++) {
            if (j == col) continue; // Skip the column
            minor[minorRow][minorCol] = matrix[i][j];
            minorCol++;
        }
        minorRow++;
    }

    // Apply the sign correction based on the position (row, col)
    float cofactor = minor[0][0] * (minor[1][1] * minor[2][2] - minor[1][2] * minor[2][1])
            - minor[0][1] * (minor[1][0] * minor[2][2] - minor[1][2] * minor[2][0])
            + minor[0][2] * (minor[1][0] * minor[2][1] - minor[1][1] * minor[2][0]);

    // Apply the checkerboard sign pattern
    return (float) (Math.pow(-1.0, (double) row + col) * cofactor);


}

    // Transpose matrix
    public Matrix4x4 transpose() {
        float[][] transposed = new float[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                transposed[j][i] = matrix[i][j]; // Swap row and column indices
            }
        }

        return new Matrix4x4(transposed);
    }
    // Inverse matrix
    public Matrix4x4 inverse() {
        float det = determinant4x4();
        if (Math.abs(det) < 0.0001) {
            throw new ArithmeticException("Matrix is singular and cannot be inverted.");
        }


        float[][] adjugate = new float[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                adjugate[j][i] = cofactor4x4(i, j); // Transpose of cofactor matrix
            }
        }


        float[][] inverseMatrix = new float[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                inverseMatrix[i][j] = adjugate[i][j] / det;
            }
        }


        return new Matrix4x4(inverseMatrix);
    }


}
