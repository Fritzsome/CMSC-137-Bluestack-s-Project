package net.bluestacks.coc;

import java.awt.Image;
import java.util.Random;

public class Troop implements Runnable {
	public Image image;
	public int x;
	public int y;
	public int dimension;
	public Thread thread = new Thread(this);
	
	public Troop(Image image, int x, int y, int dimension){
		this.image = image;
		this.x = x;
		this.y = y;
		this.dimension = dimension;
		thread.start();
	}

	public void run() {
		Random rand = new Random();
		int randomX = rand.nextInt((Screen.width -32 - 0) + 1);
		int randomY = rand.nextInt((Screen.height -32 - 0) + 1);
		
		int xDistance = randomX - x;
		int yDistance = randomY - y;
		int distance = (int) Math.sqrt(xDistance * xDistance + yDistance * yDistance);
		
		while(true){
			if(x!=randomX || y!=randomY){
				if(x>randomX)this.x--;
				else if(x<randomX)this.x++;
				
				if(y>randomY)this.y--;
				else if(y<randomY)this.y++;
			
			}
			//System.out.print(xDistance+" "+yDistance);
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
		
	}
}
