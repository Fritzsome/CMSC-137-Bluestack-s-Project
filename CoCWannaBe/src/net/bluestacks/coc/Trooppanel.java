package net.bluestacks.coc;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Trooppanel extends JPanel{

	public static Image[] troopset_image;
	public static int buttonSize = 3;
	public static int buttonHeight = 64, buttonWidth = 64;
	public static int width,height;
	public static int heldID = -1;
	
	public static troopButton[] troopbutton;
	public static boolean holdsitem = false;
	public static boolean isFirst = true;
	
	public Trooppanel(){
		//addMouseListener(new KeyHandle());
		//addMouseMotionListener(new KeyHandle());
		setPreferredSize(new Dimension(300,75));
		setBackground(new Color(200,200,200));
		
	}
	
	public void define(){
		troopset_image = new Image[buttonSize];
		troopbutton = new troopButton[buttonSize];
		
		for(int i = 0;i<troopset_image.length-1;i++){
			troopset_image[i] = new ImageIcon(Value.troopFilename[i]).getImage();
		} troopset_image[2] = new ImageIcon(Value.troopFilename[10]).getImage();
		
		for(int i = 0;i<troopbutton.length;i++){
			troopbutton[i] = new troopButton(troopset_image[i]);
			troopbutton[i].setUI(new ButtonUI());
			add(troopbutton[i]);
			final int k = i;
			troopbutton[i].addActionListener(new ActionListener() { 
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
	
	

}
