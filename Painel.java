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
    private final GeneralPath pen = new GeneralPath();
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
    
    
    private Obstacle obstacle = new Obstacle(-1, 0);
    private boolean collisionFlag = false;
    
    static InputKeyBoard keyboard = new InputKeyBoard();
    private boolean flag = true;
    private final Thread thread;
    
    private final JButton pauseButton = new JButton("Pause");
    private final JButton playButton = new JButton("Play");
    private boolean flagGamePaused = false;
    
    public Painel() {
        running = true;
        thread = new Thread(this);
        this.setFocusable(true);
        pauseButton.setBounds(130, 200, 100, 40);
        playButton.setBounds(330, 200, 100, 40);
        add(playButton);
        add(pauseButton);
        pauseButton.addActionListener(this);
        playButton.addActionListener(this);
        addKeyListener(keyboard);
        thread.start();
        dinossauro.setCharacter(new ImageIcon("src/main/java/com/mycompany/dinosaurgame/Dinosaur2.png").getImage());
        
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        if (this.flag) {
            dinossauro.setPosY((int)(getHeight() - dinossauro.getHeight() - 5));
            this.flag = false;
            posLimitY = dinossauro.getPosY();
        }
        
        if (this.running == false) {
            gameOverScreen(g2d);
        } else {
            this.scoreBoard(g2d);
            this.lifeStatus(g2d);
            g2d.drawImage(dinossauro.getCharacter(), dinossauro.getPosX(), dinossauro.getPosY(),
                dinossauro.getWidth(), dinossauro.getHeight(), this);
            generateObstacle(g2d);
        }
    }
    
    public void generateObstacle(Graphics2D g2d) {
        if(obstacle.getPosX() <= 0) {
            if (collisionFlag == false) {
                //this.dinoScore += 1; Antiga maneira de gerar pontos
            } else {
                this.collisionFlag = false;
            }
            obstacle = new Obstacle(30, (int)(dinossauro.getHeight() * 0.75));
            obstacle.setPosY(getHeight() - obstacle.getHeight());
            obstacle.setPosX(getWidth() + 10);
        } else {
            obstacle.setPosX(obstacle.getPosX() - obstacle.getObstacleXVelocity());
        }
        
        if (this.dinoScore < 100) {
            obstacle.setObstacleXVelocity(5);
        }
        else if (this.dinoScore < 200) {
            obstacle.setObstacleXVelocity(10);
        }
        else if (this.dinoScore < 300) {
            obstacle.setObstacleXVelocity(15);
        }
        else if (this.dinoScore < 400) {
            obstacle.setObstacleXVelocity(20);
        }
        else if (this.dinoScore < 500) {
            obstacle.setObstacleXVelocity(25);
        }
        else if (this.dinoScore < 600){
            obstacle.setObstacleXVelocity(30);
        }
        else if (this.dinoScore < 700){
            obstacle.setObstacleXVelocity(35);
        }
        else if (this.dinoScore < 800){
            obstacle.setObstacleXVelocity(40);
        }
        
        if(generateRandomNumber(0, 5) < 3) {
            obstacle.setCharacter(new ImageIcon("src/main/java/com/mycompany/dinosaurgame/chrome-tree.png").getImage());
            g2d.drawImage(obstacle.getCharacter(), obstacle.getPosX(), obstacle.getPosY(),
                obstacle.getWidth(), obstacle.getHeight(), this);
        } else {
            g2d.setColor(new Color(generateRandomNumber(0, 255), generateRandomNumber(0, 255), generateRandomNumber(0, 255)));
            g2d.fillOval(obstacle.getPosX(), obstacle.getPosY(),
                obstacle.getWidth(), obstacle.getHeight());
        }
        
        
    }
    
    public void collision() {
        int yBottomPointDino = dinossauro.getHeight() + dinossauro.getPosY();
        int rightBorderDino = dinossauro.getWidth() + dinossauro.getPosX();
        int leftBorderDino = dinossauro.getPosX();
        
        int leftBorderObstacle = obstacle.getPosX();
        
        if (collisionFlag == false) {
            if (yBottomPointDino >= obstacle.getPosY()) {
                if (leftBorderObstacle >= leftBorderDino && leftBorderObstacle <= rightBorderDino) {
                    System.out.println("Collision");
                    this.dinoLife -= 20;
                    if(this.dinoLife <= 0) {
                        this.running = false;
                    }
                    collisionFlag = true;
                }
            }
        }
    }

    public void changeCharacter() {
        if (this.changeDinoCharacter == 0) {
            if (actualDinoCharacter) {
                actualDinoCharacter = false;
                dinossauro.setCharacter(new ImageIcon("src/main/java/com/mycompany/dinosaurgame/Dinosaur2.png").getImage());
            } else {
                actualDinoCharacter = true;
                dinossauro.setCharacter(new ImageIcon("src/main/java/com/mycompany/dinosaurgame/Dinosaur3.png").getImage());
            }
            this.changeDinoCharacter = 300;
        }
        this.changeDinoCharacter -= 20;
        this.scoreEngine();
    }
    
    int pointFlag = 70;
    
    public void scoreEngine() {
        if (pointFlag == 0) {
            this.dinoScore += 1;
            pointFlag = 70;
        } else {
            pointFlag -= 10;
        }
    }
    
    public void update() {
        changeCharacter();
        if(keyboard.isCima()) {
            if (this.dinoJumping == false) {
                dinossauro.setPosY(dinossauro.getPosY() - dinossauro.getMaxJump());
                dinossauro.setPosX(50);
                this.dinoJumping = true;
            }
        } 
        if (this.dinoJumping == true){
            if (dinossauro.getPosY() + 9 < posLimitY) {
                this.dinoJumping = true;
                dinossauro.setPosY(dinossauro.getPosY() + gravityForce);
                
            } else {
                this.dinoJumping = false;
                dinossauro.setPosX(25);  
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
        String cmd = arg0.getActionCommand();
        
        if(flagGamePaused && "Play".equals(cmd)) {
            this.setFocusable(true);
            this.flagGamePaused = false;
            setFocusable(true);
        } else if ("Pause".equals(cmd)) {
            this.flagGamePaused = true;
        } else if ("Reiniciar".equals(cmd)) {
            this.running = true;
            this.flagGamePaused = false;
            this.dinoScore = 0;
            this.dinoLife = 100;
        }
    }

    @Override
    public void run() {
 
        try {
            while(running){
                while(this.flagGamePaused) {
                    Thread.sleep(100);
                }
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
