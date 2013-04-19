package com.thoughtworks.trains.serviceImpl;

import com.thoughtworks.trains.graph.Edge;
import com.thoughtworks.trains.graph.Graph;
import com.thoughtworks.trains.service.TrainsGraphService;

public class TrainsGraphServiceImpl implements TrainsGraphService {

	public Graph<String> generateGraph(String nodes, String edges) {
		Graph<String> graph = new Graph<String>();
		String[] nodeArray = nodes.split("\\,");
		for (String s : nodeArray) {
			graph.addNode(s);
		}
		String[] strs = edges.split("\\,");
		for (String s : strs) {
			Edge<String> edge = new Edge<String>(String.valueOf(s.charAt(0)),
					String.valueOf(s.charAt(1)), s.charAt(2) - '0');
			graph.addEdge(edge);
		}
		return graph;
	}
}
