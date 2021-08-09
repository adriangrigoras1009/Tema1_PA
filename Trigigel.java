import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;


public class Trigigel {
	
	static class Resolve {
		public static final String INPUT_FILE = "trigigel.in";
		public static final String OUTPUT_FILE = "trigigel.out";
		long Mod = 1000000007;
		public long n;

		public void solve() {
			readInput();
			writeOutput(getResult());
		}

		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(INPUT_FILE));
				n = sc.nextLong();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} 
		private void writeOutput(long result) {
			try {
				PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
				pw.printf("%d", result);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		long[][] multiply_matrix(long[][] A, long[][] B) {
			long[][] tmp = new long[3][3];
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					long sum = 0;
					for (int k = 0; k < 3; k++) {
						long sum_aux = ((A[i][k] % Mod) * (B[k][j] % Mod)) % Mod;
						sum = ((sum % Mod) + (sum_aux % Mod)) % Mod;
					}
					tmp[i][j] = sum;
				}
			}
			return tmp;
		}

		long[][] power_matrix(long[][] C, long p) {
			long[][] tmp = {
				{1, 0, 0},
				{0, 1, 0},
				{0, 0, 1}
			};
			while (p != 1) {
				if (p % 2 == 0) {
					C = multiply_matrix(C,C);
					p = p / 2;
				} else {
					tmp = multiply_matrix(tmp, C);
					p--;
				}
			}
			return multiply_matrix(C, tmp);
		}
		private long getResult() {
			if (n == 1) {
				return 1;
			}
			if (n == 2) {
				return 3;
			}
			if (n == 3) {
				return 6;
			}
			long[][] c = {
				{0, 0, 1},
				{1, 0, 0},
				{0, 1, 1}
			};			
			long sol2 = 2;
			long sol3 = 3;
			long sol1 = 1;
			if ((n - 1) % 3 == 0) {
				long[][] c1 = power_matrix(c, n - 2);
				sol1 = 1 * c1[0][2] + 2 * c1[1][2] + 3 * c1[2][2] - 1;
				if ((n - 4) > 0) {
					long[][] c2 = power_matrix(c, n - 4);
					sol2 = 1 * c2[0][2] + 2 * c2[1][2] + 3 * c2[2][2] - 1;
				} else {
					sol2 = 2;
				}
				long[][] c3 = power_matrix(c, n - 3);
				sol3 = 1 * c3[0][2] + 2 * c3[1][2] + 3 * c3[2][2] - 1;
			} else if ((n - 2) % 3 == 0) {
				long[][] c1 = power_matrix(c, n - 3);
				sol1 = 1 * c1[0][2] + 2 * c1[1][2] + 3 * c1[2][2] - 1;
				long[][] c2 = power_matrix(c, n - 2);
				sol2 = 1 * c2[0][2] + 2 * c2[1][2] + 3 * c2[2][2] - 1;
				if (n - 4 > 0) {
					long[][] c3 = power_matrix(c, n - 4);
					sol3 = 1 * c3[0][2] + 2 * c3[1][2] + 3 * c3[2][2] - 1;
				} else {
					sol3 = 3;
				}
			} else if ((n - 3) % 3 == 0) {
				long[][] c3 = power_matrix(c, n - 2);
				sol3 = 1 * c3[0][2] + 2 * c3[1][2] + 3 * c3[2][2] - 1;
				long[][] c2 = power_matrix(c, n - 3);
				sol2 = 1 * c2[0][2] + 2 * c2[1][2] + 3 * c2[2][2] - 1;
				if (n - 4 > 0) {
					long[][] c1 = power_matrix(c, n - 4);
					sol1 = 1 * c1[0][2] + 2 * c1[1][2] + 3 * c1[2][2] - 1;
				} else {
					sol1 = 1;
				}
			}
			long sol_final_aux = ((sol1 % Mod) + (sol2 % Mod));
			return ((sol_final_aux % Mod) + (sol3 % Mod)) % Mod;
		}
	}
	public static void main(String[] args) {
		new Resolve().solve();
	}
}
