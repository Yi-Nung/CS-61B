public class LinkedListDeque<T> implements Deque<T> {
    private class SomeNode {
        private T item;
        private SomeNode next;
        private SomeNode prev;

        SomeNode(T i, SomeNode p, SomeNode n) {
            item = i;
            prev = p;
            next = n;
        }
    }

    private SomeNode sentinel;
    private int size;

    /**
     * Creates an empty linked list deque.
     */
    public LinkedListDeque() {
        sentinel = new SomeNode(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    /**
     * Creates a deep copy of other.
     * @source https://www.youtube.com/watch?v=JNroRiEG7U4
     */
    public LinkedListDeque(LinkedListDeque other) {
        sentinel = new SomeNode(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;

        for (int i = 0; i < other.size(); i += 1) {
            addLast((T) other.get(i));
        }
    }

    /**
     * Adds an item of type T to the front of the deque.
     * @param item An item of type T.
     */
    @Override
    public void addFirst(T item) {
        sentinel.next = new SomeNode(item, sentinel, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size += 1;
    }

    /**
     * Adds an item of type T to the end of the deque.
     * @param item An item of type T.
     */
    @Override
    public void addLast(T item) {
        sentinel.prev = new SomeNode(item, sentinel.prev, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        size += 1;
    }

    /** Returns the number of items in the deque. */
    @Override
    public int size() {
        return size;
    }

    /**
     * Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, print out a new line.
     */
    @Override
    public void printDeque() {
        SomeNode p = sentinel;
        int k = 0;
        while (k < size) {
            System.out.print(p.next.item + " ");
            p = p.next;
            k += 1;
        }
        System.out.println();
    }

    /**
     * Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     * @return The item at the front of the deque.
     */
    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        } else {
            size -= 1;
            T firstItem = sentinel.next.item;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            return firstItem;
        }
    }

    /**
     * Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.
     * @return The item at the back of the deque.
     */
    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        } else {
            size -= 1;
            T lastItem = sentinel.prev.item;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            return lastItem;
        }
    }

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque!
     * @param index The index of the item.
     * @return The item.
     */
    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        SomeNode p = sentinel.next;
        while (index > 0) {
            p = p.next;
            index -= 1;
        }
        return p.item;
    }

    /**
     * Helper function for getRecursive.
     * @param i The index of the item.
     * @param node The current node.
     * @return The item.
     */
    private T getRecursiveHelper(int i, SomeNode node) {
        if (i == 0) {
            return node.item;
        } else {
            return getRecursiveHelper(i - 1, node.next);
        }
    }

    /**
     * Same as get, but uses recursion.
     * @param index The index of the item.
     * @return The item.
     */
    public T getRecursive(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        return getRecursiveHelper(index, sentinel.next);
    }
}
