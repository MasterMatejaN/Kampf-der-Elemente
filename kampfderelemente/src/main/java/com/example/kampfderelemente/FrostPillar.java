package com.example.kampfderelemente;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

import static com.example.kampfderelemente.main.*;

public class FrostPillar extends ImageView {
    int x;
    int y;
    int width;
    int height;
    long start;
    int[] FrameDurationArray;
    int Frames1ToNDuration = 0;
    boolean frostEnd = false;
    long endFrost;
    long endCool;
    long timerCheckTime;
    int frameCounter = 1;
    boolean belongsToPlayer1;

    public FrostPillar(int x, int y, int width, int height, long start, CharacterPlayable user,
                       CharacterPlayable other, boolean belongsToPlayer1
    ) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        setLayoutX(x);
        setLayoutY(y);
        setFitWidth(width);
        setFitHeight(height);
        this.belongsToPlayer1 = belongsToPlayer1;
        this.start = start;
        int frameInt = user.frozenDuration / 3;
        FrameDurationArray = new int[]{frameInt, frameInt, frameInt};
        for (int i : FrameDurationArray) {
            Frames1ToNDuration += i;
        }
        endFrost = start + Frames1ToNDuration;
        freeze(other, user);
    }

    private void imageChange() {
        if (frameCounter == 3) {
            this.setImage(new Image("file:images/frost_pillar3.png"));
            System.out.println("drittes bild");
            frameCounter++;
        }
        if (frameCounter == 2) {
            this.setImage(new Image("file:images/frost_pillar2.png"));
            System.out.println("zweites bild");
            frameCounter++;
        }
        if (frameCounter == 1) {
            this.setImage(new Image("file:images/frost_pillar1.png"));
            System.out.println("erstes bild");
            frameCounter++;
        }
    }

    public void freeze(CharacterPlayable character, CharacterPlayable user) {
        character.health -= user.damage / 4;
        main.updateCharacterHealth();
        System.out.println("frozen beginn");
        character.speed = 0;
        character.frozen = true;
        character.frozenStart = System.currentTimeMillis();
        endFrost = character.frozenStart + Frames1ToNDuration + FrameDurationArray[0];
        endCool = endFrost + character.cooledDuration;
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (timerCheckTime + FrameDurationArray[0] < System.currentTimeMillis() && !matchEnded) {
                    System.out.println(System.currentTimeMillis() - timerCheckTime + FrameDurationArray[0]);
                    if (endFrost < System.currentTimeMillis() && !frostEnd) {
                        System.out.println("frozen end");
                        System.out.println("cooled beginn");
                        character.frozen = false;
                        character.frozenStart = 0;
                        character.cooledStart = System.currentTimeMillis();
                        character.speed = character.speedUnchanged / 2;
                        character.cooled = true;
                        setLayoutX(-1000);
                        setLayoutY(-1000);
                        frostEnd = true;
                        if (character.direction && belongsToPlayer1 && !isPressed.contains(KeyCode.DOWN)) {
                            System.out.println("Player2 looking left");
                            PlayerTwo.setImage(new Image("file:images/waterbender_normal_reversed.png"));
                            PlayerTwo.isSneaking = false;
                            PlayerTwo.y = PlayerTwo.yStand;
                            PlayerTwo.setLayoutY(PlayerTwo.y);
                            PlayerTwo.height = PlayerTwo.heightStand;
                            PlayerTwo.setFitHeight(PlayerTwo.height);
                        } else if (!character.direction && belongsToPlayer1 && !isPressed.contains(KeyCode.DOWN)) {
                            System.out.println("Player2 looking right");
                            PlayerTwo.setImage(new Image("file:images/waterbender_normal.png"));
                            PlayerTwo.isSneaking = false;
                            PlayerTwo.y = PlayerTwo.yStand;
                            PlayerTwo.setLayoutY(PlayerTwo.y);
                            PlayerTwo.height = PlayerTwo.heightStand;
                            PlayerTwo.setFitHeight(PlayerTwo.height);
                        } else if (character.direction && !belongsToPlayer1 && !isPressed.contains(KeyCode.S)) {
                            System.out.println("Player1 looking left");
                            PlayerOne.setImage(new Image("file:images/waterbender_normal_reversed.png"));
                            PlayerOne.isSneaking = false;
                            PlayerOne.y = PlayerOne.yStand;
                            PlayerOne.setLayoutY(PlayerOne.y);
                            PlayerOne.height = PlayerOne.heightStand;
                            PlayerOne.setFitHeight(PlayerOne.height);
                        } else if (!character.direction && !belongsToPlayer1 && !isPressed.contains(KeyCode.S)) {
                            System.out.println("Player1 looking right");
                            PlayerOne.setImage(new Image("file:images/waterbender_normal.png"));
                            PlayerOne.isSneaking = false;
                            PlayerOne.y = PlayerOne.yStand;
                            PlayerOne.setLayoutY(PlayerOne.y);
                            PlayerOne.height = PlayerOne.heightStand;
                            PlayerOne.setFitHeight(PlayerOne.height);
                        }
                    }
                    if (endCool < System.currentTimeMillis()) {
                        System.out.println("cooled end");
                        character.cooled = false;
                        character.speed = character.speedUnchanged;
                        this.stop();
                    }
                    if (character.frozen) {
                        System.out.println(frameCounter);
                        imageChange();
                        System.out.println(frameCounter);
                    }
                    timerCheckTime += FrameDurationArray[0];
                }
            }
            @Override
            public void stop() {
                super.stop();
            }
            @Override
            public void start() {
                timerCheckTime = System.currentTimeMillis();
                super.start();
            }
        };
        animationTimer.start();
    }
}
