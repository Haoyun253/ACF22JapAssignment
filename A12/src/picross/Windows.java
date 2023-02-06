/**
 * File name: Windows.java
 * Identification: Haoyun Deng 04101223
 * Course: CST 8221-JAP, Lab Section: 301
 * Assignment: A12
 * Professor: Paulo Sousa
 * Date: 2/5/2023
 * Compiler: Eclipse IDE
 * Purpose: Intialize the program to display its user interface
 */

package picross;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Class name: data
 * Methods list:
 * 1.baseWindow
 * 2.initializeLanguage
 * 3.updateLanguage
 * 4.initializePlayPanel
 * 5.initializeControl
 * 6.initializeInfo
 * 7.initializeBroad
 * 8.initializeTop
 * 9.initializeLeft
 * 10.time
 * 11.infotext
 * 12.main
 * Constants list: NULL
 * purpose: Intialize the program to display its user interface
 * @author Haoyun Deng
 * @version 1.0
 * @see java.awt , java.util , java.swing
 * @since 17.0.3
 */
public class Windows {

	/**
	 * The uesr inferface of the whole program
	 */
	private static JFrame frame;

	/**
	 * The timekeeper of the program
	 */
	private static long timing;

	/**
	 * The information outputing text field of the program
	 */
	private JTextArea infoField;

	/**
	 * The grade counting area
	 */
	private JTextField pointField;

	/**
	 * The user plaaying area
	 */
	private JPanel playPanel;

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
	public String TITLE	= "Haoyun253-Picoss";

	/**
	 * The default language
	 */
	public String LANGUAGE ="language";

	/**
	 * The default menu option
	 */
	public String ENGLISH ="english";

	/**
	 * The default menu option
	 */
	public String CHINESE ="chinese";

	/**
	 * The default resetting botton
	 */
	public String RESET ="Reset";

	/**
	 * The default solution botton
	 */
	public String SOLUTION ="Solution";

	/**
	 * The default saving botton
	 */
	public String SAVE ="Save";

	/**
	 * The default loading botton
	 */
	public String LOAD ="Load";

	/**
	 * The default randomliziation botton
	 */
	public String RANDOM ="Random";

	/**
	 * The default creating botton
	 */
	public String CREATE ="Create";

	/**
	 * The default time label
	 */
	public String TIME ="Time is: ";

	/**
	 * The default point label
	 */
	public String POINT ="Point is: ";

	/**
	 * The default gaming mode
	 */
	private int mode = 1;

	/**
	 * Data class
	 */
	data d = new data();


	/**
	 * Method name: baseWindow
	 * Purpose: Implements the visuialization for the whole program and allocate each part to their location
	 * Algorithm: Invoke functioanlities from internal packages to intailize and set up the major panel
	 */
	public void baseWindow() {
		
		frame = new JFrame(TITLE);
		d.randomly();
		frame.setSize(1000, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);			//Initialize window

		playPanel = new JPanel();
		JPanel controlPanel = new JPanel();
		JPanel infoPanel = new JPanel();
		playPanel.setBackground(Color.black);	//Initialize the major panel
		
		JMenuBar bar = new JMenuBar();
		frame.setJMenuBar(bar);
		FlowLayout layout = new FlowLayout(FlowLayout.CENTER, 1, 1);
		frame.setLayout(layout);
		playPanel.setPreferredSize(new Dimension(750, 700));
		frame.add(playPanel);
		controlPanel.setPreferredSize(new Dimension(227, 700));
		frame.add(controlPanel);
		infoPanel.setPreferredSize(new Dimension(980, 235));
		frame.add(infoPanel);			//Add menu and set panel layout

		initializeLanguage(bar);
		initializePlayPanel(playPanel);
		initializeControl(controlPanel);
		initializeInfo(infoPanel);				//call method to initialize
		
		frame.setVisible(true);
		frame.revalidate();
		frame.repaint();		//to refresh
	}


