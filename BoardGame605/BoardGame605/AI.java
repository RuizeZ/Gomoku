package BoardGame605;

import java.awt.Graphics;

public class AI extends GameControl{
	
	public AI(Graphics draw, BoardPanel panel) {
		super(draw, panel);
	}
	public void AITurn(String turn, int pieceX, int pieceY) {
		
		drawPiece(pieceX + 1, pieceY, "AI");
	}
}
