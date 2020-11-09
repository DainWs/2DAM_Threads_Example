package com.josehaake.presentacion;

import com.josehaake.presentacion.gui.MyFrame;

public class Program implements Runnable {
	
	private MyFrame frame;
	
	private boolean isRunning = false;
	private Thread thisThread;

	public Program() {
		frame = new MyFrame();
		start();		
	}
	
	public synchronized void start() {
		isRunning = true;
		
		thisThread = new Thread(this, "Graphics");
		thisThread.start();
	}
	
	private synchronized void stop() {
		isRunning = false;
		
		try {
			thisThread.join();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	private void Update() {
		frame.update();
		
		if(frame.isClosing()) stop();
	}
	
	private void Draw() {
		frame.draw();
	}
	
	/**
	 * Actualiza los graficos 60 veces por segundo
	 */
	@Override
	public void run() {
		
		final byte updatesPerSecond = 60;
		final int nanosecondsPerSecond = 1000000000;
		final double nanosecondsPerUpdate = nanosecondsPerSecond / updatesPerSecond;
		
		long lastUpdate = System.nanoTime();
		
		double lapsedTime;
		double delta = 0;
		
		frame.requestFocus();
		
		while(isRunning) {
			final long initLoop = System.nanoTime(); 
			
			//calculate the lapsed time between the lastUpdate and currentUpdate in nanoseconds
			lapsedTime = initLoop - lastUpdate;
			lastUpdate = initLoop;
			
			//times it has to be updated in this second
			// example: 
			//			27600(lapsedTime in Nanoseconds) / 16666666 = 0,0016 nanoseconds
			// if delta was 0,9984 the result will be 
			//			delta = delta(0,9984) + 0,0016 
			//			delta = 1 second
			delta += lapsedTime / nanosecondsPerUpdate;
			
			//if delta is greater than 1 seconds, do 1 update
			while (delta >= 1) {
				Update();
				delta--;
			}
			
			Draw();
			
		}
		
	}
	
	public static void main(String[] args) {
		new Program();
	}
	
	
	
}
