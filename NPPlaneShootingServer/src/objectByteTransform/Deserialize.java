package objectByteTransform;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import model.Missile;
import model.Player;
import model.Room;

public class Deserialize {

	public static String deserializeString(byte[] data) throws IOException,
			ClassNotFoundException {
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		ObjectInputStream is = new ObjectInputStream(in);
		return (String) is.readObject();
	}

	public static Player deserializePlayer(byte[] data) throws IOException,
			ClassNotFoundException {
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		ObjectInputStream is = new ObjectInputStream(in);
		return (Player) is.readObject();
	}

	public static Room deserializeRoom(byte[] data)
			throws ClassNotFoundException, IOException {
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		ObjectInputStream is = new ObjectInputStream(in);
		return (Room) is.readObject();
	}

	public static Player deserializePlaneModel(byte[] data)
			throws IOException, ClassNotFoundException {
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		ObjectInputStream is = new ObjectInputStream(in);
		return (Player) is.readObject();
	}


	public static Missile deserializeMissileModel(byte[] data)
			throws IOException, ClassNotFoundException {
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		ObjectInputStream is = new ObjectInputStream(in);
		return (Missile) is.readObject();
	}


//	public static ArrayList<PlaneModel> deserializePlaneModelArrayList(
//			byte[] data) throws IOException, ClassNotFoundException {
//		ByteArrayInputStream in = new ByteArrayInputStream(data);
//		ObjectInputStream is = new ObjectInputStream(in);
//		return (ArrayList<PlaneModel>) is.readObject();
//
//	}
}
