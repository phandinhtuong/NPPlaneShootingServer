package model;

import java.io.Serializable;

public class Player  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String playerID;
	String status;
	public Player(String playerID){
		this.playerID = playerID;
		this.status = "idle";
	}
	public void updatePlayerStatus(String status){
		this.status=status;
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
