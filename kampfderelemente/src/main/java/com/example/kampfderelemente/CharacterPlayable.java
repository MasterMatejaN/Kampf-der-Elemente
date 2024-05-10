package com.example.kampfderelemente;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CharacterPlayable extends ImageView {
    int health;
    int width;
    int height;

    public CharacterPlayable(int health, int width, int height) {
        this.health = health;
        this.width = width;
        this.height = height;
        this.setFitHeight(health);
        this.setFitWidth(width);
    }

    public CharacterPlayable(String s, int health, int width, int height) {
        super(s);
        this.health = health;
        this.width = width;
        this.height = height;
        this.setFitHeight(health);
        this.setFitWidth(width);
    }

    public CharacterPlayable(Image image, int health, int width, int height) {
        super(image);
        this.health = health;
        this.width = width;
        this.height = height;
        this.setFitHeight(health);
        this.setFitWidth(width);
    }
}
