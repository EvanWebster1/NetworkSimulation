import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.*;
import java.util.Scanner;
import java.io.FileNotFoundException;

/**
 * Graph class is used to make and store the maps that build the overall graph of the network
 */
public class Graph{
    private Map<String, ArrayList<Vertex>> map;
    private Map<String, Vertex> vertmap;

    /**
     * Default constructor to create a graph class.
     * setting a new map with key of a String and ArrayList of Vertex's
     * and a vertex map linking a String of the node name to the Vertex object
     */
    public Graph() {
        //String of node name, with the list of all connected vertex's
        map = new HashMap<String, ArrayList<Vertex>>();
        //String node name, with the vertex object for the key node
        vertmap = new HashMap<String, Vertex>();
    }

    /**
     * @return the current Map of a String node name and its links
     */
    public Map<String, ArrayList<Vertex>> getMap() {
        return map;
    }

    /**
     * @return the current map of string linked to its vertex object
     */
    public Map<String, Vertex> getVertmap(){return vertmap;}

    /**
     * Adds a new vertex to the links map and the vertex object map
     * Vertex is only added if it isn't already in the map
     * @param src Vertex object to be added
     */
    public void addNewVertex(Vertex src){
        map.putIfAbsent(src.getSrc(), new ArrayList<Vertex>());
        vertmap.putIfAbsent(src.getSrc(), src);
    }

    /**
     * Removes a Vertex from the map
     * if the vertex passed as a param is in the map
     * it is removed from the all connected nodes
     * @param src String to be removed
     */
    public void removeVertex (String src){

        for (int i = 0; i < map.get(src).size(); i++){
            Vertex v = new Vertex(map.get(src).get(i).getSrc());
            for (int j = 0; j < map.get(v.getSrc()).size(); j++){
                if (map.get(v.getSrc()).get(j).getSrc().equals(src)){
                    map.get(v.getSrc()).remove(j);
                }
            }
        }
        map.remove(src);
        vertmap.remove(src);
    }

    /**
     * adds an edge from the corresponding map value
     * each the dest is added to the corresponding src,
     * and same for src added to dest link
     * @param src String location 1
     * @param dest String location 2
     */
    public void addEdge(String src, String dest){
        Vertex v1 = new Vertex(src);
        Vertex v2 = new Vertex(dest);
        map.get(src).add(v2);
        map.get(dest).add(v1);

    }

    /**
     * Removes an edge from the map between two Strings passed as params.
     * each nodes connections are searched through until the opposite
     * node is found, then removed. repeated for the other connected side.
     * @param src String location 1
     * @param dest String location 2
     */
    public void removeEdge(String src, String dest){
        Vertex v1 = new Vertex(src);
        Vertex v2 = new Vertex(dest);
        for (int i = 0; i < map.get(v1.getSrc()).size(); i++) {
            if (map.get(v1.getSrc()).get(i).getSrc().equals(dest)) {
                map.get(src).remove(i);
            }
        }
        for (int i = 0; i < map.get(v2.getSrc()).size(); i++) {
            if (map.get(v2.getSrc()).get(i).getSrc().equals(src)) {
                map.get(dest).remove(i);
            }
        }

    }

