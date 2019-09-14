import edu.princeton.cs.algs4.Queue;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestSortAlgs {

    @Test
    public void testQuickSort() {
        Queue<String> testQueue = new Queue<>();
        assertTrue(isSorted(QuickSort.quickSort(testQueue)));
        testQueue.enqueue("b");
        testQueue.enqueue("d");
        testQueue.enqueue("c");
        testQueue.enqueue("d");
        testQueue.enqueue("c");
        testQueue.enqueue("a");
        Queue<String> sortedQueue = QuickSort.quickSort(testQueue);
        assertTrue(isSorted(sortedQueue));
    }

    @Test
    public void testMergeSort() {
        Queue<String> testQueue = new Queue<>();
        assertTrue(isSorted(MergeSort.mergeSort(testQueue)));
        testQueue.enqueue("b");
        testQueue.enqueue("d");
        testQueue.enqueue("c");
        testQueue.enqueue("a");
        testQueue.enqueue("d");
        testQueue.enqueue("c");
        testQueue.enqueue("a");
        Queue<String> sortedQueue = MergeSort.mergeSort(testQueue);
        assertTrue(isSorted(sortedQueue));
    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
