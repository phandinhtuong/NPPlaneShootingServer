package directPlaying.refactorDataStructure;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

import java.awt.Color;

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
	static JTextArea gameLog = new JTextArea(""); // display game log
	private void initialize() {
		frmPlaneShootingServer = new JFrame();
		//frmPlaneShootingServer.setBackground(Color.WHITE);
		frmPlaneShootingServer.getContentPane().setBackground(Color.WHITE);
		frmPlaneShootingServer.setTitle("Plane Shooting Server");
		frmPlaneShootingServer.setBounds(950, 100, 975, 648);
		frmPlaneShootingServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameLog.setBackground(Color.WHITE);
		
		gameLog.setOpaque(false);
		//gameLog.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		// frame.setCursor(frame.getToolkit().createCustomCursor(
		// new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB),
		// new Point(0, 0), "null"));
		// game log
		gameLog.setEditable(false);
		gameLog.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		 DefaultCaret caret = (DefaultCaret)gameLog.getCaret();
		 caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		JScrollPane scrollPaneGameLog = new JScrollPane();
		scrollPaneGameLog.setViewportView(gameLog);
		scrollPaneGameLog.setBounds(0, 0, 951, 498);
		scrollPaneGameLog.getViewport().setOpaque(false);
		frmPlaneShootingServer.getContentPane().setLayout(null);
		scrollPaneGameLog.setOpaque(false);
		frmPlaneShootingServer.getContentPane().add(scrollPaneGameLog);
		frmPlaneShootingServer.setVisible(true);
	}
	public static void displayGameLog(String s) {
		gameLog.setText(gameLog.getText() + s + "\n");
	}

}
