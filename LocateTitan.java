package avengers;
//import java.util.Arrays;
/**
 * 
 * Using the Adjacency Matrix of n vertices and starting from Earth (vertex 0), 
 * modify the edge weights using the functionality values of the vertices that each edge 
 * connects, and then determine the minimum cost to reach Titan (vertex n-1) from Earth (vertex 0).
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * LocateTitanInputFile name is passed through the command line as args[0]
 * Read from LocateTitanInputFile with the format:
 *    1. g (int): number of generators (vertices in the graph)
 *    2. g lines, each with 2 values, (int) generator number, (double) funcionality value
 *    3. g lines, each with g (int) edge values, referring to the energy cost to travel from 
 *       one generator to another 
 * Create an adjacency matrix for g generators.
 * 
 * Populate the adjacency matrix with edge values (the energy cost to travel from one 
 * generator to another).
 * 
 * Step 2:
 * Update the adjacency matrix to change EVERY edge weight (energy cost) by DIVIDING it 
 * by the functionality of BOTH vertices (generators) that the edge points to. Then, 
 * typecast this number to an integer (this is done to avoid precision errors). The result 
 * is an adjacency matrix representing the TOTAL COSTS to travel from one generator to another.
 * 
 * Step 3:
 * LocateTitanOutputFile name is passed through the command line as args[1]
 * Use Dijkstra’s Algorithm to find the path of minimum cost between Earth and Titan. 
 * Output this number into your output file!
 * 
 * Note: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut (here, minCost represents the minimum cost to 
 *   travel from Earth to Titan):
 *     StdOut.setFile(outputfilename);
 *     StdOut.print(minCost);
 *  
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/LocateTitan locatetitan.in locatetitan.out
 * 
 * @author Yashas Ravi
 * 
 */

public class LocateTitan {

    double[] functionalityOfEachGenerator;

    int[][] energyCostMatrix;

    int[][] adjacencyMatrix;


    public LocateTitan(){

        //***Don't think I need to call this */
        //LocateTitan sample = new LocateTitan(); 
    }


    public static void main (String [] args) {
    	
        if ( args.length < 2 ) {
            StdOut.println("Execute: java LocateTitan <INput file> <OUTput file>");
            return;
        }

        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);


        //Reads number of nodes that willl determine dimension of matrix***
        int num = StdIn.readInt();

        LocateTitan whereItAtDoe = new LocateTitan();

        //StdOut.print(num);

        whereItAtDoe.makeListOfFunctionalities(num);

        /*for(int i = 0; i <num; i ++){
            StdOut.println(sample.getFunctionality(i) + "  Functionality");
        }*/
        
        whereItAtDoe.createMatrix(num);

        //Prints out generator matrix; should be the same exact one from standard input

        /*for(int i = 0; i < num; i++){
            for(int j = 0; j < num; j++){
                StdOut.println(sample.getGenerator(i, j) + "  Matrix numbers");
            }
        }*/

        whereItAtDoe.calculateEnergyCost(num);

        //Prints out energy cost matrix; should be the same exact one from standard input

        /*for(int i = 0; i < num; i++){
            for(int j = 0; j < num; j++){
                StdOut.println(sample.getEnergyCost(i, j) + "   Energy Cost");

            }
            
        } */




        //This is the only result that needs to be printed into file for grade
        StdOut.println(whereItAtDoe.findMinimumCost(num));



