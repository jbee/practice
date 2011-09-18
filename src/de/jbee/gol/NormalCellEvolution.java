package de.jbee.gol;

import java.util.Iterator;

public class NormalCellEvolution implements CellEvolution {

	@Override
	public void eval(Cell cell, Iterator<Cell> surroundingCells, CellTransition transition) {
		int living = 0;
		while (surroundingCells.hasNext() && living <= 3) {
			Cell surrounding = surroundingCells.next();
			if (surrounding == Cell.LIVING) {
				++living;
			}
		}
		transition.become(living == 3 || (cell == Cell.LIVING && living == 2)  ? Cell.LIVING : Cell.DEAD);
	}

}
