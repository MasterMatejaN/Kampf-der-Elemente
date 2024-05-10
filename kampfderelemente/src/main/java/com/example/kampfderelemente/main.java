package com.example.kampfderelemente;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
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

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Kampf der Elemente");

        int groundY = (height / 6) * 5;
        ImageView ground = new ImageView();
        ground.setX(0);
        ground.setY(groundY);
        ground.setFitWidth(width);
        ground.setFitHeight(height / 6);

        CharacterPlayable PlayerOne =
                new CharacterPlayable(100, 50 , 100, (int) (width / 10), groundY);
        everything.getChildren().addAll(PlayerOne, ground);
        stage.setScene(fight);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void showabilities() {
        everything.getChildren().add(HpPlayer1);
        everything.getChildren().add(HpPlayer2);
    }
}
