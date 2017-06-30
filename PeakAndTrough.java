//******************************************************************************************************************************************************************
//peakAndTroughMethod Class
//Ken Ming Lee
//Date: Monday March 27, 2017
//Programming Language: Java
//IDE: Eclipse Java EE 
//Version Number: 4.6.2
//******************************************************************************************************************************************************************
//<Class>
//This class solves the puzzle by using the "peak" and "trough" method
//"peak" is when a group of numbers are surrounded by numbers that are smaller than the value of the group
//"trough" is when a group of numbers are surrounded by numbers that are larger than the value of the group
//This method solves the puzzle by calculating the number of peaks and troughs in the method:
//If number of peak> number of trough- it will increment trough to match the peak
//If number of trough> number of peak - it will decrement peak to match the trough
//It chooses the peak/trough to decrement/increment randomly
//Else if number of trough = number of peak - it will try both to see which one takes the least moves
//Although this method solves the puzzle very quickly, there are times where neither the peak nor the trough is incremented in the first step
//<List of Identifiers>
//min = number used to store the minimum steps used to solve the puzzle <type int>
//totalSteps = store the total number of steps used to solve the puzzle once <type int>
//******************************************************************************************************************************************************************
package lvlrPuzzle;
import java.util.Random;

public class PeakAndTrough {
	static int min = Integer.MAX_VALUE;
	static int totalSteps = 0;
	
	/**puzzleSolver method:<br>
	 * This functional method calls others and itself recursively to solve the puzzle
	 * <p>
	 * List of Local Variables:<br>
	 * boxSelectedB4 - changes from "false" to "true" once recursion has been through that cell <type boolean> <br>
	 * group - a 2-dimensional array that stores 1 coordinate per group for all groups in the array <type int> <br>
	 * totalNumOfPeakAndTrough - total number of peaks and troughs in the array <type int> <br>
	 * coordinateOfPeak - coordinates of all peaks in the array <type int> <br>
	 * coordinateOfTrough - coordinates of all troughs in the array <type int> <br>
	 * rand - a Random object used to generate random number within appropriate range <type Random> <br>
	 * i - a random number that has a range of between 0 <= i < the number of troughs <type int> <br>
	 * n - a random number that has a range of between 0 <= n < the number of peaks <type int> <br>
	 * steps - the number of steps <type int>
	 * <p>
	 * @param array - the array of the puzzle <type int>
	 * @param steps - the number of steps <type int>
	 * @return totalSteps - the totalSteps used to solve this puzzle once <type int>
	 */
	public static int puzzleSolver(int [][] array, int steps){
		boolean [][] boxSelectedB4 = new boolean[array.length][array.length];
		totalSteps+=steps;
		Functions.wipe(boxSelectedB4);
		int[][]group = Functions.group(array);
		
		if(group.length==1){
			return totalSteps;
		}
		
		int [] totalNumOfPeakAndTrough = PeakAndTrough.totalNumOfPeakAndTrough(array, group, boxSelectedB4);
		int [][] coordinateOfPeak = new int[totalNumOfPeakAndTrough[0]][2];
		int [][] coordinateOfTrough = new int[totalNumOfPeakAndTrough[1]][2];
		PeakAndTrough.coordinateOfPeakAndTrough(array, group, boxSelectedB4, coordinateOfPeak, coordinateOfTrough);		
	
		Random rand = new Random();
		int i = rand.nextInt(coordinateOfTrough.length);
		int n = rand.nextInt(coordinateOfPeak.length);
		if(coordinateOfPeak.length>coordinateOfTrough.length){
			steps = 0;	
			steps = PeakAndTrough.increment(array,coordinateOfTrough[i][0],coordinateOfTrough[i][1],boxSelectedB4);
			return puzzleSolver(array,steps);
			
			
		}else if (coordinateOfTrough.length > coordinateOfPeak.length){
			steps = 0;
			steps = PeakAndTrough.decrement(array, coordinateOfPeak[n][0], coordinateOfPeak[n][1], boxSelectedB4);
			return puzzleSolver(array,steps);
			
			
		}else{
			int random = rand.nextInt(2);
			if (random == 0){
				steps = 0;
				steps = PeakAndTrough.increment(array, coordinateOfTrough[i][0], coordinateOfTrough[i][1], boxSelectedB4);
				return puzzleSolver(array,steps);
			
			}else{
				steps = 0;
				steps = PeakAndTrough.decrement(array, coordinateOfPeak[n][0], coordinateOfPeak[n][1], boxSelectedB4);
				return puzzleSolver(array,steps);
			
		}
		}
			
	}//end puzzleSolver method
	
