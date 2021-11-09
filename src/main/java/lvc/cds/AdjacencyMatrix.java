package lvc.cds;

import java.util.List;
import java.util.ArrayList;

public class AdjacencyMatrix {


    private int size;
    private int edges;
    private boolean[][] matrix;


    // constructor
    public AdjacencyMatrix(int x) {
		size = x;
        edges = 0;
		matrix = new boolean[size][size];
	}



    public int numVertices() {
        return size;
    }

    public int numEdges() {
        return edges;
    }

    
    public void addEdge(int i, int j) {
        matrix[i][j] = true;
        edges++;
    }

    
    public void removeEdge(int i, int j) {
        matrix[i][j] = false;
        edges--;
    }

    
    public boolean hasEdge(int i, int j) {
        return matrix[i][j];
    }

    
    public List<Integer> outEdges(int i) {
        List<Integer> temp = new ArrayList<Integer>();
        for (int j = 0; j < size; j++) {
            if (hasEdge(i, j)) {
                temp.add(j);
            }
        }
        return temp;
    }

    
    public List<Integer> inEdges(int i) {
        List<Integer> temp = new ArrayList<Integer>();
        for (int j = 0; j < size; j++) {
            if (hasEdge(j, i)) {
                temp.add(j);
            }
        }
        return temp;
    }

    
    public int outDegree(int i) {
        List<Integer> temp = outEdges(i);
        return temp.size();
    }

    
    public int inDegree(int i) {
        List<Integer> temp = inEdges(i);
        return temp.size();
    }
    

}
