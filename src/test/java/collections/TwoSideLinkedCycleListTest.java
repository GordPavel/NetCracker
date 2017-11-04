package collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TwoSideLinkedCycleListTest{
    private List<Integer> cycleList;
    private List<Integer> integers;
    private Random random = new Random( System.currentTimeMillis() );

    @BeforeEach
    void setUp(){
        integers = Stream.generate( () -> random.nextInt( 20 ) ).limit( random.nextInt( 50 ) + 25 )
                         .collect( Collectors.toList() );
        cycleList = new TwoSideLinkedCycleList<>( integers );
    }

    @Test
    void get(){
        int i = -3 * integers.size();
        for( int j = 0 ; j < integers.size() ; j++ , i++ ){
            assertEquals( integers.get( j ) , cycleList.get( i ) );
        }
        for( int j = 0 ; j < integers.size() ; j++ , i++ ){
            assertEquals( integers.get( j ) , cycleList.get( i ) );
        }
        for( int j = 0 ; j < integers.size() ; j++ , i++ ){
            assertEquals( integers.get( j ) , cycleList.get( i ) );
        }
        for( int j = 0 ; j < integers.size() ; j++ , i++ ){
            assertEquals( integers.get( j ) , cycleList.get( i ) );
        }
        for( int j = 0 ; j < integers.size() ; j++ , i++ ){
            assertEquals( integers.get( j ) , cycleList.get( i ) );
        }
        for( int j = 0 ; j < integers.size() ; j++ , i++ ){
            assertEquals( integers.get( j ) , cycleList.get( i ) );
        }
        for( int j = 0 ; j < integers.size() ; j++ , i++ ){
            assertEquals( integers.get( j ) , cycleList.get( i ) );
        }
    }

    @Test
    void size(){
        assertEquals( integers.size() , cycleList.size() );
    }

    @Test
    void contains(){
        assertTrue( cycleList.contains( integers.get( random.nextInt( integers.size() ) ) ) );
    }

    @Test
    void toArray(){
        integersEquals();
    }

    private void integersEquals(){
        assertIntegersEquals();
    }

    private void assertIntegersEquals(){
        assertArrayEquals( integers.toArray() , cycleList.toArray() );
    }

    @Test
    void toArray1(){
        assertArrayEquals( integers.toArray( new Integer[ 0 ] ) , cycleList.toArray( new Integer[ 0 ] ) );
    }

    @Test
    void set(){
        int index = random.nextInt( integers.size() );
        integers.set( index , 5 );
        cycleList.set( index , 5 );
        integersEquals();
    }

    @Test
    void add(){
        int index = random.nextInt( integers.size() );
        integers.add( index , 5 );
        cycleList.add( index , 5 );
        integersEquals();
    }

    @Test
    void remove(){
        int index = random.nextInt( integers.size() );
        integers.remove( index );
        cycleList.remove( index );
        integersEquals();
    }

    @Test
    void indexOf(){
        Integer randomElement = integers.get( random.nextInt( integers.size() ) );
        assertEquals( integers.indexOf( randomElement ) , cycleList.indexOf( randomElement ) );
    }

    @Test
    void lastIndexOf(){
        Integer randomElement = integers.get( random.nextInt( integers.size() ) );
        assertEquals( integers.lastIndexOf( randomElement ) , cycleList.lastIndexOf( randomElement ) );
    }

    @Test
    void stream(){
        assertEquals( integers.stream().mapToInt( o -> o ).sum() , cycleList.stream().mapToInt( o -> o ).sum() );
    }
}