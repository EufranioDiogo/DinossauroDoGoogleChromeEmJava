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
    private Obstacle previousObstacle, nextObstacle;
    private boolean dinoCollided = false;

    public Obstacle(int width, int height) {
        this.width = width;
        this.height = height;
    }
    public Obstacle(int width, int height, int posX, int posY) {
        this.width = width;
        this.height = height;
        this.posX = posX;
        this.posY = posY;
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
    
    public void setPreviousObstacle(Obstacle obstacle) {
        this.previousObstacle = obstacle;
    }
    
    public void setNextObstacle(Obstacle obstacle) {
        this.nextObstacle = obstacle;
    }
    
    public Obstacle getPreviousObstacle() {
        return this.previousObstacle;
    }
    
    public Obstacle getNextObstacle() {
        return this.nextObstacle;
    }
    
    public void setDinoCollided(boolean obstaclePassed) {
        this.dinoCollided = obstaclePassed;
    }
    public boolean getDinoCollided() {
        return this.dinoCollided;
    }
}
