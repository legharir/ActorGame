package com.actorgame;

public class Connection<K, V, C> {
    public K first;
    public V second;
    public C connection;

    public Connection(K first, V second, C connection) {
        this.first = first;
        this.second = second;
        this.connection = connection;
    }

    public Connection() {}

    @Override
    public String toString() {
        return new String(first + " -> " + second + " (" + connection + ")");
    }
}
