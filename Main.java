/*
Programming 12 Final - Change 5 things


1 - Add lives (start with 3, decrease score by 0 if you lose one, game ends if you die with 0 left)
2 - Add levels (each level makes a more complex map structure with more bricks to break)
3 - Make the game speed up every level according to the math function y = 1.3^x + 0.7 where x is level and y is speed
4 - Add a button to restart the game, exit the game, and progress to the next level
5 - Speed up the paddle so you can move a larger distance per click so it's not so hard with higher levels

6 - Add controls for A and D in addition to arrow keys
1 - Turn the border red, the ball green, the paddle yellow
8 - change window title

*/

import javax.swing.JFrame;

// main method includes game initialization and defaults

public class Main {
  public static void main(String[] args) {
    JFrame obj = new JFrame();
    Gameplay gamePlay = new Gameplay();

    obj.setBounds(10, 10, 708, 595); //width default 700, height default 600
    obj.setTitle("Brick Breaker - Aidan Lynch");
    obj.setResizable(false);
    obj.setVisible(true);
    obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    obj.add(gamePlay);
    obj.setVisible(true);
  }
}
