package application;

import application.Graph;
import application.Controller;

public class Model {
	// In this class, we will be usingthe graph and importing JSON files in order
	// add certain friends to the graph, etc.
	
	// instance fields
	
	static Graph userNetwork;
	
	protected Model() {
		userNetwork = new Graph();
	}
}
