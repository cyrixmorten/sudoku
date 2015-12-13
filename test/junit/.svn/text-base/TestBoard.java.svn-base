package junit;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;


import framework.Board;
import framework.SolverStrategy;
import framework.Tile;

import solver.SudokuSolver;
import solver.solverFactories.QuickAllStrategiesFactory;
import solver.solverFactories.CustomStrategiesFactory;
import solver.solverStrategies.BoxLineReduction;
import solver.solverStrategies.HiddenPair;
import solver.solverStrategies.HiddenTriplets;
import solver.solverStrategies.NakedTriplets;
import solver.solverStrategies.NakedTwins;
import solver.solverStrategies.OneOption;
import solver.solverStrategies.PointingPairs;
import solver.solverStrategies.ShowOptions;
import solver.solverStrategies.SimpleColouring;
import solver.solverStrategies.SwordFish;
import solver.solverStrategies.UniqueOption;
import solver.solverStrategies.XWing;
import solver.solverStrategies.XYChain;
import solver.solverStrategies.YWing;
import standard.StandardBoard;
import toolbox.Coord;
import toolbox.Tools;


public class TestBoard {

	private static Board board;
	
	@Before
	public void setUp() {
		board = new StandardBoard("000041000060000200000000000320600000000050041700000000000200300048000000501000000");
	}
	
	@Test
    public void constructor2() {
		board = new StandardBoard(
				new int[]{ 
						   5, 0, 6,  0, 9, 4,  0, 0, 3,
						   0, 0, 0,  0, 8, 6,  9, 2, 5,
						   8, 9, 2,  5, 1, 3,  6, 4, 7,

						   7, 3, 8,  6, 2, 9,  0, 5, 0,
						   1, 5, 4,  3, 7, 8,  2, 9, 6, 
						   6, 2, 9,  4, 5, 1,  7, 3, 8,

						   9, 8, 7,  1, 4, 5,  3, 6, 2,
						   0, 0, 0,  9, 6, 0,  0, 0, 0,
						   0, 0, 0,  8, 3, 0,  0, 0 ,9 });
	}	
	
	@Test
    public void getBoard() {
		assertTrue(board.getBoard().equals(board));
	}
	
	@Test
    public void validMove() {
		board.getTileAt(new Coord(0,0)).setValue(2);
		assertTrue(board.validMove());
	}
	
	@Test
    public void notvalidMoveValue() {
		board.getTileAt(new Coord(0,0)).setValue(1);
		assertFalse(board.validMove());
	}
	
	
	@Test
    public void notvalidMoveOptionsRow() {
		List<Coord> set = board.getAreaAt(Board.Areas.ROW, new Coord(0,0)).getCoords();
		set.remove(new Coord(0,0));
		board.removeOptionsAtCoords(new int[] {1,2,3,4,5,6,7,8,9}, set);
		board.removeOptionAtCoord(2, new Coord(0,0));
		assertFalse(board.validMove());
	}
	
	@Test
    public void notvalidMoveOptionsCol() {
		List<Coord> set = board.getAreaAt(Board.Areas.COL, new Coord(0,0)).getCoords();
		set.remove(new Coord(0,0));
		board.removeOptionsAtCoords(new int[] {1,2,3,4,5,6,7,8,9}, set);
		board.removeOptionAtCoord(2, new Coord(0,0));
		assertFalse(board.validMove());
	}
	
	@Test
    public void notvalidMoveOptionsBox() {
		List<Coord> set = board.getAreaAt(Board.Areas.BOX, new Coord(0,0)).getCoords();
		set.remove(new Coord(0,0));
		board.removeOptionsAtCoords(new int[] {1,2,3,4,5,6,7,8,9}, set);
		board.removeOptionAtCoord(2, new Coord(0,0));
		assertFalse(board.validMove());
	}
	
	@Test (expected=IndexOutOfBoundsException.class)
    public void getTilesOutOfBouds() {
		Tile[][] tiles = board.getTiles();
		Tile t = tiles[Board.DIMENSION+1][Board.DIMENSION+1];
	}
	

	@Test
    public void getTiles() {
		Tile[][] tiles = board.getTiles();
		for (int i = 0; i < Board.DIMENSION; i++) {
			for (int j = 0; j < Board.DIMENSION; j++) {
				assertEquals(tiles[i][j], board.getTileAt(new Coord(i,j)));
			}
		}
	}
	
	@Test
	public void setTiles() {
		Board board2 = new StandardBoard("742895316805010429109030587598361742613040958427958631971400265386529174254176893");
		board.setTiles(board2.getTiles());
		assertTrue(board.getTiles().equals(board2.getTiles()));
	}
	
	@Test
	public void getAreaAtInvalid() {
		assertNull(board.getAreaAt(Board.Areas.ZERO, new Coord(1,1)));
	}
	
	@Test
	public void getAreaAtValid() {
		assertNotNull(board.getAreaAt(Board.Areas.BOX, new Coord(0,0)));
	}
	
	@Test
	public void getAreaOptionsAt() {
		assertNotNull(board.getAreaOptionsAt(Board.Areas.BOX, new Coord(0,0)));
	}
	
	@Test
	public void removeOptionAtCoordTrue() {
		assertTrue(board.removeOptionAtCoord(1, new Coord(8,8)));
	}
	
	@Test
	public void removeOptionAtCoordFalse() {
		board.removeOptionAtCoord(1, new Coord(8,8));
		assertFalse(board.removeOptionAtCoord(1, new Coord(8,8)));
	}
	
	@Test
	public void removeOptionsAtCoordsTrue() {
		List<Coord> list = new ArrayList<Coord>();
		list.add(new Coord(0,0)); list.add(new Coord(0,1));
		assertTrue(board.removeOptionsAtCoords(new int[] {1,2}, list));
	}
	
	@Test
	public void removeOptionsAtCoordsFalse() {
		removeOptionsAtCoordsTrue();
		List<Coord> list = new ArrayList<Coord>();
		list.add(new Coord(0,0)); list.add(new Coord(0,1));
		assertFalse(board.removeOptionsAtCoords(new int[] {1,2}, list));
	}
	
	
	
}