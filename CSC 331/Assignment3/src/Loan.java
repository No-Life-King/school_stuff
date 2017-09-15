import java.util.Calendar;
import java.util.Date;

/** @author Philip Smith */

public class Loan {
	private String bookId;
	private String patronId;
	private Calendar dueDate;
	
	public Loan(String bookId, String patronId, String dueDate) {
		this.bookId = bookId;
		this.patronId = patronId;
		
		String[] dates = dueDate.split("-");
		this.dueDate = new Calendar.Builder().setDate(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]) - 1, Integer.parseInt(dates[2])).build();
	}
	
	public Loan(String bookId, String patronId) {
		this.bookId = bookId;
		this.patronId = patronId;
		
		this.dueDate = new Calendar.Builder().setInstant(System.currentTimeMillis()).build();
		dueDate.add(Calendar.WEEK_OF_YEAR, 2);
	}
	
	public String getBookId() {
		return bookId;
	}
	
	public String getPatronId() {
		return patronId;
	}
	
	public Calendar getDueDate() {
		return dueDate;
	}
	
	public String toString() {
		return bookId + ", " + patronId + ", " + dueDate.get(Calendar.YEAR) + "-" + dueDate.get(Calendar.MONTH) + 1 + "-" + dueDate.get(Calendar.DAY_OF_MONTH);
	}
}
