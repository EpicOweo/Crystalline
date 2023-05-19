package com.github.epicoweo.crystalline.main;

import java.util.ArrayList;

import com.github.epicoweo.crystalline.gameobjects.GameObject;

public abstract class GameScene {

	public static GameEngine engine;
	public static ArrayList<GameObject> gameObjects;

	public abstract void update(float dt);
	
}
