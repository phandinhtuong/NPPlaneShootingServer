package directPlaying.testOneClient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import model.Player;
import server.NewClientHome;

public class OneClientServer {
	public static void main(String[] args) throws Exception {
		int tcp_port = 6789;
		int cNumber = 1;
		String clientSentence;
		ServerSocket welcomeSocket = new ServerSocket(tcp_port);
		while(true) {
			System.out.println("TCP Server port: " + tcp_port);
			Socket connectionSocket = welcomeSocket.accept();
			System.out.println("client connect from: " + connectionSocket.getInetAddress().getHostAddress() + ":" + connectionSocket.getPort());
			DataInputStream inFromClient = new DataInputStream(connectionSocket.getInputStream());
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			int i = 0;
			while((i=inFromClient.readInt())!=0){
				switch (i) {
				case 1:
					i=inFromClient.readInt();
					byte[] planeModelFromClientInByte = new byte[i];
					inFromClient.read(planeModelFromClientInByte);
					PlaneModel planeModelFromClient = deserializePlaneModel(planeModelFromClientInByte);
					System.out.println(planeModelFromClient.getX()+" "+planeModelFromClient.getY());
					byte[] planeModelInByte = serialize(planeModelFromClient);
					outToClient.writeInt(planeModelInByte.length);
					outToClient.write(planeModelInByte);
					
					
					break;
				case 2:
					i=inFromClient.readInt();
					byte[] missileModelFromClientInByte = new byte[i];
					inFromClient.read(missileModelFromClientInByte);
					MissileModel missileModelFromClient = deserializeMissileModel(missileModelFromClientInByte);
					System.out.println("missile "+missileModelFromClient.getID()+" "+missileModelFromClient.getX()+" "+missileModelFromClient.getY());
					byte[] missileModelInByte = serialize(missileModelFromClient);
					outToClient.writeInt(missileModelInByte.length);
					outToClient.write(missileModelInByte);
					break;
				case 3:
					i=inFromClient.readInt();
					byte[] enemyModelFromClientInByte = new byte[i];
					inFromClient.read(enemyModelFromClientInByte);
					EnemyModel enemyModelFromClient = deserializeEnemyModel(enemyModelFromClientInByte);
					System.out.println("enemy "+enemyModelFromClient.getID());
					byte[] enemyModelInByte = serialize(enemyModelFromClient);
					outToClient.writeInt(enemyModelInByte.length);
					outToClient.write(enemyModelInByte);
					break;
				default:
					break;
				}
			}
//			while((i=inFromClient.readInt())!=0){
//				byte[] planeModelFromClientInByte = new byte[i];
//				inFromClient.read(planeModelFromClientInByte);
//				PlaneModel planeModelFromClient = deserialize(planeModelFromClientInByte);
//				System.out.println(planeModelFromClient.getX()+" "+planeModelFromClient.getY());
//				byte[] planeModelInByte = serialize(planeModelFromClient);
//				outToClient.writeInt(planeModelInByte.length);
//				outToClient.write(planeModelInByte);
//				
//			}
			//clientSentence = inFromClient.readLine();
			
			//System.out.println(clientSentence);
//			if (clientSentence.equals("c")){
//				NewClientHome newPlayer = new NewClientHome(connectionSocket,cNumber++);
//				newPlayer.start();
//			}
			//String playerID = connectionSocket.getInetAddress().getHostAddress() + ":" + connectionSocket.getPort();
			//TestPlayer testPlayer = new TestPlayer(playerID,"idle");
		}
	}
	public static byte[] serialize(PlaneModel plane) throws IOException{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
	    ObjectOutputStream os = new ObjectOutputStream(out);
	    os.writeObject(plane);
	    return out.toByteArray();
	}
	public static byte[] serialize(MissileModel missileModel) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(out);
		os.writeObject(missileModel);
		return out.toByteArray();
	}
	public static byte[] serialize(EnemyModel enemyModel) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(out);
		os.writeObject(enemyModel);
		return out.toByteArray();
	}
	public static PlaneModel deserializePlaneModel(byte[] data) throws IOException, ClassNotFoundException{
		ByteArrayInputStream in = new ByteArrayInputStream(data);
	    ObjectInputStream is = new ObjectInputStream(in);
	    return (PlaneModel) is.readObject();
	}
	public static MissileModel deserializeMissileModel(byte[] data) throws IOException, ClassNotFoundException{
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		ObjectInputStream is = new ObjectInputStream(in);
		return (MissileModel) is.readObject();
	}
	public static EnemyModel deserializeEnemyModel(byte[] data)
			throws IOException, ClassNotFoundException {
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		ObjectInputStream is = new ObjectInputStream(in);
		return (EnemyModel) is.readObject();
	}
}
