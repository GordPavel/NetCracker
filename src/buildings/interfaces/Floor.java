package buildings.interfaces;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

public interface Floor extends Serializable, Cloneable, Iterable<Space>, Comparable<Floor>{
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

    @Override
    default int compareTo( Floor o ){
        return Objects.compare( this , o , Comparator.comparingDouble( Floor::getSpacesCount ) );
    }
}
