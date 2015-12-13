package solver.solverStrategies;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import standard.StandardArea;
import toolbox.Coord;
import toolbox.Tools;
import framework.Area;
import framework.Board;
import framework.SolverStrategy;
import framework.Tile;
import framework.Board.Areas;

public class HiddenPair implements SolverStrategy {

	private boolean optionsRemoved;
	private List<Integer> coioptions;
	private List<Coord> coiremoved;
	private List<Coord> coi;
	private Enum<Areas> area;
	private int runcount = 0;

	@Override
	public Board run(Board board, Enum<Areas> area, Coord c) {
		runcount++;
		this.area = area;
		optionsRemoved = false;
		coioptions = new ArrayList<Integer>();

		coi = new ArrayList<Coord>();

		/*
		 * Contains a map between an option value and two coords. These coords
		 * are the only coords containing option value in this area.
		 */
		Map<Integer, List<Coord>> pairmap = new LinkedHashMap<Integer, List<Coord>>();

		Map<Integer, List<Coord>> ocmap = Tools
				.option_contained_in_coords(board, area, c);
		for (Integer option : ocmap.keySet()) {
			if (ocmap.get(option).size() == 2) {
				pairmap.put(option, ocmap.get(option));
			}
		}

		/*
		 * Now iterate over pairmap to see if any to pairs consists of the same
		 * coords, thus beeing a hidden pair.
		 */
		
		boolean pairFound = false;
		for (Integer o1 : pairmap.keySet()) {
			for (Integer o2 : pairmap.keySet()) {
				if (o1 != o2 && !pairFound) {
					if (pairmap.get(o1).equals(pairmap.get(o2))) {
						// hidden pair found !
						coioptions.add(o1);
						coioptions.add(o2);
						coi.addAll(pairmap.get(o1));
						pairFound = true;
					}
				}
			}
		}
		/*
		 * Update options in this area.
		 */
		if (pairFound) {
			Area boardArea = board.getAreaAt(area, c);
			for (Coord coord : boardArea.getCoords()) {
				Tile tile = board.getTileAt(coord);
				if (coi.contains(coord)) {
					if (tile.getOptions().size() != coioptions.size()) {
						tile.getOptions().setOptions(coioptions);
						optionsRemoved = true;
					}
				}
			}
		}

		return board;
	}
	
	@Override
	public Enum<Board.Areas>[] areas() {
		Board.Areas[] areas = {Board.Areas.BOX,Board.Areas.COL,Board.Areas.ROW};
		return areas;
	}

	@Override
	public boolean valueSet() {
		return false;
	}

	@Override
	public String actionsMade() {
		return "found hidden pair " + coioptions + " at " + area + " " + coi;
	}

	@Override
	public boolean optionRemoved() {
		return optionsRemoved;
	}

	@Override
	public String toString() {
		return "Hidden Pair" + " " + area + ":";
	}

	@Override
	public int runCount() {
		return runcount;
	}
}
