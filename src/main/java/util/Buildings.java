package util;

import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;
import util.factories.DwellingFactory;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings( "JavaReflectionMemberAccess" )
public final class Buildings{

    public static void outputBuilding( Building building , OutputStream outputStream ) throws IOException{
        DataOutputStream dataOutputStream = new DataOutputStream( new BufferedOutputStream( outputStream ) );
        dataOutputStream.writeInt( building.getFloorsCount() );
        for( Floor floor : building.getFloors() ){
            dataOutputStream.writeInt( floor.getSpacesCount() );
            for( Space space : floor.getSpaces() ){
                dataOutputStream.writeDouble( space.getArea() );
                dataOutputStream.writeInt( space.getRoomsCount() );
            }
        }
        dataOutputStream.flush();
    }

    public static Building inputBuilding( InputStream inputStream ) throws IOException{
        DataInputStream dataInputStream = new DataInputStream( inputStream );
        List<Floor>     floors          = new ArrayList<>( dataInputStream.readInt() );
        List<Space>     spaces;
        for( int i = 0 ; i < floors.size() ; i++ ){
            spaces = new ArrayList<>( dataInputStream.readInt() );
            for( int j = 0 ; j < spaces.size() ; j++ ){
                double area       = dataInputStream.readDouble();
                int    roomsCount = dataInputStream.readInt();
                spaceCollector( spaces , area , roomsCount );
            }
            floorsCollector( floors , spaces );
        }
        return createBuilding( floors );
    }

    private static void spaceCollector( List<Space> spaces , double area , int roomsCount ){
        spaces.add( buildingFactory.createSpace( area , roomsCount ) );
    }

    private static void floorsCollector( List<Floor> floors , List<Space> spaces ){
        floors.add( buildingFactory.createFloor( spaces ) );
    }

    public static Building inputBuilding( InputStream inputStream , Class<? extends Building> buildingClass ,
                                          Class<? extends Floor> floorClass , Class<? extends Space> spaceClass ) throws
                                                                                                                  IOException{
        DataInputStream dataInputStream = new DataInputStream( inputStream );
        List<Floor>     floors          = new ArrayList<>( dataInputStream.readInt() );
        List<Space>     spaces;
        for( int i = 0 ; i < floors.size() ; i++ ){
            spaces = new ArrayList<>( dataInputStream.readInt() );
            for( int j = 0 ; j < spaces.size() ; j++ ){
                double area       = dataInputStream.readDouble();
                int    roomsCount = dataInputStream.readInt();
                spaces.add( Buildings.createSpace( spaceClass , area , roomsCount ) );
            }
            floors.add( Buildings.createFloor( floorClass , spaces ) );
        }
        return Buildings.createBuilding( buildingClass , floors );
    }

    public static void writeBuilding( Building building , Writer writer ) throws IOException{
        writer.write( building.getFloorsCount().toString() );
        for( Floor floor : building.getFloors() ){
            writer.write( floor.getSpacesCount().toString() );
            for( Space space : floor.getSpaces() ){
                writer.write( space.getArea().toString() );
                writer.write( space.getRoomsCount().toString() );
            }
        }
        writer.flush();
    }

    public static Building readBuilding( Reader reader ) throws IOException{
        StreamTokenizer tokenizer = new StreamTokenizer( reader );
        tokenizer.nextToken();
        List<Floor> floors = new ArrayList<>( ( int ) tokenizer.nval );
        List<Space> spaces;
        int         rooms;
        double      area;
        for( int i = 0 ; i < floors.size() ; i++ ){
            tokenizer.nextToken();
            spaces = new ArrayList<>( ( int ) tokenizer.nval );
            for( int j = 0 ; j < spaces.size() ; j++ ){
                tokenizer.nextToken();
                area = tokenizer.nval;
                tokenizer.nextToken();
                rooms = ( int ) tokenizer.nval;
                spaceCollector( spaces , area , rooms );
            }
            floorsCollector( floors , spaces );
        }
        return createBuilding( floors );
    }

    public static Building readBuilding( Reader reader , Class<? extends Building> buildingClass ,
                                         Class<? extends Floor> floorClass , Class<? extends Space> spaceClass ) throws
                                                                                                                 IOException{
        StreamTokenizer tokenizer = new StreamTokenizer( reader );
        tokenizer.nextToken();
        List<Floor> floors = new ArrayList<>( ( int ) tokenizer.nval );
        List<Space> spaces;
        int         rooms;
        double      area;
        for( int i = 0 ; i < floors.size() ; i++ ){
            tokenizer.nextToken();
            spaces = new ArrayList<>( ( int ) tokenizer.nval );
            for( int j = 0 ; j < spaces.size() ; j++ ){
                tokenizer.nextToken();
                area = tokenizer.nval;
                tokenizer.nextToken();
                rooms = ( int ) tokenizer.nval;
                spaces.add( Buildings.createSpace( spaceClass , area , rooms ) );
            }
            floors.add( Buildings.createFloor( floorClass , spaces ) );
        }
        return Buildings.createBuilding( buildingClass , floors );
    }

