//***************************************************************************************************************************************
//manualMethod class
//Ken Ming Lee
//Date: Monday March 27, 2017
//Programming Language: Java
//IDE: Eclipse Java EE 
//Version Number: 4.6.2
//***************************************************************************************************************************************
//<Class>
//This class contains methods that are used to solve the puzzle manually
//<List Of Identifiers>
//none
//***************************************************************************************************************************************
package lvlrPuzzle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Manual {
	
	/**solveManually method: <br>
	 * This procedural method allows user to solve array manually, while keeping track of their number of moves
	 * <p>
	 * List of Local Variables: <br>
	 * group - a 2-dimensional array that stores 1 coordinate per group for all groups in the array <type int> <br>
	 * br - a BufferedReader object used for keyboard input <type BufferedReader> <br>
	 * y - y-coordinate <type int> <br>
	 * x - x-coordinate <type int> <br>
	 * boxSelectedB4 - an array used to see whether a coordinate have been incremented/decremented before <type boolean> 
	 * <p> 
	 * @param array - the array of the puzzle <type int> 
	 * @param steps - the number of steps used <type int> 
	 * @throws IOException 
	 * @result void
	 */
	public static void solveManually(int[][] array, int steps) throws IOException{
		
		for(int i = 0; i<array.length;i++){
			for(int c = 0; c<array.length; c++){
				System.out.print(array[i][c]+"  ");
			}
			System.out.println("");
		}
		System.out.println("You have moved "+ steps+ " step(s)");
		int [][] group = Functions.group(array);
		if(group.length==1){
			System.out.println("Congrats you solved the puzzle in "+ steps + " moves!");
			InputOutput.fileWrite(steps);
			return;
		}else{	
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Enter y-coordinate (row) that you would like to inc/dec - ranging from 0 to "+(array.length-1));
			int y = InputOutput.getNum();
			System.out.println("Enter x-coordinate (column) that you would like to inc/dec - ranging from 0 to "+(array.length-1));
			int x = InputOutput.getNum();
			boolean [][] boxSelectedB4 = new boolean[array.length][array.length];
			System.out.println("Would you like to increment or decrement? (Enter inc or dec)");
			if(br.readLine().equalsIgnoreCase("inc")){
				Functions.incrementBy1(array, y, x, array[y][x], boxSelectedB4);
				solveManually(array,steps+=1);
			}else{
				Functions.decrementBy1(array, y, x, array[y][x], boxSelectedB4);
				solveManually(array,steps+=1);
			}
		}	
	}
}
