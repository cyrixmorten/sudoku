package solver.solverStrategies;

import java.util.List;

import toolbox.Coord;
import toolbox.Options;
import framework.Board;
import framework.SolverStrategy;
import framework.Tile;
import framework.Board.Areas;

public class ShowOptions implements SolverStrategy {

	private boolean optionRemoved;
	private boolean valueSet;
	private int runcount = 0;

	@Override
	public Board run(Board board, Enum<Board.Areas> area, Coord c) {
		runcount++;
		optionRemoved = false;

		List<Coord> areaCoords = board.getAreaAt(area, c).getCoords();
		for (Coord coord : areaCoords) {
			Tile tile = board.getTileAt(coord);
			int value = tile.getValue();
			if (value != 0) {
				/*
				 * go through all tiles in this area and remove 'value' from
				 * options
				 */
				Board.Areas[] areas = { Board.Areas.BOX, Board.Areas.COL,
						Board.Areas.ROW };
				
				for (Board.Areas aarea : areas) {
					for (Coord rcoord : board.getAreaAt(aarea, coord).getCoords()) {
						Tile rtile = board.getTileAt(rcoord);
						if (rtile.removeOption(value)) {
							optionRemoved = true;
						}
					}
				}
			}
		}

		return board;

	}

	@Override
	public Enum<Board.Areas>[] areas() {
		Board.Areas[] areas = { Board.Areas.BOARD };
		return areas;
	}

	@Override
	public boolean optionRemoved() {
		return optionRemoved;
	}

	@Override
	public boolean valueSet() {
		return valueSet;
	}

	@Override
	public String toString() {
		return "Check for singles";
	}

	@Override
	public String actionsMade() {
		return "removed options from all areas that has a value set";
	}

	@Override
	public int runCount() {
		return runcount;
	}

}
