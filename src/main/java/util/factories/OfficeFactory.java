package util.factories;

import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;
import buildings.officebuilding.Office;
import buildings.officebuilding.OfficeBuilding;
import buildings.officebuilding.OfficeFloor;
import util.BuildingFactory;

import java.util.List;

public class OfficeFactory implements BuildingFactory{
    @Override
    public Space createSpace( Double area ){
        return new Office( area );
    }

    @Override
    public Space createSpace( Double area , Integer roomsCount ){
        return new Office( area , roomsCount );
    }

    @Override
    public Floor createFloor( Integer spacesCount ){
        return new OfficeFloor( spacesCount );
    }

    @Override
    public Floor createFloor( List<Space> spaces ){
        return new OfficeFloor( spaces );
    }

    @Override
    public Building createBuilding( Integer floorsCount , Integer[] spacesCounts ){
        return new OfficeBuilding( floorsCount , spacesCounts );
    }

    @Override
    public Building createBuilding( List<Floor> floors ){
        return new OfficeBuilding( floors );
    }
}
