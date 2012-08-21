package org.hgame;

import java.util.Random;

public class Board {

	/**
	 * The width of the game board
	 */
	final static public int SIZE_X = 10;

	/**
	 * The height of the game board
	 */
	final static public int SIZE_Y = 20;

	enum ChangePos {
		MOVE_LEFT, MOVE_RIGHT, TURN_LEFT, TURN_RIGHT
	}

	/**
	 * The generator of pseudo-random values
	 */
	private static final Random RND = new Random();

	final Figure.Point FIGURE_L[] = { new Figure.Point(0, 0),
			new Figure.Point(-1, 0), new Figure.Point(1, 0),
			new Figure.Point(2, 0) };

	final Figure.Point FIGURE_J[] = { new Figure.Point(0, 0),
			new Figure.Point(-1, 0), new Figure.Point(1, 0),
			new Figure.Point(1, 1) };

	final Figure.Point FIGURE_Z[] = { new Figure.Point(0, 0),
			new Figure.Point(-1, 0), new Figure.Point(0, 1),
			new Figure.Point(1, 1) };

	final Figure.Point FIGURE_S[] = { new Figure.Point(0, 0),
			new Figure.Point(1, 0), new Figure.Point(0, 1),
			new Figure.Point(-1, 1) };

	final Figure.Point FIGURE_O[] = { new Figure.Point(0, 0),
			new Figure.Point(0, 1), new Figure.Point(-1, 0),
			new Figure.Point(-1, 1) };

	final Figure.Point FIGURE_T[] = { new Figure.Point(0, 0),
			new Figure.Point(-1, 0), new Figure.Point(1, 0),
			new Figure.Point(0, 1) };

	final Figure.Point FIGURE_I[] = { new Figure.Point(0, 0),
			new Figure.Point(-1, 0), new Figure.Point(1, 0),
			new Figure.Point(2, 0) };

	/**
	 * The array of colors of figures and background: colorsRGB[0] - the
	 * background color colorsRGB[1]..colorsRGB[7] - the color of L,J,Z,S,O,T,I
	 */
	private int colorsRGB[] = { 0xffffff, 0xc0b6fa, 0xf5f4a7, 0xf4adff,
			0xa3d5ee, 0xffd8b1, 0xa4d9b6, 0xffb4b4 };

	/**
	 * The array of tetramino figures: L,J,Z,S,O,T,I
	 */
	private Figure figures[] = { new Figure(FIGURE_L), new Figure(FIGURE_J),
			new Figure(FIGURE_Z), new Figure(FIGURE_S), new Figure(FIGURE_O),
			new Figure(FIGURE_T), new Figure(FIGURE_I) };

	private int board[][] = new int[SIZE_X][SIZE_Y];

	/**
	 * The index of current figure in array Board.figures[]
	 */
	int curFigureIndex = 0;

	/**
	 * nextFigureIndex - the index of current figure in array Board.figures[]
	 */
	int nextFigureIndex = 0;
	int nextOrientation = 0;

	public void init() {
		for (int i = 0; i < SIZE_X; ++i) {
			for (int j = 0; j < SIZE_Y; ++j) {
				board[i][j] = 0;
			}
		}
		genNextFigure();
		initCurFigure();
	}

	private void genNextFigure() {
		nextFigureIndex = RND.nextInt(figures.length);
		nextOrientation = RND.nextInt(Figure.COUNT_OF_ORIENTATION);
	}

	private boolean initCurFigure() {
		curFigureIndex = nextFigureIndex;
		if (figures[curFigureIndex].init(nextOrientation, board)) {
			genNextFigure();
			return true;
		}
		return false;
	}

	/**
	 * 
	 * Move down the current figure if it's allowed. Add the current figure to
	 * the board state if the step-down is not allowed. Initialize the next
	 * figure if the step-down is not allowed. The game is over if cannot
	 * initialize the next figure.
	 * 
	 * @return false if the game over
	 */
	public boolean doDown() {
		if (figures[curFigureIndex].down(board)) { // step-down
			return true;
		} else // step-down is not allowed
		{
			// add figure to board
			Figure.Point curPos[] = figures[curFigureIndex].getPos();
			for (Figure.Point p : curPos) {
				board[p.x][p.y] = curFigureIndex + 1;
			}
			// remove the 'full' lines
			for (int curLine = 0; curLine < board.length; ++curLine) {
				int[] line = board[curLine];
				boolean isFull = true;
				for (int value : line) {
					if (value == 0) { // the current cell is empty
						isFull = false;
						break;
					}
				}
				if (isFull) { // the line is 'full'
					// copy upper lines to down
					for (int j = curLine; j < board.length - 1; ++j) {
						System.arraycopy(board[j + 1], 0, board[j], 0,
								board[j].length);
					}
					// decrease the counter because the current line has been
					// replaced
					--curLine;
				}
			}
			// Initialize the next the figure
			return initCurFigure();
		}
	}

	public boolean doMove(ChangePos move) {
		if (move.equals(ChangePos.MOVE_LEFT)) {
			return figures[curFigureIndex].left(board);
		} else if (move.equals(ChangePos.MOVE_RIGHT)) {
			return figures[curFigureIndex].right(board);
		} else if (move.equals(ChangePos.TURN_LEFT)) {
			return figures[curFigureIndex].turnLeft(board);
		}
		return figures[curFigureIndex].turnRight(board);
	}
}
