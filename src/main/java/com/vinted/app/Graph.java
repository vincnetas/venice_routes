package com.vinted.app;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Graph {

	private Map<String, Node> nodes = new HashMap<String, Node>();
	
	/**
	 * Constructs a graph from input text. Data is made from edge definitions
	 * separated by comma, edge is defined by [node][node][weight]. [node] is a
	 * one letter identifier of node, and [weight] is a number representing edge
	 * weight. AB10, EF9, etc...
	 * 
	 * @param input
	 *            Graph input data
	 * @return
	 * @throws GraphException
	 *             if there is error in input data
	 */
	public static Graph buildGraph(String input) throws GraphException {
		Graph graph = new Graph();
		
		StringTokenizer tokenizer = new StringTokenizer(input, ",");
		while (tokenizer.hasMoreTokens()) {
			String segment = tokenizer.nextToken().trim();
			if (segment.length() < 3) {
				throw new GraphException("Input segment to short (at least 3 chars needed) : " + segment);
			}
			
			String fromNode = String.valueOf(segment.charAt(0));
			String toNode = String.valueOf(segment.charAt(1));
			
			Integer length;
			try {
				length = Integer.valueOf(segment.substring(2));
			} catch (NumberFormatException numberFormatException) {
				throw new GraphException("Invalid link length provided : " + segment.substring(2));
			}
			
			if (length < 0) {
				throw new GraphException("Distance can't be negative : " + segment);
			}
			
			if (fromNode.equals(toNode)) {
				throw new GraphException("From and to nodes cant be the same in one link : " + segment);
			}
			
			if (graph.hasDirectLink(fromNode, toNode)) {
				throw new GraphException("Same link already specified : " + segment);
			}
			
			Node node = graph.getNodeByName(fromNode);
			if (node == null) {
				node = new Node(fromNode, graph);
				graph.addNode(node);
			}
			node.addLink(toNode, length);
		}
		
		if (!graph.isValid()) {
			throw new GraphException("Some nodes in graph have link with non existing nodes");
		}
		
		return graph;
	}
	
	public void addNode(Node node) {
		nodes.put(node.getName(), node);
	}
	
	public boolean hasNode(String name) {
		return nodes.containsKey(name);
	}
	
	public Node getNodeByName(String name) {
		return nodes.get(name);
	}
	
	/**
	 * Checks if node from is neighbor of node to
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public boolean hasDirectLink(String from, String to) {
		return nodes.containsKey(from) && nodes.get(from).hasLink(to);
	}
	
	/**
	 * Checks if current graph has all nodes that are defined in links. AB1,
	 * AC1, would be not valid graph as nodes B and C are not defined.
	 * 
	 * @return Returns true if graph is valid, false otherwise
	 */
	public boolean isValid() {
		for (Node node : nodes.values()) {
			if (!node.isValid()) {
				return false;
			};
		}
		
		return true;
	}
}
