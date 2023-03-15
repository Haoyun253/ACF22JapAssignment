/**
 * File name: GameModel.java
 * Identification: Haoyun Deng 04101223
 * Course: CST 8221-JAP, Lab Section: 301
 * Assignment: A22
 * Professor: Paulo Sousa
 * Date: 3/14/2023
 * Compiler: Eclipse IDE
 * Purpose: Create and control arrays storing patterns and user input
 */

package picross;

import java.util.Arrays;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * Class name: data Methods list: 1.data 2.setArray 3.getArray 4.getMatrixSize
 * 5.setMatrixSize 6.reset 7.randomly 8.saveArray 9.loadArray 10.printTop
 * 11.printLeft 12.update 13.mark 14.creat 15.newGame 16.updatePoint 17.getPoint
 * 18.getStep 19.SLPoint 20.getState 21.getSolution
 * Constants list: NULL purpose: Create
 * and control arrays storing patterns and user input
 * 
 * @author Haoyun Deng
 * @version 1.0
 * @see java.util
 * @since 17.0.3
 */
public class GameModel {

	/**
	 * the array that obtains the generated patterns
	 */
	private int solution[][];

	/**
	 * the array that for user playing
	 */
	private int playArea[][];

	/**
	 * the array to save the current status
	 */
	private int saveArea[][];

	/**
	 * default size of the matrix
	 */
	private int matrixSize = 8;
	
	/**
	 * initial score
	 */
	private int point = 0;
		
	/**
	 * saved score
	 */
	private int Spoint = 0;

	/**
	 * initial step
	 */
	private int step = 0;

	/**
	 * saved step
	 */
	private int Sstep = 0;

	/**
	 * Method name: data Purpose: overload construcotr Algorithm: null
	 */
	public GameModel() { // The constructor initializes the array
		setArray(0);
		setArray(1);
		setArray(2);
	}

	/**
	 * Method name: setArray Purpose: Initialize the array Algorithm: Decides which
	 * array would be intialized by swtich case conditional statement
	 * 
	 * @param choose - the number indicates the array option
	 */
	public void setArray(int choose) {

		switch (choose) { // Choose which array to initialize
		case 0:
			this.solution = null;
			this.solution = new int[this.matrixSize][this.matrixSize];
			break;
		case 1:
			this.playArea = null;
			this.playArea = new int[this.matrixSize][this.matrixSize];
			break;
		default:
			this.saveArea = null;
			this.saveArea = new int[this.matrixSize][this.matrixSize];
			break;
		}
	}
	
	/**
	 * Method name: newGame Purpose: Initialize the target pattern with the returned string
	 * Algorithm: Determine what type the string belongs to and segment it with tokenizer
	 * @param s-input string
	 */
	public void newGame(String s) {
		int type = 0;
		String line;
		if (s == null) {
			return;
		}
		try {			//Judging whether it is a number
			Integer.parseInt(s);
			if (Integer.parseInt(s) > 1) {
				type = 1;
			}
		} catch (NumberFormatException e) {
			;
		}
		if (type == 1) {
			this.point = 0;
			this.step = 0;
			this.matrixSize = Integer.parseInt(s);
			creat();
			randomly();
		}
		if (s.contains(",")) {			//Determine whether it is a string
			this.point = 0;
			this.step = 0;
			StringTokenizer st = new StringTokenizer(s, ",");
			line = st.nextToken();
			int j = 0;
			this.matrixSize = line.length();
			creat();
			while (st.hasMoreTokens()) {
				for (int i = 0; i < this.matrixSize; i++) {
					char ch = line.charAt(i);
					if (ch == '0') {
						solution[j][i] = 0;
					} else {
						solution[j][i] = 1;
					}
				}
				j++;
				line = st.nextToken();
			}
			for (int i = 0; i < this.matrixSize; i++) {
				char ch = line.charAt(i);
				if (ch == '0') {
					solution[j][i] = 0;
				} else {
					solution[j][i] = 1;
				}
			}
			j++;
		}
	}

	/**
	 * Method name: getArray Purpose: Return the requried array Algorithm: Decides
	 * which array would be returned by swtich case conditional statement
	 * 
	 * @param choose - the number indicates the array option
	 * @return the choosed array
	 */
	public int[][] getArray(int choose) {

		switch (choose) { // Choose which array to return
		case 0:
			return this.solution;
		case 1:
			return this.playArea;
		default:
			return this.saveArea;
		}
	}

	/**
	 * Method name: getMatrixSize Purpose: return the size of the matrix Algorithm:
	 * null
	 * 
	 * @return the size of the matrix
	 */
	public int getMatrixSize() { // return size
		return matrixSize;
	}

	/**
	 * Method name: setMatrixSize Purpose: set the size of the matrix Algorithm:
	 * null
	 * 
	 * @param matrixSize - the required size of the matrix
	 */
	public void setMatrixSize(int matrixSize) {
		this.matrixSize = matrixSize; // update size
	}

	/**
	 * Method name: reset Purpose: reintialize the area array Algorithm: Traversal
	 * and clean each element in the array by for loop
	 */
	public void reset() {
		for (int i = 0; i < this.matrixSize; i++) {
			for (int j = 0; j < this.matrixSize; j++)
				playArea[i][j] = 0; // clear play array
		}
	}

	/**
	 * Method name: randomly Purpose: generates a solution area randomly Algorithm:
	 * Traversal and randomly generates each element in the array by for loop
	 */

	public void randomly() {

		Random r = new Random();
		int number;
		for (int i = 0; i < this.matrixSize; i++) {
			for (int j = 0; j < this.matrixSize; j++) {
				number = r.nextInt(2);
				solution[i][j] = number; // randomly generated pattern
			}
		}
	}

