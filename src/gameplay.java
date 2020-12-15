package brickBreaker;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;

    private int totalBricks = 40;

    private Timer timer;
    private int delay = 8;

    private int playerX = 310;

    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;

    private WallGenerator map;

    public Gameplay() {
        map = new WallGenerator(5, 8);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        // background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        // drawing map
        map.draw((Graphics2D) g);

        // borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(680, 0, 3, 592);

        // the scores 		
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("" + score + "/200", 590, 30);

        // the paddle
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);

        // the ball
        g.setColor(Color.yellow);
        g.fillOval(ballposX, ballposY, 20, 20);

        if (play == false && totalBricks == 40) {
            //game start message
            g.setColor(Color.white);
            g.setFont(new Font("serif", Font.PLAIN, 20));
            g.drawString("PRESS LEFT OR RIGHT TO START", 190, 350);

            //game name
            g.setColor(Color.white);
            g.setFont(new Font("serif", Font.PLAIN, 40));
            g.drawString("BREAKOUT BALL", 180, 240);

            //hide ball
            g.setColor(Color.black);
            g.fillRect(110, 340, 40, 40);

            //hide bricks
            g.setColor(Color.black);
            g.fillRect(80, 40, 540, 170);
        }


        // when you won the game
        if (totalBricks <= 0) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("You Won", 260, 300);

            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press (Enter) to Restart", 230, 350);
        }

        // when you lose the game
        if (ballposY > 570) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Game Over, Scores: " + score, 190, 300);

            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press (Enter) to Restart", 230, 350);
        }

        g.dispose();
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 570) {
                playerX = 580;
            } else {
                moveRight();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 5) {
                playerX = 0;
            } else {
                moveLeft();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                play = true;
                ballposX = 120;
                ballposY = 350;
                ballXdir = -1;
                ballYdir = -2;
                playerX = 310;
                score = 0;
                totalBricks = 40;
                map = new WallGenerator(5, 8);

                repaint();
            }
        }
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    public void moveRight() {
        play = true;
        playerX += 20;
    }

    public void moveLeft() {
        play = true;
        playerX -= 20;
    }

    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (play) {
            if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 30, 8))) {
                ballYdir = -ballYdir;
                ballXdir = -2;
            } else if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX + 70, 550, 30, 8))) {
                ballYdir = -ballYdir;
                ballXdir = ballXdir + 1;
            } else if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX + 30, 550, 40, 8))) {
                ballYdir = -ballYdir;
            }

            // check map collision with the ball
            int[][] varWall = map.getWall();
            A:
                for (int i = 0; i < varWall.length; i++) {
                    for (int j = 0; j < varWall[0].length; j++) {
                        if (varWall[i][j] > 0) {
                            //scores++;
                            int brickX = j * map.getBrickWidth() + 80;
                            int brickY = i * map.getBrickHeight() + 50;
                            int brickWidth = map.getBrickWidth();
                            int brickHeight = map.getBrickHeight();

                            Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                            Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
                            Rectangle brickRect = rect;

                            if (ballRect.intersects(brickRect)) {
                                map.setBrickValue(0, i, j);
                                score += 5;
                                totalBricks--;

                                // when ball hit right or left of brick
                                if (ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
                                    ballXdir = -ballXdir;
                                }
                                // when ball hits top or bottom of brick
                                else {
                                    ballYdir = -ballYdir;
                                }

                                break A;
                            }
                        }
                    }
                }

            ballposX += ballXdir;
            ballposY += ballYdir;

            if (ballposX < 0) {
                ballXdir = -ballXdir;
            }
            if (ballposY < 0) {
                ballYdir = -ballYdir;
            }
            if (ballposX > 670) {
                ballXdir = -ballXdir;
            }

            repaint();
        }
    }
}