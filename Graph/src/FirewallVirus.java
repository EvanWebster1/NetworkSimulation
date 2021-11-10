import java.sql.Time;
import java.util.Date;

public class FirewallVirus {
    private String type;
    private Date date;


    FirewallVirus(String type, Date date){
        this.type = type;
        this.date = date;
        //this.time = time;
    }

    public String getType() {
        return type;
    }

    public Date getDate() {
        return date;
    }

}
