package com.codecool.snake.entities.snakes;


import com.codecool.snake.Globals;
import com.codecool.snake.entities.GameEntity;
import com.sun.javafx.geom.Vec2d;

import java.awt.*;

import javax.swing.*;

public class HealthBar extends GameEntity {

    private double baseHealth = 100.0;

    HealthBar(boolean green){
        if (green) {
            setImage(Globals.getInstance().getImage("GreenHealth"));
            setFitWidth(baseHealth);
        } else {
            setImage(Globals.getInstance().getImage("RedHealth"));
            setFitWidth(baseHealth);
        }

        setPosition(new Vec2d(700,100));
    }

    HealthBar(boolean green, boolean second){
        if (green) {
            setImage(Globals.getInstance().getImage("GreenHealth"));
            setFitWidth(baseHealth);
        } else {
            setImage(Globals.getInstance().getImage("RedHealth"));
            setFitWidth(baseHealth);
        }

        setPosition(new Vec2d(100,100));
    }

    public void changeGreenHealth(double value){
        if(baseHealth + value > 100){
            baseHealth = 100;
            setFitWidth(100);
        } else {
            baseHealth += value;
            setFitWidth(baseHealth + value);
        }
    }
}
