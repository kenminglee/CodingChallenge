//*****************************************************************************************************************************
//inputOutput Class
//Ken Ming Lee
//Date: Monday March 27, 2017
//Programming Language: Java
//IDE: Eclipse Java EE 
//Version Number: 4.6.2
//*****************************************************************************************************************************
//<Class>
//This class contains all methods that are associated with getting input from user, and outputting results to an external file.
//<List Of Identifiers>
//Listed in each method
//*****************************************************************************************************************************
package lvlrPuzzle;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputOutput {

	
	/**getArray method: <br>
	 * This is a functional method that converts number in selected .txt files and convert it into an array
	 * <p>
	 * List Of Local Variables: <br>
	 * line - to make sure that it stops reading when it reaches a blank line <type String> <br>
	 * array - the name of the array where the puzzle will be stored <type int> <br>
	 * i - to make sure that each line in the .txt file is a new row in the array <type int> <br>
	 * fileReader - a FileReader object used to read from the .txt file <type FileReader> <br>
	 * bufferedReader - a BufferedReader object used to wrap FileReader <type BufferedReader> <br> 	
	 * @param dimension - the dimension of the array <type int>
	 * @param fileName - the directory of the file to be read <type String> 
	 * @return array - returns the array that contains the puzzle <type int>
	 */
	public static int [][] getArray(int dimension, String fileName){
		// The name of the file to open.

		// This will reference one line at a time
		String line = null;
		int [][] array = new int[dimension][dimension];
		int i = 0;
		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(fileName);
			
			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			while((line = bufferedReader.readLine()) != null) {
				//System.out.println(line);
				//Feed every line into a new row in a 2d array
				for(int c = 0; c<=line.length()-1; c++){
					array[i][c]= Character.digit(line.charAt(c),10);
				}
				i+=1;
			}   
			// Always close files.
			bufferedReader.close();         
		}
		catch(FileNotFoundException ex) {
			System.out.println(
					"Unable to open file '" + 
							fileName + "'");                
		}
		catch(IOException ex) {
			System.out.println(
					"Error reading file '" 
							+ fileName + "'");                  
			// Or we could just do this: 
			// ex.printStackTrace();
		}
		return array;
	}//end getArray method
	
	/**getFilename method: <br>
	 * This is a functional method that gets an input from the user to be used as the directory of the array
	 * <p>
	 * List Of Variables: <br>
	 * inputValid - to ensure that user cannot enter a blank directory accidentally <type boolean> <br>
	 * fileName - the directory of the file <type String> <br>
	 * @param none
	 * @return fileName - directory of the file that will be read from 
	 * @throws IOException
	 */
	public static String getFilename() throws IOException{
		boolean inputValid = false;
		String fileName = null;
		do{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Enter directory of array (e:\\TestFile2.txt)");
			fileName = br.readLine();
			if(fileName.equalsIgnoreCase(""))
				inputValid = false;
			else
				inputValid = true;
		}while(!inputValid);
		return fileName;
	}//end getFilename method
	
	/**getNum method: <br>
	 * This is a functional method that gets a valid number from the user.
	 * <p>
	 * List Of Variables: <br>
	 * inputValid - to ensure that the user has entered a number <type int> <br>
	 * num - the number that is entered by the user
	 * @param none
	 * @return num - the number entered by the user
	 * @throws IOException
	 */
	public static int getNum() throws IOException{
		boolean inputValid = false;
		int num = 0;
		do{
			try{
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				num = Integer.parseInt(br.readLine());
				inputValid = true;
			}catch(NumberFormatException e){
				inputValid = false;
				System.out.println("Not a valid number, please try again");
			}
		}while(inputValid==false);
		return num;
	}//end getNum method
	
	/**fileWrite method: <br>
	 * This is a procedural method that writes the final answer into a .txt file.
	 * <p>
	 * List Of Local Variables:<br>
	 * fileName - The directory of the text file that is written to <type String> <br>
	 * fileWriter - a FileWriter object used to write to files <type FileWriter> <br>
	 * bufferedWriter - a BufferedWriter object used to wrap fileWrite in order to write to files <type BufferedWriter> <br>
	 * <p>
	 * @param steps - the number of steps taken to solve the puzzle; the number that will be stored in .txt file <type int>  
	 * @return void
	 */
	public static void fileWrite(int steps){

		// The path and name of the file to write to.
		String fileName = "h:\\Answer.txt";

		try {
			// Assume default encoding.
			FileWriter fileWriter =
					new FileWriter(fileName);

			// Always wrap FileWriter in BufferedWriter.
			BufferedWriter bufferedWriter =
					new BufferedWriter(fileWriter);

			// Note that write() does not automatically
			// append a newline character.
			
			bufferedWriter.write("Number of moves required is "+ steps+"");
				
			bufferedWriter.newLine();
			// Always close files.
			bufferedWriter.close();
			System.out.println("The answer is now stored in " + fileName);
		}
		catch(IOException ex) {
			System.out.println("Error writing to file '"+ fileName + "'");
		}
	}//end fileWrite method
}
