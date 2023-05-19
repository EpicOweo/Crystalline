package com.github.epicoweo.crystalline.main;

public class EngineConfig {

	public int width;
	public int height;
	public String title = "Game Engine";
	public int fps = 60;
	public float spf = 1f/fps;
	public boolean fpsCap = true;
	public boolean is3d = false;
	private boolean doSpacePartitioning = false;
	
	public EngineConfig() {
		
	}
	
	public void setWindowSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setFPS(int fps) {
		this.fps = fps;
		spf = 1f/fps;
		fpsCap = true;
	}
}
