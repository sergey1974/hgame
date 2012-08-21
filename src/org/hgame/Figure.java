package org.hgame;

public class Figure {
	private static final int MAX_FIGURE_SIZE = 4;
	private static final int MAX_FIRST_DOWN = 4;
	public static final int COUNT_OF_ORIENTATION = 4;

	static public class Point {
		int x = 0;
		int y = 0;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public Point turn() {
			return new Point(y, -x);
		}
	}

	private int curOrientation = 0;
	private Point orientations[][] = new Point[COUNT_OF_ORIENTATION][];
	private Point curCenterPoint = new Point(Board.SIZE_X / 2, 0);
	private Point curPos[] = new Point[MAX_FIGURE_SIZE];

	Figure(Point piece0[]) {
		orientations[0] = piece0;
		for (int i = 1; i < COUNT_OF_ORIENTATION; ++i) {
			orientations[i] = new Point[orientations[i].length];
			for (int j = 0; j < orientations[i].length; ++j) {
				orientations[i][j] = orientations[i - 1][j].turn();
			}
		}
	}

	boolean init(int orientation, int board[][]) {
		curOrientation = orientation;
		curCenterPoint.x = Board.SIZE_X / 2;
		curCenterPoint.y = 0;
		while (curCenterPoint.y < MAX_FIRST_DOWN && !isPosAllowed(board)) {
			++curCenterPoint.y;
		}

		return curCenterPoint.y < MAX_FIRST_DOWN;
	}

	boolean down(int board[][]) {
		++curCenterPoint.y;
		if (isPosAllowed(board)) {
			return true;
		}
		--curCenterPoint.y;
		return false;
	}

	boolean left(int board[][]) {
		--curCenterPoint.x;
		if (isPosAllowed(board)) {
			return true;
		}
		++curCenterPoint.x;
		return false;
	}

	boolean right(int board[][]) {
		++curCenterPoint.x;
		if (isPosAllowed(board)) {
			return true;
		}
		--curCenterPoint.x;
		return false;
	}

	boolean turnLeft(int board[][]) {
		curOrientation = ((curOrientation - 1) + COUNT_OF_ORIENTATION)
				% COUNT_OF_ORIENTATION;
		if (isPosAllowed(board)) {
			return true;
		}
		curOrientation = ((curOrientation + 1) + COUNT_OF_ORIENTATION)
				% COUNT_OF_ORIENTATION;
		return false;
	}

	boolean turnRight(int board[][]) {
		curOrientation = ((curOrientation + 1) + COUNT_OF_ORIENTATION)
				% COUNT_OF_ORIENTATION;
		if (isPosAllowed(board)) {
			return true;
		}
		curOrientation = ((curOrientation - 1) + COUNT_OF_ORIENTATION)
				% COUNT_OF_ORIENTATION;
		return false;
	}

	Point[] getPos() {
		for (int i = 0; i < orientations[curOrientation].length; ++i) {
			Point p = orientations[curOrientation][i];
			curPos[i].x = p.x + curCenterPoint.x;
			curPos[i].y = p.y + curCenterPoint.y;
		}
		return curPos;
	}

	private boolean isPosAllowed(int[][] board) {
		for (Point p : orientations[curOrientation]) {
			int indX = p.x + curCenterPoint.x;
			int indY = p.y + curCenterPoint.y;
			if (0 <= indX && indX < board.length && 0 <= indY
					&& indY < board[indX].length && board[indX][indY] != 0) {
				return false;
			}
		}
		return true;
	}

}
