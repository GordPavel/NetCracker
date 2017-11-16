package buildings.net.client.sequental;

import buildings.dwelling.Dwelling;
import buildings.dwelling.hotel.Hotel;
import buildings.interfaces.Building;
import buildings.officebuilding.OfficeBuilding;
import exceptions.BuildingUnderArrestException;
import util.Buildings;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class BinaryServer{
    public static void main( String[] args ) throws IOException{
        ServerSocket serverSocket = new ServerSocket( 5673 );
        PrintWriter  printWriter;
        InputStream  inputStream;
        Socket       socket;
        while( true ){
            socket = serverSocket.accept();
            printWriter = new PrintWriter( new DataOutputStream( socket.getOutputStream() ) );
            inputStream = socket.getInputStream();
            while( true ){
                try{
                    printWriter.println( getPrice( Buildings.inputBuilding( inputStream ) ) );
                    printWriter.flush();
                }catch( BuildingUnderArrestException e ){
                    printWriter.println( "Building is arrested" );
                    printWriter.flush();
                }catch( IOException e ){
                    break;
                }
            }
        }
    }

    private static Integer getCoef( Building building ) throws BuildingUnderArrestException{
        if( isArrested( building ) ){
            throw new BuildingUnderArrestException();
        }else if( building instanceof Hotel ){
            return 2000;
        }else if( building instanceof Dwelling ){
            return 1000;
        }else if( building instanceof OfficeBuilding ){
            return 1500;
        }else{
            throw new IllegalArgumentException();
        }
    }

    private static Double getPrice( Building building ){
        return building.getSpacesArea() * getCoef( building );
    }

    private static Boolean isArrested( Building building ){
        return new Random( System.currentTimeMillis() ).nextInt( 10 ) == 0;
    }
}
