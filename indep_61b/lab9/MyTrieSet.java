import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyTrieSet implements TrieSet61B {
    private Node root;

    private class Node {
        private boolean isKey;
        private HashMap<Character, Node> map;
        private Node() {
            this.isKey = false;
            this.map = new HashMap<>();
        }
    }

    public MyTrieSet() {
        clear();
    }

    /** Clears all items out of Trie */
    public void clear() {
        root = new Node();
    }

    /** Returns true if the Trie contains KEY, false otherwise */
    public boolean contains(String key) {
        if (key == null) {
            return false;
        }
        Node curr = root;
        for (int i = 0, n = key.length(); i < n; i++) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                return false;
            }
            curr = curr.map.get(c);
        }
        return curr.isKey;
    }

    /** Inserts string KEY into Trie */
    @Override
    public void add(String key) {
        if (key == null || key.length() < 1) {
            return;
        }
        Node curr = root;
        for (int i = 0, n = key.length(); i < n; i++) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                curr.map.put(c, new Node());
            }
            curr = curr.map.get(c);
        }
        curr.isKey = true;
    }

    /** Returns a list of all words that start with PREFIX */
    public List<String> keysWithPrefix(String prefix) {
        if (prefix == null) {
            prefix = "";
        }
        Node curr = root;
        for (int i = 0; i < prefix.length(); i ++) {
            char c = prefix.charAt(i);
            if (!curr.map.containsKey(c)) {
                return new ArrayList<>();
            }
            curr = curr.map.get(c);
        }
        return collect(prefix, new ArrayList<>(), curr);
    }

    /** Take a prefix String s, and add it to all keys start with the node */
    private List<String> collect(String s, List<String> words, Node n) {
        if (n.isKey) {
            words.add(s);
        }
        for (Character c: n.map.keySet()) {
            collect(s + c, words, n.map.get(c));
        }
        return words;
    }

    /** Returns the longest prefix of KEY that exists in the Trie
     * Not required for Lab 9. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException();
    }

}
