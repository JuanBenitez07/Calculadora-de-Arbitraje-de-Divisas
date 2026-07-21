package model;

public class Arista {

    private int source;
    private int destination;
    private double weight;

    public Arista(int source, int destination, double weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public int getSource() {
        return source;
    }

    public int getDestination() {
        return destination;
    }

    public double getWeight() {
        return weight;
    }
}