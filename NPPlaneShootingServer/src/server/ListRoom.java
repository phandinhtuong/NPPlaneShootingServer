package server;

import java.io.DataOutputStream;
import java.io.IOException;

import objectByteTransform.Serialize;

public class ListRoom {

	DataOutputStream outToClient;
	public ListRoom(DataOutputStream out){
		outToClient = out;
		
	}
	public void run() throws IOException{
		outToClient.write(Serialize.serialize(Server.roomList));
		//outToClient.
	}
}
