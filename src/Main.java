import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Create c = new Create(2.0);
        Process p = new Process(1.0);
        Process p2 = new Process(1.0);
        Process p3 = new Process(1.0);
        Process p4 = new Process(1.0);

        c.setName("CREATOR");
        p.setName("PROCESS-1");
        p2.setName("PROCESS-2");
        p3.setName("PROCESS-3");
        p4.setName("PROCESS-4");

        p.setMaxqueue(5);
        p2.setMaxqueue(5);
        p3.setMaxqueue(5);
        p4.setMaxqueue(5);

        c.setDistribution("exp");
        p.setDistribution("exp");
        p2.setDistribution("exp");
        p3.setDistribution("exp");
        p4.setDistribution("exp");

        c.setNextElements(new ArrayList<>(List.of(p)));
        p.setNextElements(new ArrayList<>(List.of(p2)));
        p2.setNextElements(new ArrayList<>(List.of(p3)));

        ArrayList<Element> list = new ArrayList<>(List.of(c, p, p2, p3));
        Model model = new Model(list);
        model.simulate(1000);
    }
}