	/**
	 * Method name: saveArray Purpose: save the current values on the array
	 * Algorithm: Traversal and copy each element in the array by for loop to save
	 * array
	 */
	public void saveArray() {

		for (int i = 0; i < playArea.length; i++) {
			saveArea[i] = Arrays.copyOf(playArea[i], playArea[i].length); // copy array to save array
		}
	}

	/**
	 * Method name: loadArray Purpose: retreieve the values from the saved array to
	 * the area array Algorithm: Traversal and retrieve each element in the array by
	 * for loop
	 */
	public void loadArray() {

		for (int i = 0; i < saveArea.length; i++) {
			playArea[i] = Arrays.copyOf(saveArea[i], saveArea[i].length); // copy array to play array
		}
	}

	/**
	 * Method name: printTop Purpose: validates the amount of the patterns in each
	 * colomn Algorithm: Traversal and check each element in the colomn by for loop
	 * 
	 * @return the amount of the patterns in the column
	 */

	public String printTop() {
		String print = null;
		for (int j = 0; j < solution.length; j++) {
			String s = "(";
			int number = 0;
			for (int i = 0; i < solution.length; i++) { // Iterate over the pattern for each column
				if (solution[i][j] == 1) {
					number = number + 1;
				} else {
					if (number != 0)
						s = s + number + ",";
					number = 0;
				}
			}
			if (number != 0)
				s = s + number + ",";
			number = 0;
			s = s.substring(0, s.length() - 1);
			s = s + ")";
			if (s.equals(")")) {
				s = "(0)";
			}
			print = print + " " + s;
		}

		return print; // return prompt information

	}

	/**
	 * Method name: printLeft Purpose: validates the amount of the patterns in each
	 * row Algorithm: Traversal and check each element in the row by for loop
	 * 
	 * @return the amount of the patterns in the row
	 */
	public String printLeft() {
		String print = null;
		for (int j = 0; j < solution.length; j++) {
			String s = "(";
			int number = 0;
			for (int i = 0; i < solution.length; i++) { // Iterate over the pattern for each row
				if (solution[j][i] == 1) {
					number = number + 1;
				} else {
					if (number != 0)
						s = s + number + ",";
					number = 0;
				}
			}
			if (number != 0)
				s = s + number + ",";
			number = 0;
			s = s.substring(0, s.length() - 1);
			s = s + ")";
			if (s.equals(")")) {
				s = "(0)";
			}
			print = print + " " + s;
		}

		return print; // return prompt information

	}

	/**
	 * Method name: update Purpose: Records the inputs from the user Algorithm: null
	 * 
	 * @param column - the index of the column
	 * @param row    - the index of the row
	 * @param state  - the state of the clicking status
	 */
	public void update(int row, int column, int state) {
		this.playArea[row][column] = state; // update the state of the array
	}

	/**
	 * Method name: set Purpose: Records the inputs from the user Algorithm: null
	 * 
	 * @param column - the index of the column
	 * @param row    - the index of the row
	 * @param state  - the state of the clicking status
	 */
	public void set(int row, int column, int state) {
		this.solution[row][column] = state; // update the state of the array
	}

	/**
	 * Method name: updatePoint Purpose: Records the point and step from the user Algorithm: null
	 * @param state - change in score
	 */
	public void updatePoint(int state) {
		this.step++;
		this.point = this.point + state; // update the state of the point
		if (this.point < 0)
			this.point = 0;
	}

	/**
	 * Method name: getPoint Purpose: return the point of the user Algorithm:
	 * null
	 * 
	 * @return the point of the user
	 */
	public int getPoint() {			// return point
		return this.point;		
	}
	
	/**
	 * Method name: getStep Purpose: return the save point of the user Algorithm:
	 * null
	 * 
	 * @return the save point of the user
	 */
	public int getStep() {		// return step
		return this.step;
	}

	/**
	 * Method name: getStep Purpose: Save and read scores and steps
	 * Algorithm: By choosing to judge whether to save, read, or clear
	 * 
	 * @param state-selected function
	 */
	public void SLPoint(int state) {
		if (state == 1) {				//save state
			this.Spoint = this.point;
			this.Sstep = this.step;
		} else if (state == 0) {		//load state
			this.point = this.Spoint;
			this.step = this.Sstep;
		} else {
			this.point = 0;
			this.step = 0;
		}
	}
	
	/**
	 * Method name: judge Purpose: Determine whether the user clicked correctly
	 * Algorithm: null
	 * 
	 * @param column - the index of the column
	 * @param row    - the index of the row
	 * @param state  - the state of the clicking status
	 * @return -Right and wrong
	 */
	public boolean judge(int row, int column, int state) {
		if (state == 1) {				//Judging whether it is consistent with the correct answer
			if (this.solution[row][column] == 1) {		
				return true;
			} else {
				return false;
			}
		} else {
			if (this.solution[row][column] == 0) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * Method name: getStep Purpose: return the state of the paly array Algorithm:
	 * null
	 * 
	 * @param column - the index of the column
	 * @param row    - the index of the row
	 * @return the state of the paly array
	 */
	public int getState(int row, int column) {		//return state
		return this.playArea[row][column];
	}

	/**
	 * Method name: getStep Purpose: return the state of the solution array Algorithm:
	 * null
	 * 
	 * @param column - the index of the column
	 * @param row    - the index of the row
	 * @return the state of the solution array
	 */
	public int getSolution(int row, int column) {		//return state
		return this.solution[row][column];
	}

	/**
	 * Method name: create Purpose: Invoke the methods to intialize the whole array
	 * Algorithm: null
	 */
	public void creat() { // initialize all arrays
		setArray(0);
		setArray(1);
		setArray(2);
	}
}
