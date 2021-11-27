import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class NetworkTester {
    private ArrayList<String> outbreaks = new ArrayList<>();
    private StopWatch st = new StopWatch();

    public NetworkTester() {
    }

    //Method for manipulating graph using user input
    //Params: current instance of the graph class being used
    public void userLoop(Graph graph){
        Scanner keyboard = new Scanner(System.in);
        //Prompting the user to enter a city for connected cities information
        //System.out.println();
        System.out.println("Welcome to the Network Simulation ");
        System.out.println("For connected info type (info) ");
        System.out.println("For Outbreak information (outbreak) ");
        System.out.println("To view the graph (graph) ");
        String option = keyboard.nextLine();

        //Retrieves the connected cities or removes cities from the map
        while (!option.equals("END")){
            //retrieves connected cities to user input city
            if (option.equals("info")){
                System.out.println("Enter a City to retrieve connected locations: ");
                option = keyboard.nextLine();
                st.reset();
                st.start();
                if (graph.getVertmap().get(option).getActive()){
                    System.out.println("The node: " +option+ " is active");
                    graph.listConnected(option);
                    System.out.println("Took: " +st.getElapsedTime() /1000f+ " to retreive conn");
                    graph.listVirus(option);
                    System.out.println("Took: " +st.getElapsedTime()/1000f+ " to retreive virus");
                    graph.listFirewallVirus(option);
                    System.out.println("Took: " +st.getElapsedTime()/1000f+ " to retreive firewall stopped");
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

            if (option.equals("outbreak")){
                st.reset();
                st.start();
                System.out.println("Nodes " +outbreaks+ " have outbreaks");
                st.stop();
                if (st.getElapsedTime() > 1000){
                    System.out.println("Retreiving outbreaks took: " +st.getElapsedTime() / 1000f  + " microseconds");
                }
                else{
                    System.out.println("Retreiving outbreaks took: " +st.getElapsedTime() /1000f+ " microseconds");
                }
                //System.out.println("The nodes: " +outbreaks+ " have outbreaks");
            }

            if (option.equals("alerts")){
                System.out.println("Enter Location to retrieve alerts from: ");
                option = keyboard.nextLine();
                System.out.println("node: " +option+ " has " + graph.getVertmap().get(option).getAlerts()+ " alerts");
            }
            System.out.println("What would you like to do?: ");
            option = keyboard.nextLine();
        }
    }


    public static void main(String[] args) throws FileNotFoundException, ParseException {
        //NetworkGui gui = new NetworkGui();
        Graph G = new Graph();
        G.createGraph();
        NetworkTester test = new NetworkTester();
        G.Outbreak(test.outbreaks);
        test.userLoop(G);
    }
}
