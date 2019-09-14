package bearmaps;

import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayHeapMinPQTest {

    @Test
    public void addTest() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
        pq.add("a", 2);
        pq.add("b", 1);
        assertTrue(pq.contains("b") && pq.getSmallest().equals("b"));
    }
    @Test
    public void containsTest() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
        assertFalse(pq.contains("a"));
        pq.add("a", 2);
        assertTrue(pq.contains("a"));
    }

    @Test
    public void changePriorityTest() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
        pq.add("a", 1);
        pq.add("b", 3);
        pq.add("c", 2);
        pq.add("d", 1);
        pq.add("e", 6);
        pq.add("f", 4);
        pq.changePriority("a", 4);
        assertEquals(6, pq.size());
        assertTrue(pq.contains("a"));
        assertEquals("d", pq.removeSmallest());
        assertEquals("c", pq.removeSmallest());
        assertEquals("b", pq.removeSmallest());
        pq.removeSmallest();
        pq.removeSmallest();
        assertEquals("e", pq.removeSmallest());
        assertEquals(0, pq.size());
        for (int i = 0; i < 500; i += 1) {
            pq.add("a" + i, i);
        }
        assertEquals("a0", pq.getSmallest());
        for (int i = 0; i < 500; i += 1) {
            pq.changePriority("a" + i, -i);
        }
        assertEquals("a499", pq.getSmallest());
    }

    @Test
    public void getSmallestTest() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
        pq.add("a", 1.5);
        pq.add("b", 1.5);
        pq.add("c", 10);
        assertTrue(pq.getSmallest().equals("a") || pq.getSmallest().equals("b"));
    }

    @Test
    public void removeSmallestTest() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
        pq.add("a", 10);
        pq.add("b", 2);
        pq.add("c", 1);
        assertEquals("c", pq.removeSmallest());
        assertFalse(pq.contains("c"));
    }

    @Test
    public void sizeTest() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
        assertEquals(0, pq.size());
        pq.add("a", 1);
        assertEquals(1, pq.size());
        for (int i = 0; i < 500; i += 1) {
            pq.add("a" + i, i + 1);
        }
        assertEquals(501, pq.size());
    }
}
