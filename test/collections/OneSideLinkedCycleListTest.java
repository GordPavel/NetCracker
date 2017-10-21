package collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class OneSideLinkedCycleListTest{
    private OneSideLinkedCycleList<Integer> cycleList;
    private List<Integer>                   integers;
    private Random random = new Random( System.currentTimeMillis() );

    @BeforeEach
    void setUp(){
        int limit = 10;
        integers =
                IntStream.generate( () -> random.nextInt( 50 ) ).limit( limit ).boxed().collect( Collectors.toList() );
        cycleList = new OneSideLinkedCycleList<>( integers );
    }

    @Test
    void size(){
        assertEquals( integers.size() , cycleList.size() );
    }

    @Test
    void isEmpty(){
        assertEquals( integers.isEmpty() , cycleList.isEmpty() );
    }

    @Test
    void contains(){
        assertTrue( cycleList.contains( integers.get( random.nextInt( integers.size() ) ) ) );
    }

    @Test
    void toArray(){
        assertArrayEquals( integers.toArray() , cycleList.toArray() );
    }

    @Test
    void toTypeArray(){
        assertArrayEquals( integers.toArray( new Integer[ 0 ] ) , cycleList.toArray( new Integer[ 0 ] ) );
    }

    @Test
    void clear(){
        cycleList.clear();
        assertTrue( cycleList.isEmpty() );
    }

    @Test
    void get(){
        for( int i = 0 ; i < integers.size() ; i++ ){ assertEquals( integers.get( i ) , cycleList.get( i ) ); }
    }

    @Test
    void set(){
        int index = random.nextInt( integers.size() );
        integers.set( index , 5 );
        cycleList.set( index , 5 );
        assertArrayEquals( integers.toArray() , cycleList.toArray() );
    }

    @Test
    void addWithIndex(){
        int index = random.nextInt( integers.size() + 1 );
        integers.add( index , 5 );
        cycleList.add( index , 5 );
        assertArrayEquals( integers.toArray() , cycleList.toArray() );
        cycleList.clear();
        cycleList.add( 0 , 5 );
        assertArrayEquals( new Integer[]{ 5 } , cycleList.toArray() );
    }

    @Test
    void remove(){
        int index = random.nextInt( integers.size() );
        integers.remove( index );
        cycleList.remove( index );
        assertArrayEquals( integers.toArray() , cycleList.toArray() );
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

    @Test
    void cloneTest() throws CloneNotSupportedException{
        List<Integer> clone = ( List<Integer> ) cycleList.clone();
        clone.set( 0 , 5 );
        assertFalse( cycleList.equals( clone ) );
    }
}