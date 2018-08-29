package connection;

import connection.Message;
import control.PresentationController;

import java.awt.Robot;
import java.awt.event.KeyEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import watchsliderserver.MainFrame;

public class ConnectionHandler {
	private Socket socket;
	private BufferedReader socket_reader;
	private PresentationController controller;
	
	public ConnectionHandler(Socket socket, PresentationController controller){
		try{
			this.setSocket(socket);
			this.setBufferedReader(socket);
			this.setPresentationController(controller);
		}
		catch(IOException ex){
			//Client immediatly closed connection, therefore
			//a stream could not be created and the run()
			//method will return upon call, hence everything
			//is safe and nothing should be done here.
		}
	}
	
	private void setSocket(Socket socket){
		this.socket = socket;
	}
	
	private Socket getSocket(){
		return this.socket;
	}
	
	private void setBufferedReader(Socket socket) throws IOException{
		InputStreamReader stream_reader = new InputStreamReader(socket.getInputStream());
		this.socket_reader = new BufferedReader(stream_reader);
	}
	
	private BufferedReader getBufferedReader(){
			return this.socket_reader;
	}
	
	private void setPresentationController(PresentationController controller){
		this.controller = controller;
	}
	
	private PresentationController getPresentationController(){
		return this.controller;
	}
	
	public void handleUntilClose(){
		Socket socket = this.getSocket();
		BufferedReader socket_reader = this.getBufferedReader();
		String received_message;
		Message.Type message_type;
		PresentationController controller = this.getPresentationController();
		
		boolean is_closed = socket.isClosed();
		
		MainFrame.log(String.format("Connected to %s::%d\n", socket.getInetAddress(),socket.getPort()));
		
		while(!is_closed){
			try{
				received_message = socket_reader.readLine();
			}
			catch(IOException io_ex){
				received_message = null;
			}
			
			is_closed = (received_message==null);
			
			if(!is_closed){
				message_type = Message.getTypeOfMessage(received_message);
				controller.performAction(message_type);
			}
			
		}
		
		MainFrame.log(String.format("Connection from %s:%d has been closed\n", socket.getInetAddress(), socket.getPort()));
		
	}
	
	
	
	
}
