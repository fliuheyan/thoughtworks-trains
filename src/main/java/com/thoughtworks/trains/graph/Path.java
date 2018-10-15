package com.thoughtworks.trains.graph;

import java.util.LinkedList;
import java.util.List;

public class Path<H> implements Comparable<Path<H>>{
	private List<Edge<H>> edgeList;
	private H start;
	private H end;
    private int totalWright;
	
	private Path() {
		this.edgeList = new LinkedList<>();
		this.totalWright = 0;
	}

    public Path(H start) {
		this();
		this.start = start;
	}

	Path(H start, H end) {
		this(start);
		this.end = end;
	}
	
	private Path(Path<H> path){
		this.start = path.getStart();
		this.end = path.getEnd();
		this.edgeList = new LinkedList<>();
		this.edgeList.addAll(path.getEdgeList());
		this.totalWright = path.getTotalWeight();
	}

	private H getStart() {
		return start;
	}

    private H getEnd() {
		return end;
	}

    public void addEdge(Edge<H> edge) {
		this.edgeList.add(edge);
		totalWright += edge.getWeight();
	}

	public List<Edge<H>> getEdgeList() {
		return edgeList;
	}

    public Edge<H> removeLastNode() {
		Edge<H> edge = this.edgeList.remove(edgeList.size() - 1);
		this.totalWright -= edge.getWeight();
		return edge;
	}

	public H getCurrentNode() {
		return this.edgeList.get(edgeList.size() - 1).getTo();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Edge<H> edge : edgeList){
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

    public int compareTo(Path<H> otherPath) {
		int otherWeight = otherPath.getTotalWeight();
		return this.getTotalWeight() - otherWeight;
	}


}
