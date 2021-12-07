import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class NetworkTester {
    private ArrayList<String> outbreaks = new ArrayList<>();
    private StopWatch st = new StopWatch();
    int[][] adjMatrix;

    public NetworkTester() {
    }

    //Method for manipulating graph using user input
    //Params: current instance of the graph class being used
    public void userLoop(Graph graph){
        BFS breadth = new BFS(graph.getVertmap().size());
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Welcome to the Network Simulation ");
        System.out.println("To exit ui (END)");
        System.out.println("For Node info (info) ");
        System.out.println("For Outbreak information (outbreak) ");
        System.out.println("To view the written graph connections (graph) ");
        System.out.println("To view graph status (status) ");
        System.out.println("To view alerts (alerts)");
        System.out.println("To view Adjacency Matrix (matrix)");
        System.out.println("To view shortest path between 2 nodes (path)");

        String option = keyboard.nextLine();

        //Retrieves the connected cities or removes cities from the map
        while (!option.equals("END")){
            //retrieves connected cities to user input city
            if (option.equals("info")){
                System.out.println("Enter a City to retrieve connected locations: ");
                option = keyboard.nextLine();
                st.reset();
                st.start();
                if (!graph.getVertmap().containsKey(option)){
                    System.out.println("Specified node not on graph");
                    continue;
                }
                else if (graph.getVertmap().get(option).getActive()){
                    System.out.println("The node: " +option+ " is active");
                    graph.listConnected(option);
                    graph.listVirus(option);
                    graph.listFirewallVirus(option);
                    st.stop();
                    System.out.println("Retrieval of information took: " +st.getElapsedTime() /1000f+ " microseconds");
                }
                else{
                    System.out.println("The node: " +option+ " is not active");
                }

            }

            //removes a city of the user choice from the map
            if (option.equals("graph")){
                System.out.println("Current state of the graph is: ");
                graph.printGraph();
            }

            //entering outbreak will print all the outbreaks on the graph
            if (option.equals("outbreak")){
                st.reset();
                st.start();
                //System.out.println("Nodes " +outbreaks+ " have outbreaks totaling: " +outbreaks.size()+ " outbreaks");
                outbreaks = graph.Outbreak(outbreaks);
                st.stop();
                System.out.println("Retreiving outbreaks took: " +st.getElapsedTime() / 1000f  + " microseconds");

            }

            //entering alerts will prompt for a node to output all the alerts contained on that node
            if (option.equals("alerts")){
                System.out.println("Enter Location to retrieve alerts from: ");
                option = keyboard.nextLine();
                System.out.println("node: " +option+ " has " + graph.getVertmap().get(option).getAlerts()+ " alerts");
            }

            //matrix will output the adjacency matrix for the graph
            if (option.equals("matrix")){
                System.out.println("Contents of Adjacency Matrix: ");
                graph.CreateAdjacency();
            }

            //status prints all the infected nodes, attacked nodes, inactive nodes, and protected nodes
            if (option.equals("status")){
                st.reset();
                st.start();
                graph.listInfected();
                graph.listattacked();
                graph.inActive();
                graph.printProtected();
                System.out.println("\nNodes " + outbreaks+ " have outbreaks for a total of " +outbreaks.size()+ " nodes");
                st.stop();
                System.out.println("Time to retrieve graph status: " + st.getElapsedTime()/1000f+ " microseconds");
            }

            //allows the user to enter 2 nodes to find the shortest safe path between them
            if (option.equals("path")){
                System.out.println("Enter a source node: ");
                option = keyboard.nextLine();
                System.out.println("Enter a destination node: ");
                String option2 = keyboard.nextLine();
                if (!graph.getVertmap().containsKey(option) || !graph.getVertmap().containsKey(option2)){
                    System.out.println("One or more specified node node on graph");
                    continue;
                }
                else {
                    System.out.println("All paths between " +option+ " and " +option2+ " are as follows: ");
                    AllPaths p = new AllPaths(option, option2, graph);
                    System.out.println("Found " +p.count+ " safe paths from " +option+ " to " +option2);
                    st.reset();
                    st.start();
                    breadth.BFS_Algo(graph, option, option2);
                    st.stop();
                    System.out.println("Finding the shortest safe route took a total of "
                            +st.getElapsedTime()/1000f+ " microseconds");
                }
            }

            if (option.equals("help")){
                System.out.println("To exit ui (END)");
                System.out.println("For Node info (info) ");
                System.out.println("For Outbreak information (outbreak) ");
                System.out.println("To view the written graph connections (graph) ");
                System.out.println("To view graph status (status) ");
                System.out.println("To view alerts (alerts)");
                System.out.println("To view Adjacency Matrix (matrix)");
                System.out.println("To view shortest path between 2 nodes (path)");
            }

            System.out.println("What would you like to do?: ");
            option = keyboard.nextLine();
        }
    }


    public static void main(String[] args) throws FileNotFoundException, ParseException {
        Graph G = new Graph();
        G.createGraph();
        NetworkTester test = new NetworkTester();
        G.Outbreak(test.outbreaks);
        //test.adjMatrix = new int[G.getVertmap().size()][G.getVertmap().size()];
        //test.adjMatrix = G.CreateAdjacency();
        test.userLoop(G);
    }
}
