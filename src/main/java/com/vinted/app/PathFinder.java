package com.vinted.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PathFinder {

	private Graph graph;
	
	public PathFinder(Graph graph) {
		this.graph = graph;
	}
	
	/**
	 * Calculates path length
	 * @param pathName Path as string "A-B-C"
	 * @return
	 * @throws NoPathException
	 */
	public int findPathLength(String pathName) throws NoPathException {
		String[] nodes = pathName.split("-");
		int distance = 0;
		
		for (int i = 0; i < nodes.length - 1; i++) {
			Node node = graph.getNodeByName(nodes[i]);
			if (node == null) {
				throw new NoPathException();
			} else {
				int distanceTo = node.distanceTo(nodes[i + 1]);
				if (distanceTo == -1) {
					throw new NoPathException();
				} else {
					distance += distanceTo;
				}
			}
		}
		
		return distance;
	}

	/**
	 * Returns list of paths up to maximum specified nodes between from and to.
	 * 
	 * @param from
	 * @param to
	 * @param maxSteps
	 * @return Returns list with paths. If there are no paths, returns empty
	 *         list.
	 */
	public List<Path> getPaths(String from, String to, int maxSteps) {
		List<Path> result = new ArrayList<Path>();
		Path path = new Path();
		tripFinderByMaxSteps(result, path, from, to, maxSteps);
		return result;	
	}
	
	/**
	 * Recursive function looking for paths between from and to. Limited by
	 * maximum number of steps
	 * 
	 * @param result
	 *            List that will contain all found paths
	 * @param path
	 *            Current path that is used for path finding
	 * @param from
	 *            from node name
	 * @param to
	 *            to node name
	 * @param maxSteps
	 */
	private void tripFinderByMaxSteps(List<Path> result, Path path, String from, String to, int maxSteps) {
		if (path.size() > maxSteps) {
			return;
		}
		
		if (from.equals(to) && path.size() > 0) {
			result.add(new Path(path));
		}
		
		Node node = graph.getNodeByName(from);
		if (node == null) {
			return;
		}
		
		path.add(node);
		for (String link : node.getLinks().keySet()) {
			tripFinderByMaxSteps(result, path, link, to, maxSteps);
		}
		path.pop();
	}
	
	/**
	 * Returns paths between from and to which are not longer than max length
	 * specified.
	 * 
	 * @param from
	 * @param to
	 * @param maxLength
	 * @return Returns list of paths. If no paths are found returns empty list.
	 */
	public List<Path> getPathsUnder(String from, String to, int maxLength) {
		List<Path> result = new ArrayList<Path>();
		Path path = new Path();
		tripFinderByMaxLength(result, path, from, to, maxLength );
		return result;	
	}
	
	/**
	 * Recursive function looking for paths between from and to which are
	 * limited by total length of a path.
	 * 
	 * @param result
	 *            List for containing found paths
	 * @param path
	 *            Current path
	 * @param from
	 * @param to
	 * @param maxLength
	 */
	private void tripFinderByMaxLength(List<Path> result, Path path, String from, String to, int maxLength) {
		Node node = graph.getNodeByName(from);
		if (node == null) {
			return;
		}
		
		path.add(node);
		
		if (path.getPathLength() < maxLength) {
			if (path.size() > 1 && from.equals(to)) {
				result.add(new Path(path));
			}

			for (String link : node.getLinks().keySet()) {
				tripFinderByMaxLength(result, path, link, to, maxLength);
			}
		}
		
		path.pop();
	}

	/**
	 * Function for finding shortest path between from and to. If graph is
	 * disconnected and from and to are in separate regions, null will be
	 * returned. Example AB1, BA1, CD1, DC1. There is no path between A and D
	 * 
	 * @param from
	 * @param to
	 * @return Returns shortest path or null if no path found
	 */
	public Path shortestPath(String from, String to) {
		List<Path> found = new ArrayList<Path>();
		List<Path> paths = new ArrayList<Path>();
		
		Path startPath = new Path();
		startPath.add(graph.getNodeByName(from));
		paths.add(startPath);
		
		while (true) {
			startPath = paths.remove(0);
			from = startPath.getLast().getName();
		
			Node node = graph.getNodeByName(from);
			for (String nodeName : node.getLinks().keySet()) {
				Node toNode = graph.getNodeByName(nodeName);
				
				if (!startPath.contains(toNode) || nodeName.equals(to)) {
					Path path = new Path(startPath);
					path.add(toNode);
					
					if (nodeName.equals(to)) {
						found.add(path);
					} else {
						paths.add(path);
					}
				} else {
					// Cycle, do not follow this path anymore
				}
			}
			
			Collections.sort(paths);
			Collections.sort(found);
			
			if (paths.isEmpty() && found.isEmpty()) {
				// No path found. Disconnected graph.
				return null;
			}
			
			if (!found.isEmpty() && (paths.isEmpty() || found.get(0).compareTo(paths.get(0)) < 1)) {
				return found.get(0);
			}
		}
	}

	/**
	 * Returns paths from to which are made from exact number of nodes. This
	 * method reuses getPaths method and the filters only suitable paths for
	 * result.
	 * 
	 * @param from
	 * @param to
	 * @param exact
	 *            Exact number of nodes that should be in the path
	 * @return Returns list of paths or empty list if there are no paths.
	 */
	public List<Path> getPathsExact(String from, String to, int exact) {
		List<Path> paths = getPaths(from, to, exact);
		List<Path> result = new ArrayList<Path>();
		for (Path path : paths) {
			if (path.size() == exact) {
				result.add(path);
			}
		}
		
		return result;
	}

}
