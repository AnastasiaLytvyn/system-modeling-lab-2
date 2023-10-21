import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Create c = new Create(2.0);
        Process p = new Process(1.0);
        Process p2 = new Process(1.0);
        Process p3 = new Process(1.0);

        c.setName("CREATOR");
        p.setName("PROCESS-1");
        p2.setName("PROCESS-2");
        p3.setName("PROCESS-3");

        p.setMaxqueue(5);
        p2.setMaxqueue(5);
        p3.setMaxqueue(5);

        c.setDistribution("exp");
        p.setDistribution("exp");
        p2.setDistribution("exp");
        p3.setDistribution("exp");

        c.setNextElement(p);
        p.setNextElement(p2);
        p2.setNextElement(p3);

        ArrayList<Element> list = new ArrayList<>();
        list.add(c);
        list.add(p);
        list.add(p2);
        list.add(p3);

        Model model = new Model(list);
        model.simulate(1000);
    }
}
