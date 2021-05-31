package BoardGame524;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.sun.java.swing.action.ExitAction;

public class GameMouse implements MouseListener, MouseMotionListener {
	private Graphics draw = null;
	private BoardPanel panel = null;
	public static int pieceSize = 40;
	public static String turn = "BLACK";
	int pieceX = -1, pieceY = -1;
	private static char[][] pieceArray = new char[BoardPanel.line][BoardPanel.line];
	private boolean win = false;

	public GameMouse(Graphics draw, BoardPanel panel) {
		this.draw = draw;
		this.panel = panel;
	}

	public static char[][] getPieceArray() {
		return pieceArray;
	}

	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		boolean changeColor = false;
		setPieceXY(x, y);
		System.out.println("1");
		if (pieceX != -1 && pieceY != -1 && !win) {
			if (pieceX < BoardPanel.line && pieceY < BoardPanel.line) {
				if (pieceArray[pieceY][pieceX] != 'B' && pieceArray[pieceY][pieceX] != 'G') {
					draw.fillOval(pieceX * BoardPanel.size + BoardPanel.x0 - pieceSize / 2,
							pieceY * BoardPanel.size + BoardPanel.y0 - pieceSize / 2, pieceSize, pieceSize);
					draw.drawOval(pieceX * BoardPanel.size + BoardPanel.x0 - pieceSize / 2,
							pieceY * BoardPanel.size + BoardPanel.y0 - pieceSize / 2, pieceSize, pieceSize);
					changeColor = true;
				}
			}

		}
		if (changeColor == true) {
			if (turn.equals("BLACK")) {
				pieceArray[pieceY][pieceX] = 'B';
				draw.setColor(Color.GRAY);
				turn = "GRAY";
			} else {
				pieceArray[pieceY][pieceX] = 'G';
				draw.setColor(Color.BLACK);
				turn = "BLACK";
			}
			win = checkWin();
			if (win) {
				int n;
				String[] answer = { "Play Again", "Exit" };
				JFrame result = new JFrame();
				result.setSize(400, 300);
				result.setLocationRelativeTo(null);
				if (turn.equals("GRAY")) {
					n = JOptionPane.showOptionDialog(result, "BLACK win!", "Win", JOptionPane.DEFAULT_OPTION,
							JOptionPane.PLAIN_MESSAGE, null, answer, answer[0]);
					result.setVisible(true);
				} else {
					n = JOptionPane.showOptionDialog(result, "GRAY win!", "Win", JOptionPane.DEFAULT_OPTION,
							JOptionPane.PLAIN_MESSAGE, null, answer, answer[0]);
					result.setVisible(true);
				}
				if(n == 0) {
					System.out.println(n);
					pieceArray = new char[BoardPanel.line][BoardPanel.line];
					panel.repaint();
					win = false;
				}else {
					System.exit(0);
				}
				

			}
		}

	}

	private boolean checkWin() {
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

	private void setPieceXY(int x, int y) {
		// TODO Auto-generated method stub
		if ((x - BoardPanel.x0) % BoardPanel.size < pieceSize / 3) {
			pieceX = (x - BoardPanel.x0) / BoardPanel.size;
		} else if ((x - BoardPanel.x0) % BoardPanel.size > 2 * pieceSize / 3) {
			pieceX = (x - BoardPanel.x0) / BoardPanel.size + 1;
		} else {
			pieceX = -1;
		}
		if ((y - BoardPanel.y0) % BoardPanel.size < pieceSize / 3) {
			pieceY = (y - BoardPanel.y0) / BoardPanel.size;
		} else if ((y - BoardPanel.y0) % BoardPanel.size > 2 * pieceSize / 3) {
			pieceY = (y - BoardPanel.y0) / BoardPanel.size + 1;
		} else {
			pieceY = -1;
		}
	}

	public void mousePressed(MouseEvent e) {

	}

	/**
	 * Invoked when a mouse button has been released on a component.
	 * 
	 * @param e the event to be processed
	 */
	public void mouseReleased(MouseEvent e) {

	}

	/**
	 * Invoked when the mouse enters a component.
	 * 
	 * @param e the event to be processed
	 */
	public void mouseEntered(MouseEvent e) {
	}

	/**
	 * Invoked when the mouse exits a component.
	 * 
	 * @param e the event to be processed
	 */
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = e.getX();
		int y = e.getY();
		setPieceXY(x, y);
		if (pieceX != -1 && pieceY != -1 && !win) {
			if (pieceX < BoardPanel.line && pieceY < BoardPanel.line) {
				draw.drawOval(pieceX * BoardPanel.size + BoardPanel.x0 - pieceSize / 2,
						pieceY * BoardPanel.size + BoardPanel.y0 - pieceSize / 2, pieceSize, pieceSize);
			}

		} else {
			panel.repaint();
		}
	}

}
