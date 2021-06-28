---
description: >-
  This class handles drawing stones for each player, deciding winner, changing
  turn and restart the game.
---

# Class GameControl

## Class Declaration and Data Field

```java
public class GameControl {
	// Graphics object draw passed from class GameUI
	protected static Graphics draw = null;
	// pieceArray records the location for each peice
	protected static char[][] pieceArray = new char[BoardPanel.line][BoardPanel.line];
	// Flag for if the current board has a winner
	protected static boolean win = false;
	//Diameter for each piece
	public static int pieceSize = 40;
	//recentPlay records latest two peices that have been played, it is for redo function
	protected static int[][] recentPlay = new int[2][2];
	//flag for if needs to change turn
	protected boolean changeColor = false;
	// turn shows the current player, the default starter is Black
	protected static char turn = 'B';
	// gameBoardPanel where all the piece are placed
	protected static BoardPanel gameBoardPanel = null;
	// the score of current board
	static int score = 0;
	// flag for if this is a AI tuen
	public static boolean AITurn = false;
	// HashMap to set score for each board
	HashMap<String, Integer> scoreHash;
	...
	}
```

## Constructor

```java
// the constructor requires two parameters. draw is a Graphics object and is uesed
// to draw pieces. panel is where all the pieces are been drew 
public GameControl(Graphics draw, BoardPanel panel) {
		GameControl.draw = draw;
		GameControl.gameBoardPanel = panel;
		// String is the char representation of current board, and Integer is the score
		// for certain board
		scoreHash = new HashMap<String, Integer>();
		scoreHash.put("B", 0);
		scoreHash.put("BB", -100);
		scoreHash.put("BBB", -200);
		scoreHash.put("BBBB", -400);
		scoreHash.put("BBBBB", -800);
		scoreHash.put("BG", 10);
		scoreHash.put("BBG", 100);
		scoreHash.put("BBBG", 400);
		scoreHash.put("BBBBG", -200);
		scoreHash.put("BBBBBG", -800);
		scoreHash.put("GB", 10);
		scoreHash.put("GBB", 100);
		scoreHash.put("GBBB", 400);
		scoreHash.put("GBBBB", -200);
		scoreHash.put("GBBBBB", -800);
		scoreHash.put("GBG", 0);
		scoreHash.put("GBBG", 50);
		scoreHash.put("GBBBG", 200);
		scoreHash.put("GBBBBG", 500);
		scoreHash.put("GBBBBBG", -800);
		scoreHash.put("G", 0);
		scoreHash.put("GG", 100);
		scoreHash.put("GGG", 200);
		scoreHash.put("GGGG", 400);
		scoreHash.put("GGGGG", 800);
		scoreHash.put("GB", 10);
		scoreHash.put("GGB", 50);
		scoreHash.put("GGGB", 100);
		scoreHash.put("GGGGB", 400);
		scoreHash.put("GGGGGB", 800);
		scoreHash.put("BG", 10);
		scoreHash.put("BGG", 50);
		scoreHash.put("BGGG", 100);
		scoreHash.put("BGGGG", 400);
		scoreHash.put("BGGGGG", 800);
		scoreHash.put("BGB", -50);
		scoreHash.put("BGGB", -100);
		scoreHash.put("BGGGB", -200);
		scoreHash.put("BGGGGB", -400);
		scoreHash.put("BGGGGGB", 800);

	}
```

The HashMap is from AI's perspective. Positive value means the current board is good for AI to win, so AI will more likely to play that way. For example, if the current board is "GGGGB", this is good for AI because it only needs one more piece to win. A negative number means the current board is bad for AI, AI has a bigger change to lose the game, so AI will avoid to form this kind of board. For example, "BBBBBG" is -800, this will cause AI to lose the game, therefore, AI will more likely to play "GBBBBG" to earn more point and avoid lose.

## Methods

* drawPiece\(\): draw a piece on the board and check if it has a winner

