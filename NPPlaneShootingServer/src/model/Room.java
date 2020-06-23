package model;
import java.io.Serializable;
import java.util.List;
public class Room implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int roomID;
	String roomName;
	int hostPlayerID;
	List<Player> playerList;
	List<Missile> missileList;
	List<Enemy> enemyList;
	String status;
	public int getRoomID() {
		return roomID;
	}
	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public int getHostPlayerID() {
		return hostPlayerID;
	}
	public void setHostPlayerID(int hostPlayerID) {
		this.hostPlayerID = hostPlayerID;
	}
	public List<Player> getPlayerListInRoom() {
		return playerList;
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
	
	public List<Missile> getMissileList() {
		return missileList;
	}
	public void setMissileList(List<Missile> missileList) {
		this.missileList = missileList;
	}
	public void setPlayerListInRoom(List<Player> playerListInRoom) {
		this.playerList = playerListInRoom;
	}
	
	public List<Enemy> getEnemyList() {
		return enemyList;
	}
	public void setEnemyList(List<Enemy> enemyList) {
		this.enemyList = enemyList;
	}
	public Room(int roomID, String roomName, int hostPlayerID,
			List<Player> playerListInRoom, List<Missile> missileList,
			List<Enemy> enemyList, String status) {
		super();
		this.roomID = roomID;
		this.roomName = roomName;
		this.hostPlayerID = hostPlayerID;
		this.playerList = playerListInRoom;
		this.missileList = missileList;
		this.enemyList = enemyList;
		this.status = status;
	}
	
}
