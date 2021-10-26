import java.awt.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

//Class used to store and pull information regarding the individual
//vertex's in the graph. this information is all information that pertains
//to the private information about each node.
public class Vertex {
    private String src;
    private Point Location;
    private Date date;
    private Time time;
    private ArrayList<String> virus;
    private Boolean firewall;
    private ArrayList<String> firewall_virus;
    private Boolean active;

    Vertex(String label){
        this.src = label;
        this.active = true;
        this.virus = new ArrayList<String>();
        this.firewall_virus = new ArrayList<String>();
        this.firewall = false;
        this.Location = new Point();
    }

    //setter method for active
    public void setActive(Boolean active) {
        this.active = active;
    }

    //setter method for location
    public void setLocation(Point p){
        this.Location = p;
    }

    //setter method for virus array list
    public void setVirus(ArrayList<String> virus){
        this.virus = virus;
    }

    //method for adding a virus to the virus array list
    public void addVirus(String virus){
        this.virus.add(virus);
    }

    //method for adding a virus to the array list virus's stopped by the firewall
    //takes in param string of the virus type
    public void addfirewall_virus(String virus){
        this.firewall_virus.add(virus);
    }

    //getter for firewall virus array list
    public ArrayList<String> getFirewall_virus() {
        return firewall_virus;
    }

    //setter for firewall, taking in a boolean
    public void setFirewall(boolean firewall){
        this.firewall = firewall;
    }

    //getter for src string of the node
    public String getSrc(){
        return this.src;
    }

    //getter for location point
    public Point getLocation(){return this.Location;}

    //getter for bool of active
    public boolean getActive(){
        return this.active;
    }

    //getter for virus array list
    public ArrayList<String> getVirus() {
        return virus;
    }

    //getter for firewall status
    public Boolean getFirewall() {
        return firewall;
    }
}
