package util;

import dwelling.Dwelling;
import dwelling.DwellingFloor;
import dwelling.Flat;
import interfaces.Building;
import interfaces.Floor;
import interfaces.Space;

import java.io.*;
import java.util.Scanner;

public final class Buildings{

    public static void outputBuilding( Building building , OutputStream outputStream ) throws IOException{
        DataOutputStream dataOutputStream = new DataOutputStream( new BufferedOutputStream( outputStream ) );
        dataOutputStream.writeInt( building.getFloorsCount() );
        for( Floor floor : building.getFloors() ){
            dataOutputStream.writeInt( floor.getSpacesCount() );
            for( Space space : floor.getSpaces() ){
                dataOutputStream.writeInt( space.getRoomsCount() );
                dataOutputStream.writeInt( space.getArea() );
            }
        }
        dataOutputStream.flush();
    }

    public static Building inputBuilding( InputStream inputStream ) throws IOException{
        DataInputStream dataInputStream = new DataInputStream( inputStream );
        Floor[]         floors          = new Floor[ dataInputStream.readInt() ];
        Space[]         spaces;
        int             rooms, area;
        for( int i = 0 ; i < floors.length ; i++ ){
            spaces = new Flat[ dataInputStream.readInt() ];
            for( int j = 0 ; j < spaces.length ; j++ ){
                rooms = dataInputStream.readInt();
                area = dataInputStream.readInt();
                spaces[ j ] = new Flat( area , rooms );
            }
            floors[ i ] = new DwellingFloor( spaces );
        }
        return new Dwelling( floors );
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
        int     rooms, area;
        for( int i = 0 ; i < floors.length ; i++ ){
            tokenizer.nextToken();
            spaces = new Space[ ( int ) tokenizer.nval ];
            for( int j = 0 ; j < spaces.length ; j++ ){
                tokenizer.nextToken();
                rooms = ( int ) tokenizer.nval;
                tokenizer.nextToken();
                area = ( int ) tokenizer.nval;
                spaces[ j ] = new Flat( area , rooms );
            }
            floors[ i ] = new DwellingFloor( spaces );
        }
        return new Dwelling( floors );
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
        int     rooms, area;
        for( int i = 0 ; i < floors.length ; i++ ){
            spaces = new Space[ scanner.nextInt() ];
            for( int j = 0 ; j < spaces.length ; j++ ){
                rooms = scanner.nextInt();
                area = scanner.nextInt();
                spaces[ j ] = new Flat( area , rooms );
            }
            floors[ i ] = new DwellingFloor( spaces );
        }
        return new Dwelling( floors );
    }
}
