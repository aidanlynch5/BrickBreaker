import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

  // initializing defaults
  private boolean play = false;
  private int score = 0;
  private int counter = 1;
  private int delay = 8;
  private int playerX = 310;

  // ball starting location and initial speeds
  private int ballposX = 350; 
  private int ballposY = 350;
  private double ballXdir = -2;
  private double ballYdir = -4;

  // amount of lives you have and ability for program to decrement them
  private int lives = 3;
  private boolean decrementLives = true;

  // current level and info for map generator x and y
  private int level = 1;
  private int totalBricks = 10 * level;
  private int mapX = totalBricks / 5; 
  private int mapY = 5; 

  // essential inits
  private Timer timer;
  private MapGenerator map;

  // Gameplay constructor 
  public Gameplay() {
    map = new MapGenerator(mapX, mapY);
    addKeyListener(this);
    setFocusable(true);
    setFocusTraversalKeysEnabled(false);
    timer = new Timer(delay, this);
    timer.start();
  }

  // Paint on window
  public void paint(Graphics g) {

    // background
    g.setColor(Color.black);
    g.fillRect(1, 1, 692, 592);

    // drawing map
    map.draw((Graphics2D) g);

    // borders
    g.setColor(Color.red);
    g.fillRect(0, 0, 3, 592);
    g.fillRect(0, 0, 692, 3);
    g.fillRect(691, 0, 3, 592);

    // display the score
    g.setColor(Color.orange);
    g.setFont(new Font("serif", Font.BOLD, 25));
    g.drawString("Score: " + score, 560, 30);

    // display the lives
    g.setColor(Color.orange);
    g.setFont(new Font("serif", Font.BOLD, 25));
    g.drawString("Lives: " + lives, 15, 30);

    // display the level
    g.setColor(Color.orange);
    g.setFont(new Font("serif", Font.BOLD, 25));
    g.drawString("Level: " + level, 300, 30);

    // display the paddle
    g.setColor(Color.yellow);
    g.fillRect(playerX, 550, 100, 8);

    // display the ball
    g.setColor(Color.green);
    g.fillOval(ballposX, ballposY, 20, 20);

    // executed when you win the game (all bricks are destroyed)
    if (totalBricks <= 0) {

      // stopping the game
      play = false;
      ballXdir = 0;
      ballYdir = 0;

      //displays winning screen
      g.setColor(Color.RED);
      g.setFont(new Font("serif", Font.BOLD, 30));
      g.drawString("You Won", 275, 315);

      g.setFont(new Font("serif", Font.BOLD, 20));
      g.drawString("Press (Backspace) to Exit the Game", 190, 375);
      g.drawString("Press (Enter) to Restart", 230, 350);
      g.drawString("Press (Space) to Go to the Next Level", 185, 400);
    }

    // executed when you go below the paddle
    if (ballposY > 570 && play) {

      // lose a life
      lives--;

      // decrement your score by 10 when you lose a life
      score -= 10;

      // executed when you've lost
      if (lives <= -1) {

        // no longer decrement lives because player has none
        decrementLives = false;

        // executed when you've lost
        if (lives < 0) {
          
          // stop the game
          play = false;
          ballXdir = 0;
          ballYdir = 0;
          
          // displays losing screen
          g.setColor(Color.RED);
          g.setFont(new Font("serif", Font.BOLD, 30));
          g.drawString("Game Over", 260, 315);

          g.setFont(new Font("serif", Font.BOLD, 20));
          g.drawString("Press (Enter) to Restart", 230, 350);
          g.drawString("Press (Backspace) to Exit the Game", 190, 375);
        }
      }
    }
    g.dispose();
  }

  // listens for keys
  public void keyPressed(KeyEvent e) {

    // executed when you hit the right arrow key or 'd'
    if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
      // move the paddle right unless they are as right as can be
      if (playerX >= 600) {
        playerX = 600;
      } else {
        moveRight();
      }
    }

    // executed when you hit the left arrow key or 'a'
    if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
      // move the paddle left unless left as can be
      if (playerX < 10) {
        playerX = 10;
      } else {
        moveLeft();
      }
    }

    // restart. executes when you hit the enter key when the game is stopped
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      if (!play) { // if the game is over

        // resets defaults
        lives = 3;
        decrementLives = true;
        play = true;
        ballposX = 350;
        ballposY = 350;
        ballXdir = -2;
        ballYdir = -4;
        playerX = 310;
        score = 0;
        level = 1; 
        totalBricks = 10 * level;
        mapX = totalBricks / 5;
        mapY = 5;

        // generates new map with default values
        map = new MapGenerator(mapX, mapY);

        repaint();
      }
    }

    // progress to the next level. executes when you hit the space bar and the game is stopped
    if (e.getKeyCode() == KeyEvent.VK_SPACE && totalBricks == 0) {
      if (!play) {

        // increment the level
        level++;

        //resets defaults 
        counter = 1;
        decrementLives = true;
        play = false;
        ballposX = 350;
        ballposY = 350;
        playerX = 310;
        ballXdir = -2;
        ballYdir = -4;
        totalBricks = 10 * level;
        mapX = totalBricks / 5;
        mapY = 5;

        // increase the speed of the ball according to the function y=1.3^x + 0.7 where x is the level and y is speed
        ballXdir = -1 * (Math.pow(1.3, level) + 0.7);
        ballYdir = -2 * (Math.pow(1.3, level) + 0.7);   

        // generate new map with bigger values
        map = new MapGenerator(mapX, mapY);
        System.out.println("Map x: " + mapX + "Map y: " + mapY);

        repaint();
      }
    }

    // executed when you hit the backspace when the game is stopped
    if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
      if (!play) {
        // terminates running JVM, normal termination code
        System.exit(0);
      }
    }
  }

  // necessary methods to implement because I'm implementing the KeyListener interface
  public void keyReleased(KeyEvent e) {}
  public void keyTyped(KeyEvent e) {}

  // paddle move right method
  public void moveRight() {
    // moves the paddle over 40 pixels
    playerX += 40; 
    // if the program can decrement lives then it should be able to move the paddle 
    if (decrementLives) {
      play = true;
      if (counter == 1) {
        ballXdir = -1 * (Math.pow(1.3, level) + 0.7);
        ballYdir = -2 * (Math.pow(1.3, level) + 0.7);
        counter++;
      }
    }
  }

  // paddle move left method
  public void moveLeft() {
    // moves the paddle over 40 pixels
    playerX -= 40; 
    // if the program can decrement lives then it should be able to move the paddle 
    if (decrementLives) {
      play = true;
      if (counter == 1) {
        ballXdir = -1 * (Math.pow(1.3, level) + 0.7);
        ballYdir = -2 * (Math.pow(1.3, level) + 0.7);
        counter++;
      }
    }
  }

  // executed whenever an action occurs
  public void actionPerformed(ActionEvent e) {
    timer.start();
    if (play) {
      // Conditions for if the ball hits the paddle
      if (new Rectangle(ballposX, ballposY, 20, 20)
              .intersects(new Rectangle(playerX, 550, 30, 8))) {
        ballYdir = -ballYdir;
        // ballXdir = -2;

      } else if (new Rectangle(ballposX, ballposY, 20, 20)
                     .intersects(new Rectangle(playerX + 70, 550, 30, 8))) {
        ballYdir = -ballYdir;
        // ballXdir++;

      } else if (new Rectangle(ballposX, ballposY, 20, 20)
                     .intersects(new Rectangle(playerX + 30, 550, 40, 8))) {
        ballYdir = -ballYdir;
      }

    // check map collision with the ball
    A:
      for (int i = 0; i < map.map.length; i++) {
        for (int j = 0; j < map.map[0].length; j++) {
          if (map.map[i][j] > 0) {
           
            int brickX = j * map.brickWidth + 80;
            int brickY = i * map.brickHeight + 50;
            int brickWidth = map.brickWidth;
            int brickHeight = map.brickHeight;

            Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
            Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
            Rectangle brickRect = rect;

            // when the ball hits a brick, decrement the bricks and add 5 to your score
            if (ballRect.intersects(brickRect)) {
              map.setBrickValue(0, i, j);
              score += 5;
              totalBricks--;

              // when the ball hits the right or left of the brick, turn it the other way
              if (ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
                ballXdir = -ballXdir;
              } else { // when the ball hits the top or bottom of the brick
                ballYdir = -ballYdir;
              }
              break A; // break only outer for loop
            }
          }
        }
      }

      // adding the ball position to the direction
      ballposX += ballXdir;
      ballposY += ballYdir;

      // bounces the ball off left side
      if (ballposX < 0) {
        ballXdir = -ballXdir;
      }

      // bounces the ball off top
      if (ballposY < 0) {
        ballYdir = -ballYdir;
      }

      // bounces the ball off right
      if (ballposX > 670) {
        ballXdir = -ballXdir;
      }

      // bounce the ball off the bottom while lives are greater than 0
      if (lives > 0 && ballposY >= 571) {
        ballYdir = -ballYdir;
      }

      repaint();
    }
  }
}