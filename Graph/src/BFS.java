import java.util.*;

public class BFS {
    private int len;
    private Queue<String> queue;
    private Map<String, Boolean> vis = new HashMap<>();
    private Map<String, String> prev = new HashMap<>();

    public BFS(int len){
        this.len = len;
        this.queue = new LinkedList<>();
    }

    /**
     * Breadth first search algorithm used to find the shorest safe path
     * from a startNode to a destinationNode. A safe node is notes as
     * any node that is active with no virus infections.
     * @param g Graph being worked with
     * @param startNode String starting node searching from
     * @param destNode Destination node to be reached
     */
    public void BFS_Algo(Graph g, String startNode, String destNode){
        List<String> directions = new LinkedList();
        String current = startNode;
        queue.add(current);
        vis.put(current, true);

        if (!g.getVertmap().get(startNode).getVirus().isEmpty()){
            System.out.println("Start node is currently infected and cannot be included in any safe route");
            return;
        }
        if (!g.getVertmap().get(destNode).getVirus().isEmpty()){
            System.out.println("Destination node is currently infected and cannot be included in any safe route");
            return;
        }


        while(!queue.isEmpty()){
            current = queue.remove();
            if (current.equals(destNode)){
                break;
            }
            else{
                for (Vertex v : g.getMap().get(current)){
                    if (!vis.containsKey(v.getSrc())){
                        if (v.getActive() && v.getVirus().isEmpty()){
                            queue.add(v.getSrc());
                            vis.put(v.getSrc(), true);
                            prev.put(v.getSrc(), current);
                        }
                    }
                }
            }
        }

        for (String node = destNode; node != null; node = prev.get(node)){
            directions.add(node);
        }

        List<String> finalPath = new LinkedList<>();
        if (directions.contains(startNode) && directions.contains(destNode)){
            for (int i = directions.size()-1; i >= 0; i-- ){
                finalPath.add(directions.get(i));
            }
            System.out.println("Shortest safe route from " +startNode+ " to " +destNode+ " follows: " +finalPath);
        }

    }


}
