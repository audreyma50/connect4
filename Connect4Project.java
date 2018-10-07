/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connect4;

import java.awt.Dimension;


public class Connect4Project {

    public static void main(String[] args) {
        int screenHeight= java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
        int screenWidth= java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
        System.out.println();
        GameJFrame frame= new GameJFrame();
        int panelWidth= screenWidth-15;
        int panelHeight= screenHeight-30;
        frame.setSize(screenWidth, screenHeight);
        frame.setVisible(true);
        
        GamePanel p1= new GamePanel(panelWidth, panelHeight, frame);
        p1.setLocation(0, 0);
        p1.setSize(panelWidth, panelHeight);
        frame.add(p1);
        p1.setVisible(true);
        //work on developing a method to check if there's a four in a row =)
    }
    
}
