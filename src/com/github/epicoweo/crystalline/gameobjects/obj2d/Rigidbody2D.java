package com.github.epicoweo.crystalline.gameobjects.obj2d;

import java.util.ArrayList;

import com.epicoweo.math.linearalgebra.Vector2;
import com.epicoweo.math.linearalgebra.Vector3;
import com.github.epicoweo.crystalline.gameobjects.GameObject;
import com.github.epicoweo.crystalline.gameobjects.GameRect;
import com.github.epicoweo.crystalline.gameobjects.drawables.Line;
import com.github.epicoweo.crystalline.main.GameEngine;
import com.github.epicoweo.crystalline.utils.collision.Collision;
import com.github.epicoweo.crystalline.utils.collision.SpacePartition;

public class Rigidbody2D extends Attribute {
	
	Vector2 dim;
	GameObject obj;
	
	public Vector2 velocity;
	public Vector2 acceleration;
	public Vector2 kineticEnergy;
	
	public float mass = 1;
	
	public boolean hasCollided = false;
	
	public boolean isTrigger = false;
	private Runnable trigger;
	public boolean triggered = false;
	public boolean multiTrigger = false;
	
	public RBType type = RBType.DYNAMIC;
	public RBShape shape = RBShape.RECTANGLE;
	public TriggerType triggerType = TriggerType.DISCONTINUOUS;
	
	public static ArrayList<Rigidbody2D> loadedBodies = new ArrayList<Rigidbody2D>();
	
	//add CONDITIONAL, SINGLE;
	public enum TriggerType {
		CONTINUOUS, DISCONTINUOUS, SINGLE_USE;
	}
	
	public enum RBType {
		DYNAMIC, STATIC, KINEMATIC;
	}
	
	public enum RBShape {
		RECTANGLE, CIRCLE;
	}
	
	public Rigidbody2D(GameObject obj, RBType type) {
		this.obj = obj;
		this.type = type;
		velocity = new Vector2(0, 0);
		acceleration = new Vector2(0, 0);
		kineticEnergy = new Vector2(0, 0);
		loadedBodies.add(this);
	}
	
	public Rigidbody2D(GameObject obj) {
		this(obj, RBType.DYNAMIC);
	}
	
	@Override
	public void update(float dt) {
		
		hasCollided = false;
		
		accelerate(dt);
		move(dt);
		computePhysics();
			
		checkCollision(dt);
		
		if(!hasCollided) triggered = false;
	}
	
	public void setTrigger(Runnable tr) {
		this.trigger = tr;
	}
	
	public void trigger() {
		if(!trigger.equals(null)) {
			if((!triggered && triggerType != TriggerType.CONTINUOUS) || triggerType == TriggerType.CONTINUOUS) {
				trigger.run();
				triggered = true;
			}
		} else {
			throw new NullPointerException("Undefined trigger");
		}
	}
	
	private void computePhysics() {
		kineticEnergy.x = 0.5f * mass * velocity.x*velocity.x; // 1/2 mv^2
		kineticEnergy.y = 0.5f * mass * velocity.y*velocity.y;
	}
	
	private void accelerate(float dt) {
		velocity.add(new Vector2(acceleration.x*dt, acceleration.y*dt));
	}
	
	private void move(float dt) {
		obj.position.add(new Vector2(velocity.x*dt, velocity.y*dt));
		obj.drawable.setPos(new Vector3(velocity.x*dt, velocity.y*dt, 0).add(obj.drawable.position));
	}

