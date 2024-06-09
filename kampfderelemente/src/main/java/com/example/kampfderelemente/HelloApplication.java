package com.example.kampfderelemente;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        System.out.println("Hello Akinci");
        launch();
    }
}

/* Animation Idea for Walking
                    final KeyCode kcopy = k;
                    Thread t = new Thread(() -> {
                        boolean PlayerOnewalk = false;
                        while (kcopy == KeyCode.A) {
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            if(PlayerOnewalk) {
                                PlayerOne.setImage(new Image("file:images/waterbender_normal_reversed.png"));
                                PlayerOnewalk = false;
                            } else {
                                PlayerOne.setImage(new Image("file:images/waterbender_walking_reversed.png"));
                                PlayerOnewalk = true;
                            }
                        }
                        try {
                            this.stop();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
                    t.start();/**/