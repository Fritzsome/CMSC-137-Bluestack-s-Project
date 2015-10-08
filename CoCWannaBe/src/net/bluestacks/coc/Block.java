package net.bluestacks.coc;

import java.awt.*;

public class Block extends Rectangle {
	public int groundID;
	public boolean buildable = true;
	
	public Block(int x, int y, int width, int height, int groundID){
		setBounds(x,y,width,height);
		this.groundID = groundID;
		
	}
	
	public void draw(Graphics g){
		//g.drawRect(x,y,width,height);
		if(this.groundID == 0)g.drawImage(Screen.tileset_ground1, x, y, width, height, null);
		else if(this.groundID == 1 )g.drawImage(Screen.tileset_ground2, x, y, width, height, null);

	}
}
