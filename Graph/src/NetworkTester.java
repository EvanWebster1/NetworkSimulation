import java.io.FileNotFoundException;
import java.util.Scanner;

public class NetworkTester {
    public static void main(String[] args) throws FileNotFoundException {
        Graph graph = new Graph();
        graph = graph.createGraph();
        System.out.println(graph.getMap().keySet());

        //Creating scanner for user input
        Scanner keyboard = new Scanner(System.in);

        //Prompting the user to enter a city for connected cities information
        System.out.println("Enter a City to retrieve connected locations: ");
        String option = keyboard.nextLine();

        //Continues to retrieve connected cities of input locations until END is entered
        while (!option.equals("END")){
            graph.listConnected(option);
            System.out.println("Enter a City to retrieve connected locations: ");
            option = keyboard.nextLine();
        }

    }
}
