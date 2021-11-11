import java.sql.Time;
import java.util.Date;

/**
 * Class utilized to store a virus type as well as the date/time
 * it was injected to a node
 */
public class Virus {
    private String type;
    private Date date;

    /**
     * Default constructor to setup the type of virus
     * and time the virus infected the node
     * @param type String virus type injected into a node
     * @param date Date object of the injection date/time
     */
    Virus(String type, Date date){
        this.type = type;
        this.date = date;
    }

    /**
     * @return String type of virus injected into a node
     */
    public String getType() {
        return type;
    }

    /**
     * @return Date Object of the date/time virus got injected
     */
    public Date getDate() {
        return date;
    }

}
