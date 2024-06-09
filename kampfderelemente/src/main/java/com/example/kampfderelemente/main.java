package com.example.kampfderelemente;

import javafx.application.Application;
import javafx.application.Platform;
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
    public static ProgressBar HpPlayer1 = new ProgressBar(100);
    public static ProgressBar HpPlayer2 = new ProgressBar(100);

    private Button[] abilities1 = new Button[4];
    private Button[] abilities2 = new Button[4];
    static int groundY = (height / 16) * 5;
    public static CharacterPlayable PlayerOne = new CharacterPlayable(
            100, 250, 500, 300, (int) (width / 10), groundY, 5, 250, false, 5,
            2, 2, 1, 1
    );
    public static CharacterPlayable PlayerTwo = new CharacterPlayable(
            100, 250, 500, 300, (int) (width / 10) * 7, groundY, 5, 250, true, 5,
            2, 2, 1, 1
    );

    private long lastinputPlayerOne = 0;
    private boolean PlayerOneStand = false;

    public static Set<KeyCode> isPressed = new HashSet<>();

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Kampf der Elemente");

        PlayerOne.setImage(new Image("file:images/waterbender_normal.png"));
        PlayerTwo.setImage(new Image("file:images/waterbender_normal_reversed.png"));


        System.out.println("PlayerOne.x = " + PlayerOne.x);
        System.out.println("PlayerTwo.x = " + PlayerTwo.x);


        ImageView ground = new ImageView();
        ground.setX(0);
        ground.setY(groundY);
        ground.setFitWidth(width);
        ground.setFitHeight(height / 6);

        fight.setOnKeyReleased(e -> {
            boolean containedS = false;
            boolean containedDown = false;
            if (isPressed.contains(KeyCode.S)) {
                containedS = true;
            }
            if (isPressed.contains(KeyCode.DOWN)) {
                containedDown = true;
            }
            isPressed.remove(e.getCode());
            if (!isPressed.contains(KeyCode.S) && containedS && !PlayerOne.frozen) {
                PlayerOne.isSneaking = false;
                PlayerOne.y = PlayerOne.yStand;
                PlayerOne.setLayoutY(PlayerOne.y);
                PlayerOne.height = PlayerOne.heightStand;
                PlayerOne.setFitHeight(PlayerOne.height);
            }
            if (!isPressed.contains(KeyCode.DOWN) && containedDown && !PlayerTwo.frozen) {
                PlayerTwo.isSneaking = false;
                PlayerTwo.y = PlayerTwo.yStand;
                PlayerTwo.setLayoutY(PlayerTwo.y);
                PlayerTwo.height = PlayerTwo.heightStand;
                PlayerTwo.setFitHeight(PlayerTwo.height);
            }
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

 /**/
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
                    if (!PlayerOne.frozen) {
                        PlayerOne.setLayoutX(PlayerOne.getLayoutX() - PlayerOne.speed);
                        PlayerOne.x = (int) PlayerOne.getLayoutX();
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
                    t.start();/**/

                        lastinputPlayerOne = System.nanoTime();
                    }
                    break;
                case W:
                    if (!PlayerOne.frozen) {
                        lastinputPlayerOne = System.nanoTime();
                        if (PlayerOne.y == groundY) {
                            PlayerOne.jump();
                        }
                    }
                    break;
                case S:
                    if (!PlayerOne.frozen) {
                        lastinputPlayerOne = System.nanoTime();
                        PlayerOne.isSneaking = true;
                        PlayerOne.y = PlayerOne.ySneaking;
                        PlayerOne.setLayoutY(PlayerOne.y);
                        PlayerOne.height = PlayerOne.heightSneaking;
                        PlayerOne.setFitHeight(PlayerOne.height);
                    }
                    break;
                case D:
                    if (!PlayerOne.frozen) {
                        PlayerOne.setLayoutX(PlayerOne.getLayoutX() + PlayerOne.speed);
                        PlayerOne.x = (int) PlayerOne.getLayoutX();
                        PlayerOne.direction = false;
                        PlayerOne.setImage(new Image("file:images/waterbender_normal.png"));
                        lastinputPlayerOne = System.currentTimeMillis();
                    }
                    break;
                case UP:
                    if (!PlayerTwo.frozen) {
                        if (PlayerTwo.y == groundY) {
                            PlayerTwo.jump();
                        }
                    }
                    break;
                case DOWN:
                    if (!PlayerTwo.frozen) {
                        PlayerTwo.isSneaking = true;
                        //todo delete isSneaking (vllt doch nicht, wenn beim sneaken "gehen" animiert wird...)
                        // während jump kein sneak, ist das ok?
                        PlayerTwo.y = PlayerTwo.ySneaking;
                        PlayerTwo.setLayoutY(PlayerTwo.y);
                        PlayerTwo.height = PlayerTwo.heightSneaking;
                        PlayerTwo.setFitHeight(PlayerTwo.height);
                    }
                    break;
                case LEFT:
                    if (!PlayerTwo.frozen) {
                        PlayerTwo.setLayoutX(PlayerTwo.getLayoutX() - PlayerTwo.speed);
                        PlayerTwo.x = (int) PlayerTwo.getLayoutX();
                        PlayerTwo.direction = true;
                        PlayerTwo.setImage(new Image("file:images/waterbender_normal_reversed.png"));
                    }
                    break;
                case RIGHT:
                    if (!PlayerTwo.frozen) {
                        PlayerTwo.setLayoutX(PlayerTwo.getLayoutX() + PlayerTwo.speed);
                        PlayerTwo.x = (int) PlayerTwo.getLayoutX();
                        PlayerTwo.direction = false;
                        PlayerTwo.setImage(new Image("file:images/waterbender_normal.png"));
                    }
                    break;
                case Y: //todo: Da steht Basic Punch Player1
                    if (((PlayerOne.direction && !PlayerTwo.frozen
                            && (PlayerTwo.x + PlayerTwo.width > PlayerOne.x - PlayerOne.basic_Attack_Range
                            && PlayerTwo.x < PlayerOne.x)
                    ) || ((!PlayerOne.direction) && (PlayerTwo.x < PlayerOne.x + PlayerOne.basic_Attack_Range
                            && PlayerTwo.x > PlayerOne.x)))
                            && (System.currentTimeMillis() >
                            PlayerOne.basic_Attack_LastUsed + PlayerOne.basic_Attack_Cooldown)
                    ) {
                        PlayerOne.basic_Attack_LastUsed = System.currentTimeMillis();
                        PlayerTwo.health = (PlayerTwo.health - PlayerOne.damage);
                        System.out.println(PlayerTwo.health);
                        updateCharacterHealth();
                    }
                    break;
                case M://todo: Da steht Basic Punch Player2
                    if (((PlayerTwo.direction
                            && (PlayerOne.x + PlayerOne.width > PlayerTwo.x - PlayerTwo.basic_Attack_Range
                            && PlayerOne.x < PlayerTwo.x)
                    ) || ((!PlayerTwo.direction) && (PlayerOne.x < PlayerTwo.x + PlayerTwo.basic_Attack_Range
                            && PlayerOne.x > PlayerTwo.x)))
                            && (System.currentTimeMillis() >
                            PlayerTwo.basic_Attack_LastUsed + PlayerTwo.basic_Attack_Cooldown)
                    ) {
                        PlayerTwo.basic_Attack_LastUsed = System.currentTimeMillis();
                        PlayerOne.health = (PlayerOne.health - PlayerTwo.damage);
                        System.out.println(PlayerOne.health);
                        updateCharacterHealth();
                    }
                    break;
                case X://todo: Da steht Basic Projectile Player1
                    if (System.currentTimeMillis() >
                            PlayerOne.basic_Projektile_LastUsed + PlayerOne.basic_Projektile_Cooldown
                    ) {
                        PlayerOne.basic_Projektile_LastUsed = System.currentTimeMillis();
                        Projectile projectilePlayer1 = new Projectile(
                                150, 150, PlayerOne.x, PlayerOne.y + PlayerOne.height / 2, 20, 5,
                                false, 1, PlayerOne.direction
                        );
                        projectilePlayer1.setImage(new Image("file:images/waterball.png"));
                        everything.getChildren().add(projectilePlayer1);
                    }
                    break;
                case N://todo: Da steht Basic Projectile Player2
                    if (System.currentTimeMillis() >
                            PlayerTwo.basic_Projektile_LastUsed + PlayerTwo.basic_Projektile_Cooldown
                    ) {
                        PlayerTwo.basic_Projektile_LastUsed = System.currentTimeMillis();
                        Projectile projectilePlayer2 = new Projectile(
                                150, 150, PlayerTwo.x, PlayerTwo.y + PlayerTwo.height / 2, 20, 5,
                                false, 2, PlayerTwo.direction
                        );
                        projectilePlayer2.setImage(new Image("file:images/waterball.png"));
                        everything.getChildren().add(projectilePlayer2);
                    }
                    break;
                case DIGIT1://todo: Da steht water-whip Player2
                    if (PlayerTwo.second_AttackAmount > 0) {
                        if ((PlayerTwo.direction
                                && (PlayerOne.x + PlayerOne.width > PlayerTwo.x - PlayerTwo.first_Attack_Range
                                && PlayerOne.x < PlayerTwo.x))
                                && (System.currentTimeMillis() >
                                PlayerTwo.first_Attack_LastUsed + PlayerTwo.first_Attack_Cooldown)
                        ) {
                            PlayerOne.health = (PlayerOne.health -
                                    ((PlayerTwo.damage + (PlayerTwo.x - (PlayerOne.x + PlayerOne.width))) / 10)
                            );
                            System.out.println("damage: " +
                                    ((PlayerTwo.damage + (PlayerTwo.x - (PlayerOne.x + PlayerOne.width))) / 10)
                            );
                            System.out.println(PlayerOne.health);
                            updateCharacterHealth();
                        }
                        if (((!PlayerTwo.direction)
                                && (PlayerOne.x < PlayerTwo.x + PlayerTwo.first_Attack_Range
                                && PlayerOne.x > PlayerTwo.x))
                                && (System.currentTimeMillis() >
                                PlayerTwo.first_Attack_LastUsed + PlayerTwo.first_Attack_Cooldown)
                        ) {
                            PlayerOne.health = (PlayerOne.health -
                                    ((PlayerTwo.damage + (PlayerOne.x - PlayerTwo.x)) / 10)
                            );
                            System.out.println("damage: " +
                                    ((PlayerTwo.damage + (PlayerOne.x - PlayerTwo.x)) / 10)
                            );
                            System.out.println(PlayerOne.health);
                            updateCharacterHealth();
                        }
                        PlayerTwo.first_Attack_LastUsed = System.currentTimeMillis();
                        PlayerTwo.first_AttackAmount--;
                    }
                    break;
                case NUMPAD2://todo: Da steht ice-spike Player2
                    if (PlayerTwo.second_AttackAmount > 0) {
                        boolean projectileDirection;
                        Projectile projectilePlayer2 = new Projectile(
                                150, 150, PlayerTwo.x, PlayerTwo.y + PlayerTwo.height / 2, 20, 5,
                                true, 2, projectileDirection = PlayerTwo.direction
                        );
                        int random = (int) ((Math.random() * 3) + 1);
                        if (projectileDirection) {// todo hier ice-spike bild mit reversed ersetzen
                            if (random == 1) projectilePlayer2.setImage(new Image("file:images/ice_spike1.png"));
                            else if (random == 2) projectilePlayer2.setImage(new Image("file:images/ice_spike2.png"));
                            else projectilePlayer2.setImage(new Image("file:images/ice_spike3.png"));
                        } else {
                            if (random == 1) projectilePlayer2.setImage(new Image("file:images/ice_spike1.png"));
                            else if (random == 2) projectilePlayer2.setImage(new Image("file:images/ice_spike2.png"));
                            else projectilePlayer2.setImage(new Image("file:images/ice_spike3.png"));
                        }
                        everything.getChildren().add(projectilePlayer2);
                        PlayerTwo.second_Attack_LastUsed = System.currentTimeMillis();
                        PlayerTwo.second_AttackAmount--;
                    }
                    break;
                case NUMPAD3://todo: Da steht frost-pillar Player2
                    if (PlayerTwo.third_AttackAmount > 0) {
                        if (!PlayerOne.jumping && (System.currentTimeMillis() >
                                PlayerTwo.third_Attack_LastUsed + PlayerTwo.third_Attack_Cooldown)) {
                            FrostPillar frostPillar = new FrostPillar(PlayerOne.x, PlayerOne.y, PlayerOne.width,
                                    PlayerOne.height, System.currentTimeMillis(), PlayerTwo, PlayerOne,
                                    false
                            );
                            everything.getChildren().add(frostPillar);
                            PlayerTwo.third_Attack_LastUsed = System.currentTimeMillis();
                            PlayerTwo.third_AttackAmount--;
                        }
                    }
                    break;
                case NUMPAD4://todo: Da steht enhanced-movement Player2
                    if (PlayerTwo.fourth_AttackAmount > 0) {
                        PlayerTwo.fourth_Attack_LastUsed = System.currentTimeMillis();
                        if (PlayerTwo.first_AttackAmount < PlayerTwo.first_AttackMaxAmount) {
                            PlayerTwo.first_AttackAmount++;
                        }
                        if (PlayerTwo.second_AttackAmount < PlayerTwo.second_AttackMaxAmount) {
                            PlayerTwo.second_AttackAmount++;
                        }
                        if (PlayerTwo.third_AttackAmount < PlayerTwo.third_AttackMaxAmount) {
                            PlayerTwo.third_AttackAmount++;
                        }
                        PlayerTwo.enhancedModeActive = true;
                        PlayerTwo.fourth_AttackAmount--;
                    }
                    break;
                case F://todo: Da steht water-whip Player1
                    if (PlayerOne.second_AttackAmount > 0) {
                        if ((PlayerOne.direction
                                && (PlayerTwo.x + PlayerTwo.width > PlayerOne.x - PlayerTwo.first_Attack_Range
                                && PlayerTwo.x < PlayerOne.x)
                        )
                                && (System.currentTimeMillis() >
                                PlayerOne.first_Attack_LastUsed + PlayerOne.first_Attack_Cooldown)
                        ) {
                            PlayerTwo.health = (PlayerTwo.health -
                                    ((PlayerOne.damage + (PlayerOne.x - PlayerTwo.x)) / 10)
                            );
                            System.out.println("damage: " +
                                    ((PlayerOne.damage + (PlayerOne.x - PlayerTwo.x)) / 10)
                            );
                            System.out.println(PlayerTwo.health);
                            updateCharacterHealth();
                        }
                        if (((!PlayerOne.direction)
                                && (PlayerTwo.x < PlayerOne.x + PlayerTwo.first_Attack_Range
                                && PlayerTwo.x > PlayerOne.x)
                        )
                                && (System.currentTimeMillis() >
                                PlayerOne.first_Attack_LastUsed + PlayerOne.first_Attack_Cooldown)
                        ) {
                            PlayerTwo.health = (PlayerTwo.health -
                                    ((PlayerOne.damage + (PlayerTwo.x - PlayerOne.x)) / 10)
                            );
                            System.out.println("damage: " +
                                    ((PlayerOne.damage + (PlayerTwo.x - PlayerOne.x)) / 10)
                            );
                            System.out.println(PlayerTwo.health);
                            updateCharacterHealth();
                        }
                        PlayerOne.first_Attack_LastUsed = System.currentTimeMillis();
                        PlayerOne.first_AttackAmount--;
                    }
                    break;
                case G://todo: Da steht ice-spike Player1
                    if (PlayerOne.second_AttackAmount > 0) {
                        boolean projectileDirection;
                        Projectile projectilePlayer1 = new Projectile(
                                150, 150, PlayerOne.x, PlayerOne.y + PlayerOne.height / 2, 20, 5,
                                true, 1, projectileDirection = PlayerOne.direction
                        );
                        int random = (int) ((Math.random() * 3) + 1);
                        if (projectileDirection) {// todo hier ice-spike bild mit reversed ersetzen
                            if (random == 1) projectilePlayer1.setImage(new Image("file:images/ice_spike1.png"));
                            else if (random == 2) projectilePlayer1.setImage(new Image("file:images/ice_spike2.png"));
                            else projectilePlayer1.setImage(new Image("file:images/ice_spike3.png"));
                        } else {
                            if (random == 1) projectilePlayer1.setImage(new Image("file:images/ice_spike1.png"));
                            else if (random == 2) projectilePlayer1.setImage(new Image("file:images/ice_spike2.png"));
                            else projectilePlayer1.setImage(new Image("file:images/ice_spike3.png"));
                        }
                        everything.getChildren().add(projectilePlayer1);
                        PlayerOne.second_Attack_LastUsed = System.currentTimeMillis();
                        PlayerOne.second_AttackAmount--;
                    }
                    break;
                case H://todo: Da steht frost-pillar Player1
                    if (PlayerOne.third_AttackAmount > 0) {
                        if (!PlayerTwo.jumping && (System.currentTimeMillis() >
                                PlayerOne.third_Attack_LastUsed + PlayerOne.third_Attack_Cooldown)) {
                            FrostPillar frostPillar = new FrostPillar(PlayerTwo.x, PlayerTwo.y, PlayerTwo.width,
                                    PlayerTwo.height, System.currentTimeMillis(), PlayerOne, PlayerTwo,
                                    true
                            );
                            everything.getChildren().add(frostPillar);
                            PlayerOne.third_Attack_LastUsed = System.currentTimeMillis();
                            PlayerOne.third_AttackAmount--;
                        }
                    }
                    break;
                case J://todo: Da steht enhanced-movement Player1
                    if (PlayerOne.fourth_AttackAmount > 0) {
                        PlayerOne.fourth_Attack_LastUsed = System.currentTimeMillis();
                        if (PlayerOne.first_AttackAmount < PlayerOne.first_AttackMaxAmount) {
                            PlayerOne.first_AttackAmount++;
                        }
                        if (PlayerOne.second_AttackAmount < PlayerOne.second_AttackMaxAmount) {
                            PlayerOne.second_AttackAmount++;
                        }
                        if (PlayerOne.third_AttackAmount < PlayerOne.third_AttackMaxAmount) {
                            PlayerOne.third_AttackAmount++;
                        }
                        PlayerOne.enhancedModeActive = true;
                        PlayerOne.fourth_AttackAmount--;
                    }
                    break;
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static void updateCharacterHealth() {
        HpPlayer1.setProgress((double) PlayerOne.health / 100);
        HpPlayer2.setProgress((double) PlayerTwo.health / 100);
    }

    public void showabilities() {
        everything.getChildren().add(HpPlayer1);
        everything.getChildren().add(HpPlayer2);

        HpPlayer1.setProgress((double) PlayerOne.health / 100);
        HpPlayer2.setProgress((double) PlayerTwo.health / 100);

        HpPlayer1.setLayoutX(50);
        HpPlayer2.setLayoutX(500);
    }
}