package buildings.officebuilding;

import buildings.interfaces.Floor;
import buildings.interfaces.Space;
import collections.OneSideLinkedCycleList;

import java.util.*;

public class OfficeFloor implements Floor{
    private OneSideLinkedCycleList<Space> offices;

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
    public Double getSpacesArea(){
        return offices.stream().mapToDouble( Space::getArea ).sum();
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
        if( !( 0 <= index && index < getSpacesCount() ) ){
            throw new StringIndexOutOfBoundsException();
        }
    }

    @Override
    public void setSpace( int index , Space space ){
        checkSpacesRange( index );
        offices.set( index , space );
    }

    @Override
    public void addSpace( int index , Space space ){
        if( !( 0 <= index && index <= getSpacesCount() ) ){
            throw new StringIndexOutOfBoundsException();
        }
        offices.add( index , space );
    }

    @Override
    public void removeSpace( int index ){
        checkSpacesRange( index );
        offices.remove( index );
    }

    @Override
    public Space getBestSpace(){
        return offices.stream().max( Comparator.comparingDouble( Space::getArea ) )
                      .orElseThrow( IllegalStateException::new );
    }

    @Override
    public int hashCode(){
        return offices.hashCode();
    }

    @Override
    public boolean equals( Object o ){
        if( this == o ){
            return true;
        }
        if( !( o instanceof Floor ) ){
            return false;
        }
        Floor that = ( Floor ) o;
        return Arrays.equals( this.offices.toArray() , that.getSpaces() );
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        OfficeFloor clone = ( OfficeFloor ) super.clone();
        clone.offices = new OneSideLinkedCycleList<>();
        for( Space space : this ){
            clone.offices.add( ( Space ) space.clone() );
        }
        return clone;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append( "OfficeFloor (" ).append( getSpacesCount() ).append( ", " );
        for( Space space : getSpaces() ){
            stringBuilder.append( space ).append( ", " );
        }
        stringBuilder.append( ")" );
        return stringBuilder.toString();
    }

    @Override
    public Iterator<Space> iterator(){
        return offices.iterator();
    }
}
