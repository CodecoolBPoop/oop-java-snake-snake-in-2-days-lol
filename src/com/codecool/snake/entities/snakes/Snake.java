package com.codecool.snake.entities.snakes;

import com.codecool.snake.Game;
import com.codecool.snake.Globals;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.eventhandler.InputHandler;
import com.sun.javafx.geom.Vec2d;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;

import java.util.LinkedList;
import java.util.List;

public class Snake implements Animatable {
    private static final float speed = 2;
    private int health;

    private SnakeHead head;
    private List<GameEntity> body = new LinkedList<>();
    private List<GameEntity> newBody = new LinkedList<>();


    public Snake(Vec2d position) {
        head = new SnakeHead(position);
        health = 100;

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

        if(!newBody.isEmpty()) {
            body.addAll(newBody);
            newBody.clear();
        }
    }

    public void addPart(int numParts) {
        // place it visually below the current tail
        GameEntity parent = newBody.isEmpty() ? getLastPart() : newBody.get(newBody.size() - 1);
        Vec2d position = parent.getPosition();

        for (int i = 0; i < numParts; i++) {
            SnakeBody newBodyPart = new SnakeBody(position);
            newBody.add(newBodyPart);
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
        for(GameEntity bodyPart : body) {
            for (GameEntity entity : Globals.gameObjects.getGameObjects()) {
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
        for(GameEntity currentPart : body) {
            currentPart.setPosition(prev.getPosition());
            prev = currentPart;
        }
    }


    private GameEntity getLastPart() {
        if(body.isEmpty()) return head;

        return body.get(body.size() - 1);
    }
}
