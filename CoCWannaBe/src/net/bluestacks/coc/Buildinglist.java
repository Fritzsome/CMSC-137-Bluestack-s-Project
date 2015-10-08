package net.bluestacks.coc;

import java.awt.Image;

public class Buildinglist {
	public Image image;
	public int x;
	public int y;
	public int dimension;
	
	public Buildinglist(Image image, int x, int y, int dimension){
		this.image = image;
		this.x = x;
		this.y = y;
		this.dimension = dimension;
	}
}
