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
}

class Vertex{
    String label;

    Vertex(String label){
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return Objects.equals(label, vertex.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }
}
