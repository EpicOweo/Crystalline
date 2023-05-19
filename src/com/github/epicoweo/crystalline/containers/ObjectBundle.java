package com.github.epicoweo.crystalline.containers;

import java.util.ArrayList;

import com.github.epicoweo.crystalline.gameobjects.GameObject;
import com.github.epicoweo.crystalline.gameobjects.obj2d.Rigidbody2D;
import com.github.epicoweo.crystalline.gameobjects.obj2d.Rigidbody2D.RBType;

/**
 * 
 * @author epicoweo
 *
 * Stores multiple GameObjects in one group (or ObjectBundle)
 *
 */
public class ObjectBundle {

	private ArrayList<GameObject> objects;
	
	public ObjectBundle() {
		objects = new ArrayList<GameObject>();
	}
	
	public ArrayList<GameObject> getAll() {
		return objects;
	}
	
	public boolean remove(GameObject obj) {
		return objects.remove(obj);
	}
	
	public void add(GameObject obj) {
		objects.add(obj);
	}
	
	public void add(GameObject obj1, GameObject obj2) {
		add(obj1);
		add(obj2);
	}
	
	public void add(GameObject obj1, GameObject obj2, GameObject obj3) {
		add(obj1);
		add(obj2, obj3);
	}
}
