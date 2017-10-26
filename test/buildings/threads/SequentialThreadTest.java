package buildings.threads;

import buildings.dwelling.DwellingFloor;
import buildings.dwelling.Flat;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Stream;

class SequentialThreadTest{
    private CountDownLatch starter   = new CountDownLatch( 1 );
    private Random         random    = new Random( System.currentTimeMillis() );
    private Semaphore      semaphore = new Semaphore();
    private Thread repairer;
    private Thread cleaner;

    @BeforeEach
    void setUp(){
        Floor floor = Stream.generate( () -> new Flat( random.nextDouble() * 100 ) ).limit( 10 )
                            .collect( () -> new DwellingFloor( 0 ) , ( spaces , flat ) -> spaces.addSpace( 0 , flat ) ,
                                      ( spaces , spaces2 ) -> {
                                          for( Space space : spaces2 ){ spaces.addSpace( 0 , space ); }
                                      } );
        repairer = new Thread( new SequentialRepairer( semaphore , starter , floor ) );
        cleaner = new Thread( new SequentialCleaner( semaphore , starter , floor ) );
    }

    @Test
    void run() throws InterruptedException{
        repairer.start();
        cleaner.start();
        Thread.sleep( 500 );
        starter.countDown();
    }

}