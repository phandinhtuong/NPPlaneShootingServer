package testOneClient;

import java.io.Serializable;

public class EnemyModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int ID;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	int x;
	public EnemyModel(int x){
		this.x = x;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
}
