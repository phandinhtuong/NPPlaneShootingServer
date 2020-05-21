package testMultipleClient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import testOneClient.EnemyModel;
import testOneClient.MissileModel;
import testOneClient.PlaneModel;

public class ServeOneClient extends Thread{
	Socket connectionSocket = null;
	int cNumber;
	DataInputStream inFromClient;
	DataOutputStream outToClient;
	public ServeOneClient(Socket th, int cn,DataInputStream in,
	DataOutputStream out){
		inFromClient = in;
		outToClient = out;
		connectionSocket = th;
		cNumber = cn;
	}
	public void run(){
		int i = 0;
		try {
			while((i=inFromClient.readInt())!=0){
				switch (i) {
				case 1:
					i=inFromClient.readInt();
					byte[] planeModelFromClientInByte = new byte[i];
					inFromClient.read(planeModelFromClientInByte);
					PlaneModel planeModelFromClient = deserializePlaneModel(planeModelFromClientInByte);
					MultipleClientServer.planeModelList[cNumber] = planeModelFromClient;
					System.out.println("client "+cNumber+ " status: "+planeModelFromClient.getStatus()+" : "+planeModelFromClient.getX()+" "+planeModelFromClient.getY());
					break;
				case 2:
					i=inFromClient.readInt();
					byte[] missileModelFromClientInByte = new byte[i];
					inFromClient.read(missileModelFromClientInByte);
					MissileModel missileModelFromClient = deserializeMissileModel(missileModelFromClientInByte);
					System.out.println("client "+cNumber+" missile "+missileModelFromClient.getID()+" "+missileModelFromClient.getX()+" "+missileModelFromClient.getY()+" status "+missileModelFromClient.getStatus());
					MultipleClientServer.missileModelList[cNumber][missileModelFromClient.getID()] = missileModelFromClient;
					break;
				case 3:
					i=inFromClient.readInt();
					byte[] enemyModelFromClientInByte = new byte[i];
					inFromClient.read(enemyModelFromClientInByte);
					EnemyModel enemyModelFromClient = deserializeEnemyModel(enemyModelFromClientInByte);
					MultipleClientServer.enemyModelList[cNumber][enemyModelFromClient.getID()] = enemyModelFromClient;
					System.out.println("enemy "+enemyModelFromClient.getID()+" from client "+cNumber+" status "+enemyModelFromClient.getStatus());
					break;
				case 4:
					byte[] planeModelListInByte = serialize(MultipleClientServer.planeModelList);
					outToClient.writeInt(planeModelListInByte.length);
					outToClient.write(planeModelListInByte);
					byte[] missileModelListInByte = serialize(MultipleClientServer.missileModelList);
					outToClient.writeInt(missileModelListInByte.length);
					outToClient.write(missileModelListInByte);
					byte[] enemyModelListInByte = serialize(MultipleClientServer.enemyModelList);
					outToClient.writeInt(enemyModelListInByte.length);
					outToClient.write(enemyModelListInByte);
					break;
				default:
					break;
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("connection to client "+cNumber+" closed.");
			try {
				connectionSocket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	public static byte[] serialize(PlaneModel[] planeModelList) throws IOException{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
	    ObjectOutputStream os = new ObjectOutputStream(out);
	    os.writeObject(planeModelList);
	    return out.toByteArray();
	}
	public static byte[] serialize(PlaneModel planeModel) throws IOException{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
	    ObjectOutputStream os = new ObjectOutputStream(out);
	    os.writeObject(planeModel);
	    return out.toByteArray();
	}
	public static byte[] serialize(MissileModel missileModel) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(out);
		os.writeObject(missileModel);
		return out.toByteArray();
	}
	public static byte[] serialize(MissileModel[][] missileModelList) throws IOException {
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
	public static byte[] serialize(EnemyModel[][] enemyModelList) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(out);
		os.writeObject(enemyModelList);
		return out.toByteArray();
	}
	public static PlaneModel deserializePlaneModel(byte[] data) throws IOException, ClassNotFoundException{
		ByteArrayInputStream in = new ByteArrayInputStream(data);
	    ObjectInputStream is = new ObjectInputStream(in);
	    return (PlaneModel) is.readObject();
	}
	public static PlaneModel[] deserializePlaneModelList(byte[] data) throws IOException, ClassNotFoundException{
		ByteArrayInputStream in = new ByteArrayInputStream(data);
	    ObjectInputStream is = new ObjectInputStream(in);
	    return (PlaneModel[]) is.readObject();
	}
	public static MissileModel deserializeMissileModel(byte[] data) throws IOException, ClassNotFoundException{
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		ObjectInputStream is = new ObjectInputStream(in);
		return (MissileModel) is.readObject();
	}
	public static MissileModel[][] deserializeMissileModelList(byte[] data) throws IOException, ClassNotFoundException{
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		ObjectInputStream is = new ObjectInputStream(in);
		return (MissileModel[][]) is.readObject();
	}
	public static EnemyModel deserializeEnemyModel(byte[] data)
			throws IOException, ClassNotFoundException {
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		ObjectInputStream is = new ObjectInputStream(in);
		return (EnemyModel) is.readObject();
	}

}