	/**countMembers method:<br>
	 * This procedural recursive method counts the number of members in the group of coordinate(y,x) and stores them in the array numOfMembers
	 * <p>
	 * List of Local Variables:<br>
	 * none 
	 * <p>
	 * @param array - array of the puzzle <type int>
	 * @param numOfMembers - the array where its first elements contains the number of members in the group of coordinate(y,x) <type int> 
	 * @param y - y-coordinate <type int> 
	 * @param x - x-coordinate <type int>
	 * @param valueOfBoxSelected - value of the coordinate (y,x) <type int>
	 * @param boxSelectedB4 - To ensure that when the method calls itself recursively, it does not reuse a coordinate that has been called before. <type boolean>
	 * @return void
	 */
	public static void countMembers(int [][] array, int [] numOfMembers /*equals to 0!*/, int y, int x, int valueOfBoxSelected, boolean[][] boxSelectedB4){
		try{
			if(array[y][x]!=valueOfBoxSelected||boxSelectedB4[y][x]){ 
				return;
			}else{
				
				numOfMembers[0] += 1;
				boxSelectedB4[y][x]=true;
				countMembers(array,numOfMembers,y,x+1,valueOfBoxSelected,boxSelectedB4);
				countMembers(array,numOfMembers,y,x-1,valueOfBoxSelected,boxSelectedB4);
				countMembers(array,numOfMembers,y+1,x,valueOfBoxSelected,boxSelectedB4);
				countMembers(array,numOfMembers,y-1,x,valueOfBoxSelected,boxSelectedB4);
			}
		}catch(ArrayIndexOutOfBoundsException e){
			return;	
		}
	}//end countMembers method
	
	/**merge method: <br>
	 * A recursive procedural method that stores all coordinates of the group into an array for the particular coordinate entered
	 * <p>
	 * List of Local Variables: <br>
	 * none
	 * <P>
	 * @param array - array of the puzzle <type int> 
	 * @param allCoordinatesInAGroup - this array contains all coordinates in that particular group <type int> 
	 * @param y - y-coordinate <type int> 
	 * @param x - x-coordinate <type int>
	 * @param valueOfBoxSelected -  value of the coordinate (y,x) <type int>
	 * @param boxSelectedB4 - To ensure that when the method calls itself recursively, it does not reuse a coordinate that has been called before. <type boolean>
	 * @param startingIndex - startingIndex ensures that it stores coordinates into allCoordinatesInAGroup without skipping a row/column
	 * @return void
	 */
	public static void merge(int[][] array, int[][] allCoordinatesInAGroup, int y, int x, int valueOfBoxSelected, boolean [][] boxSelectedB4, int [] startingIndex){
		try{
			if(array[y][x]!=valueOfBoxSelected||boxSelectedB4[y][x]){ 
				return;
			}else{
				
				allCoordinatesInAGroup[startingIndex[0]][0] = y;
				allCoordinatesInAGroup[startingIndex[0]][1] = x;
				boxSelectedB4[y][x]=true;
				startingIndex[0]+=1;
				merge(array,allCoordinatesInAGroup,y,x+1,valueOfBoxSelected,boxSelectedB4,startingIndex);
				merge(array,allCoordinatesInAGroup,y,x-1,valueOfBoxSelected,boxSelectedB4,startingIndex);
				merge(array,allCoordinatesInAGroup,y+1,x,valueOfBoxSelected,boxSelectedB4,startingIndex);
				merge(array,allCoordinatesInAGroup,y-1,x,valueOfBoxSelected,boxSelectedB4,startingIndex);
			}
		}catch(ArrayIndexOutOfBoundsException e){
			return;	
		}	
	}//end merge method
	
