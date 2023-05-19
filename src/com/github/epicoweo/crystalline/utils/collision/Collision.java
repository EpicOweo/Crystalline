package com.github.epicoweo.crystalline.utils.collision;

import com.epicoweo.math.linearalgebra.Vector2;
import com.github.epicoweo.crystalline.gameobjects.GameRect;
import com.github.epicoweo.crystalline.gameobjects.drawables.Line;
import com.github.epicoweo.crystalline.gameobjects.obj2d.Rigidbody2D;
import com.github.epicoweo.crystalline.gameobjects.obj2d.Rigidbody2D.RBType;

public class Collision {

	public static void handleRectCollision(Rigidbody2D rb1, Rigidbody2D rb2, boolean horizontalCollision) {
		
		Vector2 v1 = rb1.velocity.duplicate().getColVector(0).toVector2();
		Vector2 v2 = rb2.velocity.duplicate().getColVector(0).toVector2();
		
		Vector2 vPrime1 = new Vector2(0, 0);
		Vector2 vPrime2 = new Vector2(0, 0);
		
		float m1 = rb1.mass;
		float m2 = rb2.mass;
		
		if(horizontalCollision) {
			if(rb1.type == RBType.STATIC && rb2.type == RBType.KINEMATIC) {
				rb2.velocity.x = 0;
			} else if(rb2.type == RBType.STATIC && rb1.type == RBType.KINEMATIC) {
				rb1.velocity.x = 0;
			} else if(rb1.type == RBType.STATIC && rb2.type == RBType.DYNAMIC) {
				rb2.velocity.x = -rb2.velocity.x;
			} else if(rb2.type == RBType.STATIC && rb1.type == RBType.DYNAMIC) {
				rb1.velocity.x = -rb1.velocity.x;
			} else if(rb1.type == RBType.DYNAMIC && rb2.type == RBType.DYNAMIC) {
				vPrime1.x = ((m1 - m2)/(m1 + m2))*v1.x + ((2*m2)/(m1 + m2))*v2.x;
				
				vPrime2.x = ((m1 - m2)/(m1 + m2))*v2.x + ((2*m1)/(m1 + m2))*v1.x;
				
				rb1.velocity.x = vPrime1.x;
				rb2.velocity.x = vPrime2.x;
			} else if(rb1.type == RBType.KINEMATIC && rb2.type == RBType.KINEMATIC) {
				rb1.velocity.x = 0;
				rb2.velocity.x = 0;
			} else if(rb1.type == RBType.DYNAMIC && rb2.type == RBType.KINEMATIC) {
				vPrime1.x = ((m1 - m2)/(m1 + m2))*v1.x + ((2*m2)/(m1 + m2))*v2.x;
				rb1.velocity.x = vPrime1.x;
			} else if(rb1.type == RBType.KINEMATIC && rb2.type == RBType.DYNAMIC) {
				vPrime2.x = ((m1 - m2)/(m1 + m2))*v1.x + ((2*m2)/(m1 + m2))*v2.x;
				rb2.velocity.x = vPrime2.x;
			}
		} else {
			if(rb1.type == RBType.STATIC && rb2.type == RBType.KINEMATIC) {
				rb2.velocity.y = 0;
			} else if(rb2.type == RBType.STATIC && rb1.type == RBType.KINEMATIC) {
				rb1.velocity.y = 0;
			} else if(rb1.type == RBType.STATIC && rb2.type == RBType.DYNAMIC) {
				rb2.velocity.y = -rb2.velocity.y;
			} else if(rb2.type == RBType.STATIC && rb1.type == RBType.DYNAMIC) {
				rb1.velocity.y = -rb1.velocity.y;
			} else if(rb1.type == RBType.DYNAMIC && rb2.type == RBType.DYNAMIC) {
				vPrime1.y = ((m1 - m2)/(m1 + m2))*v1.y + ((2*m2)/(m1 + m2))*v2.y;
				vPrime2.y = ((m1 - m2)/(m1 + m2))*v2.y + ((2*m1)/(m1 + m2))*v1.y;
				
				rb1.velocity.y = vPrime1.y;
				rb2.velocity.y = vPrime2.y;
			} else if(rb1.type == RBType.KINEMATIC && rb2.type == RBType.KINEMATIC) {
				rb1.velocity.y = 0;
				rb2.velocity.y = 0;
			} else if(rb1.type == RBType.DYNAMIC && rb2.type == RBType.KINEMATIC) {
				vPrime1.y = ((m1 - m2)/(m1 + m2))*v1.y + ((2*m2)/(m1 + m2))*v2.y;
				rb1.velocity.y = vPrime1.y;
			} else if(rb1.type == RBType.KINEMATIC && rb2.type == RBType.DYNAMIC) {
				vPrime2.y = ((m1 - m2)/(m1 + m2))*v2.y + ((2*m1)/(m1 + m2))*v1.y;
				rb2.velocity.y = vPrime2.y;
			}
		}
		
	}
	
	public static boolean overlaps(GameRect r1, GameRect  r2) {
		
		Vector2 pt0_1 = new Vector2(r1.position.x, r1.position.y);
		Vector2 pt0_2 = new Vector2(r1.position.x + r1.width, r1.position.y);
		Vector2 pt0_3 = new Vector2(r1.position.x + r1.width, r1.position.y + r1.height);
		Vector2 pt0_4 = new Vector2(r1.position.x, r1.position.y + r1.height);
		
		Vector2 pt1_1 = new Vector2(r2.position.x, r2.position.y);
		Vector2 pt1_2 = new Vector2(r2.position.x + r2.width, r2.position.y);
		Vector2 pt1_3 = new Vector2(r2.position.x + r2.width, r2.position.y + r2.height);
		Vector2 pt1_4 = new Vector2(r2.position.x, r2.position.y + r2.height);
		
		//check both rects with all vertices of the opposite rect for collision
		
		if(overlaps(pt0_1, r2) || overlaps(pt0_2, r2) || overlaps(pt0_3, r2) || overlaps(pt0_4, r2)) {
			return true;
		} else if(overlaps(pt1_1, r1) || overlaps(pt1_2, r1) || overlaps(pt1_3, r1) || overlaps(pt1_4, r1)) {
			return true;
		}
		
		return false;
		
	}
	
	public static boolean overlaps(Vector2 v, GameRect r) { // vector2 is inside of rectangle
		return v.x > r.position.x && v.x < r.position.x + r.width
				&& v.y > r.position.y && v.y < r.position.y + r.height;
	}
	
	public static boolean overlaps(Line l, GameRect r) { // line is inside of rectangle
		
		int divSize = 5;
		
		if(l.vertical) {
			
			int totalDivs = (int)Math.ceil((l.p2.y-l.p1.y)/divSize);
			
			// cut the line into pieces of size divSize and check whether those vertices collide with the rect
			for(int i = 0; i < totalDivs; i++) { 
				Vector2 vertex = new Vector2(l.p1.x, i*divSize + l.p1.y);
				if(overlaps(vertex, r)) return true;
			}
			
			return false;
		} else if(l.slope == 0) {
			
			int totalDivs = (int)Math.ceil((l.p2.x-l.p1.x)/divSize);
			
			// cut the line into pieces of size divSize and check whether those vertices collide with the rect
			for(int i = 0; i < totalDivs; i++) { 
				Vector2 vertex = new Vector2(i*divSize + l.p1.x, l.p1.y);
				if(overlaps(vertex, r)) return true;
			}
			
			return false;
		}
		return false;
	}
}