```java
// draw a piece on the board, pieceX, pieceY is the x-axis and y-axis of the piece
// to draw; player is the color of current turn
	protected void drawPiece(int pieceX, int pieceY, String player) 
		//if the current board does not have a winner, the penal allows to draw
		if (!win) {
			// if the x and y are legal positive
			if (pieceX < BoardPanel.line && pieceY < BoardPanel.line) {
				// if this position in array is empty i.e. a piece can place at this position
				if (pieceArray[pieceY][pieceX] != 'B' && pieceArray[pieceY][pieceX] != 'G') { 
					//draw the peice
					draw.fillOval(pieceX * BoardPanel.size + BoardPanel.x0 - pieceSize / 2,
							pieceY * BoardPanel.size + BoardPanel.y0 - pieceSize / 2, pieceSize, pieceSize);
					// if the current player is human, after the peice has been placed, stop the clock
					// and reset it to 16 second
					if (player == "human") {
						GameUI.timer.stop();
						GameUI.second = 16;
						// change turen to AI
						AITurn = true;
						//record this piece to the recentPlay
						recentPlay[0][0] = pieceY;
						recentPlay[0][1] = pieceX;
					} else {
						AITurn = false;
						//record this piece to the recentPlay
						recentPlay[1][0] = pieceY;
						recentPlay[1][1] = pieceX;
					}
					//set change color to true
					changeColor = true;
				}
			}

		}
		changeTurn(pieceX, pieceY);
	}
```

* changeTurn\(\): change color and check if we have a winner

```java
private void changeTurn(int pieceX, int pieceY) {
		if (changeColor == true) {
			// reset the flag
			changeColor = false;
			if (turn == 'B') {
			// if this is Black turn, record as B in array change color to Gray and change
			// turn to G
				pieceArray[pieceY][pieceX] = 'B';
				draw.setColor(Color.GRAY);
				turn = 'G';
			}
			// if this is Gray turn, record as G in array change color to Black and change
			// turn to B and start the count down
			else {
				pieceArray[pieceY][pieceX] = 'G';
				draw.setColor(Color.BLACK);
				turn = 'B';
				GameUI.timer.start();
			}
			// check winner
			win = checkWin(pieceArray, pieceX, pieceY, false, ' ');
			// check result
			if (win) {
			// if we have a winner, popup window asks user choose two options
				int n;
				String[] answer = { "Play Again", "Exit" };
				GameUI.timer.stop();
				if (turn == 'G') {
					n = JOptionPane.showOptionDialog(null, "BLACK win!", "Win", JOptionPane.DEFAULT_OPTION,
							JOptionPane.PLAIN_MESSAGE, null, answer, answer[0]);
				} else {
					n = JOptionPane.showOptionDialog(null, "GRAY win!", "Win", JOptionPane.DEFAULT_OPTION,
							JOptionPane.PLAIN_MESSAGE, null, answer, answer[0]);
				}
				if (n == 0) {
					restartGame();

				} else {
					System.exit(0);
				}

			}
		}
	}

```

* findScore\(\): Evaluate current board and decided next step for AI

```java
	public int findScore(char[][] copiedArray) {
		int totalScore = 0;
		for (int y = 0; y < BoardPanel.line; y++) {
			for (int x = 0; x < BoardPanel.line; x++) {
				// for each location on the board
				if (copiedArray[y][x] == 'B') {
					// calculate the score for Black
					checkWin(copiedArray, x, y, true, 'B');
					totalScore += score;
				} else if (copiedArray[y][x] == 'G') {
					// calculate the score for Gray
					checkWin(copiedArray, x, y, true, 'G');

					totalScore += score;
				}
			}
		}

		return totalScore;
	}
```

* checkWin\(\): check winner or calculate the score of the board

