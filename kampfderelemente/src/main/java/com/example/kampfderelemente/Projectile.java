package com.example.kampfderelemente;

import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;

import static com.example.kampfderelemente.main.*;

public class Projectile extends ImageView {
    int height;
    int width;
    int x;
    int y;
    int damage;
    int speed;
    boolean isIceSpike;
    int belongsToPlayer;// entweder (1 == von Player 1) oder (2 == von Player 2)
    long start;
    boolean direction;

    public Projectile(int height, int width, int x, int y, int damage, int speed, boolean isIceSpike,
                      int belongsToPlayer, boolean direction
    ) {
        this.height = height;
        this.width = width;
        this.x = x;
        this.y = y;
        this.damage = damage;
        this.speed = speed;
        this.isIceSpike = isIceSpike;
        this.belongsToPlayer = belongsToPlayer;
        this.direction = direction;
        if (this.direction) {
            this.setLayoutX(this.x);
        } else {
            this.x += width;
            this.setLayoutX(this.x);
        }
        this.setLayoutY(this.y - this.height);
        this.setFitWidth(this.width);
        this.setFitHeight(this.height);
        move();
    }

    public void move() {
        // todo: (only Akinci delete me) | notiz: direction "false" => rechts | "true" == links
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                //if (start + 50 < System.currentTimeMillis()) {
                if (x > main.width || x < 0  && !matchEnded) {
                    setLayoutX(-100);
                    setLayoutY(-100);
                    stop();
                }

                if (belongsToPlayer == 1 &&
                        !direction  && !matchEnded
                ) {
                    x += speed;
                    setLayoutX(x);
                    System.out.println(
                            "y  = " + y
                                    + "\nx  = " + x
                                    + "\nheight = " + height
                                    + "\nwidth = " + width
                                    + "\nmain.PlayerTwo.y = " + main.PlayerTwo.y
                                    + "\nmain.PlayerTwo.x = " + main.PlayerTwo.x
                                    + "\nmain.PlayerTwo.height = " + main.PlayerTwo.height
                                    + "\nmain.PlayerTwo.width = " + main.PlayerTwo.width
                    );
                    if ((
                            (x + width >= main.PlayerTwo.x - 10 && x < main.PlayerTwo.x + main.PlayerTwo.width)
                                    ||
                                    (x < main.PlayerTwo.x + main.PlayerTwo.width && x > main.PlayerTwo.x)
                    ) &&
                            (((y + height > main.PlayerTwo.y) && y < main.PlayerTwo.y + main.PlayerTwo.height))
                    ) {
                        //todo collision, give damage to player 2
                        if (!isIceSpike) {
                            main.PlayerTwo.health = main.PlayerTwo.health - damage;
                        } else {
                            System.out.println("Bleeding started");
                            main.PlayerTwo.bleeding = true;
                            main.PlayerTwo.bleedingStart = System.currentTimeMillis();
                            main.PlayerTwo.health = main.PlayerTwo.health - (damage / 5);
                        }
                        updateCharacterHealth();
                        System.out.println("clear hit with my projectile-boi");
                        setLayoutX(-100);
                        setLayoutY(-100);
                        stop();
                    }
                }
                if (belongsToPlayer == 1 &&
                        direction  && !matchEnded) {
                    x -= speed;
                    setLayoutX(x);
                    if ((
                            (x <= main.PlayerTwo.x + 10 && x > main.PlayerTwo.x + main.PlayerTwo.width)
                                    ||
                                    (x < main.PlayerTwo.x + main.PlayerTwo.width && x > main.PlayerTwo.x)
                    ) &&
                            (((y + height > main.PlayerTwo.y) && y < main.PlayerTwo.y + main.PlayerTwo.height))
                    ) {
                        //todo collision, give damage to player 2
                        System.out.println(
                                "y  = " + y
                                        + "\nx  = " + x
                                        + "\nheight = " + height
                                        + "\nwidth = " + width
                                        + "\nmain.PlayerTwo.y = " + main.PlayerTwo.y
                                        + "\nmain.PlayerTwo.x = " + main.PlayerTwo.x
                                        + "\nmain.PlayerTwo.height = " + main.PlayerTwo.height
                                        + "\nmain.PlayerTwo.width = " + main.PlayerTwo.width
                        );
                        if (!isIceSpike) {
                            main.PlayerTwo.health = main.PlayerTwo.health - damage;
                        } else {
                            System.out.println("Bleeding started");
                            main.PlayerTwo.bleeding = true;
                            main.PlayerTwo.bleedingStart = System.currentTimeMillis();
                            main.PlayerTwo.health = main.PlayerTwo.health - (damage / 5);
                        }
                        updateCharacterHealth();
                        System.out.println("clear hit with my projectile-boi");
                        setLayoutX(-100);
                        setLayoutY(-100);
                        stop();
                    }
                }

                if (belongsToPlayer == 2 &&
                        !direction  && !matchEnded
                ) {
                    x += speed;
                    setLayoutX(x);
                    System.out.println("y  = " + y
                            + "\nheight = " + height
                            + "\nmain.PlayerOne.y = " + main.PlayerOne.y
                            + "\nmain.PlayerOne.height = " + main.PlayerOne.height
                    );
                    if ((
                            (x + width >= main.PlayerOne.x - 10 && x < main.PlayerOne.x + main.PlayerOne.width)
                                    ||
                                    (x < main.PlayerOne.x + main.PlayerOne.width && x > main.PlayerOne.x)
                    ) &&
                            (((y + height > main.PlayerOne.y) && y < main.PlayerOne.y + main.PlayerOne.height))
                    ) {
                        //todo collision, give damage to player 2
                        if (!isIceSpike) {
                            main.PlayerOne.health = main.PlayerOne.health - damage;
                        } else {
                            System.out.println("Bleeding started");
                            main.PlayerOne.bleeding = true;
                            main.PlayerOne.bleedingStart = System.currentTimeMillis();
                            main.PlayerOne.health = main.PlayerOne.health - (damage / 5);
                        }
                        updateCharacterHealth();
                        System.out.println("clear hit with my projectile-boi");
                        setLayoutX(-100);
                        setLayoutY(-100);
                        stop();
                    }
                }
                if (belongsToPlayer == 2 &&
                        direction  && !matchEnded) {
                    x -= speed;
                    setLayoutX(x);
                    if ((
                            (x <= main.PlayerOne.x + 10 && x > main.PlayerOne.x + main.PlayerOne.width)
                                    ||
                                    (x < main.PlayerOne.x + main.PlayerOne.width && x > main.PlayerOne.x)
                    ) &&
                            (((y + height > main.PlayerOne.y) && y < main.PlayerOne.y + main.PlayerOne.height))
                    ) {
                        //todo collision, give damage to player 2
                        if (!isIceSpike) {
                            main.PlayerOne.health = main.PlayerOne.health - damage;
                        } else {
                            System.out.println("Bleeding started");
                            main.PlayerOne.bleeding = true;
                            main.PlayerOne.bleedingStart = System.currentTimeMillis();
                            main.PlayerOne.health = main.PlayerOne.health - (damage / 5);
                        }
                        updateCharacterHealth();
                        System.out.println("clear hit with my projectile-boi");

                        setLayoutX(-100);
                        setLayoutY(-100);
                        stop();
                    }
                }
                //start += 50;
                //}

            }

            @Override
            public void stop() {
                setLayoutX(-100);
                setLayoutY(-100);
                super.stop();
            }

            @Override
            public void start() {
                start = System.currentTimeMillis();
                super.start();
            }
        };
        animationTimer.start();
    }
}
