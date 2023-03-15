/**
 * File name: GameView.java
 * Identification: Haoyun Deng 04101223
 * Course: CST 8221-JAP, Lab Section: 301
 * Assignment: A22
 * Professor: Paulo Sousa
 * Date: 3/14/2023
 * Compiler: Eclipse IDE
 * Purpose: Intialize the program to display its user interface
 */

package picross;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * Class name: data Methods list: 1.baseWindow 2.initializeLanguage
 * 3.updateLanguage 4.initializePlayPanel 5.initializeControl 6.initializeInfo
 * 7.initializeBroad 8.initializeTop 9.initializeLeft 10.setTime 11.infotext
 * 12.Splash 13.pointtext 14.initializeSolution 15.endgame 16.addListener
 * 17.newDialog 18.colorDialog 19.colorChoose 20.about Constants list: NULL
 * purpose: Intialize the program to display its user interface
 * 
 * @author Haoyun Deng
 * @version 1.0
 * @see java.awt , java.util , java.swing
 * @since 17.0.3
 */
public class GameView {

	/**
	 * The uesr inferface of the whole program
	 */
	public JFrame frame;

	/**
	 * 
	 */
	JPanel playPanel;
	/*
	 * menus
	 */
	JMenuItem english;
	JMenuItem chinese;
	JMenuItem new1;
	JMenuItem solution;
	JMenuItem exit;
	JMenuItem colors;
	JMenuItem about;
	/**
	 * buttons
	 */
	JRadioButton markButton;
	JButton resetButton;
	JButton solutionButton;
	JButton saveButton;
	JButton loadButton;
	JButton randomButton;
	JButton creatButton;
	JButton C1button;
	JButton C2button;
	JButton C3button;
	/**
	 * color panel
	 */
	JPanel C1panel;
	JPanel C2panel;
	JPanel C3panel;
	/**
	 * dialogs
	 */
	JDialog dialogC;
	JDialog dialogS;
	/**
	 * text box
	 */
	JTextArea infoField;
	JTextField pointField;
	JTextField sizeField;
	JTextField SizeField;
	JTextField timeField;
	/**
	 * panel
	 */
	JPanel topPanel;
	JPanel boardPanel;
	JPanel leftPanel;
	JPanel[][] p;

	/**
	 * The language files
	 */
	private static ResourceBundle texts;

	/**
	 * Locale
	 */
	Locale loc;

	/**
	 * The default title
	 */
	public String TITLE = "Haoyun253-Picoss";

	/**
	 * The default language
	 */
	public String LANGUAGE = "Language";

	/**
	 * The default menu option
	 */
	public String ENGLISH = "English";

	/**
	 * The default menu option
	 */
	public String CHINESE = "Chinese";

	/**
	 * The default menu option
	 */
	public String GAME = "Game";

	/**
	 * The default new option
	 */
	public String NEW = "New";

	/**
	 * The default exit option
	 */
	public String EXIT = "Exit";

	/**
	 * The default help option
	 */
	public String HELP = "Help";

	/**
	 * The default color option
	 */
	public String COLORS = "Colors";

	/**
	 * The default about option
	 */
	public String ABOUT = "About";

	/**
	 * The default color option
	 */
	public String COLOR1 = "Color1:Correct";

	/**
	 * The default color option
	 */
	public String COLOR2 = "Color2:Marked";

	/**
	 * The default color option
	 */
	public String COLOR3 = "Color3:Error";

	/**
	 * The default mark botton
	 */
	public String MARK = "Mark";

	/**
	 * The default resetting botton
	 */
	public String RESET = "Reset";

	/**
	 * The default solution botton
	 */
	public String SOLUTION = "Solution";

	/**
	 * The default saving botton
	 */
	public String SAVE = "Save";

	/**
	 * The default loading botton
	 */
	public String LOAD = "Load";

	/**
	 * The default randomliziation botton
	 */
	public String RANDOM = "Random";

	/**
	 * The default creating botton
	 */
	public String CREATE = "Create";

	/**
	 * The default time label
	 */
	public String TIME = "Time is: ";

	/**
	 * The default point label
	 */
	public String POINT = "Point is: ";

	/**
	 * The default gaming mode
	 */
	public int mode = 1;

	/**
	 * The default mark mode
	 */
	public int mark = 1;

	/**
	 * The default correct color
	 */
	public Color color1 = Color.GREEN;
	
