---
description: This class constructs the UI. It includes the main method
---

# Class GameUI

## Data Field

```java
private static BoardPanel gameBoardPanel = new BoardPanel();//the panel contains board  
public static int second = 16;//the count down starts at 16 seconds
JLabel timeLabel;//label to show the count down
public static Timer timer;// Timer object to set the count down
```

## Methods

* showUI\(\)

```java
	public void showUI() {
		// set up frame
		JFrame jf = new JFrame();
		jf.setSize(1000, 800);
		jf.setTitle("Board Game");
		jf.setLocationRelativeTo(null);//locate at the center
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//close frame and exit the program  
		jf.setVisible(true);
		
		// set BorderLayout
		BorderLayout border = new BorderLayout();
		jf.setLayout(border);//set the layout of jf to BorderLayout
		//....
	}
```

> [BorderLayout](https://www.geeksforgeeks.org/java-awt-borderlayout-class/) arranges the components in the five regions. Four sides are referred to as north, south, east, and west. The middle part is called the center. Each region can contain only one component and is identified by a corresponding constant as _NORTH_, _SOUTH_, _EAST_, _WEST_, and _CENTER_.
>
> **Constructors:**
>
> 1. **BorderLayout\(\):** It will construct a new borderlayout with no gaps between the components.
> 2. **BorderLayout\(int, int\):** It will constructs a border layout with the specified gaps between the components

![](.gitbook/assets/image%20%282%29.png)

```java
		// set gameSettingPanel
		JPanel gameSettingPanel = new JPanel();
		gameSettingPanel.setBackground(Color.LIGHT_GRAY);
		// put gameSettingPanel at east of the jf
		jf.add(gameSettingPanel, BorderLayout.EAST);
		// create a new JLabel to show the count down
		timeLabel = new JLabel("Your time left:  ");
		timeLabel.setFont(new Font("Arial", Font.PLAIN, 20));//set the text font
		
		jf.add(gameBoardPanel);//add gameBoardPanel which contains game board to the jf  

		Dimension gameSettingPanelDim = new Dimension(200, 0);
		gameSettingPanel.add(timeLabel);
		gameSettingPanel.setPreferredSize(gameSettingPanelDim); // set size of the panel 
		JButton redoButton = new JButton("Redo");//create button for player redo a previous step  
		JButton reStartButton = new JButton("New Game");//create button for player to restart a new game  
		gameSettingPanel.add(redoButton);
		gameSettingPanel.add(reStartButton);
```

`gameSettingPanel.setPreferredSize(Dimension D)` requests a Dimension object as parameter, so I created a Dimension object at line 12. Since this frame is BorderLayout, 200 is the width from east to center. 

```java
		Graphics draw = gameBoardPanel.getGraphics();//add draw to gameBoardPanel
		AI AIPlayer = new AI(draw, gameBoardPanel);
		GameMouse mouse = new GameMouse(draw, gameBoardPanel, redoButton, reStartButton, AIPlayer);
		gameBoardPanel.addMouseListener(mouse);// add MouseListener to gameBoardPanel
		gameBoardPanel.addMouseMotionListener(mouse);// add MouseMotionListener to gameBoardPanel
		redoButton.addActionListener(mouse);
		reStartButton.addActionListener(mouse);
		setTimer();
		timer.start();//start the count down
	}
```

`Graphics draw = gameBoardPanel.getGraphics();` adds draw to gameBoardPanel. If we call draw to paint, the graph will shows on gameBoardPanel, since we added draw on the gameBoardPanel.

Line 4 and 5 add mouse listener and mouse motion listener to gameBoardPanel. Only Actions and motions that happened on the gameBoardPanel will cost effects.

Line 6 and 7 add action listener to button. Only Actions that happened to the button will cost effects.

* setTimer\(\)

```java
       private void setTimer() { 
       
       //take action every 1000ms
       timer = new Timer(1000, new ActionListener() { 
        @Override
        public void actionPerformed(ActionEvent e) {
            //count down
            second--;
            // if less than 5 second, change color to red
            if (second <= 5) {
                timeLabel.setForeground(Color.RED);
                timeLabel.setText("Your time left: " + second);
            } else {
                timeLabel.setForeground(Color.BLACK);
                timeLabel.setText("Your time left: " + second);
            }
            //when time out, popup window shows game end and player choose options
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
```

Line 21 popup a option dialog with options indicates in the line 20. If player chooses first option, n = 0 and restart a new game, else n = 1, and game ends

