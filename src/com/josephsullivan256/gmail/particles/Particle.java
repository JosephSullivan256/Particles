package com.josephsullivan256.gmail.particles;

import com.josephsullivan256.gmail.math.linalg.Vec2;

public class Particle {
	private Vec2 pos;
	private Vec2 vel;
	private float mass;
	
	private Vec2 accel;
	
	public Particle(Vec2 pos, Vec2 vel, float mass) {
		this.pos = pos;
		this.vel = vel;
		this.mass = mass;
		this.accel = Vec2.zero;
	}
	
	public Particle(Vec2 pos, float mass) {
		this(pos, Vec2.zero, mass);
	}
	
	public void applyForce(Vec2 f) {
		accel = accel.plus(f.scaledBy(1f/mass));
	}
	
	public void integrate(float t) {
		Vec2 oldVel = vel;
		vel = vel.plus(accel.scaledBy(t));
		pos = pos.plus(vel.plus(oldVel).scaledBy(t/2f));
		accel = Vec2.zero;
	}
	
	public Vec2 getPos() {
		return pos;
	}
	
	public Vec2 getVel() {
		return vel;
	}
	
	public float getMass() {
		return mass;
	}
}
