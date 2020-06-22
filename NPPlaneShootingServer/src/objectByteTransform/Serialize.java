package objectByteTransform;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import main.Main;
import model.Player;
import model.Room;

public class Serialize {
	public static byte[] serialize(List<Player> playerList) throws IOException{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
	    ObjectOutputStream os = new ObjectOutputStream(out);
	    os.writeObject(playerList);
	    return out.toByteArray();
	}
	public static byte[] serialize(Room room) throws IOException{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
	    ObjectOutputStream os = new ObjectOutputStream(out);
	    os.writeObject(room);
	    return out.toByteArray();
	}
	public static byte[] serialize(Player player) throws IOException{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
	    ObjectOutputStream os = new ObjectOutputStream(out);
	    os.writeObject(player);
	    return out.toByteArray();
	}
	public static byte[] serialize(Player[] planeModelList)
			throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(out);
		os.writeObject(planeModelList);
		return out.toByteArray();
	}

	public static byte[] serializePlaneModelList(int roomIDInRoomList){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ObjectOutputStream os = new ObjectOutputStream(out);
			synchronized (Main.modelRoomList.get(roomIDInRoomList).getPlayerListInRoom()) {
				os.writeObject(Main.modelRoomList.get(roomIDInRoomList).getPlayerListInRoom());
			}
			return out.toByteArray();
		} catch (IOException e) {
			return null;
		}
	}
	public static byte[] serializeMissileModelList(int roomIDInRoomList){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ObjectOutputStream os = new ObjectOutputStream(out);
			synchronized (Main.modelRoomList.get(roomIDInRoomList).getMissileList()) {
				os.writeObject(Main.modelRoomList.get(roomIDInRoomList).getMissileList());
			}
			return out.toByteArray();
		} catch (IOException e) {
			return null;
		}
	}
	public static byte[] serializeEnemyModelList(int roomIDInRoomList){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ObjectOutputStream os = new ObjectOutputStream(out);
			synchronized (Main.modelRoomList.get(roomIDInRoomList).getEnemyList()) {
				os.writeObject(Main.modelRoomList.get(roomIDInRoomList).getEnemyList());
			}
			return out.toByteArray();
		} catch (IOException e) {
			return null;
		}
	}
	public static byte[] serializeRoomModelList(){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ObjectOutputStream os = new ObjectOutputStream(out);
			synchronized (Main.modelRoomList) {
				os.writeObject(Main.modelRoomList);
			}
			return out.toByteArray();
		} catch (IOException e) {
			return null;
		}
	}
}
