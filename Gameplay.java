package brickBraker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;
     
    private int totalBricks = 21;
    
    private Timer timer;
    private int delay = 8;
    
    private int playerX = 300;
    
    private int ballposX =120;
    private int ballposY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;
    
    private MapGenerator map;
    
    
    public Gameplay()
    {
    	map = new MapGenerator(5,8);
    	addKeyListener(this);
    	setFocusable(true);
    	setFocusTraversalKeysEnabled(false);
    	timer = new Timer(delay, this);
      	timer.start();
    	
    }
    
    public void paint(Graphics g)
    {
    	// background
    	g.setColor(Color.black);
    	g.fillRect(1, 1, 692, 592);
    
    	//drawing map
    	map.draw((Graphics2D)g);
    	// border 
    	g.setColor(Color.yellow);
    	g.fillRect(0, 0, 3, 592);
    	g.fillRect(0, 0, 692, 3);
    	g.fillRect(680, 0, 3, 592);
    	
    	//scores
    	g.setColor(Color.white);
    	g.setFont(new Font("serif", Font.BOLD,25));
    	g.drawString(""+score+"/200", 590,30);
    	
    	//paddle
    	g.setColor(Color.green);
    	g.fillRect(playerX, 550, 100, 8);
    	
    	//the ball
    	g.setColor(Color.yellow);
    	g.fillOval(ballposX, ballposY, 20, 20);
    	 
    	if(play == false && totalBricks == 21) {
    		//game start message
        	g.setColor(Color.white);
        	g.setFont(new Font("serif",Font.PLAIN,20));
        	g.drawString("PRESS LEFT OR RIGHT TO START",190,350);
        	
        	//game name
        	g.setColor(Color.white);
        	g.setFont(new Font("serif",Font.PLAIN,40));
        	g.drawString("BREAKOUT BALL",180,240);
        	
        	//hide ball
            g.setColor(Color.black);
            g.fillRect(110,340,40,40);
            
            //hide bricks
            g.setColor(Color.black);
            g.fillRect(80,40,540,150);
    	}
    	    	
    	if(totalBricks ==0) {
    		play = false;
    		ballXdir = 0;
    		ballYdir = 0;
    		g.setColor(Color.green);
    		g.setFont(new Font("serif", Font.BOLD,30));
        	g.drawString("You Won !!! Score:"+score, 190,300);
        	
        	g.setFont(new Font("serif", Font.BOLD,20));
        	g.drawString("Press Enter to Restart", 230,350);
    		
    	}
    	
    	if(ballposY >570) {
    		play = false;
    		ballXdir = 0;
    		ballYdir = 0;
    		g.setColor(Color.red);
    		g.setFont(new Font("serif", Font.BOLD,30));
        	g.drawString("Game Over :D Score:"+score, 190,300);
        	
        	g.setFont(new Font("serif", Font.BOLD,20));
        	g.drawString("Press Enter to Restart", 230,350);
    	}
    	
    	g.dispose();
    	
    	
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if(play) {
			if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,550,100,5))) {
				ballYdir = -ballYdir;
			}
		  A:for(int i =0;i<map.map.length;i++)
			{
				for(int j =0;j<map.map[0].length;j++) {
					if(map.map[i][j]>0) {
						int brickX = j*map.brickWidth + 80;
						int brickY = i*map.brickHeight +50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX,brickY,brickWidth,brickHeight);
						Rectangle ballRect = new Rectangle(ballposX,ballposY,20,20);
						Rectangle brickRect =  rect;
						
						if(ballRect.intersects(brickRect)) {
							map.setBrickValue(0,i,j);
							totalBricks--;
							score +=5;
							
							if(ballposX +19<=brickRect.x || ballposX + 1>=brickRect.x + brickRect.width){
								ballXdir = -ballXdir;
								}else {
									ballYdir = -ballYdir;
								}
						        break A;	
						}
						
					}
				}
			}

			ballposX +=ballXdir;
			ballposY += ballYdir;
		 if(ballposX <0) {
			 ballXdir = -ballXdir;
		 }
		 if(ballposY <0) {
			 ballYdir = -ballYdir;
		 }
		 if(ballposX >670) {
			 ballXdir = -ballXdir;
		 }
		}
		
		repaint();
		
	}

	@Override
	public void keyTyped(KeyEvent e) {}
	
	@Override
	public void keyReleased(KeyEvent e) {}

    @Override
	public void keyPressed(KeyEvent e) {
    	if(e.getKeyCode()== KeyEvent.VK_RIGHT) {
    		if(playerX >570) {
    			playerX = 580;
    		}
    		else
    		{
    			moveRight();
    	    }
    	}
    	if(e.getKeyCode()== KeyEvent.VK_LEFT) {
    		if(playerX<5) {
    			playerX = 0;
    			}else {
    				moveLeft();
    			}
    		}
    	if(e.getKeyCode()==KeyEvent.VK_ENTER) {
    		if(!play) {
    			ballposX = 120;
    			ballposY = 350;
    			ballXdir = -1;
    			ballYdir = -2;
    			playerX = 310;score =0;
    			totalBricks = 40;
    			map = new MapGenerator(5,8);
    			
    			repaint();
    		}
    	}
    }
public void moveRight()
{
	play = true;
	playerX+=20;
}
public void moveLeft()
{
	play = true;
	playerX-=20;
}
	
}