	/**
	 * The default mark color
	 */
	public Color color2 = Color.YELLOW;
	
	/**
	 * The default error color
	 */
	public Color color3 = Color.RED;

	/**
	 * Method name: baseWindow Purpose: Implements the visuialization for the whole
	 * program and allocate each part to their location Algorithm: Invoke
	 * functioanlities from internal packages to intailize and set up the major panel
	 * 
	 */
	public void baseWindow() {

		frame = new JFrame(TITLE);
		frame.setSize(1000, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false); // Initialize window

		playPanel = new JPanel();
		JPanel controlPanel = new JPanel();
		JPanel informationPanel = new JPanel();
		playPanel.setBackground(Color.black); // Initialize the major panel

		JMenuBar bar = new JMenuBar();
		frame.setJMenuBar(bar);
		FlowLayout layout = new FlowLayout(FlowLayout.CENTER, 1, 1);
		frame.setLayout(layout);
		playPanel.setPreferredSize(new Dimension(750, 700));
		frame.add(playPanel);
		controlPanel.setPreferredSize(new Dimension(227, 700));
		frame.add(controlPanel);
		informationPanel.setPreferredSize(new Dimension(980, 235));
		frame.add(informationPanel); // Add menu and set panel layout

		initializeMenu(bar);
		initializePlayPanel(playPanel);
		initializeControl(controlPanel);
		initializeInfo(informationPanel); // call method to initialize

		frame.setVisible(true);
		frame.revalidate();
		frame.repaint(); // to refresh
	}

