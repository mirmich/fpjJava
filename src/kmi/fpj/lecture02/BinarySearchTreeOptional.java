package kmi.fpj.lecture02;

import java.io.PrintStream;
import java.util.Optional;

/**
 * Binarni vyhledavaci strom
 */
public class BinarySearchTreeOptional<K extends Comparable<K>, V> {

    private final Optional<Node<K, V>> root;

    private BinarySearchTreeOptional(Node<K, V> root) {
        this.root = Optional.of(root);
    }

    /**
     * Vytvori prazdny binarni vyhledavaci strom
     */
    public BinarySearchTreeOptional() {
        this.root = Optional.empty();
    }

    /**
     * @return vraci novy binarni vyhledavaci strom, rozsireny o novy uzel <tt>key:value</tt>.
     * Pokud v puvodnim stromu jiz uzel s klicem <tt>key</tt> existuje,
     * je zmena hodnota <tt>value</tt>.
     */
    public BinarySearchTreeOptional<K, V> put(K key, V value) {
        Node<K, V> newRoot = root.map(x -> x.put(key, value)).orElseGet(() -> new Node<>(key, value));
        return new BinarySearchTreeOptional<>(newRoot);
    }

    /**
     * Vykresli strukturu stromu
     */
    public void printOut(PrintStream out) {
        root.ifPresent(node -> node.printOut(out, 0));
    }

    /**
     * Pomocna trida reprezentujici jednotlive uzly stromu
     */
    private static class Node<KK extends Comparable<KK>, VV> {

        private final KK key;
        private final VV value;

        private final Optional<Node<KK, VV>> left;
        private final Optional<Node<KK, VV>> right;

        /**
         * Konstruktor vnitrniho uzlu
         */
        public Node(KK key, VV value, Optional<Node<KK, VV>> left, Optional<Node<KK, VV>> right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }

        /**
         * Konstruktor listu
         */
        public Node(KK key, VV value) {
            this.key = key;
            this.value = value;
            this.left = Optional.empty();
            this.right = Optional.empty();
        }

        /**
         * Vlozi novy uzel do stromu
         */
        public Node<KK, VV> put(KK newKey, VV newValue) {
            int cmp = newKey.compareTo(key);
            if (cmp == 0) return new Node<>(newKey, newValue, left, right);
            Optional<Node<KK, VV>> extendedNode = (cmp < 0 ? left : right);

            Node<KK, VV> newNode = extendedNode.map(node -> node.put(newKey, newValue)).orElseGet(() -> new Node<>(newKey, newValue));

            if (cmp < 0) return new Node<>(newKey, newValue, Optional.of(newNode), right);
            else return new Node<>(newKey, newValue, left, Optional.of(newNode));
        }

        /**
         * Vykresli cast stromu
         * @param level -- udava hloubku zanoreni uzlu
         */
        public void printOut(PrintStream out, int level) {
            left.ifPresent(node -> node.printOut(out, level + 1));

            for (int i = 0; i < level; i++)
                out.print("   ");
            out.println(key + "=" + value);

            right.ifPresent(node -> node.printOut(out, level + 1));
        }
    }
}
