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

public class SwordFish implements SolverStrategy {

	private boolean optionsRemoved;
	private String actionString;
	private int runcount = 0;

	@Override
	public Board run(Board board, Enum<Areas> area, Coord c) {
		optionsRemoved = false;
		runcount++;

		if (lookForSwordfish(board, Board.Areas.ROW, c)) {
			optionsRemoved = true;
			return board;
		}
		
		if (lookForSwordfish(board, Board.Areas.COL, c)) {
			optionsRemoved = true;
			return board;			
		}
		
		return board;
	}
	
	private boolean lookForSwordfish(Board board, Enum<Areas> area, Coord c) {
		// Map<option, Map<area_index, relevant_coords>> 
		Map<Integer, Map<Integer, List<Coord>>> slicemap = new HashMap<Integer, Map<Integer,List<Coord>>>(); 
		int index = 0;
		while(index < Board.DIMENSION) {
			Map<Integer, List<Coord>> option_to_coords = Tools.option_contained_in_coords(board, area, c);
			for (Integer option : option_to_coords.keySet()) {
				if (option_to_coords.get(option).size() <= 3 && option_to_coords.get(option).size() > 0) {
					
					Map<Integer, List<Coord>> optionmap = slicemap.get(option);
					if (optionmap != null) {
						optionmap.put(index, option_to_coords.get(option));
					}
					else {
						optionmap = new HashMap<Integer, List<Coord>>();
						optionmap.put(index, option_to_coords.get(option));
					}
					slicemap.put(option, optionmap);
				}
			}
			index++;
			c = new Coord(index,index);
		}
		
		for (Integer option : slicemap.keySet()) {
			Map<Integer, List<Coord>> optionslices = slicemap.get(option);
			Set<Integer> listindexes = optionslices.keySet();
			if (listindexes.size() >= 3) {
				/*
				 *  There is 3 or more lines containing 3 or less cells
				 *  with option in it.
				 *  If any combination of theese lines reveals exactly
				 *  3 common cols if current area is row or vica versa, 
				 *  then we have a swordfish.
				 */
				for (Integer i1: listindexes) {
					for (Integer i2: listindexes) {
						for (Integer i3: listindexes) {
							if (i1 != i2 && i2 != i3 && i3 != i1) {
								List<Coord> allcoords = new ArrayList<Coord>();
								allcoords.addAll(optionslices.get(i1));
								allcoords.addAll(optionslices.get(i2));
								allcoords.addAll(optionslices.get(i3));
								
								Set<Integer> commonlines = new HashSet<Integer>();
								if (area == Board.Areas.ROW) {
									// look for common cols
									for (Coord coord: allcoords) {
										commonlines.add(coord.getV());
									}
								}
								else {
									// look for common rows
									for (Coord coord: allcoords) {
										commonlines.add(coord.getH());
									}									
								}
								if (commonlines.size() == 3) {
									/*
									 * Select coords that can have option removed  
									 */
									Board.Areas invarea = (area == Board.Areas.ROW) ? Board.Areas.COL : Board.Areas.ROW;
									// get the three lines 
									List<Coord> coords = new ArrayList<Coord>();
									for (Integer i: commonlines) {
										Coord rcoord = new Coord(i,i);
										coords.addAll(board.getAreaAt(invarea, rcoord).getCoords());
									}
									// remove coords making the swordfish
									coords.removeAll(optionslices.get(i1));
									coords.removeAll(optionslices.get(i2));
									coords.removeAll(optionslices.get(i3));
									// now try to remove option from coords
									if (board.removeOptionsAtCoords(new int[] {option}, coords)) {
										actionString = "removed option: " + option + " from the " + invarea + "'s: " + commonlines + " swordfish located at " + area + "'s " + i1 + " " + i2 + " and " + i3 + " " + optionslices.get(i1) + " | " + optionslices.get(i2) + " | " + optionslices.get(i3);
										return true;
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
		return "SwordFish";
	}

	@Override
	public int runCount() {
		return runcount;
	}
}