    public static void writeBuildingFormat( Building building , Writer writer ){
        PrintWriter printWriter = new PrintWriter( writer );
        printWriter.printf( "%d " , building.getFloorsCount() );
        for( Floor floor : building.getFloors() ){
            printWriter.printf( "%d " , floor.getSpacesCount() );
            for( Space space : floor.getSpaces() ){
                printWriter.printf( "%.3f " , space.getArea() );
                printWriter.printf( "%d " , space.getRoomsCount() );
            }
            printWriter.print( " " );
        }
        printWriter.println();
        printWriter.flush();
    }

    public static Building readBuilding( Scanner scanner ){
        List<Floor> floors = new ArrayList<>( scanner.nextInt() );
        List<Space> spaces;
        for( int i = 0 ; i < floors.size() ; i++ ){
            spaces = new ArrayList<>( scanner.nextInt() );
            for( int j = 0 ; j < spaces.size() ; j++ ){
                double area       = scanner.nextDouble();
                int    roomsCount = scanner.nextInt();
                spaceCollector( spaces , area , roomsCount );
            }
            floorsCollector( floors , spaces );
        }
        return createBuilding( floors );
    }

    public static Building readBuilding( Scanner scanner , Class<? extends Building> buildingClass ,
                                         Class<? extends Floor> floorClass , Class<? extends Space> spaceClass ){
        List<Floor> floors = new ArrayList<>( scanner.nextInt() );
        List<Space> spaces;
        for( int i = 0 ; i < floors.size() ; i++ ){
            spaces = new ArrayList<>( scanner.nextInt() );
            for( int j = 0 ; j < spaces.size() ; j++ ){
                spaces.add( Buildings.createSpace( spaceClass , scanner.nextDouble() , scanner.nextInt() ) );
            }
            floors.add( Buildings.createFloor( floorClass , spaces ) );
        }
        return Buildings.createBuilding( buildingClass , floors );
    }

    private static BuildingFactory buildingFactory = new DwellingFactory();

    public static void setFactory( BuildingFactory buildingFactory ){
        Buildings.buildingFactory = buildingFactory;
    }

    public static Space createSpace( Double area ){
        return buildingFactory.createSpace( area );
    }

    public static Space createSpace( Integer roomsCount , Double area ){
        return buildingFactory.createSpace( area , roomsCount );
    }

    public static Space createSpace( Class<? extends Space> spaceClass , Double area , Integer roomsCount ){
        try{
            return spaceClass.getConstructor( area.getClass() , roomsCount.getClass() )
                             .newInstance( area , roomsCount );
        }catch( InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e ){
            throw new IllegalArgumentException( e );
        }
    }

    public static Space createSpace( Class<? extends Space> spaceClass , Double area ){
        try{
            return spaceClass.getConstructor( area.getClass() ).newInstance( area );
        }catch( InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e ){
            throw new IllegalArgumentException( e );
        }
    }

    public static Floor createFloor( Integer spacesCount ){
        return buildingFactory.createFloor( spacesCount );
    }

    public static Floor createFloor( Class<? extends Floor> floorClass , Integer spacesCount ){
        try{
            return floorClass.getConstructor( spacesCount.getClass() ).newInstance( spacesCount );
        }catch( InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e ){
            throw new IllegalArgumentException( e );
        }
    }

    public static Floor createFloor( List<Space> spaces ){
        return buildingFactory.createFloor( spaces );
    }

    public static Floor createFloor( Class<? extends Floor> floorClass , List<Space> spaces ){
        try{
            return floorClass.getConstructor( spaces.getClass() ).newInstance( spaces );
        }catch( InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e ){
            throw new IllegalArgumentException( e );
        }
    }

    public static Building createBuilding( Integer floorsCount , Integer[] spacesCounts ){
        return buildingFactory.createBuilding( floorsCount , spacesCounts );
    }

    public static Building createBuilding( Class<? extends Building> buildingCLass , Integer floorsCount ,
                                           Integer[] spacesCounts ){
        try{
            return buildingCLass.getConstructor( floorsCount.getClass() , spacesCounts.getClass() )
                                .newInstance( floorsCount , spacesCounts );
        }catch( InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e ){
            throw new IllegalArgumentException( e );
        }
    }

    public static Building createBuilding( List<Floor> floors ){
        return buildingFactory.createBuilding( floors );
    }

    public static Building createBuilding( Class<? extends Building> buildingCLass , List<Floor> floors ){
        try{
            return buildingCLass.getConstructor( floors.getClass() ).newInstance( floors );
        }catch( InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e ){
            throw new IllegalArgumentException( e );
        }
    }

}
