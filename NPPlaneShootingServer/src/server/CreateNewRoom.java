package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import objectByteTransform.Deserialize;
import objectByteTransform.Serialize;
import model.Player;
import model.Room;

public class CreateNewRoom extends Thread{
	Socket connectionSocket = null;
	int cNumber;
	DataInputStream inFromClient;
	DataOutputStream outToClient;
	Player player;
	public CreateNewRoom(Player p,Socket th,DataInputStream in,DataOutputStream out, int cn){
		connectionSocket = th;
		cNumber = cn;
		inFromClient = in;
		outToClient = out;
		player = p;
	}
	public void run(){
		try {
			String newRoomID = connectionSocket.getInetAddress().getHostAddress() + ":" + connectionSocket.getPort();
			//DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			outToClient.writeBytes(newRoomID+'\n');
			Room newRoom = new Room(newRoomID, player);
			Server.roomList.add(newRoom);
			int i=0;
			byte[] byteInFromClient = null;
			byte[] byteOutToClient = null;
			Player player = null;
			Room room = null;
			String sentenceFormClient = null;
			while((sentenceFormClient=inFromClient.readLine())!=null){
				
				System.out.println(sentenceFormClient);
				
				break;
			}
			while((i=inFromClient.readInt())!=0){
				byteInFromClient = new byte[i];
				inFromClient.read(byteInFromClient);
				player = Deserialize.deserializePlayer(byteInFromClient);
				if (newRoom.getPlayerList().contains(player)){
					newRoom.updatePlayer(player);
				}
				byteOutToClient = Serialize.serialize(newRoom);
				outToClient.writeInt(byteOutToClient.length);
				outToClient.write(byteOutToClient);
			}
		} catch (IOException e) {
			System.out.println("client "+player.getPlayerID()+" disconnected.");
			try {
				inFromClient.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("client "+player.getPlayerID()+" disconnected.");
			e.printStackTrace();
		}
	}
}
