package com.codecool.snake.entities.powerups;

import com.codecool.snake.entities.snakes.SnakeHead;

// interface that all powerups must implement.
public interface IPowerup {

    void apply(SnakeHead snakeHead);
}
