package objectByteTransform;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import model.Player;
import model.Room;
import model.RoomList;

public class Serialize {
	public static byte[] serialize(RoomList roomList) throws IOException{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
	    ObjectOutputStream os = new ObjectOutputStream(out);
	    os.writeObject(roomList);
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
}
