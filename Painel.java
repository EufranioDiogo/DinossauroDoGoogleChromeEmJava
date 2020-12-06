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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private final int gravityForce = 9;
    
    private final Dinossauro dinossauro = new Dinossauro(50, 75, 0, 25);
    private boolean actualDinoCharacter = false;
    private int changeDinoCharacter = 300;
    private boolean dinoJumping = false;
    private int dinoLife = 100;
    private int posLimitY = 0;
    private final int lifeBarWidth = 250;
    private int dinoScore = 0;
    
    
    private int enviornmentColorController = 0;
    private byte enviornmentColor = 0;
    private final int[] possibleVelocity = {5, 10, 15, 20, 25, 30, 35, 40};
    private int velocityController = 0;
    private int velocityIndex = 0;
    
    private LifePointObject lifePoint;
    private boolean lifePointFlag;
    
    
    private Obstacle obstacle = new Obstacle(-1, 0);
    private boolean objectPassedObstacle = false;
    
    static InputKeyBoard keyboard = new InputKeyBoard();
    private boolean flag = true;
    private final Thread thread;
    
    
    // Scenario Variables
    private final int sunWidth = 75;
    private final int sunHeight = 75;
    
    
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
        
         if (enviornmentColor == 0) {
            g2d.setBackground(Color.WHITE);
        } else {
            g2d.setBackground(new Color(33, 33, 33));
        }
        mainMethod(g2d);
    }
    
    public void mainMethod(Graphics2D g2d) {
        if (this.flag) {
            dinossauro.setPosY((int)(getHeight() - dinossauro.getHeight() - 5));
            this.flag = false;
            posLimitY = dinossauro.getPosY();
        }
        
        if (this.running == false) {
            gameOverScreen(g2d);
        } else {
            this.paintScenario(g2d);
            this.paintEnviornment(g2d);
        }
    }
    
    public void paintEnviornment(Graphics2D g2d) {
        this.scoreBoard(g2d);
        this.lifeStatus(g2d);
        g2d.drawImage(dinossauro.getCharacter(), dinossauro.getPosX(), dinossauro.getPosY(),
            dinossauro.getWidth(), dinossauro.getHeight(), this);
        generateObstacle(g2d);
    }
    
    public void paintScenario(Graphics2D g2d) {
        g2d.setColor(Color.YELLOW);
        g2d.fillOval((getWidth() - (sunWidth * 2)), 20, sunWidth, sunHeight);
    }

    public void generateObstacle(Graphics2D g2d) {
        if(obstacle.getPosX() <= 0) {
            if(objectPassedObstacle == false) {
                dinoScore += 1;
                enviornmentColorController += 1;
                velocityController += 1;
                if (enviornmentColorController < 10) {
                    enviornmentColor = 0;
                } else if (enviornmentColorController < 20) {
                    enviornmentColor = 1;
                } else {
                    enviornmentColorController = 0;
                }
                
                if (velocityController > 5) {
                    velocityIndex += 1;
                    velocityController = 0;
                    
                    if (velocityIndex >= possibleVelocity.length) {
                        velocityIndex = 0;
                    }
                }
            } else {
                this.objectPassedObstacle = false;
            }
            
            if (dinoLife < 100) {
                if(generateRandomNumber(1, 6) <= 3) {
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
            obstacle = new Obstacle(30, (int)(dinossauro.getHeight() * 0.75));
            obstacle.setCharacter(new ImageIcon("src/main/java/com/mycompany/dinosaurgame/chrome-tree.png").getImage());
            obstacle.setPosY(getHeight() - obstacle.getHeight());
            obstacle.setPosX(getWidth() + 10);
        } else {
            obstacle.setPosX(obstacle.getPosX() - obstacle.getObstacleXVelocity());
        }
        
        if (lifePointFlag) {
            g2d.setColor(Color.GREEN);
            lifePoint.setPosX(lifePoint.getPosX() - obstacle.getObstacleXVelocity());
            g2d.fillRect(lifePoint.getPosX(), lifePoint.getPosY(), lifePoint.getWidth(), lifePoint.getHeight());
        }
        
        g2d.drawImage(obstacle.getCharacter(), obstacle.getPosX(), obstacle.getPosY(),
            obstacle.getWidth(), obstacle.getHeight(), this);
        
        
        obstacle.setObstacleXVelocity(possibleVelocity[velocityIndex]);
        
    }
    
    public void collision() {
        int yBottomPointDino = dinossauro.getHeight() + dinossauro.getPosY();
        int rightBorderDino = dinossauro.getWidth() + dinossauro.getPosX();
        int leftBorderDino = dinossauro.getPosX();
        
        int leftBorderObstacle = obstacle.getPosX();
        
        if (objectPassedObstacle == false) {
            if (yBottomPointDino >= obstacle.getPosY()) {
                if (leftBorderObstacle >= leftBorderDino && leftBorderObstacle <= rightBorderDino) {
                    System.out.println("Collision");
                    this.dinoLife -= 20;
                    if(this.dinoLife <= 0) {
                        this.running = false;
                    }
                    objectPassedObstacle = true;
                }
            }
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
        this.changeDinoCharacter -= 20;
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
        if (this.dinoJumping == true){
            if (dinossauro.getPosY() + 9 < posLimitY) {
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
        g2d.fillRect(10, 40, lifeBarWidth, 25);
        g2d.setColor(this.dinoLife <= 30 ? new Color(255, 0, 0) : this.dinoLife <= 60 ? new Color(255, 178, 12) : new Color(50, 255, 84));
        
        g2d.fillRect(10, 40, (int)((lifeBarWidth * dinoLife) / 100), 25);
    }
    
    public void scoreBoard(Graphics2D g2d) {
        g2d.setColor(new Color(51, 51, 51));
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
                collision();
                Thread.sleep(25);
                repaint();
            }
            thread.join();
        } catch (Exception e) {
        }
        
        
    }
    
}
