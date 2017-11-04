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
    private CountDownLatch starter  = new CountDownLatch( 1 );
    private CountDownLatch finisher = new CountDownLatch( 4 );
    private Random         random   = new Random( System.currentTimeMillis() );
    private Thread repairer1;
    private Thread repairer2;
    private Thread cleaner2;
    private Thread cleaner1;

    @BeforeEach
    void setUp(){
        Floor floor = Stream.generate( () -> new Flat( random.nextDouble() * 100 ) ).limit( 12 )
                            .collect( () -> new DwellingFloor( 0 ) , ( spaces , flat ) -> spaces.addSpace( 0 , flat ) ,
                                      ( spaces , spaces2 ) -> {
                                          for( Space space : spaces2 ){
                                              spaces.addSpace( 0 , space );
                                          }
                                      } );
        Semaphore repairingSemaphore = new Semaphore( false );
        Semaphore cleaningSemaphore  = new Semaphore( true );
        repairer1 = new Thread(
                new SequentialRepairer( starter , finisher , repairingSemaphore , cleaningSemaphore , floor ) );
        repairer1.setName( "Repairer1" );
        repairer2 = new Thread(
                new SequentialRepairer( starter , finisher , repairingSemaphore , cleaningSemaphore , floor ) );
        repairer2.setName( "Repairer2" );
        cleaner1 = new Thread(
                new SequentialCleaner( starter , finisher , repairingSemaphore , cleaningSemaphore , floor ) );
        cleaner1.setName( "Cleaner1" );
        cleaner2 = new Thread(
                new SequentialCleaner( starter , finisher , repairingSemaphore , cleaningSemaphore , floor ) );
        cleaner2.setName( "Cleaner2" );
    }

    @Test
    void run() throws InterruptedException{
        repairer1.start();
        repairer2.start();
        cleaner1.start();
        cleaner2.start();
        Thread.sleep( 500 );
        starter.countDown();
        finisher.await();
    }
}