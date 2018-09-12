package com.codecool.snake.entities.snakes;

import com.codecool.snake.Game;
import com.codecool.snake.Globals;
import com.codecool.snake.Utils;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.Interactable;
import com.sun.javafx.geom.Vec2d;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.LinkedList;
import java.util.List;

public class Snake implements Animatable {
    private static final float speed = 2;
    private int health;

    private SnakeHead head;
    private List<SnakeBody> body = new LinkedList<>();
    private List<SnakeBody> newBody = new LinkedList<>();
    private Pane pane;

    public Snake(Pane pane, Vec2d position) {
        this.pane = pane;
        head = new SnakeHead(pane, position);
        health = 100;

        addPart(4);
    }

    public void step() {
        SnakeControl turnDir = SnakeControl.INVALID;
        if(Globals.leftKeyDown) turnDir = SnakeControl.TURN_LEFT;
        if(Globals.rightKeyDown) turnDir = SnakeControl.TURN_RIGHT;

        head.updateRotation(turnDir, speed);
        updateSnakeBodyHistory();

        checkInteractableCollision();
        checkForGameOverConditions();


        if(!newBody.isEmpty()) {
            body.addAll(newBody);
            newBody.clear();
            head.setDrawOrder();
        }
    }

    public void addPart(int numParts) {
        // place it visually below the current tail
        GameEntity parent = newBody.isEmpty() ? getLastPart() : newBody.get(newBody.size() - 1);
        Vec2d position = parent.getPosition();

        for (int i = 0; i < numParts; i++) {
            SnakeBody newBodyPart = new SnakeBody(pane, position);
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
        for(SnakeBody currentPart : body) {
            currentPart.addNewPos(prev.getPosition());
            prev = currentPart;
        }
    }


    private GameEntity getLastPart() {
        if(body.isEmpty()) return head;

        return body.get(body.size() - 1);
    }
}
