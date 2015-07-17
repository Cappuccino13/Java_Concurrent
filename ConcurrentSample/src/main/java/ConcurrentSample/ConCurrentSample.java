package ConcurrentSample;

public class ConCurrentSample {
    public static void main( String[] args ){
    	//CountDownLatch Test
    	CountDownLatchSample.startServer();
    	//CyclicBarrier Test
    	CyclicBarrierSample.StartServer();
    }
}
