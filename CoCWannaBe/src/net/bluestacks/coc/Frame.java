package net.bluestacks.coc;

import java.awt.*;
import javax.swing.*;

public class Frame extends JFrame {
	
	JPanel leftPane = new JPanel();
	JPanel rightPane = new JPanel();
	public static Buildpanel buildPane;
	public static Trooppanel troopPane;
	
	public void addComponents(Container container) {
		
		leftPane.setPreferredSize(new Dimension(800,700));
		rightPane.setBackground(Color.YELLOW);
	
		rightPane.setPreferredSize(new Dimension(300,700));
		
		container.add(leftPane, BorderLayout.WEST);
		container.add(rightPane, BorderLayout.EAST);
		
		Screen gameScreen = new Screen();
		buildPane = new Buildpanel();
		troopPane = new Trooppanel();
		leftPane.add(gameScreen);
		leftPane.add(buildPane);
		rightPane.add(troopPane, BorderLayout.SOUTH);
		
	}
	
	public Frame(String name){
		
		super(name);
		setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addComponents(this.getContentPane());
		this.setPreferredSize(new Dimension(1100,700));
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
		
		
	}
	
	

	public static void main(String[] args){
		new Frame("COC Wanna Be");
	}
}
