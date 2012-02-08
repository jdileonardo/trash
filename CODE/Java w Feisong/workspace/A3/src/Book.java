/**
 * Manages book entries for the BookDB
 * @author juliandileonardo
 *
 */
public class Book extends Reference{

private String author;
private String publisher;

/**
 * Constructs a Book entry for its addition to the arraylist.
 * @param _call
 * @param _title
 * @param _year
 */
public Book(String _call,String _author,String _title,String _publisher,String _year)
{
	super(_call,_title,_year);
	this.call = _call;
	this.author = _author;
	this.title = _title;
	this.publisher = _publisher;
	this.year = _year;
}

/**
 * Simply returns the Call string from the entry in the BookDB
 * @return the author string
 */
public String getAuthor(){
	return this.author;
}

/**
 * Simply returns the Publisher string from the entry in the BookDB
 * @return the publisher string
 */
public String getPublisher(){
	return this.publisher;
}

/**
 * Takes in the Strings from an entry in the BookDB and formats them into an easy to read String for printing.
 * @param newBook
 * @return the new book's date formatted into an easy to read layout
 */
public static String toString(Book newBook) {
    String outputString;
    outputString = "Call #: " + newBook.call + "\nAuthor(s): " + newBook.author + "\nTitle: " + newBook.title + "\nPublisher: " + newBook.publisher + "\nYear: " + newBook.year +"\n";
    return outputString;
 }

}
