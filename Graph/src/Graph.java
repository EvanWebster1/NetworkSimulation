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
     * Removes all the connections from a node specified as a param
     * the specified node is also removed from all connected nodes connections
     * @param src String to be removed
     */
    public void removeConnections (String src){

        for (int i = 0; i < map.get(src).size(); i++){
            Vertex v = new Vertex(map.get(src).get(i).getSrc());
            for (int j = 0; j < map.get(v.getSrc()).size(); j++){
                if (map.get(v.getSrc()).get(j).getSrc().equals(src)){
                    map.get(v.getSrc()).remove(j);
                }
            }
        }
    }

    /**
     * adds an edge from the corresponding map value
     * each the dest is added to the corresponding src,
     * and same for src added to dest link
     * @param src String location 1
     * @param dest String location 2
     */
    public void addEdge(String src, String dest){
        //adds the edge between the two nodes only if the edge doesn't exist
        if (!map.get(src).contains(vertmap.get(dest))){
            map.get(src).add(vertmap.get(dest));
        }
        if (!map.get(dest).contains(vertmap.get(src))){
            map.get(dest).add(vertmap.get(src));
        }

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

        File myInput = new File("Graph.txt");
        //File myInput = new File("sampleGraph.txt");
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
        File myAttack = new File("ownAttack.txt");
        //File myAttack = new File("Attack.txt");
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
        //sort all the virus' in the node being attacked by decending number of types
        //where each type is sorted chronologically
        for (Map.Entry<String, Vertex> entry : vertmap.entrySet()){
            String k = entry.getKey();
            if (!vertmap.get(k).getVirus().isEmpty()){
                sort(k, 0, vertmap.get(k).getVirus().size() -1);
            }
            else if (!vertmap.get(k).getFirewall_virus().isEmpty()){
                sort(k, 0, vertmap.get(k).getFirewall_virus().size() -1);
            }
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
            if (!vertmap.get(k).getActive()){
                System.out.print(k+ " is inactive");
            }
            else{
                System.out.print("Key: " +k+ " is Connected to: ");
            }

            for (Vertex v : map.get(k)){
                if (vertmap.get(k).getActive()){
                    System.out.print(v.getSrc() +", ");
                }
            }
            System.out.println();
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
        System.out.println("The node " +node+ " has " + vertmap.get(node).getVirus().size()+ " Virus'");
        for (int i = 0; i < vertmap.get(node).getVirus().size(); i++){
            System.out.println(vertmap.get(node).getVirus().get(i).getType() + " at: "
                    + vertmap.get(node).getVirus().get(i).getDate());
        }
    }

    public void listFirewallVirus(String node){
        if (vertmap.get(node).getFirewall_virus().isEmpty()){
            System.out.println("This node has not blocked any virus' ");
            return;
        }
        System.out.println("The node " +node+ " has blocked " + vertmap.get(node).getFirewall_virus().size()+ " Virus'");
        for (int i = 0; i < vertmap.get(node).getFirewall_virus().size(); i++){
            System.out.println(vertmap.get(node).getFirewall_virus().get(i).getType() + " at: "
                    + vertmap.get(node).getFirewall_virus().get(i).getDate());
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
        System.out.println("There are a total of " +count+ " infected nodes");
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
        System.out.println("There are a total of " +count+ " nodes that have been attacked with a firewall");
    }

    /**
     * Calculates the time between two dates passed in as params
     * @param date1 Date object
     * @param date2 Date object
     * @return Long of the time passed between the two dates in seconds
     */
    public long TimeBetween(Date date1, Date date2){
        long time;
        time = ((date1.getTime() - date2.getTime()) / 1000) ;
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

        int size = outbreaks.size();
        //loop used to retrieve all the node keys to the String k
        for (Map.Entry<String, Vertex> entry : vertmap.entrySet()){
            String k = entry.getKey();
            Set<String> type = new HashSet<>();
            ArrayList<Virus> added = new ArrayList<>();
            long time = 0;
            int prev = 0;

            //only attempts to find outbreaks if the node has 4 or more infections, is active, and
            // doesn't already have an outbreak
            if (!outbreaks.contains(k) && vertmap.get(k).getActive()){
                if (vertmap.get(k).getVirus().size() >= 4){
                    for (int i = 0; i <= vertmap.get(k).getVirus().size() -1; i++){
                        //if the virus type is different from the lastvirus. clear all information stored
                        //and start looking for an outbreak of that type
                        if (!type.contains(vertmap.get(k).getVirus().get(i).getType()) && !type.isEmpty()){
                            type.clear();
                            added.clear();
                            type.add(vertmap.get(k).getVirus().get(i).getType());
                            added.add(vertmap.get(k).getVirus().get(i));
                            time = 0;
                            prev = i;
                            continue;
                        }
                        else if (i == 0){
                            type.add(vertmap.get(k).getVirus().get(i).getType());
                            added.add(vertmap.get(k).getVirus().get(i));
                        }
                        else{
                            //setting a tempTime to the time between current virus and prev virus
                            long tempTime = TimeBetween(vertmap.get(k).getVirus().get(i).getDate(),
                                    vertmap.get(k).getVirus().get(prev).getDate());
                            //if the tempTime fits criteria for 4 min then it gets added to the total time
                            if (tempTime <=240){
                                time += tempTime;
                                prev = i;
                                if (time <= 240){
                                    //when time is less than 4 min the current type and virus get added to
                                    //their set, and list
                                    type.add(vertmap.get(k).getVirus().get(i).getType());
                                    added.add(vertmap.get(k).getVirus().get(i));
                                }
                            }
                        }

                        //if all the criteria for an outbreak are met, add the node to the list of outbreaks
                        if ((added.size() >= 4 && (time <=240))){
                            outbreaks.add(k);
                            String Stype = type.iterator().next();
                            type.clear();
                            added.clear();

                            //add 1 virus to each node connected to the outbreak source at the time
                            //of the infections that caused the outbreak
                            for (Vertex v : map.get(k)){
                                if (vertmap.get(v.getSrc()).getFirewall()){
                                    vertmap.get(v.getSrc()).addfirewall_virus(Stype, vertmap.get(k).getVirus().get(i).getDate());
                                    sort(v.getSrc(), 0, v.getFirewall_virus().size()-1);
                                }
                                else{
                                    vertmap.get(v.getSrc()).addVirus(Stype, vertmap.get(k).getVirus().get(i).getDate());
                                    sort(v.getSrc(), 0, v.getVirus().size()-1);
                                }
                            }
                        }
                    }
                }
            }
        }
        //when no more outbreaks have been found, call alerts, putOffline,
        // and sort the changed virus lists
        if (outbreaks.size() == size){
            Alerts();
            putOffline(outbreaks);
            for (String s : outbreaks){
                sort(s, 0, vertmap.get(s).getVirus().size()-1);
            }
            int count = 1;
            for (int i = 0; i < outbreaks.size(); i++){
                if ((i + 1) < outbreaks.size()){
                    if (!outbreaks.get(i+1).equals(outbreaks.get(i))){
                        System.out.println("Node " + outbreaks.get(i)+ " has " +count+ " outbreaks");
                        count = 1;
                    }
                    else{
                        count++;
                    }
                }
                else{
                    System.out.println("Node " + outbreaks.get(i)+ " has " +count+ " outbreaks");
                }

            }
            return outbreaks;
        }
        else{
            //recursively call outbreaks with the current list of outbreaks found
            return Outbreak(outbreaks);
        }

    }

    public void putOffline(ArrayList<String> toCheck){

        Set<String> type = new HashSet<>();
        ArrayList<Virus> added = new ArrayList<>();

        //check all nodes that have an outbreak
        for (String s : toCheck) {
            if(!vertmap.get(s).getActive()){
                continue;
            }
            long time = 0;
            int prev = 0;

            //if the node is infected with 6 or more virus'
            // check if fits critera for being put offline
            if (vertmap.get(s).getVirus().size() >= 6) {
                for (int i = 0; i < vertmap.get(s).getVirus().size(); i++){
                    //add the first virus information to the set, and list
                    if (i == 0){
                        type.add(vertmap.get(s).getVirus().get(i).getType());
                        added.add(vertmap.get(s).getVirus().get(i));
                    }
                    else{
                        //checking each set of dates if the time between them is less than 6 min
                        long tempTime = TimeBetween(vertmap.get(s).getVirus().get(i).getDate(),
                                vertmap.get(s).getVirus().get(prev).getDate());
                        if (tempTime < 360){
                            //when the time between the virus is less than 6 min add it to the total time
                            //and set the prev index to the current one, save the virus information
                            time += tempTime;
                            prev = i;
                            if(time <= 360){
                                type.add(vertmap.get(s).getVirus().get(i).getType());
                                added.add(vertmap.get(s).getVirus().get(i));
                            }
                        }
                    }
                }
                // when offline critera is satisfied the node is put offline
                //and is removed from all connections
                if (type.size() > 1 && added.size() >= 6){
                    vertmap.get(s).setActive(false);
                    removeConnections(s);
                }
            }
        }


    }

    /**
     * prints all the inactive nodes in the graph
     */
    public void inActive(){
        ArrayList<String> node = new ArrayList<>();
        System.out.println();
        //checking all nodes in the map adding all inactive ones to the list
        for (Map.Entry<String, Vertex> entry : vertmap.entrySet()){
            String k = entry.getKey();
            if (!vertmap.get(k).getActive()){
                //System.out.println("Node: " +k+ " is inactive");
                node.add(k);
            }
        }
        if (node.isEmpty()){
            System.out.println("There are no inactive nodes on this graph");
        }
        else{
            System.out.println("There are " +node.size()+ " inactive nodes: " +node);
        }
    }

    /**
     * Sorts the array of virus' into separate lists based on type
     * each of the 4 type based lists are sorted based on date using DateSort method.
     * The sorted lists are added to the final sorted list of virus
     * the Virus list within Vertex is then set equal to the sorted list
     * @param node String of the Vertex name
     * @param low int of the first index for sorting to be started at
     * @param high int of the last index to be sorted to
     */
    public void sort(String node, int low, int high){
        //for sorting the firewall virus'
        if (!vertmap.get(node).getFirewall_virus().isEmpty()){
            if (vertmap.get(node).getFirewall_virus() == null || vertmap.get(node).getFirewall_virus().size() == 0){
                return;
            }
            if (low >= high){
                return;
            }
            ArrayList<FirewallVirus> red = new ArrayList<>();
            ArrayList<FirewallVirus> blue = new ArrayList<>();
            ArrayList<FirewallVirus> black = new ArrayList<>();
            ArrayList<FirewallVirus> green = new ArrayList<>();

            ArrayList<FirewallVirus> sorted = new ArrayList<>();
            for (int i = 0; i < vertmap.get(node).getFirewall_virus().size(); i++){
                if (vertmap.get(node).getFirewall_virus().get(i).getType().equals("red")){
                    red.add(vertmap.get(node).getFirewall_virus().get(i));
                }
                else if (vertmap.get(node).getFirewall_virus().get(i).getType().equals("blue")){
                    blue.add(vertmap.get(node).getFirewall_virus().get(i));
                }
                else if (vertmap.get(node).getFirewall_virus().get(i).getType().equals("black")){
                    black.add(vertmap.get(node).getFirewall_virus().get(i));
                }
                else if (vertmap.get(node).getFirewall_virus().get(i).getType().equals("green")){
                    green.add(vertmap.get(node).getFirewall_virus().get(i));
                }
            }

            red = FDateSort(red, low, red.size() -1);
            blue = FDateSort(blue, low, blue.size() -1);
            black = FDateSort(black, low, black.size() -1);
            green = FDateSort(green, low, green.size() -1);

            ArrayList<ArrayList<FirewallVirus>> size = new ArrayList<>();
            if (!red.isEmpty()){
                size.add(red);

            }
            if (!blue.isEmpty()){
                size.add(blue);
            }
            if(!black.isEmpty()){
                size.add(black);
            }
            if(!green.isEmpty()){
                size.add(green);
            }

            for (int i = 0; i < size.size(); i++){
                int most = i;
                for (int j = i; j < size.size(); j++){
                    if (size.get(most).size() < size.get(j).size()){
                        most = j;
                    }
                }
                sorted.addAll(size.get(most));
            }
            vertmap.get(node).setFirewall_virus(sorted);
        }
        //For Sorting the Virus List
        else if (!vertmap.get(node).getVirus().isEmpty()){
            if (vertmap.get(node).getVirus() == null || vertmap.get(node).getVirus().size() == 0){
                return;
            }
            if (low >= high){
                return;
            }
            ArrayList<Virus> red = new ArrayList<>();
            ArrayList<Virus> blue = new ArrayList<>();
            ArrayList<Virus> black = new ArrayList<>();
            ArrayList<Virus> green = new ArrayList<>();

            ArrayList<Virus> sorted = new ArrayList<>();
            for (int i = 0; i < vertmap.get(node).getVirus().size() ; i++){
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

            ArrayList<ArrayList<Virus>> size = new ArrayList<>();
            if (!red.isEmpty()){
                size.add(red);

            }
            if (!blue.isEmpty()){
                size.add(blue);
            }
            if(!black.isEmpty()){
                size.add(black);
            }
            if(!green.isEmpty()){
                size.add(green);
            }

            while (!size.isEmpty()){
                int most = 0;
                for (int j = 0; j < size.size(); j++){
                    if (size.get(most).size() < size.get(j).size()){
                        most = j;
                    }
                }
                sorted.addAll(size.get(most));
                size.remove(most);
            }

            vertmap.get(node).setVirus(sorted);
        }

    }

    /**
     * Sorts the passed in ArrayList of Virus Objects in chronological order
     * @param type ArrayList<Virus> of sorted type virus'
     * @param low int lowest index to be sorted of the ArrayList
     * @param high int highest index to be sorted of the ArrayList
     * @return ArrayList<Virus> sorted in chronological order
     */
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

    /**
     * Sorts the passed in ArrayList of FirewallVirus objects in chronological order
     * @param type ArrayList<FirewallVirus> of sorted by type
     * @param low int lowest index to be sorted of the ArrayList
     * @param high int highest index to be sorted of the ArrayList
     * @return ArrayList<FirewallVirus> sorted in chronological order
     */
    public ArrayList<FirewallVirus> FDateSort(ArrayList<FirewallVirus> type, int low, int high){
        if (type.isEmpty() || type.size() < 2){
            return type;
        }
        int middle = low + (high - low) / 2;
        FirewallVirus pivot = type.get(middle);

        int i = low, j = high;

        while (type.get(i).getDate().compareTo(pivot.getDate()) < 0){
            i++;
        }
        while (type.get(j).getDate().compareTo(pivot.getDate()) > 0){
            j--;
        }
        if (i <= j){
            FirewallVirus temp = type.get(i);
            type.set(i, type.get(j));
            type.set(j, temp);
            i++;
            j--;
        }
        if (low < j){
            type = FDateSort(type, low, j);
        }
        if (high > i){
            type = FDateSort(type, i, high);
        }

        return type;
    }

    /**
     * Finds all alerts on each node, saving them to the alerts variable
     * in the corresponding node
     */
    public void Alerts() {


        for (Map.Entry<String, Vertex> entry : vertmap.entrySet()) {
            String k = entry.getKey();
            long time = 0;

            //When the node has been infected with 2 or more virus'
            //checks if 2 infections they are the same type and within 2 min
            if (vertmap.get(k).getVirus().size() >= 2) {
                for (int j = 0; j < vertmap.get(k).getVirus().size(); j++) {
                    if (j == 0) {
                        continue;
                    }
                    else {
                        time = TimeBetween(vertmap.get(k).getVirus().get(j).getDate(), vertmap.get(k).getVirus().get(j - 1).getDate());
                    }

                    String type1 = vertmap.get(k).getVirus().get(j).getType();
                    String type2 = vertmap.get(k).getVirus().get(j-1).getType();

                    if ((time <= 120) && (type1.equals(type2))) {
                        vertmap.get(k).addAlerts(1);
                    }
                }
            }
        }
    }

    /**
     * Method used to create and output the adjacency graph
     * this done in Graph.java for ease of access to the graph data
     */
    public int[][] CreateAdjacency(){
        Map<String, Integer> adjVal = new HashMap<>();
        int count = 0;

        //setting the matrix size using the number of nodes in the graph
        GraphAdjacencyMatrix matrix = new GraphAdjacencyMatrix(vertmap.size());

        //linking the node to its number order in the graph
        for (Map.Entry<String, ArrayList<Vertex>> entry : map.entrySet()){
            String k = entry.getKey();
            adjVal.putIfAbsent(k, count);
            System.out.println(count+ " = " +k);
            count++;
        }

        //using each node and its number order to add the edges to the matrix
        for (Map.Entry<String, ArrayList<Vertex>> entry : map.entrySet()){
            String k = entry.getKey();
            for (Vertex v : map.get(k)){
                if (vertmap.get(k).getActive()){
                    matrix.addEdge(adjVal.get(k), adjVal.get(v.getSrc()));
                }
            }
        }
        matrix.printMatrix();
        return matrix.getMatrix();
    }


}