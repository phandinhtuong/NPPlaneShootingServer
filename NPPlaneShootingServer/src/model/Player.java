package model;

import java.io.Serializable;

public class Player implements Serializable{ //Player is also a plane
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int ID;
	String status;
	int x;
	int y;
	int score;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public Player(int iD, String status, int x, int y, int score) {
		super();
		ID = iD;
		this.status = status;
		this.x = x;
		this.y = y;
		this.score = score;
	}
	public void addOneScore(){
		this.score++;
	}
	
}
