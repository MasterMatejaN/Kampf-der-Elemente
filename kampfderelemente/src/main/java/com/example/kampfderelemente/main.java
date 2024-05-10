package com.example.kampfderelemente;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class main extends Application {

    private Group everything = new Group();
    private Scene fight = new Scene(everything);

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Kampf der Elemente");



        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void showabilities(Stage stage) {

    }
}
