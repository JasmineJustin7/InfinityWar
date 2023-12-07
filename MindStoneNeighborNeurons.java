package avengers;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;

/**
 * Given a Set of Edges representing Vision's Neural Network, identify all of the 
 * vertices that connect to the Mind Stone. 
 * List the names of these neurons in the output file.
 * 
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * MindStoneNeighborNeuronsInputFile name is passed through the command line as args[0]
 * Read from the MindStoneNeighborNeuronsInputFile with the format:
 *    1. v (int): number of neurons (vertices in the graph)
 *    2. v lines, each with a String referring to a neuron's name (vertex name)
 *    3. e (int): number of synapses (edges in the graph)
 *    4. e lines, each line refers to an edge, each line has 2 (two) Strings: from to
 * 
 * Step 2:
 * MindStoneNeighborNeuronsOutputFile name is passed through the command line as args[1]
 * Identify the vertices that connect to the Mind Stone vertex. 
 * Output these vertices, one per line, to the output file.
 * 
 * Note 1: The Mind Stone vertex has out degree 0 (zero), meaning that there are 
 * no edges leaving the vertex.
 * 
 * Note 2: If a vertex v connects to the Mind Stone vertex m then the graph has
 * an edge v -> m
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
 *     //Call StdOut.print() for EVERY neuron (vertex) neighboring the Mind Stone neuron (vertex)
 *  
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/MindStoneNeighborNeurons mindstoneneighborneurons.in mindstoneneighborneurons.out
 *
 * @author Yashas Ravi
 * 
 */


public class MindStoneNeighborNeurons {

    String mindStone = "";
    String[] vertices;
    String[] edges;
    boolean[][] isNeighbor;
    String[] vertexArray;
    String[] edgeArray;
    int numOfEdges;
    int numOfVertices;
    String[] neighbors;
    String[] copyOfVertexArray;

    //Contains all of the Nodes
    HashSet<String> keys = new HashSet<String>();

    //Contains all of the nodes and their neighbors
    HashMap<String, ArrayList<String>> mindStoneNeurons = new HashMap<String, ArrayList<String>>();

    //List that contains all of one node's neighbors
    //Will only be used for the mindStone which has an outdegree of 0
    ArrayList<String> totalNeighbors = new ArrayList<String>();

    ArrayList<String> mindStoneNeighbors = new ArrayList<String>();



    //Constructor
    public MindStoneNeighborNeurons(){

    }
    
    public static void main (String [] args) {
        
    	if ( args.length < 2 ) {
            StdOut.println("Execute: java MindStoneNeighborNeurons <INput file> <OUTput file>");
            return;
        }

        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);

        MindStoneNeighborNeurons ripVision = new MindStoneNeighborNeurons();

        ripVision.numOfVertices = StdIn.readInt();

        ripVision.createHashSet(ripVision.numOfVertices);

        ripVision.numOfEdges = StdIn.readInt();

        ripVision.createHashSetForKeys(ripVision.numOfEdges);

        ripVision.mindStoneSearch();

        ripVision.findMindStoneNeighbors();
        //ripVision.insertIntoArrays(ripVision.numOfEdges);

        //ripVision.createHashMap(ripVision.numOfVertices);
        

