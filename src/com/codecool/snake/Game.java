package com.codecool.snake;

import com.codecool.snake.entities.enemies.SimpleEnemy;
import com.codecool.snake.entities.powerups.SimplePowerup;
import com.codecool.snake.entities.snakes.Snake;
import com.codecool.snake.entities.snakes.SnakeHead;
import com.sun.javafx.geom.Vec2d;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import jdk.nashorn.internal.objects.Global;

//TODO: make a GameScene?
public class Game extends Pane {

    public static Snake snake = null;
    public static GameLoop gameLoop = new GameLoop();
    Timeline looper = new Timeline();



    public Game() {
        setupResources();
        setupLooper();
        spawnSnake();
        spawnEnemies(4);
        spawnPowerUps(4);

        looper.play();
    }

    private void setupLooper() {
        looper.setCycleCount( Timeline.INDEFINITE );
        final long timeStart = System.currentTimeMillis();

        KeyFrame kf = new KeyFrame(
                Duration.seconds(0.017),                // 60 FPS
                ae -> {
                    double t = (System.currentTimeMillis() - timeStart) / 1000.0;
                    gameLoop.step(t);
                });

        looper.getKeyFrames().add( kf );

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
        snake = new Snake(this, new Vec2d(500, 500));
    }
    private void spawnEnemies(int numberOfEnemies) {
        for(int i = 0; i < numberOfEnemies; ++i) new SimpleEnemy(this);
    }

    private void spawnPowerUps(int numberOfPowerUps) {
        for(int i = 0; i < numberOfPowerUps; ++i) new SimplePowerup(this);
    }

    private void setupInputHandling() {
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
    }
}
