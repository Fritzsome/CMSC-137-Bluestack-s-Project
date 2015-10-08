package net.bluestacks.coc;


import java.awt.Image;

public class Building implements Runnable {
	public Image image;
	public int x;
	public int y;
	public int dimension;
	public Thread thread = new Thread(this);
	
	public Building(Image image, int x, int y, int dimension){
		this.image = image;
		this.x = x;
		this.y = y;
		this.dimension = dimension;
		thread.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	
	
}
