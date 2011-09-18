package de.jbee.gol;

import java.util.Random;


public class SimpleCellGrid implements CellGrid {

	private CellGeneration current;
	private CellGeneration emerging;
	private CellEvolution evolution;
	
	public SimpleCellGrid(int width, int height) {
		current = new CellGeneration(width, height, Cell.DEAD);
		emerging = new CellGeneration(width, height, null);
		evolution = new NormalCellEvolution();
	}
	
	@Override
	public void evolute() {
		current.visitAll(new CellVisitor() {
			@Override
			public void visit(Cell cell, final int x, final int y) {
				evolution.eval(cell, new SourroundingCellsIterator(x, y, current), new CellTransition() {
					
					@Override
					public void become(Cell cell) {
						emerging.setCell(x, y, cell);
					}
				});
			}
		}, 1);
		swapGenerations();
	}
	
	public void visitAll(CellVisitor visitor) {
		current.visitAll(visitor);
	}
		
	private void swapGenerations() {
		CellGeneration tmp = current;
		current = emerging;
		emerging = tmp; 
	}
	
	public void placePentadecathlon() {
		for (int i = 195; i < 205; i++) {
			current.setCell(i, 200, Cell.LIVING);
		}
	}
	
	public void placeGlider() {
		int xOffset = 200;
		int yOffset = 200;
		current.setCell(xOffset, yOffset, Cell.LIVING);
		current.setCell(xOffset, yOffset+1, Cell.LIVING);
		current.setCell(xOffset+1, yOffset+1, Cell.LIVING);
		current.setCell(xOffset+1, yOffset+2, Cell.LIVING);
		current.setCell(xOffset-1, yOffset+2, Cell.LIVING);
	}
	
	public void placeOscillator() {
		int xOffset = 100;
		int yOffset = 100;
		for (int y = 0; y < 2; y++) {
			for (int x = 0; x < 3; x++) {
				current.setCell(xOffset + x + y, yOffset + y, Cell.LIVING);
			}
		}
	}
	
	public void placeRandom() {
		Random rnd = new Random();
		int cellCount = current.cellCount() / 2;
		for (int i = 0; i < cellCount; i++) {
			current.setCell(rnd.nextInt(399)+1, rnd.nextInt(399)+1, Cell.LIVING);
		}
	}
	

}
