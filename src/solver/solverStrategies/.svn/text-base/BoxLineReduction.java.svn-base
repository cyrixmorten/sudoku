package solver.solverStrategies;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import standard.StandardArea;
import toolbox.Coord;
import framework.Area;
import framework.Board;
import framework.SolverStrategy;
import framework.Tile;
import framework.Board.Areas;

public class BoxLineReduction implements SolverStrategy {

	private boolean optionsRemoved;
	private int optionremoved;
	private Coord coi;
	private Set<Integer> boxOptions;
	private List<Coord> boxCoords;
	private Enum<Areas> area;
	private int boxNumber;
	private int runcount = 0;

	@Override
	public Board run(Board board, Enum<Areas> area, Coord c) {
		optionsRemoved = false;

			runcount++;
			boxOptions = new HashSet<Integer>();
			boxCoords = new ArrayList<Coord>();
			boxCoords.addAll(board.getAreaAt(area, c).getCoords());
			boxNumber = (c.getH() / Board.TILES_PER_BOX * Board.TILES_PER_BOX + (c.getV() / Board.TILES_PER_BOX + 1));
			
			/*
			 *  ____________
			 * | vh | v | v | hCoords & vCoords in a box
			 * | h  |   |   |
			 * |_h__|___|___| 
			 * 
			 */
			
			List<Coord> vCoords = new ArrayList<Coord>();
			List<Coord> hCoords = new ArrayList<Coord>();
			for (Coord coord : boxCoords) {
				boxOptions
						.addAll(board.getTileAt(coord).getOptions().copyList());
				if (coord.getH() % 3 == 0) {
					vCoords.add(coord);
				}
				if (coord.getV() % 3 == 0) {
					hCoords.add(coord);
				}
			}

			blr(board, Board.Areas.COL, vCoords);
			if (!optionsRemoved) {
				blr(board, Board.Areas.ROW, hCoords);
			}

		return board;
	}

	private void blr(Board board, Enum<Areas> area, List<Coord> linecoords) {
		this.area = area;
		for (Coord boxlinecoord : linecoords) {
			// V or H line from box
			Set<Integer> linewoboxOptions = new HashSet<Integer>(); // box
																	// excluded
			Set<Integer> boxwolineOptions = new HashSet<Integer>(); // box only
			List<Coord> lineareaCoords = new ArrayList<Coord>();
			lineareaCoords.addAll(board.getAreaAt(area, boxlinecoord)
					.getCoords());
			for (Coord coord : lineareaCoords) {
				Tile tile = board.getTileAt(coord);
				if (tile.getValue() == 0) {
					for (Integer option : tile.getOptions().copyList()) {
						if (!boxCoords.contains(coord)) {
							linewoboxOptions.add(option);
						} else {
							boxwolineOptions.add(option);
						}
					}
				}
			}

			// System.out.println("line: " + linewoboxOptions);
			// System.out.println("boxline: " +boxwolineOptions);
			Set<Integer> boxsectionwolineOptions = new HashSet<Integer>();
			Set<Coord> boxsectionwolineCoords = new HashSet<Coord>();
			for (Coord coord : boxCoords) {
				if (!lineareaCoords.contains(coord)) {
					boxsectionwolineOptions.addAll(board.getTileAt(coord)
							.getOptions().copyList());
					boxsectionwolineCoords.add(coord);
				}
			}

			// System.out.println("section: " + boxsectionwolineOptions);

			for (Integer boxoption : boxwolineOptions) {
				if (!linewoboxOptions.contains(boxoption)
						&& boxsectionwolineOptions.contains(boxoption)) {
					/*
					 * Found an option contained in the box that is not an
					 * option anywhere else on this line. This means that this
					 * boxoption HAS to be in this line in this box, thus it is
					 * ok to remove option from all other boxCoords than those
					 * on this line.
					 */
					for (Coord boxcoord : boxsectionwolineCoords) {
						Tile tile = board.getTileAt(boxcoord);
						if (tile.removeOption(boxoption)) {
							optionsRemoved = true;
							coi = boxlinecoord;
							optionremoved = boxoption;
						}
					}
				}
			}

		}
	}
	
	@Override
	public Board.Areas[] areas() {
		Board.Areas[] areas = {Board.Areas.BOX};
		return areas;
	}

	@Override
	public boolean valueSet() {
		return false;
	}

	@Override
	public String actionsMade() {
		int linenumber = 0;
		if (area == Board.Areas.ROW) {
			linenumber = coi.getH() + 1;
		} else {
			linenumber = coi.getV() + 1;
		}
		return toString() + ": option " + optionremoved + " removed from box "
				+ boxNumber + " if not in " + area + " " + linenumber
				+ " since " + optionremoved + " is not yet found in " + area + " "
				+ linenumber + " and can only occur once in a box ";
	}

	@Override
	public boolean optionRemoved() {
		return optionsRemoved;
	}

	@Override
	public String toString() {
		return "Box/Line Reduction";
	}

	@Override
	public int runCount() {
		return runcount;
	}
}
