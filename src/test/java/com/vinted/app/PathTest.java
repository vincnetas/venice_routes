package com.vinted.app;

import static org.junit.Assert.*;

import org.junit.Test;

public class PathTest {

	@Test
	public void test() {
		Graph graph = new Graph();
		Path path = new Path();
		Node a = new Node("A", graph);
		Node b = new Node("B", graph);
		Node c = new Node("C", graph);
		Node d = new Node("D", graph);

		a.addLink("B", 10);
		b.addLink("C", 10);
		c.addLink("D", 10);
		
		path.add(a);
		path.add(b);
		path.add(c);
		
		assertEquals(20, path.getPathLength());
		
		Path newPath = new Path(path);
		newPath.add(d);
		
		assertEquals(30, newPath.getPathLength());
		
		assertTrue(newPath.compareTo(path) > 1);
		
		newPath.pop();
		
		assertEquals(20, newPath.getPathLength());

		try {
			path.add(a);
			fail("Non neighbor added to graph");
		} catch (GraphException e) {
			
		}
	}

}
