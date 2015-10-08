package net.bluestacks.coc;

import java.awt.Graphics;

public class Environment {
	
	public int worldWidth = 24;
	public int worldHeight = 18;
	public int blockSize = 32;
	public int flag = 0;
	public static Block[][] block;
	public Environment(){
		define();
	}
	
	public void define(){
		block = new Block[worldHeight][worldWidth];
		for(int y=0;y<block.length; y++){
			for(int x=0;x<block[0].length; x++){
				if(flag==0){
				block[y][x] = new Block((Screen.width/2 - worldWidth*blockSize/2) + (x * blockSize), (Screen.height/2 - worldHeight*blockSize/2) + ( y* blockSize), blockSize, blockSize,Value.groundGrass1);
				flag=1;
					if(x == block[0].length-1) flag=0;
				}
				else{
					block[y][x] = new Block((Screen.width/2 - worldWidth*blockSize/2) + (x * blockSize), (Screen.height/2 - worldHeight*blockSize/2) + ( y* blockSize), blockSize, blockSize,Value.groundGrass2);	
					flag=0;
					if(x == block[0].length-1) flag=1;
				}
			}
		}
	}
	
	
	public void physic(){
		
	}
	
	public void draw(Graphics g){
		
		for(int y=0;y<block.length; y++){
			for(int x=0;x<block[0].length; x++){
				block[y][x].draw(g);
				
			}
		}
		
		if(Buildpanel.holdsitem){
			g.drawImage(Buildpanel.buildbutton[Buildpanel.heldID].getImage(),Screen.mse.x-15,Screen.mse.y-15,Buildpanel.buttonDimension,Buildpanel.buttonDimension,null);
			
		}
		
		if(Trooppanel.holdsitem){
			g.drawImage(Trooppanel.troopbutton[Trooppanel.heldID].getImage(),Screen.mse.x-15,Screen.mse.y-15,Trooppanel.buttonWidth/2,Trooppanel.buttonHeight/2,null);
		}
		
		
		if(!Screen.list.isEmpty()){
		for(Building building : Screen.list){
			g.drawImage(building.image,building.x, building.y, building.dimension, building.dimension,null);
		}
		}
		
		if(!Screen.list2.isEmpty()){
			for(Troop troop : Screen.list2){
				g.drawImage(troop.image,troop.x, troop.y, troop.dimension, troop.dimension,null);
			}
			}
	}
}
