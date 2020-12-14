/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dinosaurgame;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JButton;


/**
 *
 * @author ed
 */
public class Painel extends JPanel implements ActionListener, Runnable {
    private boolean running = true;
    private final int gravityForce = 13;
    
    private final Dinossauro dinossauro = new Dinossauro(50, 75, 0, 25);
    private boolean actualDinoCharacter = false;
    private int changeDinoCharacter = 300;
    private boolean dinoJumping = false;
    private int dinoLife = 100;
    private int posLimitY = 0;
    private final int lifeBarWidth = 250;
    private final int superPowerBarWidth = 250;
    private int superPowerBarLevel = 0;
    private boolean superPowerBarFlagActived = false;
    private int fireDelay = 300;
    private int dinoScore = 0;
    
    
    private int dayPeriod = 0;
    private int dayDelay = 500;
    private byte enviornmentColor = 0;
    private final int[] possibleVelocity = {10, 15, 20, 25, 30, 35, 40, 45};
    private int velocityController = 0;
    private int velocityIndex = 0;
    private int firstScenarioXMovimentation, secondScenarioXMovimentation = 850;
    private LifePointObject lifePoint;
    private boolean lifePointFlag;
    
    private int obstacleDistanceDelay = 200;
    private final ObstacleList obstaclesList = new ObstacleList();
    
    static InputKeyBoard keyboard = new InputKeyBoard();
    private boolean flag = true;
    private final Thread thread;
    
    
    public Painel() {
        running = true;
        thread = new Thread(this);
        this.setFocusable(true);
        addKeyListener(keyboard);
        thread.start();
        dinossauro.setCharacter(new ImageIcon("src/main/java/com/mycompany/dinosaurgame/Dinosaur2.png").getImage());
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (this.dayPeriod == 0) {
            g2d.setColor(Color.WHITE);
        } else {
            g2d.setColor(new Color(22, 22, 22));
        }
        
        paintScenario(g2d);
        mainMethod(g2d);
    }
    
    public void mainMethod(Graphics2D g2d) {
        if (this.flag) {
            dinossauro.setPosY((int)(getHeight() - dinossauro.getHeight() - 5));
            this.flag = false;
            posLimitY = dinossauro.getPosY();
            obstaclesList.add(new Obstacle(30, (int)(dinossauro.getHeight() * 0.75), getWidth() + 10, getHeight() - (int)(dinossauro.getHeight() * 0.75)));
        }
        
        if (this.running == false) {
            gameOverScreen(g2d);
        } else {
            this.paintEnviornment(g2d);
        }
    }
    
    public void paintEnviornment(Graphics2D g2d) {
        this.scoreBoard(g2d);
        this.lifeStatus(g2d);
        this.powerStatus(g2d);
        
        g2d.drawImage(dinossauro.getCharacter(), dinossauro.getPosX(), dinossauro.getPosY(),
            dinossauro.getWidth(), dinossauro.getHeight(), this);
        obstacleEngine(g2d);
    }
    
    public void paintScenario(Graphics2D g2d) {
        AffineTransform aff = g2d.getTransform();
        g2d.fillRect(0, 0, getWidth(), getHeight());
        this.dayDelay -= 3;
       
        if (this.dayDelay <= 0) {
            if (this.dayPeriod == 0) {
                this.dayPeriod = 1;
                this.enviornmentColor = 1;
            } else {
                this.dayPeriod = 0;
                this.enviornmentColor = 0;
            }
            this.dayDelay = 500;
        }
        
        GeneralPath pen = new GeneralPath();
        
        g2d.translate(firstScenarioXMovimentation, 0);
        g2d.setColor(new Color(229, 166, 6));
        pen.moveTo(0, getHeight());
        pen.lineTo(10, getHeight() - 80);
        pen.lineTo(20, getHeight());
        pen.lineTo(50, getHeight() - 90);
        pen.lineTo(100, getHeight());
        pen.lineTo(125, getHeight() - 100);
        pen.lineTo(150, getHeight());
        pen.lineTo(200, getHeight() - 150);
        pen.lineTo(300, getHeight());
        pen.lineTo(350, getHeight() - 60);
        pen.lineTo(400, getHeight());
        pen.lineTo(410, getHeight() - 100);
        pen.lineTo(420, getHeight());
        pen.lineTo(500, getHeight() - 150);
        pen.lineTo(600, getHeight());
        pen.lineTo(650, getHeight() - 100);
        pen.lineTo(700, getHeight());
        pen.lineTo(750, getHeight() - 150);
        pen.lineTo(850, getHeight());
        g2d.fill(pen);
        g2d.setTransform(aff);
        
        firstScenarioXMovimentation -= possibleVelocity[velocityIndex];
        if (firstScenarioXMovimentation <= -850) {
            firstScenarioXMovimentation = 850;
        }
        
        g2d.translate(secondScenarioXMovimentation, 0);
        g2d.setColor(new Color(229, 166, 6));
        pen.moveTo(0, getHeight());
        pen.lineTo(10, getHeight() - 80);
        pen.lineTo(20, getHeight());
        pen.lineTo(50, getHeight() - 90);
        pen.lineTo(100, getHeight());
        pen.lineTo(125, getHeight() - 100);
        pen.lineTo(150, getHeight());
        pen.lineTo(200, getHeight() - 150);
        pen.lineTo(300, getHeight());
        pen.lineTo(350, getHeight() - 60);
        pen.lineTo(400, getHeight());
        pen.lineTo(410, getHeight() - 100);
        pen.lineTo(420, getHeight());
        pen.lineTo(500, getHeight() - 150);
        pen.lineTo(600, getHeight());
        pen.lineTo(650, getHeight() - 100);
        pen.lineTo(700, getHeight());
        pen.lineTo(750, getHeight() - 150);
        pen.lineTo(850, getHeight());
        g2d.fill(pen);
        
        secondScenarioXMovimentation -= possibleVelocity[velocityIndex];
        if (secondScenarioXMovimentation <= -850) {
            secondScenarioXMovimentation = 850;
        }
        
        g2d.setTransform(aff);
        
    }

