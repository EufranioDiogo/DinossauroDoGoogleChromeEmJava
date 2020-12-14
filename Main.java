/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dinosaurgame;
import javax.swing.JFrame;
/**
 *
 * @author ed
 */
public class Main extends JFrame {
    
    public Main() {
        setTitle("Dino Game");
        add(new Painel());
        addKeyListener(Painel.keyboard);
        setFocusable(true);
        setSize(750, 400);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args) {
        new Main();
    }
    
}
