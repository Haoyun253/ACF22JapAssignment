/**
 * File name: Main.java
 * Identification: Haoyun Deng 04101223
 * Course: CST 8221-JAP, Lab Section: 301
 * Assignment: A22
 * Professor: Paulo Sousa
 * Date: 3/14/2023
 * Compiler: Eclipse IDE
 * Purpose: Intialize the program to display its user interface
 */
package picross;

/**
 * Class name: Main list: Main
 * Constants list: NULL
 * purpose:Initializes the NVC object and as a start point of the whole program
 * 
 * @author Haoyun Deng
 * @version 1.0
 * @see java.awt , java.util , java.swing
 * @since 17.0.3
 */
public class Main{
	/**
	 * Mehotd name : main purpose : the starting point of the whole program
	 * Algorithm: null
	 * 
	 * @param args - the string arguments that are used for the program
	 */
	public static void main(String[] args) {
		GameModel gameModel = new GameModel();
		GameView gameView = new GameView();
		ControllableTimer time=new ControllableTimer(gameView);
		GameController gameController=new GameController(gameModel, gameView,time);
		gameController.start();
	}
}
