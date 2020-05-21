package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class RoomList implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<Room> roomList = new ArrayList<Room>();
	
	public Room getRoomByID(String roomID){
		int i = 0;
		for (i=0;i<roomList.size();i++){
			if (roomList.get(i).getRoomID().equals(roomID)) return roomList.get(i);
		}
		return null;
	}
	public Room get(int arg0) {
		return roomList.get(arg0);
	}

	public List<Room> getRoomList() {
		return roomList;
	}

	public void setRoomList(List<Room> roomList) {
		this.roomList = roomList;
	}

	public boolean add(Room arg0) {
		System.out.println("Room "+arg0.getRoomID()+" added.");
		return roomList.add(arg0);
	}

	public void forEach(Consumer<? super Room> arg0) {
		roomList.forEach(arg0);
	}
	
}
