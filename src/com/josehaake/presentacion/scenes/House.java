package com.josehaake.presentacion.scenes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import javax.swing.JTextPane;

import com.josehaake.presentacion.entities.Person;
import com.josehaake.presentacion.gui.MyFrame;

/**
 * 
 * @author JoseDuarte
 *
 */
public class House {

	private Collection<Person> persons = new ArrayList<Person>();
	private Room[] rooms;
	private Hall hall;
	
	private boolean running = false;
	
	public House() {
		hall = new Hall();
		rooms = new Room[] {
				new Room(1),
				new Room(2),
				new Room(3),
				new Room(2)
		};
		
		int finalRoomWidth = (MyFrame.CANVAS_WIDTH - hall.getWidth())/2;
		
		rooms[0].setRectangle(new Rectangle(0, 0, finalRoomWidth, MyFrame.CANVAS_HEIHT/2));
		rooms[1].setRectangle(new Rectangle(0, MyFrame.CANVAS_HEIHT/2, finalRoomWidth, MyFrame.CANVAS_HEIHT/2));
		rooms[2].setRectangle(new Rectangle(finalRoomWidth + hall.getWidth(), 0, finalRoomWidth, MyFrame.CANVAS_HEIHT/2));
		rooms[3].setRectangle(new Rectangle(finalRoomWidth + hall.getWidth(), MyFrame.CANVAS_HEIHT/2, finalRoomWidth, MyFrame.CANVAS_HEIHT/2));
	}
	
	/**
	 * Start the emulation
	 */
	public void start() {
		if(!running) {
			running = true;
			
			for (Person person : persons) person.start();
		}
	}
	
	/**
	 * finish the threads
	 */
	public void stop() {
		try {
			running = false;
			
			for (Person person : persons) {
				person.join();
			}
		} 
		catch (InterruptedException e) {}
	}
	
	/**
	 * finish the threads and print person names in a textPane
	 * @param textPane
	 */
	public void stop(JTextPane textPane) {
		try {
			running = false;
			
			for (Person person : persons) {
				String currentName = person.getName();
				person.join();
				textPane.setText(textPane.getText() + currentName + " finished; ");
			}
		} 
		catch (InterruptedException e) {}
	}
	
	/**
	 * simulate a person entering in a house
	 * @param person
	 */
	public void enter(Person person) {
		if(person != null && !persons.contains(person)) {
			persons.add(person);
		}
	}
	
	/**
	 * if the emulation has finished
	 * @return true if has finished
	 * 			false if not
	 */
	public boolean isFinished() {
		return !running;
	}

	/**
	 * @return the count of rooms of the house
	 */
	public int getRoomCount() {
		return rooms.length;
	}

	/**
	 * get a room by her id
	 * @param i the id
	 * @return the room with this id
	 */
	public Room getRoom(int i) {
		return (i < rooms.length && i >= 0) ? rooms[i] : null;
	}

	/**
	 * move a person from one room to other
	 * @param person
	 * @param newRoom
	 */
	public synchronized void goTo(Person person, Room newRoom) {
		if(newRoom != null && !newRoom.itsFull() && !newRoom.itsIn(person)) {
			//Change destination room backgroundColor
			newRoom.setBackgroundColor(Color.GREEN);
			
			hall.enter(person);
			
			try { TimeUnit.SECONDS.sleep(2); } 
			catch (InterruptedException e) {}
			
			hall.goTo(newRoom);
			
			//Change destination room backgroundColor
			newRoom.setBackgroundColor(Color.WHITE);
			
			try { TimeUnit.SECONDS.sleep(1); } 
			catch (InterruptedException e) {}
		}
	}
	
	public void update() {
		for (int i = 0; i < rooms.length; i++) {
			rooms[i].update();
		}
	}
	
	public void draw(Graphics g) {
		hall.draw(g);
		for (int i = 0; i < rooms.length; i++) {
			rooms[i].draw(g);
		}		
	}
	
}
