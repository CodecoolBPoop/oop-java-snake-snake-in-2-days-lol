package com.codecool.snake.entities.snakes;

import com.codecool.snake.DelayedModificationList;
import com.codecool.snake.Globals;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.eventhandler.InputHandler;

import com.sun.javafx.geom.Vec2d;
import javafx.scene.input.KeyCode;

import javax.sound.midi.SysexMessage;


public class Snake implements Animatable {
    private float speed = 2;
    private int health = 100;
    private KeyCode leftDir = KeyCode.LEFT;
    private KeyCode rightDir = KeyCode.RIGHT;
    private KeyCode shootKey = KeyCode.M;
    private int snakeBodyLenght = 0;
    private static int numberOfSnakes = 0;
    private int snakeNumber;
    private boolean theSnakeIsAlive;

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    private SnakeHead head;
    private DelayedModificationList<GameEntity> body;


    public Snake(Vec2d position) {
        head = new SnakeHead(this, position);
        body = new DelayedModificationList<>();
        numberOfSnakes++;
        snakeNumber = numberOfSnakes;
        theSnakeIsAlive = true;

        addPart(4);
    }

    public void secondSnakeDiretionSetter() {
        leftDir = KeyCode.A;
        rightDir = KeyCode.D;
        shootKey = KeyCode.G;
    }

    public void step() {
        SnakeControl turnDir = getUserInput();
        boolean shoot = Shoot();

        head.updateRotation(turnDir, speed, shoot);

        updateSnakeBodyHistory();
        checkForDeathCondition();
        checkGameOver();


        body.doPendingModifications();
    }

    private SnakeControl getUserInput() {
        SnakeControl turnDir = SnakeControl.INVALID;
        if (InputHandler.getInstance().isKeyPressed(leftDir)) turnDir = SnakeControl.TURN_LEFT;
        if (InputHandler.getInstance().isKeyPressed(rightDir)) turnDir = SnakeControl.TURN_RIGHT;
        return turnDir;
    }

    private boolean Shoot() {
        boolean shoot = false;
        if (InputHandler.getInstance().isKeyPressed(shootKey)) shoot = true;
        InputHandler.getInstance().setKeyReleased(shootKey);
        return shoot;
    }

    public void addPart(int numParts) {
        GameEntity parent = getLastPart();
        Vec2d position = parent.getPosition();

        for (int i = 0; i < numParts; i++) {
            SnakeBody newBodyPart = new SnakeBody(position);
            body.add(newBodyPart);
            snakeBodyLenght++;
        }
        Globals.getInstance().display.updateSnakeHeadDrawPosition(head);
    }

    public void changeHealth(int diff) {
        health += diff;
    }

    private void checkForDeathCondition() {
        if (theSnakeIsAlive) {
            if (head.isOutOfBounds() || health <= 0) {
                theSnakeIsAlive = false;
                setSpeed(0);
                Globals.getInstance().display.remove(head);
                System.out.println("Game Over for the Second!");
                Globals.getInstance().deadSnakeIncrement();
            }
        }

    }

    private void checkGameOver() {
        if (Globals.getInstance().getDeadSnakeNumber() == numberOfSnakes) {
            System.out.println("Game Over!" + snakeBodyLenght);
            System.out.println("number of snakes: " + numberOfSnakes);
            Globals.getInstance().stopGame();
        }
    }


    private void updateSnakeBodyHistory() {
        GameEntity prev = head;
        for (GameEntity currentPart : body.getList()) {
            currentPart.setPosition(prev.getPosition());
            prev = currentPart;
        }
    }

    private GameEntity getLastPart() {
        GameEntity result = body.getLast();

        if (result != null) return result;
        return head;
    }
}
