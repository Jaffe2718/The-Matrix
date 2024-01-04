package me.jaffe2718.the_matrix.unit;

import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public abstract class MathUnit {
    public static double vec3dCos(@NotNull Vec3d a, @NotNull Vec3d b) {
        double len_len = a.length() * b.length();
        if (len_len != 0) {   // not zero vector
            return a.dotProduct(b) / len_len;
        } else {              // zero vector -> angle is 0
            return 1;
        }
    }

    public static boolean isBetween(double value, double minInclusive, double maxInclusive) {
        return value >= minInclusive && value <= maxInclusive;
    }

    public static Vec3d relativePos(@NotNull Vec3d from, @NotNull Vec3d to) {
        return to.subtract(from);
    }

    @Contract("_, _ -> new")
    public static @NotNull Vec3d getRotationVector(double pitch, double yaw) {
        double f = pitch * Math.PI / 180;
        double g = -yaw * Math.PI / 180;
        double h = Math.cos(g);
        double i = Math.sin(g);
        double j = Math.cos(f);
        double k = Math.sin(f);
        return new Vec3d(i * j, -k, h * j);
    }

    public static float getPitchDeg(@NotNull Vec3d rotationVector) {
        return (float) Math.asin(-rotationVector.y / rotationVector.length()) * 57.295776F;
    }

    public static float getYawDeg(@NotNull Vec3d rotationVector) {
        return (float) Math.atan2(rotationVector.x, rotationVector.z) * 57.295776F;
    }

    /**
     * Class Matrix4i is a 4x4 matrix of integers.<br>
     * It is used to store the game board of 2048.
     * */
    public static class Matrix4i {
        public int[][] matrix;

        /**
         * Restore from a 4x4 matrix of integers.
         * */
        public Matrix4i(int[] oneDimensionalArray) {
            matrix = new int[4][4];
            boolean allZero = true;
            for (int i = 0; i < 16; i++) {
                matrix[i / 4][i % 4] = oneDimensionalArray[i];
                if (matrix[i / 4][i % 4] != 0) {
                    allZero = false;
                }
            }
            if (allZero) {
                this.addRandom();
                this.addRandom();
            }
        }

        /**
         * Randomly initialize for game restart.
         * */
        public Matrix4i() {
            this.reset();
        }

        public void reset() {
            matrix = new int[][]{{0, 0, 0, 0},
                                 {0, 0, 0, 0},
                                 {0, 0, 0, 0},
                                 {0, 0, 0, 0}};
            this.addRandom();
            this.addRandom();
        }

        public void up2048() {
            for (int i = 0; i < 4; i++) {
                int[] row = matrix[i];
                for (int j = 0; j < 3; j++) {
                    if (row[j] == row[j + 1]) {
                        row[j] *= 2;
                        row[j + 1] = 0;
                    }
                }
            }
            this.addRandom();
        }

        public void down2048() {
            for (int i = 0; i < 4; i++) {
                int[] row = matrix[i];
                for (int j = 3; j > 0; j--) {
                    if (row[j] == row[j - 1]) {
                        row[j] *= 2;
                        row[j - 1] = 0;
                    }
                }
            }
            this.addRandom();
        }

        public void left2048() {
            for (int i = 0; i < 4; i++) {
                int[] row = matrix[i];
                for (int j = 0; j < 3; j++) {
                    if (row[j] == row[j + 1]) {
                        row[j] *= 2;
                        row[j + 1] = 0;
                    }
                }
            }
            this.addRandom();
        }

        public void right2048() {
            for (int i = 0; i < 4; i++) {
                int[] row = matrix[i];
                for (int j = 3; j > 0; j--) {
                    if (row[j] == row[j - 1]) {
                        row[j] *= 2;
                        row[j - 1] = 0;
                    }
                }
            }
            this.addRandom();
        }

        private void addRandom() {
            int x = (int) (Math.random() * 4);
            int y = (int) (Math.random() * 4);
            while (matrix[x][y] != 0) {
                x = (int) (Math.random() * 4);
                y = (int) (Math.random() * 4);
            }
            matrix[x][y] = Math.random() < 0.9 ? 2 : 4;
        }

        /**
         * @return the score of the game board<br>
         * If maximum value < 256, score = 0<br>
         * Else score = floor(log2(sum(matrix))) - 5
         * */
        public int getScore() {
            int max = 0;
            int sum = 0;
            for (int[] row : matrix) {
                for (int i : row) {
                    if (i > max) {
                        max = i;
                    }
                    sum += i;
                }
            }
            if (max < 256) {
                return 0;
            } else {
                return (int) (Math.log(sum) / Math.log(2)) - 5;
            }
        }

        public int[] to1DArray() {
            int[] oneDimensionalArray = new int[16];
            for (int i = 0; i < 16; i++) {
                oneDimensionalArray[i] = matrix[i / 4][i % 4];
            }
            return oneDimensionalArray;
        }
    }
}
