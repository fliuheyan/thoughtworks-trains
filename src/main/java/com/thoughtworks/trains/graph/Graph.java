package com.thoughtworks.trains.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph<T> {
	private List<T> visitedNodeList;
	private Map<T, List<Edge<T>>> nodeEdgeMap;
	
	public Graph(){
		nodeEdgeMap = new HashMap<T,List<Edge<T>>>();
	}

	public int getDistance(T start, T end, T[] passby) {
		Path<T> path = generatePath(start, end, passby);
		return getDistanceFromPath(path);
	}

	public Path<T> generatePath(T start, T end, T[] passby) {
		Path<T> path = new Path<T>(start, end);
		T current = start;
		for (T s : passby) {
			Edge<T> edge = new Edge<T>(current, s);
			current = s;
			path.addEdge(edge);
		}
		path.addEdge(new Edge<T>(current,end));
		return path;
	}
	
	public Path<T> searchPath(T start,T end){
		Path<T> path = new Path<T>(start, end);
		
		return path;
	}

	public int getDistanceFromPath(Path<T> path) {
		List<Edge<T>> allEdgeList = getAllEdge();
		List<Edge<T>> edgeList = path.getEdgeList();
		if (!allEdgeList.containsAll(edgeList)) {
			return -1;
		}
		int distance = 0;
		for (Edge<?> edge : edgeList) {
			int index = allEdgeList.indexOf(edge);
			distance += allEdgeList.get(index).getWeight();
		}
		return distance;
	}

	public void addNode(T node) {
		if (!nodeEdgeMap.containsKey(node)) {
			nodeEdgeMap.put(node, new ArrayList<Edge<T>>());
		}
	}

	public void addEdge(Edge<T> edge) {
		T from = edge.getFrom();
		if (nodeEdgeMap.containsKey(from)) {
			if (nodeEdgeMap.get(from).contains(edge)) {
				nodeEdgeMap.get(from).remove(edge);
			}
			nodeEdgeMap.get(from).add(edge);
		} else {
			List<Edge<T>> newEdge = new ArrayList<Edge<T>>();
			newEdge.add(edge);
			nodeEdgeMap.put(edge.getFrom(), newEdge);
		}
	}

	public Map<T, List<Edge<T>>> getNodeEdgeMap() {
		return nodeEdgeMap;
	}

	public void setNodeEdgeMap(HashMap<T, List<Edge<T>>> nodeEdgeMap) {
		this.nodeEdgeMap = nodeEdgeMap;
	}

	public List<Edge<T>> getAllEdge() {
		List<Edge<T>> allEdgeList = new ArrayList<Edge<T>>();
		Collection<List<Edge<T>>> edgeListCollection = nodeEdgeMap.values();
		for (List<Edge<T>> list : edgeListCollection) {
			for (Edge<T> edge : list) {
				allEdgeList.add(edge);
			}
		}
		return allEdgeList;
	}

	public List<T> getVisitedNodeList() {
		return visitedNodeList;
	}

	public void setVisitedNodeListList(List<T> visitedNodeList) {
		this.visitedNodeList = visitedNodeList;
	}
	
	public boolean isVisited(T node){
		return visitedNodeList.contains(node);
	}
	
	public void clearAllVisited(){
		visitedNodeList.clear();
	}
	
	public void addVisitedNode(T node){
		visitedNodeList.add(node);
	}
	
	public List<Edge<T>> getAllAdjacent(T node){
		return nodeEdgeMap.get(node);
	}
}
