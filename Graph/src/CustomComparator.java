import java.util.Comparator;

public class CustomComparator implements Comparator<Virus> {

    @Override
    public int compare(Virus o1, Virus o2) {
        return o1.getDate().compareTo(o2.getDate());
    }
}
