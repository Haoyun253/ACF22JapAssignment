/**
 * File name: Main.java
 * Identification: Haoyun Deng 04101223
 * Course: CST 8221-JAP, Lab Section: 301
 * Assignment: A32
 * Professor: Paulo Sousa
 * Date: 4/09/2023
 * Compiler: Eclipse IDE
 * Purpose: Intialize the program to display its user interface
 */
package picross;

import java.util.Scanner;

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

			int item = 0;		//Create a launcher to select
			System.out.println("Please select one of the following:");
			System.out.println("1: Client");
			System.out.println("2: Server");
			System.out.println("3: MVC");
			Scanner scan = new Scanner(System.in);
			item=scan.nextInt();
			if (item == 1)
			{
				GameClient gameClient = new GameClient();
				gameClient.begin();
			}
			else if (item == 2)
			{
				GameServer gameServer = new GameServer();
				gameServer.begin();
			}
			else if (item == 3)
			{
				GameModel gameModel = new GameModel();
				GameView gameView = new GameView();
				GameController gameControllernew =new GameController(gameModel, gameView);
				gameModel.randomly();
				gameControllernew.start();
			}
		
		
	}
	

		

	
}
