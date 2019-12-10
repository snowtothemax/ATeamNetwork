package application;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Filename:   Graph.java
 * Project:    p4
 * Authors:    Max Johnson
 * Due Date:   Nov. 14th
 * 
 * Directed and unweighted graph implementation
 */

/**
 * A Graph Object that is used as an abstract data type to organize data by
 * creating Verticies that represent that data to be stored, connecting each
 * Vertex with an Edge that creates a path, which creates a data structure.
 * 
 * @author Max Johnson
 *
 */
public class Graph implements GraphADT {

    /**
     * A GraphNode object represents a vertex in the GraphADT. Each node contains
     * the data to be stored as a vertex, the list of all neighboring vertices, and
     * it's index in the adjacency matrix.
     * 
     * @author Maxwell Johnson
     *
     * @param <T>
     */
    private class GraphNode<T> {

        // private instance fields
        T vertex; // the vertex within the node
        List<GraphNode<T>> neighbors;// all of the nodes that the node shares an edge with
        int AMindex;// index within the adjacency matrix of the graph

        // constructor
        /**
         * Constructs a GraphNode with given vertex and AMindex
         * 
         * @param vert - the data stored in the node
         */
        private GraphNode(T vert) {
            // initializes the private fields of the GraphNode
            vertex = vert;
            AMindex = AMINDEX;// gets the next available index for the AM
            neighbors = new ArrayList<GraphNode<T>>();// the neighboors will be set with a setter method

            AMINDEX++;// increments the AM for the next node to be created
        }

        // getter methods
        /**
         * Returns the vertex of the given node
         * 
         * @return - vertex<T>
         */
        private T getVertex() {
            return vertex;
        }

        /**
         * Gets the nodes index in the adjaceny matrix
         * 
         * @return AMindex
         */
        private int getIndex() {
            return AMindex;
        }

        /**
         * returns the list of neighbors for the given GraphNode
         * 
         * @return neighbors
         */
        private List<GraphNode<T>> getNeighbors() {
            return neighbors;
        }

        // setter methods

        /**
         * Adds a neighbor to the list of neighbors for the given node on the graph.
         * 
         * @param node
         */
        private void addNeighbor(GraphNode<T> node) {
            neighbors.add(node);
        }
    }

    // Instance Variables
    private static int AMINDEX;// a static variable that is incremented every time a node is added
    private int size; // the # of edges in the array
    private int degree; // the # of vertices
    private List<GraphNode<String>> vertList; // List of all the vertices
    boolean[][] adjMatrix; // 2D list of each vertex represented by an index on each axis. the row index
                            // will tell where each edge comes from and the columns will tell where the edge
                            // goes to

    /*
     * Default no-argument constructor
     */
    /**
     * Constructs and initializes a Graph object. The adjacency matrix is
     * initialized to 100 indices in both rows and columns
     */
    public Graph() {
        AMINDEX = 0;// initializes the static index for the next index in the AM
        size = 0;// no edges are present at construction
        degree = 0;// no vertices are present at construction
        vertList = new ArrayList<GraphNode<String>>();// creates a new arrayList of GraphNodes of String type
        adjMatrix = new boolean[100][100];// initialized to 100 indices in both directions

    }

    /**
     * Add new vertex to the graph.
     *
     * If vertex is null or already exists, method ends without adding a vertex or
     * throwing an exception.
     * 
     * Valid argument conditions: 1. vertex is non-null 2. vertex is not already in
     * the graph
     * 
     */
    public void addVertex(String vertex) {
        // checks if the input vertex is null or not
        if (vertex == null) {
            return;// ends the method if the input is null
        }
        // checks if the vertex exists or not already in the graph
        if (vertExist(vertex)) {
            return; // ends the method if the vertex exists in the graph
        }

        // Creates a new GraphNode object of type String and adds it to the list of
        // vertices
        GraphNode<String> node = new GraphNode<String>(vertex);
        vertList.add(node);
        degree++;

        // this section of the code checks if the AMINDEX is larger than the length of
        // the adjMatrix
        // if the AMINDEX is equal to the adjMatrix length, and if it is, it creates a
        // new list to be made for the adjMatrix with a larger length
        if (AMINDEX >= adjMatrix.length) {
            boolean[][] matrix = new boolean[AMINDEX * 2][AMINDEX * 2];// doubles the length of the current matrix

            // loop to add all previous values from the matrix into the new one
            for (int i = 0; i < adjMatrix.length; i++) {
                for (int j = 0; j < adjMatrix[i].length; j++) {
                    matrix[i][j] = adjMatrix[i][j];// copies value from same position in adjMatrix into matrix

                }
            }
            // sets the adjMatrix array equal to the new matrix
            adjMatrix = matrix;
        }
    }

