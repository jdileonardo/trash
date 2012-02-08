
import com.google.api.translate.Language;
import com.google.api.translate.Translate;

public class Languages extends WikiRand {
public int lang;
	public Languages() {
		lang = 0;
	}

	public Languages(String Language) {
		if(Language.toLowerCase().equals("english"))
		{
			lang = 0;
		}
		else if(Language.toLowerCase().equals("french"))
		{
			lang = 1;
		}
		else if(Language.toLowerCase().equals("italian"))
		{
			lang = 2;

		}
		else{
			lang = 3;
		}
	}
	
	public String getPageTitle(){
		if(lang == 0)
		{
			System.out.println("English Wiki's");
			return(getTitle());
		}
		if(lang == 1)
		{
			System.out.println("French Wiki's");
			String title = getTitle();
			
			// set the referrer so Google knows who is using their service.
			// We'll use the school of computer science webpage
			Translate.setHttpReferrer("http://www.socs.uoguelph.ca");

			// set the language and translate
			// Note that Language is a class defined in the api
			Language l = Language.FRENCH;
			String frenchTitle = null;
			try {
				frenchTitle = Translate.execute(title, Language.ENGLISH, l);
			}
			catch(Exception e) {
				System.out.println("Problem: " + e + "\n");
			}

			return(frenchTitle);
		}
		if(lang == 2)
		{
			System.out.println("Italian Wiki's");
			String title = getTitle();
			
			// set the referrer so Google knows who is using their service.
			// We'll use the school of computer science webpage
			Translate.setHttpReferrer("http://www.socs.uoguelph.ca");

			// set the language and translate
			// Note that Language is a class defined in the api
			Language l = Language.ITALIAN;
			String italianTitle = null;
			try {
				italianTitle = Translate.execute(title, Language.ENGLISH, l);
			}
			catch(Exception e) {
				System.out.println("Problem: " + e + "\n");
			}

			return(italianTitle);
		}
		else
		{
			return("DOE");
		}
	}
	
}
