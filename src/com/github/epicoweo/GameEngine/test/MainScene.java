package com.github.epicoweo.GameEngine.test;

import java.util.ArrayList;
import java.util.Random;

import com.epicoweo.math.linearalgebra.Vector2;
import com.epicoweo.math.linearalgebra.Vector3;
import com.github.epicoweo.GameEngine.gameobjects.GameObject;
import com.github.epicoweo.GameEngine.gameobjects.GameRect;
import com.github.epicoweo.GameEngine.gameobjects.obj2d.Attribute.AttributeType;
import com.github.epicoweo.GameEngine.gameobjects.obj2d.Rigidbody2D.RBType;
import com.github.epicoweo.GameEngine.gameobjects.obj2d.Rigidbody2D.TriggerType;
import com.github.epicoweo.GameEngine.gameobjects.obj2d.Rigidbody2D;
import com.github.epicoweo.GameEngine.main.GameEngine;
import com.github.epicoweo.GameEngine.main.GameScene;
import com.github.epicoweo.GameEngine.main.drawables.Line;
import com.github.epicoweo.GameEngine.main.drawables.Rectangle;
import com.github.epicoweo.GameEngine.renderers.ShapeRenderer2D;
import com.github.epicoweo.GameEngine.test.scripts.Player;
import com.github.epicoweo.GameEngine.utils.Input;
import com.github.epicoweo.GameEngine.utils.collision.SpacePartition;

import javafx.scene.paint.Color;

public class MainScene extends GameScene {

	public static ShapeRenderer2D sr;
	
	public MainScene() {
		sr = new ShapeRenderer2D();
		gameObjects = new ArrayList<GameObject>();
		init();
	}
	
	private void init() {
		Player p = new Player();
		((Rectangle)p.drawable).setFilled(true);
		
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
		
		GameRect topWall = new GameRect(-20, -20, 1320, 20);
		topWall.addAttribute(new Rigidbody2D(topWall, RBType.STATIC));
		GameRect bottomWall = new GameRect(-20, 720, 1320, 20);
		bottomWall.addAttribute(new Rigidbody2D(bottomWall, RBType.STATIC));
		GameRect leftWall = new GameRect(-20, -20, 20, 720);
		leftWall.addAttribute(new Rigidbody2D(leftWall, RBType.STATIC));
		GameRect rightWall = new GameRect(1280, -20, 20, 720);
		rightWall.addAttribute(new Rigidbody2D(rightWall, RBType.STATIC));
		
		gameObjects.add(p);
		gameObjects.add(topWall);
		gameObjects.add(bottomWall);
		gameObjects.add(leftWall);
		gameObjects.add(rightWall);
	}
	
	@Override
	public void update(float dt) {
		doDraws();
		
		
		if(GameEngine.getConfig().doSpacePartitioning) {
			SpacePartition.partitionObjects();
		}
		
		for(GameObject obj : gameObjects) {
			obj.update(dt);
		}
		
		lateUpdate(dt);
	}
	
	public void lateUpdate(float dt) {
		//reset all static positions cause they're moving fsr
	}
	
	public void stressTest() {
		int squares = 100;
		
		for(int i = 0; i < squares; i++) {
			GameRect r = new GameRect(new Random().nextInt(0, 1280), new Random().nextInt(0, 720), 10, 10);
			((Rectangle)r.drawable).setFilled(true);
			r.addAttribute(new Rigidbody2D(r));
			Rigidbody2D rb1 = r.getAttribute(Rigidbody2D.class);
			rb1.velocity = new Vector2(new Random().nextFloat(-1f, 1f)*20, new Random().nextFloat(-1f, 1f)*20);
			gameObjects.add(r);
		}
	}
	
	public void doDraws() {
		sr.fillCanvas(Color.DARKGRAY);
		
		for(GameObject obj : gameObjects) {
			obj.drawable.position = new Vector3(obj.position.x, obj.position.y, 0);
			sr.setColor(obj.drawable.color);
			sr.draw(obj.drawable);
		}
		
	}

}
