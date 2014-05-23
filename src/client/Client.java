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

	
	public Client (Model2048 model, int loop) 
	{
		this.m = model;
		this.loop = loop;
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
		}
	}
	

	public int[] connectServer() throws UnknownHostException, IOException
	{

		System.out.println("Client started:...");
		Socket myserver= new Socket(InetAddress.getLocalHost(),5000);
		System.out.println("Connected to server");
		
		int[] hints = new int[loop];
		for (int i=0; i<loop; i++)
		{
			System.out.println("test");
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(myserver.getInputStream()));
			PrintWriter out2server = new PrintWriter(new OutputStreamWriter(myserver.getOutputStream()));	
			ObjectOutputStream obout2server = new ObjectOutputStream(myserver.getOutputStream());
			String strcommand = "NotSet";
			Model2048 ng= new Model2048(m);
			boolean flag=true;
			int intcommand = 0 ;
			obout2server.writeObject(ng);
			obout2server.flush();
			strcommand = inFromServer.readLine();
				
			if (strcommand.contains("0"))
				intcommand=0;
			else if (strcommand.contains("1"))
				intcommand=1;
			else if (strcommand.contains("2"))
				intcommand=2;
			else if (strcommand.contains("3"))
				intcommand=3;
			while (flag==true)
			{
				out2server.flush();
				flag = false;
			}
			hints[i] = intcommand;
			out2server.flush();
			inFromServer.close();
			out2server.close();
			obout2server.close();
		}
		
		myserver.close();
		return hints;
		
	}

	public String getError() {
		return error;
	}

}
	


