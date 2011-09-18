package de.jbee.gol;

import java.util.Iterator;

public interface CellEvolution {

	void eval(Cell cell, Iterator<Cell> surroundingCells, CellTransition transition);
}
