package com.thoughtworks.trains.graph;

import java.util.LinkedList;
import java.util.List;

public class Path<T> implements Comparable<Path<T>>{
	private List<Edge<T>> edgeList;
	private T start;
	private T end;
	private List<T> passyByList;
	private int totalweight;
	
	public Path() {
		this.edgeList = new LinkedList<Edge<T>>();
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
		this.edgeList = new LinkedList<Edge<T>>();
		this.edgeList.addAll(path.getEdgeList());
		this.totalweight = path.getTotalweight();
	}

	public T getStart() {
		return start;
	}

	public void setStart(T start) {
		this.start = start;
	}

	public T getEnd() {
		return end;
	}

	public void setEnd(T end) {
		this.end = end;
	}

	public List<T> getPassyByList() {
		return passyByList;
	}

	public void setPassyByList(List<T> passyByList) {
		this.passyByList = passyByList;
	}

	public List<Edge<?>> convertToEdgeList() {

		return null;

	}

	public void addEdge(Edge<T> edge) {
		this.edgeList.add(edge);
		totalweight += edge.getWeight();
	}

	public List<Edge<T>> getEdgeList() {
		return edgeList;
	}

	public void setEdgeList(List<Edge<T>> edgeList) {
		this.edgeList = edgeList;
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
		return new Path<T>(path);
	}

	public int getTotalweight() {
		return totalweight;
	}

	public void setTotalweight(int totalweight) {
		this.totalweight = totalweight;
	}

	public int compareTo(Path<T> otherPath) {
		int otherWeight = otherPath.getTotalweight();
		return this.getTotalweight() - otherWeight;
	}
	
	// @Override
	// public boolean equals(Object obj){
	// Path<?> path = (Path<?>)obj;
	// return
	// (this.start.equals(path.getStart()))&&(this.end.equals(path.getEnd()));
	// }
	//
	// @Override
	// public int hashCode(){
	// return 0;
	// }
	//
}
