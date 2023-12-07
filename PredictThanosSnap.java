package avengers;

import java.util.ArrayList;

/**
 * Given an adjacency matrix, use a random() function to remove half of the nodes. 
 * Then, write into the output file a boolean (true or false) indicating if 
 * the graph is still connected.
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * PredictThanosSnapInputFile name is passed through the command line as args[0]
 * Read from PredictThanosSnapInputFile with the format:
 *    1. seed (long): a seed for the random number generator
 *    2. p (int): number of people (vertices in the graph)
 *    2. p lines, each with p edges: 1 means there is a direct edge between two vertices, 0 no edge
 * 
 * Note: the last p lines of the PredictThanosSnapInputFile is an ajacency matrix for
 * an undirected graph. 
 * 
 * The matrix below has two edges 0-1, 0-2 (each edge appear twice in the matrix, 0-1, 1-0, 0-2, 2-0).
 * 
 * 0 1 1 0
 * 1 0 0 0
 * 1 0 0 0
 * 0 0 0 0
 * 
 * Step 2:
 * Delete random vertices from the graph. You can use the following pseudocode.
 * StdRandom.setSeed(seed);
 * for (all vertices, go from vertex 0 to the final vertex) { 
 *     if (StdRandom.uniform() <= 0.5) { 
 *          delete vertex;
 *     }
 * }
 * Answer the following question: is the graph (after deleting random vertices) connected?
 * Output true (connected graph), false (unconnected graph) to the output file.
 * 
 * Note 1: a connected graph is a graph where there is a path between EVERY vertex on the graph.
 * 
 * Note 2: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut (here, isConnected is true if the graph is connected,
 *   false otherwise):
 *     StdOut.setFile(outputfilename);
 *     StdOut.print(isConnected);
 * 
 * @author Yashas Ravi
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/PredictThanosSnap predictthanossnap.in predictthanossnap.out
*/

public class PredictThanosSnap {

    public PredictThanosSnap(){

    }

    boolean[][] areDead;
    int[][] survivors;
    String[][] intToString;
    boolean[] isMarkedOrNot;
    String[] edgeToNeighbor;

    boolean[] isAlive;
    //ArrayList<int> deletedNodes = new ArrayList<int>();
    ArrayList<Integer> deletedNodes = new ArrayList<Integer>();
	 
    public static void main (String[] args) {
 
        if ( args.length < 2 ) {
            StdOut.println("Execute: java PredictThanosSnap <INput file> <OUTput file>");
            return;
        }
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);

        long seed = StdIn.readLong();
        StdRandom.setSeed(seed);
        //StdOut.println(seed);

        int numberOfNodes = StdIn.readInt();

        PredictThanosSnap iLoveU3000 = new PredictThanosSnap();

        iLoveU3000.createMatrices(numberOfNodes);
        iLoveU3000.snapThosePeepsAway(numberOfNodes);
        iLoveU3000.isConnected(numberOfNodes);
        
