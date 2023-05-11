package com.github.epicoweo.GameEngine.test.scripts;

import com.github.epicoweo.GameEngine.gameobjects.GameRect;
import com.github.epicoweo.GameEngine.gameobjects.obj2d.Rigidbody2D;
import com.github.epicoweo.GameEngine.gameobjects.obj2d.Rigidbody2D.RBType;
import com.github.epicoweo.GameEngine.utils.Input;

import javafx.scene.input.KeyCode;

public class Player extends GameRect {

	public static float speed = 20f;
	public Rigidbody2D rb;
	
	public Player() {
		super(100, 100, 40, 40);
		rb = new Rigidbody2D(this, RBType.KINEMATIC);
		addAttribute(rb);
	}
	
	@Override
	public void update(float dt) {
		doInput(dt);
		super.update(dt);
	}
	
	
	public void doInput(float dt) {
		if(Input.getKey(KeyCode.W)) {
			rb.velocity.y = -Player.speed;
		} else if(Input.getKey(KeyCode.S)) {
			rb.velocity.y = Player.speed;
		} else {
			rb.velocity.y = 0;
		}
		
		if(Input.getKey(KeyCode.A)) {
			rb.velocity.x = -Player.speed;
		} else if(Input.getKey(KeyCode.D)) {
			rb.velocity.x = Player.speed;
		} else {
			rb.velocity.x = 0;
		}
	}
	
	
	

}
