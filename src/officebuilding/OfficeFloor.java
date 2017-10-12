package officebuilding;

import collections.OneSideLinkedCycleList;
import interfaces.Floor;
import interfaces.Space;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OfficeFloor implements Floor, Serializable{
    private List<Space> offices;

    public OfficeFloor( int officesCount ){
        this.offices = new OneSideLinkedCycleList<>( Collections.nCopies( officesCount , new Office() ) );
    }

    public OfficeFloor( List<Space> offices ){
        this.offices = new OneSideLinkedCycleList<>( offices );
    }

    @Override
    public Integer getSpacesCount(){
        return offices.size();
    }

    @Override
    public Integer getSpacesArea(){
        return offices.stream().mapToInt( Space::getArea ).sum();
    }

    @Override
    public Integer getSpacesRooms(){
        return offices.stream().mapToInt( Space::getRoomsCount ).sum();
    }

    @Override
    public Space[] getSpaces(){
        return offices.toArray( new Space[ getSpacesCount() ] );
    }

    @Override
    public Space getSpace( int index ){
        checkSpacesRange( index );
        return offices.get( index );
    }

    private void checkSpacesRange( int index ){
        if( !( 0 <= index && index < getSpacesCount() ) ){ throw new StringIndexOutOfBoundsException(); }
    }

    @Override
    public void setSpace( int index , Space space ){
        checkSpacesRange( index );
        offices.set( index , space );
    }

    @Override
    public void addSpace( int index , Space space ){
        if( !( 0 <= index && index <= getSpacesCount() ) ){ throw new StringIndexOutOfBoundsException(); }
        offices.add( index , space );
    }

    @Override
    public void removeSpace( int index ){
        checkSpacesRange( index );
        offices.remove( index );
    }

    @Override
    public Space getBestSpace(){
        return offices.stream().max( Comparator.comparingInt( Space::getArea ) )
                      .orElseThrow( IllegalStateException::new );
    }

    @Override
    public boolean equals( Object o ){
        if( this == o ){ return true; }
        if( !( o instanceof Floor ) ){ return false; }
        Floor that = ( Floor ) o;
        return Arrays.equals( this.offices.toArray() , that.getSpaces() );
    }

    @Override
    public int hashCode(){
        return offices.hashCode();
    }
}
