package com.codecool.snake.entities.enemies;

import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.Globals;
import com.codecool.snake.entities.IAnimatedEntity;
import com.codecool.snake.Utils;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

import java.util.Random;

// a simple enemy TODO make better ones.
public class SimpleEnemy extends GameEntity implements IAnimatedEntity, IEnemy {

    private Point2D heading;

    public SimpleEnemy(Pane pane) {
        super(pane);
        int speed = 1;
        setImage(Globals.simpleEnemy);
        pane.getChildren().add(this);

        Random rnd = new Random();
        setX(rnd.nextDouble() * Globals.WINDOW_WIDTH);
        setY(rnd.nextDouble() * Globals.WINDOW_HEIGHT);

        double direction = rnd.nextDouble() * 360;
        setRotate(-direction);
        heading = Utils.directionToVector(direction, speed);
    }

    @Override
    public void step() {
        if (IsOutOfBounds()) {
            destroy();
        }
        setX(getX() - heading.getX());
        setY(getY() - heading.getY());
    }

    @Override
    public int getDamage() {
        return 10;
    }
}
