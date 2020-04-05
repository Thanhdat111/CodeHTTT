package com.thanhdat.matrixfactorization;

import java.util.Random;

public class MatrixFactorization {
    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static double[][] makeMatrix(int user, int item, int min, int max) {
        double m[][] = new double[user][item];
        for (int i = 0; i < user; i++) {
            for (int j = 0; j < item; j++) {
                m[i][j] = getRandomNumberInRange(min, max);
            }
        }
        return m;
    }

    public static void showMaxtrix(double m[][]) {
        int row = m.length;
        int column = m[0].length;
        System.out.println();
        for (int i = 0; i < row; i++) {
            System.out.print("User" + i + "\t");
            for (int j = 0; j < column; j++) {
                System.out.print(m[i][j] + "\t");
            }
            System.out.println("\n");
        }
    }

    public static double[][] matrixFactorization(double[][] DTrain, double[][] W, double[][] H, double beta) {
        int t = 0;
        int row = DTrain.length;
        int column = DTrain[0].length;
        int k = W[0].length;
        while (t < 100000) {
            int user = getRandomNumberInRange(0, row - 1);
            int item = getRandomNumberInRange(0, column - 1);
            double r = DTrain[user][item];
            double r_bar = 0;
            for (int i = 0; i < k; i++) {
                r_bar += W[user][i] * H[i][item];
            }
            double err = r - r_bar;
            for (int i = 0; i < k; i++) {
                double tmp = W[user][i];
                W[user][i] += 2 * beta * err * H[i][item];
                H[i][item] += 2 * beta * err * tmp;
            }
            t++;
        }
        //show matrix after matrix factorization
        double[][] outComeMatrix = new double[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                outComeMatrix[i][j]=0;
            for (int m = 0; m < k; m++) {
                    outComeMatrix[i][j] += W[i][m] * H[m][j];
                }
            }
        }
        return outComeMatrix;
    }


    public static void main(String[] args) {
        double[][] Dtrain = makeMatrix(5, 7, 0, 5);
        showMaxtrix(Dtrain);
        System.out.println("Matrix W:");
        double[][] W = makeMatrix(5, 2, 0, 5);
        showMaxtrix(W);
        System.out.println("Matrix H:");
        double[][] H = makeMatrix(2, 7, 0, 5);
        showMaxtrix(H);
        System.out.println("Show outcome Matrix");
        double[][] outCome = matrixFactorization(Dtrain, W, H, 0.004);
        showMaxtrix(outCome);
    }

}
