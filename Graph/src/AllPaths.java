import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AllPaths {

    private Map<String, Boolean> isVisited;
    private ArrayList<String> currentPath;
    private Map<String, Integer> adjVal = new HashMap<>();
    int count;

    public AllPaths(String start, String dest, Graph g){
        count = 0;
        isVisited = new HashMap<>();
        currentPath = new ArrayList<>();
        currentPath.add(start);
        printPaths(g, start, dest);
    }

    private void printPaths(Graph g, String start, String dest){

        if (!g.getVertmap().get(start).getVirus().isEmpty()){
            System.out.println("The starting node entered is infected");
            return;
        }
        if(!g.getVertmap().get(dest).getVirus().isEmpty()){
            System.out.println("The end node entered is infected");
            return;
        }
        if (start.equals(dest)){
            System.out.println(currentPath);
            count++;
            return;
        }
        isVisited.put(start, true);

        for (Vertex v : g.getMap().get(start)){
            if (!isVisited.containsKey(v.getSrc())){
                if (v.getActive() && v.getVirus().isEmpty()){
                    currentPath.add(v.getSrc());
                    printPaths(g, v.getSrc(), dest);
                    currentPath.remove(v.getSrc());
                }
            }
        }

        isVisited.remove(start);

    }

}
