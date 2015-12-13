package solver.solverFactories;

import java.util.ArrayList;
import java.util.List;

import solver.solverStrategies.*;

import framework.SolverFactory;
import framework.SolverStrategy;

public class QuickAllStrategiesFactory implements SolverFactory {

	@Override
	public List<SolverStrategy> getStrategies() {
		
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
		strategies.add(new SwordFish());
		strategies.add(new XYChain());
		strategies.add(new JellyFish());
		strategies.add(new XChain());
		
		return strategies;
	}

}