	/**
	 * Method name: Splash Purpose: Create a splash screen that automatically closes 
	 * Algorithm: Set a pop-up window with a picture, and use the timer class to control it to close
	 */
	public void Splash() {
		JDialog splash = new JDialog();
		splash.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		ImageIcon img = new ImageIcon("images/picross.png");
		JLabel splashLabel = new JLabel(img);
		splash.add(splashLabel);
		splash.setLocationRelativeTo(null);
		splash.setSize(img.getIconWidth(), img.getIconHeight()); 		//set splash dialog

		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				splash.dispose();
			}
		};
		timer.schedule(task, 2000);			//use timer close
		splash.setVisible(true);
	}

	/**
	 * Method name: initializeMenu Purpose: Initialize the menu bar and buttons 
	 * Algorithm: Invoke functioanlities from internal packages to intailize and set up the menu and dialog
	 * 
	 * @param panel - the menu bar
	 */
	public void initializeMenu(Container panel) {

		JMenu language = new JMenu(LANGUAGE);
		english = new JMenuItem(ENGLISH);
		chinese = new JMenuItem(CHINESE);
		language.add(english);
		language.add(chinese);
		panel.add(language); // initialization language menu

		JMenu game = new JMenu(GAME);
		new1 = new JMenuItem(NEW);
		new1.setIcon(new ImageIcon("images/piciconload.gif"));
		solution = new JMenuItem(SOLUTION);
		solution.setIcon(new ImageIcon("images/piciconsol.gif"));
		exit = new JMenuItem(EXIT);
		exit.setIcon(new ImageIcon("images/piciconext.gif"));
		game.add(new1);
		game.add(solution);
		game.add(exit);
		panel.add(game);				// initialization game menu

		JMenu help = new JMenu(HELP);
		colors = new JMenuItem(COLORS);
		colors.setIcon(new ImageIcon("images/piciconcol.gif"));
		about = new JMenuItem(ABOUT);
		about.setIcon(new ImageIcon("images/piciconabt.gif"));
		help.add(colors);
		help.add(about);
		panel.add(help);			// initialization help menu

		dialogC = new JDialog(frame, COLORS, true);
		dialogC.setSize(600, 200);
		GridLayout layout = new GridLayout(2, 3, 1, 1);
		dialogC.setLayout(layout);
		C1panel = new JPanel();
		C2panel = new JPanel();
		C3panel = new JPanel();
		C1panel.setBackground(color1);
		C2panel.setBackground(color2);
		C3panel.setBackground(color3);
		C1button = new JButton(COLOR1);
		C2button = new JButton(COLOR2);
		C3button = new JButton(COLOR3);
		dialogC.add(C1panel);
		dialogC.add(C2panel);
		dialogC.add(C3panel);
		dialogC.add(C1button);
		dialogC.add(C2button);
		dialogC.add(C3button);				// initialization dialog
	}

	/**
	 * Method name: updateLanguage Purpose: Implements the languages switching based
	 * on required language Algorithm: Implements the language switching by if
	 * conditonal statement
	 * 
	 * @param choose - the number indicates the language option
	 */
	public void updateLanguage(int choose) {
		String language = "";
		String country = "";
		if (choose == 0) { // Check language options
			language = "en";
			country = "US";
		} else {
			language = "zh";
			country = "CN";
		}

		try {
			loc = new Locale.Builder().setLanguage(language).setRegion(country).build(); // Invoke a text file to
																							// replace the program's
																							// language
			texts = ResourceBundle.getBundle("resources/texts", loc);
			TITLE = texts.getString("TITLE");
			LANGUAGE = texts.getString("LANGUAGE");
			ENGLISH = texts.getString("ENGLISH");
			CHINESE = texts.getString("CHINESE");
			RESET = texts.getString("RESET");
			SOLUTION = texts.getString("SOLUTION");
			SAVE = texts.getString("SAVE");
			LOAD = texts.getString("LOAD");
			RANDOM = texts.getString("RANDOM");
			CREATE = texts.getString("CREATE");
			TIME = texts.getString("TIME");
			POINT = texts.getString("POINT");
			GAME = texts.getString("GAME");
			NEW = texts.getString("NEW");
			EXIT = texts.getString("EXIT");
			HELP = texts.getString("HELP");
			COLORS = texts.getString("COLORS");
			ABOUT = texts.getString("ABOUT");
			COLOR1 = texts.getString("COLOR1");
			COLOR2 = texts.getString("COLOR2");
			COLOR3 = texts.getString("COLOR3");
			MARK = texts.getString("MARK");
		} catch (Exception e) {
			;
		}
	}

	/**
	 * Method name: initializePlayPanel Purpose: Implements the visuialization for
	 * the play panel and allocate each part to their location Algorithm: Invoke
	 * functioanlities from internal packages to intailize and set up the panel
	 * 
	 * @param panel - the play panel
	 */
	public void initializePlayPanel(Container panel) {

		FlowLayout layout = new FlowLayout(FlowLayout.CENTER, 1, 1);
		panel.setLayout(layout);

		JLabel logoLabel = new JLabel(new ImageIcon("images/logo.png"));
		JPanel logoPanel = new JPanel();
		topPanel = new JPanel();
		boardPanel = new JPanel();
		leftPanel = new JPanel(); // Initialize the required panels

		boardPanel.setBackground(Color.black);

		logoPanel.setPreferredSize(new Dimension(148, 100));
		logoPanel.add(logoLabel);
		panel.add(logoPanel);
		topPanel.setPreferredSize(new Dimension(600, 100));
		panel.add(topPanel);
		leftPanel.setPreferredSize(new Dimension(148, 599));
		panel.add(leftPanel);
		boardPanel.setPreferredSize(new Dimension(600, 599));
		panel.add(boardPanel); // Set the layout of the panel
	}

	/**
	 * Method name: initializeControl Purpose: Implements the visuialization for the
	 * control panel and allocate each part to their location Algorithm: Invoke
	 * functioanlities from internal packages to intailize and set up the panel
	 * 
	 * @param panel - the control panel
	 */
	public void initializeControl(Container panel) {

		BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(layout);

		markButton = new JRadioButton(MARK);
		resetButton = new JButton(RESET);
		solutionButton = new JButton(SOLUTION);
		saveButton = new JButton(SAVE);
		loadButton = new JButton(LOAD);
		randomButton = new JButton(RANDOM);
		creatButton = new JButton(CREATE); // Initialize the required button
		sizeField = new JTextField();
		SizeField = new JTextField();
		JPanel emptyPanel = new JPanel();
		JPanel markPanel = new JPanel();
		JPanel savePanel = new JPanel();
		JPanel creatpanel = new JPanel();
		JPanel randompanel = new JPanel();
		JPanel solutionpanel = new JPanel();
		JPanel resetpanel = new JPanel(); // Initialize the required panels

		sizeField.setPreferredSize(new Dimension(100, 30));
		SizeField.setPreferredSize(new Dimension(100, 30));
		markPanel.add(markButton);
		creatpanel.add(SizeField);
		creatpanel.add(creatButton);
		randompanel.add(sizeField);
		randompanel.add(randomButton);
		savePanel.add(saveButton);
		savePanel.add(loadButton);
		solutionpanel.add(solutionButton);
		resetpanel.add(resetButton);
		panel.add(emptyPanel);
		panel.add(markPanel);
		panel.add(creatpanel);
		panel.add(randompanel);
		panel.add(savePanel);
		panel.add(solutionpanel);
		panel.add(resetpanel); // Set the layout of the panel

	}

	/**
	 * Method name: initializeInfo Purpose: Implements the visuialization for the
	 * information panel and allocate each part to their location Algorithm: Invoke
	 * functioanlities from internal packages to intailize and set up the panel
	 * 
	 * @param panel - the information panel
	 */
	public void initializeInfo(Container panel) {

		JPanel infoPanel = new JPanel();
		JPanel timePanel = new JPanel();
		JPanel pointPanel = new JPanel();
		JPanel dispalyPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 1, 1));
		infoField = new JTextArea();
		timeField = new JTextField();
		pointField = new JTextField();
		JLabel timeLabel = new JLabel(TIME, JLabel.CENTER);
		JLabel pointLabel = new JLabel(POINT, JLabel.CENTER); // Initialize the required panels

		infoPanel.setPreferredSize(new Dimension(590, 230));
		infoField.setPreferredSize(new Dimension(588, 230));
		infoPanel.add(infoField);
		panel.add(infoPanel);
		timePanel.setPreferredSize(new Dimension(380, 114));
		timeField.setPreferredSize(new Dimension(100, 30));
		timePanel.add(timeLabel);
		timePanel.add(timeField);
		pointPanel.setPreferredSize(new Dimension(380, 114));
		dispalyPanel.add(timePanel);
		pointField.setPreferredSize(new Dimension(100, 30));
		pointPanel.add(pointLabel);
		pointPanel.add(pointField);
		dispalyPanel.add(pointPanel);
		dispalyPanel.setPreferredSize(new Dimension(380, 230));
		panel.add(dispalyPanel); // Set the layout of the panel

	}

	/**
	 * Method name: initializeBoard Purpose: Implements the visuialization for the
	 * board panel Algorithm: Invoke functioanlities from internal packages to intailize and set up the panel
	 * 
	 * @param panel - the board panel
	 * @param size - Dimension
	 */
	public void initializeBoard(Container panel, int size) {
		GridLayout layout = new GridLayout(size, size, 1, 1);
		panel.setLayout(layout);
		p = new JPanel[size][size];			// Initialize the board panels

	}

	/**
	 * Method name: initializeTop Purpose: Implements the visuialization for the top
	 * panel and allocate each part to their location Algorithm: Invoke
	 * functioanlities from internal packages to intailize and set up the panel,
	 * display of each information by for loop control
	 * 
	 * @param panel - the top panel
	 * @param size - Dimension
	 * @param s - characters to display
	 */
	public void initializeTop(Container panel, int size, String s) {

		StringTokenizer st = new StringTokenizer(s, " ");
		st.nextToken();
		GridLayout layout = new GridLayout(1, size, 1, 1);
		panel.setLayout(layout); // initialization panel
		for (int i = 0; i < size; i++) {
			JLabel l = new JLabel(st.nextToken(), JLabel.CENTER); // Show Pattern Information
			panel.add(l);
		}

	}

	/**
	 * Method name: initializeLeft Purpose: Implements the visuialization for the
	 * left panel and allocate each part to their location Algorithm: Invoke
	 * functioanlities from internal packages to intailize and set up the panel,
	 * display of each information by for loop control
	 * 
	 * @param panel - the left panel
	 * @param size - Dimension
	 * @param s - characters to display
	 */
	public void initializeLeft(Container panel, int size, String s) {

		StringTokenizer st = new StringTokenizer(s, " ");
		st.nextToken();
		GridLayout layout = new GridLayout(size, 1, 1, 1);
		panel.setLayout(layout); // initialization panel
		for (int i = 0; i < size; i++) {
			JLabel l = new JLabel(st.nextToken(), JLabel.CENTER); // Show Pattern Information
			panel.add(l);
		}

	}

	/**
	 * Method name: time Purpose: Displays the past seconds in the text field
	 * Algorithm: print the incoming character
	 * 
	 * @param panel - time text field
	 * @param second - second
	 */
	public void setTime(JTextField panel, int second) {
		panel.setText(String.valueOf(second) + "s");			//print second
	}

	/**
	 * Method name: infotext Purpose: Displays the information above the text area
	 * Algorithm: Implement the string printing by insert function
	 * 
	 * @param panel - input text area
	 * @param s     - the string includes the outputing inforamtion
	 */
	public void infotext(JTextArea panel, String s) {
		panel.insert(s + "\n", 0); // Display information
	}
	/**
	 * Method name: pointtext Purpose: Displays the information above the text field
	 * Algorithm: Implement the int printing by insert function
	 * 
	 * @param panel - point text field
	 * @param point - point
	 */
	public void pointtext(JTextField panel, int point) {
		panel.setText(String.valueOf(point));			//print point
	}

	/**
	 * Method name: initializeSolution Purpose:  Implements the visuialization for the
	 * Solution dialog and allocate each part to their location  
	 * Algorithm: Invoke functioanlities from internal packages to intailize and set up the dialog
	 * @param size - Dimension
	 */
	public void initializeSolution(int size) {
		dialogS = new JDialog(frame, SOLUTION, true);
		dialogS.setSize(600, 600);
		GridLayout layout = new GridLayout(size, size, 2, 2);
		dialogS.setLayout(layout);				// initialization dialog
	}
	/**
	 * Method name: endgame Purpose:  Implements the visuialization for the
	 * end dialog and allocate each part to their location  
	 * Algorithm: Invoke functioanlities from internal packages to intailize and set up the dialog
	 * 
	 * @param point - point
	 * @param size - Dimension
	 */
	public void endgame(int point, int size) {
		JDialog dialogE = new JDialog(frame, "END", true);		// initialization dialog
		if (point == size * size) {						//Judging whether to win
			ImageIcon img = new ImageIcon("images/gamewinner.png");
			JLabel endLabel = new JLabel(img);
			dialogE.add(endLabel);
			dialogE.setSize(img.getIconWidth(), img.getIconHeight());
			dialogE.setVisible(true);
		} else {
			ImageIcon img = new ImageIcon("images/gameend.png");
			JLabel endLabel = new JLabel(img);
			dialogE.add(endLabel);
			dialogE.setSize(img.getIconWidth(), img.getIconHeight());
			dialogE.setVisible(true);
		}

	}
	/**
	 * Method name: addListener Purpose: Set the component's listener 
	 * Algorithm: Pass in the listener from the control class and assign it to each component
	 * 
	 * @param AL - incoming listener
	 */
	public void addListener(ActionListener AL) {

		english.addActionListener(AL);		//add listener
		chinese.addActionListener(AL);
		new1.addActionListener(AL);
		colors.addActionListener(AL);
		C1button.addActionListener(AL);
		C2button.addActionListener(AL);
		C3button.addActionListener(AL);
		exit.addActionListener(AL);
		about.addActionListener(AL);
		solution.addActionListener(AL);
		randomButton.addActionListener(AL);
		loadButton.addActionListener(AL);
		saveButton.addActionListener(AL);
		creatButton.addActionListener(AL);
		resetButton.addActionListener(AL);
		solutionButton.addActionListener(AL);
		markButton.addActionListener(AL);
	}
	
	/**
	 * Method name: newDialog Purpose: Set a popup and return the information used to create it
	 * Algorithm:Use the built-in input popup to allow user input
	 * 
	 * @return s-A string of information to create
	 */
	public String newDialog() {
		String s = JOptionPane.showInputDialog(null, "Enter the string or Dimension to create the pattern",
				"Creat new pattern", JOptionPane.QUESTION_MESSAGE);			// initialization dialog
		return s;
	}

	/**
	 * Method name: colorChoose Purpose: Sets the popup and returns the selected color
	 * Algorithm: Use the built-in color picker popup to let the user choose
	 * 
	 * @return color - selected color
	 */
	public Color colorChoose() {
		Color color = JColorChooser.showDialog(dialogC, "color", null);		// initialization dialog
		return color;
	}
	
	/**
	 *  Method name: about Purpose: Show an information popup
	 * Algorithm: Use built-in methods to display information
	 */
	public void about() {
		JOptionPane.showMessageDialog(null,
				"Game Created by Haoyun Deng! \n The objective is to correctly fill in the grid according to the clues. \n"
						+ "Spaces will either be empty or filled, and when all of the squares are correctly set, you will have won, revealing the picture.",
				"About", JOptionPane.QUESTION_MESSAGE);			// initialization dialog

	}

}
