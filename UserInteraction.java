/*=========================================================================================================================
Program name: Lvlr Puzzle Solver
Author: Ken Ming Lee
Date: Monday March 27, 2017
Programming Language: Java
IDE: Eclipse Java EE 
Version Number: 4.6.2
===========================================================================================================================
Problem Definition - To find the least number of steps required to solve the Lvlr Puzzles
Input - User-input array, the dimension of the array and the method of solving
Output - Least number of moves required/ number of moves, depending on the method of solving
Process - To use recursion where necessary to solve the puzzle
===========================================================================================================================
List of Identifiers  - Listed in each method
===========================================================================================================================  
 */
package lvlrPuzzle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class UserInteraction {

	/** main method:<br>
	 * This procedural method is used to interact with the user, and to organize and call other methods where necessary
	 * <p>
	 * List of Local Variables:<br>
	 * br - a BufferedReader object used for keyboard input <type BufferedReader><br>
	 * fileName - the directory of the file entered by the user <type String><br>
	 * dimension - the dimension of the array <type int><br>
	 * array - a 2-dimensional array of the puzzle <type int><br>
	 * group - a 2-dimensional array that stores 1 coordinate per group for all groups in the array 
	 * (a "group" is a cluster of numbers with the same value)<type int><br>
	 * allNumOfSteps - the number of steps required to solve the puzzle using the "slow" method <type int><br>
	 * tryAgain - to see whether user wants to try again while solving puzzle manually <type boolean>
	 * <p>
	 * @param args<type String>
	 * @throws IOException
	 * @return void 
	 */
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String fileName = InputOutput.getFilename();
		System.out.println("What is the dimension of the array? (if 4x4 enter 4 etc...)");
		int dimension = InputOutput.getNum();
		int [][] array = InputOutput.getArray(dimension, fileName);
		if(array[0][0]==0){
			System.out.println("Error, please reload the program (remember to include double backslash and .txt)");
			return;
		}	
		int[][]group = Functions.group(array);
		System.out.println("How would you like to solve this array? (enter manual or auto");
		if(br.readLine().equalsIgnoreCase("auto")){
			System.out.println("Would you like to do it Slow or Fast?");
			System.out.println("Slow method is very accurate, but speed is compromised");
			System.out.println("Fast method is almost instantaneous, but the number of moves may not be the minimum");
			if(br.readLine().equalsIgnoreCase("slow")){
				BruteForce.puzzleSolver(array, 0,group);
				System.out.println("The least number of moves required is "+BruteForce.leastMoves);
				InputOutput.fileWrite(BruteForce.leastMoves);
			}else{
				for(int i = 0; i<100; i++){
					array = InputOutput.getArray(dimension,fileName);
					PeakAndTrough.totalSteps = 0;
					int allNumOfSteps = PeakAndTrough.puzzleSolver(array, 0);
					if(allNumOfSteps<PeakAndTrough.min)
						PeakAndTrough.min = allNumOfSteps;
				}
				System.out.println("This array can be solved in " + PeakAndTrough.min + " moves");
				InputOutput.fileWrite(PeakAndTrough.min);
			}
		}else{
			boolean tryAgain = false;
			do{
				array = InputOutput.getArray(dimension,fileName);
				Manual.solveManually(array,0);
				System.out.println("Try again? (y/n)");
				if(br.readLine().equalsIgnoreCase("y"))
					tryAgain = true;
			}while(tryAgain==true);
		}
			
	}//end main method
}
