package util;

import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;

import java.util.List;

public interface BuildingFactory{

    Space createSpace( Double area );

    Space createSpace( Double area , Integer roomsCount );

    Floor createFloor( Integer spacesCount );

    Floor createFloor( List<Space> spaces );

    Building createBuilding( Integer floorsCount , Integer[] spacesCounts );

    Building createBuilding( List<Floor> floors );
}
