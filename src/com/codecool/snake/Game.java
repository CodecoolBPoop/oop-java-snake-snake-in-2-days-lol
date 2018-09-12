package com.codecool.snake;

import com.codecool.snake.entities.enemies.SimpleEnemy;
import com.codecool.snake.entities.powerups.SimplePowerup;
import com.codecool.snake.entities.snakes.Snake;
import com.codecool.snake.entities.snakes.SnakeHead;
import com.sun.javafx.geom.Vec2d;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import jdk.nashorn.internal.objects.Global;

//TODO: make a GameScene?
public class Game extends Pane {

    public static Snake snake = null;

    public Game() {
        setupResources();
        snake = new Snake(this, new Vec2d(500, 500));

        spawnEnemies(4);
        spawnPowerUps(4);
    }

    private void setupResources() {
        Globals.resources.addImage("SnakeHead", new Image("snake_head.png"));
        Globals.resources.addImage("SnakeBody", new Image("snake_body.png"));
        Globals.resources.addImage("SimpleEnemy", new Image("simple_enemy.png"));
        Globals.resources.addImage("PowerUpBerry", new Image("powerup_berry.png"));
    }

    public void start() {
        Scene scene = getScene();
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:  Globals.leftKeyDown  = true; break;
                case RIGHT: Globals.rightKeyDown  = true; break;
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case LEFT:  Globals.leftKeyDown  = false; break;
                case RIGHT: Globals.rightKeyDown  = false; break;
            }
        });
        Globals.gameLoop = new GameLoop();
        Globals.gameLoop.start();
    }

    private void spawnEnemies(int numberOfEnemies) {
        for(int i = 0; i < numberOfEnemies; ++i) new SimpleEnemy(this);
    }

    private void spawnPowerUps(int numberOfPowerUps) {
        for(int i = 0; i < numberOfPowerUps; ++i) new SimplePowerup(this);
    }
}
