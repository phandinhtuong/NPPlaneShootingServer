package objectByteTransform;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import testOneClient.EnemyModel;
import testOneClient.MissileModel;
import testOneClient.PlaneModel;
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
	public static byte[] serialize(PlaneModel[] planeModelList)
			throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(out);
		os.writeObject(planeModelList);
		return out.toByteArray();
	}

	public static byte[] serialize(PlaneModel planeModel) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(out);
		os.writeObject(planeModel);
		return out.toByteArray();
	}

	public static byte[] serialize(MissileModel missileModel)
			throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(out);
		os.writeObject(missileModel);
		return out.toByteArray();
	}

	public static byte[] serialize(MissileModel[][] missileModelList)
			throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(out);
		os.writeObject(missileModelList);
		return out.toByteArray();
	}

	public static byte[] serialize(EnemyModel enemyModel) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(out);
		os.writeObject(enemyModel);
		return out.toByteArray();
	}

	public static byte[] serialize(EnemyModel[][] enemyModelList)
			throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(out);
		os.writeObject(enemyModelList);
		return out.toByteArray();
	}
	public static byte[] serialize(ArrayList<PlaneModel> modelPlaneList){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ObjectOutputStream os = new ObjectOutputStream(out);
			os.writeObject(modelPlaneList);
			return out.toByteArray();
		} catch (IOException e) {
//			Client.displayGameLog(e.getMessage());
			return null;
		}
	}
}
