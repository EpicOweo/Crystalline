package com.github.epicoweo.crystalline.gameobjects;

import com.epicoweo.math.linearalgebra.Vector2;
import com.github.epicoweo.crystalline.gameobjects.drawables.Rectangle;

public class GameRect extends GameObject {
	
	public int width = 10;
	public int height = 10;
	
	public GameRect() {
		drawable = new Rectangle(0, 0, 0, 0, this);
	}
	
	public GameRect(int x, int y, int w, int h) {
		drawable = new Rectangle(x, y, w, h, this);
		this.position = new Vector2(x, y);
		setSize(w, h);
	}
	
	public Vector2 getCenter() {
		return new Vector2(this.position.x + this.width/2, this.position.y + this.height/2);
	}
	
	public Rectangle getDrawable() {
		return (Rectangle)this.drawable;
	}
	
	public void setSize(int w, int h) {
		this.width = w;
		this.height = h;
	}
}
