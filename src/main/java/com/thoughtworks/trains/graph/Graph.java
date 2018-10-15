package com.thoughtworks.trains.graph;

import java.util.*;

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

    @SuppressWarnings("unchecked")
    public void addAllEdges(List<Edge<T>> edgeList) {
        for(Edge edge: edgeList) {
            addEdge(edge);
        }
    }

    private void addEdge(Edge<T> edge) {
        T from = edge.getFrom();
        if (nodeEdgeMap.containsKey(from)) {
            if (nodeEdgeMap.get(from).contains(edge)) {
                nodeEdgeMap.get(from).remove(edge);
            }
            nodeEdgeMap.get(from).add(edge);
        } else {
            List<Edge<T>> edgeList = new ArrayList<>();
            edgeList.add(edge);
            nodeEdgeMap.put(edge.getFrom(), edgeList);
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
            return "NO SUCH ROUTE";
        }
        return String.valueOf(edgeList.stream()
            .map(edge -> allEdgeList.get(allEdgeList.lastIndexOf(edge)).getWeight()).mapToInt(i -> i).sum());
    }

    private List<Edge<T>> getAllEdge() {
        return nodeEdgeMap.values().stream().reduce(new ArrayList<>(), (result, edgeList) -> {
            result.addAll(edgeList);
            return result;
        });
    }
}
