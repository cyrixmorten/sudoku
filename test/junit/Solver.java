package junit;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;


import framework.Board;
import framework.SolverStrategy;

import solver.SudokuSolver;
import solver.solverFactories.HintsAllStrategiesFactory;
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


public class Solver {

	private static SudokuSolver solver;
	private static Board board;
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void Naked_triples() {
		board = new StandardBoard(
				new int[] { 0,0,0,0,0,0,0,0,0,0,0,1,9,0,0,5,0,0,5,6,0,3,1,0,0,9,0,1,0,0,6,0,0,0,2,8,0,0,4,0,0,0,7,0,0,2,7,0,0,0,4,0,0,3,0,4,0,0,6,8,0,3,5,0,0,2,0,0,5,9,0,0,0,0,0,0,0,0,0,0,0 }); 
		
		solver = new SudokuSolver(board);
		solver.solve();
		
		assertTrue(Tools.iscorrectlySolved(board));
	}
	
	@Test
	public void Hidden_triples() {
		board = new StandardBoard(
				new int[] { 
						   3, 0, 0,  0, 0, 0,  0, 0, 0,
						   9, 7, 0,  0, 1, 0,  0, 0, 0,
						   6, 0, 0,  5, 8, 3,  0, 0, 0,
				
						   2, 0, 0,  0, 0, 0,  9, 0, 0,
						   5, 0, 0,  6, 2, 1,  0, 0, 3, 
						   0, 0, 8,  0, 0, 0,  0, 0, 5,
				
						   0, 0, 0,  4, 3, 5,  0, 0, 2,
						   0, 0, 0,  0, 9, 0,  0, 5, 6,
						   0, 0, 0,  0, 0, 0,  0, 0, 1 }); 
		
		solver = new SudokuSolver(board);
		solver.solve();
		
		assertTrue(Tools.iscorrectlySolved(board));
	}
	
	@Test
	public void Intersection_removal() {
		board = new StandardBoard(
				new int[] { 
						   0, 0, 0,  9, 2, 1,  0, 0, 3,
						   0, 0, 9,  0, 0, 0,  0, 6, 0,
						   0, 0, 0,  0, 0, 0,  5, 0, 0,
		
						   0, 8, 0,  4, 0, 3,  0, 0, 6,
						   0, 0, 7,  0, 0, 0,  8, 0, 0, 
						   5, 0, 0,  7, 0, 0,  0, 4, 0,
		
						   0, 0, 3,  0, 0, 0,  0, 0, 0,
						   0, 2, 0,  0, 0, 0,  7, 0, 0,
						   8, 0, 0,  1, 9, 5,  0, 0, 0  }); 
		
		solver = new SudokuSolver(board);
		solver.solve();
		
		assertTrue(Tools.iscorrectlySolved(board));
	}
	
	@Test
	public void Moderate() {
		board = new StandardBoard(
				new int[]{ 
						   4, 0, 0,  0, 1, 0,  0, 0, 0,
						   0, 0, 0,  3, 0, 9,  0, 4, 0,
						   0, 7, 0,  0, 0, 5,  0, 0, 9,

						   0, 0, 0,  0, 6, 0,  0, 2, 1,
						   0, 0, 4,  0, 7, 0,  6, 0, 0, 
						   1, 9, 0,  0, 5, 0,  0, 0, 0,

						   9, 0, 0,  4, 0, 0,  0, 7, 0,
						   0, 3, 0,  6, 0, 8,  0, 0, 0,
						   0, 0, 0,  0, 3, 0,  0, 0, 6 });
		
		solver = new SudokuSolver(board);
		solver.solve();
		
		assertTrue(Tools.iscorrectlySolved(board));
	}
	
	@Test
	public void XWing() {
		board = new StandardBoard(
				new int[]{ 
						   2, 9, 4,  1, 6, 7,  8, 3, 5,
						   7, 3, 6,  0, 8, 0,  1, 9, 2,
						   1, 5, 8,  0, 9, 0,  4, 7, 6,
			
						   0, 0, 7,  0, 1, 0,  0, 2, 9,
						   0, 2, 9,  7, 4, 0,  5, 1, 8, 
						   5, 8, 1,  0, 2, 9,  0, 4, 7,
			
						   9, 0, 2,  0, 3, 0,  7, 5, 4,
						   8, 0, 5,  0, 7, 0,  9, 6, 3,
						   0, 7, 3,  9, 5, 0,  2, 8 ,1});
		
		solver = new SudokuSolver(board);
		solver.solve();
		
		assertTrue(Tools.iscorrectlySolved(board));
	}
	
