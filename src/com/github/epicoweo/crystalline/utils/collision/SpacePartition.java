package com.github.epicoweo.crystalline.utils.collision;

import java.util.ArrayList;

import com.github.epicoweo.crystalline.gameobjects.GameObject;
import com.github.epicoweo.crystalline.gameobjects.GameRect;
import com.github.epicoweo.crystalline.main.GameEngine;
import com.github.epicoweo.crystalline.main.GameScene;

public class SpacePartition {
	
	public static int rows = 3;
	public static int cols = 3;
	public static ArrayList<ArrayList<ArrayList<GameObject>>> partitions
		= new ArrayList<ArrayList<ArrayList<GameObject>>>();
	
	// divide the screen into a rows x cols grid and put each object
	// where it belongs in the grid (for collision purposes mainly)
	public static void partitionObjects() { 
		System.out.println("partitioning objects");
		partitions.clear();
		
		for(int i = 0; i < rows; i++) { // initialize partitions
			ArrayList<ArrayList<GameObject>> row = new ArrayList<ArrayList<GameObject>>();
			for(int j = 0; j < cols; j++) {
				row.add(new ArrayList<GameObject>());
			}
			partitions.add(row);
		}
		
		for(GameObject obj : GameScene.gameObjects) {
			int row = 0;
			int col = 0;
			
			float rowSize = GameEngine.getConfig().height/rows;
			float colSize  = GameEngine.getConfig().width/cols;
			
			if(obj instanceof GameRect && (((GameRect)obj).width > colSize)
					|| (((GameRect)obj).height > rowSize)) {
				for(int i = 0; i < rows; i++) { // if it spreads across multiple partitions just put it in all of them
					for(int j = 0; j < cols; j++) {
						partitions.get(row).get(col).add(obj);
					}
				}
				
				continue;
			}
			
			while(!(row*rowSize < obj.position.y && (row+1)* rowSize > obj.position.y)) { // in between row and row+1
				row++;
				if(row >= rows) break;
			}
			while(!(col*colSize < obj.position.x && (col+1)* colSize > obj.position.x)) { // in between col and col+1
				col++;
				if(cols >= cols) break;
			}
			
			if(row >= rows || col >= cols) continue;
			
			obj.partitionX = col;
			obj.partitionY = row;
			
			System.out.println("partition is " + obj.partitionX + " " + obj.partitionY);
			partitions.get(row).get(col).add(obj);
			
		}
	}
}
