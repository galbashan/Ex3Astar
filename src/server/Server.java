package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server extends Thread{

	int port;
	ServerSocket server;
	boolean stop;
	ClientHandler ch;
	int noc;
	
	public Server(int port,int noc, ClientHandler ch) {
		this.port=port;
		this.stop=false;
		this.ch=ch;
		this.noc=noc;
	}
	
	

	public void run() {
		try {
			server=new ServerSocket(port);
			server.setSoTimeout(1000);
			
			ExecutorService tp = Executors.newFixedThreadPool(noc);
			System.out.println("Server started");
			while(!stop) {
				try {
					//System.out.println("Waiting for client");
					final Socket someClient = server.accept();
					System.out.println("Client connected");
					final BufferedReader inFromClient=new BufferedReader(new InputStreamReader(someClient.getInputStream()));
					final ObjectInputStream obinFromClient = new ObjectInputStream(someClient.getInputStream());
					final PrintWriter out2client = new PrintWriter(/*new OutputStreamWriter*/(someClient.getOutputStream()));
					final ObjectOutputStream out2Server = new ObjectOutputStream(someClient.getOutputStream());
					tp.execute(new Runnable() {
						@Override
						public void run() {
							try {
								ch.handleClient(inFromClient,obinFromClient, out2Server);
							} catch (ClassNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (CloneNotSupportedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}							
							try {
								inFromClient.close();
								out2client.close();
								someClient.close();
								obinFromClient.close();
							} catch (IOException e) {
								e.printStackTrace();
							}							
						}						
					});					
				}catch(SocketTimeoutException e) {							
					{
					}
					}
			}
			server.close();
			System.out.println("Server closed");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		this.stop=true;
	}
	
	
}