	/**isItAPeak method:<br>
	 * This functional method checks to see whether the group is a peak
	 * <p>
	 * List of Local Variables: <br>
	 * peakValid - the number of coordinates in the group that are a peak <type int> <br>
	 * y - y-coordinate <type int> <br>
	 * x - x-coordinate <type int> <br>
	 * @param array - array of the puzzle <type int>
	 * @param allCoordinatesInAGroup - this array contains all coordinates of a particular group <type int> 
	 * @param numOfMembers - the number of members in the group <type int>
	 * @return "true" if this group is a peak, "false" if this group is not a peak <type boolean>
	 */
	public static boolean isItAPeak(int [][] array, int [][] allCoordinatesInAGroup, int numOfMembers){
		int peakValid = 0;
		for(int i = 0; i<=allCoordinatesInAGroup.length-1; i++){
			int y = allCoordinatesInAGroup[i][0];
			int x = allCoordinatesInAGroup[i][1];
			if(array[y][x]>= PeakAndTrough.rightNeighbour(array, y, x) && array[y][x]>=PeakAndTrough.leftNeighbour(array,y,x) && array[y][x]>= PeakAndTrough.downNeighbour(array, y, x) && array[y][x]>=PeakAndTrough.upNeighbour(array, y, x))
				peakValid++;
		}
		//System.out.println("num of peaks: "+peakValid);
		if(peakValid==numOfMembers){
			//System.out.println("Its a peak");
			return true;
		}else{
			//System.out.println("Not a peak");
			return false;
	
		}
	}//end isItAPeak method
	
	/**isItATrough method:<br>
	 * This functional method checks to see whether the group is a trough
	 * <p>
	 * List of Local Variables: <br>
	 * troughValid - the number of coordinates in the group that are a trough <type int> <br>
	 * y - y-coordinate <type int> <br>
	 * x - x-coordinate <type int> <br>
	 * @param array - array of the puzzle <type int>
	 * @param allCoordinatesInAGroup - this array contains all coordinates of a particular group <type int> 
	 * @param numOfMembers - the number of members in the group <type int>
	 * @return "true" if this group is a trough, "false" if this group is not a trough <type boolean>
	 */
	public static boolean isItATrough(int [][] array, int [][] allCoordinatesInAGroup, int numOfMembers){
		int troughValid = 0;
		for (int i =0; i<=allCoordinatesInAGroup.length-1; i++){
			int y = allCoordinatesInAGroup[i][0];
			int x = allCoordinatesInAGroup[i][1];
			if(array[y][x]<= PeakAndTrough.rightNeighbour(array, y, x) && array[y][x]<=PeakAndTrough.leftNeighbour(array,y,x) && array[y][x]<= PeakAndTrough.downNeighbour(array, y, x) && array[y][x]<=PeakAndTrough.upNeighbour(array, y, x))
				troughValid++;
		}
		//System.out.println("num of troughs: " + troughValid);
		if(troughValid==numOfMembers){
			//System.out.println("Its a trough");
			return true;
		}else{
			//System.out.println("Not a trough");
			return false;
	
		}
	}//end isItATrough method
	
	/**rightNeighbour method: <br>
	 * This functional method looks at the number on the right of the selected coordinate
	 * <p>
	 * List of Local Variables: <br>
	 * none
	 * <p>
	 * @param array - array of the puzzle <type int>
	 * @param y - y-coordinate <type int>
	 * @param x - x-coordinate <type int>
	 * @return array[y][x+1] - the number to the right of the selected coordinate<type int>
	 */
	public static int rightNeighbour(int [][] array, int y, int x){
		try{
			return array[y][x+1];
		}catch(ArrayIndexOutOfBoundsException e){
			return array[y][x];
		}
	}//end rightNeighbour method
	
