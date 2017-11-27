package buildings.dwelling;

import buildings.interfaces.Floor;
import buildings.interfaces.Space;
import exceptions.FloorIndexOutOfBoundsException;
import exceptions.SpaceIndexOutOfBoundsException;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DwellingFloor implements Floor{
    private Space[] flats;

    public DwellingFloor( Integer spacesCount ){
        this( Stream.generate( Flat::new ).limit( spacesCount ).collect( Collectors.toList() ) );
    }

    public DwellingFloor( List<Space> flats ){
        this.flats = flats.toArray( new Space[ flats.size() ] );
    }

    @Override
    public Integer getSpacesCount(){
        return flats.length;
    }

    @Override
    public Double getSpacesArea(){
        return Arrays.stream( flats ).mapToDouble( Space::getArea ).sum();
    }

    @Override
    public Integer getSpacesRooms(){
        return Arrays.stream( flats ).mapToInt( Space::getRoomsCount ).sum();
    }

    @Override
    public Space[] getSpaces(){
        return flats;
    }

    @Override
    public Space getSpace( int index ){
        try{
            return flats[ index ];
        }catch( IndexOutOfBoundsException e ){
            throw new FloorIndexOutOfBoundsException();
        }
    }

    @Override
    public void setSpace( int index , Space space ){
        try{
            this.flats[ index ] = space;
        }catch( IndexOutOfBoundsException e ){
            throw new FloorIndexOutOfBoundsException();
        }
    }

    @Override
    public void addSpace( int index , Space space ){
        if( !( 0 <= index && index <= getSpacesCount() ) ){
            throw new SpaceIndexOutOfBoundsException();
        }
        Space[] oldArray = flats;
        flats = new Space[ oldArray.length + 1 ];
        int i = 0;
        for( ; i < index ; i++ ){
            flats[ i ] = oldArray[ i ];
        }
        flats[ index ] = space;
        for( ; i < oldArray.length ; i++ ){
            flats[ i + 1 ] = oldArray[ i ];
        }
    }

    @Override
    public void removeSpace( int index ){
        checkSpacesRange( index );
        Space[] oldArray = flats;
        flats = new Space[ oldArray.length - 1 ];
        int i = 0;
        for( ; i < index ; i++ ){
            flats[ i ] = oldArray[ i ];
        }
        for( ; i < flats.length ; i++ ){
            flats[ i ] = oldArray[ i + 1 ];
        }
    }

    private void checkSpacesRange( int index ){
        if( !( 0 <= index && index < getSpacesCount() ) ){
            throw new SpaceIndexOutOfBoundsException();
        }
    }

    @Override
    public Space getBestSpace(){
        Space best = new Flat( 1.0 );
        for( Space space : flats ){
            if( space.getArea() > best.getArea() ){
                best = space;
            }
        }
        return best;
    }

    @Override
    public int hashCode(){
        return Arrays.hashCode( flats );
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
        return Arrays.equals( flats , that.getSpaces() );
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append( "DwellingFloor (" ).append( getSpacesCount() ).append( ", " );
        for( Space space : getSpaces() ){
            stringBuilder.append( space ).append( ", " );
        }
        stringBuilder.deleteCharAt( stringBuilder.length() - 2 );
        stringBuilder.append( ")" );
        return stringBuilder.toString();
    }

    @Override
    public Object clone(){
        DwellingFloor floor = null;
        try{
            floor = ( DwellingFloor ) super.clone();
            floor.flats = Arrays.copyOf( this.flats , this.flats.length );
        }catch( CloneNotSupportedException ignored ){
        }
        return floor;
    }

    @Override
    public Iterator<Space> iterator(){
        return new Iterator<>(){

            int goingIndex = 0;

            @Override
            public boolean hasNext(){
                return goingIndex < flats.length;
            }

            @Override
            public Space next(){
                Space space = flats[ goingIndex ];
                goingIndex++;
                return space;
            }
        };
    }


}
