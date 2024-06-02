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
            3, 3, 1, 1
    );
    public static CharacterPlayable PlayerTwo = new CharacterPlayable(
            100, 250, 500, 300, (int) (width / 10) * 7, groundY, 5, 250, true, 5,
            3, 3, 1, 1
    );

    private long lastinputPlayerOne = 0;
    private boolean PlayerOneStand = false;


    //todo: für frost pillar; die gehören auch weg (zu CharacterPlayable auslagern);
    private long Player1_3rd_move_frozen_effekt_time = 0;
    private long Player2_3rd_move_frozen_effekt_time = 0;
    private long Player1_3rd_move_cooled_effekt_time = 0;
    private long Player2_3rd_move_cooled_effekt_time = 0;

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

        Set<KeyCode> isPressed = new HashSet<>();
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
            if (!isPressed.contains(KeyCode.S) && containedS) {
                PlayerOne.isSneaking = false;
                PlayerOne.y = PlayerOne.yStand;
                PlayerOne.setLayoutY(PlayerOne.y);
                PlayerOne.height = PlayerOne.heightStand;
                PlayerOne.setFitHeight(PlayerOne.height);
                //todo stop sneak Player1
            }
            if (!isPressed.contains(KeyCode.DOWN) && containedDown) {
                PlayerTwo.isSneaking = false;
                PlayerTwo.y = PlayerTwo.yStand;
                PlayerTwo.setLayoutY(PlayerTwo.y);
                PlayerTwo.height = PlayerTwo.heightStand;
                PlayerTwo.setFitHeight(PlayerTwo.height);
                //todo stop sneak Player2
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
        if (System.currentTimeMillis() > Player1_3rd_move_frozen_effekt_time
                && Player1_3rd_move_frozen_effekt_time != 0
        ) {
            PlayerOne.frozen = false;
            PlayerOne.cooled = true;
            PlayerOne.speed -= 2;//todo 2 mit gescheiter variable tauschen
            Player1_3rd_move_frozen_effekt_time = 0;
            Player1_3rd_move_cooled_effekt_time = System.currentTimeMillis() + 2000;
        }
        if (System.currentTimeMillis() > Player1_3rd_move_cooled_effekt_time
                && Player1_3rd_move_cooled_effekt_time != 0
        ) {
            PlayerOne.cooled = false;
            PlayerOne.speed += 2;//todo 2 mit gescheiter variable tauschen
            Player1_3rd_move_cooled_effekt_time = 0;
        }
        if (System.currentTimeMillis() > Player2_3rd_move_frozen_effekt_time
                && Player2_3rd_move_frozen_effekt_time != 0
        ) {
            PlayerTwo.frozen = false;
            PlayerTwo.cooled = true;
            PlayerTwo.speed -= 2;//todo 2 mit gescheiter variable tauschen
            Player2_3rd_move_frozen_effekt_time = 0;
            Player2_3rd_move_cooled_effekt_time = System.currentTimeMillis() + 2000;
        }
        if (System.currentTimeMillis() > Player2_3rd_move_cooled_effekt_time
                && Player2_3rd_move_cooled_effekt_time != 0
        ) {
            PlayerTwo.cooled = false;
            PlayerTwo.speed += 2;//todo 2 mit gescheiter variable tauschen
            Player2_3rd_move_cooled_effekt_time = 0;
        }//TODO Whatwiththis
        for (KeyCode k : isPressed) {
            switch (k) {
                case A:
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
                    break;
                case W:
                    lastinputPlayerOne = System.nanoTime();
                    if (PlayerOne.y == groundY) {
                        PlayerOne.jump();
                    }
                    break;
                case S:
                    lastinputPlayerOne = System.nanoTime();
                    PlayerOne.isSneaking = true;
                    PlayerOne.y = PlayerOne.ySneaking;
                    PlayerOne.setLayoutY(PlayerOne.y);
                    PlayerOne.height = PlayerOne.heightSneaking;
                    PlayerOne.setFitHeight(PlayerOne.height);
                    //todo: sneak Frame!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    // direction mitbedenken ==> direction "false" => rechts | "true" == links
                    break;
                case D:
                    PlayerOne.setLayoutX(PlayerOne.getLayoutX() + PlayerOne.speed);
                    PlayerOne.x = (int) PlayerOne.getLayoutX();
                    PlayerOne.direction = false;
                    PlayerOne.setImage(new Image("file:images/waterbender_normal.png"));
                    lastinputPlayerOne = System.currentTimeMillis();
                    break;
                case UP:
                    if (PlayerTwo.y == groundY) {
                        PlayerTwo.jump();
                    }
                    break;
                case DOWN:
                    PlayerTwo.isSneaking = true;
                    //todo delete isSneaking (vllt doch nicht, wenn beim sneaken "gehen" animiert wird...)
                    // während jump kein sneak, ist das ok?
                    PlayerTwo.y = PlayerTwo.ySneaking;
                    PlayerTwo.setLayoutY(PlayerTwo.y);
                    PlayerTwo.height = PlayerTwo.heightSneaking;
                    PlayerTwo.setFitHeight(PlayerTwo.height);
                    //todo: sneak Frame!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    // direction mitbedenken ==> direction "false" => rechts | "true" == links
                    break;
                case LEFT:
                    PlayerTwo.setLayoutX(PlayerTwo.getLayoutX() - PlayerTwo.speed);
                    PlayerTwo.x = (int) PlayerTwo.getLayoutX();
                    PlayerTwo.direction = true;
                    PlayerTwo.setImage(new Image("file:images/waterbender_normal_reversed.png"));
                    break;
                case RIGHT:
                    PlayerTwo.setLayoutX(PlayerTwo.getLayoutX() + PlayerTwo.speed);
                    PlayerTwo.x = (int) PlayerTwo.getLayoutX();
                    PlayerTwo.direction = false;
                    PlayerTwo.setImage(new Image("file:images/waterbender_normal.png"));
                    break;
                case Y: //todo: Da steht Basic Punch von Player1
                    if ((PlayerOne.direction && (PlayerTwo.x + PlayerTwo.width > PlayerOne.x - 250 && PlayerTwo.x < PlayerOne.x))
                            || ((!PlayerOne.direction) && (PlayerTwo.x < PlayerOne.x + 250 && PlayerTwo.x > PlayerOne.x))
                    ) {
                        PlayerTwo.health = (PlayerTwo.health - PlayerOne.damage);
                        System.out.println(PlayerTwo.health);
                        updateCharacterHealth();
                    }
                    break;
                case M://todo: Da steht Basic Punch von Player2
                    if ((PlayerTwo.direction && (PlayerOne.x + PlayerOne.width > PlayerTwo.x - 250 && PlayerOne.x < PlayerTwo.x))
                            || ((!PlayerTwo.direction) && (PlayerOne.x < PlayerTwo.x + 250 && PlayerOne.x > PlayerTwo.x))
                    ) {
                        PlayerOne.health = (PlayerOne.health - PlayerTwo.damage);
                        System.out.println(PlayerOne.health);
                        updateCharacterHealth();
                    }
                    break;
                case X://todo: Da steht Basic Projectile von Player1
                    if (System.currentTimeMillis() > PlayerOne.Basic_Projektile_LastUsed + 3000) {
                        PlayerOne.Basic_Projektile_LastUsed = System.currentTimeMillis();
                        Projectile projectilePlayer1 = new Projectile(
                                150, 150, PlayerOne.x, PlayerOne.y + PlayerOne.height / 2, 20, 5,
                                false, 1, PlayerOne.direction
                        );
                        projectilePlayer1.setImage(new Image("file:images/waterball.png"));
                        everything.getChildren().add(projectilePlayer1);
                    }
                    break;
                case N://todo: Da steht Basic Projectile von Player2
                    if (System.currentTimeMillis() > PlayerTwo.Basic_Projektile_LastUsed + 3000) {
                        PlayerTwo.Basic_Projektile_LastUsed = System.currentTimeMillis();
                        Projectile projectilePlayer2 = new Projectile(
                                150, 150, PlayerTwo.x, PlayerTwo.y + PlayerTwo.height / 2, 20, 5,
                                false, 2, PlayerTwo.direction
                        );
                        projectilePlayer2.setImage(new Image("file:images/waterball.png"));
                        everything.getChildren().add(projectilePlayer2);
                    }
                    break;
                case DIGIT1://todo: Da steht water whip von Player2
                    if ((PlayerTwo.direction &&
                            (PlayerOne.x + PlayerOne.width > PlayerTwo.x - 250 && PlayerOne.x < PlayerTwo.x))) {
                        PlayerOne.health = (PlayerOne.health -
                                (PlayerTwo.damage + (PlayerTwo.x - PlayerOne.x))
                        );
                        System.out.println("damage: " +
                                (PlayerTwo.damage + (PlayerTwo.x - PlayerOne.x))
                        );
                        System.out.println(PlayerOne.health);
                        updateCharacterHealth();
                    }
                    if (((!PlayerTwo.direction) && (PlayerOne.x < PlayerTwo.x + 250 && PlayerOne.x > PlayerTwo.x))) {
                        PlayerOne.health = (PlayerOne.health -
                                (PlayerTwo.damage + (PlayerOne.x - PlayerTwo.x))
                        );
                        System.out.println("damage: " +
                                (PlayerTwo.damage + (PlayerOne.x - PlayerTwo.x))
                        );
                        System.out.println(PlayerOne.health);
                        updateCharacterHealth();
                    }
                    break;
                case NUMPAD2://todo: Da steht ice spike von Player2
                    if (PlayerTwo.second_AttackAmount > 0) {
                        Projectile projectilePlayer2 = new Projectile(
                                150, 150, PlayerTwo.x, PlayerTwo.y + PlayerTwo.height / 2, 20, 5,
                                true, 2, PlayerTwo.direction
                        );
                        int random = (int) ((Math.random() * 3) + 1);
                        if (random == 1) projectilePlayer2.setImage(new Image("file:images/ice_spike1.png"));
                        else if (random == 2) projectilePlayer2.setImage(new Image("file:images/ice_spike2.png"));
                        else projectilePlayer2.setImage(new Image("file:images/ice_spike3.png"));
                        everything.getChildren().add(projectilePlayer2);
                        PlayerTwo.second_Attack_LastUsed = System.currentTimeMillis();
                        PlayerTwo.second_AttackAmount--;
                    }
                    break;
                case NUMPAD3:
                    //Player1_3rd_move_frozen_effekt_time = PlayerTwo.frostPillar(PlayerOne);
                    break;
                case NUMPAD4:
                    break;
                case F://todo: Da steht water whip von Player1
                    if ((PlayerOne.direction &&
                            (PlayerTwo.x + PlayerTwo.width > PlayerOne.x - 250 && PlayerTwo.x < PlayerOne.x))
                    ) {
                        PlayerTwo.health = (PlayerTwo.health -
                                (PlayerOne.damage + (PlayerOne.x - PlayerTwo.x))
                        );
                        System.out.println("damage: " +
                                (PlayerOne.damage + (PlayerOne.x - PlayerTwo.x))
                        );
                        System.out.println(PlayerTwo.health);
                        updateCharacterHealth();
                    }
                    if (((!PlayerOne.direction) &&
                            (PlayerTwo.x < PlayerOne.x + 250 && PlayerTwo.x > PlayerOne.x))
                    ) {
                        PlayerTwo.health = (PlayerTwo.health -
                                (PlayerOne.damage + (PlayerTwo.x - PlayerOne.x))
                        );
                        System.out.println("damage: " +
                                (PlayerOne.damage + (PlayerTwo.x - PlayerOne.x))
                        );
                        System.out.println(PlayerTwo.health);
                        updateCharacterHealth();
                    }
                    break;
                case G:
                    if (PlayerOne.second_AttackAmount > 0) {
                        Projectile projectilePlayer1 = new Projectile(
                                150, 150, PlayerOne.x, PlayerOne.y + PlayerOne.height / 2, 20, 5,
                                true, 1, PlayerOne.direction
                        );
                        int random = (int) ((Math.random() * 3) + 1);
                        if (random == 1) projectilePlayer1.setImage(new Image("file:images/ice_spike1.png"));
                        else if (random == 2) projectilePlayer1.setImage(new Image("file:images/ice_spike2.png"));
                        else projectilePlayer1.setImage(new Image("file:images/ice_spike3.png"));
                        everything.getChildren().add(projectilePlayer1);
                        PlayerOne.second_Attack_LastUsed = System.currentTimeMillis();
                        PlayerOne.second_AttackAmount--;
                    }
                    break;
                case H:
                    //Player1_3rd_move_frozen_effekt_time = PlayerTwo.frostPillar(PlayerOne);
                    break;
                case J:
                    break;/**/
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