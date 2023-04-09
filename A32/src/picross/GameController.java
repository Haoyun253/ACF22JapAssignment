package picross;
/**
 * File name: GameController.java
 * Identification: Haoyun Deng 04101223
 * Course: CST 8221-JAP, Lab Section: 301
 * Assignment: A32
 * Professor: Paulo Sousa
 * Date: 4/09/2023
 * Compiler: Eclipse IDE
 * Purpose: Create and control arrays storing patterns and user input
 */
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;
/**
 * Class name: data Methods list: 1.baseWindow 2.GameController
 * 3.start 4.refresh 5.refreshPaly 6.creat
 * Constants list: NULL
 * purpose: Realize the functions related to game control and monitoring
 * 
 * @author Haoyun Deng
 * @version 1.0
 * @see java.util
 * @since 17.0.3
 */
public class GameController {
	
	/**
	 * View component
	 */
	private GameView V;

	/**
	 * Model component
	 */
	private GameModel M;
	
	/**
	 * Timer class
	 */
	Timer timer = new Timer();

	/**
	 * Timer Task
	 */
	TimerTask timerTask;
	
	/**
	 * Constructor
	 * 
	 * @param theModel Model to be used
	 * @param theView  View to be updated
	 */
	public GameController(GameModel theModel, GameView theView) {
		this.M = theModel;
		this.V = theView;
	}
	
	/**
	 * Starts the application
	 */
	public void start() {	
		V.Splash();
		V.baseWindow();
		V.initializeBoard(V.boardPanel,M.getMatrixSize());
		creat();
		V.initializeTop(V.topPanel,M.getMatrixSize(),M.printTop());
		V.initializeLeft(V.leftPanel,M.getMatrixSize(),M.printLeft());
		V.addListener(new FeatureListener());
		V.frame.setResizable(false);
		V.frame.setVisible(true);
		V.frame.revalidate();
		V.frame.repaint();			//Initialize the whole program
		startTimer();
		
	}
	
	/**
	 * Method name: refresh Purpose: Reload the interface again
	 *  Algorithm: null
	 * 
	 */
	public void refresh() {

		V.frame.removeAll();
		V.frame.setVisible(false);
		V.frame.revalidate();
		V.baseWindow();
		V.initializeBoard(V.boardPanel,M.getMatrixSize());
		creat();
		V.initializeTop(V.topPanel,M.getMatrixSize(),M.printTop());
		V.initializeLeft(V.leftPanel,M.getMatrixSize(),M.printLeft());
		V.addListener(new FeatureListener());
		V.frame.repaint();
		V.frame.setVisible(true); // to refresh
	}

