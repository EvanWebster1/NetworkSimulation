import java.awt.*;
import java.io.File;
import java.sql.Time;
import java.util.List;
import java.util.Map;
import java.util.*;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Graph{
    private Map<String, ArrayList<Vertex>> map;

    //Default constructor to create a graph class.
    //making an instance of map as a hashmap with param: String, and ArrayList of Vertex's
    public Graph() {
        map = new HashMap<String, ArrayList<Vertex>>();
    }

    //Returns the current hashmap
    public Map<String, ArrayList<Vertex>> getMap() {
        return map;
    }

    public void setMap(Map<String, ArrayList<Vertex>> map) {
        this.map = map;
    }

    //adds a new kay pair to the map, only if the src key doesn't
    //already appear in the maps keys
    public void addNewVertex(Vertex src){

        map.putIfAbsent(src.getSrc(), new ArrayList<Vertex>());
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
        List<Vertex> ev1 = map.get(v1.getSrc());
        List<Vertex> ev2 = map.get(v2.getSrc());

        map.get(v1.getSrc()).remove(v2);

        if (ev1 != null){
            ev1.remove(v2);
        }
        if (ev2 != null){
            ev2.remove(v1);
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

}
