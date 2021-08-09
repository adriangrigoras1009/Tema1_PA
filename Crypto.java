import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Crypto {
	static class Pc {
		public int no_crypto;
		public int upgrade;

		public Pc(int no_crypto, int upgrade) {
			this.no_crypto = no_crypto;
			this.upgrade = upgrade;
		}
	};
	static class Resolve {
		public static final String INPUT_FILE = "crypto.in";
		public static final String OUTPUT_FILE = "crypto.out";

		int N;
		int B;
		Pc[] PCs;

		public void solve() {
			readInput();
			writeOutput(getResult());
		}

		private void readInput() {
			try {
				BufferedReader sc = new BufferedReader(new FileReader(INPUT_FILE));
				String n_si_b = sc.readLine();
				String[] da = new String[2];
				int i = 0;
				for (String c : n_si_b.split(" ")) {
					da[i] = c;
					i++;
				}
				N = Integer.parseInt(da[0]);
				B = Integer.parseInt(da[1]);
				PCs = new Pc[N];
				int contor = 0;
				while ((n_si_b = sc.readLine()) != null) {     
					int j = 0;
					for (String c : n_si_b.split(" ")) {
						da[j] = c;
						j++;
					}
					int cr = Integer.parseInt(da[0]);
					int upg = Integer.parseInt(da[1]);
					PCs[contor] = new Pc(cr,upg);
					contor++;
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		private void writeOutput(long result) {
			try {
				PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
				pw.printf("%d",result);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
		private long getResult() {
			Arrays.sort(PCs, new Comparator<Pc>() {
				public int compare(Pc a, Pc b) {
					if (a.no_crypto < b.no_crypto) {
						return 1;
					}
					return -1;
				}
			});
			long max = PCs[0].no_crypto;
			double[] suma_max = new double[N];
			long[] suma_upgrade = new long[N];
			suma_max[N - 1] = 0;
			suma_upgrade[N - 1] = PCs[N - 1].upgrade;
			int j = N - 2;
			for (j = N - 2; j >= 0; j--) {
				if (PCs[j].no_crypto != PCs[j + 1].no_crypto) {
					suma_max[j] = suma_max[j + 1] + suma_upgrade[j + 1] 
						* (PCs[j].no_crypto - PCs[j + 1].no_crypto);
				} else {
					suma_max[j] = suma_max[j + 1];
				}
				suma_upgrade[j] = suma_upgrade[j + 1] + PCs[j].upgrade;
				if (suma_max[j] > B) {
					max = PCs[j].no_crypto;             
					break;
				}
			}
			if (j == -1) {
				j = 0;
			}
			if (suma_max[j] > B) {
				max -= (suma_max[j] - B) / suma_upgrade[j + 1];
				return max;
			} else if (suma_max[0] < B) {
				max = PCs[0].no_crypto;
				max += (long)(B - suma_max[0]) / suma_upgrade[0];
			} 
			return max;
		}
	}
	public static void main(String[] args) {
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		new Resolve().solve();
	}
}
