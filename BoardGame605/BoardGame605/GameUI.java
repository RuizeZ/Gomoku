package BoardGame605;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameUI {
	private static BoardPanel gameBoardPanel = new BoardPanel();

	public static void main(String[] args) {
		GameUI ui = new GameUI();

		ui.showUI();
	}

	public void showUI() {
		// set up frame
		Dimension gameSettingPanelDim = new Dimension(200, 0);
		JFrame jf = new JFrame();
		jf.setSize(1000, 800);
		jf.setTitle("Board Game");
		jf.setLocationRelativeTo(null);
		// set BorderLayout
		BorderLayout border = new BorderLayout();
		jf.setLayout(border);
		// set gameSettingPanel
		JPanel gameSettingPanel = new JPanel();
		gameSettingPanel.setBackground(Color.LIGHT_GRAY);
		// put gameSettingPanel at east
		jf.add(gameSettingPanel, BorderLayout.EAST);
		gameSettingPanel.setPreferredSize(gameSettingPanelDim);
		JButton redoButton = new JButton("Redo");
		JButton reStartButton = new JButton("New Game");
		gameSettingPanel.add(redoButton);
		gameSettingPanel.add(reStartButton);

		jf.add(gameBoardPanel);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);

		Graphics draw = gameBoardPanel.getGraphics();
		AI AIPlayer = new AI(draw,gameBoardPanel);
		GameMouse mouse = new GameMouse(draw, gameBoardPanel, redoButton, reStartButton, AIPlayer);
		gameBoardPanel.addMouseListener(mouse);
		gameBoardPanel.addMouseMotionListener(mouse);
		redoButton.addActionListener(mouse);
		reStartButton.addActionListener(mouse);

	}
}
