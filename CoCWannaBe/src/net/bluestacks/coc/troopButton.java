package net.bluestacks.coc;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JButton;

public class troopButton extends JButton {
	Image image;
	
	public troopButton(Image image){
		this.image = image;
		setPreferredSize(new Dimension(64,64));
	}
	
	public Image getImage(){
		return image;
	}
	
public void paintComponent(Graphics g){
		
		super.paintComponent(g);
		
		g.drawImage(image,0,0,Trooppanel.buttonWidth,Trooppanel.buttonHeight,null);
		//store.draw(g);
		
	}

}
