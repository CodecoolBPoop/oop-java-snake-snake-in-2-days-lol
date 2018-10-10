package com.codecool.snake;

import com.codecool.snake.entities.GameEntity;
import javafx.scene.layout.Pane;

public class Display {
    private Pane displayPane;

    public Display(Pane pane) {
        displayPane = pane;
    }

    public void add(GameEntity entity) {
        displayPane.getChildren().add(entity);
    }

    public void remove(GameEntity entity) {
        displayPane.getChildren().remove(entity);
    }
}
