/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dinosaurgame;

import javax.swing.ImageIcon;

/**
 *
 * @author ed
 */
public class ObstacleList {
    public Obstacle headObstacle, tailObstacle;
    private int quantObstacle;
    
    public ObstacleList() {
        
    }
    public void add(Obstacle obstacle) {
        if (this.isEmpty()) {
            this.headObstacle = obstacle;
            this.tailObstacle = this.headObstacle;
            this.headObstacle.setNextObstacle(this.tailObstacle);
            this.tailObstacle.setPreviousObstacle(this.headObstacle);
        } else {
            this.tailObstacle.setNextObstacle(obstacle);
            obstacle.setPreviousObstacle(this.tailObstacle);
            this.tailObstacle = obstacle;
        }
        this.quantObstacle++;
    }
    
    public Obstacle remove() {
        if (!this.isEmpty()) {
            Obstacle obstacleAux = this.headObstacle;
            
            this.headObstacle = this.headObstacle.getNextObstacle();
            this.headObstacle.setPreviousObstacle(null);
            this.quantObstacle--;
            return obstacleAux;
        }
        return null;
    }
    public boolean isEmpty() {
        return quantObstacle == 0;
    }
    
    public void forwardObstacle(int velocityArray[], int velocityIndex) {
        Obstacle obstacleAux = this.headObstacle;
        int counter = 0;
        
        while (counter < this.quantObstacle) {
            obstacleAux.setPosX(obstacleAux.getPosX() - velocityArray[velocityIndex]);
            obstacleAux = obstacleAux.getNextObstacle();
            counter++;
        }
    }
    
    public Obstacle getHeadObstacle() {
        return this.headObstacle;
    }
    
    public Obstacle getTailObstacle() {
        return this.tailObstacle;
    }
    public int getQuantObstacles() {
        return this.quantObstacle;
    }
}
