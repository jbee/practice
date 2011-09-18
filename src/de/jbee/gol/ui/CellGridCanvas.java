package de.jbee.gol.ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import de.jbee.gol.Cell;
import de.jbee.gol.CellGrid;
import de.jbee.gol.CellVisitor;
import de.jbee.gol.SimpleCellGrid;

public class CellGridCanvas extends Canvas implements CellVisitor {

	private final CellGrid cellGrid;
	private Graphics2D g2d;

	public CellGridCanvas(CellGrid cellGrid) {
		this.cellGrid = cellGrid;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		this.g2d = (Graphics2D) g;
		cellGrid.visitAll(this);
	}
	
	@Override
	public void visit(Cell cell, int x, int y) {
		final int size = 2;
		g2d.setBackground(cell == Cell.LIVING ? Color.BLACK : Color.WHITE );
		g2d.setColor(g2d.getBackground());
		g2d.fillRect(x*size, y*size, size, size);
	}
}
