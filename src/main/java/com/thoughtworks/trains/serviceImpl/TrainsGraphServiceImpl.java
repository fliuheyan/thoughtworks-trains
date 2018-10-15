package com.thoughtworks.trains.serviceImpl;

import com.thoughtworks.trains.graph.Edge;
import com.thoughtworks.trains.graph.Graph;
import com.thoughtworks.trains.service.TrainsGraphService;

public class TrainsGraphServiceImpl implements TrainsGraphService {

	public Graph<String> generateGraph(String nodes, String edges) {
		Graph<String> graph = new Graph<>();
		String[] nodeArray = nodes.split(",");
		for (String s : nodeArray) {
			graph.addNode(s);
		}
		String[] edgeStrings = edges.split(",");
		for (String edgeString : edgeStrings) {
			Edge<String> edge = new Edge<>(String.valueOf(edgeString.charAt(0)),
				String.valueOf(edgeString.charAt(1)), edgeString.charAt(2) - '0');
			graph.addEdge(edge);
		}
		return graph;
	}
}
