package buildings.threads;

import java.util.Deque;
import java.util.concurrent.LinkedBlockingDeque;

public class Semaphore{
    private Deque<Thread> waitingThreads = new LinkedBlockingDeque<>();
    private Boolean       isObjectBusy   = false;
    private Thread nextThread;

    public Semaphore( Boolean isObjectBusy ){
        this.isObjectBusy = isObjectBusy;
    }

    public synchronized void acquire() throws InterruptedException{
        if( isObjectBusy || !Thread.currentThread().equals( nextThread ) ){
            waitingThreads.add( Thread.currentThread() );
            while( isObjectBusy ) wait();
        }
        isObjectBusy = true;
    }

    public synchronized void release(){
        nextThread = waitingThreads.pollFirst();
        isObjectBusy = false;
        notifyAll();
    }

}
