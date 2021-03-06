package com.jacobniebloom.tetris;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

//Creates a Board class that extends JPanel
public class Board extends JPanel {
    static final int width = 10;
    static final int height = 18;
    private static final char[] letters = {'O', 'S', 'T', 'I', 'L', 'Z', 'J'};
    //Declaring all instance variables
    static Color[][] boardColors;
    static char letter;
    static Piece piece;
    static JLabel score;
    static int scoreInInt = 0;
    static boolean isGameOn = true;
    static JLabel gameOver, gameOver1, gameOver2, gameOver3;
    private static char nextLetter;
    private int row, col;

    //Creating a constructor
    Board() {
        //Creating and adding JLabels to the Panel
        JLabel scoreLabel = new JLabel();
        JLabel myName = new JLabel(" Jacob Niebloom  ");
        JLabel nextLabel = new JLabel();
        score = new JLabel();
        scoreLabel.setText("SCORE :");
        score.setText("0/200");
        myName.setFont(myName.getFont().deriveFont(15.0f));
        nextLabel.setText("Next piece and held piece:");
        nextLabel.setFont(nextLabel.getFont().deriveFont(18.0f));
        scoreLabel.setFont(scoreLabel.getFont().deriveFont(30.0f));
        score.setFont(scoreLabel.getFont().deriveFont(30.0f));
        add(scoreLabel);
        add(score);
        add(myName);
        add(nextLabel);
        //Setting the characteristics of the Panel
        setVisible(true);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);
        requestFocusInWindow(true);
        setBackground(Color.orange);
        //Creating an array of Colors
        boardColors = new Color[height][width];
        //Initializing all the elements in the colors array to color blue;
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                boardColors[i][j] = Color.blue;
        //Creating an instance of com.jacobniebloom.tetris.utilities class
        //Randomly generates a number between 0 an 6
        Board.letter = letters[new Random().nextInt(letters.length)];
        Board.nextLetter = letters[new Random().nextInt(letters.length)];
        //The number is associated with a spcific shape or piece
        //First instance of the com.jacobniebloom.tetris.Piece class is created
        Board.piece = new Piece(Board.letter);
        //calling the add method
        add(Board.piece);
    }

    //Checks if the game is over
    //If it is then a message is displayed on the screen
    //The user can either restart or quit the game
    private static void GameOver() {
        for (int m = 0; m < 10; m++)
            if (Board.boardColors[0][m] != Color.blue) {
                isGameOn = false;
                gameOver = new JLabel();
                gameOver1 = new JLabel();
                gameOver2 = new JLabel();
                gameOver3 = new JLabel();
                gameOver.setSize(600, 200);
                gameOver1.setSize(700, 380);
                gameOver2.setSize(800, 550);
                gameOver3.setSize(900, 690);
                gameOver.setText(" GAME      ");
                gameOver1.setText("     OVER");
                gameOver2.setText("  Press 'R' key to restart.");
                gameOver3.setText("   Press 'Q' key to quit.");
                gameOver.setFont(gameOver.getFont().deriveFont(90.0f));
                gameOver1.setFont(gameOver1.getFont().deriveFont(90.0f));
                gameOver2.setFont(gameOver2.getFont().deriveFont(40.0f));
                gameOver3.setFont(gameOver3.getFont().deriveFont(40.0f));
                gameOver.setForeground(Color.white);
                gameOver1.setForeground(Color.white);
                gameOver2.setForeground(Color.white);
                gameOver3.setForeground(Color.white);
                Test.myBoard.add(gameOver);
                Test.myBoard.add(gameOver1);
                Test.myBoard.add(gameOver2);
                Test.myBoard.add(gameOver3);
                return;
            }
    }

    //Creates and adds a randomly generated piece on the board
    void introduceNewPiece() {
        //Introduces new piece only if the game is still on
        GameOver();
        if (isGameOn) {
            letter = nextLetter;
            piece.drawMe = false;
            nextLetter = letters[new Random().nextInt(letters.length)];
            Board.piece = new Piece(letter);
            Piece.x = 4;
            Piece.y = 0;
            add(Board.piece);
            KeyListener.isPressed = false;
        }
    }

    //Switches pieces when the user released the held piece
    //by hitting the X key
    void switchPieces(char letter) {
        piece = new Piece(letter);
    }

    //Calls the movePiece method
    private void add(Piece piece) {
        movePiece(piece, MoveType.Nope, true);
    }

    void movePiece(Piece piece, MoveType x, boolean withPaint) {
        //Wipes the the existing location of the piece
        for (int i = 0; i < piece.getPieceArray().length; i++)
            for (int j = 0; j < piece.getPieceArray()[i].length; j++)
                if (piece.getPieceArray()[i][j]) {
                    setColor(i + Piece.y, j + Piece.x,
                            Color.blue);
                }
        //Moves the piece;
        if (x == MoveType.Left)
            piece.moveLeft();
        else if (x == MoveType.Down) {
            piece.moveDown(withPaint);
        } else if (x == MoveType.Right)
            piece.moveRight();
        //Draw the piece at the new location;
        if (piece.drawMe) {
            for (int i = 0; i < piece.getPieceArray().length; i++)
                for (int j = 0; j < piece.getPieceArray()[i].length; j++)
                    if (piece.getPieceArray()[i][j]) {
                        setColor(i + Piece.y, j + Piece.x,
                                piece.getColor());
                    }
        }
        piece.drawMe = true;
    }

    //Helps in setting a specific element in the colors array
    //to a specific color
    void setColor(int x, int y, Color color) {
        boardColors[x][y] = color;
        repaint();
    }
    //Colors [][] sideboardColors = new Color[4][3];

    //Paints the Board and creates the squares in a grid format
    public void paintComponent(Graphics page) {
        page.setColor(Color.black);
        page.fillRect(40, 70, 405, 725);
        int i = 0;
        int j = 0;
        for (row = 75; row < 795; row += 40) {
            for (col = 46; col < 430; col += 40) {
                page.setColor(boardColors[i][j++]);
                page.fillRect(col, row, 32, 32);
            }
            i++;
            j = 0;
        }
        displayNextPiece(page, 490, 600, 80, 210, nextLetter);
        displayNextPiece(page, 500, 710, 450, 610, KeyListener.temp);
    }

    //Method that displays the next and the help piece on the screen
    private void displayNextPiece(Graphics page, int a, int b, int c, int d,
                                  char letter) {
        int i = 0;
        int j = 0;
        for (row = a; row < b; row += 40) {
            for (col = c; col < d; col += 40) {
                page.setColor(Color.orange);
                page.fillRect(row, col, 32, 32);
                if (letter == 'O') {
                    if (i == 0 && j == 0 || i == 0 && j == 1 || i == 1 && j == 1 || i == 1
                            && j == 0) {
                        page.setColor(Color.pink);
                        page.fillRect(row, col, 32, 32);
                    }
                }
                if (letter == 'J')
                    if (i == 2 && j == 0 || i == 2 && j == 1 || i == 2 && j == 2 || i == 1
                            && j == 2) {
                        page.setColor(Color.gray);
                        page.fillRect(row, col, 32, 32);
                    }
                if (letter == 'Z')
                    if (i == 0 && j == 1 || i == 1 && j == 1 || i == 1 && j == 2 || i == 2
                            && j == 2) {
                        page.setColor(Color.red);
                        page.fillRect(row, col, 32, 32);
                    }
                if (letter == 'L')
                    if (i == 1 && j == 0 || i == 1 && j == 1 || i == 1 && j == 2 || i == 2
                            && j == 2) {
                        page.setColor(Color.yellow);
                        page.fillRect(row, col, 32, 32);
                    }
                if (letter == 'T') {
                    if (i == 1 && j == 1 || i == 0 && j == 2 || i == 1 && j == 2 || i == 2
                            && j == 2)
                        page.setColor(new Color(153, 51, 255));
                    page.fillRect(row, col, 32, 32);
                }
                if (letter == 'S') {
                    if (i == 0 && j == 2 || i == 1 && j == 2 || i == 1 && j == 1 || i == 2
                            && j == 1)
                        page.setColor(Color.green);
                    page.fillRect(row, col, 32, 32);
                }
                if (letter == 'I') {
                    if (i == 1 && j == 0 || i == 1 && j == 1 || i == 1 && j == 2 || i == 1
                            && j == 3) {
                        page.setColor(new Color(0, 255, 255));
                        page.fillRect(row, col, 32, 32);
                    }
                }

                j++;
            }
            i++;
            j = 0;
        }
    }

    // Deletes the row that is full with pieces
    void deleteFullRows() {
        int i;

        for (i = 17; i >= 0; i--) {
            boolean isRowFull = true;
            for (int j = 0; j < 10; j++)
                if (boardColors[i][j] == Color.blue) {
                    isRowFull = false;
                    break;
                }
            if (isRowFull) {
                for (col = 0; col < 10; col++)
                    boardColors[i][col] = Color.blue;
                for (int row = i; row > 0; row--)
                    for (int column = 0; column < 10; column++) {
                        boardColors[row][column] = boardColors[row - 1][column];
                        boardColors[row - 1][column] = Color.blue;
                    }
                //Increases the score
                scoreInInt = scoreInInt + 10;
                score.setText(scoreInInt + "/200");
                return;
            }
        }
    }
}








