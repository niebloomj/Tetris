package com.jacobniebloom.tetris;

import java.awt.*;

class Piece {
    static int x = 4;
    static int y = 0;
    //Declaring instance variables
    boolean drawMe = true;
    private Color color;
    private boolean isRight = false;
    private boolean isLeft = false;
    private boolean[][] pieceArray;

    //Constructor
    Piece(char a) {
        //Creating boolean arrays for each piece
        if (a == 'Z') {
            pieceArray = new boolean[][]{{true, true, false}, {false, true, true}};
            color = Color.red;
        } else if (a == 'S') {
            pieceArray = new boolean[][]{{false, true, true}, {true, true, false}};
            color = Color.green;
        } else if (a == 'O') {
            pieceArray = new boolean[][]{{true, true}, {true, true}};
            color = Color.pink;
        } else if (a == 'T') {
            pieceArray = new boolean[][]{{false, true, false}, {true, true, true}};
            color = new Color(153, 51, 255);
        } else if (a == 'I') {
            pieceArray = new boolean[][]{{true}, {true}, {true}, {true}};
            color = new Color(0, 255, 255);
        } else if (a == 'L') {
            pieceArray = new boolean[][]{{true, false}, {true, false}, {true, true}};
            color = Color.orange;
        } else if (a == 'J') {
            pieceArray = new boolean[][]{{false, true}, {false, true}, {true, true}};
            color = Color.gray;
        }
    }

    //gets number of columns
    private int getColumn() {
        return pieceArray[0].length;
    }

    //gets number of  rows
    private int getRow() {
        return pieceArray.length;
    }

    //Fetches the array of the respective pieces
    boolean[][] getPieceArray() {
        return pieceArray;
    }

    //gets color of a specific piece
    Color getColor() {
        return color;
    }

    //Gets value of X that controls the location of the piece on the board
    int getX() {
        return x;
    }

    //Gets value of Y that controls the location of the piece on the board
    int getY() {
        return y;
    }

    //Moves the piece down
    void moveDown(boolean withPaint) {
        y++;
        //Checks if the piece cis within limits
        if (outOfBounds(withPaint) && drawMe) {
            if (y == 18) y = 1;
            else y--;
        }
    }

    //Moves the piece left
    void moveLeft() {
        isLeft = true;
        x--;
        if (outOfBounds(false)) x++;
        isLeft = false;

    }

    //Moves the piece right
    void moveRight() {
        isRight = true;
        x++;
        if (outOfBounds(false)) x--;
        isRight = false;
    }

    //Called when the user hits the space key
    void moveAllDown() {
        for (int i = 0; i < 20; i++)
            Test.myBoard.movePiece(Board.piece, MoveType.Down, false);
        Test.myBoard.movePiece(Board.piece, MoveType.Down, true);
    }

    // Rotates the pieces
    void rotate() {
        // if (x +  getColumn()  < 4) {
        //  moveDown(false);
        boolean[][] tempArray = pieceArray;

        //Creates a new array that has number of columns equal to the number of rows
        //and of number rows equal to the number of columns of the
        //original piece array
        boolean[][] newPieceArray = new boolean[getColumn()][getRow()];
        int columnForNew = getRow() - 1;
        int rowForNew = 0;
        //Scanning through the original array and initializing the new array
        //with new true and false values
        for (boolean[] booleans : pieceArray) {
            for (boolean aBoolean : booleans) {
                newPieceArray[rowForNew][columnForNew] = aBoolean;
                rowForNew++;
                if (rowForNew == getColumn())
                    rowForNew = 0;
            }
            columnForNew--;
        }
        drawMe = false;
        Test.myBoard.movePiece(Board.piece, MoveType.Nope, true);
        pieceArray = newPieceArray;

        if (outOfBounds(false))
            pieceArray = tempArray;
        Test.myBoard.movePiece(Board.piece, MoveType.Nope, true);
    }

    //Checks if the piece is out of bounds
    private boolean outOfBounds(boolean withPaint) {
        if (x == -1)
            return true;
        //Prevents the piece from going below the board
        //Stops the piece when the piece hits the bottom
        for (int i = 1; i < 5; i++) {
            if (getColumn() == i && x == Board.width - i + 1)
                return true;
            if (getRow() == i && y == Board.height - i + 1) {
                paint(withPaint);
                return true;
            }
        }
        //This for loop scans through the piece arrays and looks for the true values
        //Once the true values are found, the conditional statement check if its possible to
        //move the piece without going out of bounds in the direction specified by the user
        for (int row = 0; row < pieceArray.length; row++)
            for (int cell = 0; cell < pieceArray[row].length; cell++)
                if (pieceArray[row][cell]) {
                    if (getColumn() == 4) {
                        if (x + cell > 9)
                            return true;
                    }
                    //If right key is hit, check the cell to the right
                    //If it is not blue, then that means there is some piece there
                    if (isRight)
                        if (Board.boardColors[y + row][x + cell] != Color.blue)
                            return true;
                    //If left key is pressed, check the cell to the right
                    //If it is not blue, then that means there is some piece there
                    if (isLeft)
                        if (Board.boardColors[y + row][x + cell] != Color.blue)
                            return true;
                    //If nether of the right key or left key is hit, check the bottom cell
                    //If it is not blue, then that means there is some piece there
                    if (!(isLeft || isRight))
                        if (Board.boardColors[y + row][x + cell] != Color.blue) {
                            paint(withPaint);
                            return true;
                        }
                }

        return false;
    }

    //Once a piece gets settled,the location of that piece gets appropriately colored
    //and a new piece is introduced at the top
    //This creates an illusion that multiple pieces are being generated but that does not happen
    //There is just one instance of the com.jacobniebloom.tetris.Piece class
    //The settled pieces on the board are just colors on the board
    private void paint(boolean withPaint) {
        if (withPaint) {
            for (int i = 0; i < getPieceArray().length; i++)
                for (int j = 0; j < getPieceArray()[i].length; j++)
                    if (getPieceArray()[i][j])
                        Test.myBoard.setColor(i + getY() - 1, j + getX(), getColor());
            for (int i = 0; i < 4; i++)
                Test.myBoard.deleteFullRows();
            if (Board.isGameOn)
                Test.myBoard.introduceNewPiece();
        }

    }
}