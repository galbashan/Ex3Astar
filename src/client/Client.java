package client;


import model.Model2048;
import java.io.BufferedReader;
import java.io.DataInputStream;
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
	
	public Client()
	{
		
	}
	
	public Client (Model2048 model) 
	{
		this.m = new Model2048(model);
	}
	
	public void run() 
	{
		try {
			int c=connectServer();
			m.setServercommand(c);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public int connectServer() throws UnknownHostException, IOException
	{

		System.out.println("Client started:...");
		Socket myserver= new Socket(InetAddress.getLocalHost(),5000);
		System.out.println("Connected to server");
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(myserver.getInputStream()));
		PrintWriter out2server = new PrintWriter(new OutputStreamWriter(myserver.getOutputStream()));	
	//	ObjectInputStream ois = new ObjectInputStream(myserver.getInputStream());
		DataInputStream disinFromServer  =  new DataInputStream(myserver.getInputStream());
		ObjectOutputStream obout2server = new ObjectOutputStream(myserver.getOutputStream());		 
				
		boolean flag=true;
		String command = "NotSet";
		int intcommand = 0 ;
		Model2048 ng= new Model2048(m);
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		obout2server.writeObject(ng);
		obout2server.flush();
		System.out.println("inFromServer Command B4 : " + command);
		
		command = inFromServer.readLine();
		//intcommand = disinFromServer.readInt();
		System.out.println("inFromServer Command : " + command);
		
		if (command.contains("0"))
			intcommand=0;
		else if (command.contains("1"))
			intcommand=1;
		else if (command.contains("2"))
			intcommand=2;
		else if (command.contains("3"))
			intcommand=3;
		System.out.println("inFromServer INTCommand : " + intcommand);
		while (flag==true)//!(line = inFromUser.readLine()).equals("exit"))
		{
			//System.out.println(line);
			//out2server.println(k);
		
			
			//Object c = obinFromServer.read();
			//System.out.println("ccCli" + (int)c);
			//command=inFromServer.read();
			//System.out.println("inFromServer Command : " + command);
			out2server.flush();
			flag = false;
		}
		//out2server.println(line);
		out2server.flush();
	
		inFromServer.close();
		out2server.close();
		obout2server.close();//
		inFromUser.close();
		myserver.close();
		return intcommand;
		
		
	}
		
		

}
	


