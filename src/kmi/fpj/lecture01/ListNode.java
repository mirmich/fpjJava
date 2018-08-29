package kmi.fpj.lecture01;

import java.io.PrintStream;
import java.util.List;
import java.util.NoSuchElementException;

public class ListNode<V extends Comparable<V>> {


    private final V value;
    private final ListNode<V> nextNode;


    public ListNode(V value){
        this.value = value;
        this.nextNode = null;

    }

    public ListNode(V value, ListNode<V> nextNode){
        this.value = value;
        this.nextNode = nextNode;

    }

    public ListNode<V> add(V newValue){
        int cmp = newValue.compareTo(value);
        if(cmp == 0){
            return new ListNode<>(value,nextNode);
        }else if(cmp < 0){
            return new ListNode<>(newValue,this);
        }else{
            if(nextNode != null) return new ListNode<>(value, nextNode.add(newValue));
            else return new ListNode<>(value, new ListNode<>(newValue,null));
        }

    }

    public ListNode<V> remove(V remValue){
        int cmp = remValue.compareTo(value);
        if(cmp == 0){

            return nextNode;
        }else{
            return new ListNode<>(value,nextNode.remove(remValue));
        }

    }

    public boolean contains(V findValue){
        int cmp = findValue.compareTo(value);

        if(cmp == 0){
            return true;
        }else{
            if(nextNode != null) return nextNode.contains(findValue);
            return false;
        }

    }


    public void printOut(PrintStream out){


        out.print(value + " ");

        if (nextNode != null) nextNode.printOut(out);

    }


    public V getValue() {
        return value;
    }

    public ListNode<V> getNextNode() {
        return nextNode;
    }

    @Override
    public String toString() {
        return value + " " + nextNode;
    }

   }