package com.jacobniebloom.tetris;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

//Creates a class that extends another class
public class KeyListener extends KeyAdapter {
    //Declaring the instance variables
    static char temp;
    static boolean isPressed = false;
    private static boolean isHeld = false;

    @Override
    //This method is called when the user presses some key
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        //Right key calls the moveRight method
        if (Board.isGameOn) {
            if (code == KeyEvent.VK_RIGHT)
                Test.myBoard.movePiece(Board.piece, MoveType.Right, true);
                //Down key calls the moveDown method
            else if (code == KeyEvent.VK_DOWN)
                Test.myBoard.movePiece(Board.piece, MoveType.Down, true);
                //Left key calls the moveLeft method
            else if (code == KeyEvent.VK_LEFT)
                Test.myBoard.movePiece(Board.piece, MoveType.Left, true);
                //Space key calls the moveAllDown method
            else if (code == KeyEvent.VK_SPACE)
                Board.piece.moveAllDown();
                // Z key holds or saves the current piece
            else if (code == KeyEvent.VK_Z) {
                if (!isHeld) {
                    temp = Board.letter;
                    Board.piece.drawMe = false;
                    Test.myBoard.movePiece(Board.piece, MoveType.Nope, true);
                    Test.myBoard.introduceNewPiece();
                    isHeld = true;
                    isPressed = true;
                }
            }
            //X key releases the saved piece
            else if (code == KeyEvent.VK_X) {

                if (isHeld && !isPressed) {
                    Board.letter = temp;
                    Board.piece.drawMe = false;
                    Test.myBoard.movePiece(Board.piece, MoveType.Nope, true);
                    Test.myBoard.switchPieces(temp);
                    Test.myBoard.movePiece(Board.piece, MoveType.Nope, true);
                    //isCurrentTurn = 0;
                    isHeld = false;
                }
            }
            //Up key calls the rotate method that rotates the piece
            else if (code == KeyEvent.VK_UP) {
                Board.piece.rotate();
            }
            //R key restarts the game
        } else if (code == KeyEvent.VK_R) {
            Test.myBoard.remove(Board.gameOver);
            Test.myBoard.remove(Board.gameOver1);
            Test.myBoard.remove(Board.gameOver2);
            Test.myBoard.remove(Board.gameOver3);
            for (int i = 0; i < Board.height; i++)
                for (int j = 0; j < Board.width; j++)
                    Board.boardColors[i][j] = Color.blue;
            Piece.x = 4;
            Piece.y = 0;
            Board.score.setText("0/200");
            Board.scoreInInt = 0;
            Board.isGameOn = true;
        } else if (code == KeyEvent.VK_Q) {
            System.exit(0);
        }
    }
}