package buildings.net.client;

import buildings.dwelling.Dwelling;
import buildings.dwelling.hotel.Hotel;
import buildings.interfaces.Building;
import buildings.officebuilding.OfficeBuilding;
import org.junit.jupiter.api.Test;
import util.BuildingFactory;
import util.Buildings;
import util.factories.DwellingFactory;
import util.factories.HotelFactory;
import util.factories.OfficeFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class BinaryClientTest{

    private String file1Name = "/Users/pavelgordeev/Desktop/buildings";
    private String file2Name = "/Users/pavelgordeev/Desktop/buildingsTypes";
    private Random random    = new Random( System.currentTimeMillis() );

    @Test
    void generateBuildings(){
        try( PrintWriter file1 = new PrintWriter( Files.newBufferedWriter( Paths.get( file1Name ) ) ) ;
             PrintWriter file2 = new PrintWriter( Files.newBufferedWriter( Paths.get( file2Name ) ) ) ){
            Stream.generate( this::generateBuilding ).limit( random.nextInt( 15 ) + 1 ).forEachOrdered( building -> {
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

    private Building generateBuilding(){
        Buildings.setFactory( getBuildingType() );
        int      spacesCount = random.nextInt( 15 ) + 5;
        Building building    = Buildings.createBuilding( 1 , new int[]{ spacesCount } );
        IntStream.range( 0 , spacesCount ).forEachOrdered(
                i -> building.setSpace( i , Buildings.createSpace( 1 , random.nextDouble() * 100 ) ) );
        return building;
    }

    private BuildingFactory getBuildingType(){
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

    @Test
    void readBuildings() throws IOException{
        Iterator<String> buildingsTypes = Files.lines( Paths.get( file2Name ) ).iterator();
        Files.lines( Paths.get( file1Name ) ).map( string -> {
            Buildings.setFactory( buildingFactory( buildingsTypes.next() ) );
            return Buildings.readBuilding( new Scanner( string ) );
        } ).forEachOrdered( System.out::println );
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