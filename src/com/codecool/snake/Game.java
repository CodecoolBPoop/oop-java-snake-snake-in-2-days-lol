package com.codecool.snake;

import com.codecool.snake.entities.enemies.NavEnemy;
import com.codecool.snake.entities.enemies.PoliceCapEnemy;
import com.codecool.snake.entities.enemies.SimpleEnemy;
import com.codecool.snake.entities.powerups.DollarPowerUp;
import com.codecool.snake.entities.powerups.MoneyBagPowerUp;
import com.codecool.snake.entities.powerups.SimplePowerUp;
import com.codecool.snake.entities.snakes.Snake;
import com.codecool.snake.eventhandler.InputHandler;

import com.sun.javafx.geom.Vec2d;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.util.Optional;


public class Game extends Pane {
    private Snake snake = null;
    private Snake snake2 = null;
    private GameTimer gameTimer = new GameTimer();


    public Game() {
        Globals.getInstance().game = this;
        Globals.getInstance().display = new Display(this);
        Globals.getInstance().setupResources();

        init();
    }

    public void init() {

        // if 2 player mode active: snake2 exist

        boolean singlePlayer = startingChoosePlayerNumbers();

        spawnSnake();

        if (!singlePlayer) {
            spawnSnake2();
        }

        spawnEnemies(4);
        spawnPowerUps(4);
        spawnMoneyBagPowerUps(2);

        setTableBackground(Globals.backgroundImage);

        GameLoop gameLoop = new GameLoop(snake, snake2);
        Globals.getInstance().setGameLoop(gameLoop);
        gameTimer.setup(gameLoop::step);
        gameTimer.play();
    }

    public void start() {
        setupInputHandling();
        Globals.getInstance().startGame();
    }

    private void spawnSnake() {
        snake = new Snake(new Vec2d(700, 500));
    }

    private void spawnSnake2() {
        snake2 = new Snake(new Vec2d(300, 400), true);
        snake2.secondSnakeDiretionSetter();
    }

    private void spawnEnemies(int numberOfEnemies) {
        for (int i = 0; i < numberOfEnemies; ++i){
            new SimpleEnemy();
            new PoliceCapEnemy();
            new NavEnemy();
        }
    }

    private void spawnPowerUps(int numberOfPowerUps) {
        for (int i = 0; i < numberOfPowerUps; ++i) {
            new SimplePowerUp();
            new DollarPowerUp();
        }
    }

    private void setupInputHandling() {
        Scene scene = getScene();
        scene.setOnKeyPressed(event -> InputHandler.getInstance().setKeyPressed(event.getCode()));
        scene.setOnKeyReleased(event -> InputHandler.getInstance().setKeyReleased(event.getCode()));
    }

    private boolean startingChoosePlayerNumbers() {

        boolean playAsSingle = true;

        Alert choosePlayerNumbers = new Alert(Alert.AlertType.CONFIRMATION);
        choosePlayerNumbers.setTitle("Welcome in the Snake in 2 days lol!");
        choosePlayerNumbers.setHeaderText("Please choose between SinglePlayer and MultiPlayer");


        ButtonType buttonTypeCancelPerSinglePlayer = new ButtonType("Single Player", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType multiPlayerButton = new ButtonType("Multi Player");

        choosePlayerNumbers.getButtonTypes().setAll(buttonTypeCancelPerSinglePlayer, multiPlayerButton);


        Optional<ButtonType> result = choosePlayerNumbers.showAndWait();
        if (result.get() == buttonTypeCancelPerSinglePlayer) {
            return playAsSingle;
        } else if (result.get() == multiPlayerButton) {
            playAsSingle = false;
            return playAsSingle;
        } else {
            return playAsSingle;
        }
    }

    private void spawnMoneyBagPowerUps(int numberOfPowerUps) {
        for (int i = 0; i < numberOfPowerUps; ++i) {
            new MoneyBagPowerUp();
        }
    }

    public void setTableBackground(Image tableBackground) {
        setBackground(new Background(new BackgroundImage(tableBackground,
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));

    }
}
