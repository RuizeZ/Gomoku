package BoardGame605;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class GameControl {
	protected Graphics draw = null;
	protected static char[][] pieceArray = new char[BoardPanel.line][BoardPanel.line];
	protected boolean win = false;
	public static int pieceSize = 40;
	protected static int[][] recentPlay = new int[2][2];
	protected boolean changeColor = false;
	protected static String turn = "BLACK";
	protected BoardPanel gameBoardPanel = null;

	public GameControl(Graphics draw, BoardPanel panel) {

		this.draw = draw;
		this.gameBoardPanel = panel;
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
						recentPlay[0][0] = pieceY;
						recentPlay[0][1] = pieceX;
					} else {
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
			if (turn.equals("BLACK")) {
				pieceArray[pieceY][pieceX] = 'B';
				draw.setColor(Color.GRAY);
				turn = "GRAY";
			} else {
				pieceArray[pieceY][pieceX] = 'G';
				draw.setColor(Color.BLACK);
				turn = "BLACK";
			}

			win = checkWin(pieceX, pieceY);
			// check result
			if (win) {
				int n;
				String[] answer = { "Play Again", "Exit" };
				if (turn.equals("GRAY")) {
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
	private boolean checkWin(int pieceX, int pieceY) {
		// TODO Auto-generated method stub

		char color = pieceArray[pieceY][pieceX];
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
		for (int i = 1; i < 5; i++) {
			// horizontal
			if (pieceX + i < pieceArray[pieceY].length) {
				if (pieceArray[pieceY][pieceX + i] == color && !stopRight) {
					horizontalWinPosition.add(new int[] { pieceY, pieceX + i });
					horizontalSameColor++;
				} else {
					stopRight = true;
				}
			}
			if (pieceX - i >= 0) {
				if (pieceArray[pieceY][pieceX - i] == color && !stopLeft) {
					horizontalWinPosition.add(new int[] { pieceY, pieceX - i });
					horizontalSameColor++;
				} else {
					stopLeft = true;
				}
			}
			if (horizontalSameColor == 5) {
				draw.setColor(Color.RED);
				for (int j = 0; j < 5; j++) {
					int y = horizontalWinPosition.get(j)[0];
					int x = horizontalWinPosition.get(j)[1];
					pieceArray[y][x] = 'R';
					draw.fillOval(x * BoardPanel.size + BoardPanel.x0 - pieceSize / 2,
							y * BoardPanel.size + BoardPanel.y0 - pieceSize / 2, pieceSize, pieceSize);
				}
				return true;
			}
			// vertical
			if (pieceY + i < pieceArray.length) {
				if (pieceArray[pieceY + i][pieceX] == color && !stopDown) {
					verticalWinPosition.add(new int[] { pieceY + i, pieceX });
					verticalSameColor++;
				} else {
					stopDown = true;
				}
			}
			if (pieceY - i >= 0) {
				if (pieceArray[pieceY - i][pieceX] == color && !stopTop) {
					verticalWinPosition.add(new int[] { pieceY - i, pieceX });
					verticalSameColor++;
				} else {
					stopTop = true;
				}
			}
			if (verticalSameColor == 5) {
				draw.setColor(Color.RED);
				for (int j = 0; j < 5; j++) {
					int y = verticalWinPosition.get(j)[0];
					int x = verticalWinPosition.get(j)[1];
					pieceArray[y][x] = 'R';
					draw.fillOval(x * BoardPanel.size + BoardPanel.x0 - pieceSize / 2,
							y * BoardPanel.size + BoardPanel.y0 - pieceSize / 2, pieceSize, pieceSize);
				}
				return true;
			}
			// upright
			if (pieceX + i < pieceArray[pieceY].length && pieceY - i >= 0) {
				if (pieceArray[pieceY - i][pieceX + i] == color && !stopTopRight) {
					uprightWinPosition.add(new int[] { pieceY - i, pieceX + i });
					uprightSameColor++;
				} else {
					stopTopRight = true;
				}
			}
			if (pieceX - i >= 0 && pieceY + i < pieceArray.length) {
				if (pieceArray[pieceY + i][pieceX - i] == color && !stopDownLeft) {
					uprightWinPosition.add(new int[] { pieceY + i, pieceX - i });
					uprightSameColor++;
				} else {
					stopDownLeft = true;
				}
			}
			if (uprightSameColor == 5) {
				draw.setColor(Color.RED);
				for (int j = 0; j < 5; j++) {
					int y = uprightWinPosition.get(j)[0];
					int x = uprightWinPosition.get(j)[1];
					pieceArray[y][x] = 'R';
					draw.fillOval(x * BoardPanel.size + BoardPanel.x0 - pieceSize / 2,
							y * BoardPanel.size + BoardPanel.y0 - pieceSize / 2, pieceSize, pieceSize);
				}
				return true;
			}
			// upLeft
			if (pieceX - i >= 0 && pieceY - i >= 0) {
				if (pieceArray[pieceY - i][pieceX - i] == color && !stopTopLeft) {
					upLeftWinPosition.add(new int[] { pieceY - i, pieceX - i });
					upLeftSameColor++;
				} else {
					stopTopLeft = true;
				}
			}
			if (pieceX + i < pieceArray[pieceY].length && pieceY + i < pieceArray.length) {
				if (pieceArray[pieceY + i][pieceX + i] == color && !stopDownRight) {
					upLeftWinPosition.add(new int[] { pieceY + i, pieceX + i });
					upLeftSameColor++;
				} else {
					stopDownRight = true;
				}
			}
			if (upLeftSameColor == 5) {
				draw.setColor(Color.RED);
				for (int j = 0; j < 5; j++) {
					int y = upLeftWinPosition.get(j)[0];
					int x = upLeftWinPosition.get(j)[1];
					pieceArray[y][x] = 'R';
					draw.fillOval(x * BoardPanel.size + BoardPanel.x0 - pieceSize / 2,
							y * BoardPanel.size + BoardPanel.y0 - pieceSize / 2, pieceSize, pieceSize);
				}
				return true;
			}

		}

		return false;
	}

	// restart the game
	protected void restartGame() {
		pieceArray = new char[BoardPanel.line][BoardPanel.line];
		BoardPanel.setnewGame(true);
		gameBoardPanel.repaint();
		win = false;
		turn = "BLACK";
		draw.setColor(Color.BLACK);
	}

}
