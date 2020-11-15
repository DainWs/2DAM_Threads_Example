package com.josehaake.presentacion.scenes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.concurrent.TimeUnit;

import com.josehaake.presentacion.entities.Person;
import com.josehaake.presentacion.gui.MyFrame;

/**
 * 
 * @author JoseDuarte
 *
 */
public class Hall {

	private static final int MIN_PADDING = 10;
	
	private Person currentPerson;
	
	/**
	 * a person entering in the hall
	 * @param person
	 */
	public void enter(Person person) {
		//Enter in the hall
		currentPerson = person;
		currentPerson.getCurrentRoom().leave(currentPerson);
	}
	
	/**
	 * a person going to one room
	 * @param newRoom
	 */
	public void goTo(Room newRoom) {
		//leave the hall entering a room
		if(!newRoom.itsFull() && !newRoom.itsIn(currentPerson)) {
			//Enter in the new room
			newRoom.enter(currentPerson);
			currentPerson.setCurrentRoom(newRoom);
			currentPerson = null;
		} 
		else if(newRoom.itsFull()) {
			//change destination background color
			newRoom.setBackgroundColor(Color.RED);
			
			try {
				TimeUnit.SECONDS.sleep(2);
			}
			catch (Exception e) {}
			
			//go back to our room
			currentPerson.getCurrentRoom().enter(currentPerson);
			currentPerson = null;
		}
		
	}
	
	public int getWidth() {
		return Person.CIRCLE_WIDTH + MIN_PADDING;
	}
	
	public int getHeight() {
		return MyFrame.CANVAS_HEIHT;
	}
	
	public void draw(Graphics g) {
		Rectangle rectangle = new Rectangle(0, 0, MyFrame.CANVAS_WIDTH, MyFrame.CANVAS_HEIHT);
		
		g.setColor(Color.BLACK);
		g.drawRect(
				(int)(rectangle.getCenterX() - (getWidth()/2)),
				0,
				getWidth(),
				getHeight()
		);
		if(currentPerson != null) {
			//NEW PERSON DRAWING POSITION
			Point relativePoint = new Point((int) rectangle.getCenterX(), (int) rectangle.getCenterY());
			
			//CHANGE PERSON DRAWING POSITION
			currentPerson.setPosition(relativePoint);
			currentPerson.draw(g);
		}
		
	}
}
