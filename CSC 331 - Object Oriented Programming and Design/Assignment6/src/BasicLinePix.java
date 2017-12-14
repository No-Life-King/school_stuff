/** @author Phil Smith
 *  @author Dr. Sridhar Narayan */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

@SuppressWarnings("serial")
public class BasicLinePix extends JFrame {
	/**
	 * A sweet program for drawing lines.
	 */
	
	private JPanel drawingPanel; 	// user draws here
	private Container cp;
	private JPanel statusBar;
	private JLabel statusLabel;		// used to show informational messages
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private EventHandler eh;
	private ArrayList<Line> lines = new ArrayList<Line>();
	
	/**
	 * The main method.
	 * @param args The command-line arguments.
	 */
	public static void main(String[] args) {

		BasicLinePix lp = new BasicLinePix();
		
		lp.setVisible(true);

	}
	
	/**
	 * Builds the program's window and initializes necessary variables.
	 */
	public BasicLinePix() {
		setTitle("Basic Line Pix 1.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		eh = new EventHandler();

		drawingPanel = makeDrawingPanel();
		drawingPanel.addMouseListener(eh);
		drawingPanel.addMouseMotionListener(eh);

		
		statusBar = createStatusBar();

		cp.add(drawingPanel, BorderLayout.CENTER);
		cp.add(statusBar, BorderLayout.SOUTH);

		
		buildMenu();

		pack();
	}

	/**
	 * Builds the menu bar and adds the action listener to each object.
	 */
	public void buildMenu() {	
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		
		JMenuItem menuItem = new JMenuItem("New");
		menuItem.addActionListener(eh);
		fileMenu.add(menuItem);

		menuItem = new JMenuItem("Open");
		menuItem.addActionListener(eh);
		fileMenu.add(menuItem);

		menuItem = new JMenuItem("Save");
		menuItem.addActionListener(eh);
		fileMenu.add(menuItem);

		menuItem = new JMenuItem("Exit");
		menuItem.addActionListener(eh);
		fileMenu.add(menuItem);

		menuBar.add(fileMenu);
		
		
		setJMenuBar(menuBar);
	}

	/**
	 * Builds the canvas on which lines can be drawn.
	 * @return A JPanel representing a canvas.
	 */
	private JPanel makeDrawingPanel() {
		JPanel p = new JPanel();
		p.setPreferredSize(new Dimension(500, 400));
		p.setBackground(Color.YELLOW);


		return p;
	}

	private JPanel createStatusBar() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		statusLabel = new JLabel("No message");
		panel.add(statusLabel, BorderLayout.CENTER);

		panel.setBorder(new BevelBorder(BevelBorder.LOWERED));

		return panel;
	}

	//this method overrides the paint method defined in JFrame
	//add code here for drawing the lines on the drawingPanel
	/**
	 * Draws all the lines to the canvas.
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
	
		Graphics g1 = drawingPanel.getGraphics();

		// Send a message to each line in the drawing to
		// draw itself on g1
		for (Line aLine: lines) {
			aLine.draw(g1);
		}
	}

	// Inner class - instances of this class handle action events
	private class EventHandler implements ActionListener, MouseListener, MouseMotionListener {

		private int startX, startY; 	// line's start coordinates
		private int lastX, lastY; 		// line's most recent end point
		private Line line;
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getActionCommand().equals("Exit")) {
				statusLabel.setText("Exiting program...");
				System.exit(0);
			} 
			
			if (arg0.getActionCommand().equals("New")) {
				lines = new ArrayList<Line>();
				repaint();
				statusLabel.setText("Started a new drawing.");
			}
			
			if (arg0.getActionCommand().equals("Save")) {
				JFileChooser fileChooser = new JFileChooser();
				int returnVal = fileChooser.showSaveDialog(BasicLinePix.this);

		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fileChooser.getSelectedFile();
		            try {
		    			FileWriter imageWriter = new FileWriter(file); 

		    			for (Line line: lines) {
		    				imageWriter.write(line.toWritableString() + "\r\n");
		    			}
		    			
		    			imageWriter.close();
		            } catch(Exception e) {
		            	e.printStackTrace();
		            }
		        }
			}
			
			if (arg0.getActionCommand().equals("Open")) {
				JFileChooser fileChooser = new JFileChooser();
				int returnVal = fileChooser.showOpenDialog(BasicLinePix.this);

		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fileChooser.getSelectedFile();
					try {
						BufferedReader in = new BufferedReader(new FileReader(file));
						String line;
						
						lines = new ArrayList<Line>();
						while((line = in.readLine()) != null) {
							String[] lineString = line.split("\t");
							int[] lineInfo = new int[5];
							for (int x=0; x<5; x++) {
								lineInfo[x] = Integer.parseInt(lineString[x]);
							}
							Line savedLine = new Line(lineInfo[4]);
							savedLine.setStartingPoint(lineInfo[0], lineInfo[1]);
							savedLine.setEndPoint(lineInfo[2], lineInfo[3]);
							lines.add(savedLine);
							repaint();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
		        }
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			
			startX = e.getX();
			startY = e.getY();

			// initialize lastX, lastY
			lastX = startX;
			lastY = startY;
			statusLabel.setText("Drawing...");
			line = new Line();
			line.setStartingPoint(startX, startY);
			
		}

		@Override
		public void mouseDragged(MouseEvent drag) {
			// Implement rubber-band cursor
			
			Graphics g = drawingPanel.getGraphics();
			g.setColor(line.getColor());
		
			g.setXORMode(drawingPanel.getBackground());
		
			// REDRAW the line that was drawn 
			// most recently during this drag
			// XOR mode means that yellow pixels turn black
			// essentially erasing the existing line
			g.drawLine(startX, startY, lastX, lastY);
		
			// draw line to current mouse position
			// XOR mode: yellow pixels become black
			// black pixels, like those from existing lines, temporarily become
			// yellow
			
			g.drawLine(startX, startY, drag.getX(), drag.getY());
			lastX = drag.getX();
			lastY = drag.getY();
			
		}

		@Override
		public void mouseReleased(MouseEvent release) {
			int endx = release.getX();
			int endy = release.getY();
			line.setEndPoint(endx, endy);
			if (startX != endx && startY != endy) {
				lines.add(line);
				statusLabel.setText(line.toString());
			}
			
		}

		@Override
		public void mouseClicked(MouseEvent click) {
			if (click.isControlDown()) {
				
				Line deleteMe = null;
				for (Line line: lines) {
					if (line.contains(click.getX(), click.getY())) {
						deleteMe = line;
						break;
					}
				}
				
				if (deleteMe != null) {
					lines.remove(deleteMe);
					repaint();
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

	}

}
