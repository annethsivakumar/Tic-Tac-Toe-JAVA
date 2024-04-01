import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.*;

public class Main {
  public static void main(String[] args) {
    JFrame j = new JFrame("TicTacToe by Anneth Sivakumar");
    j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    j.getContentPane().add(new TicTacToe(), BorderLayout.CENTER);
    j.setSize(500, 360);
    j.setVisible(true);
  }
}

class TicTacToe extends JPanel implements MouseListener, MouseMotionListener {

  // ====== DECLARE VARIABLES ======
  Grid grid;
  int[][] game;
  int rows, cols; // rows and columns of the grid

  int xScore; // keep track of player 1 score
  int oScore; // keep track of player 2 score

  int turn; // keep track of amount of turn to help determine ties
  int screen; // switch screens on the first 'page'

  int player;
  // player 1 = X player
  // player 2 = O player and computer
  // player 3 = use to determine ties/no wins
  // player 4 = check if X won
  // player 5 = check if O won
  // player 8 = change O to green when O wins
  // player 9 = change X to green when X wins

  ImageShape reset, newgame, home, p1vai, p1vp2; // buttons

  // ====== CONSTRUCTOR ======
  public TicTacToe() {
    setBackground(Color.black);

    // start with first screen
    screen = 1;

    // keep track of turns
    turn = 0;

    // set beginning value for X & O score
    xScore = 0;
    oScore = 0;

    // when game starts - player 1 starts
    player = 1;

    // set number of rows and columns in the grid
    rows = 3;
    cols = 3;

    // add an extra row and columns in the grid
    game = new int[rows + 2][cols + 2];

    // create a grid
    grid = new Grid();
    grid.setDimensions(rows, cols);
    grid.setLineThickness(1);
    grid.setCellSize(80);
    grid.setPosition(125, 50);
    grid.setCellColor(Color.white);

    // instantiate and initialize buttons
    reset = new ImageShape();
    reset.setPicture("buttons/reset.png");
    reset.setPosition(55, 230);
    reset.setSize(60);

    newgame = new ImageShape();
    newgame.setPicture("buttons/newgame.png");
    newgame.setPosition(375, 55);
    newgame.setSize(60);

    home = new ImageShape();
    home.setPicture("buttons/home.png");
    home.setPosition(55, 55);
    home.setSize(60);

    p1vai = new ImageShape();
    p1vai.setPicture("buttons/p1vai.png");
    p1vai.setPosition(90, 155);
    p1vai.setSize(150);

    p1vp2 = new ImageShape();
    p1vp2.setPicture("buttons/p1vp2.png");
    p1vp2.setPosition(260, 155);
    p1vp2.setSize(150);

    addMouseListener(this);
    addMouseMotionListener(this);
  }

  // ====== PAINT COMPONENT ======
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    // displays for the first screen
    if (screen == 1) {
      g.setColor(Color.red);
      g.setFont(new Font("courier", 3, 50));
      g.drawString("Tic", 80, 100);
      g.setColor(Color.green);
      g.setFont(new Font("courier", 3, 50));
      g.drawString("Tac", 205, 100);
      g.setColor(Color.blue);
      g.setFont(new Font("courier", 3, 50));
      g.drawString("Toe", 330, 100);

      p1vai.draw(g);
      p1vp2.draw(g);
    }

