package com.example.kampfderelemente;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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

        Set<KeyCode> isPressed = new HashSet<>();
        fight.setOnKeyReleased(e-> {
                isPressed.remove(e.getCode());
                System.out.println(isPressed);
        });

        //Thread, Timeline, Platform.runlater //Animationtimer
        Thread t = new Thread(()-> {
            while (true) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Platform.runLater(() -> {
                    handleIsPressed(isPressed);
                });
            }
        });
        t.start();

        fight.setOnKeyPressed(e-> {
                isPressed.add(e.getCode());
                System.out.println(isPressed);

        });

//        fight.setOnKeyPressed(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//                System.out.println(event.getCode());
//                switch (event.getCode()) {
//                    case I:
//                        break;
//                    case K:
//                        break;
//                    case J:
//                        PlayerTwo.setLayoutX(PlayerTwo.getLayoutX() - 1);
//                        break;
//                    case L:
//                        PlayerTwo.setLayoutX(PlayerTwo.getLayoutX() + 1);
//                        break;
//                }
//            }
//        });
        everything.getChildren().addAll(ground, PlayerOne, PlayerTwo);
        stage.setScene(fight);
        stage.show();
    }

    private void handleIsPressed(Set<KeyCode> isPressed) {
        for (KeyCode k: isPressed) {
            switch (k) {
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
                case I:
                    break;
                case K:
                    break;
                case J:
                    PlayerTwo.setLayoutX(PlayerTwo.getLayoutX() - 1);
                    break;
                case L:
                    PlayerTwo.setLayoutX(PlayerTwo.getLayoutX() + 1);
                    break;
            }
        }
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