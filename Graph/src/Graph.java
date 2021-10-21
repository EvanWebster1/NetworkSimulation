import java.awt.*;
import java.io.File;
import java.sql.Time;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.*;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Graph{
    private Map<String, ArrayList<Vertex>> map;

    public Graph() {
        map = new HashMap<String, ArrayList<Vertex>>();
    }

    public Map<String, ArrayList<Vertex>> getMap() {
        return map;
    }

    public void setMap(Map<String, ArrayList<Vertex>> map) {
        this.map = map;
    }

    public void addNewVertex(Vertex src){
        map.putIfAbsent(src.getSrc(), new ArrayList<Vertex>());
        map.get(src.getSrc()).add(src);
    }

    public void removeVertex (String Src){
        Vertex v = new Vertex(Src);
        map.values().stream().forEach(e -> e.remove(v));
        map.remove(new Vertex(Src));
    }

    void addEdge(String src, String dest){
        Vertex v1 = new Vertex(src);
        Vertex v2 = new Vertex(dest);
        map.get(v1).add(v2);
        map.get(v2).add(v1);
    }

    void removeEdge(String src, String dest){
        Vertex v1 = new Vertex(src);
        Vertex v2 = new Vertex(dest);
        List<Vertex> ev1 = map.get(v1);
        List<Vertex> ev2 = map.get(v2);
        if (ev1 != null){
            ev1.remove(v2);
        }
        if (ev2 != null){
            ev2.remove(v1);
        }
    }

    Graph createGraph()throws FileNotFoundException{
        Graph g1 = new Graph();
        File myInput = new File("graph.txt");
        Scanner s = new Scanner(myInput);
        int i = 0;
        while (s.hasNext()){
            String line = s.nextLine();
            String[] tokens = line.split(",");

            g1.addNewVertex(new Vertex(tokens[0]));
            i++;
        }
        return g1;
    }

}
