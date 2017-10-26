package buildings.threads;

public class Semaphore{

    private Boolean repaired = false;

    public synchronized void acquireClean() throws InterruptedException{
        while( !repaired ){ wait(); }
    }

    public synchronized void realiseClean(){
        repaired = false;
        notifyAll();
    }

    public synchronized void acquireRepair() throws InterruptedException{
        while( repaired ){ wait(); }
    }

    public synchronized void realiseRepair(){
        repaired = true;
        notifyAll();
    }

}
