package com.example.kampfderelemente;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * TODO: Punch korrigieren
 * TODO: Animationen fixen
 * TODO: Frames zeichnen
 */

public class main extends Application {

    /*Overall*/
    private Group everything = new Group();
    private Scene fight = new Scene(everything);
    public static Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
    public static int width = (int) screenBounds.getWidth();
    public static int height = (int) screenBounds.getHeight();

    /*Design*/
    private ProgressBar HpPlayer1 = new ProgressBar(100);
    private ProgressBar HpPlayer2 = new ProgressBar(100);

    private Button[] abilities1 = new Button[4];
    private Button[] abilities2 = new Button[4];
    int groundY = (height / 6) * 5;
    private CharacterPlayable PlayerOne = new CharacterPlayable(
            100, 250, 500, (int) (width / 10), groundY, 5, false
    );
    private CharacterPlayable PlayerTwo = new CharacterPlayable(
            100, 250, 500, (int) (width / 10), groundY, 5, true
    );

    private long lastinputPlayerOne = 0;
    private boolean PlayerOneStand = false;
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Kampf der Elemente");

        PlayerOne.setImage(new Image("file:images/waterbender_normal.png"));
        PlayerTwo.setImage(new Image("file:images/waterbender_normal_reversed.png"));

        ImageView ground = new ImageView();
        ground.setX(0);
        ground.setY(groundY);
        ground.setFitWidth(width);
        ground.setFitHeight(height / 6);

        Set<KeyCode> isPressed = new HashSet<>();
        fight.setOnKeyReleased(e -> {
            isPressed.remove(e.getCode());
            System.out.println(isPressed);
        });

        //Thread, Timeline, Platform.runlater //Animationtimer
        Thread t = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Platform.runLater(() -> {
                    handleIsPressed(isPressed);
                });
/*  Animation Idea für Standing
                if(lastinputPlayerOne + 5_000_000_000L <= System.nanoTime()) {
                    if (PlayerOne.direction) {
                        if(PlayerOneStand) {
                            PlayerOne.setImage(new Image("file:images/waterbender_normal_reversed.png"));
                            PlayerOneStand = false;
                        } else {
                            PlayerOne.setImage(new Image("file:images/waterbender_standing_reversed.png"));
                            PlayerOneStand = true;
                        }
                    } else {
                        if(PlayerOneStand) {
                            PlayerOne.setImage(new Image("file:images/waterbender_normal.png"));
                            PlayerOneStand = false;
                        } else {
                            PlayerOne.setImage(new Image("file:images/waterbender_standing.png"));
                            PlayerOneStand = true;
                        }
                    }
                }

 */
            }
        });
        t.start();

        fight.setOnKeyPressed(e -> {
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
        showabilities();
        stage.setScene(fight);
        stage.show();
    }

    private void handleIsPressed(Set<KeyCode> isPressed) {
        for (KeyCode k : isPressed) {
            switch (k) {
                case A:
                    PlayerOne.setLayoutX(PlayerOne.getLayoutX() - 5);
                    PlayerOne.x = (int)PlayerOne.getLayoutX();
                    PlayerOne.direction = true;
                    PlayerOne.setImage(new Image("file:images/waterbender_walking_reversed.png"));
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
                    t.start();*/

                    lastinputPlayerOne = System.nanoTime();
                    break;
                case W:
                    lastinputPlayerOne = System.nanoTime();
                    break;
                case S:
                    lastinputPlayerOne = System.nanoTime();
                    break;
                case D:
                    PlayerOne.setLayoutX(PlayerOne.getLayoutX() + 5);
                    PlayerOne.x = (int)PlayerOne.getLayoutX();
                    PlayerOne.direction = false;
                    PlayerOne.setImage(new Image("file:images/waterbender_normal.png"));
                    lastinputPlayerOne = System.currentTimeMillis();
                    break;
                case I:
                    break;
                case K:
                    break;
                case J:
                    PlayerTwo.setLayoutX(PlayerTwo.getLayoutX() - 5);
                    PlayerTwo.x = (int)PlayerTwo.getLayoutX();
                    PlayerTwo.direction = true;
                    PlayerTwo.setImage(new Image("file:images/waterbender_normal_reversed.png"));
                    break;
                case L:
                    PlayerTwo.setLayoutX(PlayerTwo.getLayoutX() + 5);
                    PlayerTwo.x = (int)PlayerTwo.getLayoutX();
                    PlayerTwo.direction = false;
                    PlayerTwo.setImage(new Image("file:images/waterbender_normal.png"));
                    break;//todo (delete me Mateja) du kannst die Buchstaben gerne anpassen
                case Y: // todo: (only Akinci delete me) | notiz: direction "false" => rechts | "true" == links
                    // todo: 80 ist range, soll ich das dann als attributspeichern?
                    System.out.println("PlayerOne x: " + PlayerOne.x);
                    System.out.println("PlayerTwo x: " + PlayerTwo.x);
                    if ((PlayerOne.direction && (PlayerTwo.x + PlayerTwo.width > PlayerOne.x - 250 && PlayerTwo.x < PlayerOne.x))
                            || ((!PlayerOne.direction) && (PlayerTwo.x < PlayerOne.x + 250 && PlayerTwo.x > PlayerOne.x))
                    ) {
                        PlayerTwo.health = (PlayerTwo.health - PlayerOne.damage);
                        System.out.println(PlayerTwo.health);
                        updateCharacterHealth();
                    }
                    break;
                case M:
                    if ((PlayerTwo.direction && (PlayerOne.x + PlayerOne.width > PlayerTwo.x - 250 && PlayerOne.x < PlayerTwo.x))
                            || ((!PlayerTwo.direction) && (PlayerOne.x < PlayerTwo.x + 250 && PlayerOne.x > PlayerTwo.x))
                    ) {
                        PlayerOne.health = (PlayerOne.health - PlayerTwo.damage);
                        System.out.println(PlayerOne.health);
                        updateCharacterHealth();
                    }
                    break;
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void updateCharacterHealth() {
        HpPlayer1.setProgress((double) PlayerOne.health /100);
        HpPlayer2.setProgress((double) PlayerTwo.health /100);
    }

    public void showabilities() {
        everything.getChildren().add(HpPlayer1);
        everything.getChildren().add(HpPlayer2);

        HpPlayer1.setProgress((double) PlayerOne.health /100);
        HpPlayer2.setProgress((double) PlayerTwo.health/100);

        HpPlayer1.setLayoutX(50);
        HpPlayer2.setLayoutX(500);
    }
}