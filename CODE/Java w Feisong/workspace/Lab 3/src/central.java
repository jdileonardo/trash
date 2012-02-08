import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class central {

	public static void main(String[] args) {
		ArrayList<String> wikiPages = new ArrayList<String>();
		long seed = Long.parseLong(args[0]);
		Random num = new Random(seed);
		
		int check = 0;

		while (check == 0) {
			int value = num.nextInt(11) + 5;
			WikiRand Rand = new WikiRand();
			for (int i = 0; i < value; i++) {

				wikiPages.add(Rand.getTitle());
				Rand.randomPage();
			}

			Collections.sort(wikiPages);

			for (int i = 0; i < wikiPages.size(); i++) {
				System.out.println(wikiPages.get(i));
			}
	
			if(Math.random() < 0.5)
			{
				check = 1;
			}
			wikiPages.clear();
		}
	}

}
