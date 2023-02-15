package personal.vahid.schedulerengine.model;

public interface Job<T extends Comparable>{

    T getEnd();
    T getStart();
    long getProfit();

}
