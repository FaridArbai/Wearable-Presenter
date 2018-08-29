
package control;

import java.awt.Robot;
import java.awt.event.KeyEvent;


import connection.Message.Type;
import java.awt.AWTException;
import watchsliderserver.MainFrame;

public class PresentationController {
	Robot robot;
	
	
	public PresentationController() throws AWTException{	
		this.robot = new Robot();
	}
	
	public void performAction(Type action){
		if(action!=null){
			switch(action){
				case NEXT:{
					this.robot.keyPress(KeyEvent.VK_RIGHT);
					MainFrame.log("Next slide command received");
					break;
				}
				case BACK:{
					this.robot.keyPress(KeyEvent.VK_LEFT);
					MainFrame.log("Previous slide command received");
					break;
				}
				default:{
					MainFrame.log("Received unknown command");
				}
				break;
			}
		}
		else{
			MainFrame.log("Received unknown command");
		}
	}
	
}
