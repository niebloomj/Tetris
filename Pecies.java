import java.awt.Color;

public class Pecies {
	//Declaring instance variables
	public boolean drawMe = true;
	private boolean[][] pieceArray;
	public Color color;
	public static int x = 4;
	public static int y = 0;
	int rowForNew ;
	int columnForNew ;
	private int row, cell ;
	boolean isRight = false;
	boolean isLeft = false;

	//Constructor
	public Pecies(char a) {
		//Creating boolean arrays for each piece
		if (a == 'Z') {
			boolean [][] temp = {{true, true, false}, {false, true, true}};
			pieceArray = temp;
			color = Color.red;
		} else if (a == 'S') {
			boolean [][] temp = {{false, true, true}, {true, true, false}};
			pieceArray = temp;
			color = Color.green;
		} else if (a == 'O') {
			boolean [][] temp = {{true, true}, {true, true}};
			pieceArray = temp;
			color = Color.pink;
		} else if (a == 'T') {
			boolean [][] temp = {{false, true, false}, {true, true, true}};
			pieceArray = temp;
			color = new Color(153, 51, 255);
		} else if (a == 'I') {
			boolean [][] temp = {{true}, {true}, {true}, {true}};
			pieceArray = temp;
			color = new Color(0, 255, 255);
		} else if (a == 'L') {
			boolean [][] temp = {{true, false}, {true, false}, {true, true}};
			pieceArray = temp;
			color = Color.orange;
		} else if (a == 'J') {
			boolean [][] temp = {{false, true}, {false, true}, {true, true}};
			pieceArray = temp;
			color = Color.gray;
		}
	}
	//gets number of columns
	public int getColumn() {
		return pieceArray[0].length;
	}
	//gets number of  rows
	public int getRow() {
		return pieceArray.length;
	}
	//Fetches the array of the respective pieces
	public boolean[][] getPieceArray() {
		return pieceArray;
	}
	//gets color of a specific piece
	public Color getColor() {
		return color;
	}
	//Gets value of X that controls the location of the piece on the board
	public int getX() {
		return x;
	}
	//Gets value of Y that controls the location of the piece on the board
	public int getY() {
		return y;
	}
	//Sets value of X
	public void setX(int x) {
		this.x = x;
	}
	//Sets value of Y
	public void setY(int y) {
		this.y = y;
	}
	//Moves the piece down
	public void moveDown(boolean withPaint) {
		y++;
		//Checks if the piece cis within limits
		if (outOfBounds(withPaint) && drawMe) {
			if (y == 18)
				y = 1;
			else
				y--;
		}
	}
	//Moves the piece left
	public void moveLeft() {
		isLeft = true;
		x--;
		if (outOfBounds(false))
			x++;
		isLeft = false;

	}
	//Moves the piece right
	public void moveRight() {
		isRight = true;
		x++;
		if (outOfBounds(false))
			x--;
		isRight = false;
	}

	//Called when the user hits the space key
	public void moveAllDown() {
		for (int i = 0; i < 20; i ++)
			Test.myBoard.movePiece(Test.myBoard.piece, 1, false);
		Test.myBoard.movePiece(Test.myBoard.piece, 1, true);
	}
	// Rotates the pieces
	public void rotate() {
		// if (x +  getColumn()  < 4) {
		//  moveDown(false);
		boolean [][] tempArray = pieceArray;

		//Creates a new array that has number of columns equal to the number of rows
		//and of number rows equal to the number of columns of the
		//original piece array
		boolean [][] newPieceArray = new boolean[getColumn()][getRow()];
		columnForNew = getRow() - 1;
		rowForNew = 0;
		//Scanning through the original array and initializing the new array
		//with new true and false values
		for (int row = 0 ; row < pieceArray.length ; row ++) {
			for (int column = 0 ; column < pieceArray[row].length ; column++) {
				newPieceArray[rowForNew][columnForNew] = pieceArray[row][column] ;
				rowForNew++;
				if (rowForNew == getColumn())
					rowForNew = 0;
			}
			columnForNew--;
		}
		drawMe = false;
		Test.myBoard.movePiece(this, 4, true);
		pieceArray = newPieceArray;

		if (outOfBounds(false))
			pieceArray = tempArray;
		Test.myBoard.movePiece(this, 4, true);
	}
	//Checks if the piece is out of bounds
	public boolean outOfBounds(boolean withPaint) {
		if (x == -1)
			return true;
		//Prevents the piece from going below the board
		//Stops the piece when the piece hits the bottom
		for (int i = 1; i < 5; i ++) {
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
		for (int row = 0 ; row < pieceArray.length ; row++)
			for (int cell = 0 ; cell < pieceArray[row].length ; cell++)
				if (pieceArray[row][cell]) {
					if (getColumn() == 4) {
						if (x + cell > 9)
							return true;
					}
					//If right key is hit, check the cell to the right
					//If it is not blue, then that means there is some piece there
					if (isRight)
						if (Board.boardColors[y + row ][x + cell ] != Color.blue)
							return true;
					//If left key is pressed, check the cell to the right
					//If it is not blue, then that means there is some piece there
					if (isLeft)
						if (Board.boardColors[y + row][x + cell ] != Color.blue)
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
	//There is just one instance of the Pecies class
	//The settled pieces on the board are just colors on the board
	public void paint(boolean withPaint) {
		if (withPaint) {
			for (int i = 0 ; i < getPieceArray().length; i++)
				for (int j = 0; j < getPieceArray()[i].length; j++)
					if (getPieceArray()[i][j] == true)
						Test.myBoard.setColor(i + getY() - 1, j + getX(), getColor());
			for (int i = 0; i < 4; i++)
				Test.myBoard.deleteFullRows();
			if (Board.isGameOn == true)
				Test.myBoard.introduceNewPiece();
		}

	}
}