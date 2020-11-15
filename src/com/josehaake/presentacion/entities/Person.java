package com.josehaake.presentacion.entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.concurrent.TimeUnit;

import com.josehaake.presentacion.scenes.House;
import com.josehaake.presentacion.scenes.Room;

/**
 * 
 * @author JoseDuarte
 *
 */
public class Person extends Thread {
	
	public static final int CIRCLE_WIDTH = 64;
	public static final int CIRCLE_HEIGHT = 64;
	
	private Point position;
	
	private String name;
	private House myHouse;
	private Room currentRoom;
	
	public Person(String name, House myHouse) {
		this.name = name;
		this.myHouse = myHouse;
		
		position = new Point(0,0);
	}
	
	public void setCurrentRoom(Room room) {
		this.currentRoom = room;
	}
	
	public Room getCurrentRoom() {
		return currentRoom;
	}
	
	public void setPosition(Point position) {
		this.position = position;
	}
	
	public void draw(Graphics g) {
		int finalXPosition = position.x - (CIRCLE_WIDTH/2);
		int finalYPosition = position.y;
		
		g.setColor(Color.ORANGE);
		g.fillOval(finalXPosition, finalYPosition, CIRCLE_WIDTH, CIRCLE_HEIGHT);
		g.setColor(Color.BLACK);
		
		Font currentFont = g.getFont();
		g.setFont(new Font(currentFont.getName(), Font.BOLD, 16));
		g.drawString(name, position.x - (CIRCLE_WIDTH/3), position.y + (CIRCLE_HEIGHT/2));
	}
	
	/**
	 * first of all, the person enter in a random room.
	 * every 0-10 seconds, the person decide go to other room
	 */
	@Override
	public void run() {
		
		//FIRST ROOM CHOOSE BY PERSON
		if(currentRoom == null) {
			int roomsCount = myHouse.getRoomCount();
			Room newRoom;
			
			do {
				newRoom = myHouse.getRoom((int)(Math.random() * roomsCount));
			} 
			while(newRoom.itsFull());
			
			newRoom.enter(this);
			setCurrentRoom(newRoom);
		}
		//END FIRST ROOM CHOOSE BY PERSON
		
		while(!myHouse.isFinished()) {
			try {
				//WAIT BETWEEN 0 AND 10 SECONDS
				TimeUnit.SECONDS.sleep((int)(Math.random() * 11));
				
				//GET THE NEW ROOM
				int roomsCount = myHouse.getRoomCount();
				Room newRoom = myHouse.getRoom((int)(Math.random() * roomsCount));
				
				//GO TO THE NEW ROOM
				myHouse.goTo(this, newRoom);
			} 
			catch (InterruptedException e) { break; }
		}
	}
	
}
