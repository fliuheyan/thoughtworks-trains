package com.thoughtworks.trains.filter;

import com.thoughtworks.trains.graph.Path;

public interface PathFilter<T> {

	public boolean passFilter(Path<T> path);
	
}
