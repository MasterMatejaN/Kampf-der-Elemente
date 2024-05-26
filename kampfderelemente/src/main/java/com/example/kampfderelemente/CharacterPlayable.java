package com.example.kampfderelemente;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CharacterPlayable extends ImageView {
    int health;
    int width;
    int height;
    int x;
    int y;
    int damage;

    boolean direction;
    public CharacterPlayable(int health, int width, int height, int x, int y, int damage, boolean direction) {
        this.health = health;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.damage = damage;
        this.direction = direction;
        this.setLayoutX(x);
        this.setLayoutY(y-height);
        this.setFitWidth(width);
        this.setFitHeight(height);
        /* //wenn wir zurück zu ImageView wechseln
        this.setFitHeight(health);
        this.setFitWidth(width);
        this.setX(x);
        this.setY(y);/**/
    }
}