package avengers;

/**
 * Given a starting event and an Adjacency Matrix representing a graph of all possible 
 * events once Thanos arrives on Titan, determine the total possible number of timelines 
 * that could occur AND the number of timelines with a total Expected Utility (EU) at 
 * least the threshold value.
 * 
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * UseTimeStoneInputFile name is passed through the command line as args[0]
 * Read from UseTimeStoneInputFile with the format:
 *    1. t (int): expected utility (EU) threshold
 *    2. v (int): number of events (vertices in the graph)
 *    3. v lines, each with 2 values: (int) event number and (int) EU value
 *    4. v lines, each with v (int) edges: 1 means there is a direct edge between two vertices, 0 no edge
 * 
 * Note 1: the last v lines of the UseTimeStoneInputFile is an ajacency matrix for a directed
 * graph. 
 * The rows represent the "from" vertex and the columns represent the "to" vertex.
 * 
 * The matrix below has only two edges: (1) from vertex 1 to vertex 3 and, (2) from vertex 2 to vertex 0
 * 0 0 0 0
 * 0 0 0 1
 * 1 0 0 0
 * 0 0 0 0
 * 
 * Step 2:
 * UseTimeStoneOutputFile name is passed through the command line as args[1]
 * Assume the starting event is vertex 0 (zero)
 * Compute all the possible timelines, output this number to the output file.
 * Compute all the posssible timelines with Expected Utility higher than the EU threshold,
 * output this number to the output file.
 * 
 * Note 2: output these number the in above order, one per line.
 * 
 * Note 3: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut:
 *     StdOut.setFile(outputfilename);
 *     //Call StdOut.print() for total number of timelines
 *     //Call StdOut.print() for number of timelines with EU >= threshold EU 
 * 
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/UseTimeStone usetimestone.in usetimestone.out
 * 
 * @author Yashas Ravi
 * 
 */

public class UseTimeStone {

    String[][] generatorMatrix;

    int[] euValues;

    int[][] euCostMatrix;

    boolean[] visited;

    int[][] convertedGeneratorMatrixToInt;

    //Constructor
    public UseTimeStone(){

    }

    public void setMatrix(String[][] matrix){
        generatorMatrix = matrix;

    }

    public void setConvertedMatrix(int[][] matrix){
        convertedGeneratorMatrixToInt = matrix;

    }

    public static void main (String [] args) {
    	
        if ( args.length < 2 ) {
            StdOut.println("Execute: java UseTimeStone <INput file> <OUTput file>");
            return;
        }

        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);

        int euThreshold = StdIn.readInt();

        int numOfNodes = StdIn.readInt();

        UseTimeStone saveTheDayDoctor = new UseTimeStone();


        //saveTheDayDoctor.generatorMatrix = new String[numOfNodes][numOfNodes];
        //saveTheDayDoctor.convertedGeneratorMatrixToInt = new int[numOfNodes][numOfNodes];

        /*int count = 0;

        for(int row = 0; row< numOfNodes; row++){
            for(int col = 0; col < numOfNodes; col++){

                while(StdIn.hasNextChar() && count < numOfNodes*numOfNodes){

                    String read = StdIn.readString();

                    saveTheDayDoctor.generatorMatrix[row][col] = read;

                    if(saveTheDayDoctor.generatorMatrix[row][col].equals("1")){
                        saveTheDayDoctor.convertedGeneratorMatrixToInt[row][col] = 1;
                    }else if(saveTheDayDoctor.generatorMatrix[row][col].equals("0")){
                        saveTheDayDoctor.convertedGeneratorMatrixToInt[row][col] = 0;
                    }

                    count++;

                    StdOut.println(saveTheDayDoctor.generatorMatrix[row][col]);

                }

            }
        }*/
        //saveTheDayDoctor.setMatrix(saveTheDayDoctor.generatorMatrix);
        //saveTheDayDoctor.setConvertedMatrix(saveTheDayDoctor.convertedGeneratorMatrixToInt);




        saveTheDayDoctor.storeEuValues(numOfNodes);

        saveTheDayDoctor.createMatrix(numOfNodes);

        saveTheDayDoctor.createVisitedArray(numOfNodes);

        saveTheDayDoctor.computeUtilityValueMatrix(numOfNodes);

