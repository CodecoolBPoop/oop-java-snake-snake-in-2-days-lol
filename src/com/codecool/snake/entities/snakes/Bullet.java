package com.codecool.snake.entities.snakes;

import com.codecool.snake.Globals;
import com.codecool.snake.Utils;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.enemies.Enemy;
import com.codecool.snake.entities.enemies.NavEnemy;
import com.codecool.snake.entities.enemies.PoliceCapEnemy;
import com.codecool.snake.entities.enemies.SimpleEnemy;
import com.sun.javafx.geom.Vec2d;
import javafx.geometry.Point2D;


public class Bullet extends GameEntity implements Animatable, Interactable {

    private Point2D heading;

    public Bullet(Vec2d headPos, double way){
        setImage(Globals.getInstance().getImage("Bullet"));
        setPosition(headPos);

        double direction = way;
        setRotate(way);

        int speed = 15;
        heading = Utils.directionToVector(direction, speed);
    }

    @Override
    public void step(){
        if(isOutOfBounds()){
            destroy();
        }
        setX(getX() + heading.getX());
        setY(getY() + heading.getY());
    }

    @Override
    public void apply(GameEntity entity){
        if(entity instanceof Enemy){
            System.out.println("Enemy killed");
            entity.destroy();
            if(entity instanceof NavEnemy){
                new NavEnemy();
            } else if(entity instanceof PoliceCapEnemy){
                new PoliceCapEnemy();
            }
        }
    }

    @Override
    public String getMessage() {
        return "DIE DIE DIE";
    }
}
