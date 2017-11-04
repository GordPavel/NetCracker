package buildings.interfaces;

import java.io.Serializable;

public interface Building extends Serializable, Cloneable, Iterable<Floor>{
    Integer getFloorsCount();

    Integer getSpacesCount();

    Double getSpacesArea();

    Integer getSpacesRooms();

    Floor[] getFloors();

    Floor getFloor( int index );

    void setFloor( int index , Floor floor );

    Space getSpace( int index );

    void setSpace( int index , Space space );

    void addSpace( int index , Space space );

    void removeSpace( int index );

    Space getBestSpace();

    Space[] getBestSpaces();

    Object clone() throws CloneNotSupportedException;

    String toString();

    int hashCode();

    boolean equals( Object object );
}
