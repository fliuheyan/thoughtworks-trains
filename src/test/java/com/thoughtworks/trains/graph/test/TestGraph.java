package com.thoughtworks.trains.graph.test;

import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import com.thoughtworks.trains.filter.MaxStopsPathFilter;
import com.thoughtworks.trains.filter.MaxWeightPatchFilter;
import com.thoughtworks.trains.filter.NoRepeatedPathFilter;
import com.thoughtworks.trains.filter.PathFilter;
import com.thoughtworks.trains.graph.Edge;
import com.thoughtworks.trains.graph.Graph;
import com.thoughtworks.trains.graph.Path;
import com.thoughtworks.trains.service.TrainsGraphService;
import com.thoughtworks.trains.service.TrainsPathService;
import com.thoughtworks.trains.serviceImpl.TrainsGraphServiceImpl;
import com.thoughtworks.trains.serviceImpl.TrainsPathServiceImpl;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class TestGraph {
	private Graph<String> graph;
	private TrainsGraphService tgs;
	private TrainsPathService tps;
	private String graph1Nodes = "A,B,C,D,E";
	private String graph1Edges = "AB5,BC4,CD8,DC8,DE6,AD5,CE2,EB3,AE7";
	private PathFilter<String> maxStopsFilter4;
	PathFilter<String> maxWeightFilter;
	PathFilter<String> noRepeatedFilter;

	@Before
	public void init() {
		tgs = new TrainsGraphServiceImpl();
		tps = new TrainsPathServiceImpl();
		graph = tgs.generateGraph(graph1Nodes, graph1Edges);
		maxStopsFilter4 = new MaxStopsPathFilter<String>(4);	
		maxWeightFilter = new MaxWeightPatchFilter<String>(30);
		noRepeatedFilter = new NoRepeatedPathFilter<String>();
	}

	@Test
	public void testA_B_C_step1() {
		String[] strs = { "B" };
		int a = graph.getDistance("A", "C", strs);
		System.out.println(a);
		assertThat(a,is(9));
	}

	@Test
	public void testA_D_step2() {
		String[] strs = {};
		int a = graph.getDistance("A", "D", strs);
		System.out.println(a);
		assertThat(a,is(5));
	}

	@Test
	public void testA_D_C_step3() {
		String[] strs = { "D" };
		int a = graph.getDistance("A", "C", strs);
		System.out.println(a);
		assertThat(a,is(13));
	}

	@Test
	public void testA_E_B_C_D_step4() {
		String[] strs = { "E", "B", "C" };
		int a = graph.getDistance("A", "D", strs);
		System.out.println(a);
		assertThat(a,is(22));
	}

	@Test
	public void testA_E_D_step5() {
		String[] strs = { "E" };
		int a = graph.getDistance("A", "C", strs);
		System.out.println(a);
		assertThat(a,is(-1));
	}

	@Test
	public void testC_C_with_maximum3stops_step_6() {
		String from = "C";
		String to = "C";
		List<Path<String>> list = tps.generatePath(graph, from, to,
				maxStopsFilter4);
		printPath(list);
		assertThat(list.size(), is(2));
	}

	@Test
	public void testA_C_withexactly4stops_step7() {
		String from = "A";
		String to = "C";
		List<Path<String>> list = tps.generatePath(graph, from, to,
				maxStopsFilter4);
		assertThat(list.size(), is(3));
	}

	@Test
	public void testA_C_shortestPath_step8() {
		String from = "A";
		String to = "C";
		Path<String> path = tps.getShortestPathFromPathList(graph, from, to);
		assertThat(path.getTotalweight(), is(9));
	}

	@Test
	public void testB_B_shortestPath_step9() {
		String from = "B";
		String to = "B";
		Path<String> path = tps.getShortestPathFromPathList(graph, from, to);
		assertThat(path.getTotalweight(), is(9));
	}

	@Test
	public void testC_C_totalWeightless30_step10() {
		String from = "C";
		String to = "C";
		List<Path<String>> list = tps.generatePath(graph, from, to, maxWeightFilter);
		assertThat(list.size(), is(7));
	}
	
	private void printPath(List<Path<String>> list){
		for(Path<String> path : list){
			System.out.println("------------------");
			printEdge(path.getEdgeList());
		}
	}
	
	private void printEdge(List<Edge<String>> set){
		for(Edge<String> edge : set){
			System.out.println(String.format("(%s,%s)", edge.getFrom(),edge.getTo()));
		}
	}

}
