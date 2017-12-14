/**  @author Philip Smith */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

public class Calculator extends JComponent {
	/**
	 * Creates a new calculator of the specified width and height.
	 * @param width The width that the calculator should be.
	 * @param height The height that the calculator should be.
	 */
	public Calculator(int width, int height) {
		setPreferredSize(new Dimension(width, height));
		setBorder(BorderFactory.createLineBorder(Color.black, 5));
		setLayout(new BorderLayout());

		Display display = new Display(width);
		add(display, BorderLayout.NORTH);

		Controller cc = new Controller(display);

		Keypad keypad = new Keypad(width, height, cc);
		add(keypad, BorderLayout.CENTER);
	}
	

}
