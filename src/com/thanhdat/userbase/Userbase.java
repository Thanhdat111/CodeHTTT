package com.thanhdat.userbase;
import java.util.Random;
import java.util.Arrays;

public class Userbase {
    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static double[][] makeMatrixUserbase(int user, int item) {
        double m[][] = new double[user][item];
        for (int i = 0; i < user; i++) {
            for (int j = 0; j < item; j++) {
                m[i][j] = getRandomNumberInRange(0, 5);
            }
        }
        return m;
    }

    public static void showMaxtrixDouble(double m[][]) {
        int row = m.length;
        int column = m[0].length;
        System.out.println();
        System.out.println("Ma tran tuong quan giua cac user!!!!!");
        for (int i = 0; i < row; i++) {
            System.out.print("User" + i + "\t");
            for (int j = 0; j < column; j++) {
                System.out.print(m[i][j] + "\t");
            }
            System.out.println("\n");
        }
    }

    public static void showMaxtrixInt(int m[][]) {
        int row = m.length;
        int column = m[0].length;
        System.out.println();
        //  System.out.println("Ma tran tuong quan giua cac user!!!!!");
        for (int i = 0; i < row; i++) {
            // System.out.print("User" + i + "\t");
            for (int j = 0; j < column; j++) {
                System.out.print(m[i][j] + "\t");
            }
            System.out.println("\n");
        }
    }

    public static double[][] cosinMatrix(double m[][]) {
        int row = m.length;
        int column = m[0].length;
        //ma tran do tuong quan
        double[][] maxtrix = new double[row][row];
        //makeMatrix
        for (int i = 0; i < row; i++) {
            maxtrix[i][i] = 1;
        }
        int rowStep = 0;
        //cong thuc tinh sin
        for (int i = 0; i < row - 1; i++) {
            for (int k = i + 1; k < row; k++) {
                double numerator = 0;
                double denominator = 0, denominator1 = 0, denominator2 = 0;
                for (int j = 0; j < column; j++) {
                    //tu so
                    numerator += m[i][j] * m[k][j];
                    denominator1 += Math.pow(m[i][j], 2);
                    denominator2 += Math.pow(m[k][j], 2);
                    denominator = Math.sqrt(denominator1) * Math.sqrt(denominator2);
                }
                // lap day ma tran so
                double tmp = numerator / denominator;
                double outcome = Math.round(tmp * 100.0) / 100.0;
                maxtrix[i][k] = maxtrix[k][i] = outcome;
            }
        }
        return maxtrix;
    }

    static double[][] sortByRowWithElement(double mat[][], int n, int k) {
        int totalRow = n;
        double[][] tmpMatrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tmpMatrix[i][j] = mat[i][j];
            }
        }
        for (int i = 0; i < n; i++) {
            // sorting row number 'i'
            Arrays.sort(tmpMatrix[i]);
        }
        double[][] matrixOrder = new double[n][k];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < k - 1; j++) {
                matrixOrder[i][j] = tmpMatrix[i][totalRow - k + j];
            }
        }
        return matrixOrder;
    }

    //lay Ma tran voi cac thuoc tinh cao nhat cua cac lang gieng
    public static double[][] getMatrixcorrelateUsercase(double[][] matrixUsercase, double[][] tmpMatrix) {
        int row = matrixUsercase.length;
        int column = matrixUsercase[0].length;
        int tmpColumn = tmpMatrix[0].length;
        double[][] tmpMatrix1 = new double[row][row];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < row; j++) {
                tmpMatrix1[i][j] = matrixUsercase[i][j];
            }
        }
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                boolean check = false;
                double tmpNumber = tmpMatrix1[i][j];
                for (int k = 0; k < tmpColumn; k++) {
                    if (tmpNumber == tmpMatrix[i][k]) {
                        check = true;
                    }
                }
                if (check == false) {
                    tmpMatrix1[i][j] = 0;
                }
            }
        }
        return tmpMatrix1;
    }

    public static double[][] solve(double[][] matrix, double[][] correlateMatrix) {
        int row = matrix.length;
        int column = matrix[0].length;
        double[][] solveMatrix = new double[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                solveMatrix[i][j] = matrix[i][j];
            }
        }


        for (int j = 0; j < column; j++) {
            for (int i = 0; i < row; i++) {
                if (solveMatrix[i][j] == 0) {
                    double tuso = 0;
                    double mauso = 0;
                    for (int k = 0; k < row; k++) {
                        tuso += solveMatrix[i][k] * correlateMatrix[i][k];
                        mauso += correlateMatrix[i][k];
                    }
                    double tmp = tuso/mauso;
                    double outcome = Math.round(tmp * 100.0) / 100.0;
                    solveMatrix[i][j] = outcome;
                }
            }
        }
        return solveMatrix;
    }

    public static void main(String[] args) {
        System.out.println("Successfully");
        double m[][] = makeMatrixUserbase(5, 7);
        showMaxtrixDouble(m);
        double matrix[][] = cosinMatrix(m);
        showMaxtrixDouble(matrix);
        double[][] matrixOrder = sortByRowWithElement(matrix, matrix.length, 3);
        showMaxtrixDouble(matrixOrder);
        showMaxtrixDouble(matrix);
        double[][] outcomeMatrixUsercase = getMatrixcorrelateUsercase(matrix, matrixOrder);
        System.out.println("Tuong quan Matran cuoi cung");
        showMaxtrixDouble(outcomeMatrixUsercase);
        System.out.println("Ket qua cuoi cung la");
        double outcomeMatrix[][] = solve(m, outcomeMatrixUsercase);
        showMaxtrixDouble(outcomeMatrix);
    }
}

