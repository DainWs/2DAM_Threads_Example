package com.josehaake.presentacion.scenes;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;

import com.josehaake.presentacion.entities.Person;

public class House {
        //aa
	private Collection<Person> persons = new ArrayList<Person>();
	private Room[] rooms;
	private Hall hall;
	
	private boolean running = false;
	
	public House() {
		hall = new Hall();
		rooms = new Room[] {
				new Room(1),
				new Room(3),
				new Room(4),
				new Room(2)
		};
	}
	
	public void start() {
		if(!running) {
			for (Person person : persons) {
				person.start();
			}
		}
	}
	
	public void stop() {
		for (Person person : persons) {
			try {
				person.join();
			} 
			catch(InterruptedException iex) {}
		}
		
	}
	
	public void enter(Person person) {
		if(person != null && !persons.contains(person)) {
			persons.add(person);
		}
	}
	
	public void goToRoom(Person person, Room room) {
		
	}
	
	public void update() {
		for (int i = 0; i < rooms.length; i++) {
			rooms[i].update();
		}
	}
	
	public void draw(Graphics g) {
		for (int i = 0; i < rooms.length; i++) {
			rooms[i].draw(g);
		}		
	}
	
}
