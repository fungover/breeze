package org.fungover.breeze.matrix;


/**
 * The Matrix3x3 class represents a 3x3 matrix and provides methods for performing common matrix operations, such as
 * multiplication, transpose, determinant calculation, and matrix inversion. It also includes methods for transforming
 * vectors using the matrix. The matrix is stored as a 2D array of floats, and various matrix operations are implemented
 * to support linear algebra calculations.
 *
 * Methods:
 * - identity3x3: Returns the identity matrix for 3x3 matrices.
 * - multiply3x3: Multiplies the current matrix with another 3x3 matrix.
 * - transpose3x3: Transposes the matrix (flips rows and columns).
 * - determinant3x3: Calculates the determinant of the 3x3 matrix.
 * - inverse: Computes the inverse of the matrix, if it is invertible (based on determinant).
 * - transformVector: Transforms a 3D vector using the matrix.
 *
 * Exception Handling:
 * - Throws IllegalArgumentException if a non-3x3 matrix is provided or if a vector does not have 3 elements.
 * - Throws ArithmeticException if the matrix is not invertible (determinant is zero).
 *
 * Additional Features:
 * - The inverse method calculates the cofactor matrix, adjugate matrix, and divides by the determinant to return the
 * inverse matrix. Matrix printing methods are included to display the matrix and intermediate results like the
 * cofactor and adjugate matrices.
 */

public class Matrix3x3 {

    private final float[][] matrix;

    public Matrix3x3(float[][] values) {
        if (values.length != 3 || values[0].length != 3) {
            throw new IllegalArgumentException("Matrix must be 3x3.");
        }
        this.matrix = values;
    }

    public static Matrix3x3 identity3x3() {
        return new Matrix3x3(new float[][]{
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1}
        });
    }

//     Matrix multiplication
    public Matrix3x3 multiply3x3(Matrix3x3 other) {
        float[][] result = new float[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                float sum = 0;
                for (int k = 0; k < 3; k++) {
                    sum += matrix[i][k] * other.matrix[k][j];
                }
                result[i][j] = sum;
            }
        }
        return new Matrix3x3(result);
    }

    // Matrix transpose
    public Matrix3x3 transpose3x3() {
        float[][] transposed = new float[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                transposed[j][i] = matrix[i][j];
            }
        }
        return new Matrix3x3(transposed);
    }

    // Determinant of the matrix
   public float determinant3x3() {
        return matrix[0][0] * (matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1])
                - matrix[0][1] * (matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0])
                + matrix[0][2] * (matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0]);

    }
    // Inverse the matrix
    public Matrix3x3 inverse() {
        float det = determinant3x3();
        if (det == 0) {
            throw new ArithmeticException("Matrix is not invertible.");
        }

        // Cofactor matrix calculation with correct signs
        float[][] cofactorMatrix = new float[3][3];

        // Calculate each cofactor with correct signs (checkered pattern of signs)
        cofactorMatrix[0][0] = +(matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1]);
        cofactorMatrix[0][1] = -(matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0]);
        cofactorMatrix[0][2] = +(matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0]);

        cofactorMatrix[1][0] = -(matrix[0][1] * matrix[2][2] - matrix[0][2] * matrix[2][1]);
        cofactorMatrix[1][1] = +(matrix[0][0] * matrix[2][2] - matrix[0][2] * matrix[2][0]);
        cofactorMatrix[1][2] = -(matrix[0][0] * matrix[1][2] - matrix[0][2] * matrix[1][0]);

        cofactorMatrix[2][0] = +(matrix[0][1] * matrix[1][2] - matrix[0][2] * matrix[1][1]);
        cofactorMatrix[2][1] = -(matrix[0][0] * matrix[1][2] - matrix[0][2] * matrix[1][0]);
        cofactorMatrix[2][2] = +(matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]);



        // Transpose the cofactor matrix to get the adjugate matrix
        float[][] adjugate = new float[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                adjugate[i][j] = cofactorMatrix[j][i]; // Transpose
            }
        }


        // Divide the adjugate matrix by the determinant
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                adjugate[i][j] /= det;
            }
        }

        return new Matrix3x3(adjugate);
    }


    // Method to transform a vector using the matrix
    public float[] transformVector(float[] vector) {
        if (vector.length != 3) {
            throw new IllegalArgumentException("Vector must have 3 elements.");
        }
        float[] result = new float[3];
        for (int i = 0; i < 3; i++) {
            result[i] = matrix[i][0] * vector[0] + matrix[i][1] * vector[1] + matrix[i][2] * vector[2];
        }
        return result;
    }

    // Print matrix

    public float[][] getMatrix() {
        return matrix;
    }
}
