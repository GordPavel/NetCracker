package buildings.interfaces;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

public interface Space extends Serializable, Cloneable, Comparable<Space>{
    Integer getRoomsCount();

    void setRoomsCount( Integer roomsCount );

    Double getArea();

    void setArea( Double area );

    Object clone();

    String toString();

    int hashCode();

    boolean equals( Object object );

    @Override
    default int compareTo( Space o ){
        return Objects.compare( this , o , Comparator.comparingDouble( Space::getArea ) );
    }
}
