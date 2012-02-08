// WikiRand.java
//
// This Java class downloads random pages from Wikipedia and allows one to retrieve the page title
//
// by William Darling, 2010.

import java.io.*;
import java.net.*;

public class WikiRand {

	private String title;
	private String text;
	
	public WikiRand() {
		
		// get a random page
		this.randomPage();
		
	}
	
	public void randomPage() {
		
		URL u;
		InputStream is = null;
		BufferedReader dis;
		String s;
		
		// reset text
		text = "";
		
		try {
			
			u = new URL("http://en.wikipedia.org/wiki/Special:Random");
			
			System.out.println("Downloading page...");
			
			is = u.openStream();
			
			dis = new BufferedReader(new InputStreamReader(is));
			
			while((s=dis.readLine()) != null) {
				text += s;
			}
		}
		catch(MalformedURLException mue) {
			
			System.out.println("Internet problem...");
		}
		catch(IOException ioe) {
			
			System.out.println("Internet problem...");
		}
		finally {
			
			try {
				is.close();
			}
			catch (IOException ioe) {
				// meh
			}
		}
	}
	
	public String getTitle() {
		
		String[] words = text.split("<");
		
		for(int i=0; i<words.length; i++) {
			
			if(words[i].length()>=5) {
				if(words[i].substring(0,5).equalsIgnoreCase("title")) {
					return words[i].substring(6,words[i].length()-35);
					
				}
			}
		}
		
		return null;
		
	}
}
