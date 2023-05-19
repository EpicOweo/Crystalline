package com.github.epicoweo.crystalline.demo.etc;

import com.github.epicoweo.crystalline.gameobjects.GameRect;
import com.github.epicoweo.crystalline.gameobjects.drawables.Shape2D;
import com.github.epicoweo.crystalline.gameobjects.drawables.Texture;
import com.github.epicoweo.crystalline.gameobjects.drawables.Texture.Interpolation;
import com.github.epicoweo.crystalline.gameobjects.obj2d.Rigidbody2D;
import com.github.epicoweo.crystalline.gameobjects.obj2d.Rigidbody2D.RBType;
import com.github.epicoweo.crystalline.handlers.InputHandler;

import javafx.scene.input.KeyCode;

public class Player extends GameRect {

	public static float speed = 20f;
	public Rigidbody2D rb;
	
	public Player() {
		super(100, 100, 40, 40);
		rb = new Rigidbody2D(this, RBType.KINEMATIC);
		addAttribute(rb);
		this.drawable.setTexture(new Texture("./assets/textures/eeeeeeeeeeee.png"));
		getTexture().scale(321, 173, Interpolation.NEAREST_NEIGHBOR);
	}
	
	public Texture getTexture() {
		return this.drawable.texture;
	}
	
	@Override
	public void update(float dt) {
		doInput(dt);
		super.update(dt);
	}
	
	
	public void doInput(float dt) {
		if(InputHandler.getKey(KeyCode.W)) {
			rb.velocity.y = -Player.speed;
		} else if(InputHandler.getKey(KeyCode.S)) {
			rb.velocity.y = Player.speed;
		} else {
			rb.velocity.y = 0;
		}
		
		if(InputHandler.getKey(KeyCode.A)) {
			rb.velocity.x = -Player.speed;
		} else if(InputHandler.getKey(KeyCode.D)) {
			rb.velocity.x = Player.speed;
		} else {
			rb.velocity.x = 0;
		}
	}
	
	
	

}
