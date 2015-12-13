package solver.solverStrategies;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import standard.StandardArea;
import toolbox.Coord;
import toolbox.Tools;
import framework.Area;
import framework.Board;
import framework.SolverStrategy;
import framework.Tile;
import framework.Board.Areas;

public class HiddenTriplets implements SolverStrategy {

	private boolean optionsRemoved;
	private List<Integer> coioptions;
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
		Map<Integer, List<Coord>> tripmap = new LinkedHashMap<Integer, List<Coord>>();

		Map<Integer, List<Coord>> ocmap = Tools
				.option_contained_in_coords(board, area, c);
		for (Integer option : ocmap.keySet()) {
			if (ocmap.get(option).size() == 2 || ocmap.get(option).size() == 3) {
				tripmap.put(option, ocmap.get(option));
			}
		}

		/*
		 * Now iterate over pairmap to see if any to pairs consists of the same
		 * coords, thus beeing a hidden pair.
		 */
		
		boolean tripFound = false;
		for (Integer o1 : tripmap.keySet()) {
			for (Integer o2 : tripmap.keySet()) {
				for (Integer o3 : tripmap.keySet()) {
				if (o1 != o2 && o2 != o3 && o3 != o1 && !tripFound) {
					// I use a set since it only contains unique items
					Set<Coord> triptest = new HashSet<Coord>();
					triptest.addAll(tripmap.get(o1));
					triptest.addAll(tripmap.get(o2));
					triptest.addAll(tripmap.get(o3));
					if (triptest.size() == 3) {
						// hidden triplets found !
						coioptions.add(o1);
						coioptions.add(o2);
						coioptions.add(o3);
						for (Coord coord : triptest) {
							coi.add(coord);
						}
						tripFound = true;
					}
					
				}
				}
			}
		}
		/*
		 * Update options in this area.
		 */
		if (tripFound) {
			Area boardArea = board.getAreaAt(area, c);
			for (Coord coord : boardArea.getCoords()) {
				Tile tile = board.getTileAt(coord);
				if (coi.contains(coord)) {
					List<Integer> removelist = new ArrayList<Integer>();
					for (Integer option : tile.getOptions().copyList()) {
						if (!coioptions.contains(option)) {
							removelist.add(option);
						}
					}
					optionsRemoved = tile.getOptions().removeOptions(removelist);
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
		return "found hidden triplets " + coioptions + " at " + area + " " + coi;
	}

	@Override
	public boolean optionRemoved() {
		return optionsRemoved;
	}

	@Override
	public String toString() {
		return "Hidden Triplets";
	}

	@Override
	public int runCount() {
		return runcount;
	}
}