	/**leftNeighbour method: <br>
	 * This functional method looks at the number on the left of the selected coordinate
	 * <p>
	 * List of Local Variables: <br>
	 * none
	 * <p>
	 * @param array - array of the puzzle <type int>
	 * @param y - y-coordinate <type int>
	 * @param x - x-coordinate <type int>
	 * @return array[y][x-1] - the number to the left of the selected coordinate<type int>
	 */
	public static int leftNeighbour(int [][] array, int y, int x){
		try{
			return array[y][x-1];
		}catch(ArrayIndexOutOfBoundsException e){
			return array[y][x];
		}
	}//end leftNeighbour method
	
	/**upNeighbour method: <br>
	 * This functional method looks at the number above the selected coordinate
	 * <p>
	 * List of Local Variables: <br>
	 * none
	 * <p>
	 * @param array - array of the puzzle <type int>
	 * @param y - y-coordinate <type int>
	 * @param x - x-coordinate <type int>
	 * @return array[y-1][x] - the number above the selected coordinate <type int>
	 */
	public static int upNeighbour(int [][] array, int y, int x){
		try{
			return array[y-1][x];
		}catch(ArrayIndexOutOfBoundsException e){
			return array[y][x];
		}
	}//end upNeighbour method
	
	/**downNeighbour method: <br>
	 * This functional method looks at the number below the selected coordinate
	 * <p>
	 * List of Local Variables: <br>
	 * none
	 * <p>
	 * @param array - array of the puzzle <type int>
	 * @param y - y-coordinate <type int>
	 * @param x - x-coordinate <type int>
	 * @return array[y+1][x] - the number below the selected coordinate <type int>
	 */
	public static int downNeighbour(int [][] array, int y, int x){
		try{
			return array[y+1][x];
		}catch(ArrayIndexOutOfBoundsException e){
			return array[y][x];
		}
	}//end downNeighbour method

	/**totalNumOfPeakAndTrough method: <br>
	 * Functional method that finds and return the total number of peak(s) and trough(s) in the array: <br> 
	 * Index 0 stores the number of peaks, Index 1 stores the number of troughs
	 * <p> 
	 * List of Local Variables: <br>
	 * numOfMembers - This array stores the number of peaks or troughs <type int> <br>
	 * numOfPeak - number of peaks <type int> <br>
	 * numOfTrough - number of troughs <type int> <br>
	 * totalNumOfPeakAndTrough - This array stores number of peaks as its first element, and the number of troughs as the second element <type int> <br>
	 * coordinateY - y-coordinate <type int> <br>
	 * coordinateX - x-coordinate <type int> <br>
	 * allCoordinatesInAGroup - this array contains all coordinates of a particular group <type int> <br>
	 * startingIndex - the number of which the counter starts with <type int> <br>
	 * @param array - the array of the puzzle
	 * @param group - an array used to store one coordinate for each group, for all groups <type int> 
	 * @param boxSelectedB4 - To ensure that when the method calls itself recursively, it does not reuse a coordinate that has been called before. <type boolean>
	 * @return totalNumOfPeakAndTrough - This array stores number of peaks as its first element, and the number of troughs as the second element <type int>
	 */
	public static int[] totalNumOfPeakAndTrough(int [][] array, int [][] group, boolean [][] boxSelectedB4){
		int [] numOfMembers = new int [1];
		int numOfPeak = 0;
		int numOfTrough = 0;
		int [] totalNumOfPeakAndTrough = new int [2];
		for(int i=0; i<=group.length-1; i++){
			int coordinateY = group[i][0];
			int coordinateX = group[i][1];
			numOfMembers[0] = 0;
			Functions.wipe(boxSelectedB4);
			PeakAndTrough.countMembers(array, numOfMembers, coordinateY, coordinateX, array[coordinateY][coordinateX], boxSelectedB4);
			
			int [][] allCoordinatesInAGroup = new int [numOfMembers[0]][2];
			Functions.wipe(boxSelectedB4);
			int [] startingIndex = new int[1];
			startingIndex[0] = 0;
			PeakAndTrough.merge(array,allCoordinatesInAGroup,coordinateY,coordinateX,array[coordinateY][coordinateX], boxSelectedB4,startingIndex);
			
			if(PeakAndTrough.isItAPeak(array, allCoordinatesInAGroup, numOfMembers[0]) == true)
				numOfPeak++;
			else if(PeakAndTrough.isItATrough(array,allCoordinatesInAGroup, numOfMembers[0])== true)
				numOfTrough++;
		}
		totalNumOfPeakAndTrough[0] = numOfPeak;
		totalNumOfPeakAndTrough[1] = numOfTrough;
		return totalNumOfPeakAndTrough;
	}//end totalNumOfPeakAndTrough method
	
