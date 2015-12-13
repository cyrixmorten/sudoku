package solver.solverStrategies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import toolbox.Coord;
import toolbox.Link;
import toolbox.Tools;
import framework.Board;
import framework.SolverStrategy;
import framework.Board.Areas;

public class XChain implements SolverStrategy {

	private boolean optionsRemoved;
	private boolean valueSet;
	private String actionString;
	private int runcount = 0;
	Set<Coord> visitedCoords;

	@Override
	public Board run(Board board, Enum<Areas> area, Coord coord) {
		optionsRemoved = false;
		runcount++;

		List<Coord> areaCoords = board.getAreaAt(area, coord).getCoords();
		for (int option = 1; option <= Board.DIMENSION; option++) {
			visitedCoords = new HashSet<Coord>();
		for (Coord startcoord : areaCoords) {
		
			List<Integer> options = board.getTileAt(startcoord).getOptions()
					.copyList();
			
				if (options.contains(option) && !visitedCoords.contains(startcoord)) {

					doXChain(board, startcoord, startcoord,
							new ArrayList<Link>(), option);

					if (optionsRemoved || valueSet) {
						return board;
					}
				}
			}
		}

		return board;
	}

	@SuppressWarnings("unchecked")
	private void doXChain(Board board, Coord startcoord, Coord currentcoord,
			ArrayList<Link> chain, int option) {

			if (startcoord.equals(currentcoord) && chain.size() > 2) {

				for (Link link: chain) {
					visitedCoords.add(link.from());
				}
				
				Collections.sort(chain);
				int chainsize = chain.size();
				

				// nice loop rule 1
				// even number of links and alternating weak and strong links
				// note: strong links can act as weak links too

				if (chainsize % 2 == 0) {
					boolean nice_loop = true;
					boolean weak_ok = true;
					for (Link link : chain) {
						if (link.isStrong()) {
							weak_ok = true; // next link can be weak
						}
						if (link.isWeak() && weak_ok) { // next link MUST be strong
							weak_ok = false;
						} else {
							nice_loop = false;
							break;
						}
					}
					if (nice_loop) {
						// delete extra options around weak links
						actionString = "Nice loop rule 1: removed option: " + option + " around weak links chain: " + chain;
						boolean anyremoved = false;
						int[] optionarray = new int[] {option};
						for (Link link: chain) {
							if (link.isWeak()) {
								List<Coord> area_wo_linkcoords = board.getAreaAt(link.getArea(), link.from()).getCoords();
								area_wo_linkcoords.remove(link.from());
								area_wo_linkcoords.remove(link.to());
								if (board.removeOptionsAtCoords(optionarray, area_wo_linkcoords)) {
									anyremoved = true;
								}
							}
						}
						optionsRemoved = anyremoved;
					}
				} else {
					/*
					 * link size is uneven here we can have two nice loops with
					 * either 2x strong links in a row or 2x weak links.
					 */

					// nice loop rule 2
					boolean seen_2x_strong = false;
					Coord connectedAt = null;
					int strongcounter = 0;
					// special case : end and tail are strong
					if (chain.get(0).isStrong()
							&& chain.get(chain.size() - 1).isStrong()) {
						seen_2x_strong = true;
						connectedAt = chain.get(0).from();
					}
					for (Link link : chain) {
						if (link.isStrong()) {
							strongcounter++;
							if (strongcounter == 2 && !seen_2x_strong) {
								seen_2x_strong = true;
								connectedAt = link.from();
							}
						}
						else if (link.isWeak()) { 
							strongcounter = 0;
						}
					}
					if (seen_2x_strong) {
						// set value to option where the two strong links connect
						actionString = "Nice loop rule 2: set value of " + connectedAt + " to " + option + " chain: " + chain; 
						//board.getTileAt(connectedAt).setValue(option);
						//valueSet = true;
					}
					// nice loop rule 3
					boolean seen_2x_weak = false;
					connectedAt = null;
					int weakcounter = 0;
					// special case : end and tail are weak
					if (chain.get(0).isWeak()
							&& chain.get(chain.size() - 1).isWeak()) {
						seen_2x_weak = true;
						connectedAt = chain.get(0).from();
					}
					for (Link link : chain) {
						if (link.isStrong()) {
							weakcounter = 0;
						} else if (link.isWeak()) { 
							weakcounter++;
							if (weakcounter == 2 && !seen_2x_weak) {
								seen_2x_weak = true;
								connectedAt = link.from();
							}
						}
					}
					if (seen_2x_weak) {
						// delete option at connected weak links
						actionString = "Nice loop rule 3: " + "removed option: " + option + " at " + connectedAt + " chain: " + chain;
						optionsRemoved = board.removeOptionAtCoord(option, connectedAt);
					}
				}

			} else {
				
				createLink(board, startcoord, Board.Areas.BOX, currentcoord,
						(ArrayList<Link>) chain.clone(), option);
				createLink(board, startcoord, Board.Areas.COL, currentcoord,
						(ArrayList<Link>) chain.clone(), option);
				createLink(board, startcoord, Board.Areas.ROW, currentcoord,
						(ArrayList<Link>) chain.clone(), option);
			}
		
	}

