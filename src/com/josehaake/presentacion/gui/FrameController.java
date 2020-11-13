package com.josehaake.presentacion.gui;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;

import com.josehaake.presentacion.entities.House;
import com.josehaake.presentacion.entities.Person;

/**
 * 
 * @author JoseDuarte
 *
 */
public class FrameController {

	private Collection<Person> threads = new ArrayList<Person>();
	
	private MyFrame owner;
	
	private boolean isRunning = false;
	
	public FrameController(MyFrame owner) {
		this.owner = owner;
	}
	
	public void start() {
		isRunning = true;
		
		House house = new House();
		
		for (int i = 0; i < 3; i++) {
			
			Person newPerson = new Person(house);
			newPerson.start();
			threads.add(newPerson);
			
		}
	}
	
	public void stop() {
		isRunning = false;
		
		
		for (Person person : threads) {
			try {
				person.join();
			} 
			catch(InterruptedException iex) {}
		}
		
	}
	
	public void restart() {
		stop();
		start();
	}
	
	public void update() {}
	
	public void draw(Graphics g) {
		for (Person person : threads) {
			person.draw(g);
		}
	}
	
	public MyFrame getOwner() {
		return owner;
	}
	
	public boolean isRunning() {
		return isRunning;
	}
}
