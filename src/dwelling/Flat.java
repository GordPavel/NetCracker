package dwelling;

import exceptions.InvalidRoomsCountException;
import exceptions.InvalidSpaceAreaException;
import interfaces.Space;

import java.io.Serializable;
import java.util.Objects;

public class Flat implements Space, Serializable{

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
        return ( 31 + area ) ^ rooms;
    }

    @Override
    public boolean equals( Object obj ){
        if( !( obj instanceof Space ) ){ return false; }
        Space flat = ( Space ) obj;
        return Objects.equals( this.area , flat.getArea() ) && Objects.equals( this.rooms , flat.getRoomsCount() );
    }

    @Override
    public String toString(){
        return String.format( "area = %d, rooms count = %d." , area , rooms );
    }
}
