package com.vinted.app.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to store path object. Paths are made from nodes. Path has a length
 * equal to sum of distances to neighboring nodes.
 * 
 * @author vincnetas
 *
 */
public class Path implements Comparable<Path> {

	private final List<Node> nodes =  new ArrayList<Node>();
	
	private int pathLength = 0;
	
	public Path() {
		super();
	}
	
	/**
	 * Create new path identical to one that is passed in
	 * 
	 * @param path
	 */
	public Path(Path path) {
		this.pathLength = path.pathLength;
		nodes.addAll(path.nodes);
	}
	
	public int getPathLength() {
		return pathLength;
	}
	
	/**
	 * Adds node to path and increases path's length. Only neighbor of last node can be added to path.
	 * 
	 * @param node
	 */
	public void add(Node node) {
		if (!nodes.isEmpty()) {
			int distance = getLast().distanceTo(node.getName());
			if (distance == -1) {
				throw new GraphException("Only neighbor of last node can be added to path " + getLast());
			}
			
			pathLength += distance;
		}
		
		nodes.add(node);
	}
	
	/**
	 * Remove last node form path and update path's length
	 */
	public void pop() {
		if (nodes.isEmpty()) {
			return;
		}
		
		Node node = nodes.remove(nodes.size() - 1);
		if (!nodes.isEmpty()) {
			pathLength -= getLast().distanceTo(node.getName());
		}
	}
	
	/**
	 * 
	 * @return Returns last node in path
	 */
	public Node getLast() {
		return nodes.get(nodes.size() - 1);
	}

	/**
	 * Checks if path contains specified node
	 * 
	 * @param node
	 * @return
	 */
	public boolean contains(Node node) {
		return nodes.contains(node);
	}

	/**
	 * 
	 * @return Returns number of nodes in path
	 */
	public int size() {
		return nodes.size();
	}

	/**
	 * Shorter path is smaller than longer one
	 */
	@Override
	public int compareTo(Path o) {
		return getPathLength() - o.getPathLength();
	}
	
	/* (non-Javadoc)
	 * @see java.util.AbstractCollection#toString()
	 */
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (Node node : nodes) {
			buffer.append(node.getName() + " -> ");
		}
		
		buffer.append("Lenght " + pathLength);
		
		return buffer.toString();
	}
}
