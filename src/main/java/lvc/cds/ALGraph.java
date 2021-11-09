package lvc.cds;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class ALGraph {
    private Vertex[] graph;
    private boolean isDirected;

    public ALGraph(int numVertices) {
        this(numVertices, true);
    }

    public ALGraph(int numVertices, boolean isDirected) {
        graph = new Vertex[numVertices];
        for (int i = 0; i < graph.length; ++i) {
            graph[i] = new Vertex();
        }
        this.isDirected = isDirected;
    }

    public void addEdge(int i, int j) {
        addEdge(i, j, 1);
    }

    public void addEdge(int i, int j, int w) {
        if (!vertexCheck(i) || !vertexCheck(j))
            throw new ArrayIndexOutOfBoundsException();
        if (i == j || hasEdge(i, j))
            return;
        graph[i].edges.add(new Edge(j, w));

         if (!isDirected) {
            graph[j].edges.add(new Edge(i));
        }
    }

    /**
     * breadth first search
     */
    public int[] bfs(int start) {
        Queue<Integer> queue = new ArrayDeque<>();
        int[] distance = new int[graph.length];
        Arrays.fill(distance, -1);

        // prime our data structures
        queue.add(start);
        distance[start] = 0;
        int idx = 0;

        while (!queue.isEmpty()) {
            boolean changed = false;
            idx++;
            int cur = queue.poll();
            for (Edge e : graph[cur].edges) {
                if (distance[e.target] == -1) {
                    distance[e.target] = idx;
                    queue.add(e.target);
                    changed = true;
                }
            }
            if(!changed) {
                idx--;
            }
        }
        return distance;
    }


    /**
     * depth first search
     */
    public boolean[] dfs(int start) {
        boolean[] reachable = new boolean[graph.length];
        reachable[start] = true;
        dfs(start, reachable);
        return reachable;
    }

    private void dfs(int start, boolean[] reachable) {
        for (Edge e : graph[start].edges) {
            if (!reachable[e.target]) {
                reachable[e.target] = true;
                dfs(e.target, reachable);
            }
        }
    }



    /**
     * minimal spanning tree
     */
    public ALGraph mst() {
        // pre-conditions: this is a connected, undirected graph
        ALGraph tree = new ALGraph(graph.length);
        boolean[] inTree = new boolean[graph.length];
    
        // start with vertex 0
        inTree[0] = true;
        // add n-1 edges
        for (int i=0; i<graph.length-1; ++i) {
            // find the next edge to add
            int min = -1;
            int bestSource = 0, bestTarget = 0;
            for (int j=0; j<graph.length; ++j) {
                if (inTree[j]) {
                    for (Edge e : graph[j].edges) {
                        if (!inTree[e.target] && (min == -1 || e.weight < min)) {
                            min = e.weight;
                            bestSource = j;
                            bestTarget = e.target;
                        }
                    }
                }
            }
            tree.addEdge(bestSource, bestTarget, min);
            inTree[bestTarget] = true;
        }
        return tree;
    }



    /**
     * vertex object to use in Dijkstra's algorithm
     */
    private static class DijVert {
        int value;
        double bestweight;

        DijVert(int value, double weight) {
            this.value = value;
            this.bestweight = weight;
        }
    }


     /**
      * Dijkstra's algorthm
      */
    public double[] dijkstra(int start) {
        boolean[] known = new boolean[graph.length];
        double[] bestweights = new double[graph.length];

        PriorityQueue<DijVert> frontier = new PriorityQueue<>();
        Arrays.fill(bestweights, -1);

        frontier.add(new DijVert(start, 0));

        while (!frontier.isEmpty()) {
            DijVert cur = frontier.poll();
            known[cur.value] = true;
            for (Edge e: graph[cur.value].edges) {
                if (!known[e.target]) {
                    DijVert temp = new DijVert(e.target, e.weight);
                    // calculate best weight
                }

            }
        }
        return bestweights;
    }


    public double diameter() {
        double max = 0.0;
        for (int i = 0; i < graph.length; i++) {
            double[] distances = dijkstra(i);
            for (int j = 0; j < distances.length; j++) {
                if (distances[j] > max) {
                    max = distances[j];
                }else if (distances[j] == -1.0) {
                    return -1.0;
                }
            }
        }
        return max;
    }



    /**
     * Topological sort
     */
    public List<Integer> topSort() {
        List<Integer> sorted = new ArrayList<Integer>();
        int[] inDegrees = inDegree();
        for (int i = 0; i < graph.length; i++) {
            if (inDegrees[i] == 0) {
                sorted.add(i);
                inDegrees[i]--; // sets this to -1 so we don't visit again
                for (Edge e: graph[i].edges) {
                    inDegrees[e.target]--;
                }
            }
        }
        return sorted;
    }


    
    public int[] inDegree() {
        int[] inEdges = new int[graph.length];
        Arrays.fill(inEdges, 0);
        for (int i = 0; i < graph.length; i++) {
            for (Edge e: graph[i].edges) {
                inEdges[e.target] = inEdges[e.target]++;
            }
        }
        return inEdges;
    }

    
    private boolean vertexCheck(int i) {
        return i >= 0 && i < graph.length;
    }

    private boolean hasEdge(int i, int j) {
        ArrayList<Edge> edges = graph[i].edges;
        for (Edge e : edges) {
            if (e.target == j) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        return graph.length;
    }


    private static class Vertex {
        ArrayList<Edge> edges;

        Vertex() {
            edges = new ArrayList<>();
        }
    }



    private static class Edge {
        int target;
        int weight;

        Edge(int target, int weight) {
            this.target = target;
            this.weight = weight;
        }

        Edge(int target) {
            this.target = target;
            this.weight = 1;
        }
    }

        
}
