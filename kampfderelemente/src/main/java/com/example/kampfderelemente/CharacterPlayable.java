package com.example.kampfderelemente;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CharacterPlayable extends Button {
    int health;
    int width;
    int height;
    int x;
    int y;
    public CharacterPlayable(int health, int width, int height, int x, int y) {
        this.health = health;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.setWidth(width);
        this.setHeight(height);
        /* //wenn wir zurück zu ImageView wechseln
        this.setFitHeight(health);
        this.setFitWidth(width);
        this.setX(x);
        this.setY(y);/**/
    }
}