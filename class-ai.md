---
description: This class inherences GameControl class. It handles the AI's action
---

# Class AI

## Class Declaration and Data Field

```java
public class AI extends GameControl {

	public AI(Graphics draw, BoardPanel panel) {
		super(draw, panel);
	}
```

## Methods

* AITurn\(\): find next move and draw the piece

```java
	public void AITurn(char turn, int pieceX, int pieceY) {
		//[y-axis, x-axis, score]
		int[] nextMove = new int[3];
		// a copy of pieceArray
		char[][] copiedArray = new char[BoardPanel.line][BoardPanel.line];
		// deep copy of pieceArray
		for (int y = 0; y < BoardPanel.line; y++) {
			for (int x = 0; x < BoardPanel.line; x++) {
				copiedArray[y][x] = pieceArray[y][x];
			}
		}
		//maxvalue returns a int array
		nextMove = maxvalue(copiedArray, 1, 0, 0, -500000, 500000);
		// draw the piece as AI
		drawPiece(nextMove[1], nextMove[0], "AI");
	}
```

* maxvalue\(\): consider all possible moves, find the move with max score
* findScoreperMove: call findScore method find score for each move 

```java
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
		// find all possible move
		for (int y = 0; y < BoardPanel.line; y++) {
			for (int x = 0; x < BoardPanel.line; x++) {
				if (!Character.isLetter(copiedArray[y][x])) {
					copiedArray[y][x] = 'G';
					totalScore = findScoreperMove(copiedArray, x, y)[2];
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

	public int[] findScoreperMove(char[][] copiedArray, int inputX, int inputY) {
		int[] nextMove = new int[3];
		nextMove[0] = inputY;
		nextMove[1] = inputX;
		nextMove[2] = super.findScore(copiedArray);
		return nextMove;
	} 
```

