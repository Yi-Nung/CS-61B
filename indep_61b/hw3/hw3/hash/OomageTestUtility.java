package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /* Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        int[] buckets = new int[M];
        int N = oomages.size();
        for (int i = 0; i < N; i += 1) {
            Oomage o = oomages.get(i);
            int bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
            buckets[bucketNum] += 1;
        }
        for (int j = 0; j < M; j += 1) {
            if (buckets[j] < N / 50) {
                return false;
            }
            if (buckets[j] > N / 2.5) {
                return false;
            }
        }
        return true;
    }
}
