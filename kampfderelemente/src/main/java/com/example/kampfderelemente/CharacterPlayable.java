package com.example.kampfderelemente;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CharacterPlayable extends ImageView {
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
        this.setFitHeight(health);
        this.setFitWidth(width);
        this.setX(x);
        this.setY(y);
    }

    public CharacterPlayable(String s, int health, int width, int height, int x, int y) {
        super(s);
        this.health = health;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.setFitHeight(health);
        this.setFitWidth(width);
        this.setX(x);
        this.setY(y);
    }

    public CharacterPlayable(Image image, int health, int width, int height, int x, int y) {
        super(image);
        this.health = health;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.setFitHeight(health);
        this.setFitWidth(width);
        this.setX(x);
        this.setY(y);
    }
}
