/**
 * 
 */
package de.jbee.gol;

import java.util.Iterator;

final class SourroundingCellsIterator implements Iterator<Cell> {
	
	private final int x;
	private final int y;
	private final CellGeneration cells;
	
	private int xOffset = -1;
	private int yOffset = -1;
	
	SourroundingCellsIterator(int x, int y, CellGeneration cells) {
		super();
		this.x = x;
		this.y = y;
		this.cells = cells;
	}

	@Override
	public boolean hasNext() {
		return xOffset <= 1 && yOffset <= 1;
	}

	@Override
	public Cell next() {
		Cell res = cells.at(x+xOffset, y+yOffset);
		++xOffset;
		if (xOffset > 1) {
			xOffset = -1;
			++yOffset;
		}
		if (xOffset == 0 && yOffset == 0) {
			++xOffset;
		}
		return res;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("No change possible!");
	}
}