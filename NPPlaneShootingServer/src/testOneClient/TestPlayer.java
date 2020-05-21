package testOneClient;

import java.io.Serializable;

public class TestPlayer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String playerID;
	String status;
	
	public TestPlayer(String playerID, String status){
		
	}
	public String getPlayerID() {
		return playerID;
	}
	public void setPlayerID(String playerID) {
		this.playerID = playerID;
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
	
}
