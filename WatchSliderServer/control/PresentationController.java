
package control;

import java.awt.Robot;
import java.awt.event.KeyEvent;


import connection.Message.Type;
import java.awt.AWTException;

public class PresentationController {
	Robot robot;
	
	
	public PresentationController() throws AWTException{	
		this.robot = new Robot();
	}
	
	public void performAction(Type action){
		switch(action){
			case NEXT:
				this.robot.keyPress(KeyEvent.VK_RIGHT);
				break;
				
			case BACK:
				this.robot.keyPress(KeyEvent.VK_LEFT);
				break;
				
			default:
				//send some error
			break;
		}
	}
	
}
