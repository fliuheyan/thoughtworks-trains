package com.thoughtworks.trains;

import com.thoughtworks.trains.filter.MaxStopsPathFilter;
import com.thoughtworks.trains.filter.MaxWeightPatchFilter;
import com.thoughtworks.trains.filter.NoRepeatedPathFilter;
import com.thoughtworks.trains.filter.PathFilter;
import com.thoughtworks.trains.graph.Edge;
import com.thoughtworks.trains.graph.Graph;
import com.thoughtworks.trains.graph.Path;
import java.util.*;

public class TrainRouteGenerator {
    Graph<String> generateGraph(String edges) {
        Graph<String> graph = new Graph<>();
        graph.addAllEdges(Arrays.stream(edges.split(",")).collect(ArrayList::new,
            (list, edgeString) -> {
                String trimString = edgeString.trim();
                String from = String.valueOf(trimString.charAt(0));
                String to = String.valueOf(trimString.charAt(1));
                list.add(new Edge<>(from, to, trimString.charAt(2) - '0'));
            }, ArrayList::addAll));
        return graph;
    }

    <T> Path<T> getShortestPathFromPathList(Graph<T> graph, T start, T end) {
        List<Path<T>> pathList = generatePath(graph, start, end, new NoRepeatedPathFilter<>());
        return Collections.min(pathList);
    }

    <T> List<Path<T>> generatePath(Graph<T> graph, T start, T end,
                                   PathFilter<T> pathFilter) {
        return graph.getAllAdjacent(start).stream().collect(ArrayList::new, (list, edge) -> {
            Path<T> path = new Path<>(start);
            path.addEdge(edge);
            list.addAll(findPath(graph, path, end, pathFilter));
        }, ArrayList::addAll);
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
        TrainRouteGenerator generator = new TrainRouteGenerator();
        System.out.println("Enter here: ");
        Scanner scanner = new Scanner(System.in);
        String graphString = scanner.nextLine();
        Graph<String> graph = generator.generateGraph(graphString.replace("Graph:",""));
        PathFilter<String> maxStopsFilter4 = new MaxStopsPathFilter<>(4);
        PathFilter<String> maxWeightFilter = new MaxWeightPatchFilter<>(30);

        output(1, generator.getDistance(graph, "ABC"));
        output(2, generator.getDistance(graph, "AD"));
        output(3, generator.getDistance(graph, "ADC"));
        output(4, generator.getDistance(graph, "AEBCD"));
        output(5, generator.getDistance(graph, "AED"));
        output(6, generator.generatePath(graph, "C", "C",
            maxStopsFilter4).size());
        output(7, generator.generatePath(graph, "A", "C",
            maxStopsFilter4).size());
        output(8, generator.getShortestPathFromPathList(graph, "A", "C").getTotalWeight());
        output(9, generator.getShortestPathFromPathList(graph, "B", "B").getTotalWeight());
        output(10, generator.generatePath(graph, "C", "C", maxWeightFilter).size());
    }

    @SuppressWarnings("unchecked")
    String getDistance(Graph graph, String stops) {
        String trimStops = stops.trim();
        String from = trimStops.substring(0, 1);
        String to = trimStops.substring(trimStops.length() - 1, trimStops.length());
        String[] middleStops = {};
        if (stops.length() > 2)
            middleStops = trimStops.substring(1, trimStops.length() - 1).split("");
        return graph.getDistance(from, to, middleStops);
    }

    private static void output(int line, String content) {
        System.out.println("Output #" + line + ":" + " " + content);
    }

    private static void output(int line, int content) {
        output(line, String.valueOf(content));
    }
}
