import java.io.FileNotFoundException;

public class NetworkTester {
    public static void main(String[] args) throws FileNotFoundException {
        Graph graph = new Graph();
        graph = graph.createGraph();
        System.out.println(graph.getMap().keySet());
        //graph.addEdge("London", "Montreal");
        //graph.addEdge("London", "Miami");
        graph.listConnected("Montreal");
    }
}
