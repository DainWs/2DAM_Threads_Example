package com.josehaake.presentacion.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BoxLayout;
import java.awt.Canvas;
import java.awt.Color;

/**
 * 
 * @author JoseDuarte
 *
 */
public class MyFrame extends JFrame {

	private static final long serialVersionUID = 5039838508134343883L;

	private FrameController controller;
	
	private JPanel contentPane;

	private JButton btnStart;
	private JButton btnStop;
	
	private Canvas canvas;
	
	private boolean isClosing = false;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyFrame frame = new MyFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

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
		
		setVisible(true);
	}
	
	public void initComponents() {
			
			canvas = new Canvas();
		contentPane.add(canvas, BorderLayout.CENTER);

			JPanel buttonPanel = new JPanel();
			FlowLayout flowLayout = (FlowLayout) buttonPanel.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			
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
		
		Graphics2D g = (Graphics2D)canvas.getGraphics();
		if(g != null) {
			
			g.setColor(Color.BLACK);
			g.drawString("hola muy buenas", 0, 0);
			g.drawRect(0, 0, canvas.getWidth(), canvas.getHeight());
			
		}
	}
	
	public boolean isClosing() {
		return isClosing;
	}
	
	public FrameController getController() {
		return controller;
	}
	
	public Graphics getCanvasGraphics() {
		return canvas.getGraphics();
	}

}
