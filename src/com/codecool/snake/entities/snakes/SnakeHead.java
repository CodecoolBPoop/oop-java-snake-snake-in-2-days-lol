package com.codecool.snake.entities.snakes;

import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.Globals;
import com.codecool.snake.Utils;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.enemies.Enemy;
import com.codecool.snake.entities.powerups.DollarPowerUp;
import com.codecool.snake.entities.powerups.MoneyBagPowerUp;
import com.codecool.snake.entities.powerups.SimplePowerUp;

import com.sun.javafx.geom.Vec2d;
import javafx.geometry.Point2D;

import java.util.Timer;
import java.util.TimerTask;


public class SnakeHead extends GameEntity implements Interactable {
    private static final float turnRate = 2;
    private Snake snake;

    public SnakeHead(Snake snake, Vec2d position) {
        this.snake = snake;
        setImage(Globals.getInstance().getImage("SnakeHead"));
        setPosition(position);
    }

    public void updateRotation(SnakeControl turnDirection, float speed) {
        double headRotation = getRotate();

        if (turnDirection.equals(SnakeControl.TURN_LEFT)) {
            headRotation = headRotation - turnRate;
        }
        if (turnDirection.equals(SnakeControl.TURN_RIGHT)) {
            headRotation = headRotation + turnRate;
        }

        // set rotation and position
        setRotate(headRotation);
        Point2D heading = Utils.directionToVector(headRotation, speed);
        setX(getX() + heading.getX());
        setY(getY() + heading.getY());
    }

    @Override
    public void apply(GameEntity entity) {
        if(entity instanceof Enemy){
            System.out.println(getMessage());
            snake.changeHealth(((Enemy) entity).getDamage());
        }
        if(entity instanceof SimplePowerUp){
            System.out.println(getMessage());
            snake.addPart(4);
        }
        if (entity instanceof DollarPowerUp) {
            System.out.println(getMessage());
            snake.changeHealth(50);
            System.out.println(snake.getHealth());
        }
        if (entity instanceof MoneyBagPowerUp) {
            System.out.println(getMessage());
            snake.changeSpeed(2);
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            snake.setSpeedBack();
                        }
                    },
                    4000
            );
        }
    }

    @Override
    public String getMessage() {
        return "IMMA SNAEK HED! SPITTIN' MAH WENOM! SPITJU-SPITJU!";
    }
}
