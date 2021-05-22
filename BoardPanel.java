package BoardGame;

import java.awt.Graphics;

import javax.swing.JPanel;

public class BoardPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int pieceSize = GameMouse.getPieceSize();

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		// g.drawRect(pieceSize/2, pieceSize/2,780 - pieceSize, 760 - pieceSize);
		for (int i = pieceSize / 2; i < 800; i += pieceSize + 10) {
			g.drawLine(i, pieceSize / 2, i, 745);
//			System.out.println(i);
//			System.out.println(pieceSize / 2);
		}
		for (int j = pieceSize / 2; j < 800; j += pieceSize + 10) {
			g.drawLine(pieceSize / 2, j, 745, j);
		}
	}
}