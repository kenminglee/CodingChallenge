//****************************************************************************************************************************************
//bruteForceMethod Class
//Ken Ming Lee
//Date: Monday March 27, 2017
//Programming Language: Java
//IDE: Eclipse Java EE 
//Version Number: 4.6.2
//****************************************************************************************************************************************
//<Class>
//This class solves the puzzle using brute force method, and contains methods that are used exclusively by this class to solve the puzzle
//The way that this algorithm solve is that it will try to increment and decrement every number until the number of groups in the array 
//have decreased by at least 1, and then repeats the step over and over again until the number of groups becomes 1.
//<List Of Identifiers>
//leastMoves - to store the least number of moves used to solve the puzzle <type int>
//****************************************************************************************************************************************
package lvlrPuzzle;
public class BruteForce {
static int leastMoves = Integer.MAX_VALUE;
	
/**puzzleSolver method:<br>
	 * This procedural method calls other methods and itself to solve the puzzle recursively 
	 * <p>
	 * List of Local Variables:<br>
	 * oldArray - a copy of "array" that is used to compare and restore "array" to its original state<type int><br>
	 * oldGroup - a copy of "group" array that is used to compare to the "group" array <type int> <br>
	 * y - represents the y-coordinate (row) <type int>
	 * x - represents the x-coordinate (column) <type int>
	 * newSteps - number of steps used in incrementing / decrementing <type int>
	 * referenceArray- a copy of "array" that is passed as the argument of the next branch <type int> <br>
	 * referenceGroup - a copy of "group" that is passed as the argument of the next branch <type int> <br>
	 * <p>
	 * @param array - the array of the the puzzle <type int> <br>
	 * @param steps - to count the number of steps taken <type int> <br>
	 * @param group - a 2-dimensional array that stores 1 coordinate per group for all groups in the array <type int> <br>
	 * @param branchNumStr - to track the number of branches <type String> <br> 
	 * @return void <br>
	 */
	public static void puzzleSolver(int [][] array, int steps, int [][] group){
		//base case : if puzzle is solved compare the number of steps to leastMoves - if it is lower than leastMoves becomes the number of steps
		if(group.length==1){
			if(steps< leastMoves){
				leastMoves=steps;
			}
			return;
		}
		// If it has already used more number of steps compared to leastMoves, stop solving this branch and move on
		if(steps + (BruteForce.max(array)-BruteForce.min(array))>=leastMoves){
			return;
		}
		
		int [][] oldArray = new int [array.length][array.length];
		for(int i =0; i<=array.length-1;i++){
			for(int c=0; c<=array.length-1;c++){
				oldArray[i][c] = array[i][c];
			}
		}
		
		int [][] oldGroup = new int [group.length][2];
		for(int i =0; i<=group.length-1;i++){
			oldGroup[i][0] = group[i][0];
			oldGroup[i][1] = group[i][1];
			
		}
		
		
		for(int a = 0; a<=oldGroup.length-1; a++){
			int y = oldGroup[a][0];
			int x = oldGroup[a][1];
			if(BruteForce.checkIfMax(array,y,x)==true)
				continue;
			int newSteps = BruteForce.increment(array, oldArray, 0,y,x,group.length , group.length);
			if(newSteps==0)//so that if incrementing that particular group does not work it will move on 
				continue;
			int [][] referenceArray = new int[array.length][array.length];
			for(int i =0; i<=array.length-1;i++){
				for(int c=0; c<=array.length-1;c++){
					referenceArray[i][c] = array[i][c];
				}
			}
			for(int i =0; i<=array.length-1;i++){
				for(int c=0; c<=array.length-1;c++){
					array[i][c] = oldArray[i][c];
				}
			}
			int [][] referenceGroup = Functions.group(referenceArray);
			puzzleSolver(referenceArray,steps+newSteps,referenceGroup);
		}
		
		for(int a = 0; a<=oldGroup.length-1; a++){
			int y = oldGroup[a][0];
			int x = oldGroup[a][1];
			if(BruteForce.checkIfMin(array, y, x)==true)
				continue;
			int newSteps = BruteForce.decrement(array, oldArray,0,y,x,group.length,group.length);
			if(newSteps==0)
				continue;
			int [][] referenceArray = new int[array.length][array.length];
			for(int i =0; i<=array.length-1;i++){
				for(int c=0; c<=array.length-1;c++){
					referenceArray[i][c] = array[i][c];
				}
			}
			for(int i =0; i<=array.length-1;i++){
				for(int c=0; c<=array.length-1;c++){
					array[i][c] = oldArray[i][c];
				}
			}
			int [][] referenceGroup = Functions.group(referenceArray);
			puzzleSolver(referenceArray,steps+newSteps,referenceGroup);
		}
	}// end puzzleSolver method
		
