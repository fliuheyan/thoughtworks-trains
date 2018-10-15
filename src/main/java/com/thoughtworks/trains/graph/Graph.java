package com.thoughtworks.trains.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph<T> {
    private Map<T, List<Edge<T>>> nodeEdgeMap;

    public Graph() {
        nodeEdgeMap = new HashMap<>();
    }

    public List<Edge<T>> getAllAdjacent(T node) {
        return nodeEdgeMap.get(node);
    }

    public String getDistance(T start, T end, T[] passby) {
        return getDistanceFromPath(generatePath(start, end, passby));
    }

    public void addNode(T node) {
        if (!nodeEdgeMap.containsKey(node)) {
            nodeEdgeMap.put(node, new ArrayList<>());
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
            List<Edge<T>> newEdge = new ArrayList<>();
            newEdge.add(edge);
            nodeEdgeMap.put(edge.getFrom(), newEdge);
        }
    }

    private Path<T> generatePath(T start, T end, T[] passby) {
        Path<T> path = new Path<>(start, end);
        T current = start;
        for (T s : passby) {
            Edge<T> edge = new Edge<>(current, s);
            current = s;
            path.addEdge(edge);
        }
        path.addEdge(new Edge<>(current, end));
        return path;
    }

    private String getDistanceFromPath(Path<T> path) {
        List<Edge<T>> allEdgeList = getAllEdge();
        List<Edge<T>> edgeList = path.getEdgeList();
        if (!allEdgeList.containsAll(edgeList)) {
            return "";
        }
        int distance = 0;
        for (Edge<?> edge : edgeList) {
            distance += edge.getWeight();
        }
        return String.valueOf(distance);
    }

    private List<Edge<T>> getAllEdge() {
        List<Edge<T>> allEdgeList = new ArrayList<>();
        Collection<List<Edge<T>>> edgeListCollection = nodeEdgeMap.values();
        for (List<Edge<T>> list : edgeListCollection) {
            allEdgeList.addAll(list);
        }
        return allEdgeList;
    }
}
