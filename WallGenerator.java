package brickBreaker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class WallGenerator {
    private int wall[][];
    private int brickWidth;
    private int brickHeight;
    public WallGenerator(int row, int col) {
        setWall(new int[row][col]);
        for (int i = 0; i < getWall().length; i++) {
            for (int j = 0; j < getWall()[0].length; j++) {
                getWall()[i][j] = 1;
            }
        }
        setBrickWidth(540 / col);
        setBrickHeight(150 / row);
    }
    public void draw(Graphics2D g) {
        for (int i = 0; i < getWall().length; i++) {
            for (int j = 0; j < getWall()[0].length; j++) {
                if (getWall()[i][j] > 0) {
                    g.setColor(Color.white);
                    g.fillRect(j * getBrickWidth() + 80, i * getBrickHeight() + 40, getBrickWidth(), getBrickHeight());

                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.black);
                    g.drawRect(j * getBrickWidth() + 80, i * getBrickHeight() + 40, getBrickWidth(), getBrickHeight());
                }
            }
        }
    }
    public void setBrickValue(int value, int row, int col) {
        getWall()[row][col] = value;
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