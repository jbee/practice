package de.jbee.gol.ui;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import de.jbee.gol.CellGrid;
import de.jbee.gol.SimpleCellGrid;

public class GolFrame extends JFrame {

	public static void main(String[] args) {
		GolFrame gol = new GolFrame();
		
		gol.setVisible(true);
	}
	
	GolFrame() {
		init();
	}
	
	private void init() {
		setLayout(new FlowLayout());
		final SimpleCellGrid cellGrid = new SimpleCellGrid(400, 400);
		
		cellGrid.placeRandom();
		cellGrid.placeGlider();
		cellGrid.placeOscillator();
		
		
		final CellGridCanvas cellGridCanvas = new CellGridCanvas(cellGrid);
		cellGridCanvas.setSize(800, 800);
		JButton evoluteButton = new JButton("Evolute");
		evoluteButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cellGrid.evolute();
				cellGridCanvas.repaint();
			}
		});

		JButton runButton = new JButton("Run");
		runButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				runEvolution(cellGrid, cellGridCanvas);
			}
		});
		
		add(cellGridCanvas);
		add(evoluteButton);
		add(runButton);
		
		setTitle("A Simple Form");
		setSize(1000, 1000); // The GUI dimensions
		setLocation(new Point(150, 150)); //The GUI position
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);
		setIgnoreRepaint(true);
	}
	
	void runEvolution(final CellGrid grid, final CellGridCanvas canvas) {
		Thread runner = new Thread() {
			@Override
			public void run() {
				super.run();
				long start = System.currentTimeMillis();
				for (int i = 0; i < 100; i++) {
					grid.evolute();
				}		
				System.out.print(System.currentTimeMillis()-start+" ms");
				canvas.repaint();
			}
		};
		runner.start();
	}
}
