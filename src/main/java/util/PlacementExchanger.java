package util;

import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;
import exceptions.InexchangeableFloorsException;
import exceptions.InexchangeableSpacesException;

import java.util.Objects;

public final class PlacementExchanger{
    public static Boolean checkSpacesExchange( Space first , Space second ){
        return Objects.equals( first.getArea() , second.getArea() ) &&
               Objects.equals( first.getRoomsCount() , second.getRoomsCount() );
    }

    public static Boolean checkFloorsExchange( Floor first , Floor second ){
        return Objects.equals( first.getSpacesArea() , second.getSpacesArea() ) &&
               Objects.equals( first.getSpacesCount() , second.getSpacesCount() );
    }

    public static void exchangeFloorRooms( Floor floor1 , int index1 , Floor floor2 , int index2 ) throws
                                                                                                   InexchangeableSpacesException{
        Space space1 = floor1.getSpace( index1 ), space2 = floor2.getSpace( index2 );
        if( !checkSpacesExchange( space1 , space2 ) ){
            throw new InexchangeableSpacesException();
        }
        floor1.setSpace( index1 , space2 );
        floor2.setSpace( index2 , space1 );
    }

    public static void exchangeBuildingFloors( Building building1 , int index1 , Building building2 ,
                                               int index2 ) throws InexchangeableFloorsException{
        Floor floor1 = building1.getFloor( index1 ), floor2 = building2.getFloor( index2 );
        if( !checkFloorsExchange( floor1 , floor2 ) ){
            throw new InexchangeableFloorsException();
        }
        building1.setFloor( index1 , floor2 );
        building2.setFloor( index2 , floor1 );
    }
}
