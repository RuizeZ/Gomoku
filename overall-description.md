# Overall Description

## Implemented Functions

In this project, human player plays black, and AI plays white. Black plays first. Human player has 15 seconds each turn. If player cannot decide where to play before time out, the game will end and player can choose either restart a game or exit the program. When one of the player forms an unbroken chain of five stones, this chain will change to red, a popup window indicates the winner and human player could choose either play another game or exit the game. In each human player's turn, the player could choose to redo one previous step, or to restart the game completely.

## Classes

* GameUI: this class constructs the UI. It includes the main method
* Boardpanel: this class handles the board drawing, background drawing, stones drawing for repaint.
* GameControl: this class handles drawing stones for each player, deciding winner, changing turn and restart the game.
* GameMouse: this class inherences GameControl class. It handles the human player's action
* AI: this class inherences GameControl class. It handles the AI's action

