/**
 * File name: GameServer.java
 * Identification: Haoyun Deng 04101223
 * Course: CST 8221-JAP, Lab Section: 301
 * Assignment: A32
 * Professor: Paulo Sousa
 * Date: 4/09/2023
 * Compiler: Eclipse IDE
 * Purpose: Implements the server side of the project
 */

package picross;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Class name: GameServer
 * Methods list: begin
 * Constants list: NULL
 * purpose: Implements the server side of the project
 * @author Haoyun Deng
 * @version 1.0
 * @see javax.swing java.awt java.io java.net java.util
 * @since 17.0.3
 */

public class GameServer {

	/**
	 *  The Server inferface of the whole program
	 */
	public JFrame frame = new JFrame("Picross Server");
	
	/**
	 * Socket
	 */
	ServerSocket serverSocket = null;
	Socket clientSocket = null;

	/**
	 * detection
	 */
	boolean isRunning = false;

	/**
	 * thread count
	 */
	int totalNumber = 0;
	int clientNumber = 0;

	/**
	 * board solution
	 */
	String solution = GameConfig.DEFAULT_SOLUTION;

	/**
	 * buttons
	 */
	JCheckBox finalize;	
	JButton endButton;
	JButton startButton;
	JButton resultsButton;
	
	/**
	 * text box
	 */
	JTextArea logArea;
	JTextField portField;

	/**
	 * Method name: begin
	 * Purpose: Initializes a server-side GUI for the project
	 * Algorithm: Invoke functioanlities from internal packages to intailize and set up the major panel
	 */
	
	public void begin() {
		frame.getContentPane().removeAll();
		frame.revalidate();
		frame.repaint();

		frame.setSize(570, 400);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);		 // Initialize window

		// center
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

		JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel title = new JLabel();
		title.setIcon(new ImageIcon("images/gameserver.png"));
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		titlePanel.add(title);
		
		JPanel broadPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		JLabel portLabel = new JLabel("Port: ");

		portField = new JTextField(Integer.toString(GameConfig.DEFAULT_PORT));
		portField.setPreferredSize(new Dimension(50, 17));

		startButton = new JButton("Execute");
		resultsButton = new JButton("Results");
		finalize = new JCheckBox("Finalize");
		endButton = new JButton("End");
		
		broadPanel.add(portLabel);
		broadPanel.add(portField);
		broadPanel.add(startButton);
		broadPanel.add(resultsButton);
		broadPanel.add(finalize);
		broadPanel.add(endButton);

		JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		logArea = new JTextArea(10, 35);
		logArea.setLineWrap(true);
		logArea.setEditable(false);

		JScrollPane logScrollPane = new JScrollPane(logArea);
		textPanel.add(logScrollPane);

		// add panels to center panel
		centerPanel.add(titlePanel);
		centerPanel.add(broadPanel);
		centerPanel.add(textPanel);
		frame.add(centerPanel);
		
		Server server = new Server();