```java
// check current board if win
//theArray: the curent board; pieceX, pieceY: the current fucosed position; AI: flag 
//indicates this method is for check winner or calculate the score; scoreColor: calculate
//the score of this color
	public boolean checkWin(char[][] theArray, int pieceX, int pieceY, Boolean AI, char scoreColor) {
		// TODO Auto-generated method stub
		// use four string to record the pattern we find on this board, each string is a 
		// direction. Right and left are conbained together in horizontalKey and it is the 
		// same for other three directions.
		String horizontalKey = "" + scoreColor;
		String verticalKey = "" + scoreColor;
		String uprightKey = "" + scoreColor;
		String upLeftKey = "" + scoreColor;
		char[][] currentArray = theArray;
		char color = currentArray[pieceY][pieceX];
		// count the 
		int horizontalSameColor = 1, verticalSameColor = 1, uprightSameColor = 1, upLeftSameColor = 1;
		boolean stopRight = false, stopLeft = false, stopTop = false, stopDown = false, stopTopRight = false,
				stopTopLeft = false, stopDownRight = false, stopDownLeft = false;
		ArrayList<int[]> horizontalWinPosition = new ArrayList<int[]>();
		ArrayList<int[]> verticalWinPosition = new ArrayList<int[]>();
		ArrayList<int[]> uprightWinPosition = new ArrayList<int[]>();
		ArrayList<int[]> upLeftWinPosition = new ArrayList<int[]>();
		horizontalWinPosition.add(new int[] { pieceY, pieceX });
		verticalWinPosition.add(new int[] { pieceY, pieceX });
		uprightWinPosition.add(new int[] { pieceY, pieceX });
		upLeftWinPosition.add(new int[] { pieceY, pieceX });
		if (AI) {
			color = scoreColor;
		}
		for (int i = 1; i < 5; i++) {
			// horizontal
			if (pieceX + i < currentArray[pieceY].length) {
				if (currentArray[pieceY][pieceX + i] == color && !stopRight) {
					horizontalWinPosition.add(new int[] { pieceY, pieceX + i });
					horizontalKey += currentArray[pieceY][pieceX + i];
					horizontalSameColor++;
				} else if (!stopRight) {
					if (Character.isLetter(currentArray[pieceY][pieceX + i])) {
						horizontalKey += currentArray[pieceY][pieceX + i];
					}

					stopRight = true;
				}
			}
			if (pieceX - i >= 0) {
				if (currentArray[pieceY][pieceX - i] == color && !stopLeft) {
					horizontalWinPosition.add(new int[] { pieceY, pieceX - i });
					horizontalKey = currentArray[pieceY][pieceX - i] + horizontalKey;
					horizontalSameColor++;

				} else if (!stopLeft) {
					if (Character.isLetter(currentArray[pieceY][pieceX - i])) {
						horizontalKey = currentArray[pieceY][pieceX - i] + horizontalKey;
					}

					stopLeft = true;
				}
			}
			if (!AI) {
				if (horizontalSameColor == 5) {
					draw.setColor(Color.RED);
					for (int j = 0; j < 5; j++) {
						int y = horizontalWinPosition.get(j)[0];
						int x = horizontalWinPosition.get(j)[1];
						currentArray[y][x] = 'R';
						draw.fillOval(x * BoardPanel.size + BoardPanel.x0 - pieceSize / 2,
								y * BoardPanel.size + BoardPanel.y0 - pieceSize / 2, pieceSize, pieceSize);
					}
					return true;
				}
			}
			// vertical
			if (pieceY + i < currentArray.length) {
				if (currentArray[pieceY + i][pieceX] == color && !stopDown) {
					verticalWinPosition.add(new int[] { pieceY + i, pieceX });
					verticalKey += currentArray[pieceY + i][pieceX];
					verticalSameColor++;
				} else if (!stopDown) {
					if (Character.isLetter(currentArray[pieceY + i][pieceX])) {
						verticalKey += currentArray[pieceY + i][pieceX];
					}
					stopDown = true;
				}
			}
			if (pieceY - i >= 0) {
				if (currentArray[pieceY - i][pieceX] == color && !stopTop) {
					verticalWinPosition.add(new int[] { pieceY - i, pieceX });
					verticalKey = currentArray[pieceY - i][pieceX] + verticalKey;
					verticalSameColor++;
				} else if (!stopTop) {
					if (Character.isLetter(currentArray[pieceY - i][pieceX])) {
						verticalKey = currentArray[pieceY - i][pieceX] + verticalKey;
					}

					stopTop = true;
				}
			}
			if (!AI) {
				if (verticalSameColor == 5) {
					draw.setColor(Color.RED);
					for (int j = 0; j < 5; j++) {
						int y = verticalWinPosition.get(j)[0];
						int x = verticalWinPosition.get(j)[1];
						currentArray[y][x] = 'R';
						draw.fillOval(x * BoardPanel.size + BoardPanel.x0 - pieceSize / 2,
								y * BoardPanel.size + BoardPanel.y0 - pieceSize / 2, pieceSize, pieceSize);
					}
					return true;
				}
			}
			// upright
			if (pieceX + i < currentArray[pieceY].length && pieceY - i >= 0) {
				if (currentArray[pieceY - i][pieceX + i] == color && !stopTopRight) {
					uprightWinPosition.add(new int[] { pieceY - i, pieceX + i });
					uprightKey += currentArray[pieceY - i][pieceX + i];
					uprightSameColor++;
				} else if (!stopTopRight) {
					if (Character.isLetter(currentArray[pieceY - i][pieceX + i])) {
						uprightKey += currentArray[pieceY - i][pieceX + i];
					}
					stopTopRight = true;
				}
			}
			if (pieceX - i >= 0 && pieceY + i < currentArray.length) {
				if (currentArray[pieceY + i][pieceX - i] == color && !stopDownLeft) {
					uprightWinPosition.add(new int[] { pieceY + i, pieceX - i });
					uprightKey = currentArray[pieceY + i][pieceX - i] + uprightKey;
					uprightSameColor++;
				} else if (!stopDownLeft) {
					if (Character.isLetter(currentArray[pieceY + i][pieceX - i])) {
						uprightKey = currentArray[pieceY + i][pieceX - i] + uprightKey;
					}

					stopDownLeft = true;
				}
			}
			if (!AI) {
				if (uprightSameColor == 5) {
					draw.setColor(Color.RED);
					for (int j = 0; j < 5; j++) {
						int y = uprightWinPosition.get(j)[0];
						int x = uprightWinPosition.get(j)[1];
						currentArray[y][x] = 'R';
						draw.fillOval(x * BoardPanel.size + BoardPanel.x0 - pieceSize / 2,
								y * BoardPanel.size + BoardPanel.y0 - pieceSize / 2, pieceSize, pieceSize);
					}
					return true;
				}
			}
			// upLeft
			if (pieceX - i >= 0 && pieceY - i >= 0) {
				if (currentArray[pieceY - i][pieceX - i] == color && !stopTopLeft) {
					upLeftWinPosition.add(new int[] { pieceY - i, pieceX - i });
					upLeftKey += currentArray[pieceY - i][pieceX - i];
					upLeftSameColor++;
				} else if (!stopTopLeft) {
					if (Character.isLetter(currentArray[pieceY - i][pieceX - i])) {
						upLeftKey += currentArray[pieceY - i][pieceX - i];
					}

					stopTopLeft = true;
				}
			}
			if (pieceX + i < currentArray[pieceY].length && pieceY + i < currentArray.length) {
				if (currentArray[pieceY + i][pieceX + i] == color && !stopDownRight) {
					upLeftWinPosition.add(new int[] { pieceY + i, pieceX + i });
					upLeftKey = currentArray[pieceY + i][pieceX + i] + upLeftKey;
					upLeftSameColor++;
				} else if (!stopDownRight) {
					if (Character.isLetter(currentArray[pieceY + i][pieceX + i])) {
						upLeftKey = currentArray[pieceY + i][pieceX + i] + upLeftKey;
					}
					stopDownRight = true;
				}
			}
			if (!AI) {
				if (upLeftSameColor == 5) {
					draw.setColor(Color.RED);
					for (int j = 0; j < 5; j++) {
						int y = upLeftWinPosition.get(j)[0];
						int x = upLeftWinPosition.get(j)[1];
						currentArray[y][x] = 'R';
						draw.fillOval(x * BoardPanel.size + BoardPanel.x0 - pieceSize / 2,
								y * BoardPanel.size + BoardPanel.y0 - pieceSize / 2, pieceSize, pieceSize);
					}
					return true;
				}

			}
		}
		if (AI == true) {
			score = 10;
			if (horizontalKey != "") {
				score += scoreHash.get(horizontalKey);
			}
			if (verticalKey != "") {
				score += scoreHash.get(verticalKey);
			}
			if (uprightKey != "") {
				score += scoreHash.get(uprightKey);
			}
			if (upLeftKey != "") {
				score += scoreHash.get(upLeftKey);
			}

		}

		return false;
	}
```