    // displays for the second screen(P1 V P2)
    if (screen == 2) {
      // draw grid
      grid.draw(g);

      // draw buttons
      reset.draw(g);
      newgame.draw(g);
      home.draw(g);

      // step through each row one at a time
      for (int row = 1; row <= rows; row++) {
        // step through each column in that row one at a time
        for (int column = 1; column <= cols; column++) {
          // get the x, y coordinates of that cell
          int x = grid.getCellX(column); // column numbers
          int y = grid.getCellY(row); // row numbers increase

          // 1 = X (player 1)
          if (game[row][column] == 1) {
            g.setColor(Color.red);
            g.setFont(new Font("arial", 1, 35));
            g.drawString("X", x + 26, y + 55);

            // 2 = O (player 2)
          } else if (game[row][column] == 2) {
            g.setColor(Color.blue);
            g.setFont(new Font("arial", 1, 35));
            g.drawString("O", x + 26, y + 55);
          }

          // 9 = change color to show X won
          if (game[row][column] == 9) {
            g.setColor(Color.green);
            g.setFont(new Font("arial", 1, 35));
            g.drawString("X", x + 26, y + 55);
          }

          // 8 = change color to show O won
          if (game[row][column] == 8) {
            g.setColor(Color.green);
            g.setFont(new Font("arial", 1, 35));
            g.drawString("O", x + 26, y + 55);
          }

          // display who's turn it is
          if (player == 1) {
            g.setColor(Color.red);
            g.setFont(new Font("courier", 3, 30));
            g.drawString("X", 15, 180);
            g.setColor(Color.green);
            g.setFont(new Font("courier", 2, 30));
            g.drawString("Turn", 40, 180);
          } else if (player == 2) {
            g.setColor(Color.blue);
            g.setFont(new Font("courier", 3, 30));
            g.drawString("O", 15, 180);
            g.setColor(Color.green);
            g.setFont(new Font("courier", 2, 30));
            g.drawString("Turn", 40, 180);
          } else if (player == 3) {
            g.setColor(Color.green);
            g.setFont(new Font("courier", 3, 22));
            g.drawString("Tie Game!", 5, 180);
          } else if (player == 4) {
            g.setColor(Color.red);
            g.setFont(new Font("courier", 3, 22));
            g.drawString("X Wins!", 20, 180);
          } else if (player == 5) {
            g.setColor(Color.blue);
            g.setFont(new Font("courier", 3, 22));
            g.drawString("O Wins!", 20, 180);
          }

          // display X score
          g.setColor(Color.red);
          g.setFont(new Font("arial", 2, 20));
          g.drawString("X", 375, 160);
          g.setColor(Color.green);
          g.setFont(new Font("arial", 2, 20));
          g.drawString("Score: " + xScore, 395, 160);

          // display O score
          g.setColor(Color.blue);
          g.setFont(new Font("arial", 2, 20));
          g.drawString("O", 372, 190);
          g.setColor(Color.green);
          g.setFont(new Font("arial", 2, 20));
          g.drawString("Score: " + oScore, 395, 190);
        }

        // display title
        g.setColor(Color.red);
        g.setFont(new Font("courier", 3, 30));
        g.drawString("Tic", 135, 40);
        g.setColor(Color.green);
        g.setFont(new Font("courier", 3, 30));
        g.drawString("Tac", 215, 40);
        g.setColor(Color.blue);
        g.setFont(new Font("courier", 3, 30));
        g.drawString("Toe", 295, 40);

      }
    }

