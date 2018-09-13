package com.codecool.snake.eventhandler;

import com.codecool.snake.Globals;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.HashMap;

public class InputHandler {
    private static InputHandler instance = null;
    private HashMap<KeyCode, Boolean> keyStates = new HashMap<>();

    public static InputHandler getInstance() {
        if(instance == null) instance = new InputHandler();
        return instance;
    }

    public void setKeyPressed(KeyCode key) {
        keyStates.put(key, true);
    }

    public void setKeyReleased(KeyCode key) {
        keyStates.put(key, false);
    }

    public Boolean isKeyPressed(KeyCode key) {
        if(keyStates.containsKey(key)) return keyStates.get(key);
        return false;
    }
}