    /**
     * Remove a vertex and all associated edges from the graph.
     * 
     * If vertex is null or does not exist, method ends without removing a vertex,
     * edges, or throwing an exception.
     * 
     * Valid argument conditions: 1. vertex is non-null 2. vertex is not already in
     * the graph
     */
    public void removeVertex(String vertex) {
        // checks if the input vertex is null or not
        if (vertex == null) {
            return;// ends the method if the input is null
        }
        // checks if the vertex exists or not already in the graph
        if (!vertExist(vertex)) {
            return; // ends the method if the vertex doesnt exist in the graph
        }

        // returns the node from the graph list that is removed
        GraphNode<String> node = removeVertexHelper(vertex);
        degree--;

        // removes all associated edges with the node removed from the graph by
        // iterating through the matrix and making all values that are true equal to
        // false within its indicated row and column. thus removing any edge that comes
        // FROM that vertex (anything that is
        // true in its row) and TO that vertex (anything true in its column).
        for (int i = 0; i < adjMatrix.length; i++) {
            // removes any edge coming from that said vertex if the matrices is true
            if (adjMatrix[node.getIndex()][i]) {
                adjMatrix[node.getIndex()][i] = false;
                size--;// decrements the size of the graph (number of edges)
            }
            // removes any edge going to that said vertex if the matrices is true;
            if (adjMatrix[i][node.getIndex()]) {
                adjMatrix[i][node.getIndex()] = false;
                size--;
            }
        }

    }

    /**
     * Add the edge from vertex1 to vertex2 to this graph. (edge is directed and
     * unweighted) If either vertex does not exist, add vertex, and add edge, no
     * exception is thrown. If the edge exists in the graph, no edge is added and no
     * exception is thrown.
     * 
     * Valid argument conditions: 1. neither vertex is null 2. both vertices are in
     * the graph 3. the edge is not in the graph
     */
    public void addEdge(String vertex1, String vertex2) {
        // determines whether the vertices are null or not
        if (vertex1 == null || vertex2 == null) {
            return;
        }

        // if either edge does not exist in the graph already, they are added to the
        // graph
        // checks if vertex1 exists, and if not, it is added to the graph
        if (!vertExist(vertex1)) {
            addVertex(vertex1);
        }
        // checks if vertex2 exists and if not, it is added to the graph
        if (!vertExist(vertex2)) {
            addVertex(vertex2);
        }

        // gets the node and AMINDEX for each vertex in the graph
        GraphNode<String> fromNode = findVertex(vertex1);// node where edge comes from
        GraphNode<String> toNode = findVertex(vertex2);// node where edge goes to
        int fromInd = fromNode.getIndex();// edge comes from this index
        int toInd = toNode.getIndex();// edge goes to this index

        // accesses the adjMatrix and uses the fromInd as the index for the row of the
        // matrix (from) and the toInd as the index for the column of the matrix (to).
        adjMatrix[fromInd][toInd] = true;
        size++;// increments the amount of edges in the list

        // adds toNode to the adjacency list of fromNode
        fromNode.addNeighbor(toNode);
    }

    /**
     * Remove the edge from vertex1 to vertex2 from this graph. (edge is directed
     * and unweighted) If either vertex does not exist, or if an edge from vertex1
     * to vertex2 does not exist, no edge is removed and no exception is thrown.
     * 
     * Valid argument conditions: 1. neither vertex is null 2. both vertices are in
     * the graph 3. the edge from vertex1 to vertex2 is in the graph
     */
    public void removeEdge(String vertex1, String vertex2) {
        // checks if either input is null and ends if so
        if (vertex1 == null || vertex2 == null) {
            return;
        }
        // checks if either vertex doesn't exist in the graph and ends if so
        if (!vertExist(vertex1) || !vertExist(vertex2)) {
            return;
        }

        // gets the node and AMINDEX for each vertex in the graph
        GraphNode<String> fromNode = findVertex(vertex1);// node where edge comes from
        GraphNode<String> toNode = findVertex(vertex2);// node where edge goes to
        int fromInd = fromNode.getIndex();// edge comes from this index
        int toInd = toNode.getIndex();// edge goes to this index

        // accesses the adjMatrix and uses the fromInd as the index for the row of the
        // matrix (from) and the toInd as the index for the column of the matrix (to).
        if (adjMatrix[fromInd][toInd]) {
            adjMatrix[fromInd][toInd] = false;// removes the edge between the two vertices if it exists
            size--;// increments the amount of edges in the list
        }

        // removes toNode from the adjacency list of fromNode
        fromNode.getNeighbors().remove(toNode);
    }

