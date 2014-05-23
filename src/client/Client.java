package client;


import model.Model2048;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;



public class Client implements Runnable{
	
	Model2048 m;
	String error;

	
	public Client (Model2048 model) 
	{
		this.m = model;
		error = null;
	}
	
	public void run() 
	{
		try {
			m.setNextMove(connectServer());
		} catch (UnknownHostException e) {
			error = e.getMessage();
			//e.printStackTrace();
		} catch (IOException e) {
			error = e.getMessage();
			//e.printStackTrace();
		} catch (ClassNotFoundException e) {
			error = e.getMessage();
			//e.printStackTrace();
		}
	}
	

	public int[] connectServer() throws UnknownHostException, IOException, ClassNotFoundException
	{

		System.out.println("Client started:...");
		Socket myserver= new Socket(InetAddress.getLocalHost(),5000);
		System.out.println("Connected to server");
		ObjectOutputStream out2server = new ObjectOutputStream(myserver.getOutputStream());
		ObjectInputStream inFromServer = new ObjectInputStream(myserver.getInputStream());

		Model2048 ng= new Model2048(m);
		out2server.writeObject(ng);
		out2server.flush();
		int[] hints = (int[]) inFromServer.readObject();
		
		out2server.flush();
		inFromServer.close();
		out2server.close();
		myserver.close();
		return hints;
		
	}

	public String getError() {
		return error;
	}

}
	


