---
description: This class inherences GameControl class. It handles the human player's action
---

# Class GameMouse

## Class Declaration and Data Field

```java
public class GameMouse extends GameControl implements MouseListener, MouseMotionListener, ActionListener {
	int pieceX = -1, pieceY = -1;
	// JButton objects controls to burron
	private JButton redoButton, reStartButton;
	private AI AIPlayer = null;

	public GameMouse(Graphics draw, BoardPanel panel, JButton redoButton, JButton reStartButton, AI AIPlayer) {
		super(draw, panel);
		this.redoButton = redoButton;
		this.reStartButton = reStartButton;
		this.AIPlayer = AIPlayer;
	}
	...
	}
```

## Methods

* mouseClicked\(\): draw a piece on the board after mouse click

```java
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		setPieceXY(x, y);
		if (pieceX != -1 && pieceY != -1) {
		// draw piece as human player
			drawPiece(pieceX, pieceY, "human");
			//if it is not a new game and it is human turn
			if (!BoardPanel.getnewGame() && AITurn) {
			//call AITurn method to let AI play
				AIPlayer.AITurn(turn, pieceX, pieceY);
			}

		}

	}
```

* setPieceXY\(\): change mouse location to location on board

```java
	private void setPieceXY(int x, int y) {
		// TODO Auto-generated method stub
		// if mouse clicked at 1/3 range of the point on board, convert this mouse 
		//location to the point location
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
```

* mouseMoved\(\): a circle will show if this mouse location is allowed to place a piece

```java
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

		}
		// if mouse location at middle 1/3 between two points, repaint() the board and
		// remove the current circle and does not show a new one
		else {
			gameBoardPanel.repaint();
		}
	}
```

* actionPerformed\(\): action control after click button

```java
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		// if action from reStartButton, restart the game
		if (e.getSource() == reStartButton) {
			restartGame();
		}
		// if action from redoButton, remove two pieces that are stored in the recentPlay
		// array from array pieceArray, set turn back to Black, reapint the board and remove
		// those two pieces from the board
		else if (e.getSource() == redoButton) {
			pieceArray[recentPlay[0][0]][recentPlay[0][1]] = ' ';
			pieceArray[recentPlay[1][0]][recentPlay[1][1]] = ' ';
			turn = 'B';
			draw.setColor(Color.BLACK);
			gameBoardPanel.repaint();
		}

	}
```

