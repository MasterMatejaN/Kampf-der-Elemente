package com.example.kampfderelemente;

import javafx.animation.AnimationTimer;
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
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class main extends Application {

    /*Overall*/
    public static Group everything = new Group();
    public static Scene fight = new Scene(everything);
    public static Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
    public static int width = (int) screenBounds.getWidth();
    public static int height = (int) screenBounds.getHeight();

    /*Design*/
    static List<ProgressBar> p = new ArrayList<>();

    public static ProgressBar HpPlayer1 = new ProgressBar(100);
    public static ProgressBar HpPlayer2 = new ProgressBar(100);

    public static ProgressBar HpPlayer12 = new ProgressBar(100);
    public static ProgressBar HpPlayer22 = new ProgressBar(100);

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
    public static Set<KeyCode> isPressed = new HashSet<>();
    public static boolean go = false;
    public static boolean go1 = false;

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Kampf der Elemente");

        PlayerOne.setImage(new Image("file:images/waterbender_normal.png"));
        PlayerTwo.setImage(new Image("file:images/waterbender_normal_reversed.png"));

        ImageView background = new ImageView(new Image("file:images/background_pixel.png"));
        background.setFitWidth((int) screenBounds.getWidth());
        background.setFitHeight((int) screenBounds.getHeight());

        System.out.println("PlayerOne.x = " + PlayerOne.x);
        System.out.println("PlayerTwo.x = " + PlayerTwo.x);

        p.add(HpPlayer1);
        p.add(HpPlayer2);

        ImageView ground = new ImageView(new Image(("file:images/ground.png")));
        ground.setX(0);
        ground.setY((int) screenBounds.getHeight() - height / 4);
        ground.setFitWidth(width);
        ground.setFitHeight(height / 4);

        fight.setOnKeyReleased(e -> {
            boolean containedS = false;
            boolean containedK = false;
            boolean containedA = false;
            boolean containedD = false;
            boolean containedJ = false;
            boolean containedL = false;
            if (isPressed.contains(KeyCode.S)) {
                containedS = true;
            }
            if (isPressed.contains(KeyCode.K)) {
                containedK = true;
            }
            if(isPressed.contains(KeyCode.A)) {
                containedA = true;
            }
            if(isPressed.contains(KeyCode.D)) {
                containedD = true;
            }
            if(isPressed.contains(KeyCode.J)) {
                containedJ = true;
            }
            if(isPressed.contains(KeyCode.L)) {
                containedL = true;
            }
            isPressed.remove(e.getCode());
            if (!isPressed.contains(KeyCode.S) && containedS && !PlayerOne.frozen) {
                PlayerOne.isSneaking = false;
                PlayerOne.y = PlayerOne.yStand;
                PlayerOne.setLayoutY(PlayerOne.y);
                PlayerOne.height = PlayerOne.heightStand;
                PlayerOne.setFitHeight(PlayerOne.height);
            }
            if (!isPressed.contains(KeyCode.K) && containedK && !PlayerTwo.frozen) {
                PlayerTwo.isSneaking = false;
                PlayerTwo.y = PlayerTwo.yStand;
                PlayerTwo.setLayoutY(PlayerTwo.y);
                PlayerTwo.height = PlayerTwo.heightStand;
                PlayerTwo.setFitHeight(PlayerTwo.height);
            }
            if(!isPressed.contains(KeyCode.A) && containedA) {
                a = 0;
            }
            if(!isPressed.contains(KeyCode.D) && containedD) {
                b = 0;
            }
            if(!isPressed.contains(KeyCode.J) && containedJ) {
                c = 0;
            }
            if(!isPressed.contains(KeyCode.L) && containedL) {
                d = 0;
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
            }
        });
        t.start();

        fight.setOnKeyPressed(e -> {
            isPressed.add(e.getCode());
            System.out.println(isPressed);
        });



        everything.getChildren().addAll(background, ground, PlayerOne, PlayerTwo);
        everything.getChildren().add(p.get(0));
        everything.getChildren().add(p.get(1));

        HpPlayer1.setProgress((double) PlayerOne.health / 100);
        HpPlayer2.setProgress((double) PlayerTwo.health / 100);

        HpPlayer1.setLayoutX(screenBounds.getWidth()/6);
        HpPlayer2.setLayoutX(screenBounds.getWidth()*5/6);
        stage.setScene(fight);
        stage.show();
    }

    public static void newMatch() {
        PlayerOne = new CharacterPlayable(
                100, 250, 500, 300, (int) (width / 10), groundY, 5, 250, false, 5,
                2, 2, 1, 1
        );
        PlayerTwo = new CharacterPlayable(
                100, 250, 500, 300, (int) (width / 10) * 7, groundY, 5, 250, true, 5,
                2, 2, 1, 1
        );

        PlayerOne.setImage(new Image("file:images/waterbender_normal.png"));
        PlayerTwo.setImage(new Image("file:images/waterbender_normal_reversed.png"));
        PlayerOne.setLayoutX(PlayerOne.x);
        PlayerTwo.setLayoutX(PlayerTwo.x);

        ImageView background = new ImageView(new Image("file:images/background_pixel.png"));
        background.setFitWidth((int) screenBounds.getWidth());
        background.setFitHeight((int) screenBounds.getHeight());

        System.out.println("PlayerOne.x = " + PlayerOne.x);
        System.out.println("PlayerTwo.x = " + PlayerTwo.x);

        updateCharacterHealth();

        ImageView ground = new ImageView(new Image(("file:images/ground.png")));
        ground.setX(0);
        ground.setY((int) screenBounds.getHeight() - height / 4);
        ground.setFitWidth(width);
        ground.setFitHeight(height / 4);

        fight.setOnKeyPressed(e -> {
            isPressed.add(e.getCode());
            System.out.println(isPressed);
        });

        everything.getChildren().addAll(background, ground, PlayerOne, PlayerTwo);

        p.remove(0);
        p.remove(0);
        HpPlayer1 = new ProgressBar(100);
        HpPlayer2 = new ProgressBar(100);
        p.add(HpPlayer1);
        p.add(HpPlayer2);

        everything.getChildren().addAll(p.get(0), p.get(1));

        p.get(0).setLayoutX(screenBounds.getWidth()/6);
        p.get(1).setLayoutX(screenBounds.getWidth()*5/6);
    }

    long a = 0;
    long b = 0;
    long c = 0;
    long d = 0;
    int counterPlayer1 = 1;
    int counterPlayer2 = 1;
    private void handleIsPressed(Set<KeyCode> isPressed) {
        for (KeyCode k : isPressed) {
            switch (k) {
                case A:
                    if (!PlayerOne.frozen) {
                        PlayerOne.setLayoutX(PlayerOne.getLayoutX() - PlayerOne.speed);
                        PlayerOne.x = (int) PlayerOne.getLayoutX();
                        PlayerOne.direction = true;
                        //PlayerOne.setImage(new Image("file:images/waterbender_walking_reversed.png"));
                        lastinputPlayerOne = System.nanoTime();
                        if(a == 0) {
                            a = System.currentTimeMillis();
                        }
                        if(a+500 < System.currentTimeMillis()) {
                            if(counterPlayer1 == 1) {
                                PlayerOne.setImage(new Image("file:images/waterbender_walking_reversed.png"));
                                counterPlayer1++;
                            } else {
                                PlayerOne.setImage(new Image("file:images/waterbender_normal_reversed.png"));
                                counterPlayer1--;
                            }
                            a+=500;
                        }
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
                        lastinputPlayerOne = System.currentTimeMillis();
                        if(b == 0) {
                            b = System.currentTimeMillis();
                        }
                        if(b+500 < System.currentTimeMillis()) {
                            if(counterPlayer1 == 1) {
                                PlayerOne.setImage(new Image("file:images/waterbender_walking.png"));
                                counterPlayer1++;
                            } else {
                                PlayerOne.setImage(new Image("file:images/waterbender_normal.png"));
                                counterPlayer1--;
                            }
                            b+=500;
                        }
                    }
                    break;
                case I:
                    if (!PlayerTwo.frozen) {
                        if (PlayerTwo.y == groundY) {
                            PlayerTwo.jump();
                        }
                    }
                    break;
                case K:
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
                case J:
                    if (!PlayerTwo.frozen) {
                        PlayerTwo.setLayoutX(PlayerTwo.getLayoutX() - PlayerTwo.speed);
                        PlayerTwo.x = (int) PlayerTwo.getLayoutX();
                        PlayerTwo.direction = true;
                        if(c == 0) {
                            c = System.currentTimeMillis();
                        }
                        if(c+500 < System.currentTimeMillis()) {
                            if(counterPlayer2 == 1) {
                                PlayerTwo.setImage(new Image("file:images/waterbender_walking_reversed.png"));
                                counterPlayer2++;
                            } else {
                                PlayerTwo.setImage(new Image("file:images/waterbender_normal_reversed.png"));
                                counterPlayer2--;
                            }
                            c+=500;
                        }
                    }
                    break;
                case L:
                    if (!PlayerTwo.frozen) {
                        PlayerTwo.setLayoutX(PlayerTwo.getLayoutX() + PlayerTwo.speed);
                        PlayerTwo.x = (int) PlayerTwo.getLayoutX();
                        PlayerTwo.direction = false;
                        if(d == 0) {
                            d = System.currentTimeMillis();
                        }
                        if(d+500 < System.currentTimeMillis()) {
                            if(counterPlayer2 == 1) {
                                PlayerTwo.setImage(new Image("file:images/waterbender_walking.png"));
                                counterPlayer2++;
                            } else {
                                PlayerTwo.setImage(new Image("file:images/waterbender_normal.png"));
                                counterPlayer2--;
                            }
                            d+=500;
                        }
                    }
                    break;
                case Q: //todo: Da steht Basic Punch Player1
                    if (((PlayerOne.direction && !PlayerTwo.frozen
                            && (PlayerTwo.x + PlayerTwo.width > PlayerOne.x - PlayerOne.basic_Attack_Range_Left
                            && PlayerTwo.x < PlayerOne.x)
                    ) || ((!PlayerOne.direction) && (PlayerTwo.x < PlayerOne.x + PlayerOne.basic_Attack_Range_Right
                            && PlayerTwo.x > PlayerOne.x)))
                            && (System.currentTimeMillis() >
                            PlayerOne.basic_Attack_LastUsed + PlayerOne.basic_Attack_Cooldown)
                    ) {
                        if(PlayerOne.direction){
                            PlayerOne.setImage(new Image("file:images/waterbender_punch_reversed.png"));
                        } else {
                            PlayerOne.setImage(new Image("file:images/waterbender_punch.png"));
                        }
                        PlayerOne.basic_Attack_LastUsed = System.currentTimeMillis();
                        PlayerTwo.health = (PlayerTwo.health - PlayerOne.damage);
                        System.out.println(PlayerTwo.health);
                        updateCharacterHealth();
                    }
                    break;
                case U://todo: Da steht Basic Punch Player2
                    if (((PlayerTwo.direction
                            && (PlayerOne.x + PlayerOne.width > PlayerTwo.x - PlayerTwo.basic_Attack_Range_Left
                            && PlayerOne.x < PlayerTwo.x)
                    ) || ((!PlayerTwo.direction) && (PlayerOne.x < PlayerTwo.x + PlayerTwo.basic_Attack_Range_Right
                            && PlayerOne.x > PlayerTwo.x)))
                            && (System.currentTimeMillis() >
                            PlayerTwo.basic_Attack_LastUsed + PlayerTwo.basic_Attack_Cooldown)
                    ) {
                        if(PlayerTwo.direction){
                            PlayerTwo.setImage(new Image("file:images/waterbender_punch_reversed.png"));
                        } else {
                            PlayerTwo.setImage(new Image("file:images/waterbender_punch.png"));
                        }
                        PlayerTwo.basic_Attack_LastUsed = System.currentTimeMillis();
                        PlayerOne.health = (PlayerOne.health - PlayerTwo.damage);
                        System.out.println(PlayerOne.health);
                        updateCharacterHealth();
                    }
                    break;
                case E://todo: Da steht Basic Projectile Player1
                    if (System.currentTimeMillis() >
                            PlayerOne.basic_Projektile_LastUsed + PlayerOne.basic_Projektile_Cooldown
                    ) {
                        if(PlayerOne.direction){
                            PlayerOne.setImage(new Image("file:images/waterbender_punch_reversed.png"));
                        } else {
                            PlayerOne.setImage(new Image("file:images/waterbender_punch.png"));
                        }
                        PlayerOne.basic_Projektile_LastUsed = System.currentTimeMillis();
                        Projectile projectilePlayer1 = new Projectile(
                                150, 150, PlayerOne.x, PlayerOne.y + PlayerOne.height / 2, 20, 5,
                                false, 1, PlayerOne.direction
                        );
                        projectilePlayer1.setImage(new Image("file:images/waterball.png"));
                        everything.getChildren().add(projectilePlayer1);
                    }
                    break;
                case O://todo: Da steht Basic Projectile Player2
                    if (System.currentTimeMillis() >
                            PlayerTwo.basic_Projektile_LastUsed + PlayerTwo.basic_Projektile_Cooldown
                    ) {
                        if(PlayerTwo.direction){
                            PlayerTwo.setImage(new Image("file:images/waterbender_punch_reversed.png"));
                        } else {
                            PlayerTwo.setImage(new Image("file:images/waterbender_punch.png"));
                        }
                        PlayerTwo.basic_Projektile_LastUsed = System.currentTimeMillis();
                        Projectile projectilePlayer2 = new Projectile(
                                150, 150, PlayerTwo.x, PlayerTwo.y + PlayerTwo.height / 2, 20, 5,
                                false, 2, PlayerTwo.direction
                        );
                        projectilePlayer2.setImage(new Image("file:images/waterball.png"));
                        everything.getChildren().add(projectilePlayer2);
                    }
                    break;
                case P://todo: Da steht water-whip Player2
                    if (PlayerTwo.second_AttackAmount > 0) {
                        if(PlayerTwo.direction){
                            PlayerTwo.setImage(new Image("file:images/waterbender_punch_reversed.png"));
                        } else {
                            PlayerTwo.setImage(new Image("file:images/waterbender_punch.png"));
                        }
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
                                && (PlayerOne.x < PlayerTwo.x + PlayerTwo.first_Attack_Range + PlayerTwo.width
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
                case H://todo: Da steht ice-spike Player2
                    if (PlayerTwo.second_AttackAmount > 0) {
                        if(PlayerTwo.direction){
                            PlayerTwo.setImage(new Image("file:images/waterbender_punch_reversed.png"));
                        } else {
                            PlayerTwo.setImage(new Image("file:images/waterbender_punch.png"));
                        }
                        boolean projectileDirection;
                        Projectile projectilePlayer2 = new Projectile(
                                50, 150, PlayerTwo.x, PlayerTwo.y + PlayerTwo.height / 2, 20, 5,
                                true, 2, projectileDirection = PlayerTwo.direction
                        );
                        int random = (int) ((Math.random() * 3) + 1);
                        if (projectileDirection) {// todo hier ice-spike bild mit reversed ersetzen
                            if (random == 1) projectilePlayer2.setImage(new Image("file:images/ice_spike1_reversed.png"));
                            else if (random == 2) projectilePlayer2.setImage(new Image("file:images/ice_spike2_reversed.png"));
                            else projectilePlayer2.setImage(new Image("file:images/ice_spike3_reversed.png"));
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
                case N://todo: Da steht frost-pillar Player2
                    if (PlayerTwo.third_AttackAmount > 0) {
                        if(PlayerTwo.direction){
                            PlayerTwo.setImage(new Image("file:images/waterbender_punch_reversed.png"));
                        } else {
                            PlayerTwo.setImage(new Image("file:images/waterbender_punch.png"));
                        }
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
                case M://todo: Da steht enhanced-movement Player2
                    if (PlayerTwo.fourth_AttackAmount > 0) {
                        if(PlayerTwo.direction){
                            PlayerTwo.setImage(new Image("file:images/waterbender_punch_reversed.png"));
                        } else {
                            PlayerTwo.setImage(new Image("file:images/waterbender_punch.png"));
                        }
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
                case R://todo: Da steht water-whip Player1
                    if (PlayerOne.second_AttackAmount > 0) {
                        if(PlayerOne.direction){
                            PlayerOne.setImage(new Image("file:images/waterbender_punch_reversed.png"));
                        } else {
                            PlayerOne.setImage(new Image("file:images/waterbender_punch.png"));
                        }
                        if ((PlayerOne.direction
                                && (PlayerTwo.x + PlayerTwo.width > PlayerOne.x - PlayerOne.first_Attack_Range
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
                                && (PlayerTwo.x < PlayerOne.x + PlayerOne.first_Attack_Range + PlayerTwo.width
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
                case X://todo: Da steht ice-spike Player1
                    if (PlayerOne.second_AttackAmount > 0) {
                        if(PlayerOne.direction){
                            PlayerOne.setImage(new Image("file:images/waterbender_punch_reversed.png"));
                        } else {
                            PlayerOne.setImage(new Image("file:images/waterbender_punch.png"));
                        }
                        boolean projectileDirection;
                        Projectile projectilePlayer1 = new Projectile(
                                50, 150, PlayerOne.x, PlayerOne.y + PlayerOne.height / 2, 20, 5,
                                true, 1, projectileDirection = PlayerOne.direction
                        );
                        int random = (int) ((Math.random() * 3) + 1);
                        if (projectileDirection) {// todo hier ice-spike bild mit reversed ersetzen
                            if (random == 1) projectilePlayer1.setImage(new Image("file:images/ice_spike1_reversed.png"));
                            else if (random == 2) projectilePlayer1.setImage(new Image("file:images/ice_spike2_reversed.png"));
                            else projectilePlayer1.setImage(new Image("file:images/ice_spike3_reversed.png"));
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
                case C://todo: Da steht frost-pillar Player1
                    if (PlayerOne.third_AttackAmount > 0) {
                        if(PlayerOne.direction){
                            PlayerOne.setImage(new Image("file:images/waterbender_punch_reversed.png"));
                        } else {
                            PlayerOne.setImage(new Image("file:images/waterbender_punch.png"));
                        }
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
                case F://todo: Da steht enhanced-movement Player1
                    if (PlayerOne.fourth_AttackAmount > 0) {
                        if(PlayerOne.direction){
                            PlayerOne.setImage(new Image("file:images/waterbender_punch_reversed.png"));
                        } else {
                            PlayerOne.setImage(new Image("file:images/waterbender_punch.png"));
                        }
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
                default:
                    if(PlayerOne.direction) {
                        if (go) {
                            PlayerOne.setImage(new Image("file:images/waterbender_normal_reversed.png"));
                            go = false;
                        } else {
                            PlayerOne.setImage(new Image("file:images/waterbender_standing_reversed.png"));
                            go = true;
                        }
                    } else {
                        if (go) {
                            PlayerOne.setImage(new Image("file:images/waterbender_normal.png"));
                            go = false;
                        } else {
                            PlayerOne.setImage(new Image("file:images/waterbender_standing.png"));
                            go = true;
                        }
                    }

                    if(PlayerTwo.direction) {
                        if (go1) {
                            PlayerTwo.setImage(new Image("file:images/waterbender_normal_reversed.png"));
                            go1 = false;
                        } else {
                            PlayerTwo.setImage(new Image("file:images/waterbender_standing_reversed.png"));
                            go1 = true;
                        }
                    } else {
                        if (go1) {
                            PlayerTwo.setImage(new Image("file:images/waterbender_normal.png"));
                            go1 = false;
                        } else {
                            PlayerOne.setImage(new Image("file:images/waterbender_standing.png"));
                            go1 = true;
                        }
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
        HpPlayer12.setProgress((double) PlayerOne.health / 100);
        HpPlayer22.setProgress((double) PlayerTwo.health / 100);
    }
}