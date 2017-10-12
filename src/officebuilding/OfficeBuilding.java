package officebuilding;

import collections.TwoSideLinkedCycleList;
import exceptions.FloorIndexOutOfBoundsException;
import exceptions.SpaceIndexOutOfBoundsException;
import interfaces.Building;
import interfaces.Floor;
import interfaces.Space;

import java.io.Serializable;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class OfficeBuilding implements Building, Serializable{

    private List<Floor> floors;

    public OfficeBuilding( int floors , int... spacesCountOnEachFloor ){
        if( floors != spacesCountOnEachFloor.length ){
            throw new IllegalArgumentException( "Floors count not equals to array of spaces count length." );
        }
        this.floors = Arrays.stream( spacesCountOnEachFloor ).mapToObj( OfficeFloor::new )
                            .collect( new Collector<OfficeFloor, TwoSideLinkedCycleList<Floor>, List<Floor>>(){
                                @Override
                                public Supplier<TwoSideLinkedCycleList<Floor>> supplier(){
                                    return () -> new TwoSideLinkedCycleList<>( Collections.emptyList() );
                                }

                                @Override
                                public BiConsumer<TwoSideLinkedCycleList<Floor>, OfficeFloor> accumulator(){
                                    return TwoSideLinkedCycleList::add;
                                }

                                @Override
                                public BinaryOperator<TwoSideLinkedCycleList<Floor>> combiner(){
                                    return ( floors1 , floors2 ) -> {
                                        for( Floor floor : floors2 ){ floors1.add( floor ); }
                                        return floors1;
                                    };
                                }

                                @Override
                                public Function<TwoSideLinkedCycleList<Floor>, List<Floor>> finisher(){
                                    return floors12 -> floors12;
                                }

                                @Override
                                public Set<Characteristics> characteristics(){
                                    return Collections.singleton( Characteristics.IDENTITY_FINISH );
                                }
                            } );
    }

    public OfficeBuilding( List<Floor> floors ){
        this.floors = new TwoSideLinkedCycleList<>( floors );
    }

    @Override
    public Integer getFloorsCount(){
        return floors.size();
    }

    @Override
    public Integer getSpacesCount(){
        return floors.stream().mapToInt( Floor::getSpacesCount ).sum();
    }

    @Override
    public Integer getSpacesArea(){
        return floors.stream().mapToInt( Floor::getSpacesArea ).sum();
    }

    @Override
    public Integer getSpacesRooms(){
        return floors.stream().mapToInt( Floor::getSpacesRooms ).sum();
    }

    @Override
    public Floor[] getFloors(){
        return floors.toArray( new Floor[ getFloorsCount() ] );
    }

    @Override
    public Floor getFloor( int index ){
        checkFloorsRange( index );
        return floors.get( index );
    }

    private void checkFloorsRange( int index ){
        if( !( 0 <= index && index < getFloorsCount() ) ){ throw new FloorIndexOutOfBoundsException(); }
    }

    @Override
    public void setFloor( int index , Floor floor ){
        checkFloorsRange( index );
        floors.set( index , floor );
    }

    @Override
    public Space getSpace( int index ){
        checkSpacesRange( index );
        int i = 0, downLimit = 0, upperLimit = floors.get( 0 ).getSpacesCount();
        for( ; upperLimit <= index ; downLimit = upperLimit , i++ , upperLimit += floors.get( i ).getSpacesCount() ){}
        return floors.get( i ).getSpace( index - downLimit );
    }

    private void checkSpacesRange( int index ){
        if( !( 0 <= index && index < getSpacesCount() ) ){ throw new SpaceIndexOutOfBoundsException(); }
    }

    @Override
    public void setSpace( int index , Space space ){
        checkSpacesRange( index );
        int i = 0, downLimit = 0, upperLimit = floors.get( 0 ).getSpacesCount();
        for( ; upperLimit <= index ; downLimit = upperLimit , i++ , upperLimit += floors.get( i ).getSpacesCount() ){}
        floors.get( i ).setSpace( index - downLimit , space );
    }

    @Override
    public void addSpace( int index , Space space ){
        if( !( 0 <= index && index <= getSpacesCount() ) ){ throw new SpaceIndexOutOfBoundsException(); }
        int i = 0, downLimit = 0, upperLimit = floors.get( 0 ).getSpacesCount();
        for( ; upperLimit <= index ; downLimit = upperLimit , i++ , upperLimit += floors.get( i ).getSpacesCount() ){}
        floors.get( i ).addSpace( index - downLimit , space );
    }

    @Override
    public void removeSpace( int index ){
        checkSpacesRange( index );
        int i = 0, downLimit = 0, upperLimit = floors.get( 0 ).getSpacesCount();
        for( ; upperLimit <= index ; downLimit = upperLimit , i++ , upperLimit += floors.get( i ).getSpacesCount() ){}
        floors.get( i ).removeSpace( index - downLimit );
    }

    @Override
    public Space getBestSpace(){
        return Arrays.stream( getSpaces() ).max( Comparator.comparingInt( Space::getArea ) )
                     .orElseThrow( () -> new IllegalStateException( "Dwelling is empty" ) );
    }

    @Override
    public Space[] getBestSpaces(){
        return Arrays.stream( getSpaces() ).sorted( Comparator.comparingInt( Space::getArea ) )
                     .toArray( value -> new Space[ getSpacesCount() ] );
    }

    private Space[] getSpaces(){
        List<Space> spaces = new ArrayList<>();
        for( Floor floor : floors ){ spaces.addAll( Arrays.asList( floor.getSpaces() ) ); }
        return spaces.toArray( new Space[ getSpacesCount() ] );
    }

    @Override
    public boolean equals( Object o ){
        if( this == o ){ return true; }
        if( !( o instanceof Building ) ){ return false; }

        Building that = ( Building ) o;

        return Arrays.equals( getFloors() , that.getFloors() );
    }

    @Override
    public int hashCode(){
        return Arrays.hashCode( getFloors() );
    }
}
