package com.github.epicoweo.crystalline.gameobjects.drawables;

import com.epicoweo.math.linearalgebra.Vector3;
import com.github.epicoweo.crystalline.gameobjects.GameObject;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public abstract class Drawable {
	
	public Vector3 position;
	public Paint color = Color.BLACK;
	public GameObject obj;
	public boolean hasObj = false;
	
	public Texture texture;
	public boolean hasTexture = false;
	
	
	public void setTexture(Texture tex) {
		this.texture = tex;
		this.hasTexture = true;
	}
	
	public abstract void setPos(Vector3 pos);
	
}
