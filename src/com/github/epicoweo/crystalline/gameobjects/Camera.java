package com.github.epicoweo.crystalline.gameobjects;

import com.epicoweo.math.linearalgebra.Vector2;
import com.epicoweo.math.linearalgebra.Vector3;

public class Camera {

	
	public Vector3 position;
	public int width;
	public int height;
	
	public CameraType type; 
	
	public enum CameraType {
		ORTHOGRAPHIC;
	}
	
	public Camera(CameraType type) {
		this.type = type;
		this.position = new Vector3(0, 0, 0);
	}
	
	public void setPosition(float x, float y, float z) {
		setPosition(new Vector3(x, y, z));
	}
	
	public void setPosition(Vector2 pos) {
		this.position = pos.toVector3();
	}
	
	public void setPosition(Vector3 pos) {
		this.position = pos;
	}
	
	public void setCenter(Vector2 pos) {
		setCenter(pos.toVector3());
	}
	
	public void setCenter(Vector3 pos) {
		this.position = pos.add(new Vector3(-width/2, -height/2, 0));
	}
	
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void update() {
		
	}
	
	public Vector3 toScreen(Vector3 coords) {
		Vector3 dupCoords = coords.duplicate();
		Vector3 dupPos = position.duplicate();
		dupPos.multiplyScalar(-1);
		Vector3 screenCoords = dupCoords.add(dupPos);
		return screenCoords;
	}
	
	public Vector3 toWorld(Vector3 coords) {
		Vector3 dupCoords = coords.duplicate();
		Vector3 dupPos = position.duplicate();
		Vector3 worldCoords = dupCoords.add(dupPos);
		return worldCoords;
	}
	
	
}
