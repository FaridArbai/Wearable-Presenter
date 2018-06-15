package watchsliderserver;
import connection.ConnectionHandler;
import control.PresentationController;

import java.awt.AWTException;
import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

public class WatchSliderServer {
	public static int PORT_NUMBER = 1010;
	public static String NEXT_CODE = "NEXT";
	public static String PREVIOUS_CODE = "BACK";
	
	public static void main(String[] args){
		ServerSocket hosting_socket;
		PresentationController controller;
		Socket client_socket;
		ConnectionHandler client_handler;
		
		try{
			controller = new PresentationController();
			
			try{
				hosting_socket = new ServerSocket(WatchSliderServer.PORT_NUMBER);
				System.out.printf("Started listening\n");
				
				while(true){
					client_socket = hosting_socket.accept();
					client_handler = new ConnectionHandler(client_socket, controller);
					client_handler.handleUntilClose();
				}
			}
			catch(IOException host_ex){
				System.out.printf("Unable to host a server in your system.\n"
				+ "Please run this software in administrator mode.\n%s", host_ex.getMessage());
			}
		}
		catch(AWTException awt_ex){
			System.out.printf("Unable to take control of your system.\n"
				+ "Please run this software in administrator mode.\n%s", awt_ex.getMessage());
		}
		
	}
	
}
