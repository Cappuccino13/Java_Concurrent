package ConcurrentSample;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 实例：CyclicBarrier的使用举例
 * CyclicBarrier类似于CountDownLatch也是个计数器， 
 * 不同的是CyclicBarrier数的是调用了CyclicBarrier.await()进入等待的线程数， 
 * 当线程数达到了CyclicBarrier初始时规定的数目时，所有进入等待状态的线程被唤醒并继续。 
 * CyclicBarrier就象它名字的意思一样，可看成是个障碍， 
 * 所有的线程必须到齐后才能一起通过这个障碍。 
 * CyclicBarrier初始时还可带一个Runnable的参数， 
 * 此Runnable任务在CyclicBarrier的数目达到后，所有其它线程被唤醒前被执行。 
 * 
 * @author vivian.gw
 */
public class CyclicBarrierSample {
	
	public static void StartServer(){
		final int[] array = new int[4];
		CyclicBarrier barrier = new CyclicBarrier(2, new Runnable(){
			//在所有线程都到达Barrier时执行
			public void run(){
				System.out.println("Over Barrier...");
			}
		});
		
		//启动线程
		new Thread(new ComponentThread(barrier, array, 0)).start();
		new Thread(new ComponentThread(barrier, array, 1)).start();
		
		try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		System.out.println("CyclicBarrier 重用");
		new Thread(new ComponentThread(barrier, array, 2)).start();
		new Thread(new ComponentThread(barrier, array, 3)).start();
	}
	
	private static class ComponentThread implements Runnable{
		 	CyclicBarrier barrier;//计数器
		 	int ID;//组件标识
		 	int array[];//数据数组
		 	
		 	//构造方法
		 	public ComponentThread(CyclicBarrier barrier, int array[], int ID){
		 		this.barrier = barrier;
		 		this.array = array;
		 		this.ID = ID;
		 	}
		 	
		 	public void run(){
		 		try{
		 			array[ID] = new Random().nextInt(100);
		 			System.out.println("Component" + ID + " generates: " + array[ID]);
		 			//在这里等待Barrier
		 			System.out.println("Component" + ID + " sleep...");
		 			barrier.await();
		 			System.out.println("Component" + ID + " awaked...");
		 			System.out.println("Component" + ID + " result: " + array[ID]);			
		 		}catch(InterruptedException e){
		 			e.printStackTrace();
		 		}catch(BrokenBarrierException e){
		 			e.printStackTrace();
		 		}
		 	}
	}

}
