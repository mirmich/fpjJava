package kmi.fpj.lecture01;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.Stack;

/**
 * Binarni vyhledavaci strom
 */
public class BinarySearchTree<K extends Comparable<K>, V> implements Iterable<K> {

    private final Node<K, V> root;

    private BinarySearchTree(Node<K, V> root) {
        this.root = root;
    }

    /**
     * Vytvori prazdny binarni vyhledavaci strom
     */
    public BinarySearchTree() {
        this(null);
    }

    /**
     * @return vraci novy binarni vyhledavaci strom, rozsireny o novy uzel <tt>key:value</tt>.
     * Pokud v puvodnim stromu jiz uzel s klicem <tt>key</tt> existuje,
     * je zmena hodnota <tt>value</tt>.
     */
    public BinarySearchTree<K, V> put(K key, V value) {
        Node<K, V> newRoot;
        if (root == null) newRoot = new Node<>(key, value);
        else newRoot = root.put(key, value);
        return new BinarySearchTree<>(newRoot);
    }

    /**
     * Vykresli strukturu stromu
     */
    public void printOut(PrintStream out) {
        if (root != null) root.printOut(out, 0);
    }

    @Override
    public Iterator<K> iterator() {
        return new BinarySearcTreeIterator(root);
    }

    /**
     * Pomocna trida reprezentujici jednotlive uzly stromu
     */
    private static class Node<KK extends Comparable<KK>, VV> {

        private final KK key;
        private final VV value;

        private final Node<KK, VV> left;
        private final Node<KK, VV> right;

        /**
         * Konstruktor vnitrniho uzlu
         */
        public Node(KK key, VV value, Node<KK, VV> left, Node<KK, VV> right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }

        /**
         * Konstruktor listu
         */
        public Node(KK key, VV value) {
            this(key, value, null, null);
        }

        /**
         * Vlozi novy uzel do stromu
         */
        public Node<KK, VV> put(KK newKey, VV newValue) {
            int cmp = newKey.compareTo(key);
            if (cmp == 0) return new Node<>(newKey, newValue, left, right);
            else if (cmp < 0) {
                // vkladame do leveho podstromu
                Node<KK, VV> newLeft;
                if (left != null) newLeft = left.put(newKey, newValue);
                else newLeft = new Node<>(newKey, newValue);
                return new Node<>(key, value, newLeft, right);
            } else {

                // vkladame do praveho podstromu
                Node<KK, VV> newRight;
                if (right != null) newRight = right.put(newKey, newValue);
                else newRight = new Node<>(newKey, newValue);
                return new Node<>(key, value, left, newRight);
            }
        }

        /**
         * Vykresli cast stromu
         * @param level -- udava hloubku zanoreni uzlu
         */
        public void printOut(PrintStream out, int level) {
            if (left != null) left.printOut(out, level + 1);

            for (int i = 0; i < level; i++)
                out.print("   ");
            out.println(key + "=" + value + " " + level);

            if (right != null) right.printOut(out, level + 1);
        }
    }

    private static class BinarySearcTreeIterator<K> implements Iterator<K> {

        private Stack<Node> stack = new Stack<>();

        //@param root: The root of binary tree.
        public BinarySearcTreeIterator(Node root) {
            push(root);
        }

        //@return: True if there has next node, or false
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        //@return: return next node
        public K next() {
            Node node = stack.pop();
            push(node.right);
            return (K) node.key;
        }

        private void push(Node node) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }
    }
}