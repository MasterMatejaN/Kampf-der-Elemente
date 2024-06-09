package com.example.kampfderelemente;

import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;

import static com.example.kampfderelemente.main.*;

public class CharacterPlayable extends ImageView {
    int health;
    int width;
    int height;
    int x;
    int y;
    int damage;
    int range;
    // todo: (only Akinci delete me) | notiz: direction "false" => rechts | "true" == links
    boolean direction;
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


    int basic_Attack_Range = 100;
    int first_Attack_Range = 250;

    boolean jumping = false;
    int heightDuringJump;
    int jump_MaxHeight;
    long lastValidJump;
    boolean isSneaking = false;
    int ySneaking;
    int heightSneaking;
    int yStand;
    int heightStand;
    //todo booleans nicht nötig, mit nur long geht auch
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
    public CharacterPlayable(int health, int width, int height, int heightSneaking, int x, int y, int damage,
                             int range, boolean direction, int speed, int first_AttackAmount,
                             int second_AttackAmount, int third_AttackAmount, int fourth_AttackAmount
    ) {
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
            @Override
            public void handle(long l) {
                if (timerCheckTime + 1000 < System.currentTimeMillis()) {
                    //reload attacks
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

                    //bleeding status effect
                    if (bleeding && bleedingStart + bleedingDuration > System.currentTimeMillis()) {
                        health -= (damage / 5) * 2;
                        updateCharacterHealth();
                        System.out.println("Bleeding");
                    }
                    if (bleedingStart + bleedingDuration < System.currentTimeMillis()) {
                        bleedingStart = 0;
                        bleeding = false;
                        //System.out.println("no Bleeding");
                    }
                    if (enhancedModeActive) {
                        speed *= 1.5;
                    }
                    if (System.currentTimeMillis() > fourth_Attack_LastUsed + enhancedModeDuration){
                        enhancedModeActive = false;
                        speed = speedUnchanged;
                    }
                    timerCheckTime += 1000;
                }
            }

            @Override
            public void start() {
                timerCheckTime = System.currentTimeMillis();
                super.start();
            }

            @Override
            public void stop() {
                super.stop();
            }
        };
        animationTimer.start();
    }

    public void jump() {//todo delete this
        jumping = true;
        long jumpstart = System.currentTimeMillis();
/*        while (jumpstart + 20 > System.currentTimeMillis()) {
            y -= 1;
            this.setLayoutY(y - height);
        }
        while(y < YGround){
            y+=5;
        }/**/
    }
}