	/**coordinateOfPeakAndTrough method: <br>
	 * Procedural method that stores coordinates of peak and trough in their respective arrays (coordinateOfPeak and coordinateOfTrough)
	 * <p>
	 * List of Local Variables: <br>
	 * numOfMembers - This array stores the number of peaks or troughs <type int> <br>
	 * startingIndex - the number of which the counter starts with <type int> <br>
	 * peak - the counter used to store coordinates in the coordinateOfPeak array <type int> <br>
	 * trough - the counter used to store coordinates in the coordinateOfTrough array <type int> <br>
	 * coordinateY - y-coordinate <type int> <br>
	 * coordinateX - x-coordinate <type int> <br>
	 * allCoordinatesInAGroup - this array contains all coordinates of a particular group <type int> <br>
	 * @param array - array of the puzzle <type int>
	 * @param group - an array used to store one coordinate for each group, for all groups <type int> 
	 * @param boxSelectedB4 - To ensure that when the method calls itself recursively, it does not reuse a coordinate that has been called before. <type boolean>
	 * @param coordinateOfPeak - an array that stores all coordinates of peaks <type int>
	 * @param coordinateOfTrough - an array that stores all coordinates of troughs <type int>
	 * @return void
	 */
	public static void coordinateOfPeakAndTrough(int[][] array, int [][] group, boolean[][] boxSelectedB4, int[][] coordinateOfPeak, int [][] coordinateOfTrough){
		int [] numOfMembers = new int [1];
		int [] startingIndex = new int[1];
		int peak = 0;
		int trough = 0;
		for(int i=0; i<=group.length-1; i++){
			int coordinateY = group[i][0];
			int coordinateX = group[i][1];
			numOfMembers[0] = 0;
			startingIndex[0] = 0;
			Functions.wipe(boxSelectedB4);
			PeakAndTrough.countMembers(array, numOfMembers, coordinateY, coordinateX, array[coordinateY][coordinateX], boxSelectedB4);
			
			int [][] allCoordinatesInAGroup = new int [numOfMembers[0]][2];
			Functions.wipe(boxSelectedB4);
			PeakAndTrough.merge(array,allCoordinatesInAGroup,coordinateY,coordinateX,array[coordinateY][coordinateX], boxSelectedB4,startingIndex);
					
			if(PeakAndTrough.isItAPeak(array, allCoordinatesInAGroup, numOfMembers[0]) == true){
				coordinateOfPeak[peak][0] = coordinateY;
				coordinateOfPeak[peak][1] = coordinateX;
				peak++;
			}else if(PeakAndTrough.isItATrough(array,allCoordinatesInAGroup, numOfMembers[0])== true){
				coordinateOfTrough[trough][0] = coordinateY;
				coordinateOfTrough[trough][1] = coordinateX;
				trough++;	
			}
		}	
	}//end coordinateOfPeakAndTrough method
	
