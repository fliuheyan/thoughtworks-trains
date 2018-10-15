package com.thoughtworks.trains.graph;

import java.util.LinkedList;
import java.util.List;

public class Path<T> implements Comparable<Path<T>>{
	private List<Edge<T>> edgeList;
	private T start;
	private T end;
    private int totalweight;
	
	public Path() {
		this.edgeList = new LinkedList<>();
		this.totalweight = 0;
	}

	public Path(T start) {
		this();
		this.start = start;
	}

	public Path(T start, T end) {
		this(start);
		this.end = end;
	}
	
	public Path(Path<T> path){
		this.start = path.getStart();
		this.end = path.getEnd();
		this.edgeList = new LinkedList<>();
		this.edgeList.addAll(path.getEdgeList());
		this.totalweight = path.getTotalweight();
	}

	private T getStart() {
		return start;
	}

    private T getEnd() {
		return end;
	}

    public void addEdge(Edge<T> edge) {
		this.edgeList.add(edge);
		totalweight += edge.getWeight();
	}

	public List<Edge<T>> getEdgeList() {
		return edgeList;
	}

    public Edge<T> removeLastNode() {
		Edge<T> edge = this.edgeList.remove(edgeList.size() - 1);
		this.totalweight -= edge.getWeight();
		return edge;
	}

	public T getCurrentNode() {
		return this.edgeList.get(edgeList.size() - 1).getTo();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Edge<T> edge : edgeList){
			sb.append(edge.toString());
		}
		return sb.toString();
	}

	public static <T> Path<T> deepCopy(Path<T> path){
		return new Path<>(path);
	}

	public int getTotalweight() {
		return totalweight;
	}

    public int compareTo(Path<T> otherPath) {
		int otherWeight = otherPath.getTotalweight();
		return this.getTotalweight() - otherWeight;
	}
}
