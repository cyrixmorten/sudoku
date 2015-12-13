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

public class XYChain implements SolverStrategy {

	private boolean optionsRemoved;
	private String actionString;
	private int runcount = 0;

	@Override
	public Board run(Board board, Enum<Areas> area, Coord coord) {
		optionsRemoved = false;
		runcount++;

		List<Coord> areaCoords = board.getAreaAt(area, coord).getCoords();

		for (Coord areacoord : areaCoords) {
			List<Integer> options = board.getTileAt(areacoord).getOptions()
					.copyList();
			if (options.size() == 2) {
				Coord[] chain = new Coord[Board.DIMENSION*Board.DIMENSION];
				
				for (int i = 0; i<=1; i++) {
					start_of_current_chain = options.get(i);
					chain[0] = areacoord;
					doXYChain(board, areacoord, areacoord, chain, options
							.get((i+1)%2), 1);
					if (optionsRemoved) {
						return board;
					}
				}

			}
		}

		return board;
	}
	
	private int start_of_current_chain;

	private void doXYChain(Board board, Coord startcoord, Coord newcoord, Coord[] chain, int xy, int chainindex) {

		if (xy == start_of_current_chain && !startcoord.equals(newcoord)) {
			// both ends are equal thus we might be able to remove
			// some xy options based on this chain.

			Set<Coord> areas_around_start = Tools.all_areas_around(board, startcoord);
			Set<Coord> areas_around_end = Tools.all_areas_around(board, newcoord);

			areas_around_start.retainAll(areas_around_end); // intersect

			Set<Coord> intersection = areas_around_start;
			intersection.remove(startcoord);
			intersection.remove(newcoord);

			if (board.removeOptionsAtCoords(new int[]{xy}, intersection)) {
				actionString = "start of chain " + startcoord + " end of chain " + newcoord +
								" removed all occurences of " + start_of_current_chain + " at " + 
								intersection;
				optionsRemoved = true;
				return;
			}

		} else {

			Set<Coord> coordarea = Tools.all_areas_around(board, newcoord);
			
			//coordarea.removeAll(chain);
			for (Coord chaincoord: chain) {
				coordarea.remove(chaincoord);
			}

			for (Coord acoord : coordarea) {
				List<Integer> nextoptions = board.getTileAt(acoord)
						.getOptions().copyList();
				if (nextoptions.size() == 2) {

					int nextoptions1 = nextoptions.get(0);
					int nextoptions2 = nextoptions.get(1);

					if (nextoptions1 == xy && nextoptions2 != xy) {
						chain[chainindex] = acoord;
						doXYChain(board, startcoord, acoord, chain, nextoptions2, chainindex+=1);
					}

					if (nextoptions1 != xy && nextoptions2 == xy) {
						chain[chainindex] = acoord;
						doXYChain(board, startcoord, acoord, chain, nextoptions1, chainindex+=1);
					}

				}
			}
		}
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
		return "XY-Chain";
	}

	@Override
	public int runCount() {
		return runcount;
	}
}
