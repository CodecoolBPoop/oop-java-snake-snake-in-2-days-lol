package com.codecool.snake.entities.snakes;

import com.codecool.snake.DelayedModificationList;

import com.codecool.snake.Game;

import com.codecool.snake.GameTimer;

import com.codecool.snake.Globals;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.eventhandler.InputHandler;

import com.sun.javafx.geom.Vec2d;
import javafx.animation.KeyFrame;
import javafx.scene.input.KeyCode;
import javafx.animation.Timeline;
import javafx.util.Duration;


public class Snake implements Animatable {
    private static float speed = 2;
    private int health = 100;

    private KeyCode leftDir= KeyCode.LEFT;
    private KeyCode rightDir= KeyCode.RIGHT;
    private KeyCode shootKey = KeyCode.M;

    private SnakeHead head;
    private DelayedModificationList<GameEntity> body;
    private HealthBar redHealth;
    private HealthBar healthBar;


    public HealthBar getGreenHealthBar(){
        return healthBar;
    }


    public Snake(Vec2d position) {
        head = new SnakeHead(this, position);
        body = new DelayedModificationList<>();
        redHealth = new HealthBar(false);
        healthBar = new HealthBar(true);

        addPart(4);
    }

    public Snake(Vec2d position, boolean second){
        head = new SnakeHead(this, position);
        body = new DelayedModificationList<>();
        redHealth = new HealthBar(false, second);
        healthBar = new HealthBar(true, second);

        addPart(4);
    }

    public void secondSnakeDiretionSetter (){
        leftDir = KeyCode.A;
        rightDir = KeyCode.D;
        shootKey = KeyCode.G;
    }

    public void step() {
        SnakeControl turnDir = getUserInput();
        boolean shoot = Shoot();

        head.updateRotation(turnDir, speed, shoot);

        updateSnakeBodyHistory();
        checkForGameOverConditions();

        body.doPendingModifications();
    }

    private SnakeControl getUserInput() {
        SnakeControl turnDir = SnakeControl.INVALID;
        if(InputHandler.getInstance().isKeyPressed(leftDir)) turnDir = SnakeControl.TURN_LEFT;
        if(InputHandler.getInstance().isKeyPressed(rightDir)) turnDir = SnakeControl.TURN_RIGHT;
        return turnDir;
    }

    private boolean Shoot(){
        boolean shoot = false;
        if(InputHandler.getInstance().isKeyPressed(shootKey)) shoot=true; InputHandler.getInstance().setKeyReleased(shootKey);
        return shoot;
    }

    public void addPart(int numParts) {
        GameEntity parent = getLastPart();
        Vec2d position = parent.getPosition();

        for (int i = 0; i < numParts; i++) {
            SnakeBody newBodyPart = new SnakeBody(position);
            body.add(newBodyPart);
        }
        Globals.getInstance().display.updateSnakeHeadDrawPosition(head);
    }

    public void changeHealth(int diff) {

        health += diff;
    }

    private void checkForGameOverConditions() {
        if (head.isOutOfBounds() || health <= 0) {
            System.out.println("Game Over");
            Globals.getInstance().stopGame();
        }
    }

    private void updateSnakeBodyHistory() {
        GameEntity prev = head;
        for(GameEntity currentPart : body.getList()) {
            currentPart.setPosition(prev.getPosition());
            prev = currentPart;
        }
    }

    private GameEntity getLastPart() {
        GameEntity result = body.getLast();

        if(result != null) return result;
        return head;
    }

    public void changeSpeed(float plusSpeed) {
        speed += plusSpeed;
    }

    public int getHealth() {
        return health;
    }

    public void setSpeedBack() {
        speed = 2;
    }

    public float getSpeed() {
        return speed;
    }

}
