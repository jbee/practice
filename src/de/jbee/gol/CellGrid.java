package de.jbee.gol;

public interface CellGrid {

	void evolute();
	
	void visitAll(CellVisitor visitor);
	
}
