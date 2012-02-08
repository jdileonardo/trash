/**
 * Reference Class is the super class that contains similarities in Book and Java entries
 * @author juliandileonardo
 *
 */
public class Reference {
	protected String call;
	protected String title;
	protected String year;
/**
 * Construct a reference object with the call, title and year entered from the user.
 * @param _call 
 * @param _title
 * @param _year
 */
public Reference(String _call,String _title,String _year)
{
	this.call = _call;
	this.title = _title;
	this.year = _year;
}
	/**
	 * Simply returns the Call string from the entry in the RefereceDB
	 * @return the call string
	 */
	public String getCall() {
		return this.call;
	}
	/**
	 * Simply returns the Title string from the entry in the RefereceDB
	 * @return the title string
	 */
	public String getTitle(){
		return this.title;
	}
	/**
	 * Simply returns the Year string from the entry in the RefereceDB
	 * @return the year string
	 */
	public String getYear() {
		return this.year;
	}
}
