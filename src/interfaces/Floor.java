package interfaces;

public interface Floor{
    Integer getSpacesCount();

    Integer getSpacesArea();

    Integer getSpacesRooms();

    Space[] getSpaces();

    Space getSpace( int index );

    void setSpace( int index , Space space );

    void addSpace( int index , Space space );

    void removeSpace( int index );

    Space getBestSpace();
}
