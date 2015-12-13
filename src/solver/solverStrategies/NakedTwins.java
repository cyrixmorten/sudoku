package solver.solverStrategies;

import java.util.ArrayList;
import java.util.List;

import standard.StandardArea;
import toolbox.Coord;
import toolbox.Tools;
import framework.Area;
import framework.Board;
import framework.SolverStrategy;
import framework.Board.Areas;

public class NakedTwins implements SolverStrategy {

	private boolean optionsRemoved;
	private List<Integer> coioptions;
	private List<Coord> coi;
	private Enum<Areas> area;
	private int runcount = 0;
	private String actionString;
	

	@Override
	public Board run(Board board, Enum<Areas> area, Coord c) {
		runcount++;
		this.area = area;
		optionsRemoved = false;
		coioptions = new ArrayList<Integer>();
		Area boardArea = board.getAreaAt(area, c);

		/*
		 * add coords where tileoptions' size is 2
		 */
		coi = new ArrayList<Coord>(); // coords of interest
		for (Coord coord : boardArea.getCoords()) {
			int options_size = board.getTileAt(coord).getOptions().size();
			if (options_size == 2) {
				coi.add(coord);
			}
		}
		if (coi.size() >= 2) {
			/*
			 * weed out coords from coi where not all options are represented in
			 * at least 2 tileoptions.
			 */
			List<Coord> invalidcois = new ArrayList<Coord>();
			for (Coord coicoord : coi) {
				List<Integer> optionlist = board.getTileAt(coicoord)
						.getOptions().copyList();
				int counter = 0;
				for (Integer option : optionlist) {
					for (Coord coicount : coi) {
						List<Integer> coioptions = board.getTileAt(coicount)
								.getOptions().copyList();
						if (coioptions.contains(option)) {
							counter++;
						}
					}
				}
				if (counter < 2) {
					invalidcois.add(coicoord);
				}
			}
			coi.removeAll(invalidcois);

			optionsRemoved = inspectCoi(board, boardArea, coi);
			if (optionsRemoved) {
				actionString = "removed options " + coioptions + " from " + area + " " + Tools.areaNumber(area, c);
			}
		}

		return board;
	}

	/*
	 * a O(N^2) search through a at worst 2 lists of 9 elements 
	 * the method returns true if any 2 optionslists combined contains only
	 * 2 options.
	 */
	private boolean inspectCoi(Board board, Area boardArea, List<Coord> coi) {
		
		for (Coord coi1 : coi) {
			for (Coord coi2 : coi) {

					if (!coi1.equals(coi2)) {
						
						List<Coord> coichecklist = new ArrayList<Coord>();
						coichecklist.add(coi1);
						coichecklist.add(coi2);

						/*
						 * the resulting 'coi' coords must only contain 2 and
						 * only 2 unique options when combined.
						 */
						coioptions.clear();
						for (Coord coicheck : coichecklist) {
							List<Integer> optionlist = board
									.getTileAt(coicheck).getOptions().copyList();
							for (Integer checkoption : optionlist) {
								if (!coioptions.contains(checkoption)) {
									coioptions.add(checkoption);
								}
							}
						}

						if (coioptions.size() == 2) {
							/*
							 * naked triplets located thus the values of
							 * 'coioptions' can safely be removed from the area
							 * around 'coi'.
							 */
							boolean optionsremoved = false;
							for (Coord coord : boardArea.getCoords()) {
								if (!coichecklist.contains(coord)) {
									for (Integer removeoption : coioptions) {
										if (board.getTileAt(coord)
												.removeOption(removeoption)) {
											optionsremoved = true;
										}
									}
								}
							}
							return optionsremoved;
						}

					}
				
			}
		}

		return false;
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
		return "Naked Twins";
	}
	@Override
	public int runCount() {
		return runcount;
	}

	@Override
	public Board.Areas[] areas() {
		Board.Areas[] areas = {Board.Areas.BOX,Board.Areas.COL,Board.Areas.ROW};
		return areas;
	}
}
