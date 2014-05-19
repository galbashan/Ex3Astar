package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

public interface ClientHandler {
	
	//public void handleClient(BufferedReader inFromClient ,PrintWriter out2client);

	public	void handleClient(BufferedReader inFromClient, ObjectInputStream ObinFromClient, PrintWriter out2client) throws ClassNotFoundException, IOException, CloneNotSupportedException;
	
	public	void handleClient(BufferedReader inFromClient, ObjectInputStream ObinFromClient, ObjectOutputStream out2client) throws ClassNotFoundException, IOException, CloneNotSupportedException;

	

}
