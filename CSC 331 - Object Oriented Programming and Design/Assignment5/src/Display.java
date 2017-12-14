/**  @author Philip Smith */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTextField;

public class Display extends JComponent {
	/**
	 * This object handles all the properties and behaviors of the display window of the calculators.
	 */
	private JTextField textField;
	
	/**
	 * Draws the display.
	 * @param width The wideness of the display.
	 */
	public Display(int width) {
		setPreferredSize(new Dimension(width-20, 50));
		setLayout(new FlowLayout());
		setBorder(BorderFactory.createLineBorder(Color.blue, 2));
		
		JTextField textField = new JTextField();
		textField.setPreferredSize(new Dimension(width-20, 38));
		add(textField);
		this.textField = textField;
	}
	
	/**
	 * Used to add the specified character to the text field of the display.
	 * @param character The number or symbol that was clicked on.
	 */
	public void addChar(String character) {
		textField.setText(textField.getText() + character);
	}

	/**
	 * Clears everything out of the text field.
	 */
	public void clearText() {
		textField.setText("");		
	}
	
	/**
	 * Gets the expression from the text field.
	 * @return A string that represents the expression.
	 */
	public String getInputString() {
		return textField.getText();
	}
	
	/**
	 * Used to set the answer or an error in the text field.
	 * @param answer The string to be displayed.
	 */
	public void displayAnswer(String answer) {
		textField.setText(answer);
	}
	
}
