/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dinosaurgame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author ed
 */

public class InputKeyBoard implements KeyListener 
{
    private boolean cima = false;
    private boolean baixo = false;
    private boolean left = false;
    private boolean right = false;
    

    public boolean isLeft()
    {
        return left;
    }

    public void setLeft(boolean left) 
    {
        this.left = left;
    }

    public boolean isRight() 
    {
        return right;
    }

    public void setRight(boolean right) 
    {
        this.right = right;
    }

    
    public boolean isCima() 
    {
        return cima;
    }

    public void setCima(boolean cima) 
    {
        this.cima = cima;
    }

    public boolean isBaixo() 
    {
        return baixo;
    }

    public void setBaixo(boolean baixo) 
    {
        this.baixo = baixo;
    }
    
    
    @Override
    public void keyTyped(KeyEvent arg0) 
    {
        System.out.println("Key Typed: " + arg0.getKeyCode());
    }

    // Cuidado esse mambo executa uma vez e o mambo baza
    @Override
    public void keyPressed(KeyEvent arg0) 
    {
        int keyCode = arg0.getKeyCode();
        
        
        switch(keyCode) {
            case KeyEvent.VK_UP:
                cima = true;
                break;
            case KeyEvent.VK_DOWN:
                baixo = true;
                break;
            case KeyEvent.VK_LEFT:
                left = true;
                break;
            case KeyEvent.VK_RIGHT:
                right = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) 
    {
        int keyCode = arg0.getKeyCode();
        
        switch(keyCode) {
            case KeyEvent.VK_UP:
                cima = false;
                break;
            case KeyEvent.VK_DOWN:
                baixo = false;
                break;
            case KeyEvent.VK_LEFT:
                left = false;
                break;
            case KeyEvent.VK_RIGHT:
                right = false;
                break;
        }
    }
    
    
}