    /**
     * Method for creating the graph by parsing through the graph.txt, and attack.txt file
     * in the default location, and sorting the relevant information into its correct location.
     * information within lines is split using ", ", and sections are split by a series
     * of "---"
     * @return graph object of build maps
     * @throws FileNotFoundException if the specified files are not found in the default location
     */
    public void createGraph() throws FileNotFoundException, ParseException {
        //importing the graph.txt file in the project folder
        //Graph g1 = new Graph();
        File myInput = new File("sampleGraph.txt");
        Scanner s = new Scanner(myInput);
        String line = s.nextLine();

        //looping through the graph.txt file adding the vertex's to the connections map and vertex map
        while (!line.contains("---")){
            String[] tokens = line.split(",");

            addNewVertex(new Vertex(tokens[0]));
            //if there is a 4th split in the line set and it isn't null set firewall to true
            if (tokens.length == 4){
                if (tokens[3] != null){
                    vertmap.get(tokens[0]).setFirewall(true);

                }
            }

            line = s.nextLine();
        }

        line = s.nextLine();

        //loop through the second section of graph.txt adding connections between nodes
        while (s.hasNext()){
            String[] tokens = line.split(", ");

            addEdge(tokens[0], tokens[1]);
            line = s.nextLine();
        }

        //importing attack.txt file from the default path in the project folder
        File myAttack = new File("Attack.txt");
        Scanner a = new Scanner(myAttack);
        String aline = a.nextLine();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //parsing through the attack.txt file adding virus' to their proper lists
        while (a.hasNext()){
            String[] tokens = aline.split(", ");
            //if the firewall is active add the virus to the firewallvirus list
            String date = tokens[2] + " " + tokens[3];

            if (vertmap.get(tokens[0]).getFirewall()){
                vertmap.get(tokens[0]).addfirewall_virus(tokens[1], formatter.parse(date));
            }
            //else add virus to the virus list
            else{
                vertmap.get(tokens[0]).addVirus(tokens[1], formatter.parse(date));
            }
            //same as done or the last line of the file
            aline = a.nextLine();
            if (!a.hasNext()){
                tokens = aline.split(",");
                String ndate = tokens[2] + " " + tokens[3];
                if (vertmap.get(tokens[0]).getFirewall()){
                    vertmap.get(tokens[0]).addfirewall_virus(tokens[1], formatter.parse(ndate));
                }
                //else add virus to the virus list
                else{
                    vertmap.get(tokens[0]).addVirus(tokens[1], formatter.parse(ndate));
                }
            }

        }
        for (Map.Entry<String, Vertex> entry : vertmap.entrySet()){
            String k = entry.getKey();
            sort(k, 0, vertmap.get(k).getVirus().size() -1);

        }

    }

    /**
     * Override method of creategraph used when a FILE object is already obtained
     * @param file FILE object to be searched through
     * @return Graph object
     * @throws FileNotFoundException throws error if the specified file is not found in the location
     */
    public Graph createGraph(File file) throws FileNotFoundException{
        Graph g1 = new Graph();
        Scanner s = new Scanner(file);
        String line = s.nextLine();

        //parsing through the graph file that has been passed in
        while (!line.contains("---")){
            String[] tokens = line.split(", ");

            g1.addNewVertex(new Vertex(tokens[0]));
            if (tokens.length == 4){
                if (tokens[3] != null){
                    g1.vertmap.get(tokens[0]).setFirewall(true);

                }
            }
            line = s.nextLine();
        }

        line = s.nextLine();

        //adding the connections between the nodes based on the txt file
        while (s.hasNext()){
            String[] tokens = line.split(", ");

            g1.addEdge(tokens[0], tokens[1]);
            line = s.nextLine();
        }

        return g1;
    }

    /**
     * Method for importing the attack file when a FILE object is already obtained
     * @param file FILE object to be parsed through
     * @throws FileNotFoundException throws error if specified file is not found
     */
    public void createAttack(File file) throws FileNotFoundException, ParseException {
        Scanner a = new Scanner(file);
        String aline = a.nextLine();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        //DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");

        //parsing through the attack.txt file adding virus' to their proper lists
        while (a.hasNext()) {
            String[] tokens = aline.split(", ");
            //if the firewall is active add the virus to the firewallvirus list
            String date = tokens[2] + " " + tokens[3];
            if (this.vertmap.get(tokens[0]).getFirewall()) {
                this.vertmap.get(tokens[0]).addfirewall_virus(tokens[1], formatter.parse(date));
            }
            //else add virus to the virus list
            else {
                this.vertmap.get(tokens[0]).addVirus(tokens[1], formatter.parse(date));
            }
            //same as done or the last line of the file
            aline = a.nextLine();
            if (!a.hasNext()) {
                tokens = aline.split(",");
                String ndate = tokens[2] + " " + tokens[3];
                if (this.vertmap.get(tokens[0]).getFirewall()) {
                    this.vertmap.get(tokens[0]).addfirewall_virus(tokens[1], formatter.parse(ndate));
                }
                //else add virus to the virus list
                else {
                    this.vertmap.get(tokens[0]).addVirus(tokens[1], formatter.parse(ndate));
                }
            }
        }
        for (Map.Entry<String, Vertex> entry : vertmap.entrySet()){
            String k = entry.getKey();
            sort(k, 0, vertmap.get(k).getVirus().size() -1);
        }
    }

    /**
     * if the node exists on the map it is
     * outputted with its connected nodes
     * @param src String node name
     */
    public void listConnected(String src){
        if (!map.containsKey(src)){
            System.out.println("The City " +src+ " isn't on the map ");
            return;
        }

        System.out.println("Location: " + src+ " is connected to: ");
        for (Vertex v : map.get(src)){
            System.out.println(v.getSrc());
        }
    }

