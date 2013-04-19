package com.thoughtworks.trains.graph;

public class Edge<T> {
	private T from;
	private T to;
	private int weight;

	public Edge() {

	}
	
	public Edge(T from,T to){
		this.from = from;
		this.to = to;
	}

	public Edge(T from, T to, int weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
	}

	public T getFrom() {
		return from;
	}

	public void setFrom(T from) {
		this.from = from;
	}

	public T getTo() {
		return to;
	}

	public void setTo(T to) {
		this.to = to;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	@Override
	public boolean equals(Object obj){
		Edge<?> edge = (Edge<?>)obj;
		return (this.from.equals(edge.getFrom()))&&(this.to.equals(edge.getTo()));
	}
	
	@Override
	public int hashCode(){
		return 0;
	}
	
	@Override
	public String toString(){
		return "(" + from.toString() + "," + to.toString() + ")";
	}
}
