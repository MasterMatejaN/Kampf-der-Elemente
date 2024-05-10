package com.example.kampfderelemente;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class main extends Application {

    /*Overall*/
    private Group everything = new Group();
    private Scene fight = new Scene(everything);
    public static Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
    public static int width = (int) screenBounds.getWidth();
    public static int height = (int) screenBounds.getHeight();

    /*Design*/
    private ProgressBar HpPlayer1 = new ProgressBar();
    private ProgressBar HpPlayer2 = new ProgressBar();

    private Button[] abilities1 = new Button[4];
    private Button[] abilities2 = new Button[4];
    int groundY = (height / 6) * 5;
    private CharacterPlayable PlayerOne = new CharacterPlayable(
            100, 50, 100, (int) (width / 10), groundY
    );
    private CharacterPlayable PlayerTwo = new CharacterPlayable(
            100, 50, 100, (int) (width / 10), groundY
    );

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Kampf der Elemente");

        updateCharacterHealth();

        ImageView ground = new ImageView();
        ground.setX(0);
        ground.setY(groundY);
        ground.setFitWidth(width);
        ground.setFitHeight(height / 6);
        fight.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.println(event.getCode());
                switch (event.getCode()) {
                    case A:
                        PlayerOne.setLayoutX(PlayerOne.getLayoutX() - 1);
                        break;
                    case W:
                        break;
                    case S:
                        break;
                    case D:
                        PlayerOne.setLayoutX(PlayerOne.getLayoutX() + 1);
                        break;
                    case UP:
                        break;
                    case DOWN:
                        break;
                    case LEFT:
                        PlayerTwo.setLayoutX(PlayerTwo.getLayoutX() - 1);
                        break;
                    case RIGHT:
                        PlayerTwo.setLayoutX(PlayerTwo.getLayoutX() + 1);
                        break;
                }
            }
        });
        everything.getChildren().addAll(ground, PlayerOne, PlayerTwo);
        stage.setScene(fight);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void updateCharacterHealth() {
        HpPlayer1.setProgress(PlayerOne.health);
        HpPlayer1.setProgress(PlayerTwo.health);
    }

    public void showabilities() {
        everything.getChildren().add(HpPlayer1);
        everything.getChildren().add(HpPlayer2);
    }
}
