package buildings.dwelling;

import buildings.interfaces.Space;
import exceptions.InvalidRoomsCountException;
import exceptions.InvalidSpaceAreaException;

import java.util.Objects;

public class Flat implements Space{

    private static final Double  DEFAULT_SPACE = 50.0;
    private static final Integer DEFAULT_ROOMS = 2;

    private Double  area;
    private Integer rooms;

    public Flat(){
        this( DEFAULT_SPACE , DEFAULT_ROOMS );
    }

    public Flat( Double area ){
        this( area , DEFAULT_ROOMS );
    }

    public Flat( Double area , Integer rooms ){
        checkArea( area );
        checkRooms( rooms );
        this.area = area;
        this.rooms = rooms;
    }

    private void checkRooms( Integer rooms ){
        if( rooms <= 0 ){
            throw new InvalidRoomsCountException();
        }
    }

    private void checkArea( Double area ){
        if( area <= 0 ){
            throw new InvalidSpaceAreaException();
        }
    }

    @Override
    public Integer getRoomsCount(){
        return rooms;
    }

    @Override
    public void setRoomsCount( Integer rooms ){
        checkRooms( rooms );
        this.rooms = rooms;
    }

    @Override
    public Double getArea(){
        return area;
    }

    @Override
    public void setArea( Double area ){
        checkArea( area );
        this.area = area;
    }

    @Override
    public int hashCode(){
        return this.toString().hashCode();
    }

    @Override
    public boolean equals( Object obj ){
        if( this == obj ){
            return true;
        }
        if( !( obj instanceof Space ) ){
            return false;
        }
        Space flat = ( Space ) obj;
        return Objects.equals( this.area , flat.getArea() ) && Objects.equals( this.rooms , flat.getRoomsCount() );
    }

    @Override
    public String toString(){
        return String.format( "Flat (%d , %.2f)" , getRoomsCount() , getArea() );
    }

    @Override
    public Object clone(){
        Flat clone = null;
        try{
            clone = ( Flat ) super.clone();
            clone.area = area;
            clone.rooms = rooms;
        }catch( CloneNotSupportedException ignored ){
        }
        return clone;
    }
}
