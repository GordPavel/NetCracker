package buildings.interfaces;

import java.io.Serializable;

public interface Floor extends Serializable, Cloneable{
    Integer getSpacesCount();

    Double getSpacesArea();

    Integer getSpacesRooms();

    Space[] getSpaces();

    Space getSpace( int index );

    void setSpace( int index , Space space );

    void addSpace( int index , Space space );

    void removeSpace( int index );

    Space getBestSpace();

    Object clone() throws CloneNotSupportedException;

    String toString();

    int hashCode();

    boolean equals( Object object );
}
