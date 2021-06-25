---
description: >-
  This class handles the board drawing, background drawing, stones drawing for
  repaint.
---

# Class BoardPanel

## Class Declaration

```java
public class BoardPanel extends JPanel {

}
```

## Data Field

```java
	private static final long serialVersionUID = 1L;
	//init board start at x0, y0, one block is 50 and 15 lines in total
	public static int x0 = 40, y0 = 25, size = 50, line = 15;
	//this array records each piece's position on the board
	private char[][] pieceArray = GameControl.pieceArray;
	//flag for restart a game
	private static boolean newGame = false;
```

## Methods

* paint\(Graphics g\)

```java
@Override
	public void paint(Graphics g) {
		// overide paint class, call its super class
		super.paintComponent(g);
		//this array records each piece's position on the board
		pieceArray = GameControl.pieceArray;
		//load background by read from a relative address
		ImageIcon icon = new ImageIcon("pic\\background.jpg");
		//draw the image
		g.drawImage(icon.getImage(), 0, 0, 800, 800, null);
		// draw the game board with 15 lines and 50 per block
		for (int i = 0; i < line; i++) {
			// horizontal line
			g.drawLine(x0, y0 + i * size, (line - 1) * size + x0, y0 + i * size);
			// vertical line
			g.drawLine(x0 + i * size, y0, x0 + i * size, (line - 1) * size + y0);
		}
		
		if (!newGame) {
		// if this is not a new game, draw all the pieces
			for (int i = 0; i < pieceArray.length; i++) {
				for (int j = 0; j < pieceArray[0].length; j++) {
					if (pieceArray[i][j] == 'G') {
					//if current location is G, draw piece in gray
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
		} 
		else {
		// if it is a new game, no need to draw the piece
			newGame = false;
		}

	}
```



* setnewGame and getnewGame

```text
public static void setnewGame(boolean newGame) {
    BoardPanel.newGame = newGame;
}

public static boolean getnewGame() {
    return BoardPanel.newGame;
}
```

