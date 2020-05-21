package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import model.Player;
import objectByteTransform.Deserialize;

public class JoinRoom extends Thread{
	DataOutputStream outToClient;
	DataInputStream inFromClient;
	String sentenceFromClient;
	byte[] playerInByte;
	int i = 0;
	public JoinRoom(DataOutputStream out,DataInputStream in){
		outToClient = out;
		inFromClient = in;
	}
	public void run(){
		try {
			while((sentenceFromClient=inFromClient.readLine())!=null){
				System.out.println(sentenceFromClient);
				i = inFromClient.readInt();
				playerInByte = new byte[i];
				inFromClient.read(playerInByte);
				break;
			}
			Player player = Deserialize.deserializePlayer(playerInByte);
			Server.roomList.getRoomByID(sentenceFromClient).add(player);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
