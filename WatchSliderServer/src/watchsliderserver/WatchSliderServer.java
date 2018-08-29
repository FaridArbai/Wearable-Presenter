package watchsliderserver;
import connection.ConnectionHandler;
import control.PresentationController;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

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
			SwingUtilities.invokeAndWait(new Runnable(){
				public void run(){
					MainFrame.init();
				}
			});
		}catch(Exception ex){}
		
		try{
			controller = new PresentationController();
			
			try{
				hosting_socket = new ServerSocket(WatchSliderServer.PORT_NUMBER);
				MainFrame.log("Started listening");
				
				String ip_info = "";
				
				try {
					InetAddress localhost = InetAddress.getLocalHost();
					InetAddress[] allMyIps = InetAddress.getAllByName(localhost.getCanonicalHostName());
					if ((allMyIps != null) && (allMyIps.length >= 1)) {
						ip_info += "Detected IP addresses : [";
						
						for (int i = 0; i < allMyIps.length; i++) {
							 ip_info += (" " + allMyIps[i] + " ");
						}
						
						ip_info += "]";
					}
					else{
						ip_info += "Detected 0 IP addresses, are you connected to the Wi-Fi?";
					}
				} catch (UnknownHostException e) {
					ip_info += "Error detecting local IP addresses. Please run this program in admin mode and "
						+ "make sure you're connected to the Wi-Fi";
				}
				
				MainFrame.log(ip_info);
				
				
				while(true){
					client_socket = hosting_socket.accept();
					client_handler = new ConnectionHandler(client_socket, controller);
					client_handler.handleUntilClose();
				}
			}
			catch(IOException host_ex){
				MainFrame.log(String.format("Unable to host a server in your system.\n"
				+ "Please run this software in administrator mode.\n%s", host_ex.getMessage()));
			}
		}
		catch(AWTException awt_ex){
			MainFrame.log(String.format("Unable to take control of your system.\n"
				+ "Please run this software in administrator mode.\n%s", awt_ex.getMessage()));
		}
		
	}
	
}
