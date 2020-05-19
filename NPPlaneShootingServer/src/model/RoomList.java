package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class RoomList implements Serializable{
	List<Room> roomList = new ArrayList<Room>();

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
