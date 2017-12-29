/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paladins;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

/**
 *
 * @author rokai
 */
public class JavaRobotRescue {
	Robot robot;
	
	public JavaRobotRescue() throws AWTException {
		this.robot = new Robot();
	}
	
	public void typeEnter()
	{
	  robot.keyPress(KeyEvent.VK_ENTER);
	  robot.keyRelease(KeyEvent.VK_ENTER);
	}

}
