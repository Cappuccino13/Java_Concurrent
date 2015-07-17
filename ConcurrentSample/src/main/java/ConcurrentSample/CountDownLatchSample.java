package ConcurrentSample;

import java.util.concurrent.CountDownLatch;

/**
 * 实例：CountDownLatch的使用举例
 * 假设我们需要打印1~100，最后再输出“OK”。
 * 1~100的打印顺序不做要求，只需保证“OK”在最后出现。
 * 
 * @author vivian.gw
 */
public class CountDownLatchSample {
	private static final int N = 10;
	
	public static void startServer(){
		CountDownLatch doneSignal = new CountDownLatch(N);
    	CountDownLatch startSignal = new CountDownLatch(1);//开始执行信号
    	
    	for(int i = 1; i <= N; i++){
    		new Thread(new Worker(i, doneSignal, startSignal)).start();//线程启动了
    	}
    	System.out.println("begin---------------");
    	startSignal.countDown();//开始执行了
    	try{
        	doneSignal.await();//等待所有的线程执行完毕
    	}catch(InterruptedException e){
    		e.printStackTrace();
    	}
    	System.out.println("OK");		
	}
	
	private static class Worker implements Runnable{
    	private final CountDownLatch doneSignal;
    	private final CountDownLatch startSignal;
    	private int beginIndex;
    	
    	Worker(int beginIndex, CountDownLatch doneSignal,
    			CountDownLatch startSignal){
    		this.startSignal = startSignal;
    		this.doneSignal = doneSignal;
    		this.beginIndex = beginIndex;
    	}
    	
    	public void run(){
    		try{
    			startSignal.await();//等待开始执行信号的发布
    			beginIndex = (beginIndex - 1) * 10 + 1;
    			for(int i = beginIndex; i < beginIndex + 10; i++){
    				System.out.println(i);
    			}
    		}catch(InterruptedException e){
    			e.printStackTrace();
    		}finally{
    			doneSignal.countDown();
    		}
    	}
    }
}
