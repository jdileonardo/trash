import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.PrintWriter; 
import java.io.FileOutputStream; 
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;
/**
 * The LibrarySearch class organizes book and journal records into a modifiable and viewable
 * database.
 * @author Julian Di Leonardo
 *
 */
public class LibrarySearch {
	/**
	 * Manages the the titles located in the ReferenceDB along with their entry index.
	 */
	public static HashMap<String, ArrayList<Integer>> TitleDB = new HashMap<String, ArrayList<Integer>>();
	
	/**
	 * The main function calls initialization, but also handles the users selection of book or journal.
	 * @param args
	 */
	public static void main(String args[]) {
		ArrayList<Reference> ReferenceDB = new ArrayList<Reference>();//An ArrayList that holds both books and journals in type Record
		String inputFile,outputFile;
		try{//If the user does not specify a input and output title name, use the default ones
		inputFile = args[0];
		outputFile = args[1];
		}
		catch(ArrayIndexOutOfBoundsException e){
			inputFile = "out.txt";
			outputFile = "out.txt";
		}
		//Uncomment to add test cases
		//ReferenceDB.add(new Book("Q12", "Fei Song","Netbeans 101","Song Inc","9123"));
		//ReferenceDB.add(new Book("Q13", "Julian D","Do the Java","JD Corp","4444"));
		//ReferenceDB.add(new Book("Q14", "Tao Xu","Mark Java","Xu Inc","7777"));
		//ReferenceDB.add(new Journal("Q21","Java for Dummies", "Arthur Corp", "4500"));
		//ReferenceDB.add(new Journal("Q55.66","Learn the Java", "Bert Corp","5000"));
		//ReferenceDB.add(new Journal("Q66.44", "Hungry Hippo", "Charlie Corp", "4000"));
		
		
		fileToArray(inputFile,ReferenceDB);//Takes entries in .txt file and fills the Reference ArrayList
		hashMapHandler(ReferenceDB);//Adds the titles and there index into the hashmap
		while (true) {
			String selection = initialization();

			if (selection.equals("a") || selection.equals("add")) {
				while (true) {
					Scanner keyboard = new Scanner(System.in);
					System.out.println("\nChoose what to ADD");
					System.out.println(" - add a BOOK");
					System.out.println(" - add a JOURNAL");

					String choice = keyboard.nextLine().toLowerCase();

					if (choice.equals("b") || choice.equals("book")) {
						addReference("book",ReferenceDB);
						break;
					}
					if (choice.equals("j") || choice.equals("journal")) {
						addReference("journal",ReferenceDB);
						break;
					} else {
						System.out
								.println("**Please select a valid option**\n");
						continue;
					}
				}
				continue;
			}
			if (selection.equals("s") || selection.equals("search")) {
				while (true) {
					Scanner keyboard = new Scanner(System.in);
					System.out.println("\nChoose what to SEARCH");
					System.out.println(" - search a BOOK");
					System.out.println(" - search a JOURNAL");

					String choice = keyboard.nextLine().toLowerCase();

					if (choice.equals("b") || choice.equals("book")) {
						if(checkExistence("book",ReferenceDB) == false)//Prevents the user from searching an empty DB
						{
							System.out.println("Book Database is Empty!");
							break;
						}
						searchReference("book",ReferenceDB);
						break;
					}
					if (choice.equals("j") || choice.equals("journal")) {
						if(checkExistence("journal",ReferenceDB) == false)//Prevents the user from searching an empty DB
						{
							System.out.println("Journal Database is Empty!");
							break;
						}
						searchReference("journal",ReferenceDB);
						break;
					} else {
						System.out
								.println("**Please select a valid option**\n");
						continue;
					}
				}
				continue;

			}
			if (selection.equals("q") || selection.equals("quit")){
				arrayToFile(outputFile,ReferenceDB);
				System.out.println("Database written to " + outputFile);
				System.out.println("Good-Bye!");
				return;
			} else {
				System.out.println("**Please select a valid option**\n");
				continue;
			}
		}
	}
	/**
	 * Starts off and accepts the users initial choice to Add Search or Quit.
	 * @return
	 */
	private static String initialization() {
		{
			Scanner keyboard = new Scanner(System.in);
			System.out.println("\nChoose one of the following options:");
			System.out.println(" - ADD Book or Journal Record");
			System.out.println(" - SEARCH Record");
			System.out.println(" - QUIT to write all Records");

			String selection = keyboard.nextLine().toLowerCase();// put in lower
																	// case for
																	// comparison
																	// with
																	// keywords
			return (selection);// used to determine which action the user wants.
		}
	}
	/**
	 * Handles the Addition of a book/journal to the ReferenceDB. It checks for valid entries, along with identifying each field's specifics.
	 */
	public static void addReference(String type, ArrayList<Reference> ReferenceDB) {
		int flag = 0;//represents if a book has successfully been added or not
		while (flag == 0) {
			Scanner keyboard = new Scanner(System.in);
			String call, author = "", title, publisher = "", year, organization = "";
			Reference newReference = null;
			while (true) {
				System.out.println("Enter Call #:");
				call = keyboard.nextLine();
				if (call.equals("") == false)
					break;
				System.out.println("This is a required field");
			}
			
			while (true) {
				System.out.println("Enter Title:");
				title = keyboard.nextLine();
				if (title.equals("") == false)
					break;
				System.out.println("This is a required field");
			}
			
			if(type.equals("book")){
				System.out.println("Enter Author(s)[Seperated by commas]:");
				author = keyboard.nextLine();

				System.out.println("Enter Publisher:");
				publisher = keyboard.nextLine();
				
			}
			
			if(type.equals("journal"))
			{
				System.out.println("Enter Organization:");
				organization = keyboard.nextLine();
			}
			while (true) {
				System.out.println("Enter Year:");
				year = keyboard.nextLine();
				if (year.equals("") == true) {
					System.out.println("This is a required field");
					continue;
				}
				if (stringIsInt(year) == false || Integer.parseInt(year) > 9999//Checks if the string entered is an int, and within range of the DB
						|| Integer.parseInt(year) < 1000) {
					System.out.println("Please enter a valid year");
					continue;
				}
				break;
			}
			if(type.equals("book"))
			{
				newReference = new Book(call, author, title, publisher, year);//Creates a book object with the entered fields
				if (checkDuplicate(call, year, newReference,ReferenceDB) == false) {
					System.out.println("Entry Already Exists!");
					continue;
				}
				ReferenceDB.add(newReference);//Adds the book to the ReferenceDB arraylist
				System.out.println(Book.toString((Book) newReference));
			}
			if(type.equals("journal"))
			{
				newReference = new Journal(call,title, organization, year);//Creates a journal object with the entered fields
				if (checkDuplicate(call, year, newReference,ReferenceDB) == false) {
					System.out.println("Entry Already Exists!");
					continue;
				}
				ReferenceDB.add(newReference);//Adds the journal to the ReferenceDB arraylist
				System.out.println(Journal.toString((Journal) newReference));
			}
			flag = 1;
		}
		hashMapHandler(ReferenceDB);//Reruns the hashmap for the title in the newly entered entry.
	}
	
/**
 * Takes in the user's search terms, checks their validity, and scans through the DB comparing the entered strings to the existing DB entries
 */
	public static void searchReference(String type, ArrayList<Reference> ReferenceDB) {
		
		String[] authorArray = null;
		String[] publisherArray = null;
		String[] organizationArray = null;
		
		Scanner keyboard = new Scanner(System.in);
		String call, author = "", title, publisher = "", year, organization = "";
		System.out.println("Enter Call #:");
		call = keyboard.nextLine();
		System.out.println("Enter Title:");
		title = keyboard.nextLine();
		
		if(type.equals("book"))
		{
			System.out.println("Enter Author(s)[Seperated by commas]:");
			author = keyboard.nextLine();
			System.out.println("Enter Publisher:");
			publisher = keyboard.nextLine();
		}
		
		if(type.equals("journal"))
		{
			System.out.println("Enter Organization:");
			organization = keyboard.nextLine();
		}
		
		//This is the Start of the Code which handles Year analysis
		int count;
		String[] yr = new String[3];//size of 3 to hold the max number of strings (xxxx-xxxx) where 0&2= xxxx and 1 = " - " 
		while (true) {
			int flag = 0;//flag used for entry validity
			System.out.println("Enter Year:");
			year = keyboard.nextLine();
			String delimiters3 = "- ";
			StringTokenizer _year = new StringTokenizer(year,delimiters3,true);//the "-" is tokenized to be stored in a string
			
			count = _year.countTokens();
			for (int i = 0; i < count; i++) {
				yr[i] = _year.nextToken();
				if (stringIsInt(yr[i]) == false && yr[i].equals("-") == false) {//if the entered string is not an int, and is not a "-"
					System.out.println("Please input a numerical value for year");
					flag = 1;
					
				}
				else if(stringIsInt(yr[i]) == true && (Integer.parseInt(yr[i]) > 9999 || Integer.parseInt(yr[i])<1000))//If the value is an int, but out of range
						{
							System.out.println("Value of year is out of range(1000-9999!");
							continue;
						}
			}
			if(flag == 1)continue;
			break;
		}

		if (call.equals("") && author.equals("") && title.equals("")&& publisher.equals("") && year.equals("") && organization.equals("")) {//If ALLL search fields are blank, print entire db
			for (int i = 0; i < ReferenceDB.size(); i++) {
				Reference tempReference = ReferenceDB.get(i);
				if(tempReference instanceof Book  && type.equals("book"))//If the user is searching book
				{
					System.out.println(Book.toString((Book) tempReference));
				}
				if(tempReference instanceof Journal  && type.equals("journal"))//if the user is searching journal
				{
					System.out.println(Journal.toString((Journal) tempReference));
				}
			
		}
			return;
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
	
		if(type.equals("book"))//Book specific parameters
		{
			StringTokenizer _authors = new StringTokenizer(author, delimiters);
			StringTokenizer _publisher = new StringTokenizer(publisher, delimiters2);
			authorArray = fillArray(_authors);//tokenize the strings and place them in there corresponding arrays
			publisherArray = fillArray(_publisher);
			if(authorArray.length != 0)
			{
				total++;
			}
			if(publisherArray.length !=0 )
			{
				total++;
			}
		}
		if(type.equals("journal"))//journal specific parameters
		{
			StringTokenizer _organization = new StringTokenizer(organization, delimiters2);
			organizationArray = fillArray(_organization);
			if(organizationArray.length !=0 )
			{
				total++;
			}
		}		
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
		for (int i = 0; i < ReferenceDB.size(); i++) {
			
			int match = 0;
			Reference tempReference = ReferenceDB.get(i);//Grabs a DB from the arraylist and sets its values to strings using a get method
			String tempCall = tempReference.getCall();
			String tempTitle = tempReference.getTitle();
			String tempYear = tempReference.getYear();
			StringTokenizer _tempTitle = new StringTokenizer(tempTitle,
					delimiters2);
			String[] _titleArray = fillArray(_tempTitle);
			
			if(tempReference instanceof Book  && type.equals("book"))
			{
				String tempAuthor = ((Book) tempReference).getAuthor();
				String tempPublisher = ((Book)tempReference).getPublisher();
				StringTokenizer _tempAuthors = new StringTokenizer(tempAuthor,//Tokenize the authors,title,and publisher strings from the DB entry
						delimiters);
				StringTokenizer _tempPublisher = new StringTokenizer(tempPublisher,
						delimiters2);
				String[] _authorArray = fillArray(_tempAuthors);//Fill the corresponding arrays with the new tokens
				String[] _publisherArray = fillArray(_tempPublisher);
				if ((checkMatches(authorArray, _authorArray) >= authorArray.length) && authorArray.length != 0) {
					match++;
				}
				if ((checkMatches(publisherArray, _publisherArray) >= publisherArray.length) && publisherArray.length != 0) {
					match++;
				}
				
			}
			if(tempReference instanceof Journal  && type.equals("journal"))
			{
				String tempOrganization = ((Journal) tempReference).getOrganization();
				StringTokenizer _tempOrganization = new StringTokenizer(tempOrganization,
						delimiters2);
				
				String[] _organizationArray = fillArray(_tempOrganization);
				if ((checkMatches(organizationArray, _organizationArray) >= organizationArray.length) && organizationArray.length != 0) {
					match++;
				}
				
			}
			
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
				if(tempReference instanceof Book  && type.equals("book")){
				System.out.println(Book.toString((Book) tempReference));
				}
				if(tempReference instanceof Journal  && type.equals("journal")){
				System.out.println(Journal.toString((Journal) tempReference));
				}
				found = 1;
			}
			
		}
		if(found == 0)
		{
			System.out.println("No Matches Found!");
		}

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
	/**Takes all the book and journal entries from a text file and fills the Database with them
	 * 
	 * @param inputFile contains the file name to load the database from
	 * @param ReferenceDB location to place all the db intos
	 */
	public static void fileToArray(String inputFile, ArrayList<Reference> ReferenceDB){
		Scanner inputStream = null;
		try
		{
			inputStream = new Scanner(new FileInputStream(inputFile));
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Notice: No previous record's found");
			return;
		}
		while(inputStream.hasNextLine() == true){
			String type = ((inputStream.nextLine()).substring(7));
			if(type.equals("book")){
				String call = (inputStream.nextLine()).substring(8);
				String author = (inputStream.nextLine()).substring(11);
				String title = (inputStream.nextLine()).substring(7);
				String publisher = (inputStream.nextLine()).substring(11);
				String year = (inputStream.nextLine()).substring(6);
				ReferenceDB.add(new Book(call, author,title,publisher,year));
			}
			if(type.equals("journal"))
			{
				String call = (inputStream.nextLine()).substring(8);
				String title = (inputStream.nextLine()).substring(7);
				String organization = (inputStream.nextLine()).substring(14);
				String year = (inputStream.nextLine()).substring(6);
				ReferenceDB.add(new Journal(call,title,organization,year));
			}
			inputStream.nextLine();
		}
		inputStream.close();
	}
	/**
	 * Transfers all the records from the ReferenceDB to the output file
	 * @param outputFile contains the location of the .txt file to print all the records to
	 * @param ReferenceDB location of the records to be put into the .txt file
	 */
	public static void arrayToFile(String outputFile, ArrayList<Reference> ReferenceDB)
	{
		PrintWriter outputStream = null;
		try
		{
			outputStream = new PrintWriter(new FileOutputStream(outputFile));
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File not found or could not be opened");
			System.exit(0);
		}
		for (int i = 0; i < ReferenceDB.size(); i++) {
			Reference tempReference = ReferenceDB.get(i);
			if(tempReference instanceof Book)
			{
				outputStream.println("type : book"); 
				outputStream.println(Book.toString((Book) tempReference));
			}
			if(tempReference instanceof Journal)
			{
				outputStream.println("type : journal"); 
				outputStream.println(Journal.toString((Journal) tempReference));
			}
		}
		 outputStream.close();
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
