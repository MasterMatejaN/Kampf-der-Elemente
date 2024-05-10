package com.example.kampfderelemente;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class main extends Application {

    private Group everything = new Group();
    private Scene fight = new Scene(everything);
    public static Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
    public static int width = (int) screenBounds.getWidth();
    public static int height = (int) screenBounds.getHeight();

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

    public void showabilities(Stage stage) {

    }
}
