package standard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import toolbox.Coord;
import toolbox.Options;
import toolbox.Tools;

import framework.Area;
import framework.Board;
import framework.Tile;

public class StandardBoard implements Board {

	private Map<Enum<Areas>, List<Area>> areaMap;
	private Tile[][] tiles;

	public StandardBoard(String setup) {

		setup(Tools.StringToIntArray(setup));

	}

	public StandardBoard(int[] setup) {
		setup(setup);
	}

	private void setup(int[] setup) {
		areaMap = new LinkedHashMap<Enum<Areas>, List<Area>>();

		List<Area> boxes = new LinkedList<Area>();
		List<Area> hlists = new LinkedList<Area>();
		List<Area> vlists = new LinkedList<Area>();
		List<Area> board = new LinkedList<Area>();
		List<Area> zero = new LinkedList<Area>();

		int side_boxcount = (DIMENSION / TILES_PER_BOX); // 3 when DIMENSION =
		// 9;
		int total_boxcount = side_boxcount * side_boxcount; // 9 when DIMENSION
		// = 9;
		tiles = new Tile[DIMENSION][DIMENSION];

		/*
		 * instanciate and add all needed Areas to lists
		 */
		for (int i = 0; i < total_boxcount; i++) {
			boxes.add(new StandardArea());
		}
		for (int i = 1; i <= DIMENSION; i++) {
			hlists.add(new StandardArea());
			vlists.add(new StandardArea());
		}
		int h = 0;
		int v = 0;
		for (Integer value : setup) {
			/*
			 * prepare the double array with Tiles and hlist, vlist and boxes
			 * with Coord objects
			 */
			Coord currentCoord = new Coord(v, h);
			tiles[v][h] = new StandardTile(currentCoord, value);
			hlists.get(h).addCoord(currentCoord);
			vlists.get(v).addCoord(currentCoord);

			// the index to the box this iteration has reached
			int currentboxnumber = (h / TILES_PER_BOX * side_boxcount + (v
					/ TILES_PER_BOX + 1));
			int currentboxindex = currentboxnumber - 1;
			boxes.get(currentboxindex).addCoord(new Coord(v, h));

			// update coords
			v++;
			if (v == DIMENSION) {
				v = 0;
				h++;
			}
		}
		Area boardArea = new StandardArea();
		for (Tile[] tilearray : tiles) {
			for (Tile tile : tilearray) {
				boardArea.addCoord(tile.getCoordinate());
			}
		}
		board.add(boardArea);

		Area zeroarea = new StandardArea();
		zeroarea.addCoord(new Coord(0, 0));
		zero.add(zeroarea);

		areaMap.put(Areas.BOX, boxes);
		areaMap.put(Areas.ROW, hlists);
		areaMap.put(Areas.COL, vlists);
		areaMap.put(Areas.BOARD, board);
		areaMap.put(Areas.ZERO, zero);
	}

	@Override
	public Board getBoard() {
		return this;
	}

	@Override
	public Area getAreaAt(Enum<Areas> area, Coord c) {
		for (Area aarea : areaMap.get(area)) {
			if (aarea.getCoords().contains(c)) {
				return aarea;
			}
		}
		return null;
	}

	@Override
	public Tile getTileAt(Coord c) {
		return tiles[c.getV()][c.getH()];
	}

	@Override
	public Tile[][] getTiles() {
		return tiles;
	}

	@Override
	public void setTiles(Tile[][] tiles) {
		this.tiles = tiles;
	}

	@Override
	public List<Options> getAreaOptionsAt(Enum<Areas> area, Coord c) {
		List<Options> options = new ArrayList<Options>();
		for (Coord coord : getAreaAt(area, c).getCoords()) {
			options.add(getTileAt(coord).getOptions().clone());
		}
		return options;
	}

	@Override
	public boolean validMove() {

		List<Board.Areas> areas_of_interest = new ArrayList<Areas>();
		areas_of_interest.add(Board.Areas.ROW);
		areas_of_interest.add(Board.Areas.COL);
		areas_of_interest.add(Board.Areas.BOX);
		/*
		 * for all areas defined in Board.Areas
		 */
		for (Enum<Board.Areas> area : areas_of_interest) {
			/*
			 * improves performance based on what area we are looking at.
			 */
			int vcondition = Board.DIMENSION;
			int hcondition = Board.DIMENSION;
			int inc = 1;
			if (area.equals(Board.Areas.ROW)) {
				vcondition = 1;
			}
			if (area.equals(Board.Areas.COL)) {
				hcondition = 1;
			}
			if (area.equals(Board.Areas.BOX)) {
				inc = Board.TILES_PER_BOX;
			}
			/*
			 * run over all possible Coords
			 */
			for (int h = 0; h < hcondition; h += inc) {
				for (int v = 0; v < vcondition; v += inc) {
					Coord current_coord = new Coord(v, h);

					Options optionschecklist = new Options();
					List<Integer> valuechecklist = new ArrayList<Integer>();
					for (Coord rowcoord : getAreaAt(area, current_coord)
							.getCoords()) {
						Tile tile = getTileAt(rowcoord);
						if (tile.getValue() != 0) {
							if (!valuechecklist.contains(tile.getValue())) {
								valuechecklist.add(tile.getValue());
								optionschecklist.removeOption(tile.getValue());
							} else {
								// System.out
								// .println("INVALID MOVE - 2X VALUES IN SAME HROW - VALUE "
								// + tile.getValue() + " AT " + rowcoord);
								return false;
							}
						}
						Options to = tile.getOptions();
						optionschecklist.removeOptions(to.copyList());
					}
					if (!optionschecklist.copyList().isEmpty()) {
						// System.out
						// .println("INVALID MOVE - THESE OPTIONS ARE NO LONGER REACHABLE: "
						// + optionschecklist + " AT " + bc);
						return false;
					}

				}
			}
		}

		return true;

	}

	@Override
	public boolean removeOptionsAtCoords(int[] options, Collection<Coord> coords) {
		boolean removedany = false;
		for (Coord coord : coords) {
			for (Integer option : options) {
				Tile tile = getTileAt(coord);
				if (tile.removeOption(option)) {
					removedany = true;
				}
			}
		}
		return removedany;
	}

	@Override
	public boolean removeOptionAtCoord(int option, Coord coord) {
		return getTileAt(coord).removeOption(option);
	}

}
