package model;

import java.io.Serializable;
import java.util.List;

public class Room  implements Serializable{
	String roomID;
	Player hostPlayer;
	List<Player> playerList;
	
	public boolean add(Player arg0) {
		return playerList.add(arg0);
	}
	public Room(String roomID,Player hostPlayer){
		this.hostPlayer = hostPlayer;
		this.roomID = roomID;
	}
	public Player getHostPlayer() {
		return hostPlayer;
	}
	public void setHostPlayer(Player hostPlayer) {
		this.hostPlayer = hostPlayer;
	}
	public List<Player> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(List<Player> playerList) {
		this.playerList = playerList;
	}
	public String getRoomID() {
		return roomID;
	}
	public void setRoomID(String roomID) {
		this.roomID = roomID;
	}

	
	
}
