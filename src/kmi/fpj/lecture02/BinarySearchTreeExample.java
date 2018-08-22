package kmi.fpj.lecture02;

import java.util.Iterator;

public class BinarySearchTreeExample<T> implements Iterable<T> {

    public static void main(String[] args) {
        BinarySearchTreeOptional<Integer, String> bst = new BinarySearchTreeOptional<>();
        bst = bst.put(10, "foo");
        bst = bst.put(20, "bar");
//		bst.printOut(System.out);q
        bst.put(35, "baz").put(5, "qux").put(15, "quux").printOut(System.out);
    }

    @Override
    public Iterator<T> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

}
