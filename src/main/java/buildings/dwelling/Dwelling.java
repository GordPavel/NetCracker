package buildings.dwelling;

import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;
import exceptions.FloorIndexOutOfBoundsException;
import exceptions.SpaceIndexOutOfBoundsException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Dwelling implements Building{

    protected Floor[] floors;

    public Dwelling( int floors , int... spacesCountOnEachFloor ){
        if( floors != spacesCountOnEachFloor.length ){
            throw new IllegalArgumentException( "Floors count not equals to array of spaces count length." );
        }
        this.floors = Arrays.stream( spacesCountOnEachFloor ).mapToObj( DwellingFloor::new )
                            .toArray( value -> new Floor[ floors ] );
    }

    public Dwelling( Floor[] floors ){
        this.floors = floors;
    }


    @Override
    public Integer getFloorsCount(){
        return floors.length;
    }

    @Override
    public Integer getSpacesCount(){
        return Arrays.stream( floors ).mapToInt( Floor::getSpacesCount ).sum();
    }

    @Override
    public Double getSpacesArea(){
        return Arrays.stream( floors ).mapToDouble( Floor::getSpacesArea ).sum();
    }

    @Override
    public Integer getSpacesRooms(){
        return Arrays.stream( floors ).mapToInt( Floor::getSpacesRooms ).sum();
    }

    @Override
    public Floor[] getFloors(){
        return floors;
    }

    @Override
    public Floor getFloor( int index ){
        try{
            return floors[ index ];
        }catch( IndexOutOfBoundsException e ){
            throw new FloorIndexOutOfBoundsException();
        }
    }

    @Override
    public void setFloor( int index , Floor floor ){
        try{
            this.floors[ index ] = floor;
        }catch( IndexOutOfBoundsException e ){
            throw new FloorIndexOutOfBoundsException();
        }
    }

    @Override
    public Space getSpace( int index ){
        checkSpacesRange( index );
        int k = 0;
        for( Floor floor : floors ){
            for( Space space : floor.getSpaces() ){
                if( k++ == index ){
                    return space;
                }
            }
        }
        return null;
    }

    private void checkSpacesRange( int index ){
        if( !( 0 <= index && index < getSpacesCount() ) ){
            throw new SpaceIndexOutOfBoundsException();
        }
    }

    @Override
    public void setSpace( int index , Space space ){
        checkSpacesRange( index );
        int k = 0;
        for( Floor floor : floors ){
            for( int i = 0, size = floor.getSpacesCount() ; i < size ; i++ ){
                if( k++ == index ){
                    floor.getSpaces()[ i ] = space;
                }
            }
        }
    }

    @Override
    public void addSpace( int index , Space space ){
        if( !( 0 <= index && index <= getSpacesCount() ) ){
            throw new SpaceIndexOutOfBoundsException();
        }
        int i = 0, downLimit = 0, upperLimit = floors[ 0 ].getSpacesCount();
        for( ; upperLimit <= index ; downLimit = upperLimit , i++ , upperLimit += floors[ i ].getSpacesCount() ){
        }
        floors[ i ].addSpace( index - downLimit , space );
    }

    @Override
    public void removeSpace( int index ){
        checkSpacesRange( index );
        int i = 0, downLimit = 0, upperLimit = floors[ 0 ].getSpacesCount();
        for( ; upperLimit <= index ; downLimit = upperLimit , i++ , upperLimit += floors[ i ].getSpacesCount() ){
        }
        floors[ i ].removeSpace( index - downLimit );
    }

    @Override
    public Space getBestSpace(){
        return Arrays.stream( getSpaces() ).max( Space::compareTo )
                     .orElseThrow( () -> new IllegalStateException( "Dwelling is empty" ) );
    }

    @Override
    public Space[] getBestSpaces(){
        return Arrays.stream( getSpaces() ).sorted( Space::compareTo )
                     .toArray( value -> new Space[ getSpacesCount() ] );
    }

    private Space[] getSpaces(){
        List<Space> spaces = new ArrayList<>();
        for( Floor floor : floors ){
            spaces.addAll( Arrays.asList( floor.getSpaces() ) );
        }
        return spaces.toArray( new Space[ getSpacesCount() ] );
    }

    @Override
    public int hashCode(){
        return Arrays.hashCode( floors );
    }

    @Override
    public boolean equals( Object obj ){
        if( this == obj ){
            return true;
        }
        if( !( obj instanceof Building ) ){
            return false;
        }
        Building dwelling = ( Building ) obj;
        return Arrays.equals( this.floors , dwelling.getFloors() );
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        Dwelling clone = ( Dwelling ) super.clone();
        clone.floors = Arrays.copyOf( this.floors , floors.length );
        return clone;
    }

    @Override
    public Iterator<Floor> iterator(){
        return new Iterator<>(){
            int goingIndex = 0;

            @Override
            public boolean hasNext(){
                return goingIndex < floors.length;
            }

            @Override
            public Floor next(){
                Floor floor = floors[ goingIndex ];
                goingIndex++;
                return floor;
            }
        };
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append( "Dwelling (" ).append( getFloorsCount() ).append( ", " );
        for( Floor floor : getFloors() ){
            stringBuilder.append( floor ).append( ", " );
        }
        stringBuilder.append( ")" );
        return stringBuilder.toString();

    }
}

