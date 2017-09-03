package com.codecool.snake.entities.snakes;

import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.Globals;
import com.codecool.snake.entities.IAnimatedEntity;
import com.codecool.snake.Utils;
import com.codecool.snake.entities.enemies.IEnemy;
import com.codecool.snake.entities.powerups.IPowerup;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

public class SnakeHead extends GameEntity implements IAnimatedEntity {

    private static final float speed = 4;
    private static final float turnRate = 2;
    private float direction; // heading in degrees
    private GameEntity tail; // the last element. Needed to know where to add the next part.
    private int health;

    public SnakeHead(Pane pane, int xc, int yc) {
        super(pane);
        setX(xc);
        setY(yc);
        health = 100;
        tail = this;
        setImage(Globals.snakeHead);
        pane.getChildren().add(this);
        direction = 0; // goes up

        addPart(4);
    }

    public void step() {
        if (Globals.leftKeyDown) {
            direction = direction + turnRate;
        }
        if (Globals.rightKeyDown) {
            direction = direction - turnRate;
        }
        // set rotation and position
        setRotate(-direction);
        Point2D heading = Utils.directionToVector(direction, speed);
        setX(getX() - heading.getX());
        setY(getY() - heading.getY());

        // check if collided with an enemy or a powerup
        for (GameEntity entity : Globals.getGameObjects()) {
            if (getBoundsInParent().intersects(entity.getBoundsInParent())) {
                if (entity instanceof IPowerup) {
                    IPowerup powerup = (IPowerup) entity;
                    addPart(powerup.getStrength());
                    entity.destroy();
                    System.out.println("got berry :)");
                }
                if (entity instanceof IEnemy) {
                    IEnemy enemy = (IEnemy) entity;
                    health = health - enemy.getDamage();
                    entity.destroy();
                    System.out.println("player health: " + health);
                }
            }
        }

        // check for game over condition
        if (IsOutOfBounds() || health <= 0) {
            System.out.println("Game Over");
            Globals.gameLoop.stop();
        }
    }

    private void addPart(int numParts) {
        for (int i = 0; i < numParts; i++) {
            SnakeBody newPart = new SnakeBody(pane, tail);
            tail = newPart;
        }
    }

}
