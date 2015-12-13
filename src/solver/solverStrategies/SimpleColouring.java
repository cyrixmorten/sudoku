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

public class SimpleColouring implements SolverStrategy {

	private boolean optionsRemoved;
	private int runcount = 0;
	String actionString;

	@Override
	public Board run(Board board, Enum<Areas> area, Coord c) {
		optionsRemoved = false;
		runcount++;

		Map<Integer, List<Coord>> option_to_coords = Tools
				.option_contained_in_coords(board, area, c);

		for (Integer option : option_to_coords.keySet()) {
			// see if this option occours two times in this box
			if (option_to_coords.get(option).size() == 2) {

				Coord coord1 = option_to_coords.get(option).get(0);
				Coord coord2 = option_to_coords.get(option).get(1);
				// recusively color option pairs in lines green or blue
				Map<Coord, Boolean> colourmap1 = new LinkedHashMap<Coord, Boolean>();
				colourmap1 = doSimpleColour(board, coord1,
						new LinkedHashMap<Coord, Boolean>(), option, true);
				// recusively color option pairs in lines green or blue
				Map<Coord, Boolean> colourmap2 = new LinkedHashMap<Coord, Boolean>();
				colourmap2 = doSimpleColour(board, coord2,
						new LinkedHashMap<Coord, Boolean>(), option, false);

				// collect all coloured coords
				Map<Coord, Boolean> colourmap = new LinkedHashMap<Coord, Boolean>();
				colourmap.putAll(colourmap1);
				colourmap.putAll(colourmap2);

				// divide into lists of green and blue
				List<Coord> greencoords = new ArrayList<Coord>();
				List<Coord> bluecoords = new ArrayList<Coord>();

				for (Coord coord : colourmap.keySet()) {
					if (colourmap.get(coord) == true) {
						greencoords.add(coord);
					} else {
						bluecoords.add(coord);
					}
				}



				if (rule_two(board, greencoords, bluecoords, option))
					return board;
				if (rule_five(board, greencoords, bluecoords, option))
					return board;

			}
		}

		return board;
	}

	/*
	 * Rule 2:
	 * 
	 * Look if any green og blue colours are in the same area, thus ruling out
	 * this colour as a possibility.
	 */

	@SuppressWarnings("unchecked")
	private boolean rule_two(Board board, List<Coord> bluecoords,
			List<Coord> greencoords, int option) {

		List<?>[] colouredcoords = { bluecoords, greencoords };

		for (List<?> colourlist : colouredcoords) {
			for (Coord colouredcoord : (List<Coord>) colourlist) {
				for (Coord cmp_colouredcoord : (List<Coord>) colourlist) {
					if (!colouredcoord.equals(cmp_colouredcoord) // different
																	// coord
							&& Tools.coords_in_same_area(board,
									colouredcoord, cmp_colouredcoord)) {
						/*
						 * found two different coords with the same colour in
						 * same area
						 */
						if (board.removeOptionsAtCoords(new int[] { option },
								(List<Coord>) colourlist)) {
							actionString = " Rule 2: removed " + option
									+ " from " + colourlist;
							optionsRemoved = true;
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	/*
	 * Rule 5:
	 * 
	 * Do a O(N^2) search trough green and blue if the intersection between
	 * green and blue reveals a coord, then can we remove 'option' from this
	 * tile.
	 * 
	 * precondition: green and blue are not part of the same area
	 */

	private boolean rule_five(Board board, List<Coord> bluecoords,
			List<Coord> greencoords, int option) {
		for (Coord green : greencoords) {
			for (Coord blue : bluecoords) {

				if (!Tools.coords_in_same_area(board, green, blue)) {

					Set<Coord> bluelines = Tools.all_areas_around(board, blue);
					Set<Coord> greenlines = Tools.all_areas_around(board, green);

					bluelines.retainAll(greenlines); // do intersection
					Set<Coord> intersection = bluelines;

					Set<Coord> twocolours = new HashSet<Coord>();

					for (Coord coord : intersection) {
						if (board.getTileAt(coord).getOptions()
								.contains(option)) {
							twocolours.add(coord);
						}
					}
					if (board.removeOptionsAtCoords(new int[] { option },
							twocolours)) {
						actionString = " Rule 5: Uncoloured candidate "
								+ option
								+ " in "
								+ twocolours
								+ " can see two different coloured candidiates of "
								+ option + " elsewhere";
						optionsRemoved = true;
						return true;
					}
				}
			}
		}
		return false;
	}

	private Map<Coord, Boolean> doSimpleColour(Board board, Coord coord,
			Map<Coord, Boolean> colourmap, int option, boolean colour) {

		colourmap.put(coord, colour);

		Board.Areas[] areas = { Board.Areas.BOX, Board.Areas.COL,
				Board.Areas.ROW };

		// depth first colouring
		for (Board.Areas area : areas) {
			Map<Integer, List<Coord>> option_to_coords = Tools
					.option_contained_in_coords(board, area, coord);
			// option is the value that we are colouring
			List<Coord> colcoords = option_to_coords.get(option);
			if (colcoords.size() == 2) {
				Coord nextCordtoColor = null;
				// locate the new coord
				if (!colcoords.get(0).equals(coord)) {
					nextCordtoColor = colcoords.get(0);
				} else {
					nextCordtoColor = colcoords.get(1);
				}
				// and make sure it isnt coloured
				if (!colourmap.keySet().contains(nextCordtoColor)) {
					colourmap = doSimpleColour(board, nextCordtoColor,
							colourmap, option, !colour);
				}
			}
		}

		return colourmap;
	}

	@Override
	public Enum<Board.Areas>[] areas() {
		Board.Areas[] areas = { Board.Areas.BOX };
		return (Enum<Areas>[]) areas;
	}

	@Override
	public boolean valueSet() {
		return false;
	}

	@Override
	public String actionsMade() {
		return actionString;
	}

	@Override
	public boolean optionRemoved() {
		return optionsRemoved;
	}

	@Override
	public String toString() {
		return "Simple Colouring";
	}

	@Override
	public int runCount() {
		return runcount;
	}
}
