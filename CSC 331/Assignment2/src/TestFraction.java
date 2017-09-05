public class TestFraction
{

   public static void main (String args[])
   {
          Fraction f1 = new Fraction(2, 3); 
          System.out.println("f1 = " + f1.toString()); 				//must print f1 = 2/3
          System.out.println("f1 = " + f1.toDecimal()); 			// must print f1 = 0.666666
          
          Fraction f2 = new Fraction(3, 6); 
          System.out.println("f2 = " + f2); 						//must print f2 = 1/2
          
          Fraction f3 = f1.add(f2); 								//f3 = f1 + f2
          System.out.println(f1 + " + " + f2 + " = " + f3); 		//must print 2/3 + 1/2 = 7/6
          
          f3 = f1.mult(f2); 										//f3 = f1 * f2
          System.out.println(f1 + " * " + f2 + " = " + f3); 		//must print 2/3 * 1/2 = 1/3
          
          f3 = f2.sub(f1); 											//f3 = f2 - f1
          System.out.println(f2 + " - " + f1 + " = " + f3); 		//must print 1/2 - 2/3 = -1/6
          
          f3 = f2.div(f1); 											// f3 = f2/f1
          System.out.println(f2 + " div " + f1 + " = " + f3); 		//must print 1/2 div 2/3 = 3/4
          
          System.out.println(f3.add(f1.mult(f2))); 					//must print 13/12
          
          System.out.println(f1.compareTo(f2)); 					//must print 1 since f1 > f2
          System.out.println(f2.compareTo(f1)); 					//must print -1 since f2 < f1
          System.out.println(f1.compareTo(new Fraction(2,3))); 		//must print 0 since the fractions being compared are equal
          
          f2 = new Fraction(0.5); 									//overloaded constructor
          System.out.println("f2 = " + f2); 						//must print f2 = 1/2
   }

}