	/**
	 * Method name: initializeLanguage
	 * Purpose: Implements the processing of language sleaction by functioanlites from JMenu
	 * Algorithm: Intializes the menu and set up events
	 * @param panel - the bar of the menu
	 */
	public void initializeLanguage(Container panel) {
		
		JMenu language = new JMenu(LANGUAGE);
		panel.add(language);
		JMenuItem english = new JMenuItem(ENGLISH);
		JMenuItem chinese = new JMenuItem(CHINESE);
		language.add(english);
		language.add(chinese);				//initialization menu
		
		english.addActionListener((ActionListener) new ActionListener() {			//Listen to the click event to switch the language

			@Override
			public void actionPerformed(ActionEvent e) {
				updateLanguage(0);				//Call the text replacement method
				frame.removeAll();
				frame.setVisible(false);
				frame.revalidate();
				baseWindow();					
				frame.repaint();
				frame.setVisible(true);		//to refresh
			}

		});
			
		chinese.addActionListener((ActionListener) new ActionListener() {			//Listen to the click event to switch the language

			@Override
			public void actionPerformed(ActionEvent e) {
				updateLanguage(1);				//Call the text replacement method
				frame.removeAll();
				frame.setVisible(false);
				frame.revalidate();
				baseWindow();
				frame.repaint();
				frame.setVisible(true);		//to refresh

			}

		});
	}

	/**
	 * Method name: updateLanguage
	 * Purpose: Implements the languages switching based on required language
	 * Algorithm: Implements the language switching by if conditonal statement
	 * @param choose - the number indicates the language option
	 */
	public void updateLanguage(int choose) {
		String language = "";
		String country = "";
		if (choose==0){				//Check language options
			language = "en";
			country = "US";
		}else {
			language = "zh";
			country = "CN";
		}

		try {
			loc = new Locale.Builder().setLanguage(language).setRegion(country).build();			//Invoke a text file to replace the program's language
			texts = ResourceBundle.getBundle("resources/texts", loc);
			TITLE=texts.getString("TITLE");
			LANGUAGE=texts.getString("LANGUAGE");
			ENGLISH=texts.getString("ENGLISH");
			CHINESE=texts.getString("CHINESE");
			RESET=texts.getString("RESET");
			SOLUTION=texts.getString("SOLUTION");
			SAVE=texts.getString("SAVE");
			LOAD=texts.getString("LOAD");
			RANDOM=texts.getString("RANDOM");
			CREATE=texts.getString("CREATE");
			TIME=texts.getString("TIME");
			POINT=texts.getString("POINT");
		} catch (Exception e) {
			;
		}
	}


	/**
	 * Method name: initializePlayPanel
	 * Purpose: Implements the visuialization for the play panel and allocate each part to their location
	 * Algorithm: Invoke functioanlities from internal packages to intailize and set up the panel
	 * @param panel - the play panel
	 */
	public void initializePlayPanel(Container panel) {

		FlowLayout layout = new FlowLayout(FlowLayout.CENTER, 1, 1);
		panel.setLayout(layout);

		JPanel logoPanel = new JPanel();
		JPanel topPanel = new JPanel();
		JPanel boardPanel = new JPanel();
		JPanel leftPanel = new JPanel();			//Initialize the required panels

		boardPanel.setBackground(Color.black);

		logoPanel.setPreferredSize(new Dimension(148, 100));
		panel.add(logoPanel);
		topPanel.setPreferredSize(new Dimension(600, 100));
		panel.add(topPanel);
		leftPanel.setPreferredSize(new Dimension(148, 599));
		panel.add(leftPanel);
		boardPanel.setPreferredSize(new Dimension(600, 599));
		panel.add(boardPanel);				//Set the layout of the panel

		initializeBoard(boardPanel);
		initializeTop(topPanel);
		initializeLeft(leftPanel);			//call method to initialize
	}

