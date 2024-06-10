package com.example.kampfderelemente;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static com.example.kampfderelemente.main.*;
import static com.example.kampfderelemente.main.everything;

public class CharacterPlayable extends ImageView {
    int health;
    int width;
    int height;
    int x;
    int y;
    int damage;
    int range;
    boolean direction; // "false" => right, "true" => left
    int speed;
    int speedUnchanged;
    int first_AttackAmount;
    int second_AttackAmount;
    int third_AttackAmount;
    int fourth_AttackAmount;
    int first_AttackMaxAmount;
    int second_AttackMaxAmount;
    int third_AttackMaxAmount;
    int fourth_AttackMaxAmount;

    long basic_Attack_LastUsed = 0;
    long basic_Projektile_LastUsed = 0;
    long first_Attack_LastUsed = 0;
    long second_Attack_LastUsed = 0;
    long third_Attack_LastUsed = 0;
    long fourth_Attack_LastUsed = 0;

    int first_Attack_Reload = 2000;
    int second_Attack_Reload = 2000;
    int third_Attack_Reload = 10000;
    int fourth_Attack_Reload = 2000;

    int basic_Attack_Range_Left = 100;
    int basic_Attack_Range_Right = 100 + width;
    int first_Attack_Range = 200;

    boolean isSneaking = false;
    int ySneaking;
    int heightSneaking;
    int yStand;
    int heightStand;
    boolean bleeding = false;
    boolean cooled = false;
    boolean frozen = false;

    long bleedingStart;
    long cooledStart;
    long frozenStart;
    int bleedingDuration = 5000;
    int cooledDuration = 2000;
    int frozenDuration = 1200;
    long timerCheckTime;
    boolean enhancedModeActive = false;
    int enhancedModeDuration = 3000;

    long basic_Attack_Cooldown = 400;
    long basic_Projektile_Cooldown = 3000;
    long first_Attack_Cooldown = 100;
    long second_Attack_Cooldown = 150;
    long third_Attack_Cooldown = (long) ((cooledDuration + frozenDuration) * 1.15);
    long fourth_Attack_Cooldown = 100;
    ImageView waterWhip = new ImageView(new Image("file:images/water_whip.png"));
    int waterWhipHeight = 40;
    int waterWhipX;
    int waterWhipY = y + height / 2;
    boolean isJumping = false;
    double velocityY = 0;
    final double GRAVITY = -150;
    final double JUMP_STRENGTH = 75;
    final double TIME_STEP = 0.016;
    long jumpstart;

    public CharacterPlayable(int health, int width, int height, int heightSneaking, int x, int y, int damage,
                             int range, boolean direction, int speed, int first_AttackAmount,
                             int second_AttackAmount, int third_AttackAmount, int fourth_AttackAmount
    ) {
        waterWhip.setFitHeight(waterWhipHeight);
        waterWhip.setFitWidth(first_Attack_Range);
        waterWhip.setLayoutY(waterWhipY);
        waterWhip.setLayoutX(-300);
        this.health = health;
        this.width = width;
        this.height = height;
        this.heightSneaking = heightSneaking;
        this.x = x;
        this.y = y;
        this.damage = damage;
        this.range = range;
        this.direction = direction;
        this.speed = speed;
        speedUnchanged = speed;
        this.first_AttackAmount = first_AttackAmount;
        this.second_AttackAmount = second_AttackAmount;
        this.third_AttackAmount = third_AttackAmount;
        this.fourth_AttackAmount = fourth_AttackAmount;
        this.first_AttackMaxAmount = first_AttackAmount;
        this.second_AttackMaxAmount = second_AttackAmount;
        this.third_AttackMaxAmount = third_AttackAmount;
        this.fourth_AttackMaxAmount = fourth_AttackAmount;
        this.ySneaking = y + (height - heightSneaking);
        this.yStand = y;
        this.heightStand = height;
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.setFitWidth(width);
        this.setFitHeight(height);
        timer();
    }

    public void timer() {
        AnimationTimer animationTimer = new AnimationTimer() {
            long time;

            @Override
            public void handle(long l) {
                Button rematch = new Button("Rematch?");
                rematch.setLayoutX(screenBounds.getWidth() /2);
                rematch.setLayoutY(screenBounds.getHeight()/2);
                rematch.setOnMousePressed((r) -> {
                    System.out.println(everything.getChildren());
                    everything.getChildren().remove(everything.getChildren().size()-1);
                    newMatch();
                });
                if(PlayerOne.health <= 0) {
                    System.out.println("P2 WINS");
                    everything.getChildren().add(rematch);
                } else if (PlayerTwo.health <= 0){
                    System.out.println("P1 WINS");
                    everything.getChildren().add(rematch);
                }
                if (isJumping) {
                    velocityY += GRAVITY * TIME_STEP;
                    y -= velocityY * TIME_STEP;
                    System.out.println(velocityY);
                    System.out.println(y);
                    if (y + height - 142 > (main.height  - main.height / 4)) {
                        System.out.println("y = " + y);
                        System.out.println("height = " + height);
                        System.out.println("y + height = " + (y + height));
                        System.out.println("groundY = " + groundY);
                        System.out.println("(main.height  - main.height / 4) = " + (main.height - main.height / 4));
                        y = (int) (main.height  - main.height / 4);
                        velocityY = 0;
                        isJumping = false;
                        y = yStand;
                        setImage(new Image("file:images/waterbender_normal.png"));
                        setLayoutY(y);
                        System.out.println("jumping stipped");
                    }

                    setLayoutY(y);
                }

                if (timerCheckTime + 1000 < System.currentTimeMillis()) {

                    // Reload attacks
                    if (first_Attack_LastUsed + first_Attack_Reload < System.currentTimeMillis() &&
                            first_AttackAmount < first_AttackMaxAmount) {
                        first_AttackAmount++;
                    }
                    if (second_Attack_LastUsed + second_Attack_Reload < System.currentTimeMillis() &&
                            second_AttackAmount < second_AttackMaxAmount) {
                        second_AttackAmount++;
                    }
                    if (third_Attack_LastUsed + third_Attack_Reload < System.currentTimeMillis() &&
                            third_AttackAmount < third_AttackMaxAmount) {
                        third_AttackAmount++;
                    }
                    if (fourth_Attack_LastUsed + fourth_Attack_Reload < System.currentTimeMillis() &&
                            fourth_AttackAmount < fourth_AttackMaxAmount) {
                        fourth_AttackAmount++;
                    }

                    // Bleeding status effect
                    if (bleeding && bleedingStart + bleedingDuration > System.currentTimeMillis()) {
                        health -= (damage / 5) * 2;
                        updateCharacterHealth();
                        System.out.println("Bleeding");
                    }
                    if (bleedingStart + bleedingDuration < System.currentTimeMillis()) {
                        bleedingStart = 0;
                        bleeding = false;
                    }

                    // Enhanced mode
                    if (enhancedModeActive) {
                        speed *= 1.5;
                    }
                    if (System.currentTimeMillis() > fourth_Attack_LastUsed + enhancedModeDuration) {
                        enhancedModeActive = false;
                        speed = speedUnchanged;
                    }

                    timerCheckTime += 1000;
                }
            }

            @Override
            public void start() {
                timerCheckTime = System.currentTimeMillis();
                time = System.currentTimeMillis();
                super.start();
            }

            @Override
            public void stop() {
                super.stop();
            }
        };
        animationTimer.start();
    }
}