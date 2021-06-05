package BoardGame605;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JButton;

public class GameMouse extends GameControl implements MouseListener, MouseMotionListener, ActionListener {
	int pieceX = -1, pieceY = -1;
	private JButton redoButton, reStartButton;
	private AI AIPlayer = null;

	public GameMouse(Graphics draw, BoardPanel panel, JButton redoButton, JButton reStartButton, AI AIPlayer) {
		super(draw, panel);
		this.redoButton = redoButton;
		this.reStartButton = reStartButton;
		this.AIPlayer = AIPlayer;
	}

	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		setPieceXY(x, y);
		if(pieceX != -1 && pieceY != -1) {
			drawPiece(pieceX, pieceY, "human");
			if (!BoardPanel.getnewGame()) {
				AIPlayer.AITurn(turn, pieceX, pieceY);
			}
			
		}
		
		
		
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
			gameBoardPanel.repaint();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == reStartButton) {
			restartGame();
		} else if (e.getSource() == redoButton) {
			pieceArray[recentPlay[0][0]][recentPlay[0][1]] = ' ';
			pieceArray[recentPlay[1][0]][recentPlay[1][1]] = ' ';
			turn = "BLACK";
			draw.setColor(Color.BLACK);
			gameBoardPanel.repaint();
		}

	}

}
