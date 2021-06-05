package BoardGame605;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class BoardPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int x0 = 40, y0 = 25, size = 50, line = 15;
	private char[][] pieceArray = GameControl.pieceArray;
	private static boolean newGame = false;

	@Override
	public void paint(Graphics g) {
		
		pieceArray = GameControl.pieceArray;
		// TODO Auto-generated method stub
		super.paintComponent(g);
		ImageIcon icon = new ImageIcon("pic\\background.jpg");
		g.drawImage(icon.getImage(), 0, 0, 800, 800, null);
		for (int i = 0; i < line; i++) {
			// horizontal line
			g.drawLine(x0, y0 + i * size, (line - 1) * size + x0, y0 + i * size);
			// vertical line
			g.drawLine(x0 + i * size, y0, x0 + i * size, (line - 1) * size + y0);
		}
		if (!newGame) {
			for (int i = 0; i < pieceArray.length; i++) {
				for (int j = 0; j < pieceArray[0].length; j++) {
					if (pieceArray[i][j] == 'G') {
						g.setColor(Color.GRAY);
						g.fillOval(j * BoardPanel.size + BoardPanel.x0 - GameMouse.pieceSize / 2,
								i * BoardPanel.size + BoardPanel.y0 - GameMouse.pieceSize / 2, GameMouse.pieceSize,
								GameControl.pieceSize);
					} else if (pieceArray[i][j] == 'B') {
						g.setColor(Color.BLACK);
						g.fillOval(j * BoardPanel.size + BoardPanel.x0 - GameMouse.pieceSize / 2,
								i * BoardPanel.size + BoardPanel.y0 - GameMouse.pieceSize / 2, GameMouse.pieceSize,
								GameControl.pieceSize);
					} else if (pieceArray[i][j] == 'R') {
						g.setColor(Color.RED);
						g.fillOval(j * BoardPanel.size + BoardPanel.x0 - GameMouse.pieceSize / 2,
								i * BoardPanel.size + BoardPanel.y0 - GameMouse.pieceSize / 2, GameMouse.pieceSize,
								GameControl.pieceSize);
					}
				}
			}
		} else {
			newGame = false;
		}

	}

	public static void setnewGame(boolean newGame) {
		BoardPanel.newGame = newGame;
	}
	public static boolean getnewGame() {
		return BoardPanel.newGame;
	}
	
}