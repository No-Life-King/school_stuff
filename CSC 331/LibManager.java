import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class LibManager {
	private ArrayList<Book> bookList = new ArrayList<Book>();
	private ArrayList<Patron> patronList = new ArrayList<Patron>();
	private ArrayList<Loan> loanList = new ArrayList<Loan>();
	private String[] menuOptions;

	public static void main(String[] args) {
		LibManager lm = new LibManager();
		lm.execute();
	}

	public LibManager() {
		bookList = readBooks("Resources/books.txt");
		patronList = readPatrons("Resources/patrons.txt");
		loanList = readLoans("Resources/loans.txt");

		menuOptions = new String[] { "Add Book", "Add Patron", "List Books", "List Patrons", "List By Author",
				"List By Year", "Lend Book", "Return Book", "Show Borrower", "Show Borrowed Books", "Remove Book", "Remove Patron",
				"Show Overdue Books", "Exit" };
	}

	private void execute() {

		while (true) {
			int opt = getMenuOption();
			switch (opt) {
			case 1:
				addBook();
				break;
			case 2:
				addPatron();
				break;
			case 3:
				listBooks();
				break;
			case 4:
				listPatrons();
				break;
			case 5:
				listByAuthor();
				break;
			case 6:
				listByYear();
				break;
			case 7:
				lendBookToPatron();
				break;
			case 8:
				returnBook();
				break;
			case 9:
				showBorrowers();
				break;
			case 10:
				showBorrowedBooks();
				break;
			case 11:
				removeBook();
				break;
			case 12:
				removePatron();
				break;
			case 13:
				showOverdueBooks();
				break;
			case 14:
				exitProgram();
				break;
			default:
				System.out.println("No such option");
			}
		}

	}

	private int getMenuOption() {

		System.out.println("Select one of the following options");
		for (int i = 0; i < menuOptions.length; i++) {
			System.out.println("\t" + (i + 1) + ". " + menuOptions[i]);
		}

		Scanner s = new Scanner(System.in);
		int choice = s.nextInt();

		return choice;
	}

	/* MAKE NO CHANGES ABOVE THIS LINE */
	/* COMPLETE ALL THE CODE STUBS BELOW */

	private void exitProgram() {
		System.out.println("Exiting..");
		System.exit(0);
	}

	private ArrayList<Book> readBooks(String filename) {
		BufferedReader in;
		ArrayList<Book> bookList = new ArrayList<Book>();
		
		try {
			in = new BufferedReader(new FileReader("src/datafile.txt"));
		}
		
		if (in != null) {
			for (String line: in.readLine()) {
				String[] line.
				Book book = new Book()
			}
		}
		
		
	}

	private ArrayList<Patron> readPatrons(String filename) {
		// TODO Auto-generated method stub
		return null;
	}

	private ArrayList<Loan> readLoans(String filename) {
		// TODO Auto-generated method stub
		return null;
	}

	private Book locateBook(String bookId) {
		// TODO Auto-generated method stub
		return null;
	}

	private Patron locatePatron(String patronId) {
		// TODO Auto-generated method stub
		return null;
	}

	private void showBorrowedBooks() {
		// TODO Auto-generated method stub

	}

	private void showBorrowers() {
		// TODO Auto-generated method stub

	}

	private void showOverdueBooks() {
		// TODO Auto-generated method stub
		
	}

	private void lendBookToPatron() {
		// TODO Auto-generated method stub

	}

	private void returnBook() {
		// TODO Auto-generated method stub
		
	}

	private void listByYear() {
		// TODO Auto-generated method stub

	}

	private void listByAuthor() {
		// TODO Auto-generated method stub

	}

	private void listPatrons() {
		// TODO Auto-generated method stub

	}

	private void listBooks() {
		// TODO Auto-generated method stub

	}

	private void addPatron() {
		// TODO Auto-generated method stub

	}

	private void addBook() {
		// TODO Auto-generated method stub

	}

	private void removePatron() {
		// TODO Auto-generated method stub

	}

	private void removeBook() {
		// TODO Auto-generated method stub

	}

}