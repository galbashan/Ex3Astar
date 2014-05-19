package controller.boot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.UnknownHostException;

import model.Model2048;
import server.ClientHandler;
import server.Server;


public class Run2048Server {

	
	public static void main(String[] args) throws InterruptedException, UnknownHostException, IOException{
		Server s= new Server(5000,1,new ClientHandler() {
			
			
			@Override
			public void handleClient(BufferedReader inFromClient ,ObjectInputStream obinFromClient ,ObjectOutputStream out2client) throws ClassNotFoundException, IOException, CloneNotSupportedException {
				boolean flag = true ;
				Model2048 gm = (Model2048) obinFromClient.readObject();
				Integer c = gm.hintMinimax();
				while (flag==true){
					out2client.flush();
					out2client.writeObject(c.toString());
					out2client.flush();
					System.out.println("SERVER - Command  : " + c);
					flag = false;
				}	
			}

			@Override
			public void handleClient(BufferedReader inFromClient,
					ObjectInputStream ObinFromClient, PrintWriter out2client)
					throws ClassNotFoundException, IOException,
					CloneNotSupportedException {
				// TODO Auto-generated method stub	
			}
			
		});
		
		s.start();
		Thread.sleep(100000);
		s.close();
	
		//c.run();
		
	}
	
}
