package com.codecool.snake.entities.powerups;

import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.Globals;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.snakes.SnakeHead;
import java.util.Random;


public class MoneyBagPowerUp extends GameEntity implements Interactable {
    private static Random rnd = new Random();

    public MoneyBagPowerUp() {
        setImage(Globals.getInstance().getImage("MoneyBag"));

        setX(rnd.nextDouble() * Globals.WINDOW_WIDTH);
        setY(rnd.nextDouble() * Globals.WINDOW_HEIGHT);
    }

    @Override
    public void apply(GameEntity entity) {
        if(entity instanceof SnakeHead){
            System.out.println(getMessage());
            destroy();
            new DollarPowerUp();
        }
    }

    @Override
    public String getMessage() {
        return "Got money-bag power-up :)";
    }
}
