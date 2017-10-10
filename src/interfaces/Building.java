package interfaces;

public interface Building{
    Integer getFloorsCount();

    Integer getSpacesCount();

    Integer getSpacesArea();

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
}
