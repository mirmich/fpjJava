package kmi.fpj.lecture01;

public class BinarySearchTreeExample {

    public static void main(String[] args) {
        BinarySearchTree<Integer, String> bst = new BinarySearchTree<>();
        bst = bst.put(10, "foo");
        bst = bst.put(20, "bar");
        bst = bst.put(5,"lol");
		//bst.printOut(System.out);



        bst = bst.put(35, "baz").put(5, "qux").put(15, "quux");
        System.out.println("BinarySearchTree iterator");
        for (Integer e : bst){
            System.out.println(e);
        }

        SortedLinkedSet<Integer> sls = new SortedLinkedSet<>();
        sls = sls.add(10).add(5).add(3).add(2).add(7).add(4).add(11).remove(4);
        System.out.println(sls.contains(4));

        System.out.println("SortedLinkedSet iterator");
        for(Integer i : sls){
            System.out.println(i);
        }
    }

}
