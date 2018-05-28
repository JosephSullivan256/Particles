package com.josephsullivan256.gmail.gl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import com.josephsullivan256.gmail.math.linalg.Vec2;
import com.josephsullivan256.gmail.particles.Particle;
import com.josephsullivan256.gmail.particles.ParticleInteraction;

public class Main {
	
	public Main() {
		System.out.println("LWJGL " + Utils.getVersion());
		
		int width = 800;
		int height = 800;
		
		Utils.initGLFW();
		Window window = new Window("hello world",width,height);
		
		Utils.initGL();
		
		Shader shader = null;
		try {
			shader = new Shader(Utils.readFile("shaders/shader.vsh"),Utils.readFile("shaders/shader.fsh"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		VertexArrayObject vao = new VertexArrayObject();
		vao.initialize(
				BufferObject.vbo().bind().bufferData(new float[] {
						-1f,-1f,
						1f,-1f,
						-1f,1f,
						1f,1f,
				}, GL15.GL_STATIC_DRAW), 
				BufferObject.ebo().bind().bufferData(new int[] {
						0,1,2,1,2,3
				}, GL15.GL_STATIC_DRAW),
				new VertexAttributes().with(2, GL11.GL_FLOAT));
		
		List<Particle> particles = initParticles(40);
		ParticleInteraction p1 = new ParticleInteraction(0.01f, 2);
		ParticleInteraction p2 = new ParticleInteraction(-0.00001f, 4);
		float[] positions = new float[particles.size()*2];
		particlesToPositions(particles,positions);
		
		Uniform<float[]> positionsUniform = new Uniform<float[]>("positions",UniformPasser.uniformFloatsV2);
		
		GL11.glViewport(0, 0, width, height);
		
		GL11.glClearColor(1f, 1f, 1f, 0f);
		while(!window.shouldClose()) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			
			vao.bind();
			shader.use();
			positionsUniform.uniform(positions, shader);
			vao.draw(6);
			vao.unbind();
			
			apply(particles,p1);
			apply(particles,p2);
			update(particles,0.005f);
			particlesToPositions(particles,positions);
			
			window.swapBuffers();
			Utils.pollGLFWEvents();
		}
		
		window.destroy();
		Utils.terminateGLFW();
	}
	
	public List<Particle> initParticles(int count) {
		List<Particle> temp = new ArrayList<Particle>();
		
		/*for(float x = -0.9f; x < 0.9f; x+=0.1f) {
			for(float y = -0.9f; y < 0.9f; y+=0.1f) {
				temp.add(new Particle(new Vec2(x,y),0.1f));
			}
		}*/
		
		for(int i = 0; i < count; i++) {
			float x = ((float) Math.random())*2f-1f;
			float y = ((float) Math.random())*2f-1f;
			temp.add(new Particle(new Vec2(x,y),0.1f));
		}
		
		return temp;
	}
	
	public void particlesToPositions(List<Particle> particles, float[] positions) {
		int i = 0;
		for(Particle particle: particles) {
			positions[2*i] = particle.getPos().x;
			positions[2*i+1] = particle.getPos().y;
			i++;
		}
	}
	
	public void apply(List<Particle> particles, ParticleInteraction p) {
		for(int i = 0; i < particles.size(); i++) {
			for(int j = 0; j < i; j++) {
				p.apply(particles.get(i), particles.get(j));
			}
		}
	}
	
	public void update(List<Particle> particles, float t) {
		for(Particle particle: particles) {
			//particle.applyForce(particle.getVel().scaledBy(-0.1f));
			particle.integrate(t);
		}
	}
	
	public static void main(String[] args) {
		new Main();
	}
}
