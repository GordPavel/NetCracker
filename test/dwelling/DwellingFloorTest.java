package dwelling;

import exceptions.SpaceIndexOutOfBoundsException;
import interfaces.Floor;
import interfaces.Space;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DwellingFloorTest{

    private Floor       dwellingFloor;
    private Integer     allRooms;
    private Integer     allArea;
    private Integer     spacesCount;
    private List<Space> spaces;
    private Random random = new Random( System.currentTimeMillis() );

    @BeforeEach
    void setUp(){
        spacesCount = random.nextInt( 15 ) + 5;
        spaces = Stream.generate( this::getNewSpace ).limit( spacesCount ).collect( Collectors.toList() );
        allArea = spaces.stream().mapToInt( Space::getArea ).sum();
        allRooms = spaces.stream().mapToInt( Space::getRoomsCount ).sum();
        dwellingFloor = new DwellingFloor( spaces.toArray( new Space[ 0 ] ) );
    }

    private Flat getNewSpace(){
        return new Flat( random.nextInt( 50 ) + 25 , random.nextInt( 5 ) + 3 );
    }

    @Test
    void getSpacesCount(){
        assertEquals( spacesCount , dwellingFloor.getSpacesCount() );
    }

    @Test
    void getSpacesArea(){
        assertEquals( allArea , dwellingFloor.getSpacesArea() );
    }

    @Test
    void getSpacesRooms(){
        assertEquals( allRooms , dwellingFloor.getSpacesRooms() );
    }

    @Test
    void getSpaces(){
        assertArrayEq();
    }

    @Test
    void getSpace(){
        for( int i = 0 ; i < spaces.size() ; i++ ){ assertEquals( spaces.get( i ) , dwellingFloor.getSpace( i ) ); }
        assertThrows( SpaceIndexOutOfBoundsException.class ,
                      () -> dwellingFloor.getSpace( dwellingFloor.getSpacesCount() ) );
    }

    @Test
    void setSpace(){
        Space newSpace = getNewSpace();
        int   index    = random.nextInt( dwellingFloor.getSpacesCount() );

        dwellingFloor.setSpace( index , newSpace );
        spaces.set( index , newSpace );

        assertArrayEq();
    }

    @Test
    void addSpace(){
        Space newSpace = getNewSpace();
        int   index    = random.nextInt( dwellingFloor.getSpacesCount() + 1 );

        dwellingFloor.addSpace( index , newSpace );
        spaces.add( index , newSpace );

        assertArrayEq();

        dwellingFloor.addSpace( dwellingFloor.getSpacesCount() , newSpace );
        spaces.add( newSpace );

        assertArrayEq();
    }

    @Test
    void removeSpace(){
        int index = random.nextInt( dwellingFloor.getSpacesCount() );

        dwellingFloor.removeSpace( index );
        spaces.remove( index );

        assertArrayEq();
    }

    private void assertArrayEq(){
        assertArrayEquals( spaces.toArray( new Space[ 0 ] ) , dwellingFloor.getSpaces() );
    }

    @Test
    void getBestSpace(){
        assertEquals( spaces.stream().max( Comparator.comparingInt( Space::getArea ) )
                            .orElseThrow( IllegalStateException::new ) , dwellingFloor.getBestSpace() );
    }
}