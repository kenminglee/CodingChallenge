//****************************************************************************************************************************************************
//functions class
//Ken Ming Lee
//Date: Monday March 27, 2017
//Programming Language: Java
//IDE: Eclipse Java EE 
//Version Number: 4.6.2
//****************************************************************************************************************************************************
//<Class>
//This class contains all common methods that are used throughout various classes.
//<List Of Identifiers>
//Listed in each method
//****************************************************************************************************************************************************
package lvlrPuzzle;
public class Functions {
	
	/**wipe method:<br>
	 * This procedural method wipes the 2-dimensional boolean array that is entered as an argument (changes all elements to false)<br>
	 * <p>
	 * List of Local Variables<br>
	 * none<br>
	 * <p>
	 * @param arrayToBeWiped - the array where all elements have to be changed to false
	 * @return void
	 */
	public static void wipe(boolean [][] arrayToBeWiped){
		for(int i =0; i<=arrayToBeWiped.length-1; i++){		
			for(int c=0; c<=arrayToBeWiped.length-1;c++){
				arrayToBeWiped[i][c] = false;
			}
		}
	}//end wipe method	
	
	/**group method: <br>
	 * This functional method  counts the number of groups in the array and ensures that all groups have one of their coordinates stored in the group array.
	 * <p>
	 * List of Local Variables: <br>
	 * boxSelectedB4 - an array used to see whether a coordinate have been incremented before <type boolean> <br>
	 * boxIsAlreadyPartOfGroup - an array used to see whether a coordinate have already been grouped <type boolean> <br> 
	 * y - y-coordinate <type int> <br>
	 * x - x-coordinate <type int> <br>
	 * groupArrayLength - counter used to count the number of groups in the array <type int> <br>
	 * group - an array used to store one coordinate for each group, for all groups <type int> <br>  
	 * @param array - the array of the puzzle <type int> <br>
	 * @return group - an array containing a coordinate each, for all groups <type int> <br>
	 */
	public static int[][] group(int [][] array){

		boolean [][] boxSelectedB4 = new boolean[array.length][array.length];
		boolean [][] boxIsAlreadyPartOfGroup = new boolean[array.length][array.length];
		
		int y=0, x=0, groupArrayLength = 0;
		for(int i = 0; i<=array.length-1; i++){
			for(int c = 0; c<=array.length-1; c++){
				if(boxIsAlreadyPartOfGroup[i][c]==false){
					Functions.sort(array, boxIsAlreadyPartOfGroup, i, c, array[i][c], boxSelectedB4);
					groupArrayLength+=1;
				}
			}
		}
		Functions.wipe(boxIsAlreadyPartOfGroup);
		Functions.wipe(boxSelectedB4);
		int [][] group = new int [groupArrayLength][2];
		for(int i = 0; i<=array.length-1; i++){
			for(int c = 0; c<=array.length-1; c++){
				if(boxIsAlreadyPartOfGroup[i][c]==false){
					Functions.sort(array, boxIsAlreadyPartOfGroup, i, c, array[i][c], boxSelectedB4);
					group[y][x] = i;
					group[y][x+1] = c;
					x=0;
					y+=1;
				}
			}
		}
		return group;
	}//end group method	
	
