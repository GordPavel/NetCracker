package buildings.interfaces;

import java.io.Serializable;

public interface Space extends Serializable, Cloneable{
    Integer getRoomsCount();

    void setRoomsCount( Integer roomsCount );

    Double getArea();

    void setArea( Double area );

    Object clone();

    String toString();

    int hashCode();

    boolean equals( Object object );
}
