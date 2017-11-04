package util.factories;

import buildings.dwelling.Flat;
import buildings.dwelling.hotel.Hotel;
import buildings.dwelling.hotel.HotelFloor;
import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;
import util.BuildingFactory;

public class HotelFactory implements BuildingFactory{

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
        return new HotelFloor( spacesCount );
    }

    @Override
    public Floor createFloor( Space[] spaces ){
        return new HotelFloor( spaces );
    }

    @Override
    public Building createBuilding( int floorsCount , int[] spacesCounts ){
        return new Hotel( floorsCount , spacesCounts );
    }

    @Override
    public Building createBuilding( Floor[] floors ){
        return new Hotel( floors );
    }
}
