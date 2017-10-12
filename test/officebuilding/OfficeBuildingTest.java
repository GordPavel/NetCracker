package officebuilding;

import exceptions.FloorIndexOutOfBoundsException;
import exceptions.SpaceIndexOutOfBoundsException;
import interfaces.Building;
import interfaces.Floor;
import interfaces.Space;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class OfficeBuildingTest{
    private Building officeBuilding;

    private List<Floor> floors;
    private Integer     floorsCount;
    private Integer     spacesCount;
    private Integer     spacesArea;
    private Integer     spacesRooms;
    private Random random = new Random( System.currentTimeMillis() );

    @BeforeEach
    void setUp(){
        floorsCount = random.nextInt( 10 ) + 5;
        floors = Stream.generate( () -> new OfficeFloor( getOffices() ) ).limit( floorsCount )
                       .collect( Collectors.toList() );
        spacesCount = floors.stream().mapToInt( Floor::getSpacesCount ).sum();
        spacesArea = floors.stream().mapToInt( Floor::getSpacesArea ).sum();
        spacesRooms = floors.stream().mapToInt( Floor::getSpacesRooms ).sum();
        officeBuilding = new OfficeBuilding( floors );
    }

    private List<Space> getOffices(){
        int flatsCount = random.nextInt( 10 ) + 5;
        return Stream.generate( () -> new Office( random.nextInt( 50 ) + 25 , random.nextInt( 5 ) + 3 ) )
                     .limit( flatsCount ).collect( Collectors.toList() );
    }


    @Test
    void getFloorsCount(){
        assertEquals( floorsCount , officeBuilding.getFloorsCount() );
    }

    @Test
    void getSpacesCount(){
        assertEquals( spacesCount , officeBuilding.getSpacesCount() );
    }

    @Test
    void getSpacesArea(){
        assertEquals( spacesArea , officeBuilding.getSpacesArea() );
    }

    @Test
    void getSpacesRooms(){
        assertEquals( spacesRooms , officeBuilding.getSpacesRooms() );
    }

    @Test
    void getFloors(){
        assertFloorsEquals();
    }

    @Test
    void getFloor(){
        for( int i = 0 ; i < floorsCount ; i++ ){ assertEquals( floors.get( i ) , officeBuilding.getFloor( i ) ); }
        assertThrows( FloorIndexOutOfBoundsException.class , () -> officeBuilding.getFloor( floorsCount ) );
    }

    @Test
    void setFloor(){
        Floor   officeFloor = new OfficeFloor( 5 );
        Integer index       = random.nextInt( floorsCount );
        officeBuilding.setFloor( index , officeFloor );
        floors.set( index , officeFloor );
        assertFloorsEquals();
        assertThrows( FloorIndexOutOfBoundsException.class ,
                      () -> officeBuilding.setFloor( floorsCount , officeFloor ) );
    }

    private void assertFloorsEquals(){
        assertArrayEquals( floors.toArray( new Floor[ floorsCount ] ) , officeBuilding.getFloors() );
    }

    @Test
    void getSpace(){
        int i = 0;
        for( Floor floor : floors ){
            for( Space space : floor.getSpaces() ){ assertEquals( space , officeBuilding.getSpace( i++ ) ); }
        }
        assertThrows( SpaceIndexOutOfBoundsException.class , () -> officeBuilding.getSpace( spacesCount ) );
    }

    private List<Space> getAllSpacesFromFloors(){
        List<Space> spaces = new ArrayList<>();
        floors.forEach( floor -> spaces.addAll( Arrays.asList( floor.getSpaces() ) ) );
        return spaces;
    }

    @Test
    void setSpace(){
        Space       office    = new Office();
        List<Space> allSpaces = getAllSpacesFromFloors();
        int         index     = random.nextInt( allSpaces.size() );
        officeBuilding.setSpace( index , office );
        allSpaces.set( index , office );
        assertTrue( allSpaces.get( index ) == officeBuilding.getSpace( index ) );
        assertThrows( SpaceIndexOutOfBoundsException.class , () -> officeBuilding.setSpace( spacesCount , office ) );
    }

    @Test
    void addSpace(){
        Space       office    = new Office();
        List<Space> allSpaces = getAllSpacesFromFloors();
        int         index     = random.nextInt( allSpaces.size() + 1 );
        officeBuilding.addSpace( index , office );
        allSpaces.add( index , office );
        assertTrue( allSpaces.get( index ) == officeBuilding.getSpace( index ) );
        assertThrows( SpaceIndexOutOfBoundsException.class ,
                      () -> officeBuilding.setSpace( spacesCount + 1 , office ) );
    }

    @Test
    void removeSpace(){
        List<Space> allSpaces = getAllSpacesFromFloors();
        int         index     = random.nextInt( allSpaces.size() + 1 );
        officeBuilding.removeSpace( index );
        List<Space> officeSpaces = new ArrayList<>();
        for( int i = 0 ; i < officeBuilding.getFloorsCount() ; i++ ){
            officeSpaces.addAll( Arrays.asList( officeBuilding.getFloor( i ).getSpaces() ) );
        }
        allSpaces.remove( index );
        assertArrayEquals( allSpaces.toArray() , officeSpaces.toArray() );
        assertThrows( SpaceIndexOutOfBoundsException.class , () -> officeBuilding.removeSpace( spacesCount ) );
    }

    @Test
    void getBestSpace(){
        assertEquals( getAllSpacesFromFloors().stream().max( Comparator.comparingInt( Space::getArea ) )
                                              .orElseThrow( IllegalStateException::new ) ,
                      officeBuilding.getBestSpace() );
    }

    @Test
    void getBestSpaces(){
        assertArrayEquals(
                getAllSpacesFromFloors().stream().sorted( Comparator.comparingInt( Space::getArea ) ).toArray() ,
                officeBuilding.getBestSpaces() );
    }
}