    	// WRITE YOUR CODE HERE

    }

    //Inputs all double values into an array according to their generator
    public double[] makeListOfFunctionalities(int numOfGenerators){

        int count =  0;

        //int num = StdIn.readInt();

        //StdOut.println(numOfGenerators);

        //Sets array that holds each generator's functionality
        functionalityOfEachGenerator = new double[numOfGenerators];


        //Reads all functionality numbers and puts them into array according to what generator they're associated with
        while(StdIn.hasNextChar() && count < numOfGenerators){

            for(int i = 0; i < numOfGenerators; i++){
                int x = StdIn.readInt(); //StdOut.println(x + "  This is x");
                double y = StdIn.readDouble(); //StdOut.println(y + "   This is y");
                if(i == x){
                    functionalityOfEachGenerator[i] = y;
                }
                count++;
            }

            
            //StdOut.println(count + " is less than 6?");

        }

        return functionalityOfEachGenerator;

    }


    //Gets the functionality of a generator
    public double getFunctionality(int index){
        return functionalityOfEachGenerator[index];
    }

    //Gets edge cost of a given generator (does this need 2 generators? If so, needs 2 extra parameters)
    public int getEnergyCost(int row, int col){

        return energyCostMatrix[row][col];
    }

    //Gets generator using adjacency matrix
    public int getGenerator(int row, int col){

        return adjacencyMatrix[row][col];
    }


    //Creates an adjacency matrix based on Standard input
    public int[][] createMatrix(int numOfGenerators){

        //numOfGenerators is taken from main method which reads from standard input

        int matrixIndicesCount = 0;

        adjacencyMatrix = new int[numOfGenerators][numOfGenerators];
        

        while(StdIn.hasNextChar() && matrixIndicesCount<numOfGenerators){

            for (int i = 0; i < numOfGenerators; i++){
                for (int j = 0; j < numOfGenerators; j++){

                    int x = StdIn.readInt();
                    adjacencyMatrix[i][j] = x;
                    matrixIndicesCount++;

                }
            }
        }

        return adjacencyMatrix;
        
    }

    public int[][] calculateEnergyCost(int numOfGenerators){

        energyCostMatrix = new int[numOfGenerators][numOfGenerators];

        //For each generator, calculate energy cost using formula on website
        for(int row = 0; row<numOfGenerators; row++){
            for(int col = 0; col <numOfGenerators; col++){

                int originalGenerator = adjacencyMatrix[row][col];

                energyCostMatrix[row][col] = (int)(originalGenerator/(getFunctionality(row)*getFunctionality(col)));

            }
            
        }

        return energyCostMatrix;

    }

    //Find total cost of energy from 0 to neighbor given a vertex
    public int totalPathCost(int row, int col){
        int totalCostInEnergy = 0;

        //Interates until it reaches the given vertex's neighbor
        for(int i = 0; i == row-1; i++){
            for(int j = 0; j == col-1; j++){
                totalCostInEnergy = totalCostInEnergy + energyCostMatrix[i][j];
            }
        }
        return totalCostInEnergy;

    }


    //tracks the minimum cost given an array of ints
    public int trackMinimumCost(int[] trackMinimumCost, boolean[] alreadyInPath){

        int minCost = Integer.MAX_VALUE;
        //int minCostIndex = -1;

        //int minCost = trackMinimumCost[0];
        int minCostIndex = 0;

        //Finds minimum cost given an array
        for(int i = 0; i < trackMinimumCost.length; i++){
            //If the neighbor hasn't been visited yet, look for the minimum cost index
            //StdOut.println(trackMinimumCost[i] + "  < -- Cost");
            if(alreadyInPath[i] == false && trackMinimumCost[i] < minCost){
        
                minCost = trackMinimumCost[i];
                minCostIndex = i;
                //StdOut.println(minCostIndex);
                }
            
        }


        //StdOut.println(minCostIndex);
        return minCostIndex;
    }


    //Calculates the minimum cost of energy that it'll take to travel from Earth to Titan
    // Uses Dijkstra's Algorithm
    //Result must be written into the output file!!
    public int findMinimumCost(int num){
        //int[] minCost = new int[num];

        int totalCost = 0;

        //Tracks minimum cost
        int[] listOfCosts = new int[num];

        boolean[] alreadyInPath = new boolean[num];

        //Sets all booleans to false initially
        for(int row = 0; row< alreadyInPath.length; row++){
            alreadyInPath[row] = false;
        }

        //Initializes all minimum Cost values to infinity initially except 0 because we start at 0
        for(int i = 0; i < listOfCosts.length; i++){
            if(i == 0){
                listOfCosts[0] = 0;
                //StdOut.println(listOfCosts[0]);
            }else{
                listOfCosts[i] = Integer.MAX_VALUE;
                //StdOut.println(listOfCosts[i] + "   is the MAX_VALUE");
            }
            
        }
        //Initializes minimum Cost to first index
        int currentSource = listOfCosts[0];

        //Iterates through listOfCosts Array
        for(int j = 0; j < num; j++){

            currentSource = trackMinimumCost(listOfCosts, alreadyInPath);

            alreadyInPath[currentSource] = true;
            
            //StdOut.println("Node " + currentSource + " is now the new source");

            //Iterate through energyCostMatrix row to find neighbors
            //Neighbors are each index where there is a number other than zero
            for(int row = 0; row < num; row++){
                
                //While neighbor doesn't equal source 
                //Runs if the neighbor in question has not been visited yet, the source's weight is not infinity
                //AND the associated cost of the neighbor is greater than the cost from source to itself
                if(energyCostMatrix[currentSource][row] > 0){ 
                    if(alreadyInPath[row] == false && listOfCosts[currentSource] != Integer.MAX_VALUE 
                    && (listOfCosts[currentSource] + energyCostMatrix[currentSource][row] < listOfCosts[row])){
                    //check if total cost from 0 to neighbor is SMALLER than its current cost*/

                        listOfCosts[row] = listOfCosts[currentSource] + energyCostMatrix[currentSource][row];

                            //StdOut.println(currentSource +  "  <== currentSource ");
                            //StdOut.println(energyCostMatrix[currentSource][row] + "  Displays edge cost between neighbor");
                            //StdOut.println(listOfCosts[row] + " New cost of the edge that replaced infinity  -->" + row);

                        totalCost = totalCost + listOfCosts[row];

                            //StdOut.println(totalCost + "  <-- total Cost ");
                            
            }
        }
            
            
        }
    }

        //Should return the titan's (which is last node) minimum energy cost from 0 to titan        
        return listOfCosts[num-1];
        //return totalCost;

    }
}
