package buildings.dwelling.hotel;

import buildings.dwelling.Dwelling;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

public class Hotel extends Dwelling{

    public Hotel( int floors , int... spacesCountOnEachFloor ){
        super( floors , spacesCountOnEachFloor );
    }

    public Hotel( Floor[] floors ){
        super( floors );
    }

    public Integer getStars(){
        return Arrays.stream( floors ).filter( floor -> floor instanceof HotelFloor )
                     .mapToInt( floor -> ( ( HotelFloor ) floor ).getStarsCount() ).max()
                     .orElseThrow( IllegalStateException::new );
    }

    private Stream<HotelSpace> getHotelSpaces(){
        return Arrays.stream( floors ).filter( floor -> floor instanceof HotelFloor )
                     .map( floor -> ( HotelFloor ) floor ).map( floor -> Stream.of( floor.getSpaces() )
                                                                               .map( space -> new HotelSpace( space ,
                                                                                                              floor.getStarsCount() ) ) )
                     .collect( Stream::empty , ( spaceStream , hotelSpaceStream ) -> Stream
                             .concat( spaceStream , Stream.of( hotelSpaceStream ) ) , Stream::concat );
    }

    private class HotelSpace{
        private Space   space;
        private Integer stars;

        public HotelSpace( Space space , Integer stars ){
            this.space = space;
            this.stars = stars;
        }
    }

    @Override
    public Space getBestSpace(){
        return getHotelSpaces().max( Comparator.comparingDouble(
                hotelSpace -> hotelSpace.space.getArea() * getCoeff( hotelSpace.stars ) ) )
                               .orElseThrow( IllegalStateException::new ).space;
    }

    @Override
    public boolean equals( Object obj ){
        return obj instanceof Hotel && super.equals( obj );
    }

    @Override
    public int hashCode(){
        return getFloorsCount() ^ super.hashCode();
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append( "Dwelling (" ).append( getStars() ).append( ", " ).append( getFloorsCount() )
                     .append( ", " );
        for( Floor floor : getFloors() ){
            stringBuilder.append( floor ).append( ", " );
        }
        stringBuilder.append( ")" );
        return stringBuilder.toString();
    }

    private static Double getCoeff( Integer stars ){
        switch( stars ){
            case 1:
                return 0.25;
            case 2:
                return 0.5;
            case 3:
                return 1.0;
            case 4:
                return 1.25;
            case 5:
                return 1.5;

            default:
                throw new IllegalStateException();
        }
    }
}