		startButton.addActionListener(new ActionListener() {		//Add button event

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!isRunning)
					server.startConnent();
			}

		});

		endButton.addActionListener(new ActionListener() {		//Add button event

			@Override
			public void actionPerformed(ActionEvent e) {
				server.shutdown();
			}

		});

		resultsButton.addActionListener(new ActionListener() {		//Add button event

			@Override
			public void actionPerformed(ActionEvent e) {
				server.results();
			}

		});

		frame.setVisible(true);
	}
	
	/**
	 * Class name: Server
	 * Methods list: run startConnect results shutdown
	 * Constants list: NULL
	 * purpose: Implements the communication between client side and server side
	 * @author Haoyun Deng
	 * @version 1.0
	 * @see Javax.swing Java.awt Java.io Java.net Java.util
	 * @since 17.0.3
	 */
	
	class Server implements Runnable {
		/**
		 * array of threads
		 */
		ArrayList<ClientThread> clientList = new ArrayList<ClientThread>(5);
		
		/**
		 * Method name: run
		 * Purpose: await and connect to the client
		 * Algorithm: Utilize the socket and invoke the accept method to build the client thread
		 */
		
		@Override
		public void run() {
			while (isRunning) {
				logArea.insert("Waiting for client connection...\n", logArea.getDocument().getLength());
				try {
					clientSocket = serverSocket.accept();
				} catch (IOException ioe) {
					// break out when the server is not running
					if (!isRunning)
						return;

					System.out.println("IO Exception: " + ioe);
				}

				ClientThread clientThread = new ClientThread(clientSocket);
				clientThread.start();
				clientList.add(clientThread);
			}
		}

		/**
		 * Method name: startConnect
		 * Purpose: builds a server socket
		 * Algorithm: Utilizes the socket to accept the client connection request
		 */
		
		public void startConnent() {			//start link
			logArea.insert("Eexc button...\n", logArea.getDocument().getLength());
			try {
				// log success
				serverSocket = new ServerSocket(Integer.valueOf(portField.getText()));
				logArea.insert("port="+portField.getText()+"\n", logArea.getDocument().getLength());
				isRunning = true;
				Thread serverT = new Thread(this);
				serverT.start();
			} catch (IOException ioe) {
				// log failure
				System.out.println("IO Exception: " + ioe);
			}
		}

		/**
		 * Method name: results
		 * Purpose: display the user's score
		 * Algorithm: Invoke JDialogue to display
		 */
		
		public void results() {			//Display user information
			if (clientList.size() > 0) {
				String results = "Game results:\n";
				for (int i = 0; i < clientList.size(); i++) {
					results += clientList.get(i).getInfo() + "\n";
				}

				JOptionPane.showMessageDialog(null, results, "Results", JOptionPane.PLAIN_MESSAGE);
			} else {
				// log failure
				logArea.insert("No customer information, can not display\n", logArea.getDocument().getLength());
			}
		}
		
		/**
		 * Method name: shutdown
		 * Purpose: clear all the client threads
		 * Algorithm: Close the streaming output and socket
		 */
		private void shutdown() {		//break link
			try {
				if (isRunning) {
					isRunning = false;
					// close client connections
					for (int i = clientList.size() - 1; i >= 0; i--) {
						clientList.get(i).closeIO();
					}

					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			frame.dispose();
		}

		
		/**
		 * Class name: ClientThread
		 * Methods list: run clientThread closeIO getInfo disconnected sendSolution receiveSolution receiveInfo
		 * Constants list: NULL
		 * purpose: Implements the protocol which sent by client
		 * @author Haoyun Deng
		 * @version 1.0
		 * @see Javax.swing Java.awt Java.io Java.net Java.util
		 * @since 17.0.3
		 */
		
		class ClientThread extends Thread {
			/**
			 * received protocol
			 */
			String data = "";
			
			/**
			 * User Info
			 */
			String info = "";
			
			/**
			 * Socket
			 */
			DataInputStream input = null;
			DataOutputStream output = null;
			Socket threadSocket = null;
			
			/**
			 * Method name: run
			 * Purpose: receive and issue the protocol
			 * Algorithm: Utilize input stream to receive protocol
			 */
			
			public void run() {
				String clientId = "";
				String protocolId = "";
				String received = "";

				StringTokenizer st;

				try {
					while (!protocolId.equals("0")) {		//Check protocol type
						data = input.readUTF();

						logArea.insert(data + "\n", logArea.getDocument().getLength());

						st = new StringTokenizer(data, "#");
						clientId = st.nextToken();
						protocolId = st.nextToken();
						if (st.hasMoreTokens()) {		
							received = st.nextToken();
						}

						switch (protocolId) {		//Assign different methods to complete the protocol
						case "1":
							receiveSolution(received);
							break;
						case "2":
							sendSolution(clientId);
							break;
						case "3":
							receiveInfo(clientId, received);
							break;
						}
					}

					disconnected(clientId);
				} catch (SocketException e) {
					// log failure
					logArea.insert("Client-server connection terminated\n",
							logArea.getDocument().getLength());
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
			
			/**
			 * Method name: getInfo
			 * Purpose: Returns the user information
			 * Algorithm: NULL
			 */
			
			public String getInfo() {		//get user information
				return this.info;
			}

			
			/**
			 * Method name: ClientThread
			 * Purpose: issue the clientID to the client
			 * Algorithm: Initializes the stream 
 			 * @param clientSocket - The socket of each client
			 */
			public ClientThread(Socket clientSocket) {
				this.threadSocket = clientSocket;
				totalNumber++;
				clientNumber++;

				try {
					input = new DataInputStream(clientSocket.getInputStream());
					output = new DataOutputStream(clientSocket.getOutputStream());
					output.writeInt(totalNumber);		//initialization stream
				} catch (IOException e) {
					e.printStackTrace();
				}
				// log success	
				logArea.insert("Client ID: " + totalNumber + " connected to server\n",
						logArea.getDocument().getLength());
			}
			
			
			/**
			 * Method name: closeIO
			 * Purpose: close the stream
			 * Algorithm: NULL
			 */
			
			public void closeIO() {		//close stream
				try {
					output.close();
					input.close();
					threadSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}


			/**
			 * Method name: disconnected
			 * Purpose: disconnect the client
			 * Algorithm: Kills the thread
			 * @param clientId - the ID of the client
			 */
			private void disconnected(String clientId) {		//disconnect client
				logArea.insert("Client ID: " + clientId + " to disconnect\n",
						logArea.getDocument().getLength());

				clientList.remove(clientList.indexOf(this));
				clientNumber--;			//delete client thread

				closeIO();

				if (clientNumber == 0 && finalize.isSelected())
					shutdown();
			}

			/**
			 * Method name: sendSolution
			 * Purpose: Sends the server chess board state to the client
			 * Algorithm: streaming output
			 * @param clientId - the ID of the client
			 */
			
			private void sendSolution(String clientId) {		//send solution
				try {
					// log success
					output.writeUTF(solution);
					logArea.insert("Sent solution to client: #" + clientId + "\n",
							logArea.getDocument().getLength());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			/**
			 * Method name: recieveSolution
			 * Purpose: Receives the client chess board state to the server
			 * Algorithm: streaming input
			 * @param clientData - the Data of the client
			 */
			private void receiveSolution(String clientData) {		//receive solution

				try {
					// log success
					output.writeUTF("SERVER: client solution received.");
					solution = clientData;
					logArea.insert("Received solution from client\nNew solution is: " + solution + "\n",
							logArea.getDocument().getLength());
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

			/**
			 * Method name: receiveInfo
			 * Purpose: Receives the client information to the server
			 * Algorithm: streaming input
			 * @param clientId - the ID of the client
			 * @param received - received information from client
			 */
			private void receiveInfo(String clientId, String received) {		//send user Info
				try {
					// log success
					output.writeUTF("SERVER: client info received");
					data = "Player[" + clientId + "]: ";
					data += received;
					this.info=data;
					logArea.insert("Received info to client: #" + clientId + "\n",
							logArea.getDocument().getLength());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

}
