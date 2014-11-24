package com.vinted.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Specify input file as an argument");
			return;
		}
		
		String graphData;
		try {
			BufferedReader br = new BufferedReader(new FileReader(args[0]));
			try {
				graphData = br.readLine();
			} finally {
				br.close();
			}
		} catch (IOException e) {
			System.out.println("Unable to read from specified file : " + e.getMessage());
			return;
		}
		
		PathFinder pathFinder;
		try {
			Graph graph = Graph.buildGraph(graphData);
			pathFinder = new PathFinder(graph);
		} catch (GraphException e) {
			System.out.println("Invalid graph data : " + e.getMessage());
			return;
		}
		
		String[] paths = new String[] {
				"A-B-C",
				"A-D",
				"A-D-C",
				"A-E-B-C-D",
				"A-E-D"
		};
		
		for (int i = 0; i < paths.length; i++) {
			try {
				System.out.println("#" + (i + 1) + ": " + pathFinder.findPathLength(paths[i]));
			} catch (NoPathException e) {
				System.out.println("#" + (i + 1) + ": NO SUCH ROUTE");
			}
		}
		
		System.out.println("#6: " + pathFinder.getPaths("C", "C", 3).size());
		System.out.println("#7: " + pathFinder.getPaths("A", "C", 3).size());
		System.out.println("#8: " + pathFinder.shortestPath("A", "C").getPathLength());
		System.out.println("#9: " + pathFinder.shortestPath("B", "B").getPathLength());
		System.out.println("#10: " + pathFinder.getPathsUnder("C", "C", 30).size());
	}

}