    /**
     * Returns a Set that contains all the vertices
     * 
     */
    public Set<String> getAllVertices() {
        // first checks if the list is empty or not, and if so, returns an empty set
        Set<String> set = new HashSet<String>();
        if (vertList.isEmpty()) {
            return set;
        }

        // now goes through the list and adds all the vertices to the set by iterating
        // through the nodes and adding each of the nodes vertices individually
        for(int i = 0; i < vertList.size(); i++) {
            //creates a temporary string from accessing the nodes vertex then adds it to the set
            String str = vertList.get(i).getVertex();
            set.add(str);
        }
        return set;
    }

    /**
     * Get all the neighbor (adjacent) vertices of a vertex
     *
     */
    public List<String> getAdjacentVerticesOf(String vertex) {
        // checks if the vertex is null or not and returns null if so
        if (vertex == null) {
            return null;
        }
        // checks if the vertex exists in the graph and returns null if not
        if (!vertExist(vertex)) {
            return null;
        }
        // accesses the node associated with the vertex
        GraphNode<String> node = findVertex(vertex);

        // returns the neighbor list of the node by iterating through the list of
        // neighbors and adding the vertex of each node to a new list
        List<String> strings = new ArrayList<String>();
        for (int i = 0; i < node.getNeighbors().size(); i++) {
            strings.add(node.getNeighbors().get(i).getVertex());
        }
        return strings;

    }

    /**
     * Returns the number of edges in this graph.
     */
    public int size() {
        return size;
    }

    /**
     * Returns the number of vertices in this graph.
     */
    public int order() {
        return degree;
    }

    // private helper methods
    /**
     * Determines whether the input vertex exists in the graph or not.
     * 
     * @param vert - the vertex to be found in the graph.
     * @return true if it exists in the graph, false otherwise.
     */
    private boolean vertExist(String vert) {
        // iterates through the list of vertices and compares the input vertex to the
        // vertex in iteration
        for (int i = 0; i < degree; i++) {
            if (vertList.get(i).getVertex().equals(vert)) {
                return true;// returns true if they match
            }
        }

        return false;// returns false otherwise
    }

    /**
     * Finds the GraphNode of the input vertex for the Graph
     * 
     * @param vert - the vertex to be found in the graph.
     * @return The GraphNode containing the matching vertex, null otherwise.
     */
    private GraphNode<String> findVertex(String vert) {
        // iterates through the list of vertices and compares the input vertex to the
        // vertex in iteration
        for (int i = 0; i < degree; i++) {
            if (vertList.get(i).getVertex().equals(vert)) {
                return vertList.get(i);// returns the GraphNode containing the vertex if they match
            }
        }

        return null;// returns null otherwise
    }

    /**
     * Removes the GraphNode of the input vertex from the graph. It also removes it
     * from the nighborlist of each node it had in its neighbor list
     * 
     * @param vert - the vertex to be found in the graph.
     * @return The GraphNode being removed, null otherwise.
     */
    private GraphNode<String> removeVertexHelper(String vert) {
        // iterates through the list of vertices and compares the input vertex to the
        // vertex in iteration.
        for (int i = 0; i < degree; i++) {
            if (vertList.get(i).getVertex().equals(vert)) {
                GraphNode<String> node = vertList.remove(i);// creates a temporary variable for the node removed
                // removes the node from the adjacency lists of other nodes it is adjacent to
                List<GraphNode<String>> list = node.getNeighbors();

                // uses a double for loop to remove the node from each other nodes list
                for (int j = 0; j < list.size(); j++) {
                    // accesses each neighboring node and its neighbor list
                    GraphNode<String> neighb = list.get(j);
                    // accesses the neighbor list from that said neighbor
                    List<GraphNode<String>> list2 = neighb.getNeighbors();

                    // uses another for loop to cycle through each neighbor of the neighbor's
                    // neighbor list
                    for (int k = 0; k < list2.size(); k++) {
                        // checks if the node currently being checked is equal to the node being removed
                        // and removes it from the neighbor list if so
                        if (list2.get(k).equals(node)) {
                            list2.remove(k);
                        }

                    }
                }

                return node;// returns the node removed from the list
            }
        }

        return null;// returns null otherwise(not in the list)
    }
    
    public static void main(String[] args) {
        System.out.print("HI");
    }

}