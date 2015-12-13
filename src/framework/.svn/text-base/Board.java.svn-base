package framework;

import java.util.Collection;
import java.util.List;

import toolbox.Coord;
import toolbox.Options;

public interface Board {

	static final int DIMENSION = 9;
	static final int TILES_PER_BOX = 3;
	static enum Areas {BOX, ROW, COL, BOARD, ZERO};
	
	Board getBoard();
	Tile getTileAt(Coord c);
	Area getAreaAt(Enum<Areas> area, Coord c);
	Tile[][] getTiles();
	boolean removeOptionAtCoord(int option, Coord coord);
	boolean removeOptionsAtCoords(int[] option, Collection<Coord> coords);
	void setTiles(Tile[][] tiles);
	List<Options> getAreaOptionsAt(Enum<Areas> area, Coord c);
	boolean validMove();
}
