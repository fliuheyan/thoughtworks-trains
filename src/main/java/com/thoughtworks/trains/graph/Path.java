package com.thoughtworks.trains.graph;

import java.util.LinkedList;
import java.util.List;

public class Path<T> implements Comparable<Path<T>>{
	private List<Edge<T>> edgeList;
	private T start;
	private T end;
    private int totalWright;
	
	private Path() {
		this.edgeList = new LinkedList<>();
		this.totalWright = 0;
	}

    public Path(T start) {
		this();
		this.start = start;
	}

	Path(T start, T end) {
		this(start);
		this.end = end;
	}
	
	private Path(Path<T> path){
		this.start = path.getStart();
		this.end = path.getEnd();
		this.edgeList = new LinkedList<>();
		this.edgeList.addAll(path.getEdgeList());
		this.totalWright = path.getTotalWeight();
	}

	private T getStart() {
		return start;
	}

    private T getEnd() {
		return end;
	}

    public void addEdge(Edge<T> edge) {
		this.edgeList.add(edge);
		totalWright += edge.getWeight();
	}

	public List<Edge<T>> getEdgeList() {
		return edgeList;
	}

    public Edge<T> removeLastNode() {
		Edge<T> edge = this.edgeList.remove(edgeList.size() - 1);
		this.totalWright -= edge.getWeight();
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

	public int getTotalWeight() {
		return totalWright;
	}

    public int compareTo(Path<T> otherPath) {
		int otherWeight = otherPath.getTotalWeight();
		return this.getTotalWeight() - otherWeight;
	}


}
