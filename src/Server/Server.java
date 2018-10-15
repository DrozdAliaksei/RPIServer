package Server;
						
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class Server {
	private static final int SERVER_PORT = 5050; 
	
	private ServerSocket server;
	public Server() {}
	
	public static void main(String[] args) {
		Server server = new Server();
		server.runServer();
	
	}
	
	public void runServer() {
		try {
			server = new ServerSocket(SERVER_PORT);
			System.out.println("Server is running");
			while(true) {
				new Controller(server.accept()).start();
				System.out.println("hello client");
			}
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
	private class Controller extends Thread{
		private Socket socket;
		private ObjectInputStream input;
		private ObjectOutputStream output;
		private String in;
		
		public Controller(Socket socket){
			this.socket = socket;
			System.out.println("New clien at"+ socket.getRemoteSocketAddress());
		}
		
		@Override
		public void run() {
			try {
				getStreams();
				output.writeObject("Hello, Welcome to RPI");
				output.flush();
				while(!(in = (String) input.readObject()).equals("close")){
						
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


