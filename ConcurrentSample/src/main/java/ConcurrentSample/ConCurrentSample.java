package ConcurrentSample;

public class ConCurrentSample 
{
    public static void main( String[] args ) throws InterruptedException
    {
    	CountDownLatchSample.startServer();  
    	CyclicBarrierSample.StartServer();
    }
}