	/**decrement method:<br>
	 * This recursive functional method decrements selected coordinate until it is no longer a peak- returns the number of steps used (used for peaks)
	 * <p>
	 * List of Local Variables: <br>
	 * numOfMembers - This array stores the number of peaks or troughs <type int> <br>
	 * startingIndex - the number of which the counter starts with <type int> <br>
	 * allCoordinatesInAGroup - this array contains all coordinates of a particular group <type int> <br>
	 * @param array - array of the puzzle <type int> 
	 * @param y - y-coordinate <type int> 
	 * @param x - x-coordinate <type int> 
	 * @param boxSelectedB4 - To ensure that when the method calls itself recursively, it does not reuse a coordinate that has been called before. <type boolean> 
	 * @return number of steps used to decrement until it is no longer a peak <type int> 
	 */
	public static int decrement(int[][] array, int y, int x, boolean [][] boxSelectedB4){
		int [] numOfMembers = new int [1];
		int [] startingIndex = new int[1];
		startingIndex[0] = 0;
		numOfMembers[0] = 0; 
		Functions.wipe(boxSelectedB4);
		PeakAndTrough.countMembers(array, numOfMembers, y,x, array[y][x], boxSelectedB4);
	
		
		int [][] allCoordinatesInAGroup = new int [numOfMembers[0]][2];
		Functions.wipe(boxSelectedB4);
		PeakAndTrough.merge(array,allCoordinatesInAGroup,y,x,array[y][x], boxSelectedB4,startingIndex);
		Functions.wipe(boxSelectedB4);
		//System.out.print("The coordinates in this group are ");
		//System.out.println(Arrays.deepToString(allCoordinatesInAGroup));
		if(PeakAndTrough.isItAPeak(array, allCoordinatesInAGroup, numOfMembers[0])==false||numOfMembers[0]==Math.pow(array.length, 2))
			return 0;
		else{
			Functions.decrementBy1(array, y, x, array[y][x], boxSelectedB4);
			return 1+decrement(array,y,x,boxSelectedB4);
		}	
		
	}//end decrement method
	
	/**increment method:<br>
	 * This recursive functional method increments selected coordinate until it is no longer a trough- returns the number of steps used (used for troughs)
	 * <p>
	 * List of Local Variables: <br>
	 * numOfMembers - This array stores the number of peaks or troughs <type int> <br>
	 * startingIndex - the number of which the counter starts with <type int> <br>
	 * allCoordinatesInAGroup - this array contains all coordinates of a particular group <type int> <br>
	 * @param array - array of the puzzle <type int> 
	 * @param y - y-coordinate <type int> 
	 * @param x - x-coordinate <type int> 
	 * @param boxSelectedB4 - To ensure that when the method calls itself recursively, it does not reuse a coordinate that has been called before. <type boolean> 
	 * @return number of steps used to increment until it is no longer a trough <type int> 
	 */
	public static int increment(int[][] array, int y, int x, boolean [][] boxSelectedB4){
		int [] numOfMembers = new int [1];
		int [] startingIndex = new int[1];
		startingIndex[0] = 0;
		numOfMembers[0] = 0; 
		Functions.wipe(boxSelectedB4);
		PeakAndTrough.countMembers(array, numOfMembers, y,x, array[y][x], boxSelectedB4);
		
		
		int [][] allCoordinatesInAGroup = new int [numOfMembers[0]][2];
		Functions.wipe(boxSelectedB4);
		PeakAndTrough.merge(array,allCoordinatesInAGroup,y,x,array[y][x], boxSelectedB4,startingIndex);
		Functions.wipe(boxSelectedB4);
		
		if(PeakAndTrough.isItATrough(array, allCoordinatesInAGroup, numOfMembers[0])==false||numOfMembers[0]==Math.pow(array.length, 2))
			return 0;
		else
			Functions.incrementBy1(array, y, x, array[y][x], boxSelectedB4);
			return 1+increment(array,y,x,boxSelectedB4);
			
	}//end increment method
}
