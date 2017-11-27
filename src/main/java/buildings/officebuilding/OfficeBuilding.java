package buildings.officebuilding;

import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;
import collections.TwoSideLinkedCycleList;
import exceptions.FloorIndexOutOfBoundsException;
import exceptions.SpaceIndexOutOfBoundsException;

import java.util.*;

public class OfficeBuilding implements Building{

    private TwoSideLinkedCycleList<Floor> floors;

    public OfficeBuilding( Integer floors , Integer... spacesCountOnEachFloor ){
        if( floors != spacesCountOnEachFloor.length ){
            throw new IllegalArgumentException( "Floors count not equals to array of spaces count length." );
        }
        this.floors = Arrays.stream( spacesCountOnEachFloor ).map( OfficeFloor::new )
                            .collect( () -> new TwoSideLinkedCycleList<>( Collections.emptyList() ) ,
                                      TwoSideLinkedCycleList::add ,
                                      ( floors1 , floors2 ) -> floors2.forEach( floors1::add ) );
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
    public Double getSpacesArea(){
        return floors.stream().mapToDouble( Floor::getSpacesArea ).sum();
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
        if( !( 0 <= index && index < getFloorsCount() ) ){
            throw new FloorIndexOutOfBoundsException();
        }
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
        for( ; upperLimit <= index ; downLimit = upperLimit , i++ , upperLimit += floors.get( i ).getSpacesCount() ){
        }
        return floors.get( i ).getSpace( index - downLimit );
    }

    private void checkSpacesRange( int index ){
        if( !( 0 <= index && index < getSpacesCount() ) ){
            throw new SpaceIndexOutOfBoundsException();
        }
    }

    @Override
    public void setSpace( int index , Space space ){
        checkSpacesRange( index );
        int i = 0, downLimit = 0, upperLimit = floors.get( 0 ).getSpacesCount();
        for( ; upperLimit <= index ; downLimit = upperLimit , i++ , upperLimit += floors.get( i ).getSpacesCount() ){
        }
        floors.get( i ).setSpace( index - downLimit , space );
    }

    @Override
    public void addSpace( int index , Space space ){
        if( !( 0 <= index && index <= getSpacesCount() ) ){
            throw new SpaceIndexOutOfBoundsException();
        }
        int i = 0, downLimit = 0, upperLimit = floors.get( 0 ).getSpacesCount();
        for( ; upperLimit <= index ; downLimit = upperLimit , i++ , upperLimit += floors.get( i ).getSpacesCount() ){
        }
        floors.get( i ).addSpace( index - downLimit , space );
    }

    @Override
    public void removeSpace( int index ){
        checkSpacesRange( index );
        int i = 0, downLimit = 0, upperLimit = floors.get( 0 ).getSpacesCount();
        for( ; upperLimit <= index ; downLimit = upperLimit , i++ , upperLimit += floors.get( i ).getSpacesCount() ){
        }
        floors.get( i ).removeSpace( index - downLimit );
    }

    @Override
    public Space getBestSpace(){
        return Arrays.stream( getSpaces() ).max( Space::compareTo )
                     .orElseThrow( () -> new IllegalStateException( "Dwelling is empty" ) );
    }

    @Override
    public Space[] getBestSpaces(){
        return Arrays.stream( getSpaces() ).sorted( Space::compareTo )
                     .toArray( value -> new Space[ getSpacesCount() ] );
    }

    private Space[] getSpaces(){
        List<Space> spaces = new ArrayList<>();
        for( Floor floor : floors ){
            spaces.addAll( Arrays.asList( floor.getSpaces() ) );
        }
        return spaces.toArray( new Space[ getSpacesCount() ] );
    }

    @Override
    public boolean equals( Object o ){
        if( this == o ){
            return true;
        }
        if( !( o instanceof Building ) ){
            return false;
        }
        Building that = ( Building ) o;
        return Arrays.equals( getFloors() , that.getFloors() );
    }

    @Override
    public int hashCode(){
        return Arrays.hashCode( getFloors() );
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        OfficeBuilding clone = ( OfficeBuilding ) super.clone();
        clone.floors = new TwoSideLinkedCycleList<>();
        for( Floor floor : this ){
            clone.floors.add( ( Floor ) floor.clone() );
        }
        return clone;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append( "OfficeBuilding (" ).append( getFloorsCount() ).append( ", " );
        for( Floor floor : getFloors() ){
            stringBuilder.append( floor ).append( ", " );
        }
        stringBuilder.append( ")" );
        return stringBuilder.toString();
    }

    @Override
    public Iterator<Floor> iterator(){
        return floors.iterator();
    }
}
