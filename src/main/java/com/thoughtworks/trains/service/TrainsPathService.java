package com.thoughtworks.trains.service;

import java.util.List;

import com.thoughtworks.trains.filter.PathFilter;
import com.thoughtworks.trains.graph.Graph;
import com.thoughtworks.trains.graph.Path;

public interface TrainsPathService {
	public <T> List<Path<T>> generatePath(Graph<T> graph, T start, T end,
			PathFilter<T> pathFilter);

	public <T> Path<T> getShortestPathFromPathList(Graph<T> graph, T start, T end);
}
