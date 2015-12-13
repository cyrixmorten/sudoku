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
import standard.StandardTile;
import toolbox.Coord;
import toolbox.Options;
import toolbox.Tools;


public class TestTile {

	private static Tile tile;
	
	@Before
	public void setup() {
		tile = new StandardTile(new Coord(0,0), 0);
	}
	
	@Test
    public void getCoord() {
		assertEquals(tile.getCoordinate(), new Coord(0,0));
	}
	
	@Test
    public void getOptions() {
		assertEquals(tile.getOptions(), new Options());
	}
	
	@Test
    public void getValue() {
		assertEquals(tile.getValue(), 0);
	}
	
	@Test
    public void setOptions() {
		Options options = new Options();
		options.removeOption(1);
		options.removeOption(8);
		tile.setOptions(options.copyList());
		assertEquals(tile.getOptions(), options);
	}
	
	@Test
    public void removeOptionTrue() {
		assertTrue(tile.removeOption(2));
	}
	
	@Test
    public void removeOptionFalse() {
		removeOptionTrue();
		assertFalse(tile.removeOption(2));
	}
	
	@Test
    public void removeOptionsTrue() {
		assertTrue(tile.removeOptions(new Options().copyList()));
	}
	
	@Test
    public void removeOptionsFalse() {
		removeOptionsTrue();
		assertFalse(tile.removeOptions(new Options().copyList()));
	}
	
	@Test
    public void setValueTrue() {
		assertTrue(tile.setValue(1));
	}
	
	@Test
    public void setValueFalse() {
		setValueTrue();
		assertFalse(tile.setValue(2));
	}
	
	@Test
    public void equalsTrue() {
		Tile tile2 = new StandardTile(new Coord(0,0), 0);
		assertEquals(tile, tile2);
	}
	
	@Test
    public void equalsFalseValue() {
		Tile tile2 = new StandardTile(new Coord(0,0), 1);
		assertFalse(tile.equals(tile2));
	}
	
	@Test
    public void equalsFalseCoord() {
		Tile tile2 = new StandardTile(new Coord(8,8), 0);
		assertFalse(tile.equals(tile2));
	}
	
	
}