    /**
     * prints the current state of the graph
     * with all the nodes and all connections to each node
     */
    public void printGraph(){
        System.out.println("\n Printing contents of the graph \n");
        if (map.isEmpty()){
            System.out.println("Current Graph is empty");
            return;
        }
        for (Map.Entry<String, ArrayList<Vertex>> entry : map.entrySet()){
            String k = entry.getKey();
            System.out.print("Key: " +k+ " is Connected to: ");
            for (Vertex v : map.get(k)){
                System.out.print(v.getSrc() +", ");
            }
            System.out.println();
        }
    }

    /**
     * Prints all the virus' in the network on each node
     */
    public void printVirus(){
        System.out.println("\n Printing virus' in the network \n");
        for (Map.Entry<String, Vertex> entry : vertmap.entrySet()){
            String k = entry.getKey();
            System.out.println("Node " +k+ " has virus': " + vertmap.get(k).getVirus());
        }
    }

    /**
     * Lists all virus' on a specific node
     * @param node String to be searched
     */
    public void listVirus(String node){
        if (vertmap.get(node).getVirus().isEmpty()){
            System.out.println("This node is not infected with any virus' ");
            return;
        }
        System.out.println("The node " +node+ " has virus': " +vertmap.get(node).getVirus());
        for (int i = 0; i < vertmap.get(node).getVirus().size(); i++){
            System.out.println(vertmap.get(node).getVirus().get(i).getType() + " at: "
                    + vertmap.get(node).getVirus().get(i).getDate());
        }
    }

    /**
     * Prints a list of nodes (with a count) that have firewalls
     */
    public void printProtected(){
        int count = 0;
        System.out.println();
        for (Map.Entry<String, Vertex> entry : vertmap.entrySet()){
            String k = entry.getKey();
            if (vertmap.get(k).getFirewall()){
                System.out.println(k + " has an active firewall");
                count++;
            }
        }
        System.out.println("There are a total of " +count+ " Protected nodes");
    }

    /**
     * Prints all the infected nodes in the graph
     */
    public void listInfected(){
        int count = 0;
        System.out.println();
        for (Map.Entry<String, Vertex> entry : vertmap.entrySet()){
            String k = entry.getKey();
            if (!vertmap.get(k).getVirus().isEmpty()){
                System.out.println(k + " is infected");
                count++;
            }
        }
        System.out.println("There are a total of " +count+ " Protected nodes");
    }

    /**
     * Prints all the nodes that have been attacked.
     * this being virus trying to affect the node but the node
     * has a firewall
     */
    public void listattacked(){
        int count = 0;
        System.out.println();
        for (Map.Entry<String, Vertex> entry : vertmap.entrySet()){
            String k = entry.getKey();
            if (!vertmap.get(k).getFirewall_virus().isEmpty()){
                System.out.println(k + " has been attacked with a firewall");
                count++;
            }
        }
        System.out.println("There are a total of " +count+ " stopped attacks");
    }

    /**
     * Calculates the time between two dates passed in as params
     * @param date1 Date object
     * @param date2 Date object
     * @return Long of the time passed between the two dates in seconds
     */
    public long TimeBetween(Date date1, Date date2){
        long time;
        time = Math.abs((date2.getTime() - date1.getTime()) / 1000) ;
        //System.out.println("There are: " +(date1.getTime() - date2.getTime()) / 1000+ " seconds between the two dates " + time);
        return time;
    }

