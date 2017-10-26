package buildings.threads;

import buildings.interfaces.Floor;

import java.util.concurrent.CountDownLatch;

public class SequentialCleaner implements Runnable{
    private Semaphore      semaphore;
    private CountDownLatch starter;
    private Floor          floor;
    private static Integer goingSpaceIndex = 0;

    public SequentialCleaner( Semaphore semaphore , CountDownLatch starter , Floor floor ){
        this.semaphore = semaphore;
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
        try{
            while( goingSpaceIndex < 10 ){
                if( Thread.currentThread().isInterrupted() ){ return; }
                semaphore.acquireClean();
                System.out.printf( "Cleaning space number %2$d with total area %1$.2f square meters\n" ,
                                   floor.getSpace( goingSpaceIndex++ ).getArea() , goingSpaceIndex );
                semaphore.realiseClean();
            }
        }catch( InterruptedException e ){
            e.printStackTrace();
        }
    }
}
