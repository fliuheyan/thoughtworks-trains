package com.thoughtworks.trains.filter;

import com.thoughtworks.trains.graph.Path;

public class ExactlyStopsPathFilter<T> implements PathFilter<T> {
	private int exactlyStops;
	
	public ExactlyStopsPathFilter(){
		
	}
	
	public ExactlyStopsPathFilter(int exactlyStops){
		this.exactlyStops = exactlyStops;
	}
	
	public boolean passFilter(Path<T> path) {
		return path.getEdgeList().size() == exactlyStops;
	}

}
