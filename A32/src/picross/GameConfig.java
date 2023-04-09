/**
 * File name: GameConfig.java
 * Identification: Haoyun Deng 04101223
 * Course: CST 8221-JAP, Lab Section: 301
 * Assignment: A32
 * Professor: Paulo Sousa
 * Date: 4/09/2023
 * Compiler: Eclipse IDE
 * Purpose: Includes the constants for class GameClient and GameServer
 */

package picross;

/**
 * Class name: GameConfig 
 * Constants list: PROTOCOL_SEPARATOR PROTOCOL_END PROTOCOL_SEND_SOLUTION PROTOCOL_REQUEST_SOLUTION PROTOCOL_SEND_DATA
 * purpose:Includes the constants for class GameClient and GameServer
 * @author Haoyun Deng
 * @version 1.0
 * @since 17.0.3
 */

public class GameConfig 
{
	static final String PROTOCOL_SEPARATOR = "#";
	static final String PROTOCOL_END = "0";
	static final String PROTOCOL_SEND_SOLUTION = "1";
	static final String PROTOCOL_REQUEST_SOLUTION = "2";
	static final String PROTOCOL_SEND_DATA = "3";
	
	static String DEFAULT_USER = "Paulo";
	static String DEFAULT_ADDR = "localhost";
	static int DEFAULT_PORT = 12345;
	

	static String DEFAULT_SOLUTION = "01110,10001,01010,10101,00000";
}