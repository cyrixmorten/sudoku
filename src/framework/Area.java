package framework;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import toolbox.Coord;
import toolbox.Options;

public interface Area {

	void addCoord(Coord c);
	List<Coord> getCoords();
}
