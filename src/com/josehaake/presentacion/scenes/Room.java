package com.josehaake.presentacion.scenes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;

import com.josehaake.presentacion.entities.Person;

public class Room {

	private static final int MIN_PADDING = 10;
	

	private final int minWidth;
	private final int minHeight;
	
	private Person[] personsInRoom;
	private int personCapacity;
	private int centinel = 0;
	
	private Rectangle rectangle;
	
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
	
	public void setRectangle(Rectangle rectangle) {
		if(
			rectangle != null &&
			rectangle.getWidth() >= minWidth &&
			rectangle.getHeight() >= minHeight 
		) {
			this.rectangle = rectangle;
		}
	}
	
	//SOLO PUEDE ENTRAR UNA PERSONA A LA VEZ
	public synchronized void enter(Person person) {
		
                if(person != null && !itsIn(person)) {
			personsInRoom[centinel++] = person;
		}
                
	}
	
	//SOLO PUEDE SALIR UNA PERSONA A LA VEZ
	public synchronized void leave(Person person) {
		for (int i = 0; i < personsInRoom.length; i++) {
			if(personsInRoom[i].equals(person)) {
				personsInRoom[i] = null;
				centinel--;
				update();
				break;
			}
		}
	}
	
	public boolean itsIn(Person person) {
		for (int i = 0; i < personsInRoom.length; i++) {
			if(personsInRoom[i].equals(person)) return true;
		}
		
		return false;
	}
	
	public void update() {

		int startYPos = MIN_PADDING / 2;
		
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
		
		//UPDATE EACH PERSON
		for (int i = 0; i < personsInRoom.length; i++) {
			//IF CURRENT PERSON IS NOT NULL.
			if(personsInRoom[i] != null) {
			
				Point relativePoint = new Point(
						(int) rectangle.getCenterX(), 
						(int)( startYPos + (i * Person.CIRCLE_HEIGHT) )
				);
				
				personsInRoom[i].setPosition(relativePoint);
			}
		}
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
		
		for (int i = 0; i < personsInRoom.length; i++) {
			personsInRoom[i].draw(g);
		}
		
	}
	
}
