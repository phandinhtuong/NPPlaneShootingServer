package testOneClient;

import java.io.Serializable;

import javax.swing.JLabel;

public class MissileModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int playerID;
	int ID;
	int x;
	int y;
	String status;
	JLabel lblMissile;
	public MissileModel(int playerID, int ID, int x, int y, String status){
		this.playerID = playerID;
		this.ID = ID;
		this.x = x;
		this.y = y;
		this.status = status;
	}
	public int getPlayerID() {
		return playerID;
	}
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public JLabel getLblMissile() {
		return lblMissile;
	}
	public void setLblMissile(JLabel lblMissile) {
		this.lblMissile = lblMissile;
	}

}
