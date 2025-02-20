package org.fungover.breeze.matrix;

import org.junit.jupiter.api.Test;



import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

public class Matrix3x3Test {
    @Test
    void testMatrix3x3Constructor() {
        // 1. Valid 3x3 matrix
        Matrix3x3 matrix = new Matrix3x3(new float[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        });
        assertThat(matrix.getMatrix()).isEqualTo(new float[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        });

        // 2. Invalid matrix (not 3x3)
        assertThatThrownBy(() -> new Matrix3x3(new float[][]{
                {1, 2},  // 2x3 matrix
                {3, 4},
        }))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Matrix must be 3x3.");

        // 3. Invalid matrix (not 3x3)
        assertThatThrownBy(() -> new Matrix3x3(new float[][]{
                {1, 2, 3},  // 3x2 matrix
                {4, 5, 6}
        }))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Matrix must be 3x3.");
    }
    @Test
    void testIdentityMatrix3x3() {
        // Create the identity matrix for 3x3
        Matrix3x3 identityMatrix3x3 = Matrix3x3.identity3x3();

        // Expected identity matrix for 3x3
        float[][] expectedValues3x3 = {
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1}
        };

        // Retrieve the actual matrix values
        float[][] actualValues3x3 = identityMatrix3x3.getMatrix();

        // Compare expected and actual matrix values for 3x3
        assertArrayEquals(expectedValues3x3, actualValues3x3, "The 3x3 identity matrix is incorrect.");

    }

    @Test
    void testMultiplyMatrix3x3() {
        float[][] data1 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        float[][] data2 = {
                {9,8,7},
                {6,5,4},
                {3,2,1}
        };
        Matrix3x3 matrix1 = new Matrix3x3(data1);
        Matrix3x3 matrix2 = new Matrix3x3(data2);

        Matrix3x3 result = matrix1.multiply3x3(matrix2);

        float[][] expected = {
                {30, 24, 18},
                {84, 69, 54},
                {138, 114, 90}
        };
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(expected[i][j], result.getMatrix()[i][j], 0.0001);
            }
        }
    }

    @Test
    void testTranspose () {
        // 1. General Matrix
        Matrix3x3 matrix1 = new Matrix3x3(new float[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        });
        Matrix3x3 transposed1 = matrix1.transpose3x3();
        assertThat(transposed1.getMatrix()).isEqualTo(new float[][]{
                {1, 4, 7},
                {2, 5, 8},
                {3, 6, 9}
        });

        // 2. Identity Matrix (transpose should be the same)
        Matrix3x3 identity = Matrix3x3.identity3x3();
        assertThat(identity.transpose3x3().getMatrix()).isEqualTo(identity.getMatrix());

        // 3. Symmetric Matrix (transpose should be the same)
        Matrix3x3 symmetricMatrix = new Matrix3x3(new float[][]{
                {1, 2, 3},
                {2, 1, 4},
                {3, 4, 1}
        });
        assertThat(symmetricMatrix.transpose3x3().getMatrix()).isEqualTo(symmetricMatrix.getMatrix());

        // 4. Zero Matrix (transpose should be the same)
        Matrix3x3 zeroMatrix = new Matrix3x3(new float[][]{
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        });
        assertThat(zeroMatrix.transpose3x3().getMatrix()).isEqualTo(zeroMatrix.getMatrix());
    }

    @Test
    void testDeterminant() {
        float[][] data = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Matrix3x3 matrix = new Matrix3x3(data);
        float determinant = matrix.determinant3x3();

        assertEquals(0.0, determinant, 0.0001);
    }
    @Test
    void testInverse() {
        // Test with an invertible matrix
        Matrix3x3 matrix = new Matrix3x3(new float[][]{
                {4, 7, 2},
                {3, 6, 1},
                {2, 5, 3}
        });

        Matrix3x3 expectedInverse = new Matrix3x3(new float[][]{
                {1.4444444f, -1.2222222f, -0.5555556f},
                {-0.7777778f, 0.8888889f, 0.22222222f},
                {0.33333334f, 0.22222222f, 0.33333334f}
        });

        Matrix3x3 result = matrix.inverse();

        for (int i = 0; i < 3; i++) {
            assertArrayEquals(expectedInverse.getMatrix()[i], result.getMatrix()[i], 0.0001f);
        }

        // Test with a non-invertible matrix (determinant = 0)
        Matrix3x3 singularMatrix = new Matrix3x3(new float[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        });

        Exception exception = assertThrows(ArithmeticException.class, singularMatrix::inverse);
        assertEquals("Matrix is not invertible.", exception.getMessage());
    }


    @Test
    void testTransformVector() {
        // Create a 3x3 matrix
        float[][] matrixValues = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Matrix3x3 matrix = new Matrix3x3(matrixValues);

        // Define a 3D vector
        float[] vector = {1, 2, 3};

        // Expected result of matrix multiplication with the vector
        float[] expected = {14, 32, 50};  // Result of multiplying the matrix with the vector

        // Transform the vector using the matrix
        float[] result = matrix.transformVector(vector);

        // Verify the result
        assertArrayEquals(expected, result, 0.0001F);
    }

    @Test
    void testTransformVectorInvalidLength() {
        // Create a 3x3 matrix
        float[][] matrixValues = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Matrix3x3 matrix = new Matrix3x3(matrixValues);

        // Define a vector that doesn't have 3 elements
        float[] vector = {1, 2};  // Invalid vector length (should be 3)

        // Check that an exception is thrown
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> matrix.transformVector(vector));
        assertEquals("Vector must have 3 elements.", exception.getMessage());
    }




}
