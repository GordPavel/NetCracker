package dwelling;

import exceptions.SpaceIndexOutOfBoundsException;
import interfaces.Floor;
import interfaces.Space;

import java.util.Arrays;
import java.util.stream.Stream;

public class DwellingFloor implements Floor{
    private Space[] flats;

    public DwellingFloor( Integer flatsCount ){
        this( Stream.generate( Flat::new ).limit( flatsCount ).toArray( value -> new Space[ flatsCount ] ) );
    }

    public DwellingFloor( Space[] flats ){
        this.flats = flats;
    }

    @Override
    public Integer getSpacesCount(){
        return flats.length;
    }

    @Override
    public Integer getSpacesArea(){
        return Arrays.stream( flats ).mapToInt( Space::getArea ).sum();
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
        checkSpacesRange( index );
        return flats[ index ];
    }

    @Override
    public void setSpace( int index , Space space ){
        checkSpacesRange( index );
        this.flats[ index ] = space;
    }

    @Override
    public void addSpace( int index , Space space ){
        if( !( 0 <= index && index <= getSpacesCount() ) ){ throw new SpaceIndexOutOfBoundsException(); }
        Space[] oldArray = flats;
        flats = new Space[ oldArray.length + 1 ];
        int i = 0;
        for( ; i < index ; i++ ){ flats[ i ] = oldArray[ i ]; }
        flats[ index ] = space;
        for( ; i < oldArray.length ; i++ ){ flats[ i + 1 ] = oldArray[ i ]; }
    }

    @Override
    public void removeSpace( int index ){
        checkSpacesRange( index );
        Space[] oldArray = flats;
        flats = new Space[ oldArray.length - 1 ];
        int i = 0;
        for( ; i < index ; i++ ){ flats[ i ] = oldArray[ i ]; }
        for( ; i < flats.length ; i++ ){ flats[ i ] = oldArray[ i + 1 ]; }
    }

    private void checkSpacesRange( int index ){
        if( !( 0 <= index && index < getSpacesCount() ) ){ throw new SpaceIndexOutOfBoundsException(); }
    }

    @Override
    public Space getBestSpace(){
        Space best = new Flat( 1 );
        for( Space space : flats ){ if( space.getArea() > best.getArea() ){ best = space; } }
        return best;
    }

    @Override
    public int hashCode(){
        return Arrays.stream( flats ).mapToInt( Object::hashCode ).reduce( ( left , right ) -> ( 31 + left ) ^ right )
                     .orElseThrow( () -> new IllegalStateException( "One of all spaces has problems with hashCode" ) );
    }


    @Override
    public boolean equals( Object obj ){
        if( !( obj instanceof DwellingFloor ) ){ return false; }
        DwellingFloor dwellingFloor = ( DwellingFloor ) obj;
        return Arrays.equals( this.flats , dwellingFloor.flats );
    }

    @Override
    public String toString(){
        return Arrays.stream( flats ).map( space -> new StringBuilder( space.toString() ) )
                     .reduce( new StringBuilder( ( "dwellingFloor" ) ) ,
                              ( stringBuilder , stringBuilder2 ) -> stringBuilder.append( "\n" )
                                                                                 .append( stringBuilder2 ) ).toString();
    }
}