	/**
	 * Method name: refresh Purpose: Reload the paly panel again
	 *  Algorithm: null
	 * 
	 */
	public void refreshPaly() {

		V.boardPanel.removeAll();
		V.topPanel.removeAll();
		V.leftPanel.removeAll();
		V.frame.setVisible(false);
		V.initializeBoard(V.boardPanel,M.getMatrixSize());
		creat();
		V.initializeTop(V.topPanel,M.getMatrixSize(),M.printTop());
		V.initializeLeft(V.leftPanel,M.getMatrixSize(),M.printLeft());
		V.frame.setVisible(true); // to refresh

	}
	/**
	 * Method name: creat Purpose:Create swappable panels and listen for play functionality
	 *  Algorithm: Use listeners to link model and view classes, and interact by clicking
	 */
	public void creat() {

		for (int i = 0; i < M.getMatrixSize(); i++) { // Create a panel matrix
			for (int j = 0; j < M.getMatrixSize(); j++) {
				int row = i;
				int column = j;
				V.p[row][column]=new JPanel();
				switch (M.getState(row, column)) {
				case 0:
					V.p[row][column].setBackground(Color.WHITE);
					V.p[row][column].addMouseListener(new MouseListener() {
						
						@Override
						public void mouseClicked(MouseEvent e) {
						}

						@Override
						public void mousePressed(MouseEvent e) {
							String step;
							step = "Pos " + column + "," + row + " clicked";
							V.infotext(V.infoField, step); // output information
							if (V.mode == 1) { // check mode
								if (V.mark == 1) {					//check mark
									if (M.judge(row, column, 1) == true) {
										V.p[row][column].setBackground(V.color1);
										M.update(row, column, 1);
										M.updatePoint(1);
										V.pointtext(V.pointField,M.getPoint());
									} else {
										V.p[row][column].setBackground(V.color3);
										M.update(row, column, 3);
										M.updatePoint(-1);
										V.pointtext(V.pointField,M.getPoint());
									}
								} else {				
									M.update(row, column, 0);
									if (M.judge(row, column, 0) == true) {
										V.p[row][column].setBackground(V.color2);
										M.update(row, column, 2);
										M.updatePoint(1);
										V.pointtext(V.pointField,M.getPoint());
									} else {
										V.p[row][column].setBackground(V.color3);
										M.update(row, column, 3);
										M.updatePoint(-1);
										V.pointtext(V.pointField,M.getPoint());
									}
								}
								V.p[row][column].removeMouseListener(this);
								if (M.getStep() == M.getMatrixSize() * M.getMatrixSize()) {			//if all clicked
									V.infotext(V.infoField, "Game over, you got "+M.getPoint()+" points and spent "+M.getSeconds()+" seconds.");
									V.endgame(M.getPoint(),M.getMatrixSize());
									M.SLPoint(2);
								}
							} else {
								if (V.p[row][column].getBackground() == Color.WHITE) { // modify array state
									V.p[row][column].setBackground(V.color1);
									M.set(row, column, 1);
								} else {
									V.p[row][column].setBackground(Color.WHITE);
									M.set(row, column, 0);
								}
							}

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
					break;
				case 1:
					V.p[row][column].setBackground(V.color1);
					break;
				case 2:
					V.p[row][column].setBackground(V.color2);
					break;
				case 3:
					V.p[row][column].setBackground(V.color3);
					break;
				}
				V.boardPanel.add(V.p[row][column]);			//choose the color
			}
		}

	}
	/**
	 * Listener (inner class) to FeatureListener
	 * 
	 * @author Haoyun Deng
	 *
	 */
	class FeatureListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == V.english) {
				V.updateLanguage(0); // Call the text replacement method
				refresh();
			}

			if (e.getSource() == V.chinese) {
				V.updateLanguage(1); // Call the text replacement method
				refresh();
			}
			
			if (e.getSource() == V.new1) {		//input string
				M.newGame(V.newDialog());
				M.setSeconds(0);
				V.pointtext(V.pointField,M.getPoint());
				refreshPaly();
			}
			
			if (e.getSource() == V.colors) {		//show popup
				V.dialogC.setVisible(true);
			}
			
			if (e.getSource() == V.C1button) {		//choose the color
				V.color1=V.colorChoose();
				V.C1panel.setBackground(V.color1);
			}
			
			if (e.getSource() == V.C2button) {		//choose the color
				V.color2=V.colorChoose();
				V.C2panel.setBackground(V.color2);
			}
			
			if (e.getSource() == V.C3button) {			//choose the color
				V.color3=V.colorChoose();
				V.C3panel.setBackground(V.color3);
			}
			
			if (e.getSource() == V.exit) {		//turn off an app
				V.frame.dispose();
			}
			
			if (e.getSource() == V.about) {		//show popup
				V.about();
			}
			
			if (e.getSource() == V.solution) {
				V.initializeSolution(M.getMatrixSize());
				for (int i = 0; i < M.getMatrixSize(); i++) { // Create a panel matrix
					for (int j = 0; j < M.getMatrixSize(); j++) {
						JPanel p = new JPanel();
						int row = i;
						int column = j;
						switch (M.getSolution(row, column)) {
						case 0:
							p.setBackground(V.color1);
							break;
						case 1:
							p.setBackground(V.color2);
							break;
						}
						V.dialogS.add(p);
					}
				}
				V.dialogS.setVisible(true);
			}
			
			if (e.getSource() == V.randomButton) {
				String size;
				int gridNumber; 
				size = V.sizeField.getText();
				try {

					gridNumber = Integer.parseInt(size);
					if (gridNumber > 1) {
						M.setMatrixSize(gridNumber);
						M.setArray(0);
						M.setArray(1);
						M.randomly(); // generate random array
						refreshPaly();
					}

				} catch (NumberFormatException NFE) {
					;
				}
				M.setSeconds(0);
				M.SLPoint(2);
				V.pointtext(V.pointField,M.getPoint());
			}
			
			if (e.getSource() == V.loadButton) {
				M.loadArray(); // copy array
				M.SLPoint(0);
				V.pointtext(V.pointField,M.getPoint());
				refreshPaly();
			}
			
			if (e.getSource() == V.saveButton) {
				M.saveArray(); // copy array
				M.SLPoint(1);
				V.pointtext(V.pointField,M.getPoint());
			}
			
			if (e.getSource() == V.creatButton) {
				String size;
				int gridNumber;
				size = V.SizeField.getText();
				try {

					gridNumber = Integer.parseInt(size);
					if (gridNumber > 1) { // Judging the current mode
						if (V.mode == 1) {
							M.setMatrixSize(gridNumber);
							M.setArray(0);
							M.setArray(1);
							refreshPaly();
							V.infotext(V.infoField, "Creat set");
							V.mode = 0;
						} else {
							System.out.println(M.getMatrixSize());
							M.setArray(1);
							refreshPaly();
							V.infotext(V.infoField, "Creat reset");
							V.mode = 1;
						}
					}

				} catch (NumberFormatException NFE) {
					;
				}
				M.setSeconds(0);
				M.SLPoint(2);
				V.pointtext(V.pointField,M.getPoint());
			}
			
			if (e.getSource() == V.resetButton) {			//reset panel
				M.reset();
				M.SLPoint(2);
				V.pointtext(V.pointField,M.getPoint());
				refreshPaly();
				V.infotext(V.infoField, "interface reset");
				M.setSeconds(0);
			}
			
			if (e.getSource() == V.solutionButton) {
				V.initializeSolution(M.getMatrixSize());
				for (int i = 0; i < M.getMatrixSize(); i++) { // Create a panel matrix
					for (int j = 0; j < M.getMatrixSize(); j++) {
						JPanel p = new JPanel();
						int row = i;
						int column = j;
						switch (M.getSolution(row, column)) {
						case 0:
							p.setBackground(V.color1);
							break;
						case 1:
							p.setBackground(V.color2);
							break;
						}
						V.dialogS.add(p);
					}
				}
				V.dialogS.setVisible(true);
			}
			
			if (e.getSource() == V.markButton) {		//change mark mode
				if (V.mark == 1) {
					V.mark = 0;
					V.infotext(V.infoField, "Mark set");
				} else {
					V.mark = 1;
					V.infotext(V.infoField, "Mark reset");
				}
			}
		}

	}
	
	/**
	 Method name: startTimer Purpose:Update the seconds to implement the timing function 
	 *  Algorithm: Use the timetask class to update data
	 */
	private void startTimer() 
	{
		if (timerTask != null) 
		{
			timerTask.cancel();
		}

		// Timer task
		timerTask = new TimerTask() 
		{
			public void run() 
			{
				M.setSeconds(M.getSeconds()+1);		//update seconds
				V.setTime(V.timeField,M.getSeconds());
				
			}
		};
		try 
		{
			timer.scheduleAtFixedRate(timerTask, 0, 1000);
		} catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	
}
