import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Process extends Element {
    private int queue, maxqueue, failure;
    private double meanQueue;
    private final List<Integer> channelsStates = new ArrayList<>();
    private final List<Double> channelsTnext = new ArrayList<>();

    public Process(double delay) {
        super(delay);
        setTnext(Double.MAX_VALUE);
        queue = 0;
        maxqueue = Integer.MAX_VALUE;
        meanQueue = 0.0;
    }

    public Process(double delay, int channels) {
        super(delay);
        super.setTnext(Double.MAX_VALUE);
        queue = 0;
        maxqueue = Integer.MAX_VALUE;
        meanQueue = 0.0;
        for (int i = 0; i < channels; i++) {
            channelsStates.add(0);
            channelsTnext.add(Double.MAX_VALUE);
        }
    }

    @Override
    public void inAct() {

        if (channelsStates.size() != 0) {
            int currentChannel = channelsStates.indexOf(0);
            if (currentChannel != -1) {
                channelsStates.set(currentChannel, 1);
                channelsTnext.set(currentChannel, super.getTcurr() + super.getDelay());
                super.setTnext(Collections.min(channelsTnext));
            } else {
                if (getQueue() < getMaxqueue()) {
                    setQueue(getQueue() + 1);
                } else {
                    failure++;
                }
            }
        } else if (super.getState() == 0) {
            super.setState(1);
            super.setTnext(super.getTcurr() + super.getDelay());

        } else {
            if (getQueue() < getMaxqueue()) {
                setQueue(getQueue() + 1);
            } else {
                failure++;
            }
        }
    }

    @Override
    public void outAct() {
        super.outAct();

        if (channelsStates.size() != 0) {
            int currentChannel = channelsTnext.indexOf(super.getTcurr());
            channelsStates.set(currentChannel, 0);
            channelsTnext.set(currentChannel, Double.MAX_VALUE);
            super.setTnext(Collections.min(channelsTnext));
            super.setTotalTime(super.getTotalTime() + super.getDelay());
            if (getQueue() > 0) {
                setQueue(getQueue() - 1);
                channelsStates.set(currentChannel, 1);
                channelsTnext.set(currentChannel, super.getTcurr() + super.getDelay());
                super.setTnext(Collections.min(channelsTnext));
            }
        } else {
            super.setTnext(Double.MAX_VALUE);
            super.setState(0);

            super.setTotalTime(super.getTotalTime() + super.getDelay());
            if (getQueue() > 0) {
                setQueue(getQueue() - 1);
                super.setState(1);
                super.setTnext(super.getTcurr() + super.getDelay());
            }
        }

        if (getNextElements() != null) {
            getNextElements().inAct();
        }
    }

    public int getFailure() {
        return failure;
    }

    public int getQueue() {
        return queue;
    }

    public void setQueue(int queue) {
        this.queue = queue;
    }

    public int getMaxqueue() {
        return maxqueue;
    }

    public void setMaxqueue(int maxqueue) {
        this.maxqueue = maxqueue;
    }

    @Override
    public void printInfo() {
        super.printInfo();
        System.out.println("failure = " + this.getFailure());
    }

    @Override
    public void doStatistics(double delta) {
        meanQueue = getMeanQueue() + queue * delta;
    }

    public double getMeanQueue() {
        return meanQueue;
    }
}
