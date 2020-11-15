package com.josehaake.presentacion.gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.BoxLayout;
import javax.swing.border.MatteBorder;

/**
 * 
 * @author JoseDuarte
 *
 */
public class MyFrame extends JFrame {

	private static final long serialVersionUID = 5039838508134343883L;

	public static final int CANVAS_WIDTH = 700;
	public static final int CANVAS_HEIHT = 700;
	
	private FrameController controller;
	
	private JPanel contentPane;

	private JButton btnStart;
	private JButton btnStop;
	
	private Canvas canvas;
	
	private boolean isClosing = false;
	private JLabel lblNewLabel;
	private JTextPane threadsMessage;

	/**
	 * Create the frame.
	 */
	public MyFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 641, 455);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		controller = new FrameController(this);
		
		
		initComponents();
		initListeners();
		update();
		
		pack();
		setVisible(true);
	}
	
	public void initComponents() {
			
			canvas = new Canvas();
			canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIHT));
		contentPane.add(canvas, BorderLayout.CENTER);

			JPanel buttonPanel = new JPanel();
			buttonPanel.setBorder(new MatteBorder(1, 0, 0, 0, (Color) new Color(0, 0, 0)));
				buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
				
				lblNewLabel = new JLabel("Finished Threads :");
				buttonPanel.add(lblNewLabel);
				
				threadsMessage = new JTextPane();
				buttonPanel.add(threadsMessage);
			
				btnStart = new JButton("Start");
			buttonPanel.add(btnStart);
			
				btnStop = new JButton("Stop");
			buttonPanel.add(btnStop);

		contentPane.add(buttonPanel, BorderLayout.SOUTH);
	}
	
	public void initListeners() {
		
		addWindowListener(new WindowAdapter() {
			
			@Override public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				isClosing = true;
			}
		});
		
		btnStart.addActionListener((ActionEvent ae) -> {
			threadsMessage.setText("");
			controller.start();
		});
		
		btnStop.addActionListener((ActionEvent ae) -> {
			controller.stop();
		});
	}
	
	public void update() {
		controller.update();
	}
	
	public void draw() {
		
		BufferStrategy strategy = canvas.getBufferStrategy();
		
		if (strategy==null) {
			canvas.createBufferStrategy(3);
			return;
		}
		
		Graphics g = strategy.getDrawGraphics();
		
		if(g != null) {
			
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
			
			g.setColor(Color.BLACK);
			g.drawRect(0, 0, canvas.getWidth() - 1, canvas.getHeight() - 1);
			
			controller.draw(g);
			
			g.dispose();
		}
		
		strategy.show();
	}
	
	public boolean isClosing() {
		return isClosing;
	}
	
	public FrameController getController() {
		return controller;
	}

	public JTextPane getThreadOutputTextPane() {
		return threadsMessage;
	}

}
