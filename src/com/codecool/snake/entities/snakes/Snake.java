package com.codecool.snake.entities.snakes;

import com.codecool.snake.DelayedModificationList;
import com.codecool.snake.Game;
import com.codecool.snake.Globals;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.eventhandler.InputHandler;
import com.sun.javafx.geom.Vec2d;
import javafx.scene.input.KeyCode;


public class Snake implements Animatable {
    private static final float speed = 2;
    private int health;

    private SnakeHead head;
    private DelayedModificationList<GameEntity> body;


    public Snake(Vec2d position) {
        head = new SnakeHead(position);
        health = 100;
        body = new DelayedModificationList<GameEntity>();

        addPart(4);
    }

    public void step() {
        SnakeControl turnDir = SnakeControl.INVALID;
        if(InputHandler.getInstance().isKeyPressed(KeyCode.LEFT)) turnDir = SnakeControl.TURN_LEFT;
        if(InputHandler.getInstance().isKeyPressed(KeyCode.RIGHT)) turnDir = SnakeControl.TURN_RIGHT;

        head.updateRotation(turnDir, speed);
        updateSnakeBodyHistory();

        checkInteractableCollision();
        checkForGameOverConditions();

        body.doPendingModifications();
    }

    public void addPart(int numParts) {
        // place it visually below the current tail
        GameEntity parent = getLastPart();
        Vec2d position = parent.getPosition();

        for (int i = 0; i < numParts; i++) {
            SnakeBody newBodyPart = new SnakeBody(position);
            body.add(newBodyPart);
        }
    }

    public void changeHealth(int diff) {
        health += diff;
    }

    private void checkForGameOverConditions() {
        if (head.isOutOfBounds() || health <= 0) {
            System.out.println("Game Over");
            Game.gameLoop.stop();
        }
    }

    private void checkInteractableCollision() {
        // check if collided with an enemy or a powerup
        for(GameEntity bodyPart : body.getList()) {
            for (GameEntity entity : Globals.gameObjects.getList()) {
                if (bodyPart.getBoundsInParent().intersects(entity.getBoundsInParent())) {
                    if (entity instanceof Interactable) {
                        Interactable interactable = (Interactable) entity;
                        interactable.apply(this);
                        System.out.println(interactable.getMessage());
                    }
                }
            }
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
}
