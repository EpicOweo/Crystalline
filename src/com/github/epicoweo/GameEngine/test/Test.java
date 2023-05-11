package com.github.epicoweo.GameEngine.test;

import com.github.epicoweo.GameEngine.main.EngineConfig;
import com.github.epicoweo.GameEngine.main.GameEngine;

public class Test {
	
	public static void main(String[] args) {
		GameEngine engine = new GameEngine();
		
		EngineConfig config = new EngineConfig();
		
		config.setWindowSize(1280, 720);
		config.setTitle("Game");
		
		engine.setConfig(config);
		engine.setScene(new MainScene());
		
		engine.startEngine(args);
	}
}
