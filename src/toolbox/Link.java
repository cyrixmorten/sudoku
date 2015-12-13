package toolbox;

import framework.Board;

public class Link implements Comparable<Link> {
	
	private boolean strong;
	private Coord from;
	private Coord to;
	private int number;
	private Board.Areas area;

	public Link(Coord from, Coord to, Board.Areas area, boolean strong, int order) {
		this.strong = strong;
		this.area = area;
		this.from = from;
		this.to = to;
		this.number = order;
	}
	
	public Board.Areas getArea() {
		return area;
	}
	
	public int getNumber() {
		return number;
	}
	
	public Coord from() {
		return from;
	}
	
	public Coord to() {
		return to;
	}
	
	public boolean isStrong() {
		return strong;
	}
	
	public boolean isWeak() {
		return !strong;
	}
	
	@Override
	public String toString() {
		String strength = (strong) ? "S" : "W";
		return " {" + strength + " " + number + " " + from + to + " " +  area +"} ";
	};

	@Override
	public int compareTo(Link o) {
		if (number > o.getNumber()) {
			return 1;
		}
		else if (number < o.getNumber()) {
			return -1;
		}
		return 0;
	}
}
