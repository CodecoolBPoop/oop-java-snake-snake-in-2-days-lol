package com.codecool.snake.entities.enemies;

//interface that all enemies must implement.
public interface IEnemy {

    // this returns the damage caused when the player collides with this enemy.
    int getDamage();
}
