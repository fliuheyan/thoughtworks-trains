package com.thoughtworks.trains.filter;

import com.thoughtworks.trains.graph.Edge;
import com.thoughtworks.trains.graph.Path;

public class NoRepeatedPathFilter<T> implements PathFilter<T>{

	public NoRepeatedPathFilter(){
		
	}
	
	public boolean passFilter(Path<T> path) {
		boolean result = false;
		Edge<T> edge = path.removeLastNode();
		if(!path.getEdgeList().contains(edge)){
			result = true;			
		}
		path.addEdge(edge);
		return result;
	}
}
