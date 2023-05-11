package com.github.epicoweo.GameEngine.main.drawables;

import com.epicoweo.math.linearalgebra.Matrix;
import com.epicoweo.math.linearalgebra.Vector3;
import com.github.epicoweo.GameEngine.gameobjects.GameObject;

public class Shape2D extends Drawable {

	public Matrix vertices;
	public boolean filled = false;
	
	public Shape2D(int vertices) {
		this(vertices, null);
	}
	
	public Shape2D(int vertices, GameObject obj) {
		this.vertices = new Matrix(2, vertices);
		if(obj != null) {
			this.obj = obj;
			hasObj = true;
		}
		
	}
	
	public void setFilled(boolean f) {
		filled = f;
	}

	@Override
	public void setPos(Vector3 pos) {
		this.position = pos;
	}
}
