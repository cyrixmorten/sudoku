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

public class NakedTriplets implements SolverStrategy {

	private boolean optionsRemoved;
	private List<Integer> coioptions;
	private List<Coord> coi;
	private Enum<Areas> area;
	private int runcount = 0;
	
	@Override
	public Board run(Board board, Enum<Areas> area, Coord c) {
		this.area = area;
		runcount++;
		optionsRemoved = false;
		coioptions = new ArrayList<Integer>();
		Area boardArea = board.getAreaAt(area, c);

		/*
		 * add coords where tileoptions' size is 2 or 3
		 */
		coi = new ArrayList<Coord>(); // coords of interest
		for (Coord coord : boardArea.getCoords()) {
			int options_size = board.getTileAt(coord).getOptions().size();
			if (options_size == 2 || options_size == 3) {
				coi.add(coord);
			}
		}
		if (coi.size() >= 3) {
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

			
			if (inspectCoi(board, boardArea, coi)) {
				optionsRemoved = true;
				return board;
			}
		}

		return board;
	}

	/*
	 * a O(N^3) search through a at worst list of 9 elements 
	 * the method returns true if any 3 optionslists combined contains only
	 * 3 options.
	 */
	private boolean inspectCoi(Board board, Area boardArea, List<Coord> coi) {
		
		for (Coord coi1 : coi) {
			for (Coord coi2 : coi) {
				for (Coord coi3 : coi) {
					if (!coi1.equals(coi2) && !coi1.equals(coi3)
							&& !coi2.equals(coi1) && !coi2.equals(coi3)
							&& !coi3.equals(coi1) && !coi1.equals(coi2)) {
						
						List<Coord> coichecklist = new ArrayList<Coord>();
						coichecklist.add(coi1);
						coichecklist.add(coi2);
						coichecklist.add(coi3);

						/*
						 * the resulting 'coi' coords must only contain 3 and
						 * only 3 unique options when combined.
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

						if (coioptions.size() == 3) {
							/*
							 * naked triplets located thus the values of
							 * 'coioptions' can safely be removed from the area
							 * around 'coi'.
							 */
							for (Coord coord : boardArea.getCoords()) {
								if (!coichecklist.contains(coord)) {
									for (Integer removeoption : coioptions) {
										if (board.getTileAt(coord)
												.removeOption(removeoption)) {
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

		return false;
	}

	@Override
	public boolean valueSet() {
		return false;
	}

	@Override
	public String actionsMade() {
		return "removed options " + coioptions + " from " + area + " " + Tools.areaNumber(area, coi.get(0));
	}

	@Override
	public boolean optionRemoved() {
		return optionsRemoved;
	}

	@Override
	public String toString() {
		return "Naked Triplets";
	}

	@Override
	public Enum<Areas>[] areas() {
		Board.Areas[] areas = {Board.Areas.BOX,Board.Areas.COL,Board.Areas.ROW};
		return areas;
	}

	@Override
	public int runCount() {
		return runcount;
	}
}
