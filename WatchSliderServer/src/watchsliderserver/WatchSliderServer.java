package watchsliderserver;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class WatchSliderServer {
	public static int PORT_NUMBER = 1010;
	public static String NEXT_CODE = "next";
	public static String PREVIOUS_CODE = "previous";
	
	public static void main(String[] args){
		try{
			ServerSocket hosting_socket = new ServerSocket(WatchSliderServer.PORT_NUMBER);
			Robot robot = new Robot();
			Socket socket;
			InputStream socket_input;
			InputStreamReader socket_input_reader;
			BufferedReader reader;
			String received_message;
			
			System.out.println("Started connection");
			
			while(true){
				socket = hosting_socket.accept();
				socket_input = socket.getInputStream();
				socket_input_reader = new InputStreamReader(socket_input);
				reader = new BufferedReader(socket_input_reader);
				
				System.out.printf("Received connection from %s::%d", socket.getInetAddress().toString(), socket.getPort());
				
				while(socket.isClosed()==false){
					received_message = reader.readLine();

					if (received_message.equals(WatchSliderServer.NEXT_CODE)){
						robot.keyPress(KeyEvent.VK_RIGHT);
					}
					else{
						robot.keyPress(KeyEvent.VK_LEFT);
					}
				}
				
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
}
