package com.thoughtworks.trains.filter;

import com.thoughtworks.trains.graph.Path;

public class MaxWeightPatchFilter<T> implements PathFilter<T> {
	private int maxWeight;

	public MaxWeightPatchFilter() {

	}

	public MaxWeightPatchFilter(int maxWeight) {
		this.maxWeight = maxWeight;
	}

	public boolean passFilter(Path<T> path) {
		return path.getTotalWeight() < maxWeight;
	}
}
