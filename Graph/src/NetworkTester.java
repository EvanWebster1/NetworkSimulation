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
        System.out.println();
        System.out.println("Welcome to the Network Simulation ");
        System.out.println("For connected info type (info) ");
        System.out.println("To remove a city from the map type (remove) ");
        System.out.println("To remove an edge from map type (edge) ");
        String option = keyboard.nextLine();

        //Retrieves the connected cities or removes cities from the map
        while (!option.equals("END")){
            //retrieves connected cities to user input city
            if (option.equals("info")){
                System.out.println("Enter a City to retrieve connected locations: ");
                option = keyboard.nextLine();
                graph.listConnected(option);
                graph.listVirus(option);
            }

            //removes a city of the user choice from the map
            if (option.equals("remove")){
                System.out.println("What city would you like to remove: ");
                option = keyboard.nextLine();
                graph.removeVertex(option);
            }

            if (option.equals("edge")){
                System.out.println("Enter two locations for edge to be removes: ");
                option = keyboard.nextLine();
                String option2 = keyboard.nextLine();
                graph.removeEdge(option, option2);
            }

            if (option.equals("virus")){
                System.out.println("Enter the location and virus to be added: ");
                option = keyboard.nextLine();
                String option2 = keyboard.nextLine();
                //graph.getVertmap().get(option).addVirus(option2);
            }
            System.out.println("What would you like to do?: ");
            option = keyboard.nextLine();
        }
    }


    public static void main(String[] args) throws FileNotFoundException {
        NetworkGui gui = new NetworkGui();
    }
}
