package testOneClient;

import java.io.Serializable;

public class PlaneModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int ID;
	String status;
	int x;
	int y;
	public PlaneModel(int ID, int x, int y, String status){
		this.ID = ID;
		this.status = status;
		this.x = x;
		this.y = y;
	}
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
