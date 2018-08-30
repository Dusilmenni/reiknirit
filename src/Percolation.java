import java.util.zip.CheckedInputStream;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private boolean [][] grid;
	private int gridSize;
	private int openCount;
	private WeightedQuickUnionUF wUF;
	private int top;
	private int bottom;
	
	public Percolation(int N) {
		if (N <= 0) {
			throw new IllegalArgumentException();
		}
		gridSize = N;
		grid = new boolean [N][N];
		top = 0;
		bottom = N * N + 1;
		wUF = new WeightedQuickUnionUF(N * N + 2);
		for (int row = 0; row < N; row++) {
			for (int col = 0; col < N; col++) {
				grid[col][row] = false;
			}
		}
		
	}
	
	public void open(int row, int col) {
		checkInput(row, col);
		/*
		if(checkIfOpened(row, col)) {
			return;
		}
		grid[col][row] = true;
		openCount++;	
		openwUF(row, col);
		*/
		if(!(checkIfOpened(row, col))) {
			grid[col][row] = true;
			gridSize++;
			openwUF(row, col);
		}
	}
	
	private void checkInput(int row, int col) {
		if(row < 0 || row > gridSize || col < 0 || col > gridSize) {
			throw new IndexOutOfBoundsException();
		}
	}
	
	private void openwUF(int row, int col) {
		if (!(row == 0)) {
			if(checkIfOpened(row - 1, col)) {
				wUF.union(gridToUF(row, col), gridToUF(row - 1, col));
			}	else {
				wUF.union(gridToUF(row, col), top);
			}
			
			if(!(row == gridSize-1)) {
				if (checkIfOpened(row+1, col)) {
					wUF.union(gridToUF(row, col), gridToUF(row+1, col));
				} else {
					wUF.union(gridToUF(row, col), bottom);
				}
			}
			
			if(!(col == 0)) {
				if(checkIfOpened(row, col-1)) {
					wUF.union(gridToUF(row, col), gridToUF(row, col-1));
				}
			}
			
			if(!(col == gridSize-1)) {
				if(checkIfOpened(row, col+1)) {
					wUF.union(gridToUF(row, col), gridToUF(row, col+1));
				}
			} else {
				return;
		} 
		}
	}
	
	private boolean checkIfOpened(int row, int col) {
		/* if(grid[col][row] == true) {
			return true;
		} else {
			return false;
		} 
		*/
		return grid[col][row] == true;
	}
		
	private int gridToUF(int row, int col) {
		return gridSize * col + row + 1;
	}
	
	public int numberOfOpenSites() {
		return openCount;
	}
	
	public boolean isFull(int row, int col) {
		checkInput(row, col);
		return wUF.connected(gridToUF(row, col), top);
	}
	
	public boolean isOpen(int row, int col) {
		checkInput(row, col);
		if (grid[col][row] == true) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean percolates() {
		return wUF.connected(top, bottom);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// simulateFromFile("input20.txt");

	}

}
