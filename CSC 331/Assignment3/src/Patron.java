/** @author Philip Smith */
import java.util.ArrayList;

public class Patron {
	private String uid;
	private String name;
	private ArrayList<Book> borrowedBooks;
	private static int currentId = 0;
	
	public Patron(String uid, String name) {
		this.uid = uid;
		this.name = name;
		
		// keep track of the highest id number
		int idNumber = Integer.parseInt(uid.substring(1));
		if (idNumber > currentId) {
			currentId = idNumber;
		}
	}
	
	// create a new patron and generate an id for that person
	public Patron(String name) {
		this.name = name;
		currentId++;
		uid = "P" + currentId;
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
