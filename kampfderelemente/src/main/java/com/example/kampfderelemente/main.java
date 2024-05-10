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
        ImageView ebene = new ImageView();
        ebene.setX(0);
        ebene.setY((height / 6) * 5);
        ebene.setFitWidth(width);
        ebene.setFitHeight(height / 6);
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
