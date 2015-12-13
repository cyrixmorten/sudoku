package toolbox;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import framework.Area;
import framework.Board;
import framework.Tile;
import framework.Board.Areas;

public class Tools {
	
	/*
	 * runs through all horizontal lines and makes sure the values 1..DIMENSION
	 * is used once.
	 */
	public static boolean iscorrectlySolved(Board board) {
		for (int h = 0; h < Board.DIMENSION; h++) {
			Options checklist = new Options();
			Area hline = board.getAreaAt(Areas.ROW, new Coord(0, h));
			for (Coord coord : hline.getCoords()) {
				int value = board.getTileAt(coord).getValue();
				checklist.removeOption(value);
			}
			if (!checklist.copyList().isEmpty()) {
				return false;
			}
		}
		return true;
	}
	
	public static int[] StringToIntArray(String string) {
		int[] intarray = new int[81];
		char[] charsetup = string.toCharArray();
		
		int index = 0;
		for (char achar: charsetup) {
			intarray[index] = Integer.parseInt(String.valueOf(achar));
			index++;
		}
		return intarray;
	}

	public static int areaNumber(Enum<Areas> area, Coord c) {
		int areanumber = (area == Board.Areas.ROW) ? c.getH() : c.getV();
		if (area == Board.Areas.BOX) {
			areanumber = (c.getH() / Board.TILES_PER_BOX * Board.TILES_PER_BOX + (c.getV() / Board.TILES_PER_BOX + 1));
		}
		return areanumber;
	}
	
	public static boolean coords_in_same_area(Board board, Coord c1, Coord c2) {
		if (c1.getH() == c2.getH()			 // same row
				||c1.getV() == c2.getV()	 // same col
				||board.getAreaAt(Board.Areas.BOX, c1)
				.getCoords().contains(c2)) { // same box
			return true;
		}
		return false;
	}
	
	public static Set<Coord> all_areas_around(Board board, Coord c) {
		Set<Coord> set = new HashSet<Coord>();
		
		set.addAll(board.getAreaAt(Board.Areas.BOX, c).getCoords());
		set.addAll(board.getAreaAt(Board.Areas.COL, c).getCoords());
		set.addAll(board.getAreaAt(Board.Areas.ROW, c).getCoords());
		
		return set;
	}
	
	public static Map<Integer, List<Coord>> option_contained_in_coords(
			Board board, Enum<Board.Areas> area, Coord c) {
		Map<Integer, List<Coord>> optionincoords = new LinkedHashMap<Integer, List<Coord>>();
		for (Coord coord : board.getAreaAt(area, c).getCoords()) {
			Tile tile = board.getTileAt(coord);
			Options options = tile.getOptions();
			for (Integer option : options.copyList()) {
				List<Coord> coords = new ArrayList<Coord>();
				if (optionincoords.get(option) != null) {
					coords = optionincoords.get(option);
				}
				coords.add(coord);
				optionincoords.put(option, coords);
			}
		}
		return optionincoords;
	}
	

	public static Map<Coord, Integer> coord_to_optionsize(Board board,
			Enum<Board.Areas> area, Coord c) {
		Map<Coord, Integer> coordoptionsize = new LinkedHashMap<Coord, Integer>();
		for (Coord coord : board.getAreaAt(area, c).getCoords()) {
			Tile tile = board.getTileAt(coord);
			coordoptionsize.put(coord, tile.getOptions().size());
		}
		return coordoptionsize;
	}
	
	public static List<Coord> coords_with_optionsize(Board board, Enum<Board.Areas> area, Coord c, int size) {
		List<Coord> coordoptionsize = new ArrayList<Coord>();
		for (Coord coord : board.getAreaAt(area, c).getCoords()) {
			Tile tile = board.getTileAt(coord);
			if (tile.getOptions().size() == size) {
				coordoptionsize.add(coord);
			}
		}
		return coordoptionsize;
	}
	
	public static List<Coord> cords_containing_option(
			Board board, Enum<Board.Areas> area, Coord c, Integer option) {
		List<Coord> coords = new ArrayList<Coord>();
		for (Coord coord : board.getAreaAt(area, c).getCoords()) {
			Tile tile = board.getTileAt(coord);
			Options options = tile.getOptions();
			if (options.contains(option)) {
				coords.add(coord);
			}
		}
		return coords;
	}
	
	public static List<Coord> intersect_all_areas_around_coords(Board board, Coord a, Coord b) {
		
		Set<Coord> aCoords = all_areas_around(board, a);

		Set<Coord> bCoords = all_areas_around(board, b);

		List<Coord> intersectedCoords = new ArrayList<Coord>();
		// intesect bcCoords and acCoords
		intersectedCoords.addAll(aCoords);
		intersectedCoords.retainAll(bCoords);
		
		return intersectedCoords;
	}
	
	public static void printBoard(Board board) {

		System.out.println(" _,.-'-.,__board__,.-'-.,_");
		for (int h = 0; h < Board.DIMENSION; h++) {
			StringBuilder b = new StringBuilder();
			if (h == 0) {
				b
						.append(" +-------+-------+-------+   +-----------------------------------------------------------+-----------------------------------------------------------+-----------------------------------------------------------+\n");
			}
			// values
			for (int v = 0; v < Board.DIMENSION; v++) {
				int prevvboxnumber = ((Board.DIMENSION / Board.TILES_PER_BOX) + ((v - 1)
						/ Board.TILES_PER_BOX + 1));
				int currentvboxnumber = ((Board.DIMENSION / Board.TILES_PER_BOX) + (v
						/ Board.TILES_PER_BOX + 1));
				if (v == 0 || prevvboxnumber != currentvboxnumber)
					b.append(" |");
				Coord currentCord = new Coord(v, h);
				b.append(" " + board.getTileAt(currentCord).getValue());
				if (v == Board.DIMENSION - 1)
					b.append(" |");
			}
			// options
			b.append("  ");
			for (int v = 0; v < Board.DIMENSION; v++) {
				int prevvboxnumber = ((Board.DIMENSION / Board.TILES_PER_BOX) + ((v - 1)
						/ Board.TILES_PER_BOX + 1));
				int currentvboxnumber = ((Board.DIMENSION / Board.TILES_PER_BOX) + (v
						/ Board.TILES_PER_BOX + 1));
				if (v == 0 || prevvboxnumber != currentvboxnumber)
					b.append(" | ");
				Coord currentCord = new Coord(v, h);
				b.append(board.getTileAt(currentCord).getOptions());
				if (v == Board.DIMENSION - 1)
					b.append(" | ");
			}
			int currenthboxnumber = ((h) / Board.TILES_PER_BOX * (Board.DIMENSION / Board.TILES_PER_BOX));
			int nexthboxnumber = ((h + 1) / Board.TILES_PER_BOX * (Board.DIMENSION / Board.TILES_PER_BOX));
			if (nexthboxnumber != currenthboxnumber) {
				b.append("\n");
				b
						.append(" +-------+-------+-------+   +-----------------------------------------------------------+-----------------------------------------------------------+-----------------------------------------------------------+");
			}

			System.out.println(b.toString());
		}
	}
	
}
