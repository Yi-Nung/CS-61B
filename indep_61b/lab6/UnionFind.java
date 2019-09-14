public class UnionFind {

    private int size;
    private int[] ds;



    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        ds = new int[n];
        for (int i = 0; i < n; i += 1) {
            ds[i] = -1;
        }
        size = n;
    }

    /* Throws an exception if v1 is not a valid index. */
    private void validate(int vertex) {
        if (vertex >= size || vertex < 0) {
            throw new IllegalArgumentException("Index not valid.");
        }
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        return -ds[find(v1)];
    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        validate(v1);
        return ds[v1];
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
        return find(v1) == find(v2);
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid 
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a 
       vertex with itself or vertices that are already connected should not 
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {
        int root1 = find(v1);
        int root2 = find(v2);
        if (root1 == root2) {
            return;
        }
        int weight1 = sizeOf(v1);
        int weight2 = sizeOf(v2);
        if (weight1 <= weight2) {
            ds[root1] = root2;
            ds[root2] -= weight1;
        } else {
            ds[root2] = root1;
            ds[root1] -= weight2;
        }
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int vertex) {
        validate(vertex);
        if (ds[vertex] < 0) {
            return vertex;
        }
        ds[vertex] = find(parent(vertex));
        return ds[vertex];
    }
}
