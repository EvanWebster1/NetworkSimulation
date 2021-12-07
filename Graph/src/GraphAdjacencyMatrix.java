public class GraphAdjacencyMatrix {

    int Vertex;
    int Matrix[][];

    public int[][] getMatrix(){
        return Matrix;
    }

    /**
     * Copy contructor for setting of the size of the matrix
     * @param vertex int number of nodes in the graph
     */
    public GraphAdjacencyMatrix(int vertex){
        this.Vertex = vertex;
        Matrix = new int[vertex][vertex];
    }

    /**
     * Method for adding an edge to the matrix, this places a 1 in the
     * coresponding location for the source and destination
     * @param source int value of the node within the map
     * @param dest int value of the node within the map
     */
    public void addEdge(int source, int dest){
        Matrix[source][dest] = 1;
        Matrix[dest][source] = 1;
    }

    /**
     * Method used for the printing of the current adjacency matrix
     * this allows for a view of the overall connections within the graph
     */
    public void printMatrix(){
        System.out.println("Graph Adjacency Matrix");
        //printing the top column count
        for (int k = 0; k < Vertex; k++){
            if (k < 10){
                System.out.print(k+ "  ");
            }
            else{
                System.out.print(k+ " ");
            }

        }
        System.out.println("\n");

        //printing the contents of the connections in each row
        for (int i = 0; i < Vertex; i++){
            for (int j = 0; j < Vertex; j++){
                System.out.print(Matrix[i][j]+ "  ");
            }
            //printing the row numbers on the right side of the matrix
            System.out.print("  " +i);
            System.out.println();
        }
    }


}
