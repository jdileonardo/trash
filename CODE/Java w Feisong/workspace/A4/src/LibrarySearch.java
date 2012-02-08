import java.util.ArrayList;

import java.util.HashMap;
import java.util.StringTokenizer;
/**
 * The LibrarySearch class organizes book and journal records into a modifiable and viewable
 * database.
 * @author Julian Di Leonardo
 *
 */
public class LibrarySearch{
	/**
	 * Manages the the titles located in the ReferenceDB along with their entry index.
	 */
	public static HashMap<String, ArrayList<Integer>> TitleDB = new HashMap<String, ArrayList<Integer>>();
	public static ArrayList<Reference> ReferenceDB = new ArrayList<Reference>();//An ArrayList that holds both books and journals in type Record

	/**
	 * The main function calls initialization, but also handles the users selection of book or journal.
	 *
	 */
	public static String main(int flag, String call, String title, String year,
			String author, String publisher, String organization) {
		hashMapHandler(ReferenceDB);//Adds the titles and there index into the hashmap

		if(flag == 0){
			
			return(addReference("book",ReferenceDB,call,title,year,author,publisher,organization));
		}
		if(flag == 1){
			return(addReference("journal",ReferenceDB,call,title,year,author,publisher,organization));

		}
		if(flag == 2){
			return(searchReference(ReferenceDB,call,title,year,author,publisher,organization));
		}
		return("");
	}
	/**
	 * Handles the Addition of a book/journal to the ReferenceDB. It checks for valid entries, along with identifying each field's specifics.
	 */
	public static String addReference(String type, ArrayList<Reference> ReferenceDB,String call, String title, String year,
			String author, String publisher, String organization ) {
			Reference newReference = null;
				if (call.equals("") == true){
					return("Call is a required field");
				}
				
				if (title.equals("") == true)
				{
					return("Title is a required field");
				}
				if (year.equals("") == true) {
					return("Year is a Required field");
				}
				if (stringIsInt(year) == false || Integer.parseInt(year) > 9999//Checks if the string entered is an int, and within range of the DB
						|| Integer.parseInt(year) < 1000) {
					return("Please enter a valid Year");
				}


			if(type.equals("book"))
			{
				newReference = new Book(call, author, title, publisher, year);//Creates a book object with the entered fields
				if (checkDuplicate(call, year, newReference,ReferenceDB) == false) {
					return("Entry Already Exists");
				}
				ReferenceDB.add(newReference);//Adds the book to the ReferenceDB arraylist
				hashMapHandler(ReferenceDB);
				return(Book.toString((Book) newReference) + "\nSuccessfully Added Book");
			}
			if(type.equals("journal"))
			{
				newReference = new Journal(call,title, organization, year);//Creates a journal object with the entered fields
				if (checkDuplicate(call, year, newReference,ReferenceDB) == false) {
					return("Entry Already Exists!");
				}
				ReferenceDB.add(newReference);//Adds the journal to the ReferenceDB arraylist
				hashMapHandler(ReferenceDB);
				return(Journal.toString((Journal) newReference) + "\nSuccessfully Added Journal");
			}
		return("");
	}
	
/**
 * Takes in the user's search terms, checks their validity, and scans through the DB comparing the entered strings to the existing DB entries
 */
	public static String searchReference(ArrayList<Reference> ReferenceDB, String call, String title, String year,
			String author, String publisher, String organization ) {
		int flag2 = 0;
		//This is the Start of the Code which handles Year analysis
		int count;
		if(year.equals("-")){
			year = "1000"+"-"+"9999";
			flag2 = 1;
		}
		String[] yr = new String[3];//size of 3 to hold the max number of strings (xxxx-xxxx) where 0&2= xxxx and 1 = " - " 

			String delimiters3 = "- ";
			StringTokenizer _year = new StringTokenizer(year,delimiters3,true);//the "-" is tokenized to be stored in a string
			
			count = _year.countTokens();
			for (int i = 0; i < count; i++) {
				yr[i] = _year.nextToken();
				if (stringIsInt(yr[i]) == false && yr[i].equals("-") == false) {//if the entered string is not an int, and is not a "-"
					return("Please input a numerical value for year");					
				}
				else if(stringIsInt(yr[i]) == true && (Integer.parseInt(yr[i]) > 9999 || Integer.parseInt(yr[i])<1000))//If the value is an int, but out of range
						{
							return("Value of year is out of range(1000-9999)!");
						}
			}

		if (call.equals("") && title.equals("") && flag2 == 1) {//If ALLL search fields are blank, print entire db
			String message = "";
			if(ReferenceDB.size() == 0){
				return("DataBase is Empty");
			}
			for (int i = 0; i < ReferenceDB.size(); i++) {
				Reference tempReference = ReferenceDB.get(i);
				if(tempReference instanceof Book)//If the user is searching book
				{
					message += "Type: Book\n" + Book.toString((Book) tempReference);
				}
				if(tempReference instanceof Journal)//if the user is searching journal
				{
					message += "Type: Journal\n" + Journal.toString((Journal) tempReference);
				}
			
		}
			return(message);
		}
		
		int[] numYearArray = new int[2];//Array which will store the 2 potential year variables
		if(count == 1)//if just one year value was entered
		{
			numYearArray[0] = 0;
			numYearArray[1] = Integer.parseInt(yr[0]);
		}
		else if(count == 0)//if no year was entered
		{
			numYearArray[0] = 1000;
			numYearArray[1] = 9999;
		}
		else if ((yr[0] != null) && (yr[1].equals("-")) && (yr[2] != null)) {//if  a year was entered in the form xxxx-yyyy
			numYearArray[0] = Integer.parseInt(yr[0]);
			numYearArray[1] = Integer.parseInt(yr[2]);
			if(numYearArray[0] == numYearArray[1])//if a year was entered xxxx-yyyy
			{
				numYearArray[0] = 0;
			}
		}
		
		else if ((yr[0].equals("-")) && (yr[1] != null)) {//if year is entered in the form -xxxx
			numYearArray[0] = 1000;
			numYearArray[1] = Integer.parseInt(yr[1]);
		}
		else if ((yr[0] != null) && (yr[1].equals("-"))) {//if year is entered in the form xxxx-
		numYearArray[0] = Integer.parseInt(yr[0]);
		numYearArray[1] = 9999;
		}
		//Year code handling ends here
		
		String delimiters = " ,"; // authors are separated by commas
		String delimiters2 = " !";
		StringTokenizer _title = new StringTokenizer(title, delimiters2);
		String[] titleArray = fillArray(_title);

		int total = 0;//check if something is in each corresponding field, and sum which search terms have been used
			
		if(titleArray.length != 0)//If a title is entered, call the hashmap search for titles
		{
			
			ReferenceDB = hashMapSearch(titleArray,ReferenceDB);
		}
		
		if (call.equals("") == false) {
			total++;
		}
		if (year.equals("") == false) {
			total++;
		}
		int found = 0;//used to determine if a result has been found or not
		String message = "";
		for (int i = 0; i < ReferenceDB.size(); i++) {
			int match = 0;
			Reference tempReference = ReferenceDB.get(i);//Grabs a DB from the arraylist and sets its values to strings using a get method
			String tempCall = tempReference.getCall();
			String tempTitle = tempReference.getTitle();
			String tempYear = tempReference.getYear();
			StringTokenizer _tempTitle = new StringTokenizer(tempTitle,
					delimiters2);
			String[] _titleArray = fillArray(_tempTitle);
			
			
			if (call.equalsIgnoreCase(tempCall)) {
				match++;
			}
			
			int tempYr = Integer.parseInt(tempYear);
			//Case 1: user entered a single year
			if(numYearArray[0] == 0)
			{
				if(numYearArray[1] == tempYr )
				{
					match++;
				}
			}
			//Case 2: user's value range contains of the year value in the DB
			else if(tempYr <= numYearArray[1] && tempYr >= numYearArray[0])
			{
				match++;
				if(year.equals("") == true)//catches the fact that the user could enter nothing, and satisfy the above
				{
					match--;
				}
			}
			
			if (match == total) {//if the total number of matches from the above checks, is equal to the total number of search fields entered
				if(tempReference instanceof Book){
				message += "Type: Book\n" + Book.toString((Book) tempReference);
				}
				if(tempReference instanceof Journal){
				message += "Type: Journal\n" + Journal.toString((Journal) tempReference);
				}
				found = 1;
			}
			
		}
		if(found == 1){
			return(message);
		}
		if(found == 0)
		{
			return("No Matches Found!");
		}
		return(message);
	}
	
