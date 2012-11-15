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

import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JButton;

public class GUI2 extends JFrame {

	private JPanel contentPane;
	private ImageIcon image1;
	private ImageIcon image2;
	private JLabel lbImage1;
	private JLabel lbImage2;
	private JPanel panel1;
	private TitledBorder borderGrayCam1;
	private TitledBorder borderRedCam1; 
	private TitledBorder borderGrayCam2;
	private TitledBorder borderRedCam2; 
	private JPanel panelActive;
	private boolean isIdle;

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
		
		
		//Initialization
		isIdle = true;
		borderGrayCam1 = new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Camera1", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51));
		borderRedCam1 = new TitledBorder(new LineBorder(new Color(255, 0, 0)), "Camera1", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51));
		borderGrayCam2 = new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Camera2", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51));
		borderRedCam2 = new TitledBorder(new LineBorder(new Color(255, 0, 0)), "Camera2", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51));
		
		image1 = new ImageIcon();
		image2 = new ImageIcon();
		
		//Creating panels etc
		
		
		panel1 = new JPanel();
		panel1.setBorder(borderGrayCam1);
		panel1.setBounds(58, 54, 328, 259);
		contentPane.add(panel1);
		panel1.setLayout(null);
		
		JPanel panel2 = new JPanel();
		panel2.setBorder(borderGrayCam2);
		panel2.setBounds(448, 54, 328, 259);
		contentPane.add(panel2);
		panel2.setLayout(null);
		
		lbImage1 = new JLabel(image1);
		lbImage1.setBounds(4, 15, 320, 240);
		panel1.add(lbImage1);
		
		lbImage2 = new JLabel(image2);
		lbImage2.setBounds(4, 5, 320, 240);
		panel2.add(lbImage2);
		
		
		JLabel lbText1 = new JLabel("");
		lbText1.setBounds(58, 325, 319, 69);
		contentPane.add(lbText1);
		
		JLabel lbText2 = new JLabel("");
		lbText2.setBounds(448, 325, 319, 69);
		contentPane.add(lbText2);
		
		JRadioButton rbSync = new JRadioButton("Synchron");
		rbSync.setSelected(true);
		rbSync.setBounds(58, 490, 149, 23);
		contentPane.add(rbSync);
	
		
		JRadioButton rbAsync = new JRadioButton("Asynchron");
		rbAsync.setBounds(237, 490, 149, 23);
		contentPane.add(rbAsync);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(rbSync);
		bg.add(rbAsync);
		
		panelActive = new JPanel();
		panelActive.setBounds(448, 398, 328, 136);
		contentPane.add(panelActive);
		panelActive.setLayout(null);
		
		JButton pbDeactivate = new JButton("Deactivate");
		pbDeactivate.setBounds(199, 99, 117, 25);
		panelActive.add(pbDeactivate);
		
		JLabel lbActive = new JLabel("Movie mode triggered by Camera 0");
		lbActive.setBounds(12, 12, 304, 53);
		panelActive.add(lbActive);
		
		panelActive.setVisible(false);
		
		setVisible(true);
		setResizable(false);
	}
	
	public void refreshImage(byte[] jpeg, boolean isIdle) {
//		image1 = new ImageIcon(jpeg);
//		System.out.println("Image recieved");
//		lbImage1.repaint();
		   Image image = getToolkit().createImage(jpeg);
	        getToolkit().prepareImage(image,-1,-1,null);
	        image1.setImage(image);
	        image1.paintIcon(this, lbImage1.getGraphics(), 0, 0);
	        
	        if(isIdle != this.isIdle){
	        	if(isIdle){
	        		panel1.setBorder(borderGrayCam1);
	        	}else{
	        		panel1.setBorder(borderRedCam1);
	        		panelActive.setVisible(true);
	        	}
	        }
	        this.isIdle = isIdle;
	        
		
	}
}
