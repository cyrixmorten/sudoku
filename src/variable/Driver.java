package variable;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.text.html.Option;

import framework.Area;
import framework.Board;
import framework.SolverStrategy;
import framework.Tile;
import solver.SudokuSolver;
import solver.solverFactories.QuickAllStrategiesFactory;
import solver.solverStrategies.BoxLineReduction;
import solver.solverStrategies.HiddenTriplets;
import solver.solverStrategies.ShowOptions;
import standard.StandardBoard;
import toolbox.Coord;
import toolbox.Options;
import toolbox.Tools;

public class Driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String list = "024090008800402900719000240075804300240900587038507604082000059007209003490050000";
		boolean printsteps = false;
		
		StandardBoard testboard = new StandardBoard(list);
		//testboard.getTileAt(new Coord(3,6)).removeOption(7);
		SudokuSolver solver = new SudokuSolver(testboard, printsteps, new QuickAllStrategiesFactory());
		Scanner sc = new Scanner(System.in);
		while (!sc.nextLine().equals("quit")) {
			solver.run();
			testboard = (StandardBoard) solver.getBoard();
			solver.printResult();
			System.out.println("solved correctly: " + Tools.iscorrectlySolved(testboard));	
		}
		
	}


}
/*

		int[] list = { 0, 0, 0,  0, 0, 0,  0, 0, 0,
					   0, 0, 0,  0, 0, 0,  0, 0, 0,
					   0, 0, 0,  0, 0, 0,  0, 0, 0,

					   0, 0, 0,  0, 0, 0,  0, 0, 0,
					   0, 0, 0,  0, 0, 0,  0, 0, 0, 
					   0, 0, 0,  0, 0, 0,  0, 0, 0,

					   0, 0, 0,  0, 0, 0,  0, 0, 0,
					   0, 0, 0,  0, 0, 0,  0, 0, 0,
					   0, 0, 0,  0, 0, 0,  0, 0, 0 };
					   
		Intersection removal			   
					   
		int[] list = { 
					   0, 0, 0,  9, 2, 1,  0, 0, 3,
					   0, 0, 9,  0, 0, 0,  0, 6, 0,
					   0, 0, 0,  0, 0, 0,  5, 0, 0,
	
					   0, 8, 0,  4, 0, 3,  0, 0, 6,
					   0, 0, 7,  0, 0, 0,  8, 0, 0, 
					   5, 0, 0,  7, 0, 0,  0, 4, 0,
	
					   0, 0, 3,  0, 0, 0,  0, 0, 0,
					   0, 2, 0,  0, 0, 0,  7, 0, 0,
					   8, 0, 0,  1, 9, 5,  0, 0, 0 }; 
					   
	    Hidden triple
					   
		int[] list = { 
					   3, 0, 0,  0, 0, 0,  0, 0, 0,
					   9, 7, 0,  0, 1, 0,  0, 0, 0,
					   6, 0, 0,  5, 8, 3,  0, 0, 0,
			
					   2, 0, 0,  0, 0, 0,  9, 0, 0,
					   5, 0, 0,  6, 2, 1,  0, 0, 3, 
					   0, 0, 8,  0, 0, 0,  0, 0, 5,
			
					   0, 0, 0,  4, 3, 5,  0, 0, 2,
					   0, 0, 0,  0, 9, 0,  0, 5, 6,
					   0, 0, 0,  0, 0, 0,  0, 0, 1 }; 
		   

		Hidden pair, hidden triplets, Box/Line Reduction, Y-Wing
		   
		int[] list = { 
					   4, 0, 0,  0, 1, 0,  0, 0, 0,
					   0, 0, 0,  3, 0, 9,  0, 4, 0,
					   0, 7, 0,  0, 0, 5,  0, 0, 9,

					   0, 0, 0,  0, 6, 0,  0, 2, 1,
					   0, 0, 4,  0, 7, 0,  6, 0, 0, 
					   1, 9, 0,  0, 5, 0,  0, 0, 0,

					   9, 0, 0,  4, 0, 0,  0, 7, 0,
					   0, 3, 0,  6, 0, 8,  0, 0, 0,
					   0, 0, 0,  0, 3, 0,  0, 0, 6 };
					   
		Y-Wing
		
		int[] list = { 
					   6, 4, 5,  0, 1, 0,  8, 9, 3,
					   7, 3, 8,  4, 5, 9,  6, 2, 1,
					   2, 1, 9,  6, 3, 8,  7, 4, 5,
	
					   5, 9, 7,  0, 6, 0,  1, 8, 4,
					   4, 8, 1,  9, 7, 5,  0, 0, 0, 
					   3, 2, 6,  8, 4, 1,  5, 7, 9,
	
					   9, 0, 2,  0, 8, 0,  0, 1, 0,
					   8, 0, 3,  1, 9, 0,  0, 0, 0,
					   1, 6, 4,  0, 2, 0,  9, 0, 8 };
					   
		X-Wing XY-Chain			    	
					   
		int[] list = { 
				   2, 9, 4,  1, 6, 7,  8, 3, 5,
				   7, 3, 6,  0, 8, 0,  1, 9, 2,
				   1, 5, 8,  0, 9, 0,  4, 7, 6,

				   0, 0, 7,  0, 1, 0,  0, 2, 9,
				   0, 2, 9,  7, 4, 0,  5, 1, 8, 
				   5, 8, 1,  0, 2, 9,  0, 4, 7,

				   9, 0, 2,  0, 3, 0,  7, 5, 4,
				   8, 0, 5,  0, 7, 0,  9, 6, 3,
				   0, 7, 3,  9, 5, 0,  2, 8 ,1};
				   
	    XY-Chain
	   
		int[] list = { 
				   5, 0, 6,  0, 9, 4,  0, 0, 3,
				   0, 0, 0,  0, 8, 6,  9, 2, 5,
				   8, 9, 2,  5, 1, 3,  6, 4, 7,

				   7, 3, 8,  6, 2, 9,  0, 5, 0,
				   1, 5, 4,  3, 7, 8,  2, 9, 6, 
				   6, 2, 9,  4, 5, 1,  7, 3, 8,

				   9, 8, 7,  1, 4, 5,  3, 6, 2,
				   0, 0, 0,  9, 6, 0,  0, 0, 0,
				   0, 0, 0,  8, 3, 0,  0, 0 ,9};	   
		   */
