package com.github.epicoweo.GameEngine.renderers;

import java.util.function.Consumer;

import com.github.epicoweo.GameEngine.main.drawables.Drawable;
import com.github.epicoweo.GameEngine.main.drawables.Line;
import com.github.epicoweo.GameEngine.main.drawables.Rectangle;
import com.github.epicoweo.GameEngine.main.drawables.Shape2D;

import javafx.scene.paint.Color;

public class ShapeRenderer2D extends Renderer2D {
	
	public ShapeRenderer2D() {
	}
	
	@Override
	public void draw(Drawable d) {
		if(d instanceof Shape2D) {
			draw((Shape2D)d);
		}
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
		if(rect.filled) {
			for(int i = (int) rect.x; i < rect.x + rect.width; i++) {
				gc.strokeLine(i, rect.y, i, rect.y+rect.height);
			}
		} else {
			for(Line line : rect.lines) {
				gc.strokeLine((int)line.p1.x, (int)line.p1.y, (int)line.p2.x, (int)line.p2.y);
			}
		}
	}
	
	protected void draw(Line line) {
		gc.setStroke(color);
		//draw my own line later?
		
		gc.strokeLine((int)line.p1.x, (int)line.p1.y, (int)line.p2.x, (int)line.p2.y);
	}
}
