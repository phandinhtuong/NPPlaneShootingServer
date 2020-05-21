package testOneClient;

import java.io.Serializable;

public class MissileModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int ID;
	int x;
	int y;
	public MissileModel(int ID, int x, int y){
		this.ID = ID;
		this.x = x;
		this.y = y;
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

}
