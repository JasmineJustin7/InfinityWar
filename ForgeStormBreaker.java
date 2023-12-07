package avengers;

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
