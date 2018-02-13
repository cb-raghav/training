package testPackage1;

import java.util.*;

public class rotateMatrix {
	public static void rotate(int[][] matrix, char dir) {
		if(dir == 'R') {
			for(int j = 0; j < 3; j++) {
				for(int i = 2; i >= 0; i--) {
					System.out.print(matrix[i][j] + " ");
				}
				System.out.println();
			}
		}
		else if(dir == 'L') {
			for(int j = 2; j >= 0; j--) {
				for(int i = 0; i < 3; i++) {
					System.out.print(matrix[i][j] + " ");
				}
				System.out.println();
			}
		}
	}

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int[][] matrix = new int[3][3];
		System.out.println("Enter the contents of the matrix: ");
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				matrix[i][j] = s.nextInt();
			}
		}
		System.out.print("Enter the direction of rotation (R/L): ");
		char dir = s.next().charAt(0);
		System.out.println("The rotated matrix is: ");
		rotate(matrix, dir);
	}
}