    // displays for the third screen (P1 V AI)
    if (screen == 3) {
      // draw grid
      grid.draw(g);

      // draw buttons
      reset.draw(g);
      newgame.draw(g);
      home.draw(g);

      // step through each row one at a time
      for (int row = 1; row <= rows; row++) {
        // step through each column in that row one at a time
        for (int column = 1; column <= cols; column++) {
          // get the x, y coordinates of that cell
          int x = grid.getCellX(column); // column numbers
          int y = grid.getCellY(row); // row numbers increase

          // 1 = X (player 1)
          if (game[row][column] == 1) {
            g.setColor(Color.red);
            g.setFont(new Font("arial", 1, 35));
            g.drawString("X", x + 26, y + 55);

            // 2 = O (player 2)
          } else if (game[row][column] == 2) {
            g.setColor(Color.blue);
            g.setFont(new Font("arial", 1, 35));
            g.drawString("O", x + 26, y + 55);
          }

          // 9 = change color to show X won
          if (game[row][column] == 9) {
            g.setColor(Color.green);
            g.setFont(new Font("arial", 1, 35));
            g.drawString("X", x + 26, y + 55);
          }

          // 8 = change color to show O won
          if (game[row][column] == 8) {
            g.setColor(Color.green);
            g.setFont(new Font("arial", 1, 35));
            g.drawString("O", x + 26, y + 55);
          }

          // display who's wins
          if (player == 3) {
            g.setColor(Color.green);
            g.setFont(new Font("courier", 3, 22));
            g.drawString("Tie Game!", 200, 316);
          } else if (player == 4) {
            g.setColor(Color.red);
            g.setFont(new Font("courier", 3, 22));
            g.drawString("You Win!", 205, 316);
          } else if (player == 5) {
            g.setColor(Color.blue);
            g.setFont(new Font("courier", 3, 22));
            g.drawString("Computer Wins!", 155, 316);
          }

          // display X score
          g.setColor(Color.red);
          g.setFont(new Font("arial", 2, 20));
          g.drawString("Your", 375, 155);
          g.setColor(Color.green);
          g.setFont(new Font("arial", 2, 20));
          g.drawString("Score: " + xScore, 395, 175);

          // display O score
          g.setColor(Color.blue);
          g.setFont(new Font("arial", 2, 20));
          g.drawString("Computer", 372, 230);
          g.setColor(Color.green);
          g.setFont(new Font("arial", 2, 20));
          g.drawString("Score: " + oScore, 395, 250);
        }

        // display title
        g.setColor(Color.red);
        g.setFont(new Font("courier", 3, 30));
        g.drawString("Tic", 135, 40);
        g.setColor(Color.green);
        g.setFont(new Font("courier", 3, 30));
        g.drawString("Tac", 215, 40);
        g.setColor(Color.blue);
        g.setFont(new Font("courier", 3, 30));
        g.drawString("Toe", 295, 40);

      }
    }

