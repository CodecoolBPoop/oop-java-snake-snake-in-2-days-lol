package com.codecool.snake;

import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.Animatable;

public class GameLoop {
    private boolean running = false;

    public void start() {
        running = true;
    }

    public void stop() {
        running = false;
    }

    public void step() {
        if(running) {
            Game.snake.step();
            for (GameEntity gameObject : Globals.gameObjects.getList()) {
                if (gameObject instanceof Animatable) {
                    ((Animatable) gameObject).step();
                }
            }
        }
        Globals.gameObjects.doPendingModifications();
    }
}
