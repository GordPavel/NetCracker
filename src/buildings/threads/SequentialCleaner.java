package buildings.threads;

import buildings.interfaces.Floor;
import buildings.interfaces.Space;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.function.Consumer;

public class SequentialCleaner implements Runnable{
    private Semaphore      repairingSemaphore;
    private Semaphore      cleaningSemaphore;
    private Floor          floor;
    private CountDownLatch starter;

    public SequentialCleaner( Semaphore repairingSemaphore , Semaphore cleaningSemaphore , Floor floor ,
                              CountDownLatch starter ){
        this.repairingSemaphore = repairingSemaphore;
        this.cleaningSemaphore = cleaningSemaphore;
        this.floor = floor;
        this.starter = starter;
    }

    @Override
    public void run(){
        try{
            starter.await();
            cleaningSemaphore.release();
            Arrays.stream( floor.getSpaces() ).forEachOrdered( new Consumer<>(){
                int i = 0;

                @Override
                public void accept( Space space ){
                    System.out.printf( "Cleaning space number %d with total area %f square meters\n" , ++i ,
                                       space.getArea() );
                }
            } );
        }catch( InterruptedException e ){
            e.printStackTrace();
        }finally{
            repairingSemaphore.release();
        }
    }
}
