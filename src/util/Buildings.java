package util;

import buildings.dwelling.DwellingFloor;
import buildings.dwelling.Flat;
import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;
import util.factories.BuildingFactory;
import util.factories.DwellingFactory;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public final class Buildings implements BuildingFactory{

    public static void outputBuilding( Building building , OutputStream outputStream ) throws IOException{
        DataOutputStream dataOutputStream = new DataOutputStream( new BufferedOutputStream( outputStream ) );
        dataOutputStream.writeInt( building.getFloorsCount() );
        for( Floor floor : building.getFloors() ){
            dataOutputStream.writeInt( floor.getSpacesCount() );
            for( Space space : floor.getSpaces() ){
                dataOutputStream.writeInt( space.getRoomsCount() );
                dataOutputStream.writeDouble( space.getArea() );
            }
        }
        dataOutputStream.flush();
    }

    public static Building inputBuilding( InputStream inputStream ) throws IOException{
        DataInputStream dataInputStream = new DataInputStream( inputStream );
        Floor[]         floors          = new Floor[ dataInputStream.readInt() ];
        Space[]         spaces;
        int             rooms;
        double          area;
        for( int i = 0 ; i < floors.length ; i++ ){
            spaces = new Flat[ dataInputStream.readInt() ];
            for( int j = 0 ; j < spaces.length ; j++ ){
                rooms = dataInputStream.readInt();
                area = dataInputStream.readDouble();
                spaces[ j ] = new Flat( area , rooms );
            }
            floors[ i ] = new DwellingFloor( spaces );
        }
        return buildingFactory.createBuilding( floors );
    }

    public static void writeBuilding( Building building , Writer writer ) throws IOException{
        writer.write( building.getFloorsCount().toString() );
        for( Floor floor : building.getFloors() ){
            writer.write( floor.getSpacesCount().toString() );
            for( Space space : floor.getSpaces() ){
                writer.write( space.getRoomsCount().toString() );
                writer.write( space.getArea().toString() );
            }
        }
        writer.flush();
    }

    public static Building readBuilding( Reader reader ) throws IOException{
        StreamTokenizer tokenizer = new StreamTokenizer( reader );
        tokenizer.nextToken();
        Floor[] floors = new Floor[ ( int ) tokenizer.nval ];
        Space[] spaces;
        int     rooms;
        double  area;
        for( int i = 0 ; i < floors.length ; i++ ){
            tokenizer.nextToken();
            spaces = new Space[ ( int ) tokenizer.nval ];
            for( int j = 0 ; j < spaces.length ; j++ ){
                tokenizer.nextToken();
                rooms = ( int ) tokenizer.nval;
                tokenizer.nextToken();
                area = tokenizer.nval;
                spaces[ j ] = new Flat( area , rooms );
            }
            floors[ i ] = new DwellingFloor( spaces );
        }
        return buildingFactory.createBuilding( floors );
    }

    public static void writeBuildingFormat( Building building , Writer writer ){
        PrintWriter printWriter = new PrintWriter( writer );
        printWriter.printf( "%d \n" , building.getFloorsCount() );
        for( Floor floor : building.getFloors() ){
            printWriter.printf( "%d \n" , floor.getSpacesCount() );
            for( Space space : floor.getSpaces() ){
                printWriter.printf( "%d " , space.getRoomsCount() );
                printWriter.printf( "%d " , space.getArea() );
            }
            printWriter.print( "\n" );
        }
        printWriter.flush();
    }

    public static Building readBuilding( Scanner scanner ) throws IOException{
        Floor[] floors = new Floor[ scanner.nextInt() ];
        Space[] spaces;
        int     rooms;
        double  area;
        for( int i = 0 ; i < floors.length ; i++ ){
            spaces = new Space[ scanner.nextInt() ];
            for( int j = 0 ; j < spaces.length ; j++ ){
                rooms = scanner.nextInt();
                area = scanner.nextDouble();
                spaces[ j ] = new Flat( area , rooms );
            }
            floors[ i ] = new DwellingFloor( spaces );
        }
        return buildingFactory.createBuilding( floors );
    }

    public static <T extends Comparable<T>> T[] sort( T[] objects ){
        return ( T[] ) Arrays.stream( objects ).sorted().toArray();
    }

    public static <T> T[] sort( T[] objects , Comparator<T> comparator ){
        return ( T[] ) Arrays.stream( objects ).sorted( comparator ).toArray();
    }

    private static BuildingFactory buildingFactory = new DwellingFactory();

    public static void setFactory( BuildingFactory buildingFactory ){
        Buildings.buildingFactory = buildingFactory;
    }

    @Override
    public Space createSpace( Double area ){
        return buildingFactory.createSpace( area );
    }

    @Override
    public Space createSpace( Integer roomsCount , Double area ){
        return buildingFactory.createSpace( roomsCount , area );
    }

    @Override
    public Floor createFloor( Integer spacesCount ){
        return buildingFactory.createFloor( spacesCount );
    }

    @Override
    public Floor createFloor( Space[] spaces ){
        return buildingFactory.createFloor( spaces );
    }

    @Override
    public Building createBuilding( int floorsCount , int[] spacesCounts ){
        return buildingFactory.createBuilding( floorsCount , spacesCounts );
    }

    @Override
    public Building createBuilding( Floor[] floors ){
        return buildingFactory.createBuilding( floors );
    }
}
