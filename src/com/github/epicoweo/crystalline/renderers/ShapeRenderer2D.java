package com.github.epicoweo.crystalline.renderers;

import com.epicoweo.math.linearalgebra.Vector3;
import com.github.epicoweo.crystalline.gameobjects.drawables.Drawable;
import com.github.epicoweo.crystalline.gameobjects.drawables.Line;
import com.github.epicoweo.crystalline.gameobjects.drawables.Rectangle;
import com.github.epicoweo.crystalline.gameobjects.drawables.Shape2D;
import com.github.epicoweo.crystalline.gameobjects.drawables.Texture;

public class ShapeRenderer2D extends Renderer2D {
	
	public ShapeRenderer2D() {
	}
	/**
	 * Draws a Drawable object to the screen.
	 * 
	 * @param d		The Drawable object to be drawn
	 */
	@Override
	public void draw(Drawable d) {
		if(d instanceof Shape2D) {
			setColor(d.color);
			draw((Shape2D)d);
		}
	}
	
	public void draw(Texture tex) {
		
		Vector3 pos = camera.toScreen(tex.position);
		
		gc.drawImage(tex.getImage(), pos.x, pos.y);
	}
	
	protected void draw(Shape2D shape) {
		if(shape instanceof Rectangle) {
			draw((Rectangle) shape);
		} else if(shape instanceof Line) {
			draw((Line) shape);
		}
	}
	
	protected void draw(Rectangle rect) {
		gc.setStroke(color);
		Vector3 pos = camera.toScreen(rect.position);
		
		if(rect.filled) {
			for(int i = (int) pos.x; i < pos.x + rect.width; i++) {
				gc.strokeLine(i, pos.y, i, pos.y+rect.height);
			}
		} else {
			for(Line line : rect.lines) {
				draw(line);
			}
		}
	}
	
	protected void draw(Line line) {
		gc.setStroke(color);
		//draw my own line later?
		Vector3 pos1 = camera.toScreen(line.p1);
		Vector3 pos2 = camera.toScreen(line.p2);
		gc.strokeLine((int)pos1.x, (int)pos1.y, (int)pos2.x, (int)pos2.y);
	}
}
