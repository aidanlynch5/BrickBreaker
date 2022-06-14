import java.util.*;
import java.awt.event.*;

import javax.swing.*;

import java.awt.*;

import javax.swing.*;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
	private boolean play = false;
	private int score = 0;
        private int counter = 1;
	
//	private int totalBricks = 48;
	private Timer timer;	

	private int delay = 8;
	
	private int playerX = 310;
	
        //ball starting location
	private int ballposX = 620; //120
	private int ballposY = 350; //350
        
        //ball speed
	private int ballXdir = -2;
	private int ballYdir = -4;
        
        //amount of lives you have (Starts with 3)
        private int lives = 3;
        
        //the ability to decrement lives
        private boolean decrementLives = true;
        
        ///current level
        private int level = 1;
        
        private int totalBricks = 12 * level;
        
        
        private int mapX = totalBricks / 6;
        private int mapY = totalBricks / 2;
        
        private boolean continuePlay = false;
        
	
	private MapGenerator map;
	
	public Gameplay() {
            map = new MapGenerator(mapX, mapY);
            addKeyListener(this);
            setFocusable(true);
            setFocusTraversalKeysEnabled(false);
            timer = new Timer(delay,this);
            timer.start();
	}
	
	public void paint(Graphics g) {    		
            // background
            g.setColor(Color.black);
            g.fillRect(1, 1, 692, 592);

            // drawing map
            map.draw((Graphics2D) g);

            // borders
            g.setColor(Color.red); //default yellow
            g.fillRect(0, 0, 3, 592);
            g.fillRect(0, 0, 692, 3);
            g.fillRect(691, 0, 3, 592);

            //display the score		
            g.setColor(Color.orange); //default white
            g.setFont(new Font("serif",Font.BOLD, 25));
            g.drawString("Score: " + score, 560, 30);
            
            //display the lives
            g.setColor(Color.orange);
            g.setFont(new Font("serif",Font.BOLD, 25));
            g.drawString("Lives: " + lives, 15, 30);
            
            //display the level
            g.setColor(Color.orange);
            g.setFont(new Font("serif",Font.BOLD, 25));
            g.drawString("Level: " + level + play, 300, 30); ////////////////////////////////////////////////////////

            // the paddle
            g.setColor(Color.yellow); //default green
            g.fillRect(playerX, 550, 100, 8);

            // the ball
            g.setColor(Color.green); //default yellow
            g.fillOval(ballposX, ballposY, 20, 20);

            // when you won the game
            if(totalBricks <= 0) {
                play = false;
                System.out.println(play + "when you won the game");
                ballXdir = 0;
                ballYdir = 0;
                System.out.println(play);
   
                g.setColor(Color.RED);
                g.setFont(new Font("serif",Font.BOLD, 30));
                g.drawString("You Won", 275, 315);

                g.setColor(Color.RED);
                g.setFont(new Font("serif",Font.BOLD, 20));           
                g.drawString("Press (Enter) to Restart", 230, 350);
                g.drawString("Press (Backspace) to Exit the Game", 190, 375); 
            }
		
            // executed when you go below the paddle
            if(ballposY > 570 && play) {
                
                //lose a life
                lives--;
                
                
                //decrement your score by 10 when you lose a life
                score -= 10;
                
                if(lives <= -1) {
                    decrementLives = false;
                    
                    if(lives < 0) {
                    
                        continuePlay = false; 
                        play = false;
                        System.out.println(play + "when you lose the game");
                        ballXdir = 0;
                        ballYdir = 0;
                        g.setColor(Color.RED);
                        g.setFont(new Font("serif",Font.BOLD, 30));
                        g.drawString("Game Over", 260, 315);

                        g.setColor(Color.RED);
                        g.setFont(new Font("serif",Font.BOLD, 20));           
                        g.drawString("Press (Enter) to Restart", 230, 350);
                        g.drawString("Press (Backspace) to Exit the Game", 190, 375); 
                    }
                }
            }
            g.dispose();
	}

	public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
		if(playerX >= 600) {
                    playerX = 600;
		} else {
                    moveRight();
		}
            }
		
            if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {    
		if(playerX < 10) {
                    playerX = 10;
                } else {
                    moveLeft();
		}
            }
            
            //restart the game
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {          
                if(!play) { //if the game is over
                    lives = 3;
                    decrementLives = true;
                    play = true;
                    System.out.println(play + "when you restart the game");
                    ballposX = 620;
                    ballposY = 350;
                    ballXdir = -2;
                    ballYdir = -4;
                    playerX = 310;
                    score = 0;
                    totalBricks = 12 * level;
                    
                    
                    //reconstruct the map ------ change to if statement - make this different for every level 
//                    totalBricks = 12 * level;
                    map = new MapGenerator(mapX, mapY);
				
                    repaint();
		}
            }
            
            //progress to the next level
            if (e.getKeyCode() == KeyEvent.VK_SPACE && totalBricks == 0) {
                if(!play) {
                    
                    counter = 1;
                    decrementLives = true;
                    play = false;
                    System.out.println(play + "when you go to next level the game");
                    ballposX = 120;
                    ballposY = 350;
                    ballXdir *= 2; //increase speed of the ball
                    ballYdir *= 2;
                    playerX = 310;
                    
                    level++;
                    totalBricks = 12 * level;
                    ////////////make new level
                    mapX = totalBricks / 6;
                    mapY = totalBricks / 4;
                    
                    
                    map = new MapGenerator(mapX, mapY);
                    
                    repaint();
                }
            }
                
            if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                if(!play) { //if the game is over
                    System.exit(0);
                }
            }
	}

	public void keyReleased(KeyEvent e) {
        
        }
        
	public void keyTyped(KeyEvent e) {
        
        }
	
        //paddle move right method
	public void moveRight() {
            playerX+=40; //default += 20
            if(decrementLives) {
                play = true;
                if(counter == 1){
                    ballXdir = -2; ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    ballYdir = -4;
                    counter++;
                }
                continuePlay = true;
                System.out.println(play + "Moving the paddle right");
            }
            
	}
        
	//paddle move left method
	public void moveLeft() {
            playerX-=40; //default -= 20
            if(decrementLives) {
                play = true;
                if(counter == 1){
                    
                    ballXdir = -2; ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    ballYdir = -4;
                    counter++;
                }
                continuePlay = true;
                System.out.println(play + "Moving the paddle left");
            }
	}
	
	public void actionPerformed(ActionEvent e) {
            timer.start();
            
//            ballXdir = -2; //////////////////////////////////////////////////////////////////////////
//            ballYdir = -4;
               
            if(play) {

                 System.out.println(play + "Moving the ball play");
                 //next three if are working (for when the ball hits the paddle) 
		if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 30, 8))) {
                    ballYdir = -ballYdir;
//                    ballXdir = -2;
                    
		} else if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX + 70, 550, 30, 8))) {
                   
                    ballYdir = -ballYdir;
//                    ballXdir = ballXdir + 1;
                    
		} else if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX + 30, 550, 40, 8))) {
                    
                    ballYdir = -ballYdir;
		}
			
		// check map collision with the ball		
		A: for(int i = 0; i < map.map.length; i++) {
                        
                       for(int j = 0; j<map.map[0].length; j++) {
                           
                            if(map.map[i][j] > 0) {
                                
                                //score++;
                                int brickX = j * map.brickWidth + 80;
                                int brickY = i * map.brickHeight + 50;
                                int brickWidth = map.brickWidth;
                                int brickHeight = map.brickHeight;

                                Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);					
                                Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
                                Rectangle brickRect = rect;

                                if(ballRect.intersects(brickRect)) {					
                                    map.setBrickValue(0, i, j);
                                    score+=5;	
                                    totalBricks--;

                                    // when ball hit right or left of brick

                                    if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
                                        ballXdir = -ballXdir;
                                        //not entering here
                                        
                                    } else { // when ball hits top or bottom of brick
                                        ballYdir = -ballYdir;		
                                        System.out.println("-----------------------------------------------------------");
                                    }

                                    break A;
                                }
                            }
                        }
                    }
			
		ballposX += ballXdir;
		ballposY += ballYdir;
			
		if(ballposX < 0) {
                    ballXdir = -ballXdir;
                    System.out.println("Hitting border");
		}
                
		if(ballposY < 0) {
                    ballYdir = -ballYdir;
                   System.out.println("Hitting border");
		} 
                
                if(ballposX > 670) {
                    ballXdir = -ballXdir;
                  System.out.println("Hitting border");
                }
                
                //bounce the ball off the bottom while lives are greater than 0
                if(lives > 0 && ballposY >= 571) {
                    ballYdir = -ballYdir;
                }
                
                
                
		repaint();		
		}
	}
}
