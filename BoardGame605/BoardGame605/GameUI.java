package BoardGame605;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameUI {
	private static BoardPanel gameBoardPanel = new BoardPanel();
	public static int second = 16;
	JLabel timeLabel;
	public static Timer timer;

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
		timeLabel = new JLabel("Your time left:  ");
		timeLabel.setFont(new Font("Arial", Font.PLAIN, 20));

		gameSettingPanel.add(timeLabel);
		gameSettingPanel.setPreferredSize(gameSettingPanelDim);
		JButton redoButton = new JButton("Redo");
		JButton reStartButton = new JButton("New Game");
		gameSettingPanel.add(redoButton);
		gameSettingPanel.add(reStartButton);

		jf.add(gameBoardPanel);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);

		Graphics draw = gameBoardPanel.getGraphics();
		AI AIPlayer = new AI(draw, gameBoardPanel);
		GameMouse mouse = new GameMouse(draw, gameBoardPanel, redoButton, reStartButton, AIPlayer);
		gameBoardPanel.addMouseListener(mouse);
		gameBoardPanel.addMouseMotionListener(mouse);
		redoButton.addActionListener(mouse);
		reStartButton.addActionListener(mouse);
		setTimer();
		timer.start();
	}

	private void setTimer() {
		// TODO Auto-generated method stub
		timer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				second--;
				if (second <= 5) {
					timeLabel.setForeground(Color.RED);
					timeLabel.setText("Your time left: " + second);
				} else {
					timeLabel.setForeground(Color.BLACK);
					timeLabel.setText("Your time left: " + second);
				}
				if (second == 0) {
					int n = 0;
					String[] answer = { "Play Again", "Exit" };
					n = JOptionPane.showOptionDialog(null, "Time out!", "Time out!", JOptionPane.DEFAULT_OPTION,
							JOptionPane.PLAIN_MESSAGE, null, answer, answer[0]);
					if (n == 0) {
						GameControl.restartGame();

					} else {
						System.exit(0);
					}
				}

			}
		});

	}

}
