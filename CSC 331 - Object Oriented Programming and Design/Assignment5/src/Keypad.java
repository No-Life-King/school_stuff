/**  @author Philip Smith */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;

public class Keypad extends JComponent {
	/**
	 * The keypad of the calculator.
	 */
	private Controller cc;
	
	/**
	 * Initializes a new keypad with the specified width and height.
	 * @param width The width that the keypad should be.
	 * @param height The height that the keypad should be.
	 * @param cc The controller that handles all of the button actions of the keypad.
	 */
	public Keypad(int width, int height, Controller cc) {
		setPreferredSize(new Dimension(width-20, height-55));
		setLayout(new GridLayout(5,4));
		setBorder(BorderFactory.createLineBorder(Color.red, 2));
		
		this.cc = cc;
		drawKeypad();
	}

	/**
	 * Painstakingly draws the keypad.
	 */
	private void drawKeypad() {
		// draw row 1 and add action listeners to the buttons
		JButton ans = new JButton("Ans");
		ans.addActionListener(cc);
		add(ans);
		
		JButton blank1 = new JButton("");
		blank1.addActionListener(cc);
		add(blank1);
		
		JButton blank2 = new JButton("");
		blank2.addActionListener(cc);
		add(blank2);
		
		JButton ac = new JButton("AC");
		ac.addActionListener(cc);
		add(ac);
		
		// draw row 2 and add action listeners to the buttons
		JButton seven = new JButton("7");
		seven.addActionListener(cc);
		add(seven);
		
		JButton eight = new JButton("8");
		eight.addActionListener(cc);
		add(eight);
		
		JButton nine = new JButton("9");
		nine.addActionListener(cc);
		add(nine);
		
		JButton divide = new JButton("/");
		divide.addActionListener(cc);
		add(divide);

		// draw row 3 and add action listeners to the buttons
		JButton four = new JButton("4");
		four.addActionListener(cc);
		add(four);
		
		JButton five = new JButton("5");
		five.addActionListener(cc);
		add(five);
		
		JButton six = new JButton("6");
		six.addActionListener(cc);
		add(six);
		
		JButton multiply = new JButton("*");
		multiply.addActionListener(cc);
		add(multiply);
		
		// draw row 4 and add action listeners to the buttons
		JButton one = new JButton("1");
		one.addActionListener(cc);
		add(one);
		
		JButton two = new JButton("2");
		two.addActionListener(cc);
		add(two);
		
		JButton three = new JButton("3");
		three.addActionListener(cc);
		add(three);
		
		JButton subtract = new JButton("-");
		subtract.addActionListener(cc);
		add(subtract);

		// draw row 5 and add action listeners to the buttons
		JButton zero = new JButton("0");
		zero.addActionListener(cc);
		add(zero);
		
		JButton decimalPoint = new JButton(".");
		decimalPoint.addActionListener(cc);
		add(decimalPoint);
		
		JButton equals = new JButton("=");
		equals.addActionListener(cc);
		add(equals);
		
		JButton add = new JButton("+");
		add.addActionListener(cc);
		add(add);
	}

}
