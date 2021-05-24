package BoardGame524;

import java.awt.Graphics;

import javax.swing.JFrame;

public class GameUI {
	private static BoardPanel panel = new BoardPanel();

	public static void main(String[] args) {
		GameUI ui = new GameUI();
		ui.showUI();
	}

	public void showUI() {
		// set up frame
		JFrame jf = new JFrame();
		jf.setSize(800, 800);
		jf.setTitle("Board Game");
		jf.setLocationRelativeTo(null);
		jf.setResizable(false);
		jf.add(panel);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);

		Graphics draw = panel.getGraphics();
		GameMouse mouse = new GameMouse(draw, panel);
		panel.addMouseListener(mouse);
		panel.addMouseMotionListener(mouse);
	}
}
