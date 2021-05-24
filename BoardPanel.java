package BoardGame524;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class BoardPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int x0 = 40, y0 = 25, size = 50, line = 15;
	private char[][] pieceArray = GameMouse.getPieceArray();

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		for (int i = 0; i < line; i++) {
			// horizontal line
			g.drawLine(x0, y0 + i * size, (line - 1) * size + x0, y0 + i * size);
			// vertical line
			g.drawLine(x0 + i * size, y0, x0 + i * size, (line - 1) * size + y0);
		}
		for (int i = 0; i < pieceArray.length; i++) {
			for (int j = 0; j < pieceArray[0].length; j++) {
				if (pieceArray[i][j] == 'G') {
					g.setColor(Color.GRAY);
					g.fillOval(j * BoardPanel.size + BoardPanel.x0 - GameMouse.pieceSize / 2,
							i * BoardPanel.size + BoardPanel.y0 - GameMouse.pieceSize / 2, GameMouse.pieceSize,
							GameMouse.pieceSize);
				} else if (pieceArray[i][j] == 'B') {
					g.setColor(Color.BLACK);
					g.fillOval(j * BoardPanel.size + BoardPanel.x0 - GameMouse.pieceSize / 2,
							i * BoardPanel.size + BoardPanel.y0 - GameMouse.pieceSize / 2, GameMouse.pieceSize,
							GameMouse.pieceSize);
				}
			}
		}

	}
}