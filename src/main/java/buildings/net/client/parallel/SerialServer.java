package buildings.net.client.parallel;

import buildings.dwelling.Dwelling;
import buildings.dwelling.hotel.Hotel;
import buildings.interfaces.Building;
import buildings.officebuilding.OfficeBuilding;
import exceptions.BuildingUnderArrestException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SerialServer{
    public static void main( String[] args ) throws IOException{
        ExecutorService service = Executors.newCachedThreadPool();
        try( ServerSocket serverSocket = new ServerSocket( 5673 ) ){
            while( true ){
                service.execute( new BuildingPriceCounter( serverSocket.accept() ) );
            }
        }
    }

    static class BuildingPriceCounter extends Thread{
        private Socket socket;

        BuildingPriceCounter( Socket socket ){
            this.socket = socket;
        }

        @Override
        public void run(){
            Building building;
            try( ObjectOutputStream objectOutputStream = new ObjectOutputStream( socket.getOutputStream() ) ;
                 ObjectInputStream objectInputStream = new ObjectInputStream( socket.getInputStream() ) ){
                while( true ){
                    try{
                        building = ( Building ) objectInputStream.readObject();
                        objectOutputStream.writeObject( getPrice( building ) );
                        objectOutputStream.flush();
                    }catch( BuildingUnderArrestException e ){
                        objectOutputStream.writeObject( e );
                        objectOutputStream.flush();
                    }catch( IOException e ){
                        break;
                    }catch( ClassNotFoundException e ){
                        e.printStackTrace();
                    }
                }
            }catch( IOException e ){
                e.printStackTrace();
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
}
