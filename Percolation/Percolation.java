import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF quickU; // models the grid
    private int n;
    private int[] openSites; // 0 is closed, 1 is open
    
    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("size n out of bounds");
        }
        
        this.n = n;
        int size = n * n + 2; // leave room for virtual top and bottom
        this.quickU = new WeightedQuickUnionUF(size + 2);
        this.openSites = new int[n * n];
        
        
    }
    
    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > n) {
            throw new IllegalArgumentException("row index out of bounds");
        }
        if (col < 1 || col > n) {
                throw new IllegalArgumentException("col index out of bounds");
        }
        
        int id = getID(row, col);
        
        if (this.openSites[id - 1] == 0) {
            if (row == 1) {
                // top row, union with virtual top
                this.quickU.union(id, 0);
            }
            // get neighbors
            int[] neighbors = getNeighbors(row, col);
            for (int i = 0; i < neighbors.size; i++) {
                if(isOpen(neighbors[i])) {
                    // neighbor is open
                    this.quickU.union(neighbors[i]);
                }
            }
            openSites[id - 1] = 1;
        }
    }
    
    
    
    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > n) {
            throw new IllegalArgumentException("row index out of bounds");
        }
        if (col < 1 || col > n) {
                throw new IllegalArgumentException("col index out of bounds");
        }
        return this.openSites[getID(row, col) - 1];
    }
    
    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > n) {
            throw new IllegalArgumentException("row index out of bounds");
        }
        if (col < 1 || col > n) {
                throw new IllegalArgumentException("col index out of bounds");
        }
        
        return this.quickU.connected(0, getID(row, col));
    }
    
    // does the system percolate?
    public boolean percolates() {
        return this.quickU.connected(0, this.n * this.n + 1);
    }
    
    // [N, E, S, W]
    private int[] getNeighbors(int row, int col) {
        int[] neighbors = new int[4];
        
        // north neighbor
        if (row == 1) {
            neighbors[0] = -1;
        } else {
            neighbors[0] = getID(row - 1, col);
        }
        
        // south neighbor
        if (row == n) {
            neighbors[2] = -1;
        } else {
            neighbors[2] = getID(row + 1, col);
        }
        
        // east neighbor
        if (col == n) {
            neighbors[1] = -1;
        } else {
            neighbors[1] = getID(row, col + 1)
        }
        
        // west neighbor
        if (col == 1) {
            neighbors[3] = -1;
        } else {
            neighbors[3] = getID(row, col - 1);
        }
        
        return neighbors;
    }
    
    private int getID(int row, int col) {
        return row * this.n + col;    
    }
    
    // test client (optional)
    public static void main(String[] args) {}
    
}