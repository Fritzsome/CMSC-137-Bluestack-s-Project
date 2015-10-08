package net.bluestacks.coc;

import java.awt.*;


import javax.swing.*;

public class buildingButton extends JButton {
	Image image;
	
	public buildingButton(Image image){
		this.image = image;
		setPreferredSize(new Dimension(64,64));
	}
	
	public Image getImage(){
		return image;
	}
	
	public void paintComponent(Graphics g){
		
		super.paintComponent(g);
		
		g.drawImage(image,0,0,Buildpanel.buttonDimension,Buildpanel.buttonDimension,null);
		//store.draw(g);
		
	}
}
