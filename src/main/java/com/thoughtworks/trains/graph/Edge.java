package com.thoughtworks.trains.graph;

public class Edge<T> {
    private T from;
    private T to;
    private int weight;

    public Edge() {

    }

    Edge(T from, T to) {
        this.from = from;
        this.to = to;
    }

    public Edge(T from, T to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    T getFrom() {
        return from;
    }

    T getTo() {
        return to;
    }

    int getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Edge &&
            this.from.equals(((Edge<?>) obj).getFrom())) &&
            (this.to.equals(((Edge<?>) obj).getTo()));
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "(" + from.toString() + "," + to.toString() + ")" + weight;
    }
}
