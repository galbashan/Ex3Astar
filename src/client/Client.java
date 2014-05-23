package client;


import model.Model2048;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;



public class Client implements Runnable{
	
	Model2048 m;
	String error;
	int loop;

	
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
			//System.out.println(error);
			//e.printStackTrace();
		} catch (IOException e) {
			error = e.getMessage();
			//System.out.println(error);
			//e.printStackTrace();
		}
	}
	

	public int connectServer() throws UnknownHostException, IOException
	{

		System.out.println("Client started:...");
		Socket myserver= new Socket(InetAddress.getLocalHost(),5002);
		System.out.println("Connected to server");
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(myserver.getInputStream()));
		PrintWriter out2server = new PrintWriter(new OutputStreamWriter(myserver.getOutputStream()));	
		ObjectOutputStream obout2server = new ObjectOutputStream(myserver.getOutputStream());
		String command = "NotSet";
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		Model2048 ng= new Model2048(m);
		
		boolean flag=true;
		
		int intcommand = 0 ;
		obout2server.writeObject(ng);
		obout2server.flush();
		command = inFromServer.readLine();
			
		if (command.contains("0"))
			intcommand=0;
		else if (command.contains("1"))
			intcommand=1;
		else if (command.contains("2"))
			intcommand=2;
		else if (command.contains("3"))
			intcommand=3;
		while (flag==true)
		{
			out2server.flush();
			flag = false;
		}
			
		out2server.flush();
		inFromServer.close();
		out2server.close();
		obout2server.close();
		inFromUser.close();
		myserver.close();
		return intcommand;
		
	}

	public String getError() {
		return error;
	}

}
	


