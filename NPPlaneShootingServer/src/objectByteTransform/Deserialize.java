package objectByteTransform;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import model.Player;
import model.Room;
import model.RoomList;

public class Deserialize {
	public static RoomList deserializeRoomList(byte[] data) throws IOException, ClassNotFoundException{
		ByteArrayInputStream in = new ByteArrayInputStream(data);
	    ObjectInputStream is = new ObjectInputStream(in);
	    return (RoomList) is.readObject();
	}
	public static String deserializeString(byte[] data) throws IOException, ClassNotFoundException{
		ByteArrayInputStream in = new ByteArrayInputStream(data);
	    ObjectInputStream is = new ObjectInputStream(in);
	    return (String) is.readObject();
	}
	public static Player deserializePlayer(byte[] data) throws IOException, ClassNotFoundException{
		ByteArrayInputStream in = new ByteArrayInputStream(data);
	    ObjectInputStream is = new ObjectInputStream(in);
	    return (Player) is.readObject();
	}
	public static Room deserializeRoom(byte[] data) throws ClassNotFoundException, IOException{
		ByteArrayInputStream in = new ByteArrayInputStream(data);
	    ObjectInputStream is = new ObjectInputStream(in);
	    return (Room) is.readObject();
	}
}