	@Test
	public void YWing() {
		board = new StandardBoard(
				new int[]{ 
						   6, 4, 5,  0, 1, 0,  8, 9, 3,
						   7, 3, 8,  4, 5, 9,  6, 2, 1,
						   2, 1, 9,  6, 3, 8,  7, 4, 5,
		
						   5, 9, 7,  0, 6, 0,  1, 8, 4,
						   4, 8, 1,  9, 7, 5,  0, 0, 0, 
						   3, 2, 6,  8, 4, 1,  5, 7, 9,
		
						   9, 0, 2,  0, 8, 0,  0, 1, 0,
						   8, 0, 3,  1, 9, 0,  0, 0, 0,
						   1, 6, 4,  0, 2, 0,  9, 0, 8 });
		
		solver = new SudokuSolver(board);
		solver.solve();
		
		assertTrue(Tools.iscorrectlySolved(board));
	}
	
	@Test
	public void SwordFish() {
		board = new StandardBoard(
				new int[]{ 
						   0, 5, 0,  0, 3, 0,  6, 0, 2,
						   6, 4, 2,  8, 9, 5,  3, 1, 7,
						   0, 3, 7,  0, 2, 0,  8, 0, 0,
		
						   0, 2, 3,  5, 0, 4,  7, 0, 0,
						   4, 0, 6,  0, 0, 0,  5, 2, 0, 
						   5, 7, 1,  9, 6, 2,  4, 8, 3,
		
						   2, 1, 4,  0, 0, 0,  9, 0, 0,
						   7, 6, 0,  1, 0, 9,  2, 3, 4,
						   3, 0, 0,  2, 4, 0,  1, 7, 0});
		
		List<SolverStrategy> strategies = new ArrayList<SolverStrategy>();

		strategies.add(new ShowOptions());
		strategies.add(new OneOption());
		strategies.add(new UniqueOption());
		strategies.add(new NakedTwins());
		strategies.add(new NakedTriplets());
		strategies.add(new HiddenPair());  
		strategies.add(new HiddenTriplets());
		strategies.add(new PointingPairs());
		strategies.add(new BoxLineReduction());
		strategies.add(new XWing());
		strategies.add(new SimpleColouring());
		strategies.add(new YWing());
		
		solver = new SudokuSolver(board, new CustomStrategiesFactory(strategies));
		solver.solve();

		assertFalse(Tools.iscorrectlySolved(board));
		
		strategies.add(new SwordFish());
		
		List<Integer> optionremoved = new ArrayList<Integer>();
		optionremoved.add(8);
		
		List<Integer> options1 = board.getTileAt(new Coord(2,8)).getOptions().copyList();
		List<Integer> options2 = board.getTileAt(new Coord(4,4)).getOptions().copyList();
		List<Integer> options3 = board.getTileAt(new Coord(4,6)).getOptions().copyList();

		assertTrue(options1.contains(8) && options2.contains(8) && options3.contains(8));
		
		solver = new SudokuSolver(board, new CustomStrategiesFactory(strategies));
		solver.solve();
		
		List<Integer> options11 = board.getTileAt(new Coord(2,8)).getOptions().copyList();
		List<Integer> options22 = board.getTileAt(new Coord(4,4)).getOptions().copyList();
		List<Integer> options33 = board.getTileAt(new Coord(4,6)).getOptions().copyList();

		assertTrue(!options11.contains(8) && !options22.contains(8) && !options33.contains(8));
		assertFalse(Tools.iscorrectlySolved(board));
		
		strategies.add(new XYChain());
		solver = new SudokuSolver(board, new CustomStrategiesFactory(strategies));
		solver.solve();

		assertTrue(Tools.iscorrectlySolved(board));
	}
	
	@Test
	public void XYChain() {
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
		
		solver = new SudokuSolver(board);
		solver.solve();
		
		assertTrue(Tools.iscorrectlySolved(board));
	}
	
