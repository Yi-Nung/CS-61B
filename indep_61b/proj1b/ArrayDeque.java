public class ArrayDeque<T> implements Deque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    /**
     * Creates an empty array deque.
     */
    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    /**
     * Creates a deep copy of other.
     * @source https://www.youtube.com/watch?v=JNroRiEG7U4
     */
    public ArrayDeque(ArrayDeque other) {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 0;
        nextLast = 1;

        for (int i = 0; i < other.size(); i += 1) {
            addLast((T) other.get(i));
        }
    }

    /**
     * Resizes the array deque to the desired capacity.
     * @param cap The desired capacity.
     */
    private void resize(int cap) {
        T[] a = (T[]) new Object[cap];
        int currFirst = after(nextFirst);
        int currLast = before(nextLast);
        if (currFirst <= currLast) {
            System.arraycopy(items, currFirst, a, 0, size);
        } else {
            System.arraycopy(items, currFirst, a, 0, items.length - currFirst);
            System.arraycopy(items, 0, a, items.length - currFirst, currLast + 1);
        }
        items = a;
        nextFirst = items.length - 1;
        nextLast = size;
    }

    /**
     * Return the index before the given index.
     * @param index The given index.
     * @return The previous index.
     */
    private int before(int index) {
        if (index == 0) {
            return items.length - 1;
        } else {
            return index - 1;
        }
    }

    /**
     * Return the index after the given index.
     * @param index The given index.
     * @return The next index.
     */
    private int after(int index) {
        if (index == items.length - 1) {
            return 0;
        } else {
            return index + 1;
        }
    }

    /**
     * Adds an item of type T to the front of the array deque.
     * @param item An item of type T.
     */
    @Override
    public void addFirst(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextFirst] = item;
        nextFirst = before(nextFirst);
        size += 1;
    }

    /**
     * Adds an item of type T to the end of the array deque.
     * @param item An item of type T.
     */
    @Override
    public void addLast(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextLast] = item;
        nextLast = after(nextLast);
        size += 1;
    }

    /** Returns the number of items in the array deque. */
    @Override
    public int size() {
        return size;
    }

    /**
     * Prints the items in the array deque from first to last, separated by a space.
     * Once all the items have been printed, print out a new line.
     */
    @Override
    public void printDeque() {
        int k = 0;
        int ind = after(nextFirst);
        while (k < size) {
            System.out.print(items[ind] + " ");
            ind = after(ind);
            k += 1;
        }
        System.out.println();
    }

    /**
     * Removes and returns the item at the front of the array deque.
     * If no such item exists, returns null.
     * @return The item at the front of the array deque.
     */
    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        } else {
            int currFirst = after(nextFirst);
            T firstItem = items[currFirst];
            items[currFirst] = null;
            nextFirst = currFirst;
            size -= 1;
            if (items.length > 16 && ((float) size  / items.length) < 0.25) {
                resize(items.length / 2);
            }
            return firstItem;
        }
    }

    /**
     * Removes and returns the item at the back of the array deque.
     * If no such item exists, returns null.
     * @return The item at the back of the array deque.
     */
    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        } else {
            int currLast = before(nextLast);
            T lastItem = items[currLast];
            items[currLast] = null;
            nextLast = currLast;
            size -= 1;
            if (items.length > 16 && ((float) size / items.length) < 0.25) {
                resize(items.length / 2);
            }
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
        int pos = after(nextFirst);
        while (index > 0) {
            pos = after(pos);
            index -= 1;
        }
        return items[pos];
    }
}
