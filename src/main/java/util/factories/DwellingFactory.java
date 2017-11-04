package util.factories;

import buildings.dwelling.Dwelling;
import buildings.dwelling.DwellingFloor;
import buildings.dwelling.Flat;
import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;
import util.BuildingFactory;

public class DwellingFactory implements BuildingFactory{
    @Override
    public Space createSpace( Double area ){
        return new Flat( area );
    }

    @Override
    public Space createSpace( Integer roomsCount , Double area ){
        return new Flat( area , roomsCount );
    }

    @Override
    public Floor createFloor( Integer spacesCount ){
        return new DwellingFloor( spacesCount );
    }

    @Override
    public Floor createFloor( Space[] spaces ){
        return new DwellingFloor( spaces );
    }

    @Override
    public Building createBuilding( int floorsCount , int[] spacesCounts ){
        return new Dwelling( floorsCount , spacesCounts );
    }

    @Override
    public Building createBuilding( Floor[] floors ){
        return new Dwelling( floors );
    }
}
