package standard;

import java.util.List;

import toolbox.Coord;
import toolbox.Options;

import framework.Tile;

public class StandardTile implements Tile {

	private int value;
	private Coord coord;
	private Options options;

	public StandardTile(Coord coord, int value) {
		options = new Options(value);
		this.coord = coord;
		this.value = value;
	}

	@Override
	public String toString() {
		return "{Tile value[" + value + "]" + " chords[" + coord + "]}";
	}

	@Override
	public Coord getCoordinate() {
		return coord;
	}

	@Override
	public Options getOptions() {
		return options;
	}

	@Override
	public int getValue() {
		return value;
	}

	@Override
	public void setOptions(List<Integer> options) {
		this.options.setOptions(options);
	}

	@Override
	public boolean removeOption(int option) {
		boolean removed = options.removeOption(option);
		/*
		if (options.size() == 1) {
			value = options.getList().get(0);
			options.getList().clear();
		}
		*/
		return removed;
	}
	
	@Override
	public boolean removeOptions(List<Integer> optionlist) {
		boolean removed = options.removeOptions(optionlist);
		/*
		if (options.size() == 1) {
			value = options.getList().get(0);
			options.getList().clear();
		}
		*/
		return removed;
	}

	@Override
	public boolean setValue(int value) {
		if (options.size() > 0) {
			this.value = value;
			options.removeOptions(options.copyList());
			return true;
		}
		return false;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof StandardTile
				&& ((StandardTile)o).getValue() == this.value
				&& ((StandardTile)o).getCoordinate().equals(this.coord)
				&& ((StandardTile)o).getCoordinate().hashCode() == this.coord.hashCode()) {
			return true;
		}
		return false;
	}

}
