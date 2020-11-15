package com.josehaake.presentacion.scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import com.josehaake.presentacion.entities.Person;


/**
 * 
 * @author JoseDuarte
 *
 */
public class Room {

	private static final int MIN_PADDING = 10;
	
	private final int minWidth;
	private final int minHeight;
	private Rectangle rectangle;
	
	private Person[] personsInRoom;
	private int personCapacity;
	private int centinel = 0;

	private Color backgroundColor = Color.WHITE;
	
	public Room(int personCapacity) {
		this.personCapacity = personCapacity;
		
		personsInRoom = new Person[this.personCapacity];
		
		minWidth = (Person.CIRCLE_WIDTH * 1) + MIN_PADDING;
		minHeight = (Person.CIRCLE_HEIGHT * personCapacity) + MIN_PADDING;
		
		rectangle = new Rectangle(
			0,
			0,
			minWidth,
			minHeight
		);
	}
	
	/**
	 * PERSON ENTER IN THE ROOM
	 * @param person
	 */
	public synchronized void enter(Person person) {
		if(person != null && !itsIn(person) && !itsFull()) {
			personsInRoom[centinel++] = person;
		}
		update();
	}
	
	/**
	 * PERSON LEAVES THE ROOM 
	 * @param person
	 */
	public synchronized void leave(Person person) {
		if(person == null) return;
		
		for (int i = 0; i < personsInRoom.length; i++) {
			if(person.equals(personsInRoom[i])) {
				personsInRoom[i] = null;
				centinel--;
				update();
				break;
			}
		}
		update();
	}
	
	/**
	 * IF THE ROOM ITS FULL
	 * @return true : the room its full
	 * 			false : the room its not full
	 */
	public synchronized boolean itsFull() {
		return (centinel == personsInRoom.length);
	}
	
	/**
	 * TEST IF A PERSON ITS IN THE ROOM
	 * @param person
	 * @return true : if the person its in the room
	 * 			false : if the person its not in the room
	 */
	public synchronized boolean itsIn(Person person) {	
		if(person == null) return true;
		
		//IF PERSON IS IN THE ROOM
		for (int i = 0; i < personsInRoom.length; i++) {
			if(person.equals(personsInRoom[i])) return true;
		}
		
		return false;
	}
	
	public void update() {

		int startYPos = rectangle.y + (MIN_PADDING / 2);
		
		//SORTING ARRAY OF PERSONS
		for (int i = 0; i < personsInRoom.length - 1; i++) {
			for (int j = i; j < personsInRoom.length - 1; j++) {
				//IF CURRENT PERSON IS NULL, GET NEXT PERSON AND REMPLACE THIS.
				if(personsInRoom[j] == null) {
					personsInRoom[j] = personsInRoom[j + 1];
					personsInRoom[j + 1] = null;
				}
			}
		}
		
		//UPDATE EACH PERSON DRAWING POSITION
		for (int i = 0; i < personsInRoom.length; i++) {
			//IF CURRENT PERSON IS NOT NULL.
			if(personsInRoom[i] != null) {
				
				//NEW PERSON DRAWING POSITION
				Point relativePoint = new Point(
						(int) rectangle.getCenterX(), 
						(int)( startYPos + (i * Person.CIRCLE_HEIGHT) )
				);
				
				//CHANGE PERSON DRAWING POSITION
				personsInRoom[i].setPosition(relativePoint);
			}
		}
	}
	
	public void setBackgroundColor(Color color) {
		this.backgroundColor = color;
	}
	
	public void setRectangle(Rectangle rectangle) {
		if(
			rectangle != null &&
			rectangle.getWidth() >= minWidth &&
			rectangle.getHeight() >= minHeight 
		) {
			this.rectangle = rectangle;
		}
	}
	
	public void draw(Graphics g) {
		g.setColor(backgroundColor);
		g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
		g.setColor(Color.BLACK);
		g.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);

		Font currentFont = g.getFont();
		g.setFont(new Font(currentFont.getName(), Font.BOLD, 16));
		
		g.setColor(Color.BLACK);
		g.drawString("Max " + personCapacity + " People.", rectangle.x + 22, rectangle.y + 22);
		
		for (int i = 0; i < personsInRoom.length; i++) {
			if(personsInRoom[i] != null) {
				personsInRoom[i].draw(g);
			}
		}
	}
	
}
