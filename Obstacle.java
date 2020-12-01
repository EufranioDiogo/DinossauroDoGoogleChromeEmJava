/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dinosaurgame;

import java.awt.Image;

/**
 *
 * @author ed
 */
public class Obstacle {
    private int width, height, posY, posX;
    private int obstacleXVelocity = 5;
    private Image character;

    public Obstacle(int width, int height) {
        this.width = width;
        this.height = height;
    }
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public Image getCharacter() {
        return character;
    }

    public void setCharacter(Image character) {
        this.character = character;
    }

    public int getObstacleXVelocity() {
        return obstacleXVelocity;
    }

    public void setObstacleXVelocity(int obstacleXVelocity) {
        this.obstacleXVelocity = obstacleXVelocity;
    }
    
    
    
}
