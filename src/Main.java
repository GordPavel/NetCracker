import interfaces.Building;
import interfaces.Floor;
import interfaces.Space;
import officebuilding.Office;
import officebuilding.OfficeBuilding;
import officebuilding.OfficeFloor;
import util.Buildings;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main{

    private static Random random = new Random( System.currentTimeMillis() );

    private static Building officeBuilding = new OfficeBuilding( Stream.generate( new Supplier<Floor>(){
        int i = 0;

        @Override
        public Floor get(){
            return new OfficeFloor( getOffices( ++i ) );
        }
    } ).limit( 5 ).collect( Collectors.toList() ) );

    private static List<Space> getOffices( int i ){
        return Stream.generate( () -> new Office( random.nextInt( 50 ) + 25 , random.nextInt( 5 ) + 3 ) ).limit( i )
                     .collect( Collectors.toList() );
    }

    public static void main( String[] args ) throws IOException{
//        ObjectOutputStream outputStream = new ObjectOutputStream( System.out );
//        outputStream.writeObject( officeBuilding );
//
        Buildings.writeBuildingFormat( officeBuilding , new FileWriter( "/Users/pavelgordeev/Desktop/test.txt" ) );
        Buildings.writeBuildingFormat(
                Buildings.readBuilding( new Scanner( Paths.get( "/Users/pavelgordeev/Desktop/test.txt" ) ) ) ,
                new FileWriter( "/Users/pavelgordeev/Desktop/test1.txt" ) );
    }
}
