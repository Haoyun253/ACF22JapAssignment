/**
 * File name: data.java
 * Identification: Haoyun Deng 04101223
 * Course: CST 8221-JAP, Lab Section: 301
 * Assignment: A12
 * Professor: Paulo Sousa
 * Date: 2/5/2023
 * Compiler: Eclipse IDE
 * Purpose: Create and control arrays storing patterns and user input
 */

package picross;

import java.util.Arrays;
import java.util.Random;

/**
 * Class name: data
 * Methods list:
 * 1.data
 * 2.setArray
 * 3.getArray
 * 4.getMatrixSize
 * 5.setMatrixSize
 * 6.reset
 * 7.randomly
 * 8.saveArray
 * 9.loadArray
 * 10.printTop
 * 11.printLeft
 * 12.update
 * 13.mark
 * 14.creat
 * Constants list: NULL
 * purpose: Create and control arrays storing patterns and user input
 * @author Haoyun Deng
 * @version 1.0
 * @see java.util
 * @since 17.0.3
 */
public class data {

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
	 * Method name: data
	 * Purpose: overload construcotr
	 * Algorithm: null
	 */
	public data() {		//The constructor initializes the array
		setArray(0);
		setArray(1);
	}

	/**
	 * Method name: setArray
	 * Purpose: Initialize the array
	 * Algorithm: Decides which array would be intialized by swtich case conditional statement
	 * @param choose - the number indicates the array option
	 */
	public void setArray(int choose) {

		
		switch (choose) {			//Choose which array to initialize
		case 0:
			this.solution=null;
			this.solution = new int[this.matrixSize][this.matrixSize];
		break;	
		case 1:
			this.playArea=null;
			this.playArea = new int[this.matrixSize][this.matrixSize];
		break;	
		default:
			this.saveArea=null;
			this.saveArea = new int[this.matrixSize][this.matrixSize];
		break;	
		}
	}

	/**
	 * Method name: getArray
	 * Purpose: Return the requried array
	 * Algorithm: Decides which array would be returned by swtich case conditional statement
	 * @param choose - the number indicates the array option
	 * @return the choosed array
	 */
	public int[][] getArray(int choose) {

		switch (choose) {		//Choose which array to return
		case 0:
			return this.solution;
		case 1:
			return this.playArea;
		default:
			return this.saveArea;
		}
	}

	/**
	 * Method name: getMatrixSize
	 * Purpose: return the size of the matrix
	 * Algorithm: null
	 * @return the size of the matrix
	 */
	public int getMatrixSize() {		//return size
		return matrixSize;
	}

	/**
	 * Method name: setMatrixSize
	 * Purpose: set the size of the matrix
	 * Algorithm: null
	 * @param matrixSize - the required size of the matrix
	 */
	public void setMatrixSize(int matrixSize) {
		this.matrixSize = matrixSize;		//update size
	}

	/**
	 * Method name: reset
	 * Purpose: reintialize the area array
	 * Algorithm: Traversal and clean each element in the array by for loop
	 */
	public void reset() {
		for (int i = 0; i < this.matrixSize; i++) {
			for (int j = 0; j < this.matrixSize; j++)
				playArea[i][j] = 0;			//clear play array
		}
	}

	/**
	 * Method name: randomly
	 * Purpose: generates a solution area randomly
	 * Algorithm: Traversal and randomly generates each element in the array by for loop
	 */

	public void randomly() {

		Random r = new Random();
		int number;
		for (int i = 0; i < this.matrixSize; i++) {
			for (int j = 0; j < this.matrixSize; j++) {
				number = r.nextInt(2);
				solution[i][j] = number;			//randomly generated pattern
			}	
		}
	}

	/**
	 * Method name: saveArray
	 * Purpose: save the current values on the array
	 * Algorithm: Traversal and copy each element in the array by for loop to save array
	 */
	public void saveArray() {
		
		for(int i = 0;i < playArea.length;i++){
			saveArea[i] = Arrays.copyOf(playArea[i],playArea[i].length);		//copy array to save array
		}
	}

	/**
	 * Method name: loadArray
	 * Purpose: retreieve the values from the saved array to the area array
	 * Algorithm: Traversal and retrieve each element in the array by for loop
	 */
	public void loadArray() {
		
		for(int i = 0;i < saveArea.length;i++){
			playArea[i] = Arrays.copyOf(saveArea[i],saveArea[i].length);		//copy array to play array
		}
	}

	/**
	 * Method name: printTop
	 * Purpose: validates the amount of the patterns in each colomn
	 * Algorithm: Traversal and check each element in the colomn by for loop
	 * @param column - the index of the column
	 * @return the amount of the patterns in the column
	 */

	public String printTop(int column) {
		String s="(";
		int number=0;
		for(int i = 0;i < solution.length;i++){		//Iterate over the pattern for each column
			if (solution[i][column]==1) {
				number=number+1;
			}else {
				if (number!=0)s=s+number+",";
				number=0;
			}
		}
		if (number!=0)s=s+number+",";
		number=0;
		s=s.substring(0,s.length()-1);
		s=s+")";
		if (s.equals(")")) {	
			s="(0)";
		}
		
		return s;		//return prompt information
		
	}

	/**
	 * Method name: printLeft
	 * Purpose: validates the amount of the patterns in each row
	 * Algorithm: Traversal and check each element in the row by for loop
	 * @param row - the index of the row
	 * @return the amount of the patterns in the row
	 */
	public String printLeft(int row) {
		String s="(";
		int number=0;
		for(int i = 0;i < solution.length;i++){		//Iterate over the pattern for each row
			if (solution[row][i]==1) {
				number=number+1;
			}else {
				if (number!=0)s=s+number+",";
				number=0;
			}
		}
		if (number!=0)s=s+number+",";
		number=0;
		s=s.substring(0,s.length()-1);
		s=s+")";
		if (s.equals(")")) {
			s="(0)";
		}
		return s;		//return prompt information
		
	}

	/**
	 * Method name: update
	 * Purpose: Records the inputs from the user
	 * Algorithm: null
	 * @param column - the index of the column
	 * @param row - the index of the row
	 * @param state - the state of the clicking status
	 */
	public void update(int row,int column,int state) {
		this.playArea[row][column]=state;		//update the state of the array
	}

	/**
	 * Method name: mark
	 * Purpose: Records the inputs from the user
	 * Algorithm: null
	 * @param column - the index of the column
	 * @param row - the index of the row
	 * @param state - the state of the clicking status
	 */
	public void mark(int row,int column,int state) {
		this.solution[row][column]=state;			//update the state of the array
	}

	/**
	 * Method name: create
	 * Purpose: Invoke the methods to intialize the whole array
	 * Algorithm: null
	 */
	public void creat() {		//initialize all arrays
		setArray(0);
		setArray(1);
		setArray(2);
	}
}