	/**
	 * Takes in a StringTokenizer type and tokenizes it into an array
	 * @param temp
	 * @return the array filled with the newly tokenized strings
	 */
			
public static String[] fillArray(StringTokenizer temp) {
		String[] Array = new String[temp.countTokens()];
		int count = temp.countTokens();
		for (int i = 0; i < count; i++) {
			Array[i] = temp.nextToken();

		}
		return (Array);
	}
/**
 * Takes in 2 arrays and cross analyzes them, comparing when they match, and returning the number of matches.
 * @param array
 * @param tempArray
 * @return the amount of matches each array contains
 */
	public static int checkMatches(String[] array, String[] tempArray) {
		int match = 0;
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < tempArray.length; j++) {
				
				if (array[i].equalsIgnoreCase(tempArray[j])) {
					match++;
				}
			}
		}
		return (match);
	}
/**
 * Takes in a type String, and returns true or false whether it's an int or not
 * @param test
 * @return true or false depending if the string can be safely converted to an int
 */
	public static boolean stringIsInt(String test) {
		try {
			Integer.parseInt(test);
			return true;
		} catch (NumberFormatException notInt) {
			return false;
		}
	}
	
	/**
	 * Checks to see if there is atleast 1 entry in the entire ReferenceDB before it can search.
	 * @param type
	 * @param ReferenceDB
	 * @return true or false depending if the ReferenceDB contains the specified search type
	 */
