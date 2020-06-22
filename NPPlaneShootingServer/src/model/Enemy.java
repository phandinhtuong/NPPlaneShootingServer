package model;

import java.io.Serializable;

public class Enemy implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int ID;
	int x;
	int y;
	String status;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
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
	public Enemy(int iD, int x, int y, String status) {
		super();
		ID = iD;
		this.x = x;
		this.y = y;
		this.status = status;
	}
	
}
