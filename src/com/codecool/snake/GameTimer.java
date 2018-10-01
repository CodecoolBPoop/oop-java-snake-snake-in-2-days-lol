package com.codecool.snake;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;


public class GameTimer {
    private static final double DEFAULT_60_FPS = 0.017;
    private double frameTime;
    private Timeline timer = new Timeline();

    GameTimer() {
        this(DEFAULT_60_FPS);
    }

    GameTimer(double frameTime) {
        this.frameTime = frameTime;
        System.out.println("GameTimer created with frame time: " + frameTime);
    }

    public void setup(Runnable loopMethod) {
        timer.setCycleCount( Timeline.INDEFINITE );

        KeyFrame kf = new KeyFrame(
                Duration.seconds(frameTime),
                ae -> loopMethod.run());

        timer.getKeyFrames().add( kf );
    }

    public void play() {
        timer.play();
        System.out.println("GameTimer playing.");
    }
}
