package com.vinted.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class PathFinderTest {

	private Graph graph;
	
	private PathFinder pathFinder;

	
    @Before
    public void setUp() {
    	try {
			graph = Graph.buildGraph("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7, FG1, GF1");
			pathFinder = new PathFinder(graph);
		} catch (GraphException e) {
			throw new Error(e);
		}
    }
	
    @Test
    public void testInvalidInput() {
    	try {
    		Graph.buildGraph("AA");
    		fail();
    	} catch (GraphException e) {	
    	}
    	
    	try {
    		Graph.buildGraph("AA-10");
    		fail();
    	} catch (GraphException e) {
    	}
    	
    	try {
			Graph.buildGraph("AA5");
			fail();
		} catch (GraphException e) {
		}
    	
    	try {
			Graph.buildGraph("AB5, AB6");
			fail();
		} catch (GraphException e) {
		}
    	
    	try {
    		Graph.buildGraph("ABC");
    		fail();
    	} catch (GraphException e) {
    		
    	}
    	
    	try {
    		Graph.buildGraph("AB1");
    		fail();
    	} catch (GraphException e) {
    	}
    }
    
	@Test
	public void testPathLength() throws NoPathException {		
		assertEquals(0, pathFinder.findPathLength("A"));
		assertEquals(9, pathFinder.findPathLength("A-B-C"));
		assertEquals(5, pathFinder.findPathLength("A-D"));
		assertEquals(13, pathFinder.findPathLength("A-D-C"));
		assertEquals(22, pathFinder.findPathLength("A-E-B-C-D"));
		try {
			pathFinder.findPathLength("A-E-D");
			fail();
		} catch (NoPathException e) {
		}
	}
	
	@Test
	public void testTripCount() {
		assertEquals(0, pathFinder.getPaths("X", "Z", 10).size());
		assertEquals(2, pathFinder.getPaths("C", "C", 3).size());
		assertEquals(3, pathFinder.getPathsExact("A", "C", 4).size());
		assertEquals(7, pathFinder.getPathsUnder("C", "C", 30).size());

	}
	
	@Test
	public void testShortestRoute() {
		assertEquals(9, pathFinder.shortestPath("A", "C").getPathLength());
		assertEquals(9, pathFinder.shortestPath("B", "B").getPathLength());		
		assertEquals(null, pathFinder.shortestPath("A", "F"));
		
	}
}
