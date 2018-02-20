package com.actorgame;

import android.util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import static java.lang.System.out;

/**
 * Adjacency matrix representation of a Graph, from algs4
 */
public class Graph {
    private int V;                                      //number of vertices in the graph
    private int E;                                      //number of edges in the graph
    private ArrayList<Pair<Integer, String>>[] adj;     //adj[i] is a list of neighbours of vertex i

    public Graph(int V) {
        this.V = V;
        this.E = 0;
        adj = (ArrayList<Pair<Integer, String>>[]) new ArrayList[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new ArrayList<Pair<Integer, String>>();
        }
    }

    /**
     * Retrives the number of vertices in the graph
     * @return the number of vertices
     */
    public int V() {
        return V;
    }

    /**
     * Retrieves the number of edges in the graph
     * @return the number of edges
     */
    public int E() {
        return E;
    }

    /**
     * Adds an edge between two vertices
     * @param from the source vertex
     * @param to the destination vertex
     * @param c the identifier of the connection (movie title, intersection name, etc)
     */
    public void addEdge(int from, int to, String c) {
        adj[from].add(new Pair<>(to, c));
        adj[to].add(new Pair<>(from, c));
        E++;
    }

    /**
     * Returns an iterable that iterates through the neighbours of the specified vertex
     * @param v the vertex to return the neighbours of
     * @return an iterable that iterates through the neighbours of the specified vertex
     */
    public Iterable<Pair<Integer, String>> adj(int v) {
        ArrayList<Pair<Integer, String>> list = new ArrayList<>();
        for (Pair<Integer, String> w : adj[v]) {
            list.add(w);
        }
        return list;
    }

    public String toString() {
        String s = V + "vertices and " + E + " edges\n----------------\n";
        for (int i = 0; i < V; i++) {
            s += i + ": ";
            for (Pair<Integer, String> p : adj[i]) {
                s += p.first + "(" + p.second + ")" + " ";
            }
            s += "\n";
        }
        return s;
    }

    public static void main(String[] args) {
        Graph g = new Graph(5);
        g.addEdge(0, 1, "a");
        g.addEdge(0, 3, "next");
        g.addEdge(0, 4, "last");
        g.addEdge(1, 2, "b");
        g.addEdge(3, 4, "c");
        for (Pair<Integer, String> c : g.adj[0]) {
            System.out.println(c.second);
        }
    }
}
