import java.util.ArrayList;

public class Element {
    private String name;
    private double tnext;
    private double delayMean, delayDev;
    private double totalTime;
    private String distribution;
    private int quantity;
    private double tcurr;
    private int state;
    private ArrayList<Element> nextElements;
    private static int nextId = 0;
    private int id;


    public Element() {
        tnext = Double.MAX_VALUE;
        delayMean = 1.0;
        distribution = "exp";
        tcurr = tnext;
        state = 0;
        nextElements = null;
        id = nextId;
        nextId++;
        name = "element" + id;
    }

    public Element(double delay) {
        tnext = 0.0;
        delayMean = delay;
        distribution = "";
        tcurr = tnext;
        state = 0;
        nextElements = null;
        id = nextId;
        nextId++;
        name = "element" + id;
    }

    public double getDelay() {
        double delay = getDelayMean();
        if ("exp".equalsIgnoreCase(getDistribution())) {
            delay = FunRand.Exp(getDelayMean());
        } else if ("norm".equalsIgnoreCase(getDistribution())) {
            delay = FunRand.Norm(getDelayMean(), getDelayDev());
        } else if ("unif".equalsIgnoreCase(getDistribution())) {
            delay = FunRand.Unif(getDelayMean(), getDelayDev());
        }
        return delay;
    }


    public double getDelayDev() {
        return delayDev;
    }

    public void setDelayDev(double delayDev) {
        this.delayDev = delayDev;
    }

    public String getDistribution() {
        return distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTcurr() {
        return tcurr;
    }

    public void setTcurr(double tcurr) {
        this.tcurr = tcurr;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Element getNextElements() {
        if (nextElements == null) {
            return null;
        }
        if (nextElements.size() == 1) {
            return nextElements.get(0);
        }

        int numOfElements = nextElements.size();
        double rand = Math.random();
        double step = (double) 1 / numOfElements;


        double[][] distribution = new double[numOfElements][2];
        for (int i = 0; i < numOfElements; i++) {
            distribution[i][0] = i;
            distribution[i][1] = (i + 1) * step;
        }

        for (int i = 0; i < distribution.length; i++) {
            int variable = (int) distribution[i][0];
            double interval = distribution[i][1];
            if (rand <= interval) {
                return nextElements.get(variable);
            }
        }

        return null;
    }

    public void setNextElements(ArrayList<Element> nextElements) {
        this.nextElements = nextElements;
    }

    public void inAct() {
    }

    public void outAct() {
        quantity++;
    }

    public double getTnext() {
        return tnext;
    }

    public void setTnext(double tnext) {
        this.tnext = tnext;
    }

    public double getDelayMean() {
        return delayMean;
    }

    public void setDelayMean(double delayMean) {
        this.delayMean = delayMean;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void printResult() {
        System.out.println(getName() + " quantity = " + quantity);
    }

    public void printInfo() {
        System.out.println(getName() + " state = " + state + " quantity = " + quantity + " tnext = " + tnext);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void doStatistics(double delta) {

    }

    public double getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(double totalTime) {
        this.totalTime = totalTime;
    }
}
