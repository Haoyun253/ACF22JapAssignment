/**
 * File name: GameCleint.java
 * Identification: Haoyun Deng 04101223
 * Course: CST 8221-JAP, Lab Section: 301
 * Assignment: A32
 * Professor: Paulo Sousa
 * Date: 4/09/2023
 * Compiler: Eclipse IDE
 * Purpose: Implements the client side of the project
 */

package picross;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Class name: GameClient
 * Methods list: begin
 * Constants list: NULL
 * purpose: Implements the client side of the project
 * @author Haoyun Deng
 * @version 1.0
 * @see javax.swing java.awt java.io java.net java.util
 * @since 17.0.3
 */

public class GameClient {

	/**
	 * The Client inferface of the whole program
	 */
	public JFrame frame = new JFrame("Picross Client");

	/**
	 * buttons
	 */
	JButton endButton;
	JButton connectButton;
	JButton newGameButton;
	JButton sendGameButton;
	JButton receiveGameButton;
	JButton sendDataButton;
	JButton playButton;

	/**
	 * text box
	 */
	JTextArea logArea;
	JTextArea nameArea;
	JTextArea portArea;
	JTextArea ipArea;

	/**
	 * Socket
	 */
	Socket clientSocket = null;
	DataInputStream input = null;
	DataOutputStream output = null;
	
	/**
	 * client Id
	 */
	int clientId = -1;
	
	/**
	 * detection
	 */
	boolean isConnected = false;
	boolean isPlay = false;
	
	/**
	 * board solution
	 */
	String solution = GameConfig.DEFAULT_SOLUTION;

	/**
	 * User Info
	 */
	String gameData = "";

	GameModel gameModel = new GameModel();

	/**
	 * Method name: begin
	 * Purpose: Initializes a client-side GUI for the project
	 * Algorithm: Invoke functioanlities from internal packages to intailize and set up the major panel
	 */
	
	public void begin() {
		frame.getContentPane().removeAll();
		frame.revalidate();
		frame.repaint();

		frame.setSize(570, 500);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);		 // Initialize window


		// center
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

		JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel title = new JLabel();
		title.setIcon(new ImageIcon("images/gameclient.png"));
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		titlePanel.add(title);
		
		JPanel broadPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		JLabel userNameLabel = new JLabel("User: ");
		nameArea = new JTextArea(GameConfig.DEFAULT_USER);
		nameArea.setPreferredSize(new Dimension(75, 17));
		JLabel ipLabel = new JLabel("Server: ");
		ipArea = new JTextArea(GameConfig.DEFAULT_ADDR);
		ipArea.setPreferredSize(new Dimension(75, 17));
		JLabel portLabel = new JLabel("Port: ");
		portArea = new JTextArea(Integer.toString(GameConfig.DEFAULT_PORT));
		portArea.setPreferredSize(new Dimension(50, 17));

		connectButton = new JButton("Connect");
		endButton = new JButton("End");
		newGameButton = new JButton("New game");
		sendGameButton = new JButton("Send game");
		receiveGameButton = new JButton("Receive game");
		sendDataButton = new JButton("Send data");
		playButton = new JButton("Play");

		JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		logArea = new JTextArea(10, 35);
		logArea.setLineWrap(true);
		logArea.setEditable(false);

		JScrollPane logScrollPane = new JScrollPane(logArea);
		textPanel.add(logScrollPane);
		
		broadPanel.add(userNameLabel);
		broadPanel.add(nameArea);
		broadPanel.add(ipLabel);
		broadPanel.add(ipArea);
		broadPanel.add(portLabel);
		broadPanel.add(portArea);
		broadPanel.add(connectButton);
		broadPanel.add(endButton);
		broadPanel.add(newGameButton);
		broadPanel.add(sendGameButton);
		broadPanel.add(receiveGameButton);
		broadPanel.add(sendDataButton);
		broadPanel.add(playButton);


		// add panels to center panel
		centerPanel.add(titlePanel);
		centerPanel.add(broadPanel);
		centerPanel.add(textPanel);
		frame.add(centerPanel);

		Client client = new Client();

