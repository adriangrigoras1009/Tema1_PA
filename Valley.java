import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Valley {
	
	static class Resolve {
		public static final String INPUT_FILE = "valley.in";
		public static final String OUTPUT_FILE = "valley.out";

		public int N;
		public int[] heights;

		public void solve() {
			readInput();
			writeOutput(getResult());
		}

		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(INPUT_FILE));
				N = sc.nextInt();
				heights = new int[N];
				for (int i = 0; i < N; i++) {
					heights[i] = sc.nextInt();
				}
				sc.close();
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
		private long getResult() {
			int min = heights[1];
			int index_min = 1;
			long secunde = 0;
			for (int i = 2; i < N - 1; i++) {
				if (heights[i] < min) {
					min = heights[i];
					index_min = i;
				}
			}
			for (int i = 1; i <= index_min; i++) {
				if (heights[i] > heights[i - 1]) {
					secunde += (long)(heights[i] - heights[i - 1]);
					heights[i] -= (heights[i] - heights[i - 1]);
				}
			}
			for (int i = N - 2; i >= index_min; i--) {
				if (heights[i] > heights[i + 1]) {
					secunde += (long)(heights[i] - heights[i + 1]);
					heights[i] -= (heights[i] - heights[i + 1]);
				}

			}
			return secunde;
		}
	}
	public static void main(String[] args) {
		new Resolve().solve();
	}
}