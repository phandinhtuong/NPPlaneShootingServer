package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Room  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String roomID;
	Player hostPlayer;
	List<Player> playerList;
	
	public boolean add(Player arg0) {
		return playerList.add(arg0);
	}
	public void updatePlayer(Player player){
		//TODO
		//System.out.println(this.playerList.indexOf(player));
		this.playerList.get(this.playerList.indexOf(player)).setStatus(player.getStatus());
	}
	
	public Room(String roomID,Player hostPlayer){
		this.playerList = new ArrayList<Player>();
		this.playerList.add(hostPlayer);
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
