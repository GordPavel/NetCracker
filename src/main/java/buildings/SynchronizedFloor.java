package buildings;

import buildings.interfaces.Floor;
import buildings.interfaces.Space;

import java.util.Iterator;

public class SynchronizedFloor implements Floor{
    private final Floor floor;

    public SynchronizedFloor( Floor floor ){
        this.floor = floor;
    }

    @Override
    public Integer getSpacesCount(){
        synchronized( floor ){
            return floor.getSpacesCount();
        }
    }

    @Override
    public Double getSpacesArea(){
        synchronized( floor ){
            return floor.getSpacesArea();
        }
    }

    @Override
    public Integer getSpacesRooms(){
        synchronized( floor ){
            return floor.getSpacesRooms();
        }
    }

    @Override
    public Space[] getSpaces(){
        synchronized( floor ){
            return floor.getSpaces();
        }
    }

    @Override
    public Space getSpace( int index ){
        synchronized( floor ){
            return floor.getSpace( index );
        }
    }

    @Override
    public void setSpace( int index , Space space ){
        synchronized( floor ){
            floor.setSpace( index , space );
        }
    }

    @Override
    public void addSpace( int index , Space space ){
        synchronized( floor ){
            floor.addSpace( index , space );
        }
    }

    @Override
    public void removeSpace( int index ){
        synchronized( floor ){
            floor.removeSpace( index );
        }
    }

    @Override
    public Space getBestSpace(){
        synchronized( floor ){
            return floor.getBestSpace();
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        synchronized( floor ){
            return floor.clone();
        }
    }

    @Override
    public int compareTo( Floor o ){
        synchronized( floor ){
            return floor.compareTo( o );
        }
    }

    @Override
    public Iterator<Space> iterator(){
        synchronized( floor ){
            return floor.iterator();
        }
    }
}
