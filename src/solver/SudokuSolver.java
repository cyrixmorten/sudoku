package solver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import solver.solverFactories.QuickAllStrategiesFactory;
import solver.solverStrategies.*;
import toolbox.Coord;
import toolbox.Options;
import toolbox.Tools;
import framework.Board;
import framework.SolverFactory;
import framework.SolverStrategy;
import framework.Tile;
import framework.Board.Areas;

public class SudokuSolver extends Thread {

	private List<SolverStrategy> strategies;
	private boolean progress;
	private boolean stuck;
	private int deadendCounter;
	private Board board;
	private boolean onestep;
	private String stepmsg = "";
	
	// assumes all strategies and not onestep
	public SudokuSolver(Board board) {
		this.board = board;
		SolverFactory solverfactory = new QuickAllStrategiesFactory();
		strategies = solverfactory.getStrategies();
	}
	
	public SudokuSolver(Board board, SolverFactory solverfactory) {
		this.board = board;
		
		strategies = solverfactory.getStrategies();
		
	}

	public SudokuSolver(Board board, boolean onestep, SolverFactory solverfactory) {
		this.board = board;
		this.onestep = onestep;

		strategies = solverfactory.getStrategies();
		
	}

	public Board getBoard() {
		return board;
	}

	public void run() {
		solve();
	}

	public synchronized void solve() {

		deadendCounter = 0;

		/*
		 * solver will give up iff all strategies fail to provide progress.
		 */
		while (!stuck) {
			stuck = true;

			progress = true;
			while (progress) {
				for (int strategyindex = 0; strategyindex < strategies.size(); strategyindex++) {
					SolverStrategy strategy = strategies.get(strategyindex);
					runWithStrategies(strategy, board, onestep);
					if (progress) {
						/*
						 * Current strategy gave progress thus we start again
						 * from the top.
						 */
						strategyindex = -1;
						
						if (onestep) {
							try {
								Scanner sc = new Scanner(System.in);
								while (!sc.nextLine().equals("")) {
									wait();
								}
							} catch (InterruptedException e) {

							}
						}
					}
				}
			}
		}

//		 board.printBoard();
//		 SolverStrategy strat = new JellyFish(); 
//		 board = strat.run(board,Board.Areas.ZERO, new Coord(0,0)); 
//		 System.out.println(strat.actionsMade());
//		 //runWithStrategies(strat, board, false);
//		 //System.out.println(strat.actionsMade());
		
	}
	
	public void printResult() {
		Tools.printBoard(board);
		System.out.println("deadendCounter: " + deadendCounter + "\n\n");
		System.out.println("strategies runcounts:");
		for (SolverStrategy strategy : strategies) {
			System.out
					.println(strategy.toString() + "  " + strategy.runCount());
		}
	}

	private Board runWithStrategies(SolverStrategy solver, Board board,
			boolean onestep) {
		progress = false;
		/*
		 * for all areas defined in Board.Areas
		 */
		for (Enum<Board.Areas> area : solver.areas()) {
			/*
			 * improves performance based on what area we are looking at.
			 */
			int vcondition = Board.DIMENSION;
			int hcondition = Board.DIMENSION;
			int inc = 1;
			if (area.equals(Board.Areas.ROW)) {
				vcondition = 1;
			}
			if (area.equals(Board.Areas.COL)) {
				hcondition = 1;
			}
			if (area.equals(Board.Areas.BOX)) {
				inc = Board.TILES_PER_BOX;
			}
			if (area.equals(Board.Areas.BOARD)) {
				vcondition = 1;
				hcondition = 1;
			}
			if (area.equals(Board.Areas.ZERO)) {
				// only coord at 0,0
				vcondition = 1;
				hcondition = 1;
			}
			/*
			 * run over all possible Coords
			 */
			for (int h = 0; h < hcondition; h += inc) {
				for (int v = 0; v < vcondition; v += inc) {
					Coord currentCoord = new Coord(v, h);
					board = solver.run(board, area, currentCoord);
					if (solver.optionRemoved() || solver.valueSet()) {
						progress = true;
						stuck = false;

						if (onestep) {
							System.out.println(solver);
							stepmsg = solver.actionsMade();
							Tools.printBoard(board);
							System.out.println(stepMsg());
							board.validMove();
						}
						return board;

					}
					deadendCounter++;
				}
			}
		}

		return board;
	}

	public String stepMsg() {
		return stepmsg;
	}

}
