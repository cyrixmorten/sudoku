package solver.solverStrategies;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import toolbox.Coord;
import toolbox.Tools;
import framework.Area;
import framework.Board;
import framework.SolverStrategy;
import framework.Board.Areas;

public class OneOption implements SolverStrategy {

	boolean optionRemoved;
	boolean valueSet;
	Enum<Board.Areas> area;
	int value;
	Coord coi;
	int runcount = 0;

	@Override
	public Board run(Board board, Enum<Board.Areas> area, Coord c) {
		runcount++;
		this.valueSet = false;
		this.area = area;

		Map<Coord, Integer> options_size_map = Tools.coord_to_optionsize(board,
				area, c);
		// look for single options
		for (Coord coord : options_size_map.keySet()) {
			int optionsize = options_size_map.get(coord);
			if (optionsize == 1) {
				valueSet = true;
				coi = coord;
				value = board.getTileAt(coi).getOptions().copyList().get(0);
				board.getTileAt(coi).setValue(value);
				return board;
			}
		}

		return board;
	}
	
	@Override
	public Board.Areas[] areas() {
		Board.Areas[] areas = {Board.Areas.BOARD};
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
		return "Check for tiles with a single option";
	}

	@Override
	public String actionsMade() {
		return "has set value to " + value
				+ " on tile with only one option in area " + area + " at " + coi;
	}

	@Override
	public int runCount() {
		return runcount;
	}

}