public static boolean checkExistence(String type, ArrayList<Reference> ReferenceDB){
	for (int i = 0; i < ReferenceDB.size(); i++) {
		Reference tempReference = ReferenceDB.get(i);
		if(tempReference instanceof Book  && type.equals("book"))
		{
			return true;
		}
		if(tempReference instanceof Journal  && type.equals("journal"))
		{
			return true;
		}
	}
	return false;
}
/**
 * Compares the call and year values the user is trying to add to those stored in various BookDB objects. Returns if the entry can be added or not
 * @param _call
 * @param _year
 * @return true or false depending if the entry exists or not
 */
	public static boolean checkDuplicate(String _call, String _year, Reference newReference, ArrayList<Reference> ReferenceDB) {
		for (int i = 0; i < ReferenceDB.size(); i++) {
			Reference tempReference = ReferenceDB.get(i);
			if(newReference.getClass() == tempReference.getClass())//a check to make sure either book OR journal is being looked at
			{
				String tempCall = tempReference.getCall();
				String tempYear = tempReference.getYear();
				if (tempCall.equalsIgnoreCase(_call)//compares call and year being added with current in DB
					&& tempYear.equalsIgnoreCase(_year) && (newReference != tempReference)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Fills the hashmap using the titles located in the ReferenceDB
	 * @param ReferenceDB
	 */
	public static void hashMapHandler(ArrayList<Reference> ReferenceDB)
	{
		String delimiters2 = " !";
		ArrayList<String> titles = new ArrayList<String>();
		for (int i = 0; i < ReferenceDB.size(); i++) 
		{
			Reference tempReference = ReferenceDB.get(i);
			String title = tempReference.getTitle();
			StringTokenizer _title = new StringTokenizer(title, delimiters2);
			String[] titleArray = fillArray(_title);
			
			for(int j = 0; j<titleArray.length ; j++)
			{
				if (titles.contains(titleArray[j]) == true)// If the arraylist of titles contains the current keyword
				{
					continue;
				}
				else if(titles.contains(titleArray[j]) == false)//If it doesnt, add it to the titles arraylist
				{
					titles.add(titleArray[j]);
				}
			}
		}
		
		for(int i = 0; i < titles.size(); i++)
		{
			ArrayList<Integer> values = new ArrayList<Integer>();
			for(int j = 0; j < ReferenceDB.size(); j++)
			{
				Reference tempReference = ReferenceDB.get(j);
				String title = tempReference.getTitle();
				StringTokenizer _title = new StringTokenizer(title, delimiters2);
				String[] titleArray = fillArray(_title);
								
				for(int k = 0; k<titleArray.length; k++)
				{
					if(titles.get(i).equals(titleArray[k]))// if the titles array has a current keyword
					{
						values.add(j);//add its index in the ReferenceDB to the Integer Arraylist(Values)
					}
				}
			}
			TitleDB.put(titles.get(i).toLowerCase(),values);//Add the entry into the TitleDB hashmap
		}

		
	}
	/**
	 * Searches through the hashmap using the search title and comapring it to entires in the hashmap of titles, returning a simplified ReferenceDB
	 * @param titleArray list of search keywords
	 * @param ReferenceDB DB that will be simplified
	 * @return simplified DB, containing those entries with matching titles
	 */
	public static ArrayList<Reference> hashMapSearch(String[] titleArray, ArrayList<Reference> ReferenceDB){
		ArrayList<Integer> masterValues = null;
		
		for(int i = 0; i < titleArray.length; i++)
		{
			if(TitleDB.containsKey(titleArray[i].toLowerCase()))//If the Hashmap contains the title being searched
			{
				ArrayList<Integer> temp = TitleDB.get(titleArray[i].toLowerCase());//Store the corresponding index values in temp
				ArrayList<Integer> common = new ArrayList<Integer>();//create a common arraylist that will hold all repeated values
				if(masterValues == null){//in case there is only one entry search
					masterValues = temp;
					continue;
				}
				else
				{
					for(int j = 0; j< masterValues.size(); j++)// runs through the current masterValues
					{
						if(temp.contains(masterValues.get(j)))//if the current temp contains any index's in the master
						{
							common.add(masterValues.get(j));//add those which are similar to common
						}
					}
					masterValues = common;//once the for loop is done, set masterValues to the commonly examined Arraylist integer index values
				}
			}
		}
		ArrayList<Reference> commonReferenceDB = new ArrayList<Reference>();

		if(masterValues != null)
		{
			for(int i = 0; i<masterValues.size(); i++)
			{
				commonReferenceDB.add(ReferenceDB.get((masterValues.get(i))));//Go to the referencedb, and get those index's that have the matching titles, and put them in 
			}
		}
		return commonReferenceDB;//Return the simplified referenceDB with titles corresponding to those index's in masterValues

	}
		
}
