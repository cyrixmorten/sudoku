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

public class PointingPairs implements SolverStrategy {

	private boolean optionsRemoved;
	private Coord coi;
	private List<Coord> coiremoved;
	private Enum<Areas> area;
	private int runcount = 0;
	private int optionremoved;

	@Override
	public Board run(Board board, Enum<Areas> area, Coord c) {
		optionsRemoved = false;
		coiremoved = new ArrayList<Coord>();
		coi = c;
		runcount++;
		
		Map<Integer, List<Coord>> option_to_coords = Tools.option_contained_in_coords(board, area, c);
		
		for (Integer option: option_to_coords.keySet()) {
			// see if this option occours two times in this box
			if (option_to_coords.get(option).size() == 2) {

				Coord coord1 = option_to_coords.get(option).get(0);
				Coord coord2 = option_to_coords.get(option).get(1);

				// look if the two coords are in the same row or col
				if (coord1.getH() == coord2.getH()) {
					// same row
					deleteFromLine(board, Board.Areas.ROW, coord1, option);
				}
				
				if (coord1.getV() == coord2.getV()) {
					// same col
					deleteFromLine(board, Board.Areas.COL, coord1, option);
				}
				
				if (optionsRemoved) {
					return board;
				}
				
			}
		}
		
		return board;
	}
	
	private void deleteFromLine(Board board, Enum<Areas> area, Coord c, int value) {
		int c_boxNumber = (c.getH() / Board.TILES_PER_BOX * Board.TILES_PER_BOX + (c.getV() / Board.TILES_PER_BOX + 1));
		List<Coord> lineCoords = board.getAreaAt(area, c).getCoords();
		for (Coord coord: lineCoords) {
			int current_boxNumber = (coord.getH() / Board.TILES_PER_BOX * Board.TILES_PER_BOX + (coord.getV() / Board.TILES_PER_BOX + 1));
			if (c_boxNumber == current_boxNumber) {
				// ignore
			}
			else {
				Tile tile = board.getTileAt(coord);
				if (tile.removeOption(value)) {
					optionsRemoved = true;
					this.area = area;
					this.coiremoved.add(coord);
					this.optionremoved = value;
				}
			}
		}
	}
	
	@Override
	public Enum<Board.Areas>[] areas() {
		Board.Areas[] areas = {Board.Areas.BOX};
		return areas;
	}

	@Override
	public boolean valueSet() {
		return false;
	}

	@Override
	public String actionsMade() {
		int boxnumber = (coi.getH() / Board.TILES_PER_BOX * Board.TILES_PER_BOX + (coi.getV() / Board.TILES_PER_BOX + 1));
		int linenumber = 0;
		if (area == Board.Areas.ROW) {
			linenumber = coi.getH() + 1;
		} else {
			linenumber = coi.getV() + 1;
		}
		return toString() + ": option " + optionremoved + " removed from " + area + " line "
				+ linenumber + " if not in box " + boxnumber + " "  
				+ " since " + optionremoved + " only occours in box " + boxnumber + " " + 
				" twice in " + area;
	}

	@Override
	public boolean optionRemoved() {
		return optionsRemoved;
	}

	@Override
	public String toString() {
		return "Pointing Pairs";
	}

	@Override
	public int runCount() {
		return runcount;
	}
}
