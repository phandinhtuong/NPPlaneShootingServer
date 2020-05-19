package objectByteTransform;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import model.RoomList;

public class Serialize {
	public static byte[] serialize(RoomList roomList) throws IOException{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
	    ObjectOutputStream os = new ObjectOutputStream(out);
	    os.writeObject(roomList);
		return out.toByteArray();
		
	}
}
