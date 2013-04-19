package com.thoughtworks.trains.serviceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.thoughtworks.trains.filter.NoRepeatedPathFilter;
import com.thoughtworks.trains.filter.PathFilter;
import com.thoughtworks.trains.graph.Edge;
import com.thoughtworks.trains.graph.Graph;
import com.thoughtworks.trains.graph.Path;
import com.thoughtworks.trains.service.TrainsPathService;

public class TrainsPathServiceImpl implements TrainsPathService {
	public <T> List<Path<T>> generatePath(Graph<T> graph, T start, T end,
			PathFilter<T> pathFilter) {
		List<Path<T>> pathList = new ArrayList<Path<T>>();
		List<Edge<T>> adjacentList = graph.getAllAdjacent(start);
		for (Edge<T> edge : adjacentList) {
			Path<T> path = new Path<T>(start);
			path.addEdge(edge);
			pathList.addAll(generatePathFromPath(graph, path, end, pathFilter));
		}
		return pathList;
	}
	
	private <T> List<Path<T>> generatePathFromPath(Graph<T> graph, Path<T> path,
			T end, PathFilter<T> pathFilter) {
		List<Path<T>> pathList = new ArrayList<Path<T>>();
		if (pathFilter.passFilter(path)) {
			T pathCurrent = path.getCurrentNode();
			if (pathCurrent.equals(end)) {
				pathList.add(Path.deepCopy(path));
			}
			for (Edge<T> edge : graph.getAllAdjacent(pathCurrent)) {
				path.addEdge(edge);
				pathList.addAll(generatePathFromPath(graph, path, end,
						pathFilter));
			}
		}
		path.removeLastNode();
		return pathList;
	}
	
	public <T> Path<T> getShortestPathFromPathList(Graph<T> graph, T start, T end) {
		List<Path<T>> pathList = generatePath(graph,start,end,new NoRepeatedPathFilter<T>());
		return Collections.min(pathList);
	}



}
