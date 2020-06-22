package model;

import java.io.Serializable;

public class Missile implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int playerID;
	int missileID;
	int x;
	int y;
	String status;
	public int getPlayerID() {
		return playerID;
	}
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	public int getMissileID() {
		return missileID;
	}
	public void setMissileID(int missileID) {
		this.missileID = missileID;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Missile(int playerID, int missileID, int x, int y, String status) {
		super();
		this.playerID = playerID;
		this.missileID = missileID;
		this.x = x;
		this.y = y;
		this.status = status;
	}
	
}
