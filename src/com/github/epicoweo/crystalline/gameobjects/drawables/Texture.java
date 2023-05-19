package com.github.epicoweo.crystalline.gameobjects.drawables;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.epicoweo.math.linearalgebra.Vector3;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Texture {

	private Image image = null;
	private Image originalImage = null;
	
	public Vector3 position;
	
	int width;
	int height;
	
	public Texture(String path) {
		position = new Vector3(0, 0, 0);
		try {
			image = new Image(new FileInputStream(path));
			originalImage = image;
			width = (int)image.getWidth();
			height = (int)image.getHeight();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Texture(Image img) {
		position = new Vector3(0, 0, 0);
		image = img;
		originalImage = image;
		width = (int)image.getWidth();
		height = (int)image.getHeight();
	}
	
	public enum Interpolation {
		NEAREST_NEIGHBOR;
	}
	
	public void scale(int width, int height, Interpolation interpolation) {
		WritableImage wImage = new WritableImage(width, height);
		PixelReader reader = image.getPixelReader();
		PixelWriter writer = wImage.getPixelWriter();
		
		float wScale = width / this.width;
		float hScale = height / this.height;
		
		for(int y = 0; y < this.height; y++) { 
			for(int x = 0; x < this.width; x++) {  
				int writeX = 0, writeY = 0;
				
				switch(interpolation) {
				case NEAREST_NEIGHBOR:
					writeX = (int)Math.round(x*wScale);
					writeY = (int)Math.round(y*hScale);
					break;
				}
				
				Color color = reader.getColor(x, y);
				writer.setColor(writeX, writeY, color); 
				for(int i = 0; i < Math.round(wScale); i++) { // fill in inside pixels
					for(int j = 0; j < Math.round(hScale); j++) {
						writer.setColor(writeX+i, writeY+j, color); 
					}
				}
				             
			}
	    }	
		
		this.image = wImage;
		
	}
	
	public void finishLoading() { // completely load the image
		while(image.getProgress() < 1) continue;
	}
	
	public void setPos(Vector3 pos) {
		this.position = pos;
	}
	
	public Image getImage() {
		return image;
	}

}