    	// WRITE YOUR CODE HERE

    }

    //Creates 2 2d Matrices that storers the int value and associated boolean values
    //1 == true;
    //0 == false;
    //Converts the string array from input file to string


    //Updates the 1d boolean matrix with all indices false 
    public void fillOutIsMarkedOrNot( boolean[] isMarkedOrNot){
        isMarkedOrNot = new boolean[areDead.length];
        for(int i = 0; i < isMarkedOrNot.length; i++){
            isMarkedOrNot[i] = false;
        }
    }     

    public void createMatrices(int numberOfNodes){
        areDead = new boolean[numberOfNodes][numberOfNodes];
        survivors = new int[numberOfNodes][numberOfNodes];
        intToString = new String[numberOfNodes][numberOfNodes];

        while(StdIn.hasNextChar()){

            for(int i = 0; i < numberOfNodes; i++){
                for(int j = 0; j < numberOfNodes; j++){

                    intToString[i][j] = StdIn.readString();
                    //StdOut.print("int to string array values  --> "+intToString[i][j]);
                    //StdOut.println();
                    if(intToString[i][j].equals("1")){
                        areDead[i][j] = true;
                    }else{
                        areDead[i][j] = false;
                    }
                    //StdOut.println();
                    //StdOut.print(areDead[i][j]);
                }
            }
        }

        //StdOut.println();

        for(int i = 0; i < numberOfNodes; i++){
            for(int j = 0; j < numberOfNodes; j++){
                if(intToString[i][j].equals("0")){
                    survivors[i][j] = 0;
                }else{
                    survivors[i][j] = 1;
                }
                //StdOut.print(survivors[i][j]);
            }
        }

        isMarkedOrNot = new boolean[numberOfNodes];
        fillOutIsMarkedOrNot(isMarkedOrNot);
        /*for(int i = 0; i <isMarkedOrNot.length; i++){
            //StdOut.print(isMarkedOrNot[i]);
        }*/

        //return areDead;
    }


    //use the random seed to dictate who gets snapped
    public void snapThosePeepsAway(int numberOfPeople){
        isAlive = new boolean[numberOfPeople];
        for(int i = 0; i<numberOfPeople; i++){
            isAlive[i] = true;

            if(StdRandom.uniform() <= 0.5){
                isAlive[i] = false;
            }

        }
        for(int i = 0; i<numberOfPeople; i++){
            if(isAlive[i] == false){
                for(int j = 0; j<numberOfPeople; j++){
                    survivors[i][j] = 0;
                    survivors[j][i] = 0;
                }
            }
        }

        /*for(int i = 0; i < numberOfPeople; i++){
            for(int j = 0; j <numberOfPeople; j++){

                if(StdRandom.uniform() <= 0.5){

                    //resets the edges of 
                    //areDead[i][j] = false;
                    //areDead[j][i] = false;

                    

                    //dead people are initiated to -1 instead of 0
                    survivors[i][j] = -1;
                    survivors[j][i] = -1;

                    //These indices have a deleted node
                    deletedNodes.add(i);


                }

                //Prints out the new status after snap
                //StdOut.println();
                //StdOut.println(areDead[i][j] + " New Matrix result");
            }
        }*/

    }

    //Finds if an entire row or column is false, if so, return false, if not return true
    //If this is true, then at least one node is unconnected, therefore, the output is true

    //SHOULD IMPLEMENT DFS depthfirstsearch which is recursive

    public void isConnected(int numberOfPeople){

        boolean connected = true;

        //int countNeighbors = 0;

        //Holds the index of a person who is alive
        int alivePerson = 0;


        //Checks each row, if there is an entire row of 0's, then one generator is not connected to 
        //any
        /*for(int i = 0; i < numberOfPeople; i++){

            if(connected == false){
                break;
            }
            for(int j = 0; j < numberOfPeople; j++){

                //If boolean at this edge is false increment count and if row has a -1 in it, skip the row/column
                if(/*areDead[i][j] == false && survivors[i][j] == 0){
                    countNeighbors++;
                    //deadPeeps++;
                }
                //If the loop has gone through an entire loop and gotten the entire to be false, return
                if(countNeighbors == numberOfPeople && j <= numberOfPeople){
                    connected = false;
                    break;
                }    
            }

        }*/

        //Find a node thats not dead and DFS that node --> iterate through a for loop

        //DFS

        //Initializes visted boolean array to false for every index
        for(int i = 0; i <numberOfPeople; i++){
            //Look for an person that is alive so i can only  DFS only once!!!
            if(isAlive[i] == true){
                alivePerson = i;
                break;
            }
        }

        depthFirstSearch(isMarkedOrNot, survivors, alivePerson);
        //depthFirstSearch(isMarkedOrNot, survivors, numberOfPeople-1);
        

        //If there's at least one node that is false after DFS, then the graph is unconnected, return false, else return true
        for(int i = 0; i < numberOfPeople; i++){
            
            if(isMarkedOrNot[i] == false && isAlive[i] == true){
                connected = false;
                break;
            }

            
        }

        //Find out if there are some nodes that are connected but disconnected from others

        /*for(int j = 0; j<isMarkedOrNot.length; j++){
            //StdOut.println(isMarkedOrNot[j]);
        }*/
        

        StdOut.println(connected);
        //return connected;
    }



    public void depthFirstSearch(boolean[] isMarkedOrNot, int[][] survivors, int vertex){

        isMarkedOrNot[vertex] = true; 

        for(int i = 0; i <survivors[vertex].length; i++){
            //StdOut.println (i);
            if(isMarkedOrNot[i] == false && survivors[vertex][i] == 1){

                depthFirstSearch(isMarkedOrNot, survivors, i);

            }
        }


    }
}
