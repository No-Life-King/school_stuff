import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JFrame;


public class CalcTest extends JFrame {

	public CalcTest() {
		setTitle("Calc Test");
		setSize(800, 800);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String[] args) {
		CalcTest ct = new CalcTest();
		Container c = ct.getContentPane();
		c.setLayout(new FlowLayout());
		
		int width = 400;
		int height = 400;
		c.add(new Calculator(width, height));
		c.add(new Calculator(400, 200));
		c.add(new Calculator(400, 300));
		c.add(new Calculator(450, 250));
		
		ct.setVisible(true);
	}

}
