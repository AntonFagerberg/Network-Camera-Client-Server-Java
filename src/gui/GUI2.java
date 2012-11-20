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
import java.awt.FlowLayout;

public class GUI2 extends JFrame {

	private JPanel contentPane;
	private ImageIcon image1;
	private ImageIcon image2;
	private JLabel lbImage1;
	private JLabel lbImage2;
	private JPanel panel1;
	private JPanel panel2;
	private TitledBorder borderGrayCam1;
	private TitledBorder borderRedCam1; 
	private TitledBorder borderGrayCam2;
	private TitledBorder borderRedCam2; 
	private JPanel panelActive;
	private boolean isIdle;
	private JLabel lbActive;
	private JLabel delayCamera1;
	private JLabel delayCamera2;
	private JRadioButton rbIdle,rbAuto,rbMovie,rbMovieAuto,rbSync,rbAsync;

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
		
		panel2 = new JPanel();
		panel2.setBorder(borderGrayCam2);
		panel2.setBounds(448, 54, 328, 259);
		contentPane.add(panel2);
		panel2.setLayout(null);
		
		lbImage1 = new JLabel(image1);
		lbImage1.setBounds(4, 15, 320, 240);
		panel1.add(lbImage1);
		
		lbImage2 = new JLabel(image2);
		lbImage2.setBounds(4, 15, 320, 240);
		panel2.add(lbImage2);
		
		
		delayCamera1 = new JLabel("");
		delayCamera1.setBounds(58, 325, 319, 69);
		contentPane.add(delayCamera1);
		
		delayCamera2 = new JLabel("");
		delayCamera2.setBounds(448, 325, 319, 69);
		contentPane.add(delayCamera2);
		
		ButtonGroup bgSync = new ButtonGroup();
		ButtonGroup bgMode = new ButtonGroup();
		
		panelActive = new JPanel();
		panelActive.setBounds(448, 398, 328, 136);
		contentPane.add(panelActive);
		panelActive.setLayout(null);
		
		lbActive = new JLabel("Movie mode triggered by Camera X");
		lbActive.setBounds(0, 0, 304, 21);
		panelActive.add(lbActive);
		
		JPanel settingsContainer = new JPanel();
		settingsContainer.setBorder(new LineBorder(new Color(184, 207, 229), 1, true));
		settingsContainer.setBounds(58, 398, 328, 145);
		contentPane.add(settingsContainer);
		settingsContainer.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(4, 32, 318, 33);
		settingsContainer.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		rbSync = new JRadioButton("Synchronous");
		panel.add(rbSync);
		
		bgSync.add(rbSync);
		
		rbAuto = new JRadioButton("Auto");
		panel.add(rbAuto);
		rbAuto.setSelected(true);
		bgSync.add(rbAuto);
		
		rbAsync = new JRadioButton("Asynchronous");
		panel.add(rbAsync);
		bgSync.add(rbAsync);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(4, 80, 107, 25);
		settingsContainer.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel lblCameraMode = new JLabel("Camera mode");
		panel_2.add(lblCameraMode);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(4, 100, 195, 33);
		settingsContainer.add(panel_3);
		panel_3.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		rbMovie = new JRadioButton("Movie");
		panel_3.add(rbMovie);
		
		rbMovieAuto = new JRadioButton("Auto");
		rbMovieAuto.setSelected(true);
		panel_3.add(rbMovieAuto);
		
		rbIdle = new JRadioButton("Idle");
		panel_3.add(rbIdle);
		
		bgMode.add(rbMovie);
		bgMode.add(rbMovieAuto);
		bgMode.add(rbIdle);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(4, 12, 106, 25);
		settingsContainer.add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel lblSyncSettings = new JLabel("Sync settings");
		panel_1.add(lblSyncSettings);
		
		panelActive.setVisible(false);
		
		setVisible(true);
		setResizable(false);
	}
	
	public void refreshImageCamera1(byte[] jpeg) {
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
	        		lbActive.setText("Movie mode triggered by Camera 1");
	        		panelActive.setVisible(true);
	        	}
	        }
	        this.isIdle = isIdle;
		
	}
	
	public void refreshImageCamera2(byte[] jpeg) {
//		image1 = new ImageIcon(jpeg);
//		System.out.println("Image recieved");
//		lbImage1.repaint();
		   Image image = getToolkit().createImage(jpeg);
	        getToolkit().prepareImage(image,-1,-1,null);
	        image2.setImage(image);
	        image2.paintIcon(this, lbImage2.getGraphics(), 0, 0);
	        
	        if(isIdle != this.isIdle){
	        	if(isIdle){
	        		panel2.setBorder(borderGrayCam2);
	        	}else{
	        		panel2.setBorder(borderRedCam2);
	        		lbActive.setText("Movie mode triggered by Camera 2");
	        		panelActive.setVisible(true);
	        	}
	        }
	        this.isIdle = isIdle;
		
	}
	
	public int getModeFromGui(){
		if(rbMovie.isSelected()){
			return 1;
		} else if (rbIdle.isSelected()){
			return 0;
		} else if(rbMovieAuto.isSelected()){
			return -1;
		}
		return 1337;
	}
	
	public int getSyncFromGui(){
		if(rbSync.isSelected()){
			return 1;
		} else if (rbAsync.isSelected()){
			return 0;
		} else if(rbAuto.isSelected()){
			return -1;
		}
		return 1338;
	}
	
	public void printDelayCamera1(long delay) {
		delayCamera1.setText("Delay: " + delay);
	}
	
	public void printDelayCamera2(long delay) {
		delayCamera2.setText("Delay: " + delay);
	}
	
}
