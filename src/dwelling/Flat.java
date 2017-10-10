package dwelling;

import exceptions.InvalidRoomsCountException;
import exceptions.InvalidSpaceAreaException;
import interfaces.Space;

import java.util.Objects;

public class Flat implements Space{

    private static final Integer DEFAULT_SPACE = 50;
    private static final Integer DEFAULT_ROOMS = 2;

    private Integer area;
    private Integer rooms;

    public Flat(){
        this( DEFAULT_SPACE , DEFAULT_ROOMS );
    }

    public Flat( Integer area ){
        this( area , DEFAULT_ROOMS );
    }

    public Flat( Integer area , Integer rooms ){
        if( area <= 0 ){ throw new InvalidSpaceAreaException(); }
        if( rooms <= 0 ){ throw new InvalidRoomsCountException(); }
        this.area = area;
        this.rooms = rooms;
    }

    @Override
    public Integer getRoomsCount(){
        return rooms;
    }

    @Override
    public void setRoomsCount( Integer roomsCount ){
        this.rooms = roomsCount;
    }

    @Override
    public Integer getArea(){
        return area;
    }

    @Override
    public void setArea( Integer area ){
        this.area = area;
    }

    @Override
    public int hashCode(){
        return ( 31 + area ) ^ rooms;
    }

    @Override
    public boolean equals( Object obj ){
        if( !( obj instanceof Flat ) ){ return false; }
        Flat flat = ( Flat ) obj;
        return Objects.equals( this.area , flat.area ) && Objects.equals( this.rooms , flat.rooms );
    }

    @Override
    public String toString(){
        return String.format( "area = %d, rooms count = %d." , area , rooms );
    }
}
