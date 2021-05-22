package BoardGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class GameMouse implements MouseListener, MouseMotionListener {
	 private Graphics draw = null;
	private static int pieceSize = 50;
	public static String turn = "BLACK";

	public GameMouse(Graphics draw) {
		this.draw = draw;
	}

	public static int getPieceSize() {
		return pieceSize;
	}

	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		int xIndex = 0;
		int yIndex = 0;
		boolean changeColor = false;
		System.out.println(x);
		System.out.println(y);

		xIndex = (x - 25) / 60;
		yIndex = (y - 25) / 60;
		if (Math.abs(x - xIndex * 60 - 25) < 25 && Math.abs(y - yIndex * 60 - 25) < 25) {
			x = 25 + xIndex * 60;
			y = 25 + yIndex * 60;
			draw.fillOval(x - pieceSize / 2, y - pieceSize / 2, pieceSize, pieceSize);
			changeColor = true;
		} else if (Math.abs(x - xIndex * 60 - 25) > 35 && Math.abs(y - yIndex * 60 - 25) > 35) {
			x = 25 + (xIndex + 1) * 60;
			y = 25 + (yIndex + 1) * 60;
			draw.fillOval(x - pieceSize / 2, y - pieceSize / 2, pieceSize, pieceSize);
			changeColor = true;
		} else if (Math.abs(x - xIndex * 60 - 25) < 25 && Math.abs(y - yIndex * 60 - 25) > 35) {
			x = 25 + (xIndex) * 60;
			y = 25 + (yIndex + 1) * 60;
			draw.fillOval(x - pieceSize / 2, y - pieceSize / 2, pieceSize, pieceSize);
			changeColor = true;
		} else if (Math.abs(x - xIndex * 60 - 25) > 35 && Math.abs(y - yIndex * 60 - 25) < 25) {
			x = 25 + (xIndex + 1) * 60;
			y = 25 + (yIndex) * 60;
			draw.fillOval(x - pieceSize / 2, y - pieceSize / 2, pieceSize, pieceSize);
			changeColor = true;
		}
		if (changeColor == true) {
			if (turn.equals("BLACK")) {
				draw.setColor(Color.BLUE);
				turn = "BLUE";
			} else {
				draw.setColor(Color.BLACK);
				turn = "BLACK";
			}
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
		int xIndex = 0;
		int yIndex = 0;
		xIndex = (x - 25) / 60;
		yIndex = (y - 25) / 60;
		if (Math.abs(x - xIndex * 60 - 25) < 25 && Math.abs(y - yIndex * 60 - 25) < 25) {
			x = 25 + xIndex * 60;
			y = 25 + yIndex * 60;
			draw.drawOval(x - pieceSize / 2, y - pieceSize / 2, pieceSize, pieceSize);
		} else if (Math.abs(x - xIndex * 60 - 25) > 35 && Math.abs(y - yIndex * 60 - 25) > 35) {
			x = 25 + (xIndex + 1) * 60;
			y = 25 + (yIndex + 1) * 60;
			draw.drawOval(x - pieceSize / 2, y - pieceSize / 2, pieceSize, pieceSize);
		} else if (Math.abs(x - xIndex * 60 - 25) < 25 && Math.abs(y - yIndex * 60 - 25) > 35) {
			x = 25 + (xIndex) * 60;
			y = 25 + (yIndex + 1) * 60;
			draw.drawOval(x - pieceSize / 2, y - pieceSize / 2, pieceSize, pieceSize);
		} else if (Math.abs(x - xIndex * 60 - 25) > 35 && Math.abs(y - yIndex * 60 - 25) < 25) {
			x = 25 + (xIndex + 1) * 60;
			y = 25 + (yIndex) * 60;
			draw.drawOval(x - pieceSize / 2, y - pieceSize / 2, pieceSize, pieceSize);
		}

	}
}
