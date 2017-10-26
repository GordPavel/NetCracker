package buildings.threads;

import buildings.interfaces.Floor;
import buildings.interfaces.Space;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

public class Repairer extends Thread{

    private CountDownLatch starter;
    private Floor          floor;

    public Repairer( CountDownLatch starter , Floor floor ){
        this.starter = starter;
        this.floor = floor;
    }

    @Override
    public void run(){
        try{
            starter.await();
        }catch( InterruptedException e ){
            e.printStackTrace();
        }
        Arrays.stream( floor.getSpaces() ).forEachOrdered( new Consumer<>(){
            int i = 0;

            @Override
            public void accept( Space space ){
                if( isInterrupted() ){ return; }
                System.out.printf( "Repairing space number %d with total area %f square meters\n" , ++i ,
                                   space.getArea() );
            }
        } );
    }
}
