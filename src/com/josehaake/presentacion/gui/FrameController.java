package com.josehaake.presentacion.gui;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;

import com.josehaake.presentacion.entities.Person;
import com.josehaake.presentacion.scenes.House;

/**
 * 
 * @author JoseDuarte
 *
 */
public class FrameController {

	private House houseThread;
	private MyFrame owner;
	
	private boolean isRunning = false;
	
	public FrameController(MyFrame owner) {
		this.owner = owner;
	}
	
	public void start() {
		isRunning = true;
		
		houseThread = new House();
		
		for (int i = 0; i < 3; i++) {
			Person newPerson = new Person(houseThread);
			houseThread.enter(newPerson);
		}
		
		houseThread.start();
	}
	
	public void stop() {
		isRunning = false;
		houseThread.stop();
	}
	
	public void restart() {
		stop();
		start();
	}
	
	public void update() {}
	
	public void draw(Graphics g) {
		houseThread.draw(g);
	}
	
	public MyFrame getOwner() {
		return owner;
	}
	
	public boolean isRunning() {
		return isRunning;
	}
}
