package com.josephsullivan256.gmail.math.linalg;

public class Vec2 {
	public final float x, y;
	
	public Vec2(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vec2 plus(Vec2 v) {
		return new Vec2(x+v.x,y+v.y);
	}
	
	public Vec2 scaledBy(float f) {
		return new Vec2(x*f, y*f);
	}
	
	public Vec2 minus(Vec2 v) {
		return plus(v.scaledBy(-1f));
	}
	
	public float dot(Vec2 v) {
		return x*v.x+y*v.y;
	}
	
	public float magnitude2() {
		return dot(this);
	}
	
	public float magnitude() {
		return (float) Math.sqrt(magnitude2());
	}
	
	public static final Vec2 zero = new Vec2(0,0);
}
