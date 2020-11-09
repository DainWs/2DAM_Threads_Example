package com.josehaake.presentacion.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * 
 * @author JoseDuarte
 *
 */
public class FrameController {

	//private Collection<Person> threads = new ArrayList<Person>();
	
	private MyFrame owner;
	
	private boolean isRunning = false;
	
	public FrameController(MyFrame owner) {
		this.owner = owner;
	}
	
	public void start() {
		isRunning = true;
		
		/*
		House house = new House();
		*/
		
		for (int i = 0; i < 3; i++) {
			/*
			Person newPerson = new Person(house, this);
			newPerson.start();
			threads.add(new Person());
			*/
		}
	}
	
	public void stop() {
		isRunning = false;
		
		/*
		for (Person person : threads) {
			try {
				person.join();
			} 
			catch(InterruptedException iex) {}
		}
		*/
	}
	
	public void restart() {
		stop();
		start();
	}
	
	public void update() {
		//draw();
	}
	
	public void draw() {
		Graphics g = owner.getGraphics();
		
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, owner.getWidth(), owner.getHeight());
		/*
		for (Person person : threads) {
			person.draw();
		}
		*/
	}
	
	public boolean isRunning() {
		return isRunning;
	}
}
