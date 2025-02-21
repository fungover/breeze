package org.fungover.breeze.matrix;

public class MatrixOps {

    /**
     * The `MatrixOps` class provides various methods for creating and manipulating 3x3 and 4x4 transformation matrices,
     * including scaling, rotation, and translation. It includes methods for constructing scaling matrices
     * (scale3x3 and scale4x4), rotation matrices around the X, Y, and Z axes (rotateX3x3, rotateY3x3, rotateZ3x3),
     * and a 2D translation matrix (translate3x3). Additionally, the class implements LU decomposition
     * (`luDecomposition4x4`) and QR decomposition (`qrDecomposition4x4`) for 4x4 matrices, which factorize the matrix
     * into simpler triangular matrices for further mathematical operations.
     * These methods are designed to facilitate common geometric transformations and matrix factorization techniques
     * used in 3D graphics, physics simulations, and computational mathematics.
     */

    // Create a 3x3 scaling matrix
    public static Matrix3x3 scale3x3(float scaleX, float scaleY, float scaleZ) {
        return new Matrix3x3(new float[][]{
                {scaleX, 0, 0},
                {0, scaleY, 0},
                {0, 0, scaleZ}
        });
    }

    // Create a 3x3 rotation matrix around the X-axis
    public static Matrix3x3 rotateX3x3(float angle) {
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        return new Matrix3x3(new float[][]{
                {1, 0, 0},
                {0, cos, -sin},
                {0, sin, cos}
        });
    }

    // Create a 3x3 rotation matrix around the Y-axis
    public static Matrix3x3 rotateY3x3(float angle) {
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        return new Matrix3x3(new float[][]{
                {cos, 0, sin},
                {0, 1, 0},
                {-sin, 0, cos}
        });
    }

    // Create a 3x3 rotation matrix around the Z-axis
    public static Matrix3x3 rotateZ3x3(float angle) {
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        return new Matrix3x3(new float[][]{
                {cos, -sin, 0},
                {sin, cos, 0},
                {0, 0, 1}
        });
    }

    // Create a 3x3 translation matrix
    public static Matrix3x3 translate3x3(float translateX, float translateY) {
        return new Matrix3x3(new float[][]{
                {1, 0, translateX},
                {0, 1, translateY},
                {0, 0, 1}
        });
    }

    // 4x4 Matrix - Rotation, Scaling, and Decomposition methods

    // Create a 4x4 scaling matrix
    public static Matrix4x4 scale4x4(float scaleX, float scaleY, float scaleZ) {
        return new Matrix4x4(new float[][]{
                {scaleX, 0, 0, 0},
                {0, scaleY, 0, 0},
                {0, 0, scaleZ, 0},
                {0, 0, 0, 1}
        });
    }

    // Create a 4x4 rotation matrix around the X-axis
    public static Matrix4x4 rotateX4x4(float angle) {
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        return new Matrix4x4(new float[][]{
                {1, 0, 0, 0},
                {0, cos, -sin, 0},
                {0, sin, cos, 0},
                {0, 0, 0, 1}
        });
    }


    // Decomposition Method - LU Decomposition for 4x4 matrix (L and U matrices)
    public static Matrix4x4[] luDecomposition4x4(Matrix4x4 mat) {
        float[][] m = mat.getMatrix();
        int n = 4;
        float[][] L = new float[n][n];
        float[][] U = new float[n][n];

        // Decompose matrix into L and U
        for (int i = 0; i < n; i++) {
            // Upper Triangular
            for (int k = i; k < n; k++) {
                float sum = 0;
                for (int j = 0; j < i; j++) {
                    sum += (L[i][j] * U[j][k]);
                }
                U[i][k] = m[i][k] - sum;
            }

            // Lower Triangular
            for (int k = i; k < n; k++) {
                if (i == k) {
                    L[i][i] = 1; // Diagonal as 1
                } else {
                    float sum = 0;
                    for (int j = 0; j < i; j++) {
                        sum += (L[k][j] * U[j][i]);
                    }
                    L[k][i] = (m[k][i] - sum) / U[i][i];
                }
            }
        }

        Matrix4x4 lower = new Matrix4x4(L);
        Matrix4x4 upper = new Matrix4x4(U);
        return new Matrix4x4[]{lower, upper};
    }

    public static Matrix4x4[] qrDecomposition4x4(Matrix4x4 mat) {
        float[][] A = mat.getMatrix();
        int n = 4;
        float[][] Q = new float[n][n];
        float[][] R = new float[n][n];

        // Copy A to Q initially (will be orthogonalized)
        for (int i = 0; i < n; i++) {
            System.arraycopy(A[i], 0, Q[i], 0, n);
        }

        // Gram-Schmidt Process
        for (int i = 0; i < n; i++) {
            // Compute R[i][i] as the norm of Q[:, i]
            float norm = 0.0f;
            for (int j = 0; j < n; j++) {
                norm += Q[j][i] * Q[j][i];
            }
            R[i][i] = (float) Math.sqrt(norm);

            // Normalize Q[:, i]
            for (int j = 0; j < n; j++) {
                Q[j][i] /= R[i][i];
            }

            // Compute R[i][j] for j > i and update Q
            for (int j = i + 1; j < n; j++) {
                float dotProduct = 0.0f;
                for (int k = 0; k < n; k++) {
                    dotProduct += Q[k][i] * Q[k][j];
                }
                R[i][j] = dotProduct;

                for (int k = 0; k < n; k++) {
                    Q[k][j] -= R[i][j] * Q[k][i];
                }
            }
        }




        Matrix4x4 qMatrix = new Matrix4x4(Q);
        Matrix4x4 rMatrix = new Matrix4x4(R);
        return new Matrix4x4[]{qMatrix, rMatrix};
    }

}