	/**increment method:<br>
	 * This recursive functional method returns the number of maximum increments that can be done to the specified group (until the number of groups decrease by at least 1)
	 * <p>
	 * List of Local Variables:<br>
	 * boxSelectedB4 - an array used to see whether a coordinate have been incremented before <type boolean> <br>
	 * boxIsAlreadyPartOfGroup - an array used to see whether a coordinate have already been grouped <type boolean> <br>  	
	 * @param array - the array of the the puzzle that will be changed (increment or decrement) <type int> <br>
	 * @param oldArray - the array of the puzzle that will not change; it is used to revert "array" back to its original state if needed <type int> <br>  
	 * @param steps - to track the number of steps used to increment the particular coordinate <type int> <br>
	 * @param y - y-coordinate <type int> <br>
	 * @param x - x-coordinate <type int> <br>
	 * @param newGroupLength - number of groups in array <type int> <br>
	 * @param oldGroupLength - number of groups in oldArray <type int> <br>
	 * @return steps - total number of steps used in incrementing <type int> <br>
	 */
	public static int increment(int [][] array,int [][] oldArray,int steps,int y,int x,int newGroupLength,int oldGroupLength/* initial # of groups*/){
		
		boolean [][] boxSelectedB4 = new boolean[array.length][array.length];
		boolean [][] boxIsAlreadyPartOfGroup = new boolean[array.length][array.length];
		
		while(newGroupLength>=oldGroupLength){
			Functions.wipe(boxIsAlreadyPartOfGroup);
			Functions.wipe(boxSelectedB4);
			Functions.incrementBy1(array, y, x, array[y][x],boxSelectedB4);
			Functions.wipe(boxSelectedB4);
			newGroupLength = Functions.group(array).length;
			if(newGroupLength>=oldGroupLength&&BruteForce.checkIfMax(array, y, x)==true){
				for(int i =0; i<=array.length-1;i++){
					for(int c=0; c<=array.length-1;c++){
						array[i][c] = oldArray[i][c];
					}
				}
				return 0;
			}
			steps+=1;
		}
		return steps;
	}//end increment method

	/**decrement method:<br>
	 * This recursive functional method returns the number of maximum decrements that can be done to the specified group (until the number of groups decrease by at least 1)
	 * <p>
	 * List of Local Variables:<br>
	 * boxSelectedB4 - an array used to see whether a coordinate have been decremented before <type boolean> <br>
	 * boxIsAlreadyPartOfGroup - an array used to see whether a coordinate have already been grouped <type boolean> <br>  	
	 * @param array - the array of the the puzzle that will be changed (increment or decrement) <type int> <br>
	 * @param oldArray - the array of the puzzle that will not change; it is used to revert "array" back to its original state if needed <type int> <br>  
	 * @param steps - to track the number of steps used to decrement the particular coordinate <type int> <br>
	 * @param y - y-coordinate <type int> <br>
	 * @param x - x-coordinate <type int> <br>
	 * @param newGroupLength - number of groups in array <type int> <br>
	 * @param oldGroupLength - number of groups in oldArray <type int> <br>
	 * @return steps - total number of steps used in decrementing <type int> <br>
	 */
	public static int decrement(int [][] array,int [][] oldArray,int steps,int y,int x,int newGroupLength,int oldGroupLength){
		boolean [][] boxSelectedB4 = new boolean[array.length][array.length];
		boolean [][] boxIsAlreadyPartOfGroup = new boolean[array.length][array.length];
		
		while(newGroupLength>=oldGroupLength){
			Functions.wipe(boxIsAlreadyPartOfGroup);
			Functions.wipe(boxSelectedB4);
			Functions.decrementBy1(array, y, x, array[y][x],boxSelectedB4);
			Functions.wipe(boxSelectedB4);
			newGroupLength = Functions.group(array).length;
			if(newGroupLength>=oldGroupLength&&BruteForce.checkIfMin(array, y, x)==true){
				for(int i =0; i<=array.length-1;i++){
					for(int c=0; c<=array.length-1;c++){
						array[i][c] = oldArray[i][c];
					}
				}
				return 0;
			}
			steps+=1;
		}
		return steps;
	}//end decrement method
	
