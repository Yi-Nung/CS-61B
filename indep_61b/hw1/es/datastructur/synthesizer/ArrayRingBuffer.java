package es.datastructur.synthesizer;
import java.util.Iterator;

public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        fillCount = 0;
    }

    /**
     * Return size of the buffer.
     */
    @Override
    public int capacity() {
        return rb.length;
    }

    /**
     * Return number of items currently in the buffer.
     */
    @Override
    public int fillCount() {
        return fillCount;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {
        if (capacity() == fillCount()) {
            throw new RuntimeException("Ring Buffer overflow");
        }
        rb[last] = x;
        last += 1;
        if (last == capacity()) {
            last = 0;
        }
        fillCount += 1;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        if (fillCount() == 0) {
            throw new RuntimeException("Ring Buffer underflow");
        }
        T firstItem = rb[first];
        rb[first] = null;
        first += 1;
        if (first == capacity()) {
            first = 0;
        }
        fillCount -= 1;
        return firstItem;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {
        if (fillCount() == 0) {
            throw new RuntimeException("Ring Buffer underflow");
        }
        return rb[first];
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    private class ArrayRingBufferIterator implements Iterator<T> {
        private int wizPos;

        ArrayRingBufferIterator() {
            wizPos = first;
        }

        public boolean hasNext() {
            return wizPos == last;
        }

        public T next() {
            T nextItem = rb[wizPos];
            wizPos += 1;
            if (wizPos == capacity()) {
                wizPos = 0;
            }
            return nextItem;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        ArrayRingBuffer<T> other = (ArrayRingBuffer<T>) o;
        if (this.fillCount() != other.fillCount()) {
            return false;
        }
        Iterator<T> otherIter = other.iterator();
        for (T myItem : this) {
            if (!otherIter.next().equals(myItem)) {
                return false;
            }
        }
        return true;
    }

}
