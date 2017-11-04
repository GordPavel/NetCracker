package buildings.threads;

import buildings.interfaces.Floor;
import buildings.interfaces.Space;
import exceptions.FloorIndexOutOfBoundsException;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class SequentialCleaner implements Runnable{
    private static volatile AtomicInteger goingSpaceIndex = new AtomicInteger( 0 );
    private CountDownLatch starter;
    private CountDownLatch finisher;
    private Semaphore      repairingSemaphore;
    private Semaphore      cleaningSemaphore;
    private Floor          floor;

    public SequentialCleaner( CountDownLatch starter , CountDownLatch finisher , Semaphore repairingSemaphore ,
                              Semaphore cleaningSemaphore , Floor floor ){
        this.starter = starter;
        this.finisher = finisher;
        this.repairingSemaphore = repairingSemaphore;
        this.cleaningSemaphore = cleaningSemaphore;
        this.floor = floor;
    }

    @Override
    public void run(){
        Space space;
        try{
            starter.await();
        }catch( InterruptedException e ){
            e.printStackTrace();
        }
        try{
            while( goingSpaceIndex.intValue() < floor.getSpacesCount() ){
                if( Thread.currentThread().isInterrupted() ){
                    return;
                }
                cleaningSemaphore.acquire();
                try{
                    space = floor.getSpace( goingSpaceIndex.getAndIncrement() );
                }catch( FloorIndexOutOfBoundsException e ){
                    repairingSemaphore.release();
                    break;
                }
                System.out.printf( "%3$s cleaning space number %2$d with total area %1$.2f square meters\n" ,
                                   space.getArea() , goingSpaceIndex.intValue() , Thread.currentThread().getName() );
                Thread.sleep( 300 );
                repairingSemaphore.release();
            }
        }catch( InterruptedException e ){
            e.printStackTrace();
        }
        finisher.countDown();
    }
}