	/**
	 * Method name: initializeControl
	 * Purpose: Implements the visuialization for the control panel and allocate each part to their location
	 * Algorithm: Invoke functioanlities from internal packages to intailize and set up the panel
	 * @param panel - the control panel
	 */
	public void initializeControl(Container panel) {

		BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(layout);

		JButton resetButton = new JButton(RESET);
		JButton solutionButton = new JButton(SOLUTION);
		JButton saveButton = new JButton(SAVE);
		JButton loadButton = new JButton(LOAD);
		JButton randomButton = new JButton(RANDOM);
		JButton creatButton = new JButton(CREATE);				//Initialize the required button
		JTextField sizeField = new JTextField();
		JTextField SizeField = new JTextField();
		JPanel emptyPanel = new JPanel();
		JPanel savePanel = new JPanel();
		JPanel creatpanel = new JPanel();
		JPanel randompanel = new JPanel();
		JPanel solutionpanel = new JPanel();
		JPanel resetpanel = new JPanel();				//Initialize the required panels

		sizeField.setPreferredSize(new Dimension(100, 30));
		SizeField.setPreferredSize(new Dimension(100, 30));
		creatpanel.add(SizeField);
		creatpanel.add(creatButton);
		randompanel.add(sizeField);
		randompanel.add(randomButton);
		savePanel.add(saveButton);
		savePanel.add(loadButton);
		solutionpanel.add(solutionButton);
		resetpanel.add(resetButton);
		panel.add(emptyPanel);
		panel.add(creatpanel);
		panel.add(randompanel);
		panel.add(savePanel);
		panel.add(solutionpanel);
		panel.add(resetpanel);					//Set the layout of the panel

		randomButton.addActionListener((ActionListener) new ActionListener() {			//Listen to click events to randomly generate patterns

			@Override
			public void actionPerformed(ActionEvent e) {
				String size;
				int gridNumber;
				size = sizeField.getText();
				try {

					gridNumber = Integer.parseInt(size);
					if (gridNumber > 1) {
						d.setMatrixSize(gridNumber);
						d.creat();
						d.randomly();			//generate random array
						playPanel.removeAll();
						frame.setVisible(false);
						initializePlayPanel(playPanel);
						frame.setVisible(true);			//to refresh
					}

				} catch (NumberFormatException NFE) {
					;
				}
			}

		});

		loadButton.addActionListener((ActionListener) new ActionListener() {			//Listen to click events to load
			@Override
			public void actionPerformed(ActionEvent e) {
				d.loadArray();			//copy array
				playPanel.removeAll();
				frame.setVisible(false);
				initializePlayPanel(playPanel);
				frame.setVisible(true);		//to refresh
			}	

		});

		saveButton.addActionListener((ActionListener) new ActionListener() {		//Listen to click events to save

			@Override
			public void actionPerformed(ActionEvent e) {
				d.saveArray();		//copy array
			}

		});

		creatButton.addActionListener((ActionListener) new ActionListener() {			//Listen to click events to enter creative mode

			@Override
			public void actionPerformed(ActionEvent e) {
				String size;
				int gridNumber;
				size = SizeField.getText();
				try {

					gridNumber = Integer.parseInt(size);
					if (gridNumber > 1) {			//Judging the current mode
						if (mode == 1) {
							d.setMatrixSize(gridNumber);
							d.creat();			//create patterns
							playPanel.removeAll();
							frame.setVisible(false);
							initializePlayPanel(playPanel);
							frame.setVisible(true);		//to refresh
							infotext(infoField, "Mark set");
							mode = 0;
						} else {
							System.out.println(d.getMatrixSize());
							d.setArray(1);
							d.setArray(2);
							playPanel.removeAll();
							frame.setVisible(false);
							initializePlayPanel(playPanel);
							frame.setVisible(true);		//to refresh
							infotext(infoField, "Mark reset");
							mode = 1;
						}
					}

				} catch (NumberFormatException NFE) {
					;
				}

			}

		});

		resetButton.addActionListener((ActionListener) new ActionListener() {		//Listen to click events to reset

			@Override
			public void actionPerformed(ActionEvent e) {
				d.reset();
				playPanel.removeAll();		//reset
				frame.setVisible(false);
				initializePlayPanel(playPanel);
				frame.setVisible(true);			//to refresh
				infotext(infoField, "interface reset");
			}

		});

	}