    // repaint();
    requestFocus();
  }

  // ====== MOUSE MOTION LISTENER METHOD ======
  // mouse moved and mouse dragged
  public void mouseDragged(MouseEvent e) {
  }

  public void mouseMoved(MouseEvent e) {
    if (home.contains(e.getX(), e.getY())) {
      home.setPicture("buttons/home.png");
    } else {
      home.setPicture("buttons/home2.png");
    }

    if (newgame.contains(e.getX(), e.getY())) {
      newgame.setPicture("buttons/newgame.png");
    } else {
      newgame.setPicture("buttons/newgame2.png");
    }

    if (reset.contains(e.getX(), e.getY())) {
      reset.setPicture("buttons/reset.png");
    } else {
      reset.setPicture("buttons/reset2.png");
    }

    if (p1vp2.contains(e.getX(), e.getY())) {
      p1vp2.setPicture("buttons/p1vp2.png");
    } else {
      p1vp2.setPicture("buttons/p1vp22.png");
      p1vp2.setSize(148);
    }

    if (p1vai.contains(e.getX(), e.getY())) {
      p1vai.setPicture("buttons/p1vai.png");
    } else {
      p1vai.setPicture("buttons/p1vai2.png");
    }

    repaint();
  } // ====== END OF MOUSE MOTION LISTENER METHODS ======

  // ====== MOUSE LISTENER METHODS ======
  // == What to do when mouse is clicked - change variables, etc.
  public void mouseClicked(MouseEvent e) {

    // do while on screen 2 and 3
    if (screen == 2 || screen == 3) {
      // when player 1 turn...
      if (player == 1) {
        if (grid.contains(e.getX(), e.getY())) {
          int r = grid.getRow(e.getY());
          int c = grid.getColumn(e.getX());
          if (game[r][c] == 0) {
            game[r][c] = 1; // replace tile with circle - if blank
            turn++; // add 1 to turn
            player++; // next players turn
          }
          player1Win(); // check for possible wins
        }
      }

      // if its a tie game...
      if (turn == 9 && player != 4) {
        player = 3;
      }

      // when reset button is clicked - reset board and score
      if (reset.contains(e.getX(), e.getY())) {
        delete();
      }
      // when reset button is clicked - reset board and score
      if (newgame.contains(e.getX(), e.getY())) {
        newGame();
      }
      // when reset button is clicked - reset board and score
      if (home.contains(e.getX(), e.getY())) {
        screen = 1;
      }

    } // end of if on screen 2 and screen 3

    // do while on screen 2
    if (screen == 2) {
      // when player 2 turn...
      if (player == 2) {
        if (grid.contains(e.getX(), e.getY())) {
          int r = grid.getRow(e.getY());
          int c = grid.getColumn(e.getX());
          if (game[r][c] == 0) {
            game[r][c] = 2; // replace tile with circle - if blank
            turn++; // add 1 to turn
            player--; // player 1 turn
          }
          player2Win(); // check for possible win
        }
      }
    } // end of if on screen 2

    // do while on screen 1
    if (screen == 1) {
      // when reset button is clicked - reset board and score
      if (p1vp2.contains(e.getX(), e.getY())) {
        delete();
        screen = 2;
      }

      // when reset button is clicked - reset board and score
      if (p1vai.contains(e.getX(), e.getY())) {
        delete();
        screen = 3;
      }
    } // end of if on screen 1

    // do while on screen 3
    if (screen == 3) {
      computerMove();
    } // end of if on screen 3

    repaint();
  } // -- end of mouse clicked --

  public void mousePressed(MouseEvent e) {
  }

  public void mouseReleased(MouseEvent e) {
  }

  public void mouseEntered(MouseEvent e) {
  }

  public void mouseExited(MouseEvent e) {
  } // ====== END OF MOUSE LISTENER METHODS ======

  // ====== METHOD TO CHECK IF PLAYER 1 WINS ======
  public void player1Win() {
    if (game[1][1] == 1 && game[1][2] == 1 && game[1][3] == 1) {
      game[1][1] = 9;
      game[1][2] = 9;
      game[1][3] = 9;
      xScore++; // add point to player 1
      player = 4; // player 4 = player 1 winner
    }
    if (game[2][1] == 1 && game[2][2] == 1 && game[2][3] == 1) {
      game[2][1] = 9;
      game[2][2] = 9;
      game[2][3] = 9;
      xScore++; // add point to player 1
      player = 4; // player 4 = player 1 winner
    }
    if (game[3][1] == 1 && game[3][2] == 1 && game[3][3] == 1) {
      game[3][1] = 9;
      game[3][2] = 9;
      game[3][3] = 9;
      xScore++; // add point to player 1
      player = 4; // player 4 = player 1 winner
    }
    if (game[1][1] == 1 && game[2][1] == 1 && game[3][1] == 1) {
      game[1][1] = 9;
      game[2][1] = 9;
      game[3][1] = 9;
      xScore++; // add point to player 1
      player = 4; // player 4 = player 1 winner
    }
    if (game[1][2] == 1 && game[2][2] == 1 && game[3][2] == 1) {
      game[1][2] = 9;
      game[2][2] = 9;
      game[3][2] = 9;
      xScore++; // add point to player 1
      player = 4; // player 4 = player 1 winner
    }
    if (game[1][3] == 1 && game[2][3] == 1 && game[3][3] == 1) {
      game[1][3] = 9;
      game[2][3] = 9;
      game[3][3] = 9;
      xScore++; // add point to player 1
      player = 4; // player 4 = player 1 winner
    }
    if (game[1][1] == 1 && game[2][2] == 1 && game[3][3] == 1) {
      game[1][1] = 9;
      game[2][2] = 9;
      game[3][3] = 9;
      xScore++; // add point to player 1
      player = 4; // player 4 = player 1 winner
    }
    if (game[1][3] == 1 && game[2][2] == 1 && game[3][1] == 1) {
      game[1][3] = 9;
      game[2][2] = 9;
      game[3][1] = 9;
      xScore++; // add point to player 1
      player = 4; // player 4 = player 1 winner
    }
  } // ====== END OF PLAYER1WIN() ======

  // ====== METHOD TO CHECK IF PLAYER 2 WINS ======
  public void player2Win() {
    if (game[1][1] == 2 && game[1][2] == 2 && game[1][3] == 2) {
      game[1][1] = 8;
      game[1][2] = 8;
      game[1][3] = 8;
      oScore++; // add point to player 2
      player = 5; // player 5 = player 2 winner
    }
    if (game[2][1] == 2 && game[2][2] == 2 && game[2][3] == 2) {
      game[2][1] = 8;
      game[2][2] = 8;
      game[2][3] = 8;
      oScore++; // add point to player 2
      player = 5; // player 5 = player 2 winner
    }
    if (game[3][1] == 2 && game[3][2] == 2 && game[3][3] == 2) {
      game[3][1] = 8;
      game[3][2] = 8;
      game[3][3] = 8;
      oScore++; // add point to player 2
      player = 5; // player 5 = player 2 winner
    }
    if (game[1][1] == 2 && game[2][1] == 2 && game[3][1] == 2) {
      game[1][1] = 8;
      game[2][1] = 8;
      game[3][1] = 8;
      oScore++; // add point to player 2
      player = 5; // player 5 = player 2 winner
    }
    if (game[1][2] == 2 && game[2][2] == 2 && game[3][2] == 2) {
      game[1][2] = 8;
      game[2][2] = 8;
      game[3][2] = 8;
      oScore++; // add point to player 2
      player = 5; // player 5 = player 2 winner
    }
    if (game[1][3] == 2 && game[2][3] == 2 && game[3][3] == 2) {
      game[1][3] = 8;
      game[2][3] = 8;
      game[3][3] = 8;
      oScore++; // add point to player 2
      player = 5; // player 5 = player 2 winner
    }
    if (game[1][1] == 2 && game[2][2] == 2 && game[3][3] == 2) {
      game[1][1] = 8;
      game[2][2] = 8;
      game[3][3] = 8;
      oScore++; // add point to player 2
      player = 5; // player 5 = player 2 winner
    }
    if (game[1][3] == 2 && game[2][2] == 2 && game[3][1] == 2) {
      game[1][3] = 8;
      game[2][2] = 8;
      game[3][1] = 8;
      oScore++; // add point to player 2
      player = 5; // player 5 = player 2 winner
    }
  } // ====== END OF PLAYER2WIN() ======

  // // ====== RESTART ENTIRE GAME METHOD ======
  public void delete() {
    // reset board
    game[1][1] = 0;
    game[1][2] = 0;
    game[1][3] = 0;
    game[2][1] = 0;
    game[2][2] = 0;
    game[2][3] = 0;
    game[3][1] = 0;
    game[3][2] = 0;
    game[3][3] = 0;

    // reset score
    xScore = 0;
    oScore = 0;

    // reset turns
    turn = 0;

    // reset first player (player 1)
    player = 1;
  } // ====== END OF DELETE() ======

  // ====== CREATE A NEW GAME - WITH SAME SCORE ======
  public void newGame() {
    // restart board
    game[1][1] = 0;
    game[1][2] = 0;
    game[1][3] = 0;
    game[2][1] = 0;
    game[2][2] = 0;
    game[2][3] = 0;
    game[3][1] = 0;
    game[3][2] = 0;
    game[3][3] = 0;

    // restart turn
    turn = 0;

    // reset first player (player 1)
    player = 1;
  } // ====== END OF NEWGAME() ======

  // ====== ALGORITHIM FOR COMPUTER ======
  public void computerMove() {
    while (player == 2) {
      int r = (int) (Math.random() * 3 + 1); // generate random row
      int c = (int) (Math.random() * 3 + 1); // generate random column
      if (game[r][c] == 0) { // if blank space - replace with O
        game[r][c] = 2;
        turn++; // next turn
        player--; // next player turn
      } else if (game[r][c] != 0) { // if there is already X or O - increase row and column by 1
        r++;
        c++;
        if (r == 3) { // when row reaches 3 - go to 1
          r = 1;
        }
        if (c == 3) { // when column reaches 3 - go to 1
          c = 1;
        }

      }
      player2Win(); // check if possible win

    }
  } // ====== END OF COMPUTERMOVE() ======

} // end of TicTacToe