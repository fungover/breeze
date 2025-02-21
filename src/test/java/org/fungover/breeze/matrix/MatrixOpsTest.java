package org.fungover.breeze.matrix;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class MatrixOpsTest {
    private static final float DELTA = 0.0001f;

    @Test
    public void testRotateY_0Degrees() {
        Matrix3x3 result = MatrixOps.rotateY3x3(0);

        float[][] expected = {
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1}
        };

        assertMatrixEquals(expected, result.getMatrix(), 0.000001f);
    }

    // Test for rotation around Y-axis with 90 degrees
    @Test
    public void testRotateY_90Degrees() {
        Matrix3x3 result = MatrixOps.rotateY3x3((float) Math.PI / 2); // 90 degrees

        float[][] expected = {
                {0, 0, 1},
                {0, 1, 0},
                {-1, 0, 0}
        };

        assertMatrixEquals(expected, result.getMatrix(), 1e-6f);
    }

    // Test for rotation around Y-axis with 180 degrees
    @Test
    public void testRotateY_180Degrees() {
        Matrix3x3 result = MatrixOps.rotateY3x3((float) Math.PI); // 180 degrees

        float[][] expected = {
                {-1, 0, 0},
                {0, 1, 0},
                {0, 0, -1}
        };

        assertMatrixEquals(expected, result.getMatrix(), 1e-6f);
    }

    // Test for rotation around Y-axis with 270 degrees
    @Test
    public void testRotateY_270Degrees() {
        Matrix3x3 result = MatrixOps.rotateY3x3((float) (3 * Math.PI / 2)); // 270 degrees

        float[][] expected = {
                {0, 0, -1},
                {0, 1, 0},
                {1, 0, 0}
        };

        assertMatrixEquals(expected, result.getMatrix(), 1e-6f);
    }

    // Test for rotation around Z-axis with 0 degrees
    @Test
    public void testRotateZ_0Degrees() {
        Matrix3x3 result = MatrixOps.rotateZ3x3(0);

        float[][] expected = {
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1}
        };

        assertMatrixEquals(expected, result.getMatrix(), 1e-6f);
    }

    // Test for rotation around Z-axis with 90 degrees
    @Test
    public void testRotateZ_90Degrees() {
        Matrix3x3 result = MatrixOps.rotateZ3x3((float) Math.PI / 2); // 90 degrees

        float[][] expected = {
                {0, -1, 0},
                {1, 0, 0},
                {0, 0, 1}
        };

        assertMatrixEquals(expected, result.getMatrix(), 1e-6f);
    }

    // Test for rotation around Z-axis with 180 degrees
    @Test
    public void testRotateZ_180Degrees() {
        Matrix3x3 result = MatrixOps.rotateZ3x3((float) Math.PI); // 180 degrees

        float[][] expected = {
                {-1, 0, 0},
                {0, -1, 0},
                {0, 0, 1}
        };

        assertMatrixEquals(expected, result.getMatrix(), 1e-6f);
    }

    // Test for rotation around Z-axis with 270 degrees
    @Test
    public void testRotateZ_270Degrees() {
        Matrix3x3 result = MatrixOps.rotateZ3x3((float) (3 * Math.PI / 2)); // 270 degrees

        float[][] expected = {
                {0, 1, 0},
                {-1, 0, 0},
                {0, 0, 1}
        };

        assertMatrixEquals(expected, result.getMatrix(), 1e-6f);
    }

    // Test for rotation matrix creation (example)
    @Test
    public void testRotationMatrixExample() {
        Matrix3x3 mat = new Matrix3x3(new float[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        });

        // Example assertions for the rotation matrix. Customize based on actual matrix operations.
        assertNotNull(mat);
    }

    // Helper method to compare two matrices with a tolerance for floating-point comparison
    private void assertMatrixEquals(float[][] expected, float[][] actual, float delta) {
        for (int i = 0; i < expected.length; i++) {
            for (int j = 0; j < expected[i].length; j++) {
                assertEquals(expected[i][j], actual[i][j], delta, "Matrices differ at index [" + i + "][" + j + "]");
            }
        }
    }


    // Test scale3x3 method
    @Test
    public void testScale3x3() {
        Matrix3x3 scaleMatrix = MatrixOps.scale3x3(2f, 3f, 4f);

        // Expected matrix
        float[][] expected = {
                {2.0f, 0.0f, 0.0f},
                {0.0f, 3.0f, 0.0f},
                {0.0f, 0.0f, 4.0f}
        };

        // Flatten the matrices for proper comparison
        float[] expectedFlattened = new float[9];
        float[] actualFlattened = new float[9];

        // Flatten expected matrix
        int index = 0;
        for (float[] row : expected) {
            for (float value : row) {
                expectedFlattened[index++] = value;
            }
        }

        // Flatten actual matrix
        index = 0;
        for (float[] row : scaleMatrix.getMatrix()) {
            for (float value : row) {
                actualFlattened[index++] = value;
            }
        }

        // Compare flattened arrays
        assertArrayEquals(expectedFlattened, actualFlattened, 0.1f);  // Added delta to handle floating point precision issues
    }

    // Test rotateX3x3 method

    @Test
    public void testRotateX3x3() {
        Matrix3x3 rotationMatrix = MatrixOps.rotateX3x3((float) Math.PI / 2); // 90 degrees around X-axis

        // Expected matrix (rotation by 90 degrees around the X-axis)
        float[][] expected = {
                {1.0f, 0.0f, 0.0f},
                {0.0f, 0.0f, -1.0f},
                {0.0f, 1.0f, 0.0f}
        };

        // Flatten the matrices for proper comparison
        float[] expectedFlattened = new float[9];
        float[] actualFlattened = new float[9];

        // Flatten expected matrix
        int index = 0;
        for (float[] row : expected) {
            for (float value : row) {
                expectedFlattened[index++] = value;
            }
        }

        // Flatten actual matrix
        index = 0;
        for (float[] row : rotationMatrix.getMatrix()) {
            for (float value : row) {
                actualFlattened[index++] = value;
            }
        }

        // Compare flattened arrays with a delta for floating-point precision
        assertArrayEquals(expectedFlattened, actualFlattened, 0.1f);  // Delta allows for floating-point precision differences
    }
    // Test translate3x3 method
    @Test
    public void testTranslate3x3() {
        Matrix3x3 translationMatrix = MatrixOps.translate3x3(1.0f, 2.0f);

        float[][] expected = {
                {1.0f, 0.0f, 1.0f},
                {0.0f, 1.0f, 2.0f},
                {0.0f, 0.0f, 1.0f}
        };

        assertArrayEquals(expected, translationMatrix.getMatrix());
    }

    // Test scale4x4 method
    @Test
    public void testScale4x4() {
        Matrix4x4 scaleMatrix = MatrixOps.scale4x4(2.0f, 3.0f, 4.0f);

        float[][] expected = {
                {2.0f, 0.0f, 0.0f, 0.0f},
                {0.0f, 3.0f, 0.0f, 0.0f},
                {0.0f, 0.0f, 4.0f, 0.0f},
                {0.0f, 0.0f, 0.0f, 1.0f}
        };

        assertArrayEquals(expected, scaleMatrix.getMatrix());
    }

    // Test rotateX4x4 method


        @Test
        public void testRotateX4x4() {
            Matrix4x4 rotationMatrix = MatrixOps.rotateX4x4((float) Math.PI / 2); // 90 degrees around X-axis

            // Expected matrix (rotation by 90 degrees around the X-axis in 4x4)
            float[][] expected = {
                    {1.0f, 0.0f, 0.0f, 0.0f},
                    {0.0f, 0.0f, -1.0f, 0.0f},
                    {0.0f, 1.0f, 0.0f, 0.0f},
                    {0.0f, 0.0f, 0.0f, 1.0f}
            };

            // Flatten the matrices for proper comparison
            float[] expectedFlattened = new float[16];
            float[] actualFlattened = new float[16];

            // Flatten expected matrix
            int index = 0;
            for (float[] row : expected) {
                for (float value : row) {
                    expectedFlattened[index++] = value;
                }
            }

            // Flatten actual matrix
            index = 0;
            for (float[] row : rotationMatrix.getMatrix()) {
                for (float value : row) {
                    actualFlattened[index++] = value;
                }
            }

            // Compare flattened arrays with a delta for floating-point precision
            assertArrayEquals(expectedFlattened, actualFlattened, 0.0001f);  // Delta allows for floating-point precision differences
        }



    @Test
    public void testLUDecomposition() {
        Matrix4x4 mat = new Matrix4x4(new float[][]{
                {4, -2, 1, 3},
                {3, 6, 2, 1},
                {1, -1, 3, 5},
                {2, 1, 3, 4}
        });

        Matrix4x4[] lu = MatrixOps.luDecomposition4x4(mat);
        Matrix4x4 L = lu[1];
        Matrix4x4 U = lu[0];

        // Expected L matrix
        float[][] expectedL = {
                {4.0f, -2.0f, 1.0f, 3.0f},
                {0.0f, 7.5f, 1.25f, -1.25f},
                {0.0f, 0.0f, 2.8333333f, 4.1666665f},
                {0.0f, 0.0f, 0.0f, -0.35294104f}
        };

        // Expected U matrix
        float[][] expectedU = {

                {1.0f, 0.0f, 0.0f, 0.0f},
                {0.75f, 1.0f, 0.0f, 0.0f},
                {0.25f, -0.06666667f, 1.0f, 0.0f},
                {0.5f, 0.26666668f, 0.76470584f, 1.0f}
        };



        // Flatten matrices for comparison
        float[] expectedLFlattened = new float[16];
        float[] actualLFlattened = new float[16];

        float[] expectedUFlattened = new float[16];
        float[] actualUFlattened = new float[16];

        // Flatten expected L matrix
        int index = 0;
        for (float[] row : expectedL) {
            for (float value : row) {
                expectedLFlattened[index++] = value;
            }
        }

        // Flatten actual L matrix
        index = 0;
        for (float[] row : L.getMatrix()) {
            for (float value : row) {
                actualLFlattened[index++] = value;
            }
        }

        // Flatten expected U matrix
        index = 0;
        for (float[] row : expectedU) {
            for (float value : row) {
                expectedUFlattened[index++] = value;
            }
        }

        // Flatten actual U matrix
        index = 0;
        for (float[] row : U.getMatrix()) {
            for (float value : row) {
                actualUFlattened[index++] = value;
            }
        }

        // Compare flattened matrices with a delta for floating-point precision
        for (int i = 0; i < expectedLFlattened.length; i++) {
            assertEquals(expectedLFlattened[i], actualLFlattened[i], 0.0001f); // Use a smaller tolerance
        }

        for (int i = 0; i < expectedUFlattened.length; i++) {
            assertEquals(expectedUFlattened[i], actualUFlattened[i], 0.0001f); // Use a smaller tolerance

        }


    }



    @Test
    public void testQRDecomposition() {
        Matrix4x4 mat = new Matrix4x4(new float[][]{
                {4, -2, 1, 3},
                {3, 6, 2, 1},
                {1, -1, 3, 5},
                {2, 1, 3, 4}
        });

        Matrix4x4[] qr = MatrixOps.qrDecomposition4x4(mat);
        Matrix4x4 Q = qr[0];
        Matrix4x4 R = qr[1];



        // Expected R matrix (upper triangular)
        float[][] expectedR = {
                {5.477226f, 2.0083158f, 3.4689095f, 5.112077f},
                {0.0f, 6.16171f, 0.49228773f, -1.8284965f},
                {0.0f, 0.0f, 3.2748008f, 4.6313696f},
                {0.0f, 0.0f, 0.0f, 0.27144045f}
        };


        for (int i = 0; i < expectedR.length; i++) {
            assertArrayEquals(expectedR[i], R.getMatrix()[i], 0.0001f);
        }


        // Validate Q is orthonormal (Q^T * Q should be identity)
        float[][] qtq = multiplyMatrices(transposeMatrix(Q.getMatrix()), Q.getMatrix());
        float[][] identity = {
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        };
        for (int i = 0; i < identity.length; i++) {
            assertArrayEquals(identity[i], qtq[i], 0.0001f);  // Use a small tolerance
        }

    }


    // Helper function to transpose a matrix
    private float[][] transposeMatrix(float[][] matrix) {
        int n = matrix.length;
        float[][] transposed = new float[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                transposed[j][i] = matrix[i][j];
            }
        }
        return transposed;
    }

    // Helper function to multiply two matrices
    private float[][] multiplyMatrices(float[][] A, float[][] B) {
        int n = A.length;
        float[][] result = new float[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    result[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return result;
    }
}
