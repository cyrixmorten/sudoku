package solver.solverStrategies;

import java.util.ArrayList;
import java.util.HashMap;
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

public class JellyFish implements SolverStrategy {

	private boolean optionsRemoved;
	private String actionString;
	private int runcount = 0;

	@Override
	public Board run(Board board, Enum<Areas> area, Coord c) {
		optionsRemoved = false;
		runcount++;

		if (lookForJellyfish(board, Board.Areas.ROW, c)) {
			optionsRemoved = true;
			return board;
		}

		if (lookForJellyfish(board, Board.Areas.COL, c)) {
			optionsRemoved = true;
			return board;
		}

		return board;
	}

	private boolean lookForJellyfish(Board board, Enum<Areas> area, Coord c) {
		// Map<option, Map<area_index, relevant_coords>>
		Map<Integer, Map<Integer, List<Coord>>> slicemap = new HashMap<Integer, Map<Integer, List<Coord>>>();
		int index = 0;
		while (index < Board.DIMENSION) {
			Map<Integer, List<Coord>> option_to_coords = Tools
					.option_contained_in_coords(board, area, c);
			for (Integer option : option_to_coords.keySet()) {
				if (option_to_coords.get(option).size() <= 4
						&& option_to_coords.get(option).size() > 0) {

					Map<Integer, List<Coord>> optionmap = slicemap.get(option);
					if (optionmap != null) {
						optionmap.put(index, option_to_coords.get(option));
					} else {
						optionmap = new HashMap<Integer, List<Coord>>();
						optionmap.put(index, option_to_coords.get(option));
					}
					slicemap.put(option, optionmap);
				}
			}
			index++;
			c = new Coord(index, index);
		}

		for (Integer option : slicemap.keySet()) {
			Map<Integer, List<Coord>> optionslices = slicemap.get(option);
			Set<Integer> listindexes = optionslices.keySet();
			if (listindexes.size() >= 4) {
				/*
				 * There is 4 or more lines containing 4 or less cells with
				 * option in it. If any combination of theese lines reveals
				 * exactly 4 common cols, if current area is row or vica versa,
				 * then we have a jellyfish.
				 */	
				for (Integer i1 : listindexes) {
					for (Integer i2 : listindexes) {
						if (i1 != i2) {
							for (Integer i3 : listindexes) {
								if (i3 != i1 && i3 != i2) {
									for (Integer i4 : listindexes) {
										if (i4 != i1 && i4 != i2 && i4 != i3) {
											List<Coord> allcoords = new ArrayList<Coord>();
											allcoords.addAll(optionslices
													.get(i1));
											allcoords.addAll(optionslices
													.get(i2));
											allcoords.addAll(optionslices
													.get(i3));
											allcoords.addAll(optionslices
													.get(i4));

											Set<Integer> commonlines = new HashSet<Integer>();
											if (area == Board.Areas.ROW) {
												// look for common cols
												for (Coord coord : allcoords) {
													commonlines.add(coord
															.getV());
												}
											} else {
												// look for common rows
												for (Coord coord : allcoords) {
													commonlines.add(coord
															.getH());
												}
											}
											if (commonlines.size() == 4) {
												/*
												 * Select coords that can have
												 * option removed
												 */
												Board.Areas invarea = (area == Board.Areas.ROW) ? Board.Areas.COL
														: Board.Areas.ROW;
												// get the three lines
												List<Coord> coords = new ArrayList<Coord>();
												for (Integer i : commonlines) {
													Coord rcoord = new Coord(i,
															i);
													coords.addAll(board
															.getAreaAt(invarea,
																	rcoord)
															.getCoords());
												}
												// remove coords making the
												// swordfish
												coords.removeAll(optionslices
														.get(i1));
												coords.removeAll(optionslices
														.get(i2));
												coords.removeAll(optionslices
														.get(i3));
												coords.removeAll(optionslices
														.get(i4));
												// now try to remove option from
												// coords
												if (board
														.removeOptionsAtCoords(
																new int[] { option },
																coords)) {
													actionString = "removed option: "
															+ option
															+ " from the "
															+ invarea
															+ "'s: "
															+ commonlines
															+ " jellyfish located at "
															+ area
															+ "'s "
															+ i1
															+ " "
															+ i2
															+ " "
															+ i3
															+ " and "
															+ i4
															+ " "
															+ optionslices
																	.get(i1)
															+ " | "
															+ optionslices
																	.get(i2)
															+ " | "
															+ optionslices
																	.get(i3)
															+ " | "
															+ optionslices
																	.get(i4);
													return true;
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	@Override
	public Enum<Board.Areas>[] areas() {
		Board.Areas[] areas = { Board.Areas.ZERO };
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
		return "JellyFish";
	}

	@Override
	public int runCount() {
		return runcount;
	}
}
