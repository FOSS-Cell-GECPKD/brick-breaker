package brickBreaker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class WallGenerator {
	
    private int wall[][];
    private int brickWidth;
    private int brickHeight;
    
    public WallGenerator(int row, int col) {
        wall=new int[row][col];
        for (int i = 0; i < wall.length; i++) {
            for (int j = 0; j < wall[0].length; j++) {
                wall[i][j] = 1;
            }
        }
       brickHeight=540 / col;
       brickHeight=150 / row;
    }
    
    public void draw(Graphics2D g) {
        for (int i = 0; i < wall.length; i++) {
            for (int j = 0; j < wall[0].length; j++) {
                if (wall[i][j] > 0) {
                    g.setColor(Color.white);
                    g.fillRect(j * brickWidth + 80, i * brickHeight + 40, brickWidth, brickHeight);

                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.black);
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 40, brickWidth, brickHeight);
                }
            }
        }
    }
    
    public void setBrickValue(int value, int row, int col) {
        wall[row][col] = value;
    }
    
    public int[][] getWall() {
        return wall;
    }
    
    public void setWall(int wall[][]) {
        this.wall = wall;
    }
    
    public int getBrickWidth() {
        return brickWidth;
    }
    
    public void setBrickWidth(int brickWidth) {
        this.brickWidth = brickWidth;
    }
    
    public int getBrickHeight() {
        return brickHeight;
    }
    
    public void setBrickHeight(int brickHeight) {
        this.brickHeight = brickHeight;
    }
    
}