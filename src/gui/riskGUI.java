/*
 * 	Authors: 	Dylan Tobia, Abigail Dodd, Sydney Komro, Jewell Finder
 * 	File:		riskGUI.java
 * 	Purpose:	GUI for visual implementation of RISK
 */

package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;



//just a simple GUI to start, with a drawingPanel for map stuff
public class riskGUI extends JFrame{

	public static void main(String[] args) throws UnknownHostException, IOException
	{
		new riskGUI().setVisible(true);
	}
	
	private BoardPanel drawingPanel;
	private JMenuBar menu;
	private int width;
	private int height;
	private ImageIcon gameBoard;
	
	public riskGUI(){
		width = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
		height = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
		setUpGui();
		setUpDrawingPanel();
		setUpMenu();
	}
	private void setUpGui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(20,20);
		setLayout(null);
		setTitle("GOT Risk");
		setSize(1800,1000);
	}
	private void setUpMenu() {
		JMenu help = new JMenu("Help");
		menu = new JMenuBar();
		JMenuItem about = new JMenuItem("About");
		menu.add(help);
		help.add(about);
		this.setJMenuBar(menu);
		
		about.addActionListener(new helpListener());
		
	}
	private void setUpDrawingPanel() {
		gameBoard = new ImageIcon("GoTMapRisk.jpg");
		drawingPanel = new BoardPanel();
		drawingPanel.setSize(width, height);
		drawingPanel.setLocation(10,10);
		drawingPanel.setBackground(Color.LIGHT_GRAY);
		drawingPanel.repaint();
		this.add(drawingPanel);
		
		
	}
	
	private class BoardPanel extends JPanel{
		public void PaintComponent(Graphics g){
			System.out.println("HI!");
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.BLACK);
			g2.fillRect(25,25,25,25);
			super.paintComponent(g2);
			Image tmp = gameBoard.getImage();
			g2.drawImage(tmp, 5, 5, null);
		}
		
	}
	
	private class helpListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JOptionPane.showMessageDialog(riskGUI.this, "This version of Risk was created by Dylan Tobia,\nAbigail Dodd, Sydney Komro, and Jewell Finder."
					+ "\nCreated for our CS335 class as our final project.", "About",JOptionPane.INFORMATION_MESSAGE);
			
		}
		
	}
}
