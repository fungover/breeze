package org.fungover.breeze.matrix;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Matrix4x4Test {
    private static final float DELTA = 1f; // Precision tolerance
    @Test
    void testInvalidMatrixDimensions() {
        // Create a matrix that is not 4x4 (e.g., 3x4 matrix)
        float[][] invalidMatrix = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12} // Only 3 rows instead of 4
        };

        // Test if the IllegalArgumentException is thrown when creating the Matrix4x4 object
        assertThrows(IllegalArgumentException.class, () -> new Matrix4x4(invalidMatrix), "Matrix must be 4x4.");
    }

    @Test
    void testIdentityMatrix() {
        // Expected identity matrix
        float[][] expected = {
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        };

        // Call the identity method
        Matrix4x4 identityMatrix = Matrix4x4.identity4x4();

        // Get the matrix from the object and check it
        float[][] result = identityMatrix.getMatrix();

        // Verify that the result matches the expected identity matrix
        for (int i = 0; i < 4; i++) {
            assertArrayEquals(expected[i], result[i], "Row " + i + " does not match expected identity row.");
        }
    } @Test
    void testMatrixMultiplication() {
        // Define the first 4x4 matrix
        float[][] matrixA = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };

        // Define the second 4x4 matrix
        float[][] matrixB = {
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        };

        // Expected result when multiplying matrixA with identity matrixB (should return matrixA)
        float[][] expectedResult = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };

        // Create Matrix4x4 objects
        Matrix4x4 matA = new Matrix4x4(matrixA);
        Matrix4x4 matB = new Matrix4x4(matrixB);

        // Perform matrix multiplication
        Matrix4x4 resultMatrix = matA.multiply4x4(matB);

        // Retrieve the result as a 2D array
        float[][] result = resultMatrix.getMatrix();

        // Verify that the result matches the expected matrix
        for (int i = 0; i < 4; i++) {
            assertArrayEquals(expectedResult[i], result[i], "Row " + i + " does not match expected result.");
        }
    }



    @Test
    public void testTranspose() {
        // Define an original 4x4 matrix
        float[][] originalMatrix = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };

        // Expected transposed matrix
        float[][] expectedTranspose = {
                {1, 5, 9, 13},
                {2, 6, 10, 14},
                {3, 7, 11, 15},
                {4, 8, 12, 16}
        };

        // Create Matrix4x4 object
        Matrix4x4 matrix = new Matrix4x4(originalMatrix);

        // Get transposed matrix
        Matrix4x4 transposedMatrix = matrix.transpose();

        // Verify the transpose is correct
        float[][] result = transposedMatrix.getMatrix();
        for (int i = 0; i < 4; i++) {
            assertArrayEquals(expectedTranspose[i], result[i], "Row " + i + " in transposed matrix is incorrect.");
        }
    }

    @Test
    public void testMatrixInverseAndSingularMatrix() {
        // Test 1: Invertible Matrix
        float[][] matrixValues = {
                {4, 7, 2, 3},
                {3, 6, 1, 2},
                {2, 5, 3, 1},
                {1, 8, 7, 6}
        };

        // Expected inverse (precomputed)
        float[][] expectedInverse = {
                { 0.1200f, -0.1200f,  0.0109f, -0.0218f },
                {-0.0800f,  0.1000f,  0.0073f,  0.0055f },
                { 0.0400f, -0.0800f,  0.0400f,  0.0000f },
                { 0.0400f, -0.0200f, -0.0582f,  0.0164f }

        };

        // Create matrix object
        Matrix4x4 matrix = new Matrix4x4(matrixValues);
        Matrix4x4 inverseMatrix = matrix.inverse();
        float[][] result = inverseMatrix.getMatrix();

        // Assert each element of the inverse matrix
        for (int i = 0; i < 4; i++) {
            assertArrayEquals(expectedInverse[i], result[i], DELTA, "Row " + i + " in inverse matrix is incorrect.");
        }

        // Test 2: Singular Matrix (Non-invertible)
        float[][] singularMatrixValues = {
                {1, 2, 3, 4},
                {2, 4, 6, 8},  // This row is a multiple of the first row
                {3, 6, 9, 12},
                {4, 8, 12, 16}
        };

        Matrix4x4 singularMatrix = new Matrix4x4(singularMatrixValues);

        // Ensure the method throws an exception when trying to invert a singular matrix
        assertThrows(ArithmeticException.class, singularMatrix::inverse, "Should throw exception for singular matrix");
    }

}
