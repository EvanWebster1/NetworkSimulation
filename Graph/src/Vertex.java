import java.awt.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class Vertex {
    private String src;
    private Point Location;
    private Date date;
    private Time time;
    private String virus;
    private Boolean firewall;

    Vertex(String label){
        this.src = label;
    }

    public void setLocation(Point p){
        this.Location = p;
    }

    public String getSrc(){
        return this.src;
    }

    public Point getLocation(){return this.Location;}
}
