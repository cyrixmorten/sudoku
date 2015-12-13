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

public class UniqueOption implements SolverStrategy {

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

		Map<Integer, List<Coord>> option_coords_map = Tools
				.option_contained_in_coords(board, area, c);

		// look for unique options
		for (Integer option : option_coords_map.keySet()) {
			List<Coord> coords = option_coords_map.get(option);
			if (coords.size() == 1) {
				// only one coord contains this option
				valueSet = true;
				value = option;
				coi = coords.get(0);
				board.getTileAt(coi).setValue(value);
				return board;
			}
		}

		return board;
	}
	
	@Override
	public Enum<Board.Areas>[] areas() {
		Enum<?>[] areas = {Board.Areas.BOX,Board.Areas.COL,Board.Areas.ROW};
		return (Enum<Areas>[]) areas;
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
		return "Check for solved tiles";
	}

	@Override
	public String actionsMade() {
		return "has set value to " + value
				+ " on tile with unique option in area " + area + " at " + coi;
	}

	@Override
	public int runCount() {
		return runcount;
	}

}