    /**
     * Checks if there is an outbreak on the network. If 4 virus' of the same type infect a node
     * within a time period of 4 min then that node has an outbreak, 1 of that type of
     * virus is added to all connected nodes.
     * if 6 virus' (containing at least 2 types) infect a network within 6 min, the node is
     * set to inactive
     * @return ArrayList<String> of all nodes that have an outbreak;
     */
    public ArrayList<String> Outbreak(ArrayList<String> outbreaks){
        //ArrayList<String> outbreak = new ArrayList<>();
        System.out.println();
        int size = outbreaks.size();

        for (Map.Entry<String, Vertex> entry : vertmap.entrySet()){
            String k = entry.getKey();
            Set<String> type = new HashSet<>();
            long time = 0;

            if (!outbreaks.contains(k) && vertmap.get(k).getActive()){
                if (vertmap.get(k).getVirus().size() >= 4){
                    for (int i = 0; i <= vertmap.get(k).getVirus().size() -1; i++){
                        if (i == 0){
                            continue;
                        }
                        else{
                            time += TimeBetween(vertmap.get(k).getVirus().get(i).getDate(), vertmap.get(k).getVirus().get(i-1).getDate());
                        }

                        type.add(vertmap.get(k).getVirus().get(i).getType());

                        if ((i == 3) && (type.size() == 1) && (time <= 240)){
                            outbreaks.add(k);
                            String Stype = type.iterator().next();
                            System.out.println("There is an outbreak on Node: " +k+ " of type: " +Stype);

                            for (Vertex v : map.get(k)){
                                if (vertmap.get(v.getSrc()).getFirewall()){
                                    vertmap.get(v.getSrc()).addfirewall_virus(Stype, vertmap.get(k).getVirus().get(i).getDate());
                                }
                                else{
                                    vertmap.get(v.getSrc()).addVirus(Stype, vertmap.get(k).getVirus().get(i).getDate());
                                }
                                System.out.println("Virus type " +Stype+ " has been added to node " +v.getSrc()+ " at "
                                        +vertmap.get(k).getVirus().get(i).getDate());
                            }
                        }
                        if ((i == 5) && (type.size() > 1) && (time <= 360)){
                            vertmap.get(k).setActive(false);
                            System.out.println(k+ " has aquired too many virus', node is now offline");
                        }
                    }
                }
            }
        }
        if (outbreaks.size() == size){
            return outbreaks;
        }
        return Outbreak(outbreaks);
    }

    /**
     * prints all the inactive nodes
     */
    public void inActive(){
        ArrayList<String> node = new ArrayList<>();

        for (Map.Entry<String, Vertex> entry : vertmap.entrySet()){
            String k = entry.getKey();

            if (!vertmap.get(k).getActive()){
                System.out.println("Node: " +k+ " is inactive");
            }
        }
    }


    public void sort(String node, int low, int high){
        ArrayList<Virus> red = new ArrayList<>();
        ArrayList<Virus> blue = new ArrayList<>();
        ArrayList<Virus> black = new ArrayList<>();
        ArrayList<Virus> green = new ArrayList<>();

        ArrayList<Virus> sorted = new ArrayList<>();
        if (vertmap.get(node).getVirus() == null || vertmap.get(node).getVirus().size() == 0){
            return;
        }
        if (low >= high){
            return;
        }

        for (int i = 0; i < vertmap.get(node).getVirus().size() -1; i++){
            if (vertmap.get(node).getVirus().get(i).getType().equals("red")){
                red.add(vertmap.get(node).getVirus().get(i));
            }
            else if (vertmap.get(node).getVirus().get(i).getType().equals("blue")){
                blue.add(vertmap.get(node).getVirus().get(i));
            }
            else if (vertmap.get(node).getVirus().get(i).getType().equals("black")){
                black.add(vertmap.get(node).getVirus().get(i));
            }
            else if (vertmap.get(node).getVirus().get(i).getType().equals("green")){
                green.add(vertmap.get(node).getVirus().get(i));
            }
        }
        red = DateSort(red, low, red.size() -1);
        blue = DateSort(blue, low, blue.size() -1);
        black = DateSort(black, low, black.size() -1);
        green = DateSort(green, low, green.size() -1);

        ArrayList<Virus>[] size = new ArrayList[]{red, blue, black, green};


        for (int i = 0; i < size.length -1; i++){
            int most = i;
            for (int j = i; j < size.length -1; j++){
                if (size[most].size() < size[j].size()){
                    most = j;
                }
            }
            sorted.addAll(size[most]);
        }
        vertmap.get(node).setVirus(sorted);

    }

    public ArrayList<Virus> DateSort(ArrayList<Virus> type, int low, int high){
        if (type.isEmpty() || type.size() < 2){
            return type;
        }
        int middle = low + (high - low) / 2;
        Virus pivot = type.get(middle);

        int i = low, j = high;

        while (type.get(i).getDate().compareTo(pivot.getDate()) < 0){
            i++;
        }
        while (type.get(j).getDate().compareTo(pivot.getDate()) > 0){
            j--;
        }
        if (i <= j){
            Virus temp = type.get(i);
            type.set(i, type.get(j));
            type.set(j, temp);
            i++;
            j--;
        }
        if (low < j){
            type = DateSort(type, low, j);
        }
        if (high > i){
            type = DateSort(type, i, high);
        }

        return type;
    }


}