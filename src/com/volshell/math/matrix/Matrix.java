package com.volshell.math.matrix;

public class Matrix {

	/**
	 * 实现矩阵的顺时针旋转90度，而不开辟额外空间
	 * 
	 * @param mat整数矩阵
	 * @param n矩阵的行数
	 * @return 顺时针旋转90度之后的矩阵
	 */
	public static int[][] transformMatrix(int[][] mat, int n) {
		int i = 0;
		while (i < n / 2) {
			for (int j = i; j < n - 1 - i; j++) {
				int temp = mat[i][j];

				mat[i][j] = mat[n - 1 - j][i];
				mat[n - 1 - j][i] = mat[n - 1 - i][n - 1 - j];
				mat[n - 1 - i][n - 1 - j] = mat[j][n - 1 - i];
				mat[j][n - 1 - i] = temp;

			}
			i++;
		}
		return mat;
	}
}
