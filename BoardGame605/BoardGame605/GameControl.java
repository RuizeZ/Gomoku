package BoardGame605;

import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

import javax.swing.JOptionPane;

public class GameControl {

	protected static Graphics draw = null;
	protected static char[][] pieceArray = new char[BoardPanel.line][BoardPanel.line];
	protected boolean willWin = false;
	protected static boolean win = false;
	public static int pieceSize = 40;
	protected static int[][] recentPlay = new int[2][2];
	protected boolean changeColor = false;
	protected static char turn = 'B';
	protected static BoardPanel gameBoardPanel = null;
	static int score = 0;
	public static boolean AITurn = false;
	HashMap<String, Integer> scoreHash;
	// set hashmap to determine score

	public GameControl(Graphics draw, BoardPanel panel) {

		GameControl.draw = draw;
		GameControl.gameBoardPanel = panel;
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

	// draw a piece on the board
	protected void drawPiece(int pieceX, int pieceY, String player) {
		// TODO Auto-generated method stub

		if (!win) {
			if (pieceX < BoardPanel.line && pieceY < BoardPanel.line) {
				if (pieceArray[pieceY][pieceX] != 'B' && pieceArray[pieceY][pieceX] != 'G') { 

					draw.fillOval(pieceX * BoardPanel.size + BoardPanel.x0 - pieceSize / 2,
							pieceY * BoardPanel.size + BoardPanel.y0 - pieceSize / 2, pieceSize, pieceSize);
					if (player == "human") {
						GameUI.timer.stop();
						GameUI.second = 16;
						AITurn = true;
						recentPlay[0][0] = pieceY;
						recentPlay[0][1] = pieceX;
					} else {
						AITurn = false;
						recentPlay[1][0] = pieceY;
						recentPlay[1][1] = pieceX;
					}

					changeColor = true;
				}
			}

		}
		changeTurn(pieceX, pieceY);
	}

	// After draw a piece, change turn
	private void changeTurn(int pieceX, int pieceY) {
		if (changeColor == true) {
			changeColor = false;
			if (turn == 'B') {
				pieceArray[pieceY][pieceX] = 'B';
				draw.setColor(Color.GRAY);
				turn = 'G';
			} else {
				pieceArray[pieceY][pieceX] = 'G';
				draw.setColor(Color.BLACK);
				turn = 'B';
				GameUI.timer.start();
			}

			win = checkWin(pieceArray, pieceX, pieceY, false, ' ');
			// check result
			if (win) {
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

	// check current board if win
	public boolean checkWin(char[][] theArray, int pieceX, int pieceY, Boolean AI, char scoreColor) {
		// TODO Auto-generated method stub
		String horizontalKey = "" + scoreColor;
		String verticalKey = "" + scoreColor;
		String uprightKey = "" + scoreColor;
		String upLeftKey = "" + scoreColor;
		char[][] currentArray = theArray;
		char color = currentArray[pieceY][pieceX];
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

//			if (horizontalSameColor == 5 || verticalSameColor == 5 || uprightSameColor == 5 || upLeftSameColor == 5) {
//				score += 100000;
//			}
//			if (horizontalSameColor == 4 || verticalSameColor == 4 || uprightSameColor == 4 || upLeftSameColor == 4) {
//				score += 1000;
//			}
//			if (horizontalSameColor == 3 || verticalSameColor == 3 || uprightSameColor == 3 || upLeftSameColor == 3) {
//				score += 100;
//			}
//			if (horizontalSameColor == 2 || verticalSameColor == 2 || uprightSameColor == 2 || upLeftSameColor == 2) {
//				score += 20;
//			}
		}

		return false;
	}

	public int findScore(char[][] copiedArray) {
		int totalScore = 0;
		for (int y = 0; y < BoardPanel.line; y++) {
			for (int x = 0; x < BoardPanel.line; x++) {

				if (copiedArray[y][x] == 'B') {

					checkWin(copiedArray, x, y, true, 'B');
					totalScore += score;
				} else if (copiedArray[y][x] == 'G') {

					checkWin(copiedArray, x, y, true, 'G');

					totalScore += score;
				}
			}
		}

		return totalScore;
	}

	// restart the game
	public static void restartGame() {
		pieceArray = new char[BoardPanel.line][BoardPanel.line];
		BoardPanel.setnewGame(true);
		gameBoardPanel.repaint();
		win = false;
		turn = 'B';
		draw.setColor(Color.BLACK);
		GameUI.second = 16;
	}

}
