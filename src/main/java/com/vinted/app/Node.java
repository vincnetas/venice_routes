package com.vinted.app;

import java.util.HashMap;
import java.util.Map;

public class Node {

	private final String name;
	
	/**
	 * Stores neighbor nodes and distance to them
	 */
	private final Map<String, Integer> links = new HashMap<String, Integer>();
	
	/**
	 * Parent graph to which this node belongs
	 */
	private final Graph graph;
	
	public Node(String name, Graph graph) {
		this.name = name;
		this.graph = graph;
	}
	
	public void addLink(String toName, int length) {
		links.put(toName, length);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the graph
	 */
	public Graph getGraph() {
		return graph;
	}
	
	/**
	 * Checks if this node is neighbor of node to.
	 * @param to
	 * @return
	 */
	public boolean hasLink(String to) {
		return links.containsKey(to);
	}
	
	/**
	 * Node is valid if it's parent graph has all nodes which are specified as
	 * neighbors of this node.
	 * 
	 * @return Returns true if this node is valid
	 */
	public boolean isValid() {
		for (String toNode : links.keySet()) {
			if (!graph.hasNode(toNode)) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Distance to neighboring node. If node is not neighbor returns -1;
	 * 
	 * @param node
	 * @return Returns distance to neighbor node or -1 if node is not neighbor.
	 */
	public int distanceTo(String node) {
		if (!links.containsKey(node)) {
			return -1;
		}
		return links.get(node);
	}
	
	/**
	 * 
	 * @return Returns all neighbors with distances to them
	 */
	public Map<String, Integer> getLinks() {
		return links;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return name.hashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	/**
	 * Nodes are equal if they names are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Node) {
			Node node = (Node) obj;
			return node.name.equals(name);
		}
		
		return false;
	}
	
	public String toString() {
		return name + " " + links;
	}
	
	
	
}
