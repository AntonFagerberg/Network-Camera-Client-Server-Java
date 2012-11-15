package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import common.JPEG;

import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class GUI2 extends JFrame {

	private JPanel contentPane;
	private ImageIcon image1;
	private ImageIcon image2;
	private JLabel lbImage1;
	private JLabel lbImage2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI2 frame = new GUI2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI2() {
     

		setTitle("Video Surveillance");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 861, 593);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		image1 = new ImageIcon("/h/d8/r/ada10lny/Pictures/turtle");
		image2 = new ImageIcon("/h/d8/r/ada10lny/Pictures/turtle");
		
		JPanel panel2 = new JPanel();
		panel2.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Cam 2", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panel2.setBounds(448, 54, 329, 352);
		contentPane.add(panel2);
		panel2.setLayout(null);
		
		lbImage2 = new JLabel(image2);
		lbImage2.setBounds(5, 20, 319, 327);
		panel2.add(lbImage2);
		
		JPanel panel1 = new JPanel();
		panel1.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Cam 1", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panel1.setBounds(58, 54, 329, 352);
		contentPane.add(panel1);
		panel1.setLayout(null);
		
		lbImage1 = new JLabel(image1);
		lbImage1.setBounds(5, 20, 319, 327);
		panel1.add(lbImage1);
		
		JLabel lbText1 = new JLabel("Tjohoo");
		lbText1.setBounds(63, 413, 319, 69);
		contentPane.add(lbText1);
		
		JLabel lbText2 = new JLabel("Tjohoo2");
		lbText2.setBounds(453, 413, 319, 69);
		contentPane.add(lbText2);
		
		JRadioButton rbSync = new JRadioButton("Synchron");
		rbSync.setSelected(true);
		rbSync.setBounds(233, 490, 149, 23);
		contentPane.add(rbSync);
	
		
		JRadioButton rbAsync = new JRadioButton("Asynchron");
		rbAsync.setBounds(453, 490, 149, 23);
		contentPane.add(rbAsync);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(rbSync);
		bg.add(rbAsync);
		
		setVisible(true);
	}
	
	public void refreshImage(byte[] jpeg) {
//		image1 = new ImageIcon(jpeg);
//		System.out.println("Image recieved");
//		lbImage1.repaint();
		   Image image = getToolkit().createImage(jpeg);
	        getToolkit().prepareImage(image,-1,-1,null);     
	        image1.setImage(image);
	        image1.paintIcon(this, lbImage1.getGraphics(), 0, 42);
	        
		
	}
}
