package com.github.epicoweo.GameEngine.main;

import java.util.ArrayList;

import com.github.epicoweo.GameEngine.gameobjects.GameObject;

public abstract class GameScene {

	public static GameEngine engine;
	public static ArrayList<GameObject> gameObjects;

	public abstract void update(float dt);
	
}
