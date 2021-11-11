import java.sql.Time;
import java.util.Date;

/**
 * Class utilized to store the type of a virus
 * and the date/time it was injected into a node
 */
public class FirewallVirus {
    private String type;
    private Date date;

    /**
     * Default constructor to set the type of virus and date the firewall stopped it at
     * @param type String type of virus stopped by the firewall
     * @param date Date object storing the date and time of stopped virus
     */
    FirewallVirus(String type, Date date){
        this.type = type;
        this.date = date;
    }

    /**
     * @return String type of the stopped virus
     */
    public String getType() {
        return type;
    }

    /**
     * @return Date object the type of virus got stopped at
     */
    public Date getDate() {
        return date;
    }

}
