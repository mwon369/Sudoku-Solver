package sudoku;

public class SudokuSolver {

	public static void main(String[] args) {

		int[][] board = {
				{0, 6, 0, 0, 0, 0, 7, 5, 0},
				{0, 0, 0, 0, 3, 6, 8, 0, 0},
				{0, 0, 2, 8, 0, 0, 4, 0, 0},
				{0, 0, 0, 0, 2, 0, 0, 0, 5},
				{9, 0, 8, 0, 0, 0, 1, 0, 7},
				{3, 0, 0, 0, 4, 0, 0, 0, 0},
				{0, 0, 6, 0, 0, 4, 2, 0, 0},
				{0, 0, 3, 6, 1, 0, 0, 0, 0},
				{0, 7, 9, 0, 0, 0, 0, 8, 0}
		};

		System.out.println();
		System.out.println("Initial board is: \n");
		printBoard(board);
		solveBoard(board);
		System.out.println();
		System.out.println("Solved board is: \n");
		printBoard(board);
	}

	public static void printBoard(int[][] board) {

		for (int i = 0; i < board.length; i++) {
			if (i % 3 == 0 && i != 0)
				System.out.println("- - - - - - - - - - -");
			for (int j = 0; j < board.length; j++) {
				if (j % 3 == 0 && j != 0)
					System.out.print("| ");
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}

	public static int[] findEmptySpace(int[][] board) {

		int[] emptyPosition = {board.length + 1, board.length + 1};
		
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if (board[i][j] == 0) {
					emptyPosition[0] = i;
					emptyPosition[1] = j;
					return emptyPosition;
				}
			}
		}
		return emptyPosition;
	}

	public static boolean checkNumber(int[][] board, int number, int rowPos, int colPos) {

		// check row
		for (int j = 0; j < board.length; j++) {
			if (board[rowPos][j] == number) {
				return false;
			}
		}
		// check column
		for (int i = 0; i < board.length; i++) {
			if (board[i][colPos] == number) {
				return false;
			}
		}
		// check 3x3 - we can easily determine which 3x3 an empty space is in by using integer division. This is because
		// integer division will only give us 0, 1 or 2 which will imply where our 3x3 starts and ends for the rows/cols.
		int vertical_3x3Pos = rowPos / 3;
		int horizontal_3x3Pos = colPos / 3;
		for (int i = vertical_3x3Pos * 3; i < vertical_3x3Pos * 3 + 3; i++) {
			for (int j = horizontal_3x3Pos * 3; j < horizontal_3x3Pos * 3 + 3; j++) {
				if (board[i][j] == number) {
					return false;
				}
			}
		}
		return true;
	}

	public static boolean solveBoard(int[][] board) {

		int[] emptyPosition = findEmptySpace(board); // get position of empty space
		if (emptyPosition[0] == board.length + 1) { // if there is no empty space, return true, i.e., function ends
			return true;
		}
		int row = emptyPosition[0]; // otherwise, assign the row and col positions respectively
		int col = emptyPosition[1];
		for (int i = 1; i < board.length + 1; i++) {
			if (checkNumber(board, i, row, col)) { // test numbers starting from 1, till we find a number that works
				board[row][col] = i; // if a given number works, assign that number to the board
				if (solveBoard(board)) { // recursively call the solveBoard function on the newly updated board
					return true;
				}
				board[row][col] = 0; // set the current square back to 0 (i.e., empty), if the next recursive call
									 // returns false
			}
		}
		return false;
	}
}
