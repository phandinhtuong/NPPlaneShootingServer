package main;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

public class ServerUI {

	private JFrame frmPlaneShootingServer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerUI window = new ServerUI();
					window.frmPlaneShootingServer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ServerUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	// display game log
	static JTextArea gameLog = new JTextArea(""); 
	private void initialize() {
		frmPlaneShootingServer = new JFrame();
		//frmPlaneShootingServer.setBackground(Color.WHITE);
		frmPlaneShootingServer.getContentPane().setBackground(Color.WHITE);
		frmPlaneShootingServer.setTitle("Plane Shooting Server");
		frmPlaneShootingServer.setBounds(950, 100, 975, 648);
		frmPlaneShootingServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameLog.setBackground(Color.WHITE);
		
		gameLog.setOpaque(false);
		gameLog.setEditable(false);
		gameLog.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		 DefaultCaret caret = (DefaultCaret)gameLog.getCaret();
		 caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		 
		 //scroll pane to display game log
		JScrollPane scrollPaneGameLog = new JScrollPane();
		scrollPaneGameLog.setViewportView(gameLog);
		scrollPaneGameLog.setBounds(0, 0, 951, 498);
		scrollPaneGameLog.getViewport().setOpaque(false);
		frmPlaneShootingServer.getContentPane().setLayout(null);
		scrollPaneGameLog.setOpaque(false);
		frmPlaneShootingServer.getContentPane().add(scrollPaneGameLog);
		frmPlaneShootingServer.setVisible(true);
	}
	public void displayGameLog(String s) {
		gameLog.setText(gameLog.getText() + s + "\n");
	}

}
