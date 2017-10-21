package dwelling;

import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;
import exceptions.FloorIndexOutOfBoundsException;
import exceptions.SpaceIndexOutOfBoundsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


class DwellingTest{

    private Building    dwelling;
    private Integer     floorsCount;
    private Integer     spacesCount;
    private Double      spacesArea;
    private Integer     spacesRooms;
    private List<Floor> floors;
    private Random random = new Random( System.currentTimeMillis() );

    @BeforeEach
    void setUp(){
        floorsCount = random.nextInt( 10 ) + 5;
        floors = Stream.generate( () -> new DwellingFloor( getFlats() ) ).limit( floorsCount )
                       .collect( Collectors.toList() );
        spacesCount = floors.stream().mapToInt( Floor::getSpacesCount ).sum();
        spacesArea = floors.stream().mapToDouble( Floor::getSpacesArea ).sum();
        spacesRooms = floors.stream().mapToInt( Floor::getSpacesRooms ).sum();
        dwelling = new Dwelling( floors.toArray( new Floor[ floorsCount ] ) );
    }

    private Space[] getFlats(){
        int flatsCount = random.nextInt( 10 ) + 5;
        return Stream.generate( () -> new Flat( random.nextDouble() * 50 + 25 , random.nextInt( 5 ) + 3 ) )
                     .limit( flatsCount ).toArray( value -> new Space[ flatsCount ] );
    }

    @Test
    void getFloorsCount(){
        assertEquals( floorsCount , dwelling.getFloorsCount() );
    }

    @Test
    void getSpacesCount(){
        assertEquals( spacesCount , dwelling.getSpacesCount() );
    }

    @Test
    void getSpacesArea(){
        assertEquals( spacesArea , dwelling.getSpacesArea() );
    }

    @Test
    void getSpacesRooms(){
        assertEquals( spacesRooms , dwelling.getSpacesRooms() );
    }

    @Test
    void getFloors(){
        assertFloorsEquals();
    }

    @Test
    void getFloor(){
        for( int i = 0 ; i < floorsCount ; i++ ){ assertEquals( floors.get( i ) , dwelling.getFloor( i ) ); }
        assertThrows( FloorIndexOutOfBoundsException.class , () -> dwelling.getFloor( floorsCount ) );
    }

    @Test
    void setFloor(){
        Floor   dwellingFloor = new DwellingFloor( 5 );
        Integer index         = random.nextInt( floorsCount );
        dwelling.setFloor( index , dwellingFloor );
        floors.set( index , dwellingFloor );
        assertFloorsEquals();
        assertThrows( FloorIndexOutOfBoundsException.class , () -> dwelling.setFloor( floorsCount , dwellingFloor ) );
    }

    private void assertFloorsEquals(){
        assertArrayEquals( floors.toArray( new Floor[ floorsCount ] ) , dwelling.getFloors() );
    }

    @Test
    void getSpace(){
        int i = 0;
        for( Floor floor : floors ){
            for( Space space : floor.getSpaces() ){ assertEquals( space , dwelling.getSpace( i++ ) ); }
        }
        assertThrows( SpaceIndexOutOfBoundsException.class , () -> dwelling.getSpace( spacesCount ) );
    }

    private List<Space> getAllSpacesFromFloors(){
        List<Space> spaces = new ArrayList<>();
        floors.forEach( floor -> spaces.addAll( Arrays.asList( floor.getSpaces() ) ) );
        return spaces;
    }

    @Test
    void setSpace(){
        Space       flat      = new Flat();
        List<Space> allSpaces = getAllSpacesFromFloors();
        int         index     = random.nextInt( allSpaces.size() );
        dwelling.setSpace( index , flat );
        allSpaces.set( index , flat );
        assertTrue( allSpaces.get( index ) == dwelling.getSpace( index ) );
        assertThrows( SpaceIndexOutOfBoundsException.class , () -> dwelling.setSpace( spacesCount , flat ) );
    }

    @Test
    void addSpace(){
        Space       flat      = new Flat();
        List<Space> allSpaces = getAllSpacesFromFloors();
        int         index     = random.nextInt( allSpaces.size() + 1 );
        dwelling.addSpace( index , flat );
        allSpaces.add( index , flat );
        assertTrue( allSpaces.get( index ) == dwelling.getSpace( index ) );
        assertThrows( SpaceIndexOutOfBoundsException.class , () -> dwelling.setSpace( spacesCount + 1 , flat ) );
    }

    @Test
    void removeSpace(){
        List<Space> allSpaces = getAllSpacesFromFloors();
        int         index     = random.nextInt( allSpaces.size() + 1 );
        dwelling.removeSpace( index );
        List<Space> dwellingSpaces = new ArrayList<>();
        for( int i = 0 ; i < dwelling.getFloorsCount() ; i++ ){
            dwellingSpaces.addAll( Arrays.asList( dwelling.getFloor( i ).getSpaces() ) );
        }
        allSpaces.remove( index );
        assertArrayEquals( allSpaces.toArray() , dwellingSpaces.toArray() );
        assertThrows( SpaceIndexOutOfBoundsException.class , () -> dwelling.removeSpace( spacesCount ) );
    }

    @Test
    void getBestSpace(){
        assertEquals( getAllSpacesFromFloors().stream().max( Comparator.comparingDouble( Space::getArea ) )
                                              .orElseThrow( IllegalStateException::new ) , dwelling.getBestSpace() );
    }

    @Test
    void getBestSpaces(){
        assertArrayEquals(
                getAllSpacesFromFloors().stream().sorted( Comparator.comparingDouble( Space::getArea ) ).toArray() ,
                dwelling.getBestSpaces() );
    }

}