    public void powerStatus(Graphics2D g2d) {
        g2d.setColor(Color.gray);
        g2d.fillRoundRect(10, 80, superPowerBarWidth, 25, 10, 10);
        g2d.setColor(new Color(0, 157, 255));
        g2d.fillRoundRect(10, 80, (int)((superPowerBarWidth * superPowerBarLevel) / 5), 25, 10, 10);
    }
    
    
    public void obstacleEngine(Graphics2D g2d) {
        obstacleDistanceDelay -= 2;
        
        if (obstacleDistanceDelay <= 0) {
            Obstacle obstacle = new Obstacle(30, (int)(dinossauro.getHeight() * 0.75), getWidth() + 10, getHeight() - (int)(dinossauro.getHeight() * 0.75));
            if (this.enviornmentColor == 0) {
                obstacle.setCharacter(new ImageIcon("src/main/java/com/mycompany/dinosaurgame/chrome-tree.png").getImage());
            } else {
                obstacle.setCharacter(new ImageIcon("src/main/java/com/mycompany/dinosaurgame/chrome-tree-white.png").getImage());
            }
            obstaclesList.add(obstacle);
            obstacleDistanceDelay = 200;
        }
        
        
        obstaclesList.forwardObstacle(possibleVelocity, velocityIndex);
        
        generateLifePoint(g2d);
        verifyCollisionWithDinoFire(g2d);
        verifyDinoPoint();
        
        verifyObstaclesAlreadyPassed();
        changeObstaclePicture(g2d);
    }
    
    public void changeObstaclePicture(Graphics2D g2d) {
        Obstacle obstacleAux = this.obstaclesList.getHeadObstacle();
        int counter = 0;
        
        while(counter < this.obstaclesList.getQuantObstacles()) {
            if (enviornmentColor == 0) {
                obstacleAux.setCharacter(new ImageIcon("src/main/java/com/mycompany/dinosaurgame/chrome-tree.png").getImage());
            } else {
                obstacleAux.setCharacter(new ImageIcon("src/main/java/com/mycompany/dinosaurgame/chrome-tree-white.png").getImage());
            }
            g2d.drawImage(obstacleAux.getCharacter(), obstacleAux.getPosX(), obstacleAux.getPosY(),
            obstacleAux.getWidth(), obstacleAux.getHeight(), this);
            obstacleAux = obstacleAux.getNextObstacle();
            counter++;
        }
    }
    
    public void verifyObstaclesAlreadyPassed() {
        Obstacle obstacleAux = this.obstaclesList.getHeadObstacle();
        int counter = 0;
        
        while(counter < this.obstaclesList.getQuantObstacles()) {
            if(obstacleAux.getPosX() <= 0) {
                obstacleAux.setPosX(getWidth() + 10);
                obstaclesList.add(obstaclesList.remove());
            } 
            obstacleAux = obstacleAux.getNextObstacle();
            counter++;
        }
    }
    
