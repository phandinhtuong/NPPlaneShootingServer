package testOneClient;
import java.io.Serializable;

public class PlaneModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int x;
	int y;
	public PlaneModel(int x, int y){
		this.x = x;
		this.y = y;
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
