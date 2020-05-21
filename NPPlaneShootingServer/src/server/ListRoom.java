package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

import model.RoomList;
import objectByteTransform.Deserialize;
import objectByteTransform.Serialize;

public class ListRoom extends Thread{

	DataOutputStream outToClient;
	DataInputStream inFromClient;
	String sentenceFromClient;
	public ListRoom(DataInputStream in,DataOutputStream out){
		outToClient = out;
		inFromClient = in;
	}
	public void run(){
		try {
			outToClient.writeInt(Serialize.serialize(Server.roomList).length);
			//System.out.println(Serialize.serialize(Server.roomList).length);
			outToClient.write(Serialize.serialize(Server.roomList));
			//System.out.println(Arrays.toString(Serialize.serialize(Server.roomList)));
			while((sentenceFromClient=inFromClient.readLine())!=null){
				System.out.println(sentenceFromClient);
				if (sentenceFromClient.equals("j")){
					JoinRoom joinRoom = new JoinRoom(outToClient,inFromClient);
				}
				break;
			}
//			RoomList newRoomList;
//			newRoomList = Deserialize.deserializeRoomList(Serialize.serialize(Server.roomList));
//			System.out.println(Server.roomList.get(0).getRoomID());
//			System.out.println(newRoomList.get(0).getRoomID());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
