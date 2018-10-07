/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connect4;

import java.awt.Color;

/**
 *
 * @author agaranj18
 */
public class Circle {
    private int xCornerCord;
    private int yCornerCord;
    private boolean isBlack;
    private int radius;
    private boolean full;
    
    public Circle(int xCorner, int yCorner, int radius) {
        this.xCornerCord=xCorner;
        this.yCornerCord=yCorner;
        this.radius=radius;
        this.isBlack=true;
        this.full= false;
    }
    public void setColor(boolean isBlack) {
        this.isBlack= isBlack;
    }
    public void setStatus(boolean status) {
        this.full=status;
    }
    public boolean getStatus() {
        return(full);
    }
    public int getXCord() {
        return(xCornerCord);
    }
    public int getYCord() {
        return(yCornerCord);
    }
    public int getRadius() {
        return(radius);
    }
    public boolean getColor() {
        return(isBlack);
    }
    @Override
    public String toString()  {
        return("("+xCornerCord+", "+yCornerCord);
    }
}
