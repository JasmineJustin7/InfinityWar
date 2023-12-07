package avengers;
/**
 * 
 * Given the 2D array where the values are the flux intensity data of the 
 * Neutron Star, calculate the total flux that Thor has to endure. 
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * ForgeStormbreakerInputFile name is passed through the command line as args[0]
 * Read from the ForgeStormbreakerInputFile with the format:
 *    1. r (int): number of rows
 *    2. c (int): number of columns
 *    3. r lines, each with c values
 *  Create and populate a 2D array with r rows and c columns with ForgeStormbreakerInputFile
 *  data.
 * 
 * Step 2:
 * ForgeStormbreakerOutputFile name is passed through the command line as args[1]
 * Given the 2D array where the values are the flux intensity data of the 
 * Neutron Star, calculate the total flux that Thor has to endure by summing
 * all values of the 2D array. 
 * Output this number into the output file. 
 * 
 * Note 1: the contents of the 2D array are guaranteed to be integers!
 * Note 2: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 * 
 *   To write to a file use StdOut (in the directions, the total flux is 45):
 *     StdOut.setFile(outputfilename);
 *     StdOut.print(total flux);
 * 
 * 
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/ForgeStormBreaker forgestormbreaker.in forgestormbreaker.out
 *
 * @author Yashas Ravi
 * 
 */

public class ForgeStormBreaker {

    int row;
    int col;
    int[][] influxGenerator;
    
    //Constructor
    public ForgeStormBreaker(){
        
    }

    public int calculateInFlux(String filename) {

        //Reads first two int values

        row = StdIn.readInt();
        col  = StdIn.readInt();
        int sum = 0;

        
        int[][] influxGenerator = new int[row][col];

        //Inputs integers into 2d array in order
        //Also sums all indices
        for(int i = 0; i<row; i++){
            for(int j = 0; j<col; j++){

                while(StdIn.hasNextChar()){

                influxGenerator[i][j] = StdIn.readInt();
                //StdOut.println(influxGenerator[i][j]);
                sum += influxGenerator[i][j];

                }
            }
        } 

        StdIn.setFile(filename);

        

        /*while(StdIn.hasNextChar()){
            sum +=StdIn.readInt();  
            StdOut.println(sum); 
        } */
        

         return sum;
         
         
         

         

    }
	public static void main (String [] args) {
        
        if ( args.length < 2 ) {
            StdOut.println("Execute: java ForgeStormBreaker <INput file> <OUTput file>");
            return;
        }

        // read file names from command line
        String forgeStormbreakerInputFile = args[0];
        String forgeStormbreakerOutputFile = args[1];

	    // Set the input file.
        StdIn.setFile(forgeStormbreakerInputFile);
        // WRITE YOUR CODE HERE TO INPUT FROM THE INPUT FILE

        // Calculate the flux
        // WRITE YOUR CODE HERE TO CALCULATE THE FLUX USING THE INPUT FILE

        //int row = StdIn.readInt();
        //int col = StdIn.readInt();

        ForgeStormBreaker canOfWhoopAss = new ForgeStormBreaker();
        
        // Set the output file.
        StdOut.setFile(forgeStormbreakerOutputFile);
        // WRITE YOUR CODE HERE TO OUTPUT TO THE OUTPUT FILE

       StdOut.println(canOfWhoopAss.calculateInFlux(forgeStormbreakerInputFile));

    }
}
