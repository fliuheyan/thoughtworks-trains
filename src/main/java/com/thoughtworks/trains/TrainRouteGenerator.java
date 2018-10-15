package com.thoughtworks.trains;

import com.thoughtworks.trains.filter.NoRepeatedPathFilter;
import com.thoughtworks.trains.filter.PathFilter;
import com.thoughtworks.trains.graph.Edge;
import com.thoughtworks.trains.graph.Graph;
import com.thoughtworks.trains.graph.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TrainRouteGenerator {
    Graph<String> generateGraph(String edges) {
        Graph<String> graph = new Graph<>();
        String[] edgeStrings = edges.split(",");
        for (String edgeString : edgeStrings) {
            String from = String.valueOf(edgeString.charAt(0));
            String to = String.valueOf(edgeString.charAt(1));
            graph.addNode(from);
            graph.addNode(to);
            Edge<String> edge = new Edge<>(from, to, edgeString.charAt(2) - '0');
            graph.addEdge(edge);
        }
        return graph;
    }

    <T> Path<T> getShortestPathFromPathList(Graph<T> graph, T start, T end) {
        List<Path<T>> pathList = generatePath(graph, start, end, new NoRepeatedPathFilter<>());
        return Collections.min(pathList);
    }

    <T> List<Path<T>> generatePath(Graph<T> graph, T start, T end,
                                   PathFilter<T> pathFilter) {
        List<Path<T>> pathList = new ArrayList<>();
        List<Edge<T>> adjacentList = graph.getAllAdjacent(start);
        for (Edge<T> edge : adjacentList) {
            Path<T> path = new Path<>(start);
            path.addEdge(edge);
            pathList.addAll(findPath(graph, path, end, pathFilter));
        }
        return pathList;
    }

    private <T> List<Path<T>> findPath(Graph<T> graph, Path<T> path,
                                       T end, PathFilter<T> pathFilter) {
        List<Path<T>> pathList = new ArrayList<>();
        if (pathFilter.passFilter(path)) {
            T pathCurrent = path.getCurrentNode();
            if (pathCurrent.equals(end)) {
                pathList.add(Path.deepCopy(path));
            }
            for (Edge<T> edge : graph.getAllAdjacent(pathCurrent)) {
                path.addEdge(edge);
                pathList.addAll(findPath(graph, path, end,
                    pathFilter));
            }
        }
        path.removeLastNode();
        return pathList;
    }

    public static void main(String[] args) {

    }
}
