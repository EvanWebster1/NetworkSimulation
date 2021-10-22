import java.io.FileNotFoundException;
import java.util.Scanner;

public class NetworkTester {
    public NetworkTester() {
    }

    //Method for manipulating graph using user input
    //Params: current instance of the graph class being used
    public void userLoop(Graph graph){
        Scanner keyboard = new Scanner(System.in);
        //Prompting the user to enter a city for connected cities information
        System.out.println("Welcome to the Network Simulation ");
        System.out.println("For connected info type (info) ");
        System.out.println("To remove a city from the map type (remove) ");
        String option = keyboard.nextLine();

        //Retrieves the connected cities or removes cities from the map
        while (!option.equals("END")){
            //retrieves connected cities to user input city
            if (option.equals("info")){
                System.out.println("Enter a City to retrieve connected locations: ");
                option = keyboard.nextLine();
                graph.listConnected(option);
            }

            //removes a city of the user choice from the map
            if (option.equals("remove")){
                System.out.println("What city would you like to remove: ");
                option = keyboard.nextLine();
                graph.removeVertex(option);
            }
            System.out.println("What would you like to do?: ");
            option = keyboard.nextLine();
        }
    }


    public static void main(String[] args) throws FileNotFoundException {
        Graph graph = new Graph();
        graph = graph.createGraph();
        System.out.println(graph.getMap().keySet());

        NetworkTester out = new NetworkTester();
        out.userLoop(graph);
/*
        graph.listConnected("London");
        graph.removeVertex("London");
        graph.listConnected("London");
        graph.listConnected("Atlanta");
        graph.listConnected("Johannesburg");

 */

    }
}
