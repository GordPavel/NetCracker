package buildings.net.client;

import buildings.dwelling.Dwelling;
import buildings.dwelling.hotel.Hotel;
import buildings.interfaces.Building;
import buildings.officebuilding.OfficeBuilding;
import util.BuildingFactory;
import util.Buildings;
import util.factories.DwellingFactory;
import util.factories.HotelFactory;
import util.factories.OfficeFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SerialClient{

    private static Random random = new Random( System.currentTimeMillis() );

    public static void main( String[] args ){
        generateBuildings( args[ 0 ] , args[ 1 ] );
        try( Socket socket = new Socket( "localhost" , 5673 ) ;
             ObjectOutputStream outputStream = new ObjectOutputStream( socket.getOutputStream() ) ;
             ObjectInputStream inputStream = new ObjectInputStream( socket.getInputStream() ) ;
             PrintWriter printWriter = new PrintWriter( Files.newBufferedWriter( Paths.get( args[ 2 ] ) ) ) ){
            Iterator<String> buildingsTypes = Files.lines( Paths.get( args[ 1 ] ) ).iterator();
            Iterator<Building> buildings = Files.lines( Paths.get( args[ 0 ] ) ).map( string -> {
                Buildings.setFactory( buildingFactory( buildingsTypes.next() ) );
                return Buildings.readBuilding( new Scanner( string ) );
            } ).iterator();
            while( buildings.hasNext() ){
                System.out.print( "Client sends building " );
                outputStream.writeObject( buildings.next() );
                try{
                    Double price = ( Double ) inputStream.readObject();
                    System.out.printf( "%.3f\n" , price );
                    printWriter.printf( "%.3f\n" , price );
                }catch( ClassCastException e ){
                    System.out.println( "arrested" );
                    printWriter.println( "arrested" );
                }catch( ClassNotFoundException e ){
                    e.printStackTrace();
                }
                Thread.sleep( 3000 );
            }
        }catch( IOException | InterruptedException e ){
            e.printStackTrace();
        }
    }

    private static void generateBuildings( String file1Name , String file2Name ){
        try( PrintWriter file1 = new PrintWriter( Files.newBufferedWriter( Paths.get( file1Name ) ) ) ;
             PrintWriter file2 = new PrintWriter( Files.newBufferedWriter( Paths.get( file2Name ) ) ) ){
            Stream.generate( SerialClient::generateBuilding ).limit( random.nextInt( 15 ) + 1 )
                  .forEachOrdered( building -> {
                      Buildings.writeBuildingFormat( building , file1 );
                      if( building instanceof Hotel ){
                          file2.println( "Hotel" );
                      }else if( building instanceof Dwelling ){
                          file2.println( "Dwelling" );
                      }else if( building instanceof OfficeBuilding ){
                          file2.println( "OfficeBuilding" );
                      }
                  } );
        }catch( IOException e ){
            e.printStackTrace();
        }
    }

    private static BuildingFactory getBuildingType(){
        switch( random.nextInt( 3 ) ){
            case 0:
                return new DwellingFactory();
            case 1:
                return new OfficeFactory();
            case 2:
                return new HotelFactory();
            default:
                return null;
        }
    }

    private static Building generateBuilding(){
        Buildings.setFactory( getBuildingType() );
        int      spacesCount = random.nextInt( 15 ) + 5;
        Building building    = Buildings.createBuilding( 1 , new int[]{ spacesCount } );
        IntStream.range( 0 , spacesCount ).forEachOrdered(
                i -> building.setSpace( i , Buildings.createSpace( 1 , random.nextDouble() * 100 ) ) );
        return building;
    }

    private static BuildingFactory buildingFactory( String string ){
        switch( string ){
            case "Dwelling":
                return new DwellingFactory();
            case "Hotel":
                return new HotelFactory();
            case "OfficeBuilding":
                return new OfficeFactory();
            default:
                throw new IllegalArgumentException();
        }
    }
}
