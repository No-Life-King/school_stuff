package assignment1;
/**
Class TestBank
Author: Philip Smith
**/
//MAKE NO CHANGES TO THIS CLASS OTHER THAN UNCOMMENTING LINES ONE BY ONE
//MODIFY THE BankAccount CLASS TO CORRECT ERRORS REVEALED AS YOU UNCOMMENT THE LINES 
public class TestBank {
    private static int counter = 0;

    public static void main(String [] args) {
         BankAccount b1 = new BankAccount(254.36);
         printBalance(b1, 254.36);					//must produce "b1 balance = 254.36..."
         
         double amt = 100.00;
         
         b1.deposit(amt);
         printBalance(b1, 354.36);					//must produce "b1 balance = 354.36..."
         
         b1.withdraw(100.00);
         printBalance(b1, 254.36);
        
         BankAccount b2 = new BankAccount(123.45);	//must produce "b2 balance = 123.45..."
         printBalance(b2, 123.45);
         
         b2.deposit(amt*2);
         printBalance(b2, 323.45);
         
         b2.withdraw(10.56);
         printBalance(b2, 312.89);
         
         b1.transfer(154.36, b2); 					//transfer $154.36 from account b1 to account b2
         printBalance(b1, 100.00);
         printBalance(b2, 467.25);
          
         BankAccount b3 = new BankAccount(83.80);
         printBalance(b3, 83.80); 					//must produce "b3 balance = 83.80..."
         
         b1.setOverdraftAccount(b2); 				// set b2 as the overdraft account for b1
         //if account balance goes below zero, funds are transferred from overdraft account in $50 increments to cover shortfall
         
         b2.setOverdraftAccount(b3); 				//set b3 as the overdraft account for b2
         
         b1.withdraw(554.50);
         printBalance(b1, 45.50);
         printBalance(b2, 17.25);
         printBalance(b3, 33.80);

         Customer x = new Customer("Joe"); 			//create a new Customer with name Joe
         System.out.println("Customer " + x.getId() + " is " + x.getName()); 		//must print Customer c1 is Joe
         
         Customer y = new Customer("Mary"); 		//create a new Customer with name Mary
         System.out.println("Customer " + y.getId() + " is " + y.getName()); 		//must print Customer c2 is Mary
         
         x.addAccount(b1); 							//record the fact that customer x is the owner of account b1
         
         y.addAccount(b2); 							//record the fact that customer y is the owner of account b2

     System.out.println(x.getName() + " assets = "+ x.getAssetTotal()); 			//must print Joe assets = 45.50
     
     System.out.println(y.getName() + " assets = "+ y.getAssetTotal()); 			//must print Mary assets = 17.25
        
    
        
        
    }
    
     private static void printBalance(BankAccount b, double expectedBalance) {
         counter++;
         System.out.println(counter + ". " +  b.getAccountNumber()+ " balance = "+ b.getBalance() + " --> must be "+ expectedBalance);
     }
}
        
        
        