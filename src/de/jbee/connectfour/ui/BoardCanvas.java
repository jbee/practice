package de.jbee.connectfour.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import de.jbee.connectfour.model.Column;
import de.jbee.connectfour.model.Displayable;
import de.jbee.connectfour.model.Position;
import de.jbee.connectfour.model.Row;

public class BoardCanvas
		extends JPanel {

	private Displayable game;

	public BoardCanvas( Displayable game ) {
		this.game = game;
	}

	@Override
	public void paint( Graphics g ) {
		super.paint( g );
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
		g2d.setStroke( new BasicStroke( 2f ) );
		paintBackground( g2d );
		Dimension size = getSize();
		int padding = 10;
		int cellSize = Math.min( ( size.height - 2 * padding ) / Row.size(),
				( size.width - 2 * padding ) / Column.size() );
		int drawSize = cellSize - padding;
		boolean finished = game.isFinised();
		for ( Row row : Row.values() ) {
			for ( Column col : Column.values() ) {
				int x = padding + ( col.ordinal() * cellSize );
				int y = padding + ( ( Row.size() - row.ordinal() - 1 ) * cellSize );
				Position position = new Position( col, row );
				paintDisc( g2d, drawSize, x, y, position );
				paintVictory( g2d, drawSize, finished, x, y, position );
				paintBorder( g2d, drawSize, x, y );
			}
		}
	}

	private void paintBackground( Graphics2D g2d ) {
		g2d.setColor( Color.BLUE );
		g2d.fill( new Rectangle( getSize() ) );
	}

	private void paintBorder( Graphics2D g2d, int drawSize, int x, int y ) {
		g2d.setColor( Color.BLACK );
		g2d.drawOval( x, y, drawSize, drawSize );
	}

	private void paintVictory( Graphics2D g2d, int drawSize, boolean finished, int x, int y,
			Position position ) {
		if ( finished && game.victoriousBoard().taken( position ) ) {
			g2d.setColor( Color.GREEN );
			int drawSize4 = drawSize / 4;
			g2d.fillOval( x + drawSize4, y + drawSize4, drawSize - 2 * drawSize4, drawSize - 2
					* drawSize4 );
		}
	}

	private void paintDisc( Graphics2D g2d, int drawSize, int x, int y, Position position ) {
		if ( game.attackerBoard().taken( position ) ) {
			g2d.setColor( Color.RED );
		} else if ( game.defenderBoard().taken( position ) ) {
			g2d.setColor( Color.YELLOW );
		} else {
			g2d.setColor( Color.WHITE );
		}
		g2d.fillOval( x, y, drawSize, drawSize );
	}

	public void setGame( Displayable game ) {
		this.game = game;
	}

}
