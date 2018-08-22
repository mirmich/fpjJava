package kmi.fpj.lecture01;

public class BinarySearchTreeExample {

    public static void main(String[] args) {
        BinarySearchTree<Integer, String> bst = new BinarySearchTree<>();
        bst = bst.put(10, "foo");
        bst = bst.put(20, "bar");
        bst = bst.put(5,"lol");
		//bst.printOut(System.out);



         bst = bst.put(35, "baz").put(5, "qux").put(15, "quux");

        for (Integer e : bst){
            System.out.println(e);
        }

        SortedLinkedSet<Integer> sls = new SortedLinkedSet<>();
        SortedLinkedSet<Integer> sls1 = new SortedLinkedSet<>();

        sls = sls.add(10).add(5).add(3).add(2).add(7).add(4).add(11);
        sls1 = sls1.add(3).add(5).add(2).add(18).add(15);

        System.out.println(sls.toString());
        System.out.println(sls1.toString());
        System.out.println(sls.intersect(sls1).toString());


        for(Integer i : sls){
            System.out.println(i);
        }
    }

}
