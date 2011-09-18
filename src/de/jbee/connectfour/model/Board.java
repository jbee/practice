package de.jbee.connectfour.model;

import java.util.ArrayList;
import java.util.List;

public final class Board {

	//  col 0123456
	// 5	---x---
	// 4	---x---
	// 3	x-x----
	// 2	-x-----
	// 1	x------
	// 0	-------

	public static final Board EMPTY = new Board( 0L );

	public static final int VICTORIOUS_LENGTH = 4;

	@SuppressWarnings ( "unchecked" )
	private static final List<Board>[][] VICTORIOUS = new List[Column.size()][Row.size()];

	private static final Column[] COLUMNS = Column.values();
	private static final Row[] ROWS = Row.values();

	static {
		for ( Column col : COLUMNS ) {
			for ( Row row : ROWS ) {
				VICTORIOUS[col.ordinal()][row.ordinal()] = new ArrayList<Board>( 5 );
			}
		}
		// vertical
		for ( Column col : COLUMNS ) {
			for ( int row = Row.size() - 1; row >= Row.size() - VICTORIOUS_LENGTH; row-- ) {
				Row r = ROWS[row];
				addVictorious( col, r, verticalLine( new Position( col, r ), VICTORIOUS_LENGTH ) );
			}
		}
		// horizontal
		for ( Column col : COLUMNS ) {
			for ( Row row : ROWS ) {
				int endCol = Column.size() - VICTORIOUS_LENGTH;
				for ( int startCol = Math.max( 0, col.ordinal() - VICTORIOUS_LENGTH + 1 ); startCol <= endCol; startCol++ ) {
					addVictorious( col, row, horizontalLine( new Position( COLUMNS[startCol], row ),
							VICTORIOUS_LENGTH ) );
				}
			}
		}
		// upwards
		for ( Column col : COLUMNS ) {
			for ( Row row : ROWS ) {
				int startCol = col.ordinal();
				int startRow = row.ordinal();
				int l = VICTORIOUS_LENGTH;
				while ( l > 1 && startCol > 0 && startRow > 0 ) {
					startCol--;
					startRow--;
					l--;
				}
				while ( startCol <= col.ordinal()
						&& startRow <= row.ordinal() //
						&& startCol + VICTORIOUS_LENGTH <= Column.size()
						&& startRow + VICTORIOUS_LENGTH <= Row.size() ) {
					Position start = new Position( COLUMNS[startCol++], ROWS[startRow++] );
					addVictorious( col, row, diagonalUpwardsLine( start, VICTORIOUS_LENGTH ) );
				}
			}
		}
		// downwards
		for ( Column col : COLUMNS ) {
			for ( Row row : ROWS ) {
				int startCol = col.ordinal();
				int startRow = row.ordinal();
				int l = VICTORIOUS_LENGTH;
				while ( l > 1 && startCol > 0 && startRow < Row.size() - 1 ) {
					startCol--;
					startRow++;
					l--;
				}
				while ( startCol <= col.ordinal()
						&& startRow >= row.ordinal() // 
						&& startCol + VICTORIOUS_LENGTH <= Column.size()
						&& startRow - VICTORIOUS_LENGTH + 1 >= 0 ) {
					Position start = new Position( COLUMNS[startCol++], ROWS[startRow--] );
					addVictorious( col, row, diagonalDownwardsLine( start, VICTORIOUS_LENGTH ) );
				}
			}
		}
	}

	private static void addVictorious( Column col, Row row, long board ) {
		VICTORIOUS[col.ordinal()][row.ordinal()].add( new Board( board ) );
	}

	private static long diagonalDownwardsLine( Position start, int length ) {
		long res = 0L;
		for ( int i = 0; i < length; i++ ) {
			res |= bitAt( COLUMNS[start.column.ordinal() + i], ROWS[start.row.ordinal() - i] );
		}
		return res;
	}

	private static long diagonalUpwardsLine( Position start, int length ) {
		long res = 0L;
		for ( int i = 0; i < length; i++ ) {
			res |= bitAt( COLUMNS[start.column.ordinal() + i], ROWS[start.row.ordinal() + i] );
		}
		return res;
	}

	private static long verticalLine( Position start, int length ) {
		long res = 0L;
		for ( int row = start.row.ordinal(); row > start.row.ordinal() - length && row >= 0; row-- ) {
			res |= bitAt( start.column, ROWS[row] );
		}
		return res;
	}

	private static long horizontalLine( Position start, int length ) {
		long res = 0L;
		for ( int col = start.column.ordinal(); col < start.column.ordinal() + length; col++ ) {
			res |= bitAt( COLUMNS[col], start.row );
		}
		return res;
	}

	public static List<Board> victoryConstellations( Position position ) {
		return VICTORIOUS[position.column.ordinal()][position.row.ordinal()];
	}

	private final long data;

	private Board( long data ) {
		this.data = data;
	}

	private Board withPlaced( long data ) {
		return new Board( this.data | data );
	}

	public boolean taken( Position position ) {
		return ( discAt( position ) & data ) > 0;
	}

	public Board victorious( Position move ) {
		List<Board> victorious = victoryConstellations( move );
		for ( Board b : victorious ) {
			long v = b.data;
			if ( ( v & data ) == v ) {
				return b;
			}
		}
		return EMPTY;
	}

	public Board merge( Board other ) {
		return withPlaced( other.data );
	}

	public boolean isEmpty() {
		return data == 0L;
	}

	public Board place( Position position ) {
		return withPlaced( discAt( position ) );
	}

	public Row playableRowInColumn( Column column ) {
		long bit = bitAt( columnIndex( column ) );
		for ( int i = 0; i < Row.size(); i++ ) {
			long mask = ~bit;
			if ( ( data | mask ) == mask ) {
				return ROWS[i];
			}
			bit <<= 1;
		}
		return null;
	}

	public int placedDiscs() {
		return Long.bitCount( data );
	}

	private static long discAt( Position position ) {
		return bitAt( position.column, position.row );
	}

	private static int columnIndex( Column column ) {
		return column.ordinal() << 3; // = * 8 
	}

	private static long bitAt( Column column, Row row ) {
		return bitAt( columnIndex( column ) + row.ordinal() );
	}

	private static long bitAt( int index ) {
		return 1L << index;
	}

	public static Position victoriousMove( Board gameBoard, Board playerBoard ) {
		if ( playerBoard.placedDiscs() < VICTORIOUS_LENGTH ) {
			return null; // impossible to win with less than 4 discs
		}
		for ( Column col : COLUMNS ) {
			Row row = gameBoard.playableRowInColumn( col );
			if ( row != null ) {
				Position move = new Position( col, row );
				Board victorious = playerBoard.place( move ).victorious( move );
				if ( !victorious.isEmpty() ) {
					return move;
				}
			}
		}
		return null;
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder( Row.size() * Column.size() );
		for ( int row = Row.size() - 1; row >= 0; row-- ) {
			for ( Column col : COLUMNS ) {
				b.append( taken( new Position( col, ROWS[row] ) )
					? 'o'
					: '+' );
			}
			b.append( '\n' );
		}
		return b.toString();
	}

}
