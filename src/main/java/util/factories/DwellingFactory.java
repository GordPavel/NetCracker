package util.factories;

import buildings.dwelling.Dwelling;
import buildings.dwelling.DwellingFloor;
import buildings.dwelling.Flat;
import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;
import util.BuildingFactory;

import java.util.List;

public class DwellingFactory implements BuildingFactory{
    @Override
    public Space createSpace( Double area ){
        return new Flat( area );
    }

    @Override
    public Space createSpace( Double area , Integer roomsCount ){
        return new Flat( area , roomsCount );
    }

    @Override
    public Floor createFloor( Integer spacesCount ){
        return new DwellingFloor( spacesCount );
    }

    @Override
    public Floor createFloor( List<Space> spaces ){
        return new DwellingFloor( spaces );
    }

    @Override
    public Building createBuilding( Integer floorsCount , Integer[] spacesCounts ){
        return new Dwelling( floorsCount , spacesCounts );
    }

    @Override
    public Building createBuilding( List<Floor> floors ){
        return new Dwelling( floors );
    }
}
