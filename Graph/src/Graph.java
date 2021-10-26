import java.io.File;
import java.util.Map;
import java.util.*;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Graph{
    private Map<String, ArrayList<Vertex>> map;
    private Map<String, Vertex> vertmap;

    //Default constructor to create a graph class.
    //making an instance of map as a hashmap with param: String, and ArrayList of Vertex's
    //vertmap stored a map of the vertex's stored in a pair with the string of its name.
    public Graph() {
        map = new HashMap<String, ArrayList<Vertex>>();
        vertmap = new HashMap<String, Vertex>();
    }

    //Returns the current hashmap
    public Map<String, ArrayList<Vertex>> getMap() {
        return map;
    }

    //returns the vertex map
    public Map<String, Vertex> getVertmap(){return vertmap;}

    //adds a new kay pair to the map, only if the src key doesn't
    //already appear in the maps keys
    public void addNewVertex(Vertex src){
        map.putIfAbsent(src.getSrc(), new ArrayList<Vertex>());
        vertmap.putIfAbsent(src.getSrc(), src);
    }

    //Removes a String src from the keys of the map
    //removes all connections to the node being removed
    public void removeVertex (String src){
        //Vertex src = new Vertex(Src);
        //map.get(Src).forEach(e ->e.remove(Src));
        //int i = 0;

        for (int i = 0; i < map.get(src).size(); i++){
            Vertex v = new Vertex(map.get(src).get(i).getSrc());
            for (int j = 0; j < map.get(v.getSrc()).size(); j++){
                if (map.get(v.getSrc()).get(j).getSrc().equals(src)){
                    map.get(v.getSrc()).remove(j);
                }
            }
        }
        map.remove(src);

    }

    //Adds the param src and dest (both strings) to the map value
    //under each corresponding key, therefore creating an edge between the two locations
    public void addEdge(String src, String dest){
        Vertex v1 = new Vertex(src);
        Vertex v2 = new Vertex(dest);
        map.get(v1.getSrc()).add(v2);
        map.get(v2.getSrc()).add(v1);

    }

    //removes edge from the graph, using param: String src and dest
    //this removes the src from the map key dest and vice verse
    public void removeEdge(String src, String dest){
        Vertex v1 = new Vertex(src);
        Vertex v2 = new Vertex(dest);
        for (int i = 0; i < map.get(v1.getSrc()).size(); i++) {
            if (map.get(v1.getSrc()).get(i).getSrc().equals(dest)) {
                map.get(src).remove(i);
            }
        }
        for (int i = 0; i < map.get(v2.getSrc()).size(); i++) {
            if (map.get(v2.getSrc()).get(i).getSrc().equals(src)) {
                map.get(dest).remove(i);
            }
        }

    }

    //Method for creating the graph by taking parsing through the graph.txt file
    //and taking the relevant information and sorting it into its correct location
    //lines currently split by "," for new cities from their point locations
    //and seperated by "---" to show where adding connections starts
    //returns a graph object
    public Graph createGraph()throws FileNotFoundException{
        Graph g1 = new Graph();
        File myInput = new File("graph.txt");
        Scanner s = new Scanner(myInput);
        String line = s.nextLine();

        while (!line.equals("---")){
            String[] tokens = line.split(",");

            g1.addNewVertex(new Vertex(tokens[0]));
            line = s.nextLine();
        }

        line = s.nextLine();

        while (s.hasNext()){
            String[] tokens = line.split(", ");

            g1.addEdge(tokens[0], tokens[1]);
            line = s.nextLine();
        }


        return g1;
    }

    //Outputs the nodes currently connected to the input string
    //if the string passed in doesn't exist in the map, tells the user and returns
    public void listConnected(String src){
        if (!map.containsKey(src)){
            System.out.println("The City " +src+ " isn't on the map ");
            return;
        }

        System.out.println("Location: " + src+ " is connected to: ");
        for (Vertex v : map.get(src)){
            System.out.println(v.getSrc());
        }
    }

    //Method for printing the current keys and values of the graph
    public void printGraph(){
        System.out.println("\n Printing contents of the graph \n");
        if (map.isEmpty()){
            System.out.println("Current Graph is empty");
            return;
        }
        for (Map.Entry<String, ArrayList<Vertex>> entry : map.entrySet()){
            String k = entry.getKey();
            System.out.print("Key: " +k+ " is Connected to: ");
            for (Vertex v : map.get(k)){
                System.out.print(v.getSrc() +", ");
            }
            System.out.println();
        }
    }

    //method for printing all virus' in the network on each of the nodes
    public void printVirus(){
        System.out.println("\n Printing virus' in the network \n");
        for (Map.Entry<String, Vertex> entry : vertmap.entrySet()){
            String k = entry.getKey();
            System.out.println("Node " +k+ " has virus': " + vertmap.get(k).getVirus());
        }
    }

    public void listVirus(String node){
        if (vertmap.get(node).getVirus().isEmpty()){
            System.out.println("This node is not infected with any virus' ");
            return;
        }
        System.out.println("The node " +node+ " has virus': " +vertmap.get(node).getVirus());
    }

}
