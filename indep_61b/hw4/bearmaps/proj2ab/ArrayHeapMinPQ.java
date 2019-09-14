package bearmaps.proj2ab;

import java.util.HashMap;
import java.util.NoSuchElementException;

// @Source https://algs4.cs.princeton.edu/24pq/MinPQ.java.html

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private Node[] pq;
    private int size;
    private HashMap<T, Node> itemsNode = new HashMap<>();

    private class Node {
        private T item;
        private double priority;
        private int index;
        private Node(T item, double priority, int index) {
            this.item = item;
            this.priority = priority;
            this.index = index;
        }
    }

    public ArrayHeapMinPQ() {
        pq =  new ArrayHeapMinPQ.Node[10];
        size = 0;
    }

    // Helper functions for swap and comparison.

    private int parent(int k) {
        return k / 2;
    }

    private boolean greater(int j, int k) {
        return pq[j].priority > pq[k].priority;
    }

    private void swap(int j, int k) {
        Node nj = pq[j];
        Node nk = pq[k];
        pq[j] = nk;
        pq[k] = nj;
        nk.index = j;
        nj.index = k;
    }

    private void swim(int k) {
        while (k > 1 && greater(parent(k), k)) {
            swap(k, parent(k));
            k = parent(k);
        }
    }

    private void sink(int k) {
        while (k * 2 <= size) {
            int j = k * 2;
            if (j  < size && greater(j, j + 1)) {
                j += 1;
            }
            if (!greater(k, j)) {
                break;
            }
            swap(k, j);
            k = j;
        }
    }

    // Helper function for resize.
    private void resize(int cap) {
        Node[] newpq = new ArrayHeapMinPQ.Node[cap];
        for (int i = 1; i <= size; i += 1) {
            newpq[i] = pq[i];
        }
        pq = newpq;
    }

    /* Adds an item with the given priority value. Throws an
     * IllegalArgumentException if item is already present.
     * You may assume that item is never null. */
    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        }
        if (size == pq.length - 1) {
            resize(2 * pq.length);
        }
        size = size + 1;
        pq[size] = new Node(item, priority, size);
        itemsNode.put(item, pq[size]);
        swim(size);
    }

    /* Returns true if the PQ contains the given item. */
    @Override
    public boolean contains(T item) {
        return itemsNode.containsKey(item);
    }

    /* Returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    @Override
    public T getSmallest() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return pq[1].item;
    }

    /* Removes and returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    @Override
    public T removeSmallest() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        T min = pq[1].item;
        swap(1, size--);
        sink(1);
        pq[size + 1] = null;
        itemsNode.remove(min);
        if (size > 0 && ((float) size  / pq.length) < 0.25) {
            resize(pq.length / 2);
        }
        return min;
    }

    /* Returns the number of items in the PQ. */
    @Override
    public int size() {
        return size;
    }

    /* Changes the priority of the given item. Throws NoSuchElementException if the item
     * doesn't exist. */
    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException();
        }
        int ind = itemsNode.get(item).index;
        double original = pq[ind].priority;
        pq[ind].priority = priority;
        if (priority > original) {
            sink(ind);
        }
        if (priority < original) {
            swim(ind);
        }
    }
}
