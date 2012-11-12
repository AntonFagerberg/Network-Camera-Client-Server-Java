package gui;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;

public class GUI {
	
	public GUI() {
		
		JFrame frame = new JFrame("Video Surveillance");
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel east = new JPanel();
		JPanel west = new JPanel();
		JPanel north = new JPanel();
		north.setLayout(new FlowLayout());
		east.setLayout(new GridLayout(2,1));
		west.setLayout(new GridLayout(2,1));
		
		JLabel eastText = new JLabel("text1" + "metodföratthämtafpsochdelay");
		JLabel westText = new JLabel("text2" + "metodföratthämtafpsochdelay");
		east.add(eastText,0,0);
		west.add(westText,0,0);
		
		ImageIcon image1 = new ImageIcon("/h/d8/r/ada10lny/Pictures/turtle");
		ImageIcon image2 = new ImageIcon("/h/d8/r/ada10lny/Pictures/turtle");
//		Canvas cv1 = new Canvas();
//		Canvas cv2 = new Canvas();
//		west.add(cv1);
//		east.add(cv2);
		
		JLabel label1 = new JLabel(image1);
		JLabel label2 = new JLabel(image2);
		label1.setSize(50,50);
		label2.setSize(50,50);
		
		east.add(label1,1,0);
		west.add(label2,1,0);
		
		JRadioButton synchron = new JRadioButton("Synkron");
		JRadioButton asynchron = new JRadioButton("Asynkron");
		ButtonGroup buttons = new ButtonGroup();
		buttons.add(synchron);
		buttons.add(asynchron);
		north.add(synchron);
		north.add(asynchron);
		
		
		frame.add(north,BorderLayout.NORTH);
		frame.add(east,BorderLayout.EAST);
		frame.add(west,BorderLayout.WEST);
		
		frame.pack();
		frame.setVisible(true);
		
	}
	
	public static void main(String[] args){
		GUI g = new GUI();
	}
	
}
