package BoardGame605;

import java.awt.Graphics;

public class AI extends GameControl {

	public AI(Graphics draw, BoardPanel panel) {
		super(draw, panel);
	}

	public void AITurn(char turn, int pieceX, int pieceY) {
		int[] nextMove = new int[3];
		char[][] copiedArray = new char[BoardPanel.line][BoardPanel.line];
		for (int y = 0; y < BoardPanel.line; y++) {
			for (int x = 0; x < BoardPanel.line; x++) {
				copiedArray[y][x] = pieceArray[y][x];
			}
		}
		nextMove = maxvalue(copiedArray, 1, 0, 0, -500000, 500000);

		drawPiece(nextMove[1], nextMove[0], "AI");
	}

	public int[] maxvalue(char[][] copiedArray, int deep, int inputX, int inputY, int alpha, int beta) {

		int[] nextMove = new int[3];
		if (deep == 0) {
			nextMove[0] = inputY;
			nextMove[1] = inputX;

			nextMove[2] = super.findScore(copiedArray);
			return nextMove;
		}

		int totalScore = 0;
		int maxScore = -100000;
		for (int y = 0; y < BoardPanel.line; y++) {
//			System.out.println("y: " + y);
			for (int x = 0; x < BoardPanel.line; x++) {
//				System.out.println("x: " + x);
				if (!Character.isLetter(copiedArray[y][x])) {
					copiedArray[y][x] = 'G';
					totalScore = minvalue(copiedArray, deep - 1, x, y, alpha, beta)[2];
					maxScore = Math.max(maxScore, totalScore);
					if (totalScore == maxScore) {
						nextMove[0] = y;
						nextMove[1] = x;
						nextMove[2] = maxScore;
					}
					if (maxScore >= beta) {
						copiedArray[y][x] = ' ';
						return nextMove;
					}
					alpha = Math.max(alpha, maxScore);
					copiedArray[y][x] = ' ';
				}

			}
		}
		return nextMove;
	}

	public int[] minvalue(char[][] copiedArray, int deep, int inputX, int inputY, int alpha, int beta) {
		int[] nextMove = new int[3];
		if (deep == 0) {
			nextMove[0] = inputY;
			nextMove[1] = inputX;
//			System.out.println("findScore");
			nextMove[2] = super.findScore(copiedArray);
			return nextMove;
		}
		int totalScore = 0;
		int minScore = 100000;

		for (int y = 0; y < BoardPanel.line; y++) {
			for (int x = 0; x < BoardPanel.line; x++) {
				if (!Character.isLetter(copiedArray[y][x])) {
					copiedArray[y][x] = 'B';
					totalScore = maxvalue(copiedArray, deep - 1, x, y, alpha, beta)[2];
					minScore = Math.min(minScore, totalScore);
					if (totalScore == minScore) {
						nextMove[0] = y;
						nextMove[1] = x;
						nextMove[2] = minScore;
					}
					if (minScore <= alpha) {
						copiedArray[y][x] = ' ';
						return nextMove;
					}
					beta = Math.min(beta, minScore);
					copiedArray[y][x] = ' ';
				}

			}
		}
		return nextMove;
	}
}
