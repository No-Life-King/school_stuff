/** @author Philip Smith */
import java.util.ArrayList;

public class Patron {
	private String uid;
	private String name;
	private ArrayList<Book> borrowedBooks;
	private static int currentId = 1001;
	
	public Patron(String uid, String name) {
		this.uid = uid;
		this.name = name;
	}
	
	public Patron(String name) {
		this.name = name;
		uid = "P" + currentId;
		currentId++;
	}
	
	public String getId() {
		return uid;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return uid + ": " + name;
	}
	
}
