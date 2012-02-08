public class main {
	private static int length1;

	public static void Comparison(String seq1, String seq2) {
		length1 = seq1.length();
		String[] stringStore = new String[50];
		
		int count = 0;
		for (int i = 0; i <= (length1 - 3); i++) {
			for (int j = length1; j >= (i + 3); j--) {
				if (seq2.indexOf(seq1.substring(i, j)) != -1) {
					stringStore[count] = seq1.substring(i,j);
					count++;
					i = j-1;
				}
			}

		}
		System.out.print(count);
		for(int i = 0; i<count; i++)
		{
			System.out.print("  " + stringStore[i] + " ");
		}
	}
}
