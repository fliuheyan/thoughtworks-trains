package com.thoughtworks.trains;

import com.thoughtworks.trains.filter.MaxStopsPathFilter;
import com.thoughtworks.trains.filter.MaxWeightPatchFilter;
import com.thoughtworks.trains.filter.PathFilter;
import com.thoughtworks.trains.graph.Graph;
import com.thoughtworks.trains.graph.Path;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TrainRouteGeneratorTest {
    private Graph<String> graph;
    private TrainRouteGenerator generator;
    private PathFilter<String> maxStopsFilter4;
    private PathFilter<String> maxWeightFilter;

    @Before
    public void init() {
        generator = new TrainRouteGenerator();
        String graph1Edges = "AB5,BC4,CD8,DC8,DE6,AD5,CE2,EB3,AE7";
        graph = generator.generateGraph(graph1Edges);
        maxStopsFilter4 = new MaxStopsPathFilter<>(4);
        maxWeightFilter = new MaxWeightPatchFilter<>(30);
    }

    @Test
    public void testA_B_C_step1() {
        String[] stops = {"B"};
        String result = graph.getDistance("A", "C", stops);
        assertThat(result, is("9"));
    }

    @Test
    public void testA_D_step2() {
        String[] strs = {};
        String result = graph.getDistance("A", "D", strs);
        assertThat(result, is("5"));
    }

    @Test
    public void testA_D_C_step3() {
        String[] stops = {"D"};
        String result = graph.getDistance("A", "C", stops);
        assertThat(result, is("13"));
    }

    @Test
    public void testA_E_B_C_D_step4() {
        String[] stops = {"E", "B", "C"};
        String result = graph.getDistance("A", "D", stops);
        assertThat(result, is("22"));
    }

    @Test
    public void testA_E_D_step5() {
        String[] stops = {"E"};
        String result = graph.getDistance("A", "C", stops);
        assertThat(result, is("NO SUCH ROUTE"));
    }

    @Test
    public void testC_C_with_maximum3stops_step_6() {
        String from = "C";
        String to = "C";
        List<Path<String>> list = generator.generatePath(graph, from, to,
            maxStopsFilter4);
        assertThat(list.size(), is("2"));
    }

    @Test
    public void testA_C_withexactly4stops_step7() {
        String from = "A";
        String to = "C";
        List<Path<String>> list = generator.generatePath(graph, from, to,
            maxStopsFilter4);
        assertThat(list.size(), is("3"));
    }

    @Test
    public void testA_C_shortestPath_step8() {
        String from = "A";
        String to = "C";
        Path<String> path = this.generator.getShortestPathFromPathList(graph, from, to);
        assertThat(path.getTotalWeight(), is("9"));
    }

    @Test
    public void testB_B_shortestPath_step9() {
        String from = "B";
        String to = "B";
        Path<String> path = this.generator.getShortestPathFromPathList(graph, from, to);
        assertThat(path.getTotalWeight(), is("9"));
    }

    @Test
    public void testC_C_totalWeightless30_step10() {
        String from = "C";
        String to = "C";
        List<Path<String>> list = generator.generatePath(graph, from, to, maxWeightFilter);
        assertThat(list.size(), is("7"));
    }
}