package client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame implements ActionListener {
    private ClientStateMonitor clientStateMonitor;
	public final static int
        SYNC_AUTO = -1,
        SYNC_SYNC = 1,
        SYNC_ASYNC = 0;


	private JPanel 		contentPane,
						panel1,
						panel2,
						panelActive;
	private ImageIcon 	image1,
						image2;
	private JLabel 		lbImage1,
						lbImage2,
						lbActive,
						delayCamera1,
						delayCamera2,
						lblCurrentSyncMode;
	private TitledBorder borderGrayCam1,
	 					borderRedCam1,
	 					borderGrayCam2,
	 					borderRedCam2;
	private JRadioButton rbIdle, 
						rbAuto, 
						rbMovie, 
						rbMovieAuto, 
						rbSync, 
						rbAsync;


	/**
	 * Create the frame.
	 */
	public GUI(String serverURL1, String serverURL2, ClientStateMonitor clientStateMonitor) {
        this.clientStateMonitor = clientStateMonitor;

		//GUI Initialization
		setTitle("Video Surveillance");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 861, 593);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		borderGrayCam1 = new TitledBorder(new LineBorder(new Color(184, 207,
				229)), serverURL1, TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(51, 51, 51));
		borderRedCam1 = new TitledBorder(new LineBorder(new Color(255, 0, 0)),
                serverURL1, TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(51, 51, 51));
		borderGrayCam2 = new TitledBorder(new LineBorder(new Color(184, 207,
				229)), serverURL2, TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(51, 51, 51));
		borderRedCam2 = new TitledBorder(new LineBorder(new Color(255, 0, 0)),
                serverURL2, TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(51, 51, 51));

		image1 = new ImageIcon();
		image2 = new ImageIcon();

		// Starting up GUI ----------------------------------------------------------------------------------------------

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
		//End of GUI setup-----------------------------------------------------------------------------------------------------
		
		//Starting up client system


	}

	public void refreshCameraImage(byte[] JPEGData, int cameraIndex) {
		if (cameraIndex == 1) {
			image1.setImage(getToolkit().createImage(JPEGData));
			image1.paintIcon(this, lbImage1.getGraphics(), 0, 0);
		} else if (cameraIndex == 2) {
			image2.setImage(getToolkit().createImage(JPEGData));
			image2.paintIcon(this, lbImage2.getGraphics(), 0, 0);
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
        switch (cameraIndex) {
            case 1:
                delayCamera1.setText("Delay: " + delay + " ms");
                break;
            case 2:
                delayCamera2.setText("Delay: " + delay + " ms");
                break;
        }
	}
	
	public void actionPerformed(ActionEvent e) {
        if (rbMovie.isSelected()) {
            clientStateMonitor.setMode(ClientStateMonitor.MOVIE_FORCED);
            changeMovieMode("client");
        } else if (rbIdle.isSelected()) {
            clientStateMonitor.setMode(ClientStateMonitor.IDLE_FORCED);
            changeMovieMode("");
        } else {
            clientStateMonitor.unsetForced();
        }
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
	public void changeMovieMode(String caller){
		if (!caller.equals("")) {
            panel1.setBorder(borderRedCam1);
            panel2.setBorder(borderRedCam2);
            lbActive.setText("Movie mode triggered by " + caller + ".");
            panelActive.setVisible(true);
        } else {
            panel1.setBorder(borderGrayCam1);
            panel2.setBorder(borderGrayCam2);
            panelActive.setVisible(false);
		}
	}
}
