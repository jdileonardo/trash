/**
 * Manages book entries for the BookDB
 * @author juliandileonardo
 *
 */
public class Book {
private String call;
private String author;
private String title;
private String publisher;
private String year;
/**
 * Constructs a Book entry for its addition to the arraylist.
 * @param _call
 * @param _author
 * @param _title
 * @param _publisher
 * @param _year
 */
public Book(String _call,String _author,String _title,String _publisher,String _year)
{
	this.call = _call;
	this.author = _author;
	this.title = _title;
	this.publisher = _publisher;
	this.year = _year;
}
/**
 * Simply returns the Call string from the entry in the BookDB
 * @return the call string
 */
public String getCall() {
	return this.call;
}
/**
 * Simply returns the Call string from the entry in the BookDB
 * @return the author string
 */
public String getAuthor(){
	return this.author;
}
/**
 * Simply returns the Title string from the entry in the BookDB
 * @return the title string
 */
public String getTitle(){
	return this.title;
}
/**
 * Simply returns the Publisher string from the entry in the BookDB
 * @return the publisher string
 */
public String getPublisher(){
	return this.publisher;
}
/**
 * Simply returns the Year string from the entry in the BookDB
 * @return the year string
 */
public String getYear() {
	return this.year;
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
