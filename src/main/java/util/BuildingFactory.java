package util;

import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;

public interface BuildingFactory{

    Space createSpace( Double area );

    Space createSpace( Integer roomsCount , Double area );

    Floor createFloor( Integer spacesCount );

    Floor createFloor( Space[] spaces );

    Building createBuilding( int floorsCount , int[] spacesCounts );

    Building createBuilding( Floor[] floors );
}