	private ArrayList<ArrayList<GameObject>> getValidPartitions() {
		System.out.println("getvalidpartitions");
		ArrayList<ArrayList<GameObject>> checkPartitions = new ArrayList<ArrayList<GameObject>>();
		checkPartitions.add(SpacePartition.partitions.get(obj.partitionY).get(obj.partitionX));
		if(obj.partitionY-1 >= 0) {
			checkPartitions.add(SpacePartition.partitions.get(obj.partitionY-1).get(obj.partitionX));
			if(obj.partitionX-1 >= 0) {
				checkPartitions.add(SpacePartition.partitions.get(obj.partitionY-1).get(obj.partitionX-1));
			}
			if(obj.partitionX+1 < SpacePartition.cols) {
				checkPartitions.add(SpacePartition.partitions.get(obj.partitionY-1).get(obj.partitionX+1));
			}
		}
		if(obj.partitionY+1 < SpacePartition.rows) {
			checkPartitions.add(SpacePartition.partitions.get(obj.partitionY+1).get(obj.partitionX));
			if(obj.partitionX-1 >= 0) {
				checkPartitions.add(SpacePartition.partitions.get(obj.partitionY+1).get(obj.partitionX-1));
			}
			if(obj.partitionX+1 < SpacePartition.cols) {
				checkPartitions.add(SpacePartition.partitions.get(obj.partitionY+1).get(obj.partitionX+1));
			}
		}
		if(obj.partitionX-1 >= 0) {
			checkPartitions.add(SpacePartition.partitions.get(obj.partitionY).get(obj.partitionX-1));
		}
		if(obj.partitionX+1 < SpacePartition.cols) {
			checkPartitions.add(SpacePartition.partitions.get(obj.partitionY).get(obj.partitionX+1));
		}
		
		return checkPartitions;
	}
	
	
	
	private void checkCollision(float dt) {
		//if(GameEngine.getConfig().doSpacePartitioning) {
		//	ArrayList<ArrayList<GameObject>> checkPartitions = getValidPartitions();
		//	for(ArrayList<GameObject> arr : checkPartitions) {
		//		for(GameObject o : arr) {
		//			if(o instanceof GameRect || o instanceof GameRect) {
		//				if(Collision.overlaps((GameRect)o, (GameRect)this.obj)) {
		//				System.out.println("doin collision");
		//					doCollision(dt, loadedBodies.indexOf(o.getAttribute(Rigidbody2D.class)));
		//				}
		//			}
		//		}
		//		
		//	}
		//} else {
			for(Rigidbody2D rb : loadedBodies) {
				if(rb.obj instanceof GameRect || rb.obj instanceof GameRect) {
					if(Collision.overlaps((GameRect)rb.obj, (GameRect)this.obj)) {
						hasCollided = true;
						rb.hasCollided = true;
						if(rb.isTrigger) { rb.trigger(); continue; }
						if(this.isTrigger) { this.trigger(); continue; }
						doCollision(dt, loadedBodies.indexOf(rb));
					}
				}
			}
		//}
		
	}
	
	private void doCollision(double dt, int index) {
		
		Rigidbody2D rb = loadedBodies.get(index);
		GameRect obj = (GameRect)rb.obj;
		
		
		//this colliding with OBJ's left, right, top, or bottom
		Line left = new Line(obj.position.x, obj.position.y,
				obj.position.x, obj.position.y + obj.height); // left line
		Line right = new Line(obj.position.x + obj.width, obj.position.y,
				obj.position.x + obj.width, obj.position.y + obj.height); // right line
		Line top = new Line(obj.position.x, obj.position.y + obj.height,
				obj.position.x + obj.width, obj.position.y + obj.height); // top line
		Line bottom = new Line(obj.position.x, obj.position.y,
				obj.position.x + obj.width, obj.position.y); // bottom line
		
		if(this.obj instanceof GameRect) {
			if(Collision.overlaps(left, (GameRect)this.obj)) {
				this.obj.position.x = left.p1.x - ((GameRect)(this.obj)).width;
				Collision.handleRectCollision(this, rb, true);
				
			} else if(Collision.overlaps(right, (GameRect)this.obj)) {
				this.obj.position.x = right.p1.x;
				Collision.handleRectCollision(this, rb, true);
			}
			
			if(Collision.overlaps(top, (GameRect)this.obj)) {
				this.obj.position.y = top.p1.y + 1;
				Collision.handleRectCollision(this, rb, false);
			} else if(Collision.overlaps(bottom, (GameRect)this.obj)) {
				this.obj.position.y = bottom.p1.y - ((GameRect)(this.obj)).height - 1;
				Collision.handleRectCollision(this, rb, false);
			} else {
				
			}
		}
		
		
	}
}
