package com.codecool.snake.eventhandler;

import java.util.HashMap;

import javafx.scene.input.KeyCode;


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

    private InputHandler() {
        // singleton needs the class to have private constructor
    }
}
