package officebuilding;

import exceptions.InvalidRoomsCountException;
import exceptions.InvalidSpaceAreaException;
import interfaces.Space;

import java.util.Objects;

public class Office implements Space{

    private static final Integer DEFAULT_AREA  = 250;
    private static final Integer DEFAULT_ROOMS = 1;

    private Integer area;
    private Integer rooms;

    public Office(){
        this( DEFAULT_AREA , DEFAULT_ROOMS );
    }

    public Office( Integer area ){
        this( area , DEFAULT_ROOMS );
    }

    public Office( Integer area , Integer rooms ){
        checkArea( area );
        checkRooms( rooms );
        this.area = area;
        this.rooms = rooms;
    }

    private void checkRooms( Integer rooms ){
        if( rooms <= 0 ){ throw new InvalidRoomsCountException(); }
    }

    private void checkArea( Integer area ){
        if( area <= 0 ){ throw new InvalidSpaceAreaException(); }
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
    public Integer getArea(){
        return area;
    }

    @Override
    public void setArea( Integer area ){
        checkArea( area );
        this.area = area;
    }

    @Override
    public int hashCode(){
        return ( 32 + area ) ^ rooms;
    }

    @Override
    public boolean equals( Object obj ){
        if( !( obj instanceof Office ) ){ return false; }
        Office office = ( Office ) obj;
        return Objects.equals( this.area , office.area ) && Objects.equals( this.rooms , office.rooms );
    }

    @Override
    public String toString(){
        return super.toString();
    }
}

