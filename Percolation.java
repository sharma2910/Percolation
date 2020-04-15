import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] opened;
    private int size;
    private int top = 0;
    private int bottom ;
    private int openSites = 0;
    private WeightedQuickUnionUF quf;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
        if(n <= 0 ){
            throw new IllegalArgumentException("n should be grater than 0");
        }
        size = n;
        quf = new WeightedQuickUnionUF((size*size)+2);
        bottom = size*size + 1;
        opened = new boolean[size][size];
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        if(row <= 0 || row > size || col <= 0 || col > size){
            throw new IllegalArgumentException("row or col not in range");
        }
        if(!isOpen(row,col)){
            opened[row-1][col-1] = true;
            openSites++;

            if(row == 1){
                quf.union(top,getQFIndex(row,col));
            }
            if(row == size){
                quf.union(bottom,getQFIndex(row,col));
            }
            if(col > 1 && isOpen(row,col-1)){
                quf.union(getQFIndex(row,col),getQFIndex(row,col-1));
            }
            if(col < size && isOpen(row,col+1)){
                quf.union(getQFIndex(row,col),getQFIndex(row,col+1));
            }
            if(row > 1 && isOpen(row-1,col)){
                quf.union(getQFIndex(row,col),getQFIndex(row-1,col));
            }
            if(row < size && isOpen(row+1,col)){
                quf.union(getQFIndex(row,col),getQFIndex(row+1,col));
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        if(row <= 0 || row > size || col <= 0 || col > size){
            throw new IllegalArgumentException("row or col not in range");
        }
        return opened[row-1][col-1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        if(row <= 0 || row > size || col <= 0 || col > size){
            throw new IllegalArgumentException("row or col not in range");
        }
        if(row > 0 && row <=size && col > 0 && col <=size ){
            return quf.connected(top, getQFIndex(row,col));
        }else{
            throw new IndexOutOfBoundsException();
        }
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        return openSites;
    }

    // does the system percolate?
    public boolean percolates(){
        return quf.connected(top,bottom);
    }

    private int getQFIndex(int i,int j){
        return size * (i-1) + j;
    }

}
