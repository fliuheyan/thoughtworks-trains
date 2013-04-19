package com.thoughtworks.trains.filter;

import com.thoughtworks.trains.graph.Path;

public class MaxStopsPathFilter<T> implements PathFilter<T> {
	private int maxStops;

	public MaxStopsPathFilter() {

	}

	public MaxStopsPathFilter(int maxStops) {
		this.maxStops = maxStops;
	}

	public boolean passFilter(Path<T> path) {
		return path.getEdgeList().size() < getMaxStops();
	}

	public int getMaxStops() {
		return maxStops;
	}

	public void setMaxStops(int maxStops) {
		this.maxStops = maxStops;
	}

}
