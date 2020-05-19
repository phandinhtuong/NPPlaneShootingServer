package model;

import java.io.Serializable;

public class Player  implements Serializable{
	String playerID;
	
	public Player(String playerID){
		this.playerID = playerID;
	}
	public String getPlayerID() {
		return playerID;
	}

	public void setPlayerID(String playerID) {
		this.playerID = playerID;
	}
}