	private void createLink(Board board, Coord startcoord, Board.Areas area,
			Coord currentcoord, ArrayList<Link> chain, int option) {

		boolean legalcycle = true;
		if (chain.size() > 1) {
			legalcycle = inspectCycle(chain);
		}
		if (legalcycle) {
			
		List<Coord> coords = Tools.cords_containing_option(board,
				area, currentcoord, option);
		
		int options_in_area = coords.size();

		coords.remove(currentcoord);
		for (Link link : chain) {
			/*
			 * removing coords involved in current chain except the very first
			 * since I would like to be able to close the chain
			 */
			coords.remove(link.to());
		}

		if (options_in_area > 0) {
			if (options_in_area == 2) { // strong
				if (coords.size() > 0) {
					chain.add(new Link(currentcoord, coords.get(0), area, true, chain
							.size()));
					doXChain(board, startcoord, coords.get(0), chain, option);
				}
			}
			if (options_in_area > 2) { // weak

					for (Coord coord : coords) {

						ArrayList<Link> chainbranch = new ArrayList<Link>();
						chainbranch.addAll(chain);
						chainbranch.add(new Link(currentcoord, coord, area, false,
								chainbranch.size()));
						// continue current chain in different directions
						doXChain(board, startcoord, coord, chainbranch, option);

					}
				}
			}
		}
	}
	
	private boolean inspectCycle(List<Link> chain) {
		// inspect chain
		List<Link> chaincopy = new ArrayList<Link>();
		boolean lastWasStrong = false;
		for (Link link: chain) {
			if (link.isStrong()) {
				if (lastWasStrong) {
					chaincopy.add(new Link(link.from(), link.to(), link.getArea(), false, link.getNumber()));
					lastWasStrong = false;
				}
				else {
					chaincopy.add(new Link(link.from(), link.to(), link.getArea(), link.isStrong(), link.getNumber()));
					lastWasStrong = true;
				}
			}
			else {
				lastWasStrong = false;
				chaincopy.add(new Link(link.from(), link.to(), link.getArea(), link.isStrong(), link.getNumber()));
			}
		}
		
		
		Collections.sort(chaincopy);
		// adding the two first links to simulate a closed chain
		Link link1 = chaincopy.get(0);
		Link l1copy = new Link(link1.from(), link1.to(), link1.getArea(), link1.isStrong(), chain.size()+1);
		Link link2 = chaincopy.get(1);
		Link l2copy = new Link(link2.from(), link2.to(), link2.getArea(), link2.isStrong(), chain.size()+1);
		chaincopy.add(l1copy);chaincopy.add(l2copy);
		Collections.sort(chaincopy);
		
		int weakcounter = 0;
		boolean two_weaks = false;
		for (Link link: chaincopy) {
			if (link.isWeak()) {
				weakcounter++;
				if (weakcounter == 3) {
					//illegal cycle
					return false;
				}
				if (weakcounter == 2) {
					if (two_weaks) {
						//illegal cycle - 2x 2 weaks
						return false;								
					}
					two_weaks = true;
				}
			}
			else {
				weakcounter = 0;
			}
		}
		
		return true;
	}

	@Override
	public Board.Areas[] areas() {
		Board.Areas[] areas = { Board.Areas.BOX };
		return areas;
	}

	@Override
	public boolean valueSet() {
		return valueSet;
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
		return "XChain";
	}

	@Override
	public int runCount() {
		return runcount;
	}
}
