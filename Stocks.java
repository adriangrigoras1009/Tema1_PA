import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Stocks {
	static class Stock {
		public int currentValue;
		public int minValue;
		public int maxValue;
		public Stock(int currentValue, int minValue, int maxValue) {
			this.currentValue = currentValue;
			this.minValue = minValue;
			this.maxValue = maxValue;
		} 
	};
	static class Schimbari {
		public int index;
		public int sol;
	}
	static class Resolve {
		public static final String INPUT_FILE = "stocks.in";
		public static final String OUTPUT_FILE = "stocks.out";

		int N;
		int B;
		int L;
		Stock[] stock_vector;
		int[][][] dp;

		public void solve() {
			readInput();
			writeOutput(getResult());
		}

		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(INPUT_FILE));
				N = sc.nextInt();
				B = sc.nextInt();
				L = sc.nextInt();
				stock_vector = new Stock[N];
				for (int i = 0; i < N; i++) {
					int cur = sc.nextInt();
					int min = sc.nextInt();
					int max = sc.nextInt();
					stock_vector[i] = new Stock(cur,min,max);
				}
				dp = new int[101][501][101];
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		private void writeOutput(int result) {
			try {
				PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
				pw.printf("%d",result);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
		private int maxN(Stock[] vector, int N, int B, int L) {
			for (int B_cap = 0; B_cap <= B; ++B_cap) {
				for (int L_cap = 0; L_cap <= L; ++ L_cap) {
					dp[0][B_cap][L_cap] = 0;
				}
			}

			for (int i = 1; i <= N; ++i) {
				for (int B_cap = 0; B_cap <= B; ++B_cap) {
					for (int L_cap = 0; L_cap <= L; ++L_cap) {
						dp[i][B_cap][L_cap] = dp[i - 1][B_cap][L_cap];
						if (B_cap - vector[i - 1].currentValue >= 0 
							&& L_cap - (vector[i - 1].currentValue - vector[i - 1].minValue) >= 0) {
							int sol_aux = dp[i - 1][B_cap - vector[i - 1].currentValue][L_cap 
								- (vector[i - 1].currentValue - vector[i - 1].minValue)] 
									+ (vector[i - 1].maxValue - vector[i - 1].currentValue);
							dp[i][B_cap][L_cap] = Math.max(dp[i][B_cap][L_cap], sol_aux);
						}
					}
				}
			} 
			return dp[N][B][L];
		}
		private int getResult() {
			return maxN(stock_vector, N, B, L);
		}
	}
	public static void main(String[] args) {
		new Resolve().solve();
	}
}