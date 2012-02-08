import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * The LibrarySearch class organizes book records into a modifiable and viewable
 * database.
 * 
 * @author Julian Di Leonardo
 * @version v1.0 ...
 */

public class LibrarySearch {
	/**
	 * The Database array is a 2D array where the records entered by the user
	 * are stored. Each index of the array stores 1 of 3 parts of the record
	 * entered by the user
	 */
	public static String[][] database = new String[3][3];

	/**
	 * Handles which actions the user wants to perform.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		int done = 0;
		int notEmpty = 0;
		while (done == 0) {
			String selection = initialization();

			if (selection.startsWith("a")) {
				int flag;
				flag = check();
				if (flag == 1) {
					add();
					notEmpty = 1;// allows for search to happen
				}
				continue;
			}
			if (selection.startsWith("s")) {
				if (notEmpty == 0 || database[0][0] == null) {
					System.out.println("Database is empty!");
					continue;
				}
				search();
				continue;
			}
			if (selection.startsWith("q")) {
				System.out.println("Good-Bye!");
				done = 1;// User wants to exit
				continue;
			} else {
				System.out.println("**Please select a valid option**\n");
				continue;
			}
		}
	}

	/**
	 * Prompts the user to select which action they want to perform
	 * 
	 */
	public static String initialization() {
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
	 * Verifies the integrity of the database, taking note of where entries are
	 * and are not.
	 * 
	 */
	public static int check() {
		int count = 0;
		int flag = 0;
		for (int i = 0; i < 3; i++) {
			if (database[i][0] == null) {// Checks to see that there is room to
											// add
				flag = 1;
				return (flag);// Enables the ADD method to run
			}
			if (database[i][0] != null)// Used to find out how many entries are
										// in the database
				count = i;

			if (database[i][0] != null && count == (database.length - 1)) {// Dictates
																			// that
																			// the
																			// database
																			// is
																			// full
				System.out.println("Database is Full\n");
				flag = 0;
				return (flag);

			}

		}
		return (flag);
	}

	/**
	 * Stores an entry at the next available location in the database.
	 */
	public static void add() {

		Scanner keyboard = new Scanner(System.in);

		System.out
				.println("Enter the 3 details in the following format:(Seperated by COMMAS)");
		System.out
				.println("Call Number, Title, Publisher(Book) or Organization(Journal)\n");

		String line = keyboard.nextLine();
		String delimiters = ","; // Removes the separators
		StringTokenizer record = new StringTokenizer(line, delimiters);
		int tokens = 0;
		int flag = 0;
		tokens = record.countTokens();
		if (tokens != 3) {
			flag = 1;
			System.out.println("Not Enough or Too many Details");
		}
		while (flag == 0) {// While there are the correct amount of details
			String callNumber = record.nextToken();
			String title = record.nextToken();
			String group = record.nextToken();

			for (int i = 0; i < 3; i++) {
				if (callNumber.equalsIgnoreCase(database[i][0])) {// Compares
																	// first
																	// index
																	// with
																	// first
																	// token to
																	// see if
																	// entry is
																	// already
																	// in
																	// database
					System.out.println("Entry Already Exists");
					break;
				}

				if (database[i][0] == null) {
					database[i][0] = callNumber;
					database[i][1] = title;
					database[i][2] = group;
					System.out.println("Record stored");
					break;

				}

				else {
					continue;
				}

			}
			flag = 1;
		}
	}

	/**
	 * Scans through the database and compares the user entered keywords to the
	 * records within the database. Returns matching entries.
	 */
	public static void search() {
		int[] tally = new int[3];// Used to count how many of the queries match
									// with each record

		Scanner keyboard = new Scanner(System.in);

		System.out
				.println("Enter the search terms in the following format:(Seperated by SPACES)");
		System.out.println("Term 1 Term 2 Term 3 etc..\n");

		String line = keyboard.nextLine();
		System.out.println("Result:");

		String delimiters = " "; // space; keywords are divided by spaces
		StringTokenizer query = new StringTokenizer(line, delimiters);

		int numOfTokens = query.countTokens();
		int count = 0;
		int found = 0;
		for (int i = 0; i < 3; i++) { // Used to find out how many entries are
										// in the database
			if (database[i][0] != null) {
				count = i;
			}
		}

		while (query.hasMoreTokens()) {

			String keyword = query.nextToken();
			for (int i = 0; i < count + 1; i++) {
				for (int j = 0; j < 3; j++) {

					if (database[i][j].toLowerCase().indexOf( // if the database
																// entry
																// contains any
																// of the
																// keyword, add
																// one to its
																// corresponding
																// index in
																// tally
							keyword.toLowerCase()) != -1) {
						tally[i]++;
						// found = 1;
						break;

					}

				}
			}
		}

		for (int i = 0; i < 3; i++) {
			if (tally[i] == numOfTokens) {// if the number of keywords, matches the value of the tally at the corres. index of the record
				if (database[0][0] == null) {//get around for printing after adding nothing and searching nothing
					break;
				}
				found = 1;
				System.out.print(database[i][0] + ", ");
				System.out.print(database[i][1] + ", ");
				System.out.print(database[i][2] + "\n");
			}

		}
		if (found == 0)
			System.out.println("No Matches Found");
	}
}