	/**checkIfMax method:<br>
	 * This functional method checks whether the coordinate given has the maximum value in the entire puzzle
	 * <p>
	 * List of Local Variables: <br>
	 * num - value of the coordinate in the puzzle <type int> <br>
	 * max - maximum value in the puzzle (array) <type int> 
	 * <p>
	 * @param array - array of the puzzle <type int> <br>
	 * @param y - y-coordinate <type int> <br>
	 * @param x - x-coordinate <type int> <br>
	 * @return "true" if that coordinate has the maximum value; "false" if the coordinate is not the maximum <type boolean>
	 */
	public static boolean checkIfMax(int [][] array, int y, int x){
		int num = array[y][x];
		int max = 0;
		for(int i=0; i<=array.length-1;i++){
			for(int c=0; c<=array.length-1; c++){
				if (array[i][c]> max)
					max = array[i][c];
			}
		}
		if(num>=max)
			return true;
		else
			return false;
			
	}//end of checkIfMax method
	
	/**checkIfMin method:<br>
	 * This functional method checks whether the coordinate given has the minimum value in the entire puzzle
	 * <p>
	 * List of Local Variables: <br>
	 * num - value of the coordinate in the puzzle <type int> <br>
	 * min - minimum value in the puzzle (array) <type int> 
	 * <p>
	 * @param array - array of the puzzle <type int> <br>
	 * @param y - y-coordinate <type int> <br>
	 * @param x - x-coordinate <type int> <br>
	 * @return "true" if that coordinate has the minimum value; "false" if the coordinate is not the minimum <type boolean>
	 */
	public static boolean checkIfMin(int [][] array, int y, int x){
		int num = array[y][x];
		int min = 100;
		for(int i=0; i<=array.length-1;i++){
			for(int c=0; c<=array.length-1; c++){
				if (array[i][c]< min)
					min = array[i][c];
			}
		}
		if(num<=min)
			return true;
		else
			return false;
			
	}//end checkIfMin method
	
	/**min method: <br>
	 * This functional method looks for the minimum value in the array
	 * <p>
	 * List of Local Variables: <br>
	 * min - minimum value in the puzzle <br>
	 * @param array - array of the puzzle
	 * @return min - returns the minimum number in the array
	 */
	public static int min(int[][] array){
		int min = 100;
		for(int i=0; i<=array.length-1;i++){
			for(int c=0; c<=array.length-1; c++){
				if (array[i][c]< min)
					min = array[i][c];
			}
		}
		return min;
	}//end min method
	
	/**max method: <br>
	 * This functional method looks for the maximum value in the array
	 * <p>
	 * List of Local Variables: <br>
	 * max - maximum value in the puzzle <br>
	 * @param array - array of the puzzle
	 * @return max - returns the minimum number in the array
	 */
	public static int max (int [][] array){
		int max = 0;
		for(int i=0; i<=array.length-1;i++){
			for(int c=0; c<=array.length-1; c++){
				if (array[i][c]> max)
					max = array[i][c];
			}
		}
		return max;
	}//end max method
}
