import java.util.ArrayList;
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
	 * BookDB stores the records a user has added in the form of a Book class
	 */
	public static ArrayList<Book> BookDB = new ArrayList<Book>();
	/**
	 * JournalDB stores the records a user has added in the form of a Journal class
	 */
	public static ArrayList<Journal> JournalDB = new ArrayList<Journal>();
	/**
	 * The main function calls initialization, but also handles the users selection of book or journal.
	 * @param args
	 */
	public static void main(String args[]) {

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
						addBook();
						break;
					}
					if (choice.equals("j") || choice.equals("journal")) {
						addJournal();
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
						if(BookDB.size() == 0)//Prevents the user from searching an empty DB
						{
							System.out.println("Book Database is Empty!");
							break;
						}
						searchBook();
						break;
					}
					if (choice.equals("j") || choice.equals("journal")) {
						if(JournalDB.size() == 0)//Prevents the user from searching an empty DB
						{
							System.out.println("Journal Database is Empty!");
							break;
						}
						searchJournal();
						break;
					} else {
						System.out
								.println("**Please select a valid option**\n");
						continue;
					}
				}
				continue;

			}
			if (selection.equals("q") || selection.equals("quit")) {
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
			System.out.println(" - QUIT");

			String selection = keyboard.nextLine().toLowerCase();// put in lower
																	// case for
																	// comparison
																	// with
																	// keywords
			return (selection);// used to determine which action the user wants.
		}
	}
	/**
	 * Handles the Addition of a book to the BookDB. It checks for valid entries, along with identifying each field's specifics.
	 */
	public static void addBook() {
		int flag = 0;//represents if a book has successfully been added or not
		while (flag == 0) {
			Scanner keyboard = new Scanner(System.in);
			String call, author, title, publisher, year;
			while (true) {
				System.out.println("Enter Call #:");
				call = keyboard.nextLine();
				if (call.equals("") == false)
					break;
				System.out.println("This is a required field");
			}

			System.out.println("Enter Author(s)[Seperated by commas]:");
			author = keyboard.nextLine();

			while (true) {
				System.out.println("Enter Title:");
				title = keyboard.nextLine();
				if (title.equals("") == false)
					break;
				System.out.println("This is a required field");
			}

			System.out.println("Enter Publisher:");
			publisher = keyboard.nextLine();
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

			if (checkDuplicate(call, year) == false) {
				System.out.println("Entry Already Exists!");
				continue;
			}

			Book newBook = new Book(call, author, title, publisher, year);//Creates a book object with the entered fields
			BookDB.add(newBook);//Adds the book to the bookDB arraylist
			System.out.println(Book.toString(newBook));
			flag = 1;
		}
	}
	/**
	 * Handles the Addition of a journal to the JournalDB. It checks for valid entries, along with identifying each field's specifics.
	 */
	public static void addJournal() {
		int flag = 0;//represents if a journal has been added or not.
		while (flag == 0) {
			Scanner keyboard = new Scanner(System.in);
			String call,title, organization, year;
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

			System.out.println("Enter Organization:");
			organization = keyboard.nextLine();
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

			if (checkDuplicate2(call, year) == false) {
				System.out.println("Entry Already Exists!");
				continue;
			}

			Journal newJournal = new Journal(call,title, organization, year);//Creates a journal object with the entered fields
			JournalDB.add(newJournal);//add the journal object to the journalDB arraylist
			System.out.println(Journal.toString(newJournal));
			flag = 1;
		}
	}
