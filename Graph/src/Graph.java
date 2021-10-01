import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.*;

public class Graph<Vertex> {
    private Map<Vertex, List<Vertex>> map = new HashMap<>();

    public void addNewVertex(Vertex label){
        map.put(label, new LinkedList<Vertex>());
    }

    public void addNewEdge(Vertex source, Vertex destination, boolean bidirectional){
        if (!map.containsKey(source)){
            addNewVertex(source);
        }
        if (!map.containsKey(destination)){
            addNewVertex(destination);
        }
        map.get(source).add(destination);
        if (bidirectional == true){
            map.get(destination).add(source);
        }
    }

    public void countVertices(){
        System.out.println("Total number of vertices: " +map.keySet().size());
    }

    public void countEdges(boolean bidirection){
        int count = 0;
        for (Vertex Vertex : map.keySet()){
            count = count + map.get(Vertex).size();
        }
        if (bidirection == true){
            count = count / 2;
        }
        System.out.println("Total number of edges: " + count);

    }

    //checks if a graph has a vertex
    public void containsVertex(Vertex label){
        if (map.containsKey(label)){
            System.out.println("The graph contains " + label + " as a vertex");
        }
        else {
            System.out.println("The graph does not contain " + label + " as a vertex");
        }
    }

    public void containsEdge(Vertex source, Vertex destination){
        if (map.get(source).contains(destination)){
            System.out.println("The Graph has an edge between " +source+ " and " +destination);
        }
        else{
            System.out.println("The graph has no edge between " +source+ " and " +destination);
        }
    }

    public static void main(String[] args){
        Graph g1 = new Graph();
        g1.addNewVertex("London");
        g1.addNewVertex("Ontario");
        g1.addNewEdge("Ontario", "London", true);
        g1.countVertices();
        g1.countEdges(false);
        //g1.containsVertex("Kingston");
        g1.containsEdge("Ontario", "London");
        System.out.println(g1.map.values());
        g1.addNewEdge("Ontario", "Kingston", true);
        System.out.println(g1.map.values());
    }

}

