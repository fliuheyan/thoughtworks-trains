package com.thoughtworks.trains.service;

import com.thoughtworks.trains.graph.Graph;

public interface TrainsGraphService {
	public Graph<String> generateGraph(String nodes, String edges);
}
