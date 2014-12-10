package com.vinted.app.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class PathTest {

	private Graph graph;

	@Before
	public void setup() throws GraphException {
		graph = Graph.buildGraph("AB10, BC10, CB10, DE10, ED10");
	}
	
	private Path getPathAB() {
		Path result = new Path();
		
		result.add(graph.getNodeByName("A"));
		result.add(graph.getNodeByName("B"));
		
		return result;
	}
	
	@Test
	public void testPathLength() {
		Path path = getPathAB();
		
		assertEquals(10, path.getPathLength());
	}

	@Test
	public void testPathFromPath() {
		Path path = getPathAB();
		Path copy = new Path(path);
		
		assertEquals(path.getPathLength(), copy.getPathLength());
	}
	
	@Test
	public void testPathLengthCompare() {
		Path path = getPathAB();
		Path copy = new Path(path);
		
		copy.add(graph.getNodeByName("C"));
		
		assertEquals(20, copy.getPathLength());
		assertTrue(path.compareTo(copy) < 1);
	}
	
	@Test
	public void testPopPath() {
		Path path = getPathAB();
		path.add(graph.getNodeByName("C"));
		path.pop();
		assertEquals(10, path.getPathLength());
	}
		
	@Test
	public void testAddingWrongNode() {
		Path path = getPathAB();
		
		try {
			path.add(graph.getNodeByName("D"));
			fail("Non neighbor added to graph");
		} catch (PathException e) {
			
		}
	}
}
