package com.github.epicoweo.crystalline.renderers;

import com.github.epicoweo.crystalline.gameobjects.Camera;
import com.github.epicoweo.crystalline.gameobjects.drawables.Drawable;
import com.github.epicoweo.crystalline.gameobjects.drawables.Texture;
import com.github.epicoweo.crystalline.main.GameEngine;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Renderer {
	
	protected Paint color;
	protected Canvas canvas;
	protected GraphicsContext gc;
	public Camera camera;
	
	public Renderer() {
		GameEngine.registerRenderer(this);
		setColor(Color.BLACK);
	}
	
	public void fillCanvas(Paint c) {
		gc.setFill(c);
        gc.fillRect(0,0,GameEngine.getConfig().width, GameEngine.getConfig().height);
    }
	
	public void draw(Drawable d) {}
	
	public void setColor(Paint c) {
		this.color = c;
	}

	public void createCanvas() {
		canvas = new Canvas(GameEngine.getConfig().width, GameEngine.getConfig().height);
		gc = canvas.getGraphicsContext2D();
		
	}

	public Canvas getCanvas() {
		return canvas;
	}
}
