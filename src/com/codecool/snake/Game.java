package com.codecool.snake;

import com.codecool.snake.entities.enemies.SimpleEnemy;
import com.codecool.snake.entities.powerups.SimplePowerUp;
import com.codecool.snake.entities.snakes.Snake;
import com.codecool.snake.eventhandler.InputHandler;

import com.sun.javafx.geom.Vec2d;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;


public class Game extends Pane {
    public static GameLoop gameLoop;
    private Snake snake = null;
    private GameTimer gameTimer = new GameTimer();


    public Game() {
        Globals.display = new Display(this);
        setupResources();
        spawnSnake();
        spawnEnemies(4);
        spawnPowerUps(4);

        gameLoop = new GameLoop(snake);
        gameTimer.setup(gameLoop::step);
        gameTimer.play();
    }

    private void setupResources() {
        Globals.resources.addImage("SnakeHead", new Image("snake_head.png"));
        Globals.resources.addImage("SnakeBody", new Image("snake_body.png"));
        Globals.resources.addImage("SimpleEnemy", new Image("simple_enemy.png"));
        Globals.resources.addImage("PowerUpBerry", new Image("powerup_berry.png"));
    }

    public void start() {
        setupInputHandling();
        gameLoop.start();
    }

    private void spawnSnake() {
        snake = new Snake(new Vec2d(500, 500));
    }
    private void spawnEnemies(int numberOfEnemies) {
        for(int i = 0; i < numberOfEnemies; ++i) new SimpleEnemy();
    }

    private void spawnPowerUps(int numberOfPowerUps) {
        for(int i = 0; i < numberOfPowerUps; ++i) new SimplePowerUp();
    }

    private void setupInputHandling() {
        Scene scene = getScene();
        scene.setOnKeyPressed(event -> InputHandler.getInstance().setKeyPressed(event.getCode()));
        scene.setOnKeyReleased(event -> InputHandler.getInstance().setKeyReleased(event.getCode()));
    }
}
