package com.github.epicoweo.GameEngine.main.drawables;

import com.epicoweo.math.linearalgebra.Vector3;
import com.github.epicoweo.GameEngine.gameobjects.GameObject;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public abstract class Drawable {
	
	public Vector3 position;
	public Paint color = Color.BLACK;
	public GameObject obj;
	public boolean hasObj = false;
	
	public abstract void setPos(Vector3 pos);
	
}
