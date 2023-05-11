package com.github.epicoweo.GameEngine.main.drawables;

import java.util.ArrayList;

import com.epicoweo.math.linearalgebra.Matrix;
import com.epicoweo.math.linearalgebra.Vector2;
import com.epicoweo.math.linearalgebra.Vector3;
import com.github.epicoweo.GameEngine.gameobjects.GameObject;

public class Rectangle extends Shape2D {

	Vector2 p1;
	Vector2 p2;
	
	public float x;
	public float y;
	public float width;
	public float height;
	
	public Matrix vertices;
	
	public ArrayList<Line> lines = new ArrayList<Line>();
	
	public Rectangle(Vector2 pos, Vector2 dim, boolean filled, GameObject obj) {
		super(4, obj);
		
		setFilled(filled);
		
		this.x = pos.x;
		this.y = pos.y;
		this.width = dim.x;
		this.height = dim.y;
		
		this.p1 = new Vector2(x, y);
		this.p2 = new Vector2(x+width, y+height);	
		this.position = new Vector3(pos.x, pos.y, 0f);
		setVertices();
		getLines();
	}
	
	public Rectangle(int x, int y, int w, int h, boolean filled, GameObject obj) {
		this(new Vector2(x, y), new Vector2(w, h), filled, obj);
	}
	
	public Rectangle(int x, int y, int w, int h, GameObject obj) {
		this(new Vector2(x, y), new Vector2(w, h), false, obj);
	}
	
	public Rectangle(Vector2 pos, Vector2 dim, GameObject obj) {
		this(pos, dim, false, obj);
	}
	
	@Override
	public void setPos(Vector3 pos) {
		this.position = pos;
		this.x = pos.x;
		this.y = pos.y;
		this.p1 = new Vector2(x, y);
		this.p2 = new Vector2(x+width, y+height);	
	}
	
	public void setVertices() {
		this.vertices = new Matrix(2, 4);
		this.vertices.set(new Float[][] {
			{p1.x, p2.x, p1.x, p2.x},
			{p1.y, p2.y, p2.y, p1.y}
		});
	}
	
	public void getLines() {
		lines.clear();
		lines.add(new Line(vertices.getColVector(0), vertices.getColVector(2))); // left
		lines.add(new Line(vertices.getColVector(1), vertices.getColVector(3))); // right
		lines.add(new Line(vertices.getColVector(1), vertices.getColVector(2))); // top
		lines.add(new Line(vertices.getColVector(0), vertices.getColVector(3))); // bottom
	}
	
	public void matrixTransform(float a, float b, float c, float d, boolean keepInitialPosition) {
		System.out.println("Original: ");
		vertices.print();
		this.vertices = this.vertices.multiplyMatrix(new Matrix(2, 2).set(new Float[][] {
			{a, b},
			{c, d}
		}));
		
		if(keepInitialPosition) { // shift back
			float transX = p1.x - this.vertices.getColVector(0).get(0);
			float transY = p1.y - this.vertices.getColVector(0).get(1);
			this.vertices = this.vertices.addMatrix(new Matrix(2, 4).set(new Float[][] {
				{transX, transX, transX, transX},
				{transY, transY, transY, transY}
			}));
		}
		
		this.getLines();
		System.out.println("New: ");
		vertices.print();
	}

}
