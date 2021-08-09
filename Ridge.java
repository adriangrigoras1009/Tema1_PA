import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Ridge {
	static class Summit {
		public int height;
		public int cost;
		public Summit(int height, int cost) {
			this.height = height;
			this.cost = cost;
		}
	}
	static class Resolve {
		public static final String INPUT_FILE = "ridge.in";
		public static final String OUTPUT_FILE = "ridge.out";

		public int N;
		public Summit[] summits;
		public double[][] dp;

		public void solve() {
			readInput();
			writeOutput(getResult());
		}
		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(INPUT_FILE));
				N = sc.nextInt();
				summits = new Summit[N];
				for (int i = 0; i < N; i++) {
					int height = sc.nextInt();
					int cost = sc.nextInt();
					summits[i] = new Summit(height, cost);
				}
				dp = new double[N][3];
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		private void writeOutput(double result) {
			try {
				PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
				pw.printf("%f",result);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private double getResult() {
			dp[0][0] = 0;
			dp[0][1] = (double)summits[0].cost;
			dp[0][2] = (double)summits[0].cost * 2;
			for (int i = 1; i < N; i++) {
				for (int j = 0; j < 3; j++) {
					if (summits[i].height - j >= 0) {
						double caz = 0;
						if (summits[i].height - j == summits[i - 1].height) {
							caz = Math.min(dp[i - 1][1], dp[i - 1][2]);
						} else if (summits[i].height - j == summits[i - 1].height - 1) {
							caz = Math.min(dp[i - 1][0], dp[i - 1][2]);
						} else if (summits[i].height - j == summits[i - 1].height - 2) {
							caz = Math.min(dp[i - 1][1], dp[i - 1][0]);
						} else {
							caz = Math.min(dp[i - 1][0], Math.min(dp[i - 1][1],dp[i - 1][2]));
						}
						dp[i][j] = ((double)j * (double)summits[i].cost) + caz;
					} else {
						dp[i][j] = ((double)j * (double)summits[i].cost) 
							+ (double)1000000000 * 1000000 * 100;
					}
				}
			}
			return Math.min(dp[N - 1][0], Math.min(dp[N - 1][1], dp[N - 1][2]));
		}
	}
	public static void main(String[] args) {
		new Resolve().solve();
	}
}
