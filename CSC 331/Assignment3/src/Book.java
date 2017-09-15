/** @author Philip Smith */


public class Book {
	private String uid;
	private String title;
	private String author;
	private int year;
	private static int currentId = 1002;
	
	public Book(String uid, String title, String author, String year) {
		this.uid = uid;
		this.title = title;
		this.author = author;
		this.year = Integer.parseInt(year);
	}
	
	public Book(String title, String author, String year) {
		this.title = title;
		this.author = author;
		this.year = Integer.parseInt(year);
		
		uid = "B" + currentId;
		currentId++;
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
