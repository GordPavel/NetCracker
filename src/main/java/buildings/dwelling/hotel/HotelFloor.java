package buildings.dwelling.hotel;

import buildings.dwelling.DwellingFloor;
import buildings.dwelling.Flat;
import buildings.interfaces.Space;

import java.util.stream.Stream;

public class HotelFloor extends DwellingFloor{
    private Integer starsCount;
    private final static Integer DEFAULT_STARS = 1;

    public HotelFloor( Integer flatsCount ){
        this( Stream.generate( Flat::new ).limit( flatsCount ).toArray( value -> new Space[ flatsCount ] ) );
    }

    public HotelFloor( Space[] flats ){
        super( flats );
        this.starsCount = DEFAULT_STARS;
    }

    public Integer getStarsCount(){
        return starsCount;
    }

    public void setStarsCount( Integer starsCount ){
        this.starsCount = starsCount;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append( "HotelFloor (" ).append( getStarsCount() ).append( ", " ).append( getSpacesCount() )
                     .append( ", " );
        for( Space space : getSpaces() ){
            stringBuilder.append( space ).append( ", " );
        }
        stringBuilder.append( ")" );
        return stringBuilder.toString();
    }

    @Override
    public boolean equals( Object o ){
        return o instanceof HotelFloor && starsCount.equals( ( ( HotelFloor ) o ).starsCount ) && super.equals( o );
    }

    @Override
    public int hashCode(){
        return getSpacesCount() ^ getStarsCount() ^ super.hashCode();
    }
}
