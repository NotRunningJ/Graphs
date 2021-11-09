package lvc.cds;

import java.util.Arrays;

public class DisjointSet {
    private int[] data;

    public DisjointSet(int sz) {
        data = new int[sz];
        Arrays.fill(data, -1);
    }

    public void connect(int u, int v) {
        int r1 = root(u);
        int r2 = root(v);

        // if they're already connected, don't do anything
        if (r1 == r2)
            return;

        // place r1 in r2's tree.
        data[r1] = r2;
    }

    public boolean areConnected(int u, int v) {
        return root(u) == root(v);
    }


    // move up our tree recursively, looking for the root.
    // along the way, apply a nifty trick called path compression
    // to speed up future root searches.
    private int root(int u) {
        if (data[u] < 0)
            return u;

        return data[u] = root(data[u]);
    }
}
