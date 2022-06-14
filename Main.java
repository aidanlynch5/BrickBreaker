import java.awt.Color;
/*
Programming 12 Final - Change 5 things

2- Make the game faster DONE
3- Turn the border red, the ball green, the paddle yellow DONE
5- Add a button to restart and exit DONE
6- Add controls for W and D DONE
7- add lives DONE 
    when you lose a life it decreases your score by 15 DONE
    make the game get progressively faster (paddle and ball)
8 - change title - DONE

make the winning screen disappear when you progress to the next level
make it so that your ball spawns in a different location
make it so that when you hold the space bar when the games over it doesn't give you a bunch of levels
*/

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		JFrame obj = new JFrame();
		Gameplay gamePlay = new Gameplay();
		
		obj.setBounds(10, 10, 700, 600);
		obj.setTitle("Brick Breaker - Aidan Lynch");		
		obj.setResizable(false);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(gamePlay);
                obj.setVisible(true);
		
	}
}