    public void verifyDinoPoint() {
        Obstacle obstacleAux = this.obstaclesList.getHeadObstacle();
        int counter = 0;
        
        while(counter < this.obstaclesList.getQuantObstacles()) {
            if(obstacleAux.getPosX() + obstacleAux.getWidth() < dinossauro.getPosX() && 
               obstacleAux.getDinoCollided() == false && 
               superPowerBarFlagActived == false) {
                
                dinoScore += 1;
                if (superPowerBarLevel < 5) {
                    superPowerBarLevel += 1;
                }

                velocityController += 1;
                if (velocityController > 5) {
                    velocityIndex += 1;
                    velocityController = 0;

                    if (velocityIndex >= possibleVelocity.length) {
                        velocityIndex = 0;
                    }
                }
                break;
            }
            obstacleAux = obstacleAux.getNextObstacle();
            counter++;
        }
        velocityIndex = 0;
    }
    public void generateLifePoint(Graphics g2d) {
        if (dinoLife < 100) {
            if(generateRandomNumber(1, 10) <= 3) {
                lifePoint = new LifePointObject(20, 20);
                lifePoint.setWidth(20);
                lifePoint.setHeight(20);
                lifePoint.setPosY(155);
                lifePoint.setPosX(getWidth() - 95);

                g2d.setColor(Color.GREEN);

                g2d.fillRect(lifePoint.getPosX(), lifePoint.getPosY(), lifePoint.getWidth(), lifePoint.getHeight());
                lifePointFlag = true;
            } else {
                lifePointFlag = false;
            }
        } else {
            lifePointFlag = false;
        }
        
        if (lifePointFlag) {
            g2d.setColor(Color.GREEN);
            lifePoint.setPosX(lifePoint.getPosX() - possibleVelocity[velocityIndex]);
            g2d.fillRect(lifePoint.getPosX(), lifePoint.getPosY(), lifePoint.getWidth(), lifePoint.getHeight());
        }
    }
    
    public void verifyCollisionWithDinoFire(Graphics g2d) {
        if (superPowerBarFlagActived == true) {
            g2d.setColor(Color.RED);
            g2d.fillRoundRect(dinossauro.getPosX() + dinossauro.getWidth() + 5,
                    dinossauro.getPosY() + 10, 150, 25, 10, 10);
            int extremeXPositionOfDinoFire = dinossauro.getPosX() + dinossauro.getWidth() + 
                    5 + 150;
            
            
            Obstacle obstacleAux = obstaclesList.getHeadObstacle();
            int counter = 0;
        
            while(counter < this.obstaclesList.getQuantObstacles()) {
               if (obstacleAux.getPosX() <= extremeXPositionOfDinoFire) {
                    obstacleAux.setPosX(0);
                    dinoScore += 1;
                }
               
                fireDelay -= 3;
            
                if (fireDelay <= 0) {
                    fireDelay = 300;
                    superPowerBarFlagActived = false;
                }
                obstacleAux = obstacleAux.getNextObstacle();
                counter++;
            }
            
        }
    }
    
    public void verifyCollision(ObstacleList obstacleList) {
        Obstacle obstacleAux = obstacleList.getHeadObstacle();
        int yBottomPointDino = dinossauro.getHeight() + dinossauro.getPosY();
        int rightBorderDino = dinossauro.getWidth() + dinossauro.getPosX();
        int leftBorderDino = dinossauro.getPosX();
        int leftBorderObstacle;
        int counter = 0;
        
        while(counter < this.obstaclesList.getQuantObstacles()) {
            leftBorderObstacle = obstacleAux.getPosX();
            if (obstacleAux.getDinoCollided() == false) {
                if (yBottomPointDino >= obstacleAux.getPosY()) {
                    if (leftBorderObstacle >= leftBorderDino && leftBorderObstacle <= rightBorderDino) {
                        System.out.println("Collision");
                        this.dinoLife -= 10;
                        if(this.dinoLife <= 0) {
                            this.running = false;
                        }
                        obstacleAux.setDinoCollided(true);
                        break;
                    }
                }
            }
            obstacleAux = obstacleAux.getNextObstacle();
            counter++;
        }
    }