        saveTheDayDoctor.countTotalPossibleTimelines();

        saveTheDayDoctor.numOfTimelineThatExcededEU(euThreshold);


    }




    public int[] storeEuValues(int numOfNodes){
        euValues = new int[numOfNodes];

        int count = 0;

        for(int i = 0; i < numOfNodes; i++){
            while(StdIn.hasNextChar() && count < numOfNodes){

                int generator = StdIn.readInt();

                int euValue = StdIn.readInt();

                euValues[generator] = euValue;

                count++;

            }
        }

        return euValues;
    }



    public String[][] createMatrix(int numOfNodes){

        generatorMatrix = new String[numOfNodes][numOfNodes];
        convertedGeneratorMatrixToInt = new int[numOfNodes][numOfNodes];

        int count = 0;

        for(int row = 0; row< numOfNodes; row++){
            for(int col = 0; col < numOfNodes; col++){

                while(StdIn.hasNextChar() && count < numOfNodes*numOfNodes){

                    String read = StdIn.readString();

                    generatorMatrix[row][col] = read;

                    if(generatorMatrix[row][col].equals("1")){
                        convertedGeneratorMatrixToInt[row][col] = 1;
                    }else if(generatorMatrix[row][col].equals("0")){
                        convertedGeneratorMatrixToInt[row][col] = 0;
                    }

                    count++;

                    StdOut.println(generatorMatrix[row][col]);

                }

            }
        }
        


        return generatorMatrix;

    }

    public int[][] computeUtilityValueMatrix(int numOfNodes){
        euCostMatrix = new int[numOfNodes][numOfNodes];

        for(int row = 0; row< numOfNodes; row++){
            for(int col = 0; col < numOfNodes; col++){

                if(generatorMatrix[row][col] == "1"){
                    euCostMatrix[row][col] = euValues[row] + euValues[col];
                }
                
            }
        }

        return euCostMatrix;
    }

    public void createVisitedArray(int numOfPeople){
        visited = new boolean[numOfPeople];
        for(int i = 0; i<visited.length; i++){
            visited[i] = false;
            //StdOut.println(visited[i] + " Initial call");
        }

    }


    public void dFSForTimeline(int vertex, String[][] generatorMatrix){
        int totalFutures = 0;

        //setMatrix(generatorMatrix);
        //createVisitedArray(generatorMatrix.length);
        visited[vertex] = true;
        for(int i = 0; i<generatorMatrix.length; i++){
            for(int j = 0; j< generatorMatrix[i].length; j++){
                StdOut.println(generatorMatrix[i][j] + "  Matrix when called in DFS");
            }
        }


        for(int i = 0; i<generatorMatrix[vertex].length; i++){

            //StdOut.println(visited[i] + " updated call");

                //If there's a connection between (edge) between the vertex and given i and it has not been visited,
                //call DFS again
                if(convertedGeneratorMatrixToInt[vertex][i] == 1 && visited[i] == false){
                    totalFutures++;

                    //if(euCostMatrix[vertex][i])
                    dFSForTimeline(i, generatorMatrix);
                }
        }


        StdOut.println(totalFutures);
        //return totalFutures;


    }
    public void countTotalPossibleTimelines(){

        dFSForTimeline(0, generatorMatrix);

    }

    //EU CALCULATIONS ---> NEEDS 2 int values!!!! one to count through each path and
    //ANother to sum up the paths .... I think.... might need 1 if I'm thinking about the logic correctly

    public void dFSForThreshold(int vertex, int euThreshold){

        visited[vertex] = true;

        //int totalFutures = 0;

        //createVisitedArray(generatorMatrix.length);

        int exceedEuTimelines = 0;



        for(int i = 0; i<visited.length; i++){

                if(convertedGeneratorMatrixToInt[vertex][i] == 1 && visited[i] == false){
                    //totalFutures++;


                    dFSForThreshold(i, euThreshold);
                }
        }
        /*if(euCostMatrix[vertex][i] >= euThreshold){
            exceedEuTimelines++;
        } */

        StdOut.println(exceedEuTimelines);

    }

    public void numOfTimelineThatExcededEU(int euThreshold){
        dFSForThreshold(0, euThreshold);
    }



}
