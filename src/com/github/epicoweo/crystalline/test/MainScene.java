package com.github.epicoweo.crystalline.test;

import java.util.ArrayList;
import java.util.Random;

import com.epicoweo.math.linearalgebra.Vector2;
import com.github.epicoweo.crystalline.containers.ObjectBundle;
import com.github.epicoweo.crystalline.demo.etc.Player;
import com.github.epicoweo.crystalline.gameobjects.Camera;
import com.github.epicoweo.crystalline.gameobjects.Camera.CameraType;
import com.github.epicoweo.crystalline.gameobjects.GameObject;
import com.github.epicoweo.crystalline.gameobjects.GameRect;
import com.github.epicoweo.crystalline.gameobjects.obj2d.Rigidbody2D;
import com.github.epicoweo.crystalline.gameobjects.obj2d.Rigidbody2D.RBType;
import com.github.epicoweo.crystalline.gameobjects.obj2d.Rigidbody2D.TriggerType;
import com.github.epicoweo.crystalline.main.GameEngine;
import com.github.epicoweo.crystalline.main.GameScene;
import com.github.epicoweo.crystalline.renderers.ShapeRenderer2D;

import javafx.scene.paint.Color;

public class MainScene extends GameScene {

	public static ShapeRenderer2D sr;
	public static Camera camera;
	
	public Player player;
	
	public MainScene() {
		sr = new ShapeRenderer2D();
		gameObjects = new ArrayList<GameObject>();
		camera = new Camera(CameraType.ORTHOGRAPHIC);
		camera.setSize(GameEngine.getConfig().width, GameEngine.getConfig().height);
		sr.camera = camera;
		
		init();
	}
	
	private void init() {
		Player p = new Player();	
		player = p;
		
		GameRect trigger = new GameRect(100, 100, 400, 200);
		Rigidbody2D triggerRb = new Rigidbody2D(trigger, RBType.STATIC);
		triggerRb.isTrigger = true;
		triggerRb.triggerType = TriggerType.DISCONTINUOUS;
		triggerRb.setTrigger(new Runnable() {
			@Override
			public void run() {
				System.out.println("triggered");
			}
		});
		trigger.addAttribute(triggerRb);
		
		gameObjects.add(trigger);
		
		//wall stuff
		GameRect topWall = new GameRect(-20, -20, 1320, 20);
		GameRect bottomWall = new GameRect(-20, 720, 1320, 20);
		GameRect leftWall = new GameRect(-20, -20, 20, 740);
		GameRect rightWall = new GameRect(1280, -20, 20, 740);
		
		ObjectBundle walls = new ObjectBundle();
		walls.add(topWall, bottomWall);
		walls.add(leftWall, rightWall);
		
		for(GameObject obj : walls.getAll()) {
			obj.addAttribute(new Rigidbody2D(obj, RBType.STATIC));
			((GameRect)obj).getDrawable().setFilled(true);
			gameObjects.add(obj);
		}
		
		
		gameObjects.add(p);
	}
	
	@Override
	public void update(float dt) {
		camera.update();
		camera.setCenter(player.getCenter());
		
		doDraws();
		
		for(GameObject obj : gameObjects) {
			obj.update(dt);
		}
		
		lateUpdate(dt);
	}
	
	public void lateUpdate(float dt) {
		
	}
	
	public void stressTest() {
		int squares = 100;
		
		for(int i = 0; i < squares; i++) {
			GameRect r = new GameRect(new Random().nextInt(0, 1280), new Random().nextInt(0, 720), 10, 10);
			r.getDrawable().setFilled(true);
			r.addAttribute(new Rigidbody2D(r));
			Rigidbody2D rb1 = r.getAttribute(Rigidbody2D.class);
			rb1.velocity = new Vector2(new Random().nextFloat(-1f, 1f)*20, new Random().nextFloat(-1f, 1f)*20);
			gameObjects.add(r);
		}
	}
	
	public void doDraws() {
		sr.fillCanvas(Color.DARKGRAY);
		
		//do world draws
		for(GameObject obj : gameObjects) {
			if(obj.drawable.hasTexture) sr.draw(obj.drawable.texture);
			else sr.draw(obj.drawable);
		}
		
	}

}
