package net.bluestacks.coc;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Screen extends JPanel implements Runnable{
	public Thread thread = new Thread(this);
	
	public static Image tileset_ground1;
	public static Image tileset_ground2;
	public static LinkedList<Building> list = new LinkedList<Building>();
	public static LinkedList<Troop> list2 = new LinkedList<Troop>();
	public static int width,height;
	public static boolean isFirst = true;
	public static Environment terrain;
	public static boolean clicked = false;
	public static Point mse;
	public static Rectangle clickedTile;
	
	
	public Screen(){
		addMouseListener(new KeyHandle());
		addMouseMotionListener(new KeyHandle());
		setPreferredSize(new Dimension(800,580));
		thread.start();
		
	}
	
	public static void click(){
		if(Buildpanel.holdsitem){
			for(int y=0;y<Environment.block.length; y++){
				for(int x=0;x<Environment.block[0].length; x++){
					if(Environment.block[y][x].contains(Screen.mse)){
						if(Environment.block[y][x].buildable && Environment.block[y+1][x].buildable && Environment.block[y][x+1].buildable && Environment.block[y+1][x+1].buildable){
							
							Environment.block[y][x].buildable = false;
							Environment.block[y+1][x].buildable = false;
							Environment.block[y][x+1].buildable = false;
							Environment.block[y+1][x+1].buildable = false;
							Buildpanel.holdsitem = false;
							list.add(new Building(Buildpanel.buildbutton[Buildpanel.heldID].getImage(), Environment.block[y][x].x, Environment.block[y][x].y, Buildpanel.buttonDimension ));
						}
					}
				}
			}
		}
		
		if(Trooppanel.holdsitem){
			for(int y=0;y<Environment.block.length; y++){
				for(int x=0;x<Environment.block[0].length; x++){
					if(Environment.block[y][x].contains(Screen.mse)){
						if(Environment.block[y][x].buildable){
							Trooppanel.holdsitem = false;
							list2.add(new Troop(Trooppanel.troopbutton[Trooppanel.heldID].getImage(), Environment.block[y][x].x, Environment.block[y][x].y, Trooppanel.buttonWidth/2 ));
						}
					}
				}
			}
		}
	}
	
	public void define(){
		terrain = new Environment();
		//load = new LoadEnvironment();
			
			tileset_ground1 = new ImageIcon("res/grass.png").getImage();
			tileset_ground2 = new ImageIcon("res/grass1.png").getImage();
			
			//tileset_ground = createImage(new FilteredImageSource(tileset_ground, new CropImageFilter(0,26,26,26)));
			
		
	}
	
	public void paintComponent(Graphics g){
		//super.paintComponent(g);
		
		if(isFirst){
			width = getWidth();
			height = getHeight();
			define();
			
			isFirst = false;
		}
		g.setColor(new Color(50,50,50));
		g.fillRect(0, 0, getWidth(), getHeight());
		
		terrain.draw(g);
		
		
		
		
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
