package com.josehaake.presentacion.gui;

import java.awt.Graphics;

import javax.swing.JOptionPane;

import com.josehaake.presentacion.entities.Person;
import com.josehaake.presentacion.scenes.House;

/**
 * 
 * @author JoseDuarte
 *
 */
public class FrameController {

	private static final String[] PERSONS_NAMES = {
			"Julio",
			"Mark",
			"Jhon",
			"Pepe",
			"Julie",
	};
	
	private House house;
	private MyFrame owner;
	
	public FrameController(MyFrame owner) {
		this.owner = owner;
		
		house = new House();
		
		for (int i = 0; i < PERSONS_NAMES.length; i++) {
			Person newPerson = new Person(PERSONS_NAMES[i], house);
			house.enter(newPerson);
		}
	}
	
	public void start() {
		house.start();
	}
	
	public void stop() {
		house.stop(owner.getThreadOutputTextPane());
		JOptionPane.showMessageDialog(
			owner,
			"The emulation has stopped successfully",
			"Stopping Emulation.",
			JOptionPane.INFORMATION_MESSAGE
		);
	}
	
	public void restart() {
		stop();
		start();
	}
	
	public void update() {
		house.update();
	}
	
	public void draw(Graphics g) {
		house.draw(g);
	}
	
	public MyFrame getOwner() {
		return owner;
	}
	
	public boolean isRunning() {
		return !house.isFinished();
	}
}
