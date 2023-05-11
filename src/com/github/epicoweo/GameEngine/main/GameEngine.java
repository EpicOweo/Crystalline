package com.github.epicoweo.GameEngine.main;

import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.github.epicoweo.GameEngine.renderers.Renderer;
import com.github.epicoweo.GameEngine.utils.Input;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameEngine extends Application {
	
	public static boolean stop = false;
	private static EngineConfig config;
	public static JFrame frame;
	private static ArrayList<Renderer> renderers = new ArrayList<Renderer>();
	static Toolkit toolkit;
	public static Canvas canvas;
	
	
	static GameScene gameScene;
	public static Scene scene;
	public static Stage stage;
	public static Pane pane;
	
	private static long time = 0;
	
	public GameEngine() {
		toolkit = Toolkit.getDefaultToolkit();
	}
	
	public void setScene(GameScene s) {
		GameScene.engine = this;
		GameEngine.gameScene = s;
	}
	
	public void start() {
		System.setProperty("java.awt.headless", "false");
		
		time = System.nanoTime();
		
		gameLoop();
		
	}
	
	public void gameLoop() {
		new AnimationTimer() {
			public void handle(long currentNanoTime) {
				//System.out.println("beginning of loop");
				//do fps stuff
				float dt = (currentNanoTime - time) / 1000000;
				if(config.fpsCap) {
					if(dt < config.spf) {
						return;
					}
				}
				//System.out.println("pre-update");
				gameScene.update(dt/100); // do user game loop
				
				time = System.nanoTime();
				if((currentNanoTime - time) / 1000000 > config.spf) config.spf += 0.01;
				//System.out.println("end of loop");
			}
			
		}.start();
	}
	
	public void update() {
		
	}
	
	private void showWindow() {
		stage.show();
	}
	
	private void createWindow() {
		pane = new Pane();
		
		for(Renderer r : GameEngine.renderers) {
			r.createCanvas();
		}
		
		for(Renderer r : renderers) {
			pane.getChildren().add(r.getCanvas());
		}
		stage.setTitle(config.title);
		scene = new Scene(pane, config.width, config.height);
		stage.setScene(scene);
	}

	public void setConfig(EngineConfig config) {
		GameEngine.config = config;
	}
	
	public static EngineConfig getConfig() {
		return GameEngine.config;
	}

	public static void registerRenderer(Renderer renderer) {
		GameEngine.renderers.add(renderer);
	}

	@Override
	public void start(Stage s) throws Exception {
		stage = s;
		createWindow();
		Input.init();
		start();
		showWindow();
	}

	public void startEngine(String[] args) {
		launch(args);
		
	}
}
