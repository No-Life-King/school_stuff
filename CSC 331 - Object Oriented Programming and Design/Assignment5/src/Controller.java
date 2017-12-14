/**  @author Philip Smith */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.JButton;

public class Controller implements ActionListener {
	/**
	 * This is the controller that handles all of the calculator events.
	 */
	private Display display;
	
	/**
	 * Initializes a new controller with the specified display.
	 * @param display The display that the controller should interact with.
	 */
	public Controller(Display display) {
		this.display = display;
	}

	/**
	 * This method is called when any of the buttons are clicked and invokes the appropriate response.
	 */
	@Override
	public void actionPerformed(ActionEvent buttonClicked) {
		JButton button = (JButton) buttonClicked.getSource();
		String buttonText = button.getText();
		
		if (display.getInputString().equals("Error: Invalid Expression")) {
			display.displayAnswer("");
		}
		
		switch (buttonText) {
		case "AC":
			display.clearText();
			break;
		case "Ans":
		case "=":
			evaluate();
			break;
		default:
			display.addChar(buttonText);
			break;
		}
		
	}
	
	/**
	 * Evaluates the expression and outputs it to the display.
	 */
	private void evaluate() {
		String inputString = display.getInputString();
		
		if (inputString.startsWith("+") || inputString.startsWith("-") || inputString.startsWith("*") || inputString.startsWith("/") 
				|| inputString.endsWith("+") || inputString.endsWith("-") || inputString.endsWith("*") || inputString.endsWith("/")) {
					display.displayAnswer("Error: Invalid Expression");
		} else {
			int index = 0;
			Stack<String> portions = new Stack<String>();
			String number = "";
			boolean lastCharOperator = false;
			
			while (index < inputString.length()) {
				
				String character = inputString.substring(index, index+1);
				if (isOperator(character)) {
					if (lastCharOperator) {
						display.displayAnswer("Error: Invalid Expression");
						break;
					}
					portions.add(number);
					portions.add(character);
					number = "";
					lastCharOperator = true;
				} else {
					number += character;
					lastCharOperator = false;
				}

				index++;
			}
			
			portions.add(number);
			Stack<String> temp = new Stack<String>();
			while (!portions.isEmpty()) {
				temp.push(portions.pop());
			}
			portions = temp;
			
			Stack<Double> numbers = new Stack<Double>();
			Stack<String> operators = new Stack<String>();
			
			while (!portions.isEmpty()) {
				String portion = portions.pop();
				if (isOperator(portion)) {
					if (portion.equals("*") || portion.equals("/")) {
						double result;
						
						if (portion.equals("*")) {
							result = numbers.pop() * Double.parseDouble(portions.pop());
						} else {
							result =  numbers.pop() / Double.parseDouble(portions.pop());
						}
						
						numbers.push(result);
					} else {
						operators.push(portion);
					}
				} else {
					numbers.push(Double.parseDouble(portion));
				}
			}
			
			while (numbers.size()>1) {
				if (operators.pop().equals("+")) {
					numbers.push(numbers.pop() + numbers.pop());
				} else {
					double num1 = numbers.pop();
					double num2 = numbers.pop();
					numbers.push(num2 - num1);
				}
			}
			
			display.displayAnswer(numbers.pop().toString());
		}
		
	}
	
	/**
	 * Determines whether or not the specified character is an operator.
	 * @param character The character to be characterized.
	 * @return True if the character is an operator, otherwise false.
	 */
	private boolean isOperator(String character) {
		return character.equals("+") || character.equals("-") || character.equals("*") || character.equals("/");
	}
	
	

}
