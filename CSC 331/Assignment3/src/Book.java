/** @author Philip Smith */


public class Book {
	private String uid;
	private String title;
	private String author;
	private int year;
	private static int currentId = 0;
	
	public Book(String uid, String title, String author, String year) {
		this.uid = uid;
		this.title = title;
		this.author = author;
		this.year = Integer.parseInt(year);
		
		// keep track of the highest id number
		int idNumber = Integer.parseInt(uid.substring(1));
		if (idNumber > currentId) {
			currentId = idNumber;
		}
	}
	
	// set the new book values and generate an id for the book
	public Book(String title, String author, String year) {
		this.title = title;
		this.author = author;
		this.year = Integer.parseInt(year);
		
		currentId++;
		uid = "B" + currentId;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public String getId() {
		return uid;
	}
	
	public String getTitle() {
		return title;
	}
	
	public int getYear() {
		return year;
	}
	
	@Override
	public String toString() {
		return uid + ", " + title + ", " + author + ", " + year;
	}
	
}
