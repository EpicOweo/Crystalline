package com.github.epicoweo.crystalline.handlers;

import java.util.HashMap;

import com.github.epicoweo.crystalline.main.GameEngine;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class InputHandler {
	
	public static HashMap<KeyCode, Boolean> keys = new HashMap<KeyCode, Boolean>();
	
	public static boolean getKey(KeyCode key) {
		try {
			return keys.get(key);
		} catch(NullPointerException e) {
			return false;
		}
	}
	
	public static void init() {
		GameEngine.pane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                keys.put(event.getCode(), true);
            }
        });
		GameEngine.pane.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                keys.put(event.getCode(), false);
            }
        });
		GameEngine.pane.requestFocus();
	}
}