	/**
	 * Method name: initializeInfo
	 * Purpose: Implements the visuialization for the information panel and allocate each part to their location
	 * Algorithm: Invoke functioanlities from internal packages to intailize and set up the panel
	 * @param panel - the information panel
	 */
	public void initializeInfo(Container panel) {

		FlowLayout layout = new FlowLayout(FlowLayout.LEFT, 1, 1);

		JPanel infoPanel = new JPanel();
		JPanel timePanel = new JPanel();
		JPanel pointPanel = new JPanel();
		JPanel dispalyPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 1, 1));
		infoField = new JTextArea();
		JTextField timeField = new JTextField();
		pointField = new JTextField();
		JLabel timeLabel = new JLabel(TIME, JLabel.CENTER);
		JLabel pointLabel = new JLabel(POINT, JLabel.CENTER);			//Initialize the required panels

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
		panel.add(dispalyPanel);									//Set the layout of the panel

		time(timeField);
		timeField.setText(null);
	}

	/**
	 * Method name: initializeBoard
	 * Purpose: Implements the visuialization for the board panel and allocate each part to their location
	 * Algorithm: Invoke functioanlities from internal packages to intailize and set up the panel, also implements the
	 * panel matrix setup the clicking events by for loop control
	 * @param panel - the board panel
	 */
	public void initializeBoard(Container panel) {

		int size = 0;
		size = d.getMatrixSize();
		GridLayout layout = new GridLayout(size, size, 1, 1);
		panel.setLayout(layout);
		for (int i = 0; i < size; i++) {			//Create a panel matrix
			for (int j = 0; j < size; j++) {
				JPanel p = new JPanel();
				int row = i;
				int column = j;
				if (d.getArray(1)[i][j] == 0) {				//set color
					p.setBackground(Color.WHITE);
				} else {
					p.setBackground(Color.BLUE);
				}
				p.addMouseListener((MouseListener) new MouseListener() {			//Listen to the click event to let the user play
					@Override
					public void mouseClicked(MouseEvent e) {
						String step;
						step = "Pos " + column + "," + row + " clicked";
						infotext(infoField, step);			//output information
						if (mode == 1) {			//check mode
							if (p.getBackground() == Color.WHITE) {			//modify array state
								p.setBackground(Color.BLUE);
								d.update(row, column, 1);
							} else {
								p.setBackground(Color.WHITE);
								d.update(row, column, 0);
							}
						} else {
							if (p.getBackground() == Color.WHITE) {		//modify array state
								p.setBackground(Color.BLUE);
								d.mark(row, column, 1);
							} else {
								p.setBackground(Color.WHITE);
								d.mark(row, column, 0);
							}
						}
					}

					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub

					}

				});
				panel.add(p);
			}
		}

	}


	/**
	 * Method name: initializeTop
	 * Purpose: Implements the visuialization for the top panel and allocate each part to their location
	 * Algorithm: Invoke functioanlities from internal packages to intailize and set up the panel, implements thesearching
	 * of the patterns on the array by for loop control
	 * @param panel - the top panel
	 */
	public void initializeTop(Container panel) {

		int size = 0;
		size = d.getMatrixSize();
		GridLayout layout = new GridLayout(1, size, 1, 1);
		panel.setLayout(layout);		//initialization panel
		for (int i = 0; i < size; i++) {
			JLabel l = new JLabel(d.printTop(i), JLabel.CENTER);		//Show Pattern Information
			panel.add(l);
		}

	}

	/**
	 * Method name: initializeLeft
	 * Purpose: Implements the visuialization for the left panel and allocate each part to their location
	 * Algorithm: Invoke functioanlities from internal packages to intailize and set up the panel, implements thesearching
	 * of the patterns on the array by for loop control
	 * @param panel - the left panel
	 */
	public void initializeLeft(Container panel) {

		int size = 0;
		size = d.getMatrixSize();
		GridLayout layout = new GridLayout(size, 1, 1, 1);
		panel.setLayout(layout);			//initialization panel
		for (int i = 0; i < size; i++) {
			JLabel l = new JLabel(d.printLeft(i), JLabel.CENTER);			//Show Pattern Information
			panel.add(l);
		}

	}

	/**
	 * Method name: time
	 * Purpose: Displays the past seconds in the text field
	 * Algorithm: Implements the timekeeping by time functionalities from time package
	 * @param panel - time text field
	 */
	public void time(JTextField panel) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				String output = "";
				timing++;
				long ss = timing % 60;
				output = String.valueOf(ss);
				output = output + "s";	
				panel.setText(output);		//display seconds
			}
		}, 0, 1000);
	}

	/**
	 * Method name: infotext
	 * Purpose: Displays the information above the text area
	 * Algorithm: Implement the string printing by insert function
	 * @param panel - input text area
	 * @param s - the string includes the outputing inforamtion
	 */
	public void infotext(JTextArea panel, String s) {
		panel.insert(s + "\n", 0);			//Display information
	}

	/**
	 * Mehotd name : main
	 * purpose : the starting point of the whole program
	 * Algorithm: null
	 * @param args - the string arguments that are used for the program
	 */
	public static void main(String[] args) {
		Windows w = new Windows();			//Create window object
		w.baseWindow();
	}

}
