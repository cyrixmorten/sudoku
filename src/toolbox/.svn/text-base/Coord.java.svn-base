package toolbox;

public class Coord {
	
	private int h;
	private int v;
	
	public Coord(int v, int h) {
		this.h = h;
		this.v = v;
	}


	public int getH() {
		return h;
	}


	public int getV() {
		return v;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Coord
				&& ((Coord)o).getH() == this.h
				&& ((Coord)o).getV() == this.v) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		if (h == 0) {
			return v*33;
		}
		if (v == 0) {
			return h*6;
		}
		return h*6+v*33;
	}
	
	@Override
	public String toString() {
		return "{[v:"+v+"][h:"+h+"]}";
	}

}
