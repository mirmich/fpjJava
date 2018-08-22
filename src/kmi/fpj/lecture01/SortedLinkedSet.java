package kmi.fpj.lecture01;

import java.io.PrintStream;
import java.util.Iterator;

import java.util.NoSuchElementException;

public class SortedLinkedSet<T extends Comparable<T>> implements Iterable<T>  {

    private final ListNode<T> firstNode;

    public SortedLinkedSet(ListNode<T> node){
        this.firstNode = node;
    }

    public SortedLinkedSet(){
        firstNode = null;
    }
    public SortedLinkedSet<T> add(T value){
        ListNode<T> newFirstNode;
        if(firstNode == null) newFirstNode = new ListNode<>(value);
        else newFirstNode = firstNode.add(value);
        return new SortedLinkedSet<>(newFirstNode);
    }

    public SortedLinkedSet<T> remove(T value){
        ListNode<T> newFirstNode;
        if(contains(value)){
            //System.out.println("found");
            newFirstNode = firstNode.remove(value);
        }else{
            newFirstNode = firstNode;
        }
        return new SortedLinkedSet<>(newFirstNode);

    }

    public boolean contains(T value){
        return firstNode.contains(value);
    }

    public SortedLinkedSet<T> intersect(SortedLinkedSet<T> item){
        SortedLinkedSet<T> intersection = new SortedLinkedSet<>();
        for(T e : this){
            if(item.contains(e)){
                intersection = intersection.add(e);
            }
        }
        return intersection;
    }



    public void printOut(PrintStream out) {
        if (firstNode != null) firstNode.printOut(out);
    }

    public ListNode<T> getFirstNode() {
        return firstNode;
    }

    @Override
    public String toString() {
        return "SortedLinkedSet{" + firstNode + '}';
    }

    public int size(){
        return firstNode.size();
    }

    public T get(int index){
        return firstNode.get(index);
    }
    public Iterator<T> iterator() {
        return new ListIterator<T>();
    }

    private class ListIterator<T> implements Iterator<T> {
        int size = size();
        int currentPointer = 0;

        public boolean hasNext() {
            return (currentPointer < size);
        }

        public T next() {

            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            @SuppressWarnings("unchecked")
            T val = (T) get(currentPointer);
            currentPointer++;

            return val;
        }

    }




}