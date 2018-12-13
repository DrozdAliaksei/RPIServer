package Server;
						
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import Controllers.Controller;

public class Server {
	private static final int SERVER_PORT = 5050; 
	
	private ServerSocket server;
	public Server() {}
	
	private static List<Controller> controllers;
	
	public static void main(String[] args) {
		Server server = new Server();
		
		controllers = new ArrayList<>();
		
		Controller first = new Controller(1,"first",1,false);
		Controller second = new Controller(2,"second",2,true);
		Controller third = new Controller(3,"third",3,false);
		
		controllers.add(first);
		controllers.add(second);
		controllers.add(third);
		
		server.runServer();
	
	}
	
	public void runServer() {
		try {
			server = new ServerSocket(SERVER_PORT);
			System.out.println("Server is running");
			while(true) {
				new Client(server.accept()).start();
				System.out.println("hello client");
			}
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
	private class Client extends Thread{
		private Socket socket;
		private ObjectInputStream input;
		private ObjectOutputStream output;
		
		public Client(Socket socket){
			this.socket = socket;
			System.out.println("New clien at"+ socket.getRemoteSocketAddress());
		}
		
		@Override
		public void run() {
			try {
				getStreams();
				output.writeObject("Hello, Welcome to RPI");
				output.flush();
				while(!(input.readObject()).equals("close")){
					if((input.readObject()).equals("getPinsStatus")) {
						output.writeObject(controllers.size());
						for(int i = 0; i<controllers.size();i++) {
							output.writeObject(controllers.get(i).toString());
						}
						
					}
					System.out.println(input.readObject().toString());
				}
			}catch(IOException e) {
				e.printStackTrace();
				System.out.println("Error handling client @ " + socket.getRemoteSocketAddress() + ": " + e.getMessage());
			}catch(ClassNotFoundException ex) {
				ex.printStackTrace();
			}
			finally {
				closeConnection();
                System.out.println("Connection with client @ " + socket.getRemoteSocketAddress() + " closed");
			}
		}
		
		 private void getStreams() throws IOException {
	            output = new ObjectOutputStream(socket.getOutputStream());
	            output.flush();
	            input = new ObjectInputStream(socket.getInputStream());
	        }//end of getStreams
		 private void closeConnection() {
	            try {
	                output.close();
	                input.close();
	                socket.close();	
	            } catch (IOException ex) {
	                ex.printStackTrace();
	            }
	        }//end of closeConnection
	}
}