    public void changeCharacter() {
        if (this.changeDinoCharacter == 0) {
            if (actualDinoCharacter) {
                actualDinoCharacter = false;
                if (enviornmentColor == 0) {    
                    dinossauro.setCharacter(new ImageIcon("src/main/java/com/mycompany/dinosaurgame/Dinosaur2.png").getImage());
                } else {
                    dinossauro.setCharacter(new ImageIcon("src/main/java/com/mycompany/dinosaurgame/Dinosaur2White.png").getImage());
                }
            } else {
                actualDinoCharacter = true;
                if (enviornmentColor == 0) {
                    dinossauro.setCharacter(new ImageIcon("src/main/java/com/mycompany/dinosaurgame/Dinosaur3.png").getImage());    
                } else {
                    dinossauro.setCharacter(new ImageIcon("src/main/java/com/mycompany/dinosaurgame/Dinosaur3White.png").getImage());
                }
            }
            this.changeDinoCharacter = 300;
        }
        this.changeDinoCharacter -= 50;
    }
    
    public void update() {
        changeCharacter();
        if(keyboard.isCima()) {
            if (this.dinoJumping == false) {
                dinossauro.setPosY(dinossauro.getPosY() - dinossauro.getMaxJump());
                dinossauro.setPosX(50);
                this.dinoJumping = true;
                collectedLifePoint();
            }
        }
        
        if (keyboard.isDinoFire()) {
            if (superPowerBarLevel == 5) {
                superPowerBarFlagActived = true;
            }
        }
        if (this.dinoJumping == true){
            if (dinossauro.getPosY() + gravityForce < posLimitY) {
                this.dinoJumping = true;
                dinossauro.setPosY(dinossauro.getPosY() + gravityForce);
                collectedLifePoint();
            } else {
                this.dinoJumping = false;
                dinossauro.setPosX(25);  
            }
        }
    }
    
    public void collectedLifePoint() {
         if (lifePointFlag) {
            int topLimitOfLifePoint = lifePoint.getPosY();
            int bottomLimitOfLifePoint = lifePoint.getPosY() + lifePoint.getHeight();
            int rightLimitOfLifePoint = lifePoint.getPosX() + lifePoint.getWidth();
            int leftLimitOfLifePoint = lifePoint.getPosX();

            int dinoTopLimit = dinossauro.getPosY();
            int dinoRightLimit = dinossauro.getPosX() + dinossauro.getWidth();
            int dinoLeftLimit = dinossauro.getPosX();

            if (dinoTopLimit >= topLimitOfLifePoint && dinoTopLimit <= bottomLimitOfLifePoint
                    && (dinoRightLimit >= leftLimitOfLifePoint && dinoRightLimit <= rightLimitOfLifePoint)
                    || (dinoLeftLimit >= leftLimitOfLifePoint && dinoLeftLimit <= rightLimitOfLifePoint)) {
                if (dinoLife < 100) {

                    dinoLife += 20;
                    lifePointFlag = false;
                }
            }
        }
    }
    public void lifeStatus(Graphics2D g2d) {
        g2d.setColor(Color.gray);
        g2d.fillRoundRect(10, 40, lifeBarWidth, 25, 10, 10);
        g2d.setColor(this.dinoLife <= 30 ? new Color(255, 0, 0) : this.dinoLife <= 60 ? new Color(255, 178, 12) : new Color(50, 255, 84));
        
        g2d.fillRoundRect(10, 40, (int)((lifeBarWidth * dinoLife) / 100), 25, 10, 10);
    }
    
    public void scoreBoard(Graphics2D g2d) {
        if (this.enviornmentColor == 0) {
            g2d.setColor(new Color(22, 22, 22));
        } else {
            g2d.setColor(new Color(255, 255, 255));
        }
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString("Score: " + this.dinoScore, 10, 20);
    }
    
    public void gameOverScreen(Graphics2D g2d) {
        g2d.setBackground(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.setColor(Color.RED);
        g2d.setFont(new Font("Arial", Font.BOLD, 50));
        FontMetrics metrics = getFontMetrics(g2d.getFont());
        g2d.drawString("GAME OVER",
                (getWidth() - metrics.stringWidth("GAME OVER" + this.dinoScore))/2,
                (getHeight())/2);
    
        
        g2d.setFont(new Font("Arial", Font.BOLD, 25));
        g2d.drawString("Score: " + this.dinoScore,
                (getWidth() - metrics.stringWidth("Score: " + this.dinoScore))/2,
                ((getHeight())/2) + 45);
        
        JButton restart = new JButton("Reiniciar");
        restart.setLocation(100, 100);
        this.add(restart);
    }
    
    public int generateRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }
    
    @Override
    public void actionPerformed(ActionEvent arg0) {
        
    }

    @Override
    public void run() {
 
        try {
            while(running){
                repaint();
                update();
                verifyCollision(obstaclesList);
                Thread.sleep(1000/24);
                repaint();
            }
            thread.join();
        } catch (Exception e) {
        }
        
        
    }
    
}