	@Test
	public void Tough() {
		board = new StandardBoard(
				new int[]{ 3,0,9,0,0,0,4,0,0,2,0,0,7,0,9,0,0,0,0,8,7,0,0,0,0,0,0,7,5,0,0,6,0,2,3,0,6,0,0,9,0,4,0,0,8,0,2,8,0,5,0,0,4,1,0,0,0,0,0,0,5,9,0,0,0,0,1,0,6,0,0,7,0,0,6,0,0,0,1,0,4 });
		
		solver = new SudokuSolver(board);
		solver.solve();
		
		assertTrue(Tools.iscorrectlySolved(board));
	}
	
	@Test
	public void XCycleWeakLink() {
		board = new StandardBoard("008003102002810306314260980923648700476351298185900634047030820209080500801700460");
		
		solver = new SudokuSolver(board);
		solver.solve();
		
		assertTrue(Tools.iscorrectlySolved(board));
	}
	
	@Test
	public void XCycleStrongLink() {
		board = new StandardBoard("723659481050834027004721305246193758507400003300570046960345072072900534435207009");
		
		solver = new SudokuSolver(board);
		solver.solve();
		
		assertTrue(Tools.iscorrectlySolved(board));
	}
	
	@Test
	public void Easy17clues() {
		board = new StandardBoard("000041000060000200000000000320600000000050041700000000000200300048000000501000000");
		
		solver = new SudokuSolver(board);
		solver.solve();
		
		assertTrue(Tools.iscorrectlySolved(board));
	}
	
	@Test
	public void Hard17clues() {
		board = new StandardBoard("742895316805010429109030587598361742613040958427958631971400265386529174254176893");
		
		solver = new SudokuSolver(board);
		solver.solve();
		
		assertTrue(Tools.iscorrectlySolved(board));
	}
	
	@Test
	public void SimpleColRule2() {
		board = new StandardBoard("123000587005817239987000164051008473390750618708100925076000891530081746810070352");
		
		solver = new SudokuSolver(board);
		solver.solve();
		
		assertTrue(Tools.iscorrectlySolved(board));
	}
	
	@Test
	public void SimpleColRule5() {
		board = new StandardBoard("004630500605401003370059640938060154457198362216345987043506019060903405509014036");
		
		solver = new SudokuSolver(board);
		solver.solve();
		
		assertTrue(Tools.iscorrectlySolved(board));
	}
	
	@Test
	public void XCycles() {
		board = new StandardBoard(
				new int[]{ 0,0,8,0,0,3,1,0,2,0,0,2,8,1,0,3,0,6,3,1,4,2,6,0,9,8,0,9,2,3,6,4,8,7,0,0,4,7,6,3,5,1,2,9,8,1,8,5,9,0,0,6,3,4,0,4,7,0,3,0,8,2,0,2,0,9,0,8,0,5,0,0,8,0,1,7,0,0,4,6,0 });
		
		solver = new SudokuSolver(board);
		solver.solve();
		
		assertTrue(Tools.iscorrectlySolved(board));
	}
	
	@Test
	public void Diabolical() {
		board = new StandardBoard(
				new int[]{ 0,0,0,7,0,4,0,0,5,0,2,0,0,1,0,0,7,0,0,0,0,0,8,0,0,0,2,0,9,0,0,0,6,2,5,0,6,0,0,0,7,0,0,0,8,0,5,3,2,0,0,0,1,0,4,0,0,0,9,0,0,0,0,0,3,0,0,6,0,0,9,0,2,0,0,4,0,7,0,0,0 });
		
		solver = new SudokuSolver(board);
		solver.solve();
		
		assertTrue(Tools.iscorrectlySolved(board));
	}
	
	@Test
	public void JellyFish() {
		board = new StandardBoard("024090008800402900719000240075804300240900587038507604082000059007209003490050000");
		
		solver = new SudokuSolver(board);
		solver.solve();
		
		assertTrue(Tools.iscorrectlySolved(board));
	}
	
	@Test
	public void Easy17cluesHintsFactory() {
		board = new StandardBoard("000041000060000200000000000320600000000050041700000000000200300048000000501000000");
		
		solver = new SudokuSolver(board, new HintsAllStrategiesFactory());
		solver.solve();
		
		assertTrue(Tools.iscorrectlySolved(board));
	}
	
	
}
