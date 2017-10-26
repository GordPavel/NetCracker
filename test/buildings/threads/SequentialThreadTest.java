package buildings.threads;

import buildings.dwelling.DwellingFloor;
import buildings.dwelling.Flat;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.stream.Stream;

class SequentialThreadTest{
    private CountDownLatch start;
    private Semaphore      repairingSemaphore, cleaningSemaphore;
    private Random random;
    private Floor  floor;
    private Thread repairer1, repairer2;
    private Thread cleaner;

    @BeforeEach
    void setUp(){
        start = new CountDownLatch( 1 );
        random = new Random( System.currentTimeMillis() );
        repairingSemaphore = new Semaphore( 1 );
        cleaningSemaphore = new Semaphore( 1 );
        floor = Stream.generate( () -> new Flat( random.nextDouble() * 100 ) ).limit( 10 )
                      .collect( () -> new DwellingFloor( 0 ) , ( spaces , flat ) -> spaces.addSpace( 0 , flat ) ,
                                ( spaces , spaces2 ) -> {
                                    for( Space space : spaces2 ){ spaces.addSpace( 0 , space ); }
                                } );
        repairer1 = new Thread( new SequentialRepairer( repairingSemaphore , cleaningSemaphore , floor , start ) );
        repairer2 = new Thread( new SequentialRepairer( repairingSemaphore , cleaningSemaphore , floor , start ) );
        cleaner = new Thread( new SequentialCleaner( repairingSemaphore , cleaningSemaphore , floor , start ) );
    }

    @Test
    void run(){
        repairer1.start();
        repairer2.start();
        cleaner.start();
        start.countDown();
    }

}