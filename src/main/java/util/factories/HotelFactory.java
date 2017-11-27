package util.factories;

import buildings.dwelling.Flat;
import buildings.dwelling.hotel.Hotel;
import buildings.dwelling.hotel.HotelFloor;
import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;
import util.BuildingFactory;

import java.util.List;

public class HotelFactory implements BuildingFactory{

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
        return new HotelFloor( spacesCount );
    }

    @Override
    public Floor createFloor( List<Space> spaces ){
        return new HotelFloor( spaces );
    }

    @Override
    public Building createBuilding( Integer floorsCount , Integer[] spacesCounts ){
        return new Hotel( floorsCount , spacesCounts );
    }

    @Override
    public Building createBuilding( List<Floor> floors ){
        return new Hotel( floors );
    }
}
