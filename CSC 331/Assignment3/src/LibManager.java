import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
		System.out.println("Exiting...");
		
		try {
			FileWriter bookWriter = new FileWriter("Resources/books.txt"); 
			FileWriter patronWriter = new FileWriter("Resources/patrons.txt"); 
			FileWriter loanWriter = new FileWriter("Resources/loans.txt"); 
			
			for (Book book: bookList) {
				bookWriter.write(book.getId() + "\t;\t" + book.getTitle() + "\t;\t" + book.getAuthor() + "\t;\t" + book.getYear() + "\r\n");
			}
			
			for (Patron patron: patronList) {
				patronWriter.write(patron.getId() + "\t" + patron.getName() + "\r\n");
			}
			
			
			for (Loan loan: loanList) {
				int month = loan.getDueDate().get(Calendar.MONTH) + 1;
				loanWriter.write(loan.getBookId() + "," + loan.getPatronId() + "," + loan.getDueDate().get(Calendar.YEAR) + "-" 
				+ month + "-" + loan.getDueDate().get(Calendar.DAY_OF_MONTH) + "\r\n");
			}
			
			bookWriter.close();
			patronWriter.close();
			loanWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	private ArrayList<Book> readBooks(String filename) {
		ArrayList<Book> bookList = new ArrayList<Book>();
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(filename));
			String line;
			
			// read in each line of the text file creating a new book object and adding it to the book list
			while((line = in.readLine()) != null) {
				String[] bookInfo = line.split(";");
				Book book = new Book(bookInfo[0].trim(), bookInfo[1].trim(), bookInfo[2].trim(), bookInfo[3].trim());
				bookList.add(book);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return bookList;
	}

	private ArrayList<Patron> readPatrons(String filename) {
		ArrayList<Patron> patronList = new ArrayList<Patron>();
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(filename));
			String line;

			while((line = in.readLine()) != null) {
				String[] patronInfo = line.split("\t");
				Patron patron = new Patron(patronInfo[0].trim(), patronInfo[1].trim());
				patronList.add(patron);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return patronList;
	}

	private ArrayList<Loan> readLoans(String filename) {
		ArrayList<Loan> loanList = new ArrayList<Loan>();
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(filename));
			String line;

			while((line = in.readLine()) != null) {
				String[] loanInfo = line.split(",");
				Loan loan = new Loan(loanInfo[0].trim(), loanInfo[1].trim(), loanInfo[2].trim());
				loanList.add(loan);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return loanList;
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
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String patronId;
  
        try {
            System.out.print("Enter Patron ID: ");
            patronId = in.readLine();
            
            for (Loan loan: loanList) {
            	if (loan.getPatronId().equalsIgnoreCase(patronId)) {
            		for (Book book: bookList) {
            			if (book.getId().equalsIgnoreCase(loan.getBookId())) {
            				System.out.println(book);
            			}
            		}
            	}
            }
            
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}

	private void showBorrowers() {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String bookId;
  
        try {
            System.out.print("Enter Book ID: ");
            bookId = in.readLine();
            
            for (Loan loan: loanList) {
            	if (loan.getBookId().equalsIgnoreCase(bookId)) {
            		String patronId = loan.getPatronId();
            		for (Patron patron: patronList) {
            			if (patron.getId().equalsIgnoreCase(patronId)) {
            				System.out.println(patron.getName());
            				break;
            			}
            		}
            	}
            }
            
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}

	private void showOverdueBooks() {
		for (Loan loan: loanList) {
			long twoWeeks = 1209600000L;
			if (((new Calendar.Builder().setInstant(new Date()).build().getTimeInMillis()) - loan.getDueDate().getTimeInMillis()) > twoWeeks) {
				System.out.print(loan.getBookId());
				for (Book book: bookList) {
					if (book.getId().equalsIgnoreCase(loan.getBookId())) {
						System.out.println(", " + book.getTitle());
					}
				}
			}
		}
	}

	private void lendBookToPatron() {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String patronId;
        String bookId;
        boolean onLoan = false;
  
        try {
        	System.out.print("Enter Patron ID: ");
            patronId = in.readLine();
            System.out.print("Enter Book ID: ");
            bookId = in.readLine();
            
            for (Loan loan: loanList) {
            	if (loan.getBookId().equalsIgnoreCase(bookId)) {
            		onLoan = true;
            		break;
            	}
            }
            
            if (!onLoan) {
            	loanList.add(new Loan(bookId, patronId));
            }
            
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}

	private void returnBook() {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String bookId;
  
        try {
            System.out.print("Enter Book ID: ");
            bookId = in.readLine();
            boolean checkedOut = false;
            
            for (Loan loan: loanList) {
            	if (loan.getBookId().equalsIgnoreCase(bookId)) {
            		checkedOut = true;
            		loanList.remove(loan);
            		break;
            	} 
            }
            
            if (!checkedOut) {
            	System.out.println("Book not checked out.");
            }
            
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}

	private void listByYear() {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int min;
        int max;
  
        try {
        	System.out.print("Minimum Year: ");
            min = Integer.parseInt(in.readLine());
            System.out.print("Maximum Year: ");
            max = Integer.parseInt(in.readLine());
            
            for (Book book: bookList) {
            	if (book.getYear() >= min && book.getYear() <= max) {
            		System.out.println(book.getId() + ", " + book.getTitle() + ", " + book.getAuthor());
            	}
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}

	private void listByAuthor() {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String name;
  
        try {
        	System.out.print("Enter an author to search for: ");
            name = in.readLine();
            for (Book book: bookList) {
            	if (book.getAuthor().equalsIgnoreCase(name)) {
            		System.out.println(book.getId() + ", " + book.getTitle() + ", " + book.getYear());
            	}
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}

	private void listPatrons() {
		for (Patron patron: patronList) {
			System.out.println(patron);
		}
	}

	private void listBooks() {
		for (Book book: bookList) {
			System.out.println(book);
		}
	}

	private void addPatron() {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String name;
  
        try {
        	System.out.print("Patron Name: ");
            name = in.readLine();
            Patron newPatron = new Patron(name);
            patronList.add(newPatron);
        } catch (Exception e) {
        	e.printStackTrace();
        }

	}

	private void addBook() {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String author;
        String title;
        String year;
  
        try {
        	System.out.print("Author: ");
            author = in.readLine();
            System.out.print("Title: ");
            title = in.readLine();
            System.out.print("Publication Year: ");
            year = in.readLine();
            Book newBook = new Book(title, author, year);
            bookList.add(newBook);
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
	

	private void removePatron() {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String patronId;
  
        try {
            System.out.print("Enter Patron ID: ");
            patronId = in.readLine();
            
            for (Patron patron: patronList) {
            	if (patron.getId().equalsIgnoreCase(patronId)) {
            		patronList.remove(patron);
            		System.out.println("\"" + patron.getName() + "\" has been deleted.");
            		break;
            	}
            }
            
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}

	private void removeBook() {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String bookId;
  
        try {
            System.out.print("Enter Book ID: ");
            bookId = in.readLine();
            
            for (Book book: bookList) {
            	if (book.getId().equalsIgnoreCase(bookId)) {
            		bookList.remove(book);
            		System.out.println("\"" + book.getTitle() + "\" has been deleted.");
            		break;
            	}
            }
            
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}

}