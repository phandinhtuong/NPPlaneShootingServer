package model;

import java.util.ArrayList;
import java.util.List;

public class RoomList {
	List<Room> roomList = new ArrayList<Room>();

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
	
}
