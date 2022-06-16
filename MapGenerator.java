import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class MapGenerator {

  // initializing public variables/2D array to create the map
  public int map[][];
  public int brickWidth;
  public int brickHeight;

  // constructor called when creating a new map, a new instance of the MapGenerator class
  public MapGenerator(int row, int col) {
    // 2D array holds x and y parameters as rows and columns
    map = new int[row][col];

    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[0].length; j++) {
        map[i][j] = 1;
      }
    }

    brickWidth = 540 / col;
    brickHeight = 150 / row;
  }

  // draws the correct amount of bricks according to row and column values
  public void draw(Graphics2D g) {
    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[0].length; j++) {
        if (map[i][j] > 0) {
          g.setColor(Color.white);
          g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth,
              brickHeight);

          // This shows seperate bricks
          g.setStroke(new BasicStroke(3));
          g.setColor(Color.black);
          g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
        }
      }
    }
  }

  // gives bricks a value that allows them to be identified
  public void setBrickValue(int value, int row, int col) {
    map[row][col] = value;
  }
}