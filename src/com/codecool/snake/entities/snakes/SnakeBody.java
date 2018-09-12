package com.codecool.snake.entities.snakes;

import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.Globals;
import com.codecool.snake.entities.Animatable;
import com.sun.javafx.geom.Vec2d;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SnakeBody extends GameEntity {
    private Queue<Vec2d> history = new LinkedList<>();
    private static final int historySize = 10;

    public SnakeBody(Pane pane, Vec2d coord) {
        super(pane);
        setImage(Globals.resources.getImage("SnakeBody"));
        setPosition(coord);

        for (int i = 0; i < historySize; i++) {
            history.add(coord);
        }
    }

    public void addNewPos(Vec2d latestPos) {
        Vec2d currentPos = history.poll(); // remove the oldest item from the history
        setPosition(currentPos);
        history.add(latestPos); // add the parent's current position to the beginning of the history
    }
}