        //StdOut.println(ripVision.createHashMap(numOfVertices));

    	
    	// WRITE YOUR CODE HERE
        
    }


    //Stores the vertices as keys and puts them into a HashSet
    public void createHashSet(int numOfVertices){

        //Array that contains all of the vertices from the input file
        vertices = new String[numOfVertices];
        int totalVerticesSoFar = 0;

        //Inputs each string in the input into an array
        //adds each key to the hashset
        while(totalVerticesSoFar < numOfVertices && StdIn.hasNextChar()){
            for(int i = 0 ; i < vertices.length; i++){

                vertices[i] = StdIn.readString();

                keys.add(vertices[i]);
                totalVerticesSoFar++;
                
            }   
        }

        //USED TO TEST
        /*for(int j = 0; j <totalVerticesSoFar; j++){
            StdOut.println(vertices[j] + " Is a vertex");
            } */

    }

    //Reads the first letter in the synapse and puts them into a String array
    public void createHashSetForKeys(int numOfEdges){

        edges = new String[numOfEdges];

        neighbors = new String[numOfEdges];

        int totalEdgesSoFar = 0;

        //Inputs each string in the input into an array
        //adds each key to the hashset
        while(totalEdgesSoFar < numOfVertices && StdIn.hasNextChar()){
            for(int i = 0 ; i < edges.length; i++){

                edges[i] = StdIn.readString();

                //Input the second part of neuron into neighbors array
                neighbors[i] = StdIn.readString();

                keys.add(edges[i]);
                totalEdgesSoFar++;
                
            }   
        }

        //input all neighbors in the 1d array into an arrayList
        for(int row = 0; row < neighbors.length; row++){
            totalNeighbors.add(neighbors[row]);
        }

        //USED TO TEST
        /*for(int j = 0; j <totalEdgesSoFar; j++){
            StdOut.println(edges[j] + "  <-- Edges");
            StdOut.println(totalNeighbors.get(j) + " a neighbor");
            } */


    }

    //Searches for mind Stone by finding which vertex is not in the edges array
    public void mindStoneSearch(){

        //Instead of looking at neighbors, just make a copy of the vertex array and then iterate through edges to see
        //if the vertex is in the edges array. If it is, put a "0" 
        //After that, iterate through the copied array to see if there are any strings left in the array
        //If there is a string, that is the mindstone

        String[] copyOfVertexArray = new String[vertices.length];

        for(int i = 0; i < copyOfVertexArray.length; i++){
            copyOfVertexArray[i] = vertices[i];
            //StdOut.print(copyOfVertexArray[i]);
        }
        //StdOut.println();

        for(int row = 0; row < edges.length; row++){
            for(int rowVertex = 0; rowVertex < vertices.length; rowVertex++){
                if(edges[row].equals(copyOfVertexArray[rowVertex])){
                    copyOfVertexArray[rowVertex] = "0";
                    //StdOut.print(copyOfVertexArray[rowVertex]);
                }
            }
        }

        for(int j = 0; j < copyOfVertexArray.length; j++){
            if(!copyOfVertexArray[j].equals("0")){
                mindStone = mindStone + copyOfVertexArray[j];
            }
        }




        //String mindStone = "";

        /*int instances = 0;

        //int indexOfMindStone = 0;

        //boolean isMindStone = false;

        for(int i = 0; i < vertices.length; i++){
            StdOut.print(vertices[i]);
        }
        StdOut.println();
        for(int i = 0; i < edges.length; i++){
            StdOut.print(edges[i]);
        }
        
        StdOut.println();
        StdOut.println(edges.length);
               

        //Finds the one vertex that doesn't appear as an edge
        for(int i = 0; i < vertices.length; i++){
            
            instances = 0;

            for(int j = 0; j < edges.length; j++){


                if(vertices[i] != edges[j]){
                    instances++;
                    StdOut.println (instances + " # times vertex " + vertices[i] + " doesn't appear in edge array");
                }
                //StdOut.println (vertices[i] + "  is the vertex being checked with edge  " + edges[j]);

                
            }
            //If the given vertex is not counted at all, then it's the mind stone
            if(instances == edges.length){
                //StdOut.println(indexOfMindStone);
                mindStone = mindStone + vertices[i];
                break;
                }            

        }*/

        //StdOut.println(); 
        //StdOut.println(mindStone);

        //return mindStone + " <== This is the mind stone";
    }

    //Looks for all neighbors of the mindstone and prints them into the output 
    public void findMindStoneNeighbors(){

        for(int i = 0; i < neighbors.length; i++){
            //StdOut.println(mindStone);
            if(neighbors[i].equals(mindStone)){

                mindStoneNeighbors.add(edges[i]);
                StdOut.println(edges[i] /*+ " is a neighbor of the mind stone"*/);
            }
            

        }

        
        
    }

}
    //Given a list of vertices, create an ArrayList for a vertex to store their neighbors
    /*public ArrayList<String> listOfKeys(String key){

        ArrayList<String> keyList = new ArrayList<String>();

        return keyList;

    }

    public ArrayList<String> getListOfKeys(String givenVertex){
        return listOfKeys(givenVertex);
    } */

    //Inputs the second half of the input file into two arrays depending on placement in edge

    //CHANGING THE ALGORITHM OF THIS METHOD
    //GONNA BE USED TO INSERT SECOND INPUT VALUE INTO
    /*public void insertIntoArrays(int totalAmountOfEdges){

        for(int i = 0; i < totalAmountOfEdges; i++){
            String matchingValue = StdIn.readString();

            if(matchingValue.equals(vertices[i])){
                String neighbor = StdIn.readString();

            }


        } */

       
       
       
       
       
        //int totalAmountOfEdges = StdIn.readInt();

        /*int count = 0;

        vertexArray = new String[totalAmountOfEdges];

        edgeArray = new String[totalAmountOfEdges];

        while(StdIn.hasNextChar() && count < totalAmountOfEdges){

            for(int i = 0; i < vertexArray.length; i++){
                vertexArray[i] = StdIn.readString();
                edgeArray[i] = StdIn.readString();
            }
            
        } */

    






    //Iterate through the arraylist of a given vertex to see if the value is in the arrayList of the given vertex
    /*public String findValue(String key, String value){
        String matchingValue = "";

        getListOfKeys(key);

        for(int i = 0; i < getListOfKeys(key).size(); i ++){
            if(getListOfKeys(key).get(i) == value){
                matchingValue = value;
            }
        }
        //If after for loop initiates, nothing happens then the value was not a neighbor
        if(matchingValue == ""){
            matchingValue = "Value is not Vertex's neighbor";
        }



        return matchingValue;
    }*/



    //Uses the HashSet and reads the second part of the input value to store the
    //neighbors
    //Have to create an array list for all vertices
    /*public void createHashMap(int numOfVertices){

        int count = 0;

        int totalAmountNeighbors = StdIn.readInt();

        while(count < totalAmountNeighbors && StdIn.hasNextChar()){


            //Iterates through the array of vertices
            for(int i = 0; i < numOfVertices; i++){

                //Reads second part of the input file that contains all edges

                //Reads the first string
                String vertex = StdIn.readString();
                //StdOut.println(vertex );


                //USED TO TEST
                //StdOut.println(vertex);

                //Reads the second string as the neighbor
                String neighbor = StdIn.readString();

                StdOut.println(vertex + "  has edge with " + neighbor);



                //If the left most String matches an index in the array of vertices or hashset
                //Add the neighor which is the right-most String into the arrayList neighbors
                if(vertices[i] == vertex){

                    //Sets the second string to a string neighbor
                    //String neighbor = StdIn.readString();

                    //Adds the neighbor into an ArrayList
                    totalNeighbors.add(neighbor);

                    //Inputs the arrayList into respective key which matches the vertex
                    mindStoneNeurons.put(vertex, totalNeighbors);
                    
                }
                count++;
            }

        }
        for(int i = 0; i < totalNeighbors.size(); i++){
            //StdOut.println(totalNeighbors.get(i));
        }

    }*/


    //Given a HashSet, find the keys whose value is the mindStone
    //The mindstone is the key with no values associated with it; 0 degrees
    /*public String findMindStone(){

        //boolean isMindStone = false;

        String mindStone = "";

        //Go through the vertices array and see if they all have values in their arrayLists
        for(int i = 0; i < vertices.length; i++){

            //If arrayList is empty, then index of hashset has an outdegree of zero and is therefore the mindstone
            if(mindStoneNeurons.isEmpty()){
                
                mindStone = vertices[i];      

                }
    
            }
            return mindStone;

        } */


//} 

