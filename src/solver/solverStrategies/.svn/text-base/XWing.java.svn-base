package solver.solverStrategies;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import standard.StandardArea;
import toolbox.Coord;
import toolbox.Options;
import toolbox.Tools;
import framework.Area;
import framework.Board;
import framework.SolverStrategy;
import framework.Tile;
import framework.Board.Areas;

public class XWing implements SolverStrategy {

	private boolean optionsRemoved;
	private String actionString;
	private int runcount = 0;

	@Override
	public Board run(Board board, Enum<Areas> area, Coord c) {
		optionsRemoved = false;
		runcount++;

		Map<Integer, List<Coord>> option_to_coords = Tools
				.option_contained_in_coords(board, area, c);

		for (Integer option : option_to_coords.keySet()) {
			if (option_to_coords.get(option).size() == 2) {

				Coord coord1 = option_to_coords.get(option).get(0);
				Coord coord2 = option_to_coords.get(option).get(1);

				Board.Areas invarea;


				invarea = (area == Board.Areas.COL) ? Board.Areas.ROW : Board.Areas.COL; 


				List<Coord> invcoords1 = Tools.cords_containing_option(board,
						invarea, coord1, option);
				List<Coord> invcoords2 = Tools.cords_containing_option(board,
						invarea, coord2, option);
				invcoords1.remove(coord1);
				invcoords2.remove(coord2);

				for (Coord invcoord1 : invcoords1) {
					int boardindex1 = (area == Board.Areas.COL) ? invcoord1
							.getV() : invcoord1.getH();
					for (Coord invcoord2 : invcoords2) {
						int boardindex2 = (area == Board.Areas.COL) ? invcoord2
								.getV() : invcoord2.getH();
						if (boardindex1 == boardindex2
								&& Tools.cords_containing_option(board, area,
										invcoord1, option).size() == 2) {
							// coord1, coord2, invcoord1 and invcoord2 areXWing
							List<Coord> invcoords1_clone = new ArrayList<Coord>();
							List<Coord> invcoords2_clone = new ArrayList<Coord>();
							invcoords1_clone.addAll(invcoords1);
							invcoords2_clone.addAll(invcoords2);
							invcoords1_clone.remove(invcoord1);
							invcoords2_clone.remove(invcoord2);
							boolean removedAt1 = board.removeOptionsAtCoords(
									new int[]{option}, invcoords1_clone);
							boolean removedAt2 = board.removeOptionsAtCoords(
									new int[]{option}, invcoords2_clone);
							if (removedAt1 || removedAt2) {
								optionsRemoved = true;
								if (area == Board.Areas.ROW) {
									actionString = "XWing found at " + coord1
											+ coord2 + invcoord1 + invcoord2
											+ " removed option: " + option
											+ " from " + invarea + " "
											+ (coord1.getV() + 1) + " and "
											+ (coord2.getV() + 1);
								} else {
									actionString = "XWing found at " + coord1
									+ coord2 + invcoord1 + invcoord2
									+ " removed option: " + option
									+ " from " + invarea + " "
									+ (coord1.getH() + 1) + " and "
									+ (coord2.getH() + 1);
								}
								return board;
							}
						}
					}
				}

			}
		}

		return board;
	}

	@Override
	public Enum<Board.Areas>[] areas() {
		Board.Areas[] areas = { Board.Areas.ROW, Board.Areas.COL };
		return areas;
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
		return "X-Wing";
	}

	@Override
	public int runCount() {
		return runcount;
	}
}
