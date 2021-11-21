import java.awt.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

/**
 * Vertex class is used to store and pull all information regarding each individual
 * Vertex in the graph. This information is private and is accessed through the use
 * of getter and setter functions within this class
 */
public class Vertex {
    private String src;
    private Point Location;
    private ArrayList<Virus> virus;
    private Boolean firewall;
    private ArrayList<FirewallVirus> firewall_virus;
    private Boolean active;

    /**
     * Default constructor for setting up each node on the graph
     * @param label String of node name
     */
    Vertex(String label){
        this.src = label;
        this.active = true;
        this.virus = new ArrayList<Virus>();
        this.firewall_virus = new ArrayList<FirewallVirus>();
        this.firewall = false;
        this.Location = new Point();
    }

    /**
     * Setter for private var stating if the node is active
     * @param active boolean if the node is active
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * Setter for the private variable location used in the gui
     * @param p POINT obj stating location of node on the map
     */
    public void setLocation(Point p){
        this.Location = p;
    }

    /**
     * Setter for the virus list that have not been stopped by a firewall
     * @param virus ArrayList<String> virus' infecting the node
     */
    public void setVirus(ArrayList<Virus> virus){
        this.virus = virus;
    }

    /**
     * Method for adding a virus to the virus to the list of infections on the node
     * @param type String type of virus to be added
     * @param date Date object of the date and time of the virus
     */
    public void addVirus(String type, Date date){
        this.virus.add(new Virus(type, date));
    }

    /**
     * Method for adding a virus to the arrayList of virus' stopped
     * by the firewall
     * @param type String of type of virus
     * @param date Date object for the virus
     */
    public void addfirewall_virus(String type, Date date){
        this.firewall_virus.add(new FirewallVirus(type, date));
    }

    /**
     * Getter for the list of virus' stopped by the firewall
     * @return ArrayList<String>
     */
    public ArrayList<FirewallVirus> getFirewall_virus() {
        return firewall_virus;
    }

    /**
     * Sets if the firewall is active or not
     * @param firewall boolean
     */
    public void setFirewall(boolean firewall){
        this.firewall = firewall;
    }

    /**
     * @return String of node name
     */
    public String getSrc(){
        return this.src;
    }

    /**
     * @return POINT location of the node on the map
     */
    public Point getLocation(){return this.Location;}

    /**
     * @return boolean of if the node is active of not
     */
    public boolean getActive(){
        return this.active;
    }

    /**
     * @return ArrayList<String>
     */
    public ArrayList<Virus> getVirus() {
        return virus;
    }

    /**
     * @return boolean firewall status
     */
    public Boolean getFirewall() {
        return firewall;
    }

}
