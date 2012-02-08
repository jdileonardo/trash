import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class main {
	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		ArrayList<String> wikiPages = new ArrayList<String>();

		Languages langSelection = new Languages();
		grabber(wikiPages,langSelection);
		langSelection = new Languages("french");
		grabber(wikiPages,langSelection);
		langSelection = new Languages("italian");
		grabber(wikiPages,langSelection);
		
		while(true)
		{
			System.out.println("Choose a Language(English/Italian/French) or QUIT");
			String selection = keyboard.nextLine().toLowerCase();
			if(selection.toLowerCase().equals("quit"))
			{
				break;
			}
			else if((selection.equals("english") || selection.equals("french") || selection.equals("italian")) == false){
				System.out.println("Not a valid Selection");
				continue;
			}
			setLanguage(wikiPages, selection.toLowerCase());
		}
		}
	
	public static void setLanguage(ArrayList<String> wikiPages, String selection){

			Languages langSelection = new Languages(selection);
			grabber(wikiPages,langSelection);
		
	}
	public static void grabber(ArrayList<String> wikiPages, Languages langSelection){
	for (int i = 0; i < 10; i++) {
		wikiPages.add(langSelection.getPageTitle());
		langSelection.randomPage();
		//wikiPages.add(langSelection.getPageTitle());
		
	}
	Collections.sort(wikiPages);
		for (int j = 0; j < wikiPages.size(); j++) {
			System.out.println(wikiPages.get(j));
		}
		wikiPages.clear();
	}
	
	
}
