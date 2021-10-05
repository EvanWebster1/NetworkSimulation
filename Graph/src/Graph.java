import java.awt.*;
import java.beans.VetoableChangeListener;
import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.*;

public class Graph<T extends Vertex>{
    private Map<T, List<T>> map = new HashMap<>();

    public void addNewVertex(T src){
        map.putIfAbsent(src, new LinkedList<T>());
    }

    // Creates Vertex for src given to be removed from the map and all Vertex's connected
    public void removeVertex (String Src){
        Vertex src = new Vertex(Src);
        map.values().stream().forEach(e -> e.remove(src));
        map.remove(new Vertex(Src));
    }

    public void addNewEdge(T source, T destination){
        if(!map.containsKey(source)){
            addNewVertex(source);
        }
        if (!map.containsKey(destination)){
            addNewVertex(destination);
        }
        map.get(source).add(destination);
        map.get(destination).add(source);

    }

    public void countVertices(){
        System.out.println("Total number of vertices: " +map.keySet().size());
    }

    public void countEdges(){
        int count = 0;
        for (Vertex Vertex : map.keySet()){
            count = count + map.get(Vertex).size();
        }

        System.out.println("Total number of edges: " + count);

    }

    //checks if a graph has a vertex
    public void containsVertex(T label){
        if (map.containsKey(label)){
            System.out.println("The graph contains " + label + " as a vertex");
        }
        else {
            System.out.println("The graph does not contain " + label + " as a vertex");
        }
    }

    public void containsEdge(String source, String destination){
        Vertex src = new Vertex(source);
        Vertex dest = new Vertex(destination);
        if (map.get(src).contains(dest)){
            System.out.println("The Graph has an edge between " +source+ " and " +destination);
        }
        else{
            System.out.println("The graph has no edge between " +source+ " and " +destination);
        }
    }

    public static void main(String[] args){
        Graph g1 = new Graph();
        g1.addNewVertex(new Vertex("London"));
        g1.addNewVertex(new Vertex("Ontario"));
        g1.countVertices();

        /*
        g1.addNewEdge("Ontario", "London");
        g1.countVertices();
        g1.countEdges(false);
        //g1.containsVertex("Kingston");
        g1.containsEdge("Ontario", "London");
        System.out.println(g1.map.values());
        g1.addNewEdge("Ontario", "Kingston");
        System.out.println(g1.map.values());
        g1.addNewEdge("London", "Kingston");
        System.out.println(g1.map.values());
        g1.addNewVertex("Tokyo");
        System.out.println(g1.map.values());
         */
    }

}

class Vertex {
    private String src;
    private Vertex[] dest;
    private Point Location;
    private Date date;
    private Time time;
    private String virus;
    private Boolean firewall;

    Vertex(String label){
        this.src = label;
    }

}