		connectButton.addActionListener(new ActionListener() {	//Add button event

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!isConnected)
					client.connect();
			}

		});

		endButton.addActionListener(new ActionListener() {		//Add button event

			@Override
			public void actionPerformed(ActionEvent e) {
				client.shutdown();
			}
		});

		newGameButton.addActionListener(new ActionListener() {		//Add button event

			@Override
			public void actionPerformed(ActionEvent e) {
				client.createRandom();
			}
		});

		sendGameButton.addActionListener(new ActionListener() {		//Add button event

			@Override
			public void actionPerformed(ActionEvent e) {
				client.send();
			}
		});

		receiveGameButton.addActionListener(new ActionListener() {		//Add button event

			@Override
			public void actionPerformed(ActionEvent e) {
				client.received();
			}
		});

		sendDataButton.addActionListener(new ActionListener() {		//Add button event

			@Override
			public void actionPerformed(ActionEvent e) {
				client.sendInfo();
			}
		});

		playButton.addActionListener(new ActionListener() {		//Add button event

			@Override
			public void actionPerformed(ActionEvent e) {
				client.playGame();
			}
		});

		gameModel.newGame(solution);

		frame.setVisible(true);
	}

	/**
	 * Class name: Client
	 * Methods list: message connect createRaandom send sendInform received playGame shutdown
	 * Constants list: NULL
	 * purpose: Implements the communication between client side and server side
	 * @author Haoyun Deng
	 * @version 1.0
	 * @see Javax.swing Java.awt Java.io Java.net Java.util
	 * @since 17.0.3
	 */
	
	class Client {
		
		/**
		 * Method name: message
		 * Purpose: send the protocol information to the game server
		 * Algorithm: utilizes the streaming input and output 
		 * @param protocolId - the ID of the protocol
		 * @param data - the data for transmission
		 * @return whether successfully send the data
		 */
		
		private boolean message(String protocolId, String data) {		//Send the protocol code to the server
			try {
				output.writeUTF(
						clientId + GameConfig.PROTOCOL_SEPARATOR + protocolId + GameConfig.PROTOCOL_SEPARATOR + data);
				return true;
			} catch (IOException e) {
				logArea.insert("Not connected to server...\n", logArea.getDocument().getLength());
				isConnected = false;
				return false;
			}
		}

		/**
		 * Method name: connect
		 * Purpose: connect to the server
		 * Algorithm: Using ports and sockets for networking
		 */
		
		public void connect() {			//linked server
			try {
				// log success
				clientSocket = new Socket(ipArea.getText(), Integer.valueOf(portArea.getText()));
				output = new DataOutputStream(clientSocket.getOutputStream());
				input = new DataInputStream(clientSocket.getInputStream());
				// receive client id
				clientId = input.readInt();

				isConnected = true;
				logArea.insert("Connection button...\n", logArea.getDocument().getLength());
				logArea.insert("Start Client...\n", logArea.getDocument().getLength());
				logArea.insert("Connection with " + ipArea.getText() + "on post " + portArea.getText() + "\n",
						logArea.getDocument().getLength());
			} catch (UnknownHostException uhe) {
				// log failure
				System.out.println("Unknown Host Exception: " + uhe);
			} catch (IOException ioe) {
				logArea.insert("No Server...\n", logArea.getDocument().getLength());
			}
		}

		
		/**
		 * Method name: createRandom
		 * Purpose: Randomly generate a chessboard
		 * Algorithm: Use the random class to generate dimensions and randomly generate the board
		 */
		
		private void createRandom() {
			// generate random number from 2 to 8
			Random r = new Random();
			int dimension = r.nextInt(6) + 2;

			gameModel.setMatrixSize(dimension);
			gameModel.setArray(0);
			gameModel.setArray(1);
			gameModel.randomly();
			solution = gameModel.getCheckerboard();		//randomly generated game

			logArea.insert("Creating new MVC Game\n", logArea.getDocument().getLength());
			logArea.insert("New solution: " + solution + "\n", logArea.getDocument().getLength());
		}

		/**
		 * Method name: send
		 * Purpose: Send the current chessboard information to the server
		 * Algorithm:Use stream output to send information and check for success
		 */
		private void send() {			//Send game information to the server
			if (isConnected) {
				solution = gameModel.getCheckerboard();
				if (message(GameConfig.PROTOCOL_SEND_SOLUTION, solution)) {
					// log success
					try {
						String reply = input.readUTF();
						logArea.insert("Solution sent!\n", logArea.getDocument().getLength());
						logArea.insert(reply + "\n", logArea.getDocument().getLength());
					} catch (IOException e) {
						logArea.insert("Connection to server lost\n", logArea.getDocument().getLength());
						isConnected = false;
					}
				}
			} else {
				// log failure
				logArea.insert("Not connected...\n", logArea.getDocument().getLength());
			}
		}


		/**
		 * Method name: sendInfo
		 * Purpose: Send the user information to the server
		 * Algorithm:Use stream output to send information and check for success
		 */
		private void sendInfo() {			//Send user information to the server
			if (isConnected && gameModel != null) {
				gameData = nameArea.getText();
				gameData += gameModel.getInfo();
				if (message(GameConfig.PROTOCOL_SEND_DATA, gameData)) {
					// log success
					try {
						String reply = input.readUTF();
						logArea.insert("Send game information to the server\n", logArea.getDocument().getLength());
						logArea.insert(reply + "\n", logArea.getDocument().getLength());
					} catch (IOException e) {
						logArea.insert("Connection to server lost\n", logArea.getDocument().getLength());
						isConnected = false;
					}
				}
			} else {
				// log failure
				logArea.insert("Not connected to server...\n", logArea.getDocument().getLength());
			}
		}
		
		/**
		 * Method name: received
		 * Purpose:Will accept board information from the server to play
		 * Algorithm:Use stream input to receive information and check for success
		 */
		private void received() {		//Receive game information from the server
			if (isConnected) {
				logArea.insert("Accepting solution from server...\n", logArea.getDocument().getLength());

				if (message(GameConfig.PROTOCOL_REQUEST_SOLUTION, "")) {
					// log success
					try {
						solution = input.readUTF();
						gameModel.newGame(solution);
						logArea.insert("Received solution from server.\nThe solution is: " + solution + "\n",
								logArea.getDocument().getLength());
					} catch (IOException e) {
						logArea.insert("Connection to server lost\n", logArea.getDocument().getLength());
						isConnected = false;
					}
				}
			} else {
				// log failure
				logArea.insert("Not connected to server...\n", logArea.getDocument().getLength());
			}
		}

		/**
		 * Method name: playGame
		 * Purpose: Open a playable program
		 * Algorithm: Null
		 */
		private void playGame() {		//open the game

			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					GameView gameView = new GameView();
					GameController gameController = new GameController(gameModel, gameView);
					gameController.start();
				}
			}); // End EventQueue

		}
		
		/**
		 * Method name: shutdown
		 * Purpose:Disconnect client from server	
		 * Algorithm: Use the socket's close method to end
		 */
		public void shutdown() {		//break link
			if (isConnected) {
				message(GameConfig.PROTOCOL_END, "");
				try {
					output.close();
					clientSocket.close();
				} catch (IOException ioe) {
					System.out.println("IO Exception: " + ioe);
				}
			}

			frame.dispose();
		}

	}
}