import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MyHashMap<K, V>  implements Map61B<K, V>  {

    private int size = 0;
    private int M;
    private double loadFactor;
    private ArrayList<Entry>[] mp;
    private HashSet<K> keys;


    private class Entry {
        private K key;
        private V val;
        private Entry(K k, V v) {
            key = k;
            val = v;
        }
    }

    private int hash(K key) {
        return Math.floorMod(key.hashCode(), M);
    }

    public MyHashMap() {
        this(16, 0.75);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, 0.75);
    }

    public MyHashMap(int initialSize, double loadFactor) {
        M = initialSize;
        this.loadFactor = loadFactor;
        mp = new ArrayList[M];
        keys = new HashSet<>();
    }

    private Entry getEntry(K key) {
        int bucketnum = hash(key);
        ArrayList<Entry> entries = mp[bucketnum];
        if (entries != null) {
            for (Entry ent: entries) {
                if (ent.key.equals(key)) {
                    return ent;
                }
            }
        }
        return null;
    }

    private void resize(int newM) {
        MyHashMap temp = new MyHashMap(newM, loadFactor);
        for (K key: keySet()) {
            temp.put(key, this.get(key));
        }
        this.size = temp.size;
        this.M = temp.M;
        this.mp = temp.mp;
    }

    @Override
    /** Removes all of the mappings from this map. */
    public void clear() {
        size = 0;
        mp = new ArrayList[M];
        keys = new HashSet<>();
    }


    /** Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        Entry ent = getEntry(key);
        if (ent == null) {
            return null;
        }
        return ent.val;
    }

    /** Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     */
    @Override
    public void put(K key, V value) {
        Entry ent = getEntry(key);
        if (ent != null) {
            ent.val = value;
            return;
        }
        if ((double) size / M > loadFactor) {
            resize(M * 2);
        }
        int bucketnum = hash(key);
        if (mp[bucketnum] == null) {
            mp[bucketnum] = new ArrayList<Entry>();
        }
        mp[bucketnum].add(new Entry(key, value));
        keys.add(key);
        size += 1;
    }

    @Override
    public Iterator<K> iterator() {
        return keys.iterator();
    }

    /** Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        return keys;
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     */
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }
}
