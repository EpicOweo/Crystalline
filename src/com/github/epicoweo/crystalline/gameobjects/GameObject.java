package com.github.epicoweo.crystalline.gameobjects;

import java.util.ArrayList;

import com.epicoweo.math.linearalgebra.Vector2;
import com.epicoweo.math.linearalgebra.Vector3;
import com.github.epicoweo.crystalline.gameobjects.drawables.Drawable;
import com.github.epicoweo.crystalline.gameobjects.obj2d.Attribute;

public class GameObject {

	public ArrayList<Attribute> attributes;
	public Vector2 position;
	public Drawable drawable;
	
	public int partitionX = 0;
	public int partitionY = 0;
	
	
	public GameObject() {
		attributes = new ArrayList<Attribute>();
		position = new Vector2(0, 0);
	}
	
	public void update(float dt) {
		for(Attribute att : attributes) {
			att.update(dt);
		}
		drawable.position = new Vector3(position.x, position.y, 0);
		if(drawable.hasTexture) drawable.texture.position = new Vector3(position.x, position.y, 0);
	}
	
	public <T extends Attribute> T getAttribute(Class<T> att) {
		for(Attribute a : attributes) {
			if(a.getClass() == att) {
				return att.cast(a);
			}
		}
		
		return null;
	}
	
	public void addAttribute(Attribute att) {
		attributes.add(att);
	}
	
	public <T extends Attribute> void removeAttribute(Class<T> att) {
		attributes.remove(getAttribute(att));
	}
}
