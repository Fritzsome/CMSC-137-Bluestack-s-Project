package net.bluestacks.coc;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Buildpanel extends JPanel implements Runnable{
	
	
	public static Image[] buildset_image;
	public static int buttonSize = 11;
	public static int buttonDimension = 64;
	public static int width,height;
	public static int heldID = -1;
	public Thread thread = new Thread(this);
	public static buildingButton[] buildbutton;
	public static boolean holdsitem = false;
	public static boolean isFirst = true;
	
	public Buildpanel(){
		//addMouseListener(new KeyHandle());
		//addMouseMotionListener(new KeyHandle());
		setPreferredSize(new Dimension(800,75));
		setBackground(new Color(200,200,200));
		thread.start();
	}
	
	
	
	public void define(){
		
		buildset_image = new Image[buttonSize];
		buildbutton = new buildingButton[buttonSize];
		
		for(int i = 0;i<buildset_image.length-1;i++){
			buildset_image[i] = new ImageIcon(Value.buildingFilename[i]).getImage();
		} buildset_image[10] = new ImageIcon(Value.buildingFilename[10]).getImage();
		
		for(int i = 0;i<buildbutton.length;i++){
			buildbutton[i] = new buildingButton(buildset_image[i]);
			buildbutton[i].setUI(new ButtonUI());
			add(buildbutton[i]);
			final int k = i;
			buildbutton[i].addActionListener(new ActionListener() { 
				  public void actionPerformed(ActionEvent e) { 
					 
					if(k==Value.trash){
						holdsitem = false;
						heldID = -1;
						}
					else{ 
						heldID = k;
						holdsitem = true;
					}
				  } 
			} );
			
		}
		
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if(isFirst){
			width = getWidth();
			height = getHeight();
			define();
			
			isFirst = false;
		}
		
		
	}
	
	public void run() {
		while(true){
			if(!isFirst){
				
			}
			repaint();
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
		
	}

}
