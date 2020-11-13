package com.josehaake.presentacion.entities;

import java.awt.Graphics;
import java.awt.Point;

import com.josehaake.presentacion.scenes.House;

public class Person extends Thread {
	
	public static final int CIRCLE_WIDTH = 32;
	public static final int CIRCLE_HEIGHT = 32;
	
	private House myHouse;
	
	public Person(House myHouse) {
		this.myHouse = myHouse;
	}
	
	public void setPosition(Point point) {
		
	}
	
	public void draw(Graphics g) {
		
	}
	
	@Override
	public synchronized void start() {
		super.start();
	}
	
	@Override
	public void run() {
		super.run();
	}
	
}
