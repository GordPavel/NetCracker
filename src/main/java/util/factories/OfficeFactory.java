package util.factories;

import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;
import buildings.officebuilding.Office;
import buildings.officebuilding.OfficeBuilding;
import buildings.officebuilding.OfficeFloor;
import util.BuildingFactory;

import java.util.Arrays;

public class OfficeFactory implements BuildingFactory{
    @Override
    public Space createSpace( Double area ){
        return new Office( area );
    }

    @Override
    public Space createSpace( Integer roomsCount , Double area ){
        return new Office( area , roomsCount );
    }

    @Override
    public Floor createFloor( Integer spacesCount ){
        return new OfficeFloor( spacesCount );
    }

    @Override
    public Floor createFloor( Space[] spaces ){
        return new OfficeFloor( Arrays.asList( spaces ) );
    }

    @Override
    public Building createBuilding( int floorsCount , int[] spacesCounts ){
        return new OfficeBuilding( floorsCount , spacesCounts );
    }

    @Override
    public Building createBuilding( Floor[] floors ){
        return new OfficeBuilding( Arrays.asList( floors ) );
    }
}
