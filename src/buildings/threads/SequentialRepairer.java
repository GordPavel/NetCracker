package buildings.threads;

import buildings.interfaces.Floor;
import buildings.interfaces.Space;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.function.Consumer;

public class SequentialRepairer implements Runnable{
    private Semaphore      repairingSemaphore;
    private Semaphore      cleaningSemaphore;
    private Floor          floor;
    private CountDownLatch starter;

    public SequentialRepairer( Semaphore repairingSemaphore , Semaphore cleaningSemaphore , Floor floor ,
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
            repairingSemaphore.acquire();
            Arrays.stream( floor.getSpaces() ).forEachOrdered( new Consumer<>(){
                int i = 0;

                @Override
                public void accept( Space space ){
                    System.out.printf( "Repairing space number %d with total area %f square meters\n" , ++i ,
                                       space.getArea() );
                }
            } );
        }catch( InterruptedException e ){
            e.printStackTrace();
        }finally{
            cleaningSemaphore.release();
        }
    }
}
