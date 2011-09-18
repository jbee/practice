package de.jbee.gol;

public final class CellGeneration {

	private final Cell[][] cells;
	
	public CellGeneration(int width, int height) {
		this(width, height, null);
	}
	
	public CellGeneration(int width, int height, Cell initial) {
		super();
		cells = new Cell[width][height];
		if (initial !=null) {
			initCells(initial);
		}
	}
	
	private void initCells(final Cell initial) {
		visitAll(new CellVisitor() {
			@Override
			public void visit(Cell cell, int x, int y) {
				cells[x][y] = initial;
			}
		});
	}

	public void visitAll(CellVisitor visitor) {
		visitAll(visitor, 0);
	}
	
	public void visitAll(CellVisitor visitor, int edgeGap) {
		final int xyStart = edgeGap;
		final int xEnd = cells.length - edgeGap;
		final int yEnd = cells[0].length - edgeGap;
		for (int y = xyStart; y < yEnd; y++) {
			for (int x = xyStart; x < xEnd; x++) {
				 visitor.visit(cells[x][y], x, y);
			}
		}
	}

	public void setCell(int x, int y, Cell cell) {
		cells[x][y] = cell;
	}
	
	public Cell at(int x, int y) {
		return cells[x][y];
	}

	public int cellCount() {
		return cells.length * cells[0].length;
	}
	
	
}
