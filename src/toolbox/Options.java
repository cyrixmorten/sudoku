package toolbox;

import java.util.ArrayList;
import java.util.List;


import framework.Board;

public class Options implements Cloneable {

	private List<Integer> options;

	public Options() {
		options = new ArrayList<Integer>();
		for (int i = 1; i <= Board.DIMENSION; i++) {
			options.add(i);
		}
	}

	// constructor suitable when creating Tiles
	public Options(int value) {
		options = new ArrayList<Integer>();
		if (value == 0) {
			for (int i = 1; i <= Board.DIMENSION; i++) {
				options.add(i);
			}
		}
	}

	public void setOptions(List<Integer> options) {
		this.options.clear();
		this.options.addAll(options);
	}

	public List<Integer> copyList() {
		List<Integer> list = new ArrayList<Integer>();
		list.addAll(options);
		return list;
	}

	public int size() {
		return options.size();
	}

	public boolean removeOption(int option) {

		for (int i = 0; i < options.size(); i++) {
			if (options.get(i) == option) {
				options.remove(i);
				return true;
			}
		}

		return false;
	}
	
	public boolean removeOptions(List<Integer> optionlist) {

		int before = options.size();
		options.removeAll(optionlist);
		int after = options.size();
		
		if (before != after) {
			return true;
		}

		return false;
	}

	public boolean contains(int value) {
		if (options.contains(value)) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuffer b = new StringBuffer();
		b.append("[");
		for (Integer o: options) {
			b.append(o + ",");
		}
		for (int i = Board.DIMENSION - options.size(); i > 0; i--) {
			b.append(" ,");
		}
		b.delete(b.length()-1, b.length());
		b.append("]");
		return b.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Options) {
			return options.equals(this.options);
		}
		return false;
	}
	
    public Options clone() {
        try {
            Options copy = (Options)super.clone();
            copy.options = (List<Integer>) ((ArrayList<Integer>) options).clone();
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new Error("This should not occur since we implement Cloneable");
        }
    }

}
