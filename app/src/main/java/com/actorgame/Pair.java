import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Pair<K, V> {
    public K key;
    public V value;

    public Pair() {}

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return (key + " -> " + value);
    }

    public static void main(String[] args) {

    }
}