/**
 * Takes in the user's search terms, checks their validity, and scans through the DB comparing the entered strings to the existing DB entries
 */
	public static void searchBook() {
		Scanner keyboard = new Scanner(System.in);
		String call, author, title, publisher, year;
		System.out.println("Enter Call #:");
		call = keyboard.nextLine();
		System.out.println("Enter Author(s)[Seperated by commas]:");
		author = keyboard.nextLine();
		System.out.println("Enter Title:");
		title = keyboard.nextLine();
		System.out.println("Enter Publisher:");
		publisher = keyboard.nextLine();
		
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
							System.out.println("Value of year is out of rang(1000-9999!");
							continue;
						}
			}
			if(flag == 1)continue;
			break;
		}

		if (call.equals("") && author.equals("") && title.equals("")&& publisher.equals("") && year.equals("")) {//If ALLL search fields are blank, print entire db
			for (int i = 0; i < BookDB.size(); i++) {
				Book tempBook = BookDB.get(i);
				System.out.println(Book.toString(tempBook));
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
		StringTokenizer _authors = new StringTokenizer(author, delimiters);
		StringTokenizer _title = new StringTokenizer(title, delimiters2);
		StringTokenizer _publisher = new StringTokenizer(publisher, delimiters2);

		String[] authorArray = fillArray(_authors);//tokenize the strings and place them in there corresponding arrays
		String[] titleArray = fillArray(_title);
		String[] publisherArray = fillArray(_publisher);
		
		int total = 0;//check if something is in each corresponding field, and sum which search terms have been used
		if(authorArray.length != 0)
		{
			total++;
		}
		if(titleArray.length != 0)
		{
			total++;
		}
		if(publisherArray.length !=0 )
		{
			total++;
		}
		if (call.equals("") == false) {
			total++;
		}
		if (year.equals("") == false) {
			total++;
		}
		int found = 0;//used to determine if a result has been found or not
		for (int i = 0; i < BookDB.size(); i++) {
			
			int match = 0;
			Book tempBook = BookDB.get(i);//Grabs a DB from the arraylist and sets its values to strings using a get method
			String tempCall = tempBook.getCall();
			String tempAuthor = tempBook.getAuthor();
			String tempTitle = tempBook.getTitle();
			String tempPublisher = tempBook.getPublisher();
			String tempYear = tempBook.getYear();

			StringTokenizer _tempAuthors = new StringTokenizer(tempAuthor,//Tokenize the authors,title,and publisher strings from the DB entry
					delimiters);
			StringTokenizer _tempTitle = new StringTokenizer(tempTitle,
					delimiters2);
			StringTokenizer _tempPublisher = new StringTokenizer(tempPublisher,
					delimiters2);
			int tempYr = Integer.parseInt(tempYear);

			String[] _authorArray = fillArray(_tempAuthors);//Fill the corresponding arrays with the new tokens
			String[] _titleArray = fillArray(_tempTitle);
			String[] _publisherArray = fillArray(_tempPublisher);
			
			if (call.equalsIgnoreCase(tempCall)) {
				match++;

			}
			//If the number of matches is equal to the length of the array which holds the user entered fields
			if ((checkMatches(authorArray, _authorArray) >= authorArray.length) && authorArray.length != 0) {
				match++;

			}
			if ((checkMatches(titleArray, _titleArray) >= titleArray.length) && titleArray.length != 0) {
				match++;
			

			}
			if ((checkMatches(publisherArray, _publisherArray) >= publisherArray.length) && publisherArray.length != 0) {
				match++;
				

			}
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
				System.out.println(Book.toString(tempBook));
				found = 1;
			}
			
		}
		if(found == 0)
		{
			System.out.println("No Matches Found!");
		}

	}
	/**
	 * Takes in the user's search terms, checks their validity, and scans through the DB comparing the entered strings to the existing DB entries
	 */
	
	//The methods and logic maintained in this method, are the same as that of searchBook(). The only differences are that of the Arraylist(journalDB)
	//and the entered parameters. Please refer to searchBook() for method explanation.
	public static void searchJournal() {
		Scanner keyboard = new Scanner(System.in);
		String call,title, organization, year;
		System.out.println("Enter Call #:");
		call = keyboard.nextLine();
		System.out.println("Enter Title:");
		title = keyboard.nextLine();
		System.out.println("Enter Organization:");
		organization = keyboard.nextLine();
		
		//**********************YEAR START
		int count;
		String[] yr = new String[3];
		while (true) {
			int flag = 0;
			System.out.println("Enter Year:");
			year = keyboard.nextLine();
			String delimiters3 = "- ";
			StringTokenizer _year = new StringTokenizer(year,delimiters3,true);
			
			count = _year.countTokens();
			for (int i = 0; i < count; i++) {
				yr[i] = _year.nextToken();
				if (stringIsInt(yr[i]) == false && yr[i].equals("-") == false) {
					System.out.println("Please input a numerical value for year");
					flag = 1;
					
				}
				else if(stringIsInt(yr[i]) == true && (Integer.parseInt(yr[i]) > 9999 || Integer.parseInt(yr[i])<1000))
						{
						System.out.println("Value of year is out of rang(1000-9999!");
							continue;
						}
			}
			if(flag == 1)continue;
			break;
		}

		if (call.equals("") && title.equals("")&& organization.equals("") && year.equals("")) {
			for (int i = 0; i < JournalDB.size(); i++) {
				Journal tempJournal = JournalDB.get(i);
				System.out.println(Journal.toString(tempJournal));
			}
			return;
		}
		
		int[] numYearArray = new int[2];
		if(count == 1)
		{
			numYearArray[0] = 0;
			numYearArray[1] = Integer.parseInt(yr[0]);
		}
		else if(count == 0)
		{
			numYearArray[0] = 1000;
			numYearArray[1] = 9999;
		}
		else if ((yr[0] != null) && (yr[1].equals("-")) && (yr[2] != null)) {
			numYearArray[0] = Integer.parseInt(yr[0]);
			numYearArray[1] = Integer.parseInt(yr[2]);
			if(numYearArray[0] == numYearArray[1])
			{
				numYearArray[0] = 0;
			}
		}
		
		else if ((yr[0].equals("-")) && (yr[1] != null)) {
			numYearArray[0] = 1000;
			numYearArray[1] = Integer.parseInt(yr[1]);
		}
		else if ((yr[0] != null) && (yr[1].equals("-"))) {
		numYearArray[0] = Integer.parseInt(yr[0]);
		numYearArray[1] = 9999;
		}
		 

		

		//**********************YEAR END
		
		String delimiters2 = " !";
		StringTokenizer _title = new StringTokenizer(title, delimiters2);
		StringTokenizer _organization = new StringTokenizer(organization, delimiters2);


		String[] titleArray = fillArray(_title);
		String[] organizationArray = fillArray(_organization);
		
		int total = 0;

		if(titleArray.length != 0)
		{
			total++;
		}
		if(organizationArray.length !=0 )
		{
			total++;
		}
		if (call.equals("") == false) {
			total++;
		}
		if (year.equals("") == false) {
			total++;
		}
		int found = 0;
		for (int i = 0; i < JournalDB.size(); i++) {
			
			int match = 0;
			Journal tempJournal = JournalDB.get(i);
			String tempCall = tempJournal.getCall();
			String tempTitle = tempJournal.getTitle();
			String tempOrganization = tempJournal.getOrganization();
			String tempYear = tempJournal.getYear();

			StringTokenizer _tempTitle = new StringTokenizer(tempTitle,
					delimiters2);
			StringTokenizer _tempOrganization = new StringTokenizer(tempOrganization,
					delimiters2);
			int tempYr = Integer.parseInt(tempYear);

			String[] _titleArray = fillArray(_tempTitle);
			String[] _organizationArray = fillArray(_tempOrganization);
			
			if (call.equalsIgnoreCase(tempCall)) {
				match++;

			}
			

			if ((checkMatches(titleArray, _titleArray) >= titleArray.length) && titleArray.length != 0) {
				match++;
			

			}
			if ((checkMatches(organizationArray, _organizationArray) >= organizationArray.length) && organizationArray.length != 0) {
				match++;
				

			}
			
			if(numYearArray[0] == 0)
			{
				if(numYearArray[1] == tempYr )
				{
					match++;


				}
			}
			else if(tempYr <= numYearArray[1] && tempYr >= numYearArray[0])
			{
				match++;
				if(year.equals("") == true)
				{
					match--;
				}
				

			}

			
			if (match == total) {
				System.out.println(Journal.toString(tempJournal));
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
	 * Compares the call and year values the user is trying to add to those stored in various BookDB objects. Returns if the entry can be added or not
	 * @param _call
	 * @param _year
	 * @return true or false depending if the entry exists or not
	 */

	// run through the bookarraylist and compare both call and year temps to real
	// callyear using get(in bookfile)

	public static boolean checkDuplicate(String _call, String _year) {
		for (int i = 0; i < BookDB.size(); i++) {
			Book tempBook = BookDB.get(i);
			String tempCall = tempBook.getCall();
			String tempYear = tempBook.getYear();
			if (tempCall.equalsIgnoreCase(_call)
					&& tempYear.equalsIgnoreCase(_year)) {
				return false;
			}
		}
		return true;
	}
	/**
	 *Compares the call and year values the user is trying to add to those stored in various JournalDB objects. Returns if the entry can be added or not
	 * @param _call
	 * @param _year
	 * @return true or false depending if the entry exist or not
	 */
	public static boolean checkDuplicate2(String _call, String _year) {
		for (int i = 0; i < JournalDB.size(); i++) {
			Journal tempJournal = JournalDB.get(i);
			String tempCall = tempJournal.getCall();
			String tempYear = tempJournal.getYear();
			if (tempCall.equalsIgnoreCase(_call)
					&& tempYear.equalsIgnoreCase(_year)) {
				return false;
			}
		}
		return true;
	}
}
