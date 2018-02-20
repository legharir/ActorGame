package com.actorgame;

import android.util.Pair;

import java.util.*;

import static java.lang.System.out;

/*
Implementation of a breadth first search, based on
https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/BreadthFirstPaths.java.html
 */
public class BFS {
    private boolean[] marked;
    private Pair<Integer, String>[] edgeTo;
    private int[] distTo;

    /**
     * Computes the shortest path between the source vertex and all other vertices in the graph
     * @param G the graph
     * @param s the source vertex
     */
    public BFS(Graph G, int s) {
        marked = new boolean[G.V()];
        edgeTo = new Pair[G.V()];
        distTo = new int[G.V()];
        bfs(G, s);
    }

    /**
     * Performs breadth first search on the target node
     * @param G the undirected graph
     * @param s the source vertex
     */
    private void bfs(Graph G, int s) {
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < G.V(); i++) {
            distTo[i] = Integer.MAX_VALUE;
            edgeTo[i] = new Pair<>(i, null);
        }
        distTo[s] = 0;
        marked[s] = true;
        q.add(s);

        while (!q.isEmpty()) {
            int v = q.remove();
            for (Pair<Integer, String> n : G.adj(v)) {
                if (!marked[n.first]) {
                    q.add(n.first);
                    edgeTo[n.first] = new Pair<>(v, n.second);
                    distTo[n.first] = distTo[v] + 1;
                    marked[n.first] = true;
                }
            }
        }
    }

    /**
     * Checks if there is a path to the specified vertex
     * @param v the destination vertex
     * @return True if there is path, and False otherwise
     */
    public boolean hasPathTo(int v) {
        return marked[v];
    }

    /**
     * Returns the shortest path from the specified vertex to the source
     * @param v the destination vertex
     * @return an iterable of the shortest path from the specified vertex to the source
     */
    public Iterable<Connection<Integer, Integer, String>> pathTo(int v) {
        if (!hasPathTo(v))
            return null;
        Stack<Connection<Integer, Integer, String>> path = new Stack<>();
        Connection<Integer, Integer, String> c = new Connection<>();
        c.first = v;
        Pair<Integer, String> i;
        for (i = edgeTo[v]; distTo[i.first] != 0; i = edgeTo[i.first]) {
            c.second = i.first;
            c.connection = i.second;
            path.push(c);
            c = new Connection<>();
            c.first = i.first;
        }
        path.push(new Connection<Integer, Integer, String>(c.first, i.first, i.second));
        return path;
    }

    /**
     * Retrieves the minimum distance to the specified vertex from the source
     * @param v the destination vertex
     * @return the length of the minimum path to the specified vertex
     */
    public int distTo(int v) {
        if (!marked[v]) return -1;
        return distTo[v];
    }

    public static void main(String[] args) {
        Graph g = new Graph(5);
        g.addEdge(0, 1, "a");
        g.addEdge(0, 3, "next");
        g.addEdge(0, 4, "last");
        g.addEdge(1, 2, "b");
        g.addEdge(3, 4, "c");


        BFS b = new BFS(g, 1);
        out.println(b.hasPathTo(2));
        System.out.println(b.pathTo(2));
        out.println(b.distTo(2));
    }
}
