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

public class YWing implements SolverStrategy {

	private boolean optionsRemoved;
	private List<Coord> removedCatCoords;
	private String actionString;
	private int runcount = 0;

	@Override
	public Board run(Board board, Enum<Areas> area, Coord coord) {
		optionsRemoved = false;
			runcount++;
			removedCatCoords = new ArrayList<Coord>();
			// coords in box with options of size 2
			List<Coord> boxCoords = Tools.coords_with_optionsize(board,
					area, coord, 2);
			
			// AB see http://www.sudokuwiki.org/Y_Wing_Strategy
			for (Coord ab_coord : boxCoords) {
				List<Integer> ab_options = board.getTileAt(ab_coord).getOptions()
						.copyList();
				List<Coord> bc_rowCandidates = Tools.coords_with_optionsize(
						board, Board.Areas.ROW, ab_coord, 2);
				List<Coord> bc_colCandidates = Tools.coords_with_optionsize(
						board, Board.Areas.COL, ab_coord, 2);
				List<Coord> bc_boxCandidates = Tools.coords_with_optionsize(
						board, Board.Areas.BOX, ab_coord, 2);

				// all coords containing 2 options around boxcoord
				List<Coord> bc_candidates = new ArrayList<Coord>();
				bc_candidates.addAll(bc_boxCandidates);
				bc_candidates.addAll(bc_rowCandidates);
				bc_candidates.addAll(bc_colCandidates);
				while (bc_candidates.contains(ab_coord)) {
					bc_candidates.remove(ab_coord);
				}

				// find a candidate containing one of the options in boxcoord
				for (Coord bc_candidate : bc_candidates) {
					Tile bc_tile = board.getTileAt(bc_candidate);
					List<Integer> bc_options = bc_tile.getOptions().copyList();
					bc_options.removeAll(ab_options);

					if (bc_options.size() == 1) {
						// C see http://www.sudokuwiki.org/Y_Wing_Strategy
						int c = bc_options.get(0);

						List<Integer> getb = bc_tile.getOptions().copyList();
						getb.removeAll(bc_options);
						int b = getb.get(0);

						// BC see http://www.sudokuwiki.org/Y_Wing_Strategy
						Set<Coord> ac_candidates = new HashSet<Coord>();
						ac_candidates.addAll(bc_rowCandidates); // row from ac
						ac_candidates.addAll(bc_colCandidates); // col from ac
						List<Coord> bc_candidate_col = board.getAreaAt(
								Board.Areas.COL, bc_candidate).getCoords();
						List<Coord> bc_candidate_row = board.getAreaAt(
								Board.Areas.ROW, bc_candidate).getCoords();
						ac_candidates.removeAll(bc_candidate_col);
						ac_candidates.removeAll(bc_candidate_row);
						for (Coord ac_candidate : ac_candidates) {
							Tile ac_tile = board.getTileAt(ac_candidate);
							List<Integer> ac_options = ac_tile.getOptions().copyList();
							ac_options.removeAll(ab_options);
							if (ac_options.size() == 1
									&& ac_options.get(0) == c
									&& !board.getAreaAt(area, bc_candidate).
										equals(board.getAreaAt(area, ac_candidate))) {

								List<Integer> geta = ac_tile.getOptions().copyList();
								geta.removeAll(ac_options);
								int a = geta.get(0);

								if (a != b) {
									actionString = "AB:" + ab_coord + " BC:"
											+ bc_candidate + " AC:"
											+ ac_candidate;
									return intersectBCACRemoveC(board,
											bc_candidate, ac_candidate, c);
								}
							}
						}
					}
				}

			}

		return board;
	}

	private Board intersectBCACRemoveC(Board board, Coord bc, Coord ac, int c) {

		cValue = c;

		List<Coord> bcacCoords = Tools.intersect_all_areas_around_coords(board, bc, ac);

		for (Coord bcac : bcacCoords) {
			Tile tile = board.getTileAt(bcac);
			if (tile.removeOption(c)) {
				removedCatCoords.add(bcac);
				optionsRemoved = true;
			}
		}

		return board;
	}

	private int cValue = 0;

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
		return toString() + " " + actionString + " removed " + cValue + " at "
				+ removedCatCoords;
	}

	@Override
	public boolean optionRemoved() {
		return optionsRemoved;
	}

	@Override
	public String toString() {
		return "Y-Wing";
	}

	@Override
	public int runCount() {
		return runcount;
	}
}
