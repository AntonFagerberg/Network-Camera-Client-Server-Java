package client;

import client.ClientStateMonitor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame implements ActionListener {

	public final static int
        SYNC_AUTO = -1,
        SYNC_SYNC = 1,
        SYNC_ASYNC = 0;
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
	private JRadioButton rbIdle, rbAuto, rbMovie, rbMovieAuto, rbSync, rbAsync;
	private ClientStateMonitor clientStateMonitor;
	private JLabel lblCurrentSyncMode;


	/**
	 * Create the frame.
	 */
	public GUI(ClientStateMonitor clientStateMonitor) {
		
		this.clientStateMonitor = clientStateMonitor;
		setTitle("Video Surveillance");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 861, 593);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Initialization
		isIdle = true;
		borderGrayCam1 = new TitledBorder(new LineBorder(new Color(184, 207,
				229)), "Camera1", TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(51, 51, 51));
		borderRedCam1 = new TitledBorder(new LineBorder(new Color(255, 0, 0)),
				"Camera1", TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(51, 51, 51));
		borderGrayCam2 = new TitledBorder(new LineBorder(new Color(184, 207,
				229)), "Camera2", TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(51, 51, 51));
		borderRedCam2 = new TitledBorder(new LineBorder(new Color(255, 0, 0)),
				"Camera2", TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(51, 51, 51));

		image1 = new ImageIcon();
		image2 = new ImageIcon();

		// Creating panels etc

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
		panelActive.setBounds(448, 429, 328, 25);
		contentPane.add(panelActive);
		panelActive.setLayout(null);
		
        lbActive = new JLabel("Movie mode triggered by Camera X");
        lbActive.setBounds(0, 0, 304, 21);
        panelActive.add(lbActive);

		JPanel settingsContainer = new JPanel();
		settingsContainer.setBorder(new LineBorder(new Color(184, 207, 229), 1,
				true));
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
		panel_3.setBounds(4, 100, 220, 33);
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
		
		rbMovie.addActionListener(this);
		rbMovieAuto.addActionListener(this);
		rbIdle.addActionListener(this);
		
		
		

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(4, 12, 106, 25);
		settingsContainer.add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		JLabel lblSyncSettings = new JLabel("Sync settings");
		panel_1.add(lblSyncSettings);
				
				lblCurrentSyncMode = new JLabel("Current sync mode: ");
				lblCurrentSyncMode.setBounds(448, 408, 328, 15);
				contentPane.add(lblCurrentSyncMode);

		panelActive.setVisible(false);

        changeSyncLabel(SYNC_ASYNC);
		setVisible(true);
		setResizable(false);
	}

	public void refreshCameraImage(byte[] jpeg, int cameraIndex) {
		if (cameraIndex == 1) {
			image1.setImage(getToolkit().createImage(jpeg));
			image1.paintIcon(this, lbImage1.getGraphics(), 0, 0);
		} else if (cameraIndex == 2) {
			image2.setImage(getToolkit().createImage(jpeg));
			image2.paintIcon(this, lbImage2.getGraphics(), 0, 0);
		}
	}

	public void setModeInMonitor() {
		if (rbMovie.isSelected()) {
            clientStateMonitor.setMode(ClientStateMonitor.MOVIE_FORCED);
		} else if (rbIdle.isSelected()) {
            clientStateMonitor.setMode(ClientStateMonitor.IDLE_FORCED);
		} else {
            clientStateMonitor.setMode(ClientStateMonitor.IDLE);
		}
	}

	public int getSyncFromGui() {
		if (rbSync.isSelected()) {
			changeSyncLabel(SYNC_SYNC);
			return SYNC_SYNC;
		} else if (rbAsync.isSelected()) {
			changeSyncLabel(SYNC_ASYNC);
			return SYNC_ASYNC;
		} else {
			return SYNC_AUTO;
		}
	}

	public void printDelay(long delay, int cameraIndex) {
		if (cameraIndex == 1) {
			delayCamera1.setText("Delay: " + delay + " ms");
		} else if (cameraIndex == 2) {
			delayCamera2.setText("Delay: " + delay + " ms");
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		setModeInMonitor();
	}
	
	public void changeSyncLabel(int mode) {
		switch(mode){
            case SYNC_SYNC:
                lblCurrentSyncMode.setText("Current sync mode: Synchronous");
                break;
            case SYNC_ASYNC:
                lblCurrentSyncMode.setText("Current sync mode: Asynchronous");
                break;
		}
	}
}