	/**sort method: <br>
	 * This procedural method ensures that all coordinates in the array has been sorted out appropriately and placed in a group by calling itself recursively
	 * <p>
	 * List of Local Variables<br>
	 * none
	 * <p>
	 * @param array - the puzzle <type int>
	 * @param boxIsAlreadyPartOfGroup - the boolean array that is used to determine whether a particular coordinate is already part of a group <type boolean>
	 * @param y - y-coordinate <type int>
	 * @param x - x-coordinate <type int>
	 * @param valueOfBoxSelected - the value of that particular coordinate <type int>
	 * @param boxSelectedB4 - To ensure that when the method calls itself recursively, it does not reuse a coordinate that has been called before. <type boolean> 
	 * @return void
	 */
	public static void sort(int[][] array, boolean[][]boxIsAlreadyPartOfGroup, int y, int x, int valueOfBoxSelected, boolean [][] boxSelectedB4){
		try{
			if(array[y][x]!=valueOfBoxSelected||boxSelectedB4[y][x]){ 
				return;
			}else{
				boxIsAlreadyPartOfGroup[y][x] = true;
				boxSelectedB4[y][x]=true;
				sort(array,boxIsAlreadyPartOfGroup,y,x+1,valueOfBoxSelected,boxSelectedB4);
				sort(array,boxIsAlreadyPartOfGroup,y,x-1,valueOfBoxSelected,boxSelectedB4);
				sort(array,boxIsAlreadyPartOfGroup,y+1,x,valueOfBoxSelected,boxSelectedB4);
				sort(array,boxIsAlreadyPartOfGroup,y-1,x,valueOfBoxSelected,boxSelectedB4);
			}
		}catch(ArrayIndexOutOfBoundsException e){
			return;	
		}
		
		
	}//end sort method
	
	/**incrementBy1 method: <br>
	 * This procedural method uses recursion to increment the entire group that is associated with the coordinate given by 1
	 * <p>
	 * List Of Local Variables: <br>
	 * none
	 * <p>
	 * @param array - array of the puzzle <type int>
	 * @param y - y-coordinate <type int>
	 * @param x - x-coordinate <type int>
	 * @param valueOfBoxSelected - value of the particular coordinate <type int>
	 * @param boxSelectedB4 - To ensure that when the method calls itself recursively, it does not reuse a coordinate that has been called before. <type boolean>
	 * @return void
	 */
	public static void incrementBy1(int[][] array, int y, int x, int valueOfBoxSelected, boolean [][] boxSelectedB4/*must equals false initially*/){
		try{
			if(array[y][x]!=valueOfBoxSelected||boxSelectedB4[y][x]){ 
				return;
			}else{
				array[y][x]+=1;
				boxSelectedB4[y][x]=true;
				incrementBy1(array,y,x+1,valueOfBoxSelected,boxSelectedB4);
				incrementBy1(array,y,x-1,valueOfBoxSelected,boxSelectedB4);
				incrementBy1(array,y+1,x,valueOfBoxSelected,boxSelectedB4);
				incrementBy1(array,y-1,x,valueOfBoxSelected,boxSelectedB4);
			}
		}catch(ArrayIndexOutOfBoundsException e){
			return;	
		}
	}//end incrementBy1 method
	
	/**decrementBy1 method: <br>
	 * This procedural method uses recursion to decrement the entire group that is associated with the coordinate given by 1
	 * <p>
	 * List Of Local Variables: <br>
	 * none
	 * <p>
	 * @param array - array of the puzzle <type int>
	 * @param y - y-coordinate <type int>
	 * @param x - x-coordinate <type int>
	 * @param valueOfBoxSelected - value of the particular coordinate <type int>
	 * @param boxSelectedB4 - To ensure that when the method calls itself recursively, it does not reuse a coordinate that has been called before. <type boolean>
	 * @return void
	 */
	public static void decrementBy1(int[][] array, int y, int x, int valueOfBoxSelected, boolean [][] boxSelectedB4){
		try{
			if(array[y][x]!=valueOfBoxSelected||boxSelectedB4[y][x]){ 
				return;
			}else{
				array[y][x]-=1;
				boxSelectedB4[y][x]=true;
				decrementBy1(array,y,x+1,valueOfBoxSelected,boxSelectedB4);
				decrementBy1(array,y,x-1,valueOfBoxSelected,boxSelectedB4);
				decrementBy1(array,y+1,x,valueOfBoxSelected,boxSelectedB4);
				decrementBy1(array,y-1,x,valueOfBoxSelected,boxSelectedB4);
			}
		}catch(ArrayIndexOutOfBoundsException e){
			return;	
		}	
	}//end decrementBy1 method
}
