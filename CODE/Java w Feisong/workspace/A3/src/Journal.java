/**
 * Manages Journal entries for the JournalDB
 * @author juliandileonardo
 *
 */
public class Journal extends Reference {

	private String organization;

/**
 * Constructs a Journal entry for its addition to the JournalDB arraylist
 * @param _call
 * @param _title
 * @param _organization
 * @param _year
 */
	public Journal(String _call,String _title,String _organization,String _year)
	{
		super(_call,_title,_year);
		this.call = _call;
		this.title = _title;
		this.organization = _organization;
		this.year = _year;
	}
	
	/**
	 * Simply returns the Organization string from the entry in the BookDB
	 * @return the organization string
	 */
	public String getOrganization(){
		return this.organization;
	}
	/**
	 * Takes in the Strings from an entry in the JournalDB and formats them into an easy to read String for printing.
	 * @param newJournal
	 * @return the new book's date formatted into an easy to read layout
	 */
	public static String toString(Journal newJournal) {
	    String outputString;
	    outputString = "Call #: " + newJournal.call +"\nTitle: " + newJournal.title + "\nOrganization: " + newJournal.organization + "\nYear: " + newJournal.year +"\n";
	    return outputString;
	 }

}
