//code
//work item list
//charter document
//reflection (what you did and what you learneed)


package connect4;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author agaranj18
 */
public class GamePanel extends javax.swing.JPanel {

    private int width;
    private int height;
    private Circle[][]circleArray= new Circle[6][7];
    private int[] xLinePositions= new int[circleArray[0].length+1];
    private Circle tempCircle= new Circle (10000, 10000, 0);
    private boolean onBlack= false;
    private int radius= 50;
    private GameJFrame jf;
    private JLabel blackLabel= new JLabel("Player 1 Turn (Black)");
    private JLabel redLabel = new JLabel("Player 2 Turn (Red)");
    /**
     * Creates new form GamePanel
     */
    public GamePanel(int width, int height, GameJFrame jf) {
        this.width=width;
        this.height=height;
        this.jf= jf;
        createArray();
        initComponents();
        
        createPlayerLabel();
                
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                for (int i=0; i<xLinePositions.length-1; i++) {
                    int xPos= e.getX();
                    if (xPos>=xLinePositions[i] && xPos<xLinePositions[i+1]) {
                        placeToken(i);
                    }
                }
                
            }
        });
    }
    public void placeToken(int column) {
        boolean empty= true;
        int row= circleArray.length-1;
        while(empty && row>=0) {
            if (!circleArray[row][column].getStatus()) {
                empty=false;
                Circle c= circleArray[row][column];
                c.setStatus(true);
                boolean temp= onBlack;
                c.setColor(temp);
                setPlayerLabel(onBlack);
                this.tempCircle= c;
                repaint();
                checkVerticalWin(row, column);
                checkHorizontalWin(row);
                checkDiagonalWin1(row, column);
                checkDiagonalWin2(row, column);
            }
            row--;
        }
    }
    public void createPlayerLabel() {
        int screenHeight= java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
        int screenWidth= java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
        Font ft= new Font("Lucida Bright", Font.PLAIN, 50); 
        blackLabel.setFont(ft);
        blackLabel.setHorizontalAlignment(JLabel.CENTER);
        blackLabel.setBounds(0, screenHeight-screenHeight/4, screenWidth, 150);
        this.add(blackLabel);
        
        redLabel.setFont(ft);
        redLabel.setHorizontalAlignment(JLabel.CENTER);
        redLabel.setBounds(0, screenHeight-screenHeight/4, screenWidth, 150);
    }
    
    public void setPlayerLabel(boolean isBlack) {
        if (isBlack) {
            redLabel.setForeground(Color.BLACK);
            this.add(redLabel);
            
            Color yellow= new Color(255, 255, 153);
            blackLabel.setForeground(yellow);
            this.add(blackLabel);
        }
        else {
            blackLabel.setForeground(Color.BLACK);
            this.add(blackLabel);
            
            Color yellow= new Color(255, 255, 153);
            redLabel.setForeground(yellow);
            this.add(redLabel);
        }
    }
    
    public boolean checkVerticalWin(int row, int column){
       boolean isBlack = tempCircle.getColor();
       boolean inRow = true;
       if(row <3){
           for(int i=row+1; i<row+4; i++){
               if(!circleArray[i][column].getColor()==isBlack){
                    inRow=false; 
                } 
            }    
        }
       else{
           inRow=false;
       }
        if(inRow){
            alertWinner(isBlack);
        }
       return inRow;
    }
    
    public boolean checkHorizontalWin(int row){
        boolean isBlack = tempCircle.getColor();
        int count=0;
        for(int i=0; i<circleArray[0].length; i++){
            if(circleArray[row][i].getColor()==isBlack && circleArray[row][i].getStatus()==true){
                count++;
            }
            else{
                count=0;
            }
            
            if(count>=4){
                alertWinner(isBlack);
                return true; 
            }
        }
        return (false);
    }
    public boolean checkDiagonalWin1(int row, int column) {
        Circle orig= circleArray[row][column];
        boolean isBlack= orig.getColor();
        //from top left corner to bottom right corner
        int tempRow= row;
        int tempColumn= column;
        while (tempRow>0 && tempColumn>0) {
            tempRow--;
            tempColumn--;
        }
        int count =0;
        while (tempRow<circleArray.length && tempColumn<circleArray[0].length) {
            Circle c= circleArray[tempRow][tempColumn];
            if (c.getColor()==isBlack && c.getStatus()==true) {
                count++;
            }
            else {
                count=0;
            }
            if (count>=4) {
                alertWinner(isBlack);
                return (true);
            }
            tempRow++;
            tempColumn++;
        }
        
        return (false);
    }
    
    //work on this method!!!!
    public boolean checkDiagonalWin2(int row, int column){
        Circle orig= circleArray[row][column];
        boolean isBlack= orig.getColor();
        //from top right corner to bottom left corner
        int tempRow= row;
        int tempColumn= column;
        while (tempRow<circleArray.length-1 && tempColumn>0) {
            tempRow++;
            tempColumn--;
        }
        int count =0; 
        while(tempRow>=0 && tempColumn<circleArray[0].length){
            Circle c= circleArray[tempRow][tempColumn];
            if (c.getColor()==isBlack && c.getStatus()==true) {
                count++;
            }
            else {
                count=0;
            }
            if (count>=4) {
                alertWinner(isBlack);
                return (true);
            }
            tempRow--;
            tempColumn++;
        }
        return false;
    }
    public void alertWinner(boolean isBlack) {
        Color yellow= new Color(255, 255, 153);
        blackLabel.setForeground(yellow);
        this.add(blackLabel);
        redLabel.setForeground(yellow);
        this.add(blackLabel);
        
        
        NotifyDelete done= new NotifyDelete(jf, true);
        
        int doneWidth= 600;
        int doneHeight= 400;
        done.setLocation((int)(0.5*(this.width-doneWidth)), (int)(0.5*(this.height-doneHeight)));
        
        if (isBlack) {
            done.setWinnerLabel(isBlack);
        }
        else {
            done.setWinnerLabel(isBlack);
        }
        done.setVisible(true);
        
        clearAll();

        blackLabel.setForeground(Color.black);
        this.add(blackLabel);
        
        redLabel.setForeground(yellow);
        this.add(redLabel);
        
        onBlack= false;
    }
    public void createLabel() {
        int screenHeight= java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
        int screenWidth= java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
        JLabel title= new JLabel("Connect 4 Game");
        Font ft= new Font("Lucida Handwriting", Font.PLAIN, 100); 
        title.setFont(ft);
        
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setBounds(0, screenHeight/15-50, screenWidth, 150);
        
        this.add(title);
    }
    public void createArray() {
        int spaceBetweenX= 30; //should be even
        int spaceBetweenY= 15;

        int widthOfBoard= circleArray[0].length*(2*radius+spaceBetweenX)-spaceBetweenX;
        int heightOfBoard= circleArray.length*(2*radius+spaceBetweenY)- spaceBetweenY;
        int startingX=(int)((this.width-widthOfBoard)/2);
        int x= startingX;
        int startingY= (int)(this.height-heightOfBoard)/2;
        int y= startingY;

        for (int i=0; i<circleArray.length; i++) {
            for (int j=0; j<circleArray[i].length; j++) {
                circleArray[i][j]= new Circle(x, y, radius);
                Circle temp= circleArray[i][j];
                x+= 2*radius+spaceBetweenX;
            }
            y+=2*radius+spaceBetweenY;
            x=startingX;
        } 
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        //repainting the board
        this.createLabel();
        int spaceBetweenX= 30; //should be even
        int spaceBetweenY= 15;

        int widthOfBoard= circleArray[0].length*(2*radius+spaceBetweenX)-spaceBetweenX;
        int heightOfBoard= circleArray.length*(2*radius+spaceBetweenY)- spaceBetweenY;
        int startingX=(int)((this.width-widthOfBoard)/2);
        int x= startingX;
        int startingY= (int)(this.height-heightOfBoard)/2;
        int y= startingY;

        for (int i=0; i<circleArray.length; i++) {
            for (int j=0; j<circleArray[i].length; j++) {
                Circle temp= circleArray[i][j];
                if (temp.getStatus()==true) {
                    if (temp.getColor()==true) {
                        g.setColor(Color.black);
                    }
                    else {
                        g.setColor(Color.red);
                    }
                }
                else {
                    g.setColor(Color.white);
                }
                g.fillOval(temp.getXCord(), temp.getYCord(), 2*temp.getRadius(), 2*temp.getRadius());
                x+= 2*radius+spaceBetweenX;
                
            }
            y+=2*radius+spaceBetweenY;
            x=startingX;
        } 
        g.setColor(Color.black);
        int borderY= 10;
        int xPos= 0;
        for (int i=0; i<=circleArray[0].length; i++) {
            int spaceBetweenLines=2*spaceBetweenX+2*radius;
            xPos=startingX-((int)(0.5*spaceBetweenX))+(spaceBetweenX+2*radius)*(i);
            g.drawLine(xPos, startingY-borderY, xPos, startingY+heightOfBoard+borderY);
            xLinePositions[i]=xPos;
        }        
        g.drawLine(startingX-((int)(0.5*spaceBetweenX)), startingY-borderY, xPos, startingY-borderY);
        g.drawLine(startingX-((int)(0.5*spaceBetweenX)), startingY+borderY+heightOfBoard, xPos, startingY+borderY+heightOfBoard);
        
        onBlack= !onBlack;
    } 
    public void clearAll() {
        for (int i=0; i<circleArray.length; i++) {
            for (int j=0; j<circleArray[0].length; j++) {
                Circle c= circleArray[i][j];
                c.setColor(onBlack);
                c.setStatus(false);
            }
        }
        repaint();
    }
    
    
     
    @Override
    public synchronized void addMouseListener(MouseListener l) {
        super.addMouseListener(l); //To change body of generated methods, choose Tools | Templates.
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 153));
        setBorder(new javax.swing.border.MatteBorder(null));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel1)
                .addContainerGap(418, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1)
                .addContainerGap(285, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
    }//GEN-LAST:event_formComponentResized

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
    }//GEN-LAST:event_formComponentShown


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables

}
