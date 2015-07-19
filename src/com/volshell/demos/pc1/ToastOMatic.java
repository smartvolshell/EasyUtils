package com.volshell.demos.pc1;

/**
 * @author volshell
 *	这是一个制作吐司包括烘焙--抹黄油---抹果酱，最后食用的整个过程，使用消息队列的方式来实现。
 *队列被阻塞的条件
 *	执行get() 队列中为0
 *执行put()的时候，队列满。
 */
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

class Toast {
	/***
	 * 吐司实体类
	 * 
	 * @author volshell
	 * 
	 *         其中由吐司的状态，烘干，已抹黄油，已抹果酱
	 */
	public enum Status {
		DRY, BUTTERED, JAMMED
	}

	private Status status = Status.DRY;
	private final int id;

	public Toast(int idn) {
		id = idn;
	}

	public void butter() {
		status = Status.BUTTERED;
	}

	public void jam() {
		status = Status.JAMMED;
	}

	public Status getStatus() {
		return status;
	}

	public int getId() {
		return id;
	}

	public String toString() {
		return "Toast " + id + ": " + status;
	}
}

/**
 * 吐司的队列
 * 
 * @author volshell
 *
 */
class ToastQueue extends LinkedBlockingQueue<Toast> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}

/**
 * 生产吐司
 * 
 * @author volshell
 *
 */
class Toaster implements Runnable {
	private ToastQueue toastQueue;
	private int count = 0;
	private Random rand = new Random(47);

	public Toaster(ToastQueue tq) {
		toastQueue = tq;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				TimeUnit.MILLISECONDS.sleep(100 + rand.nextInt(500));
				// Make toast
				Toast t = new Toast(count++);
				System.out.println(t);
				// Insert into queue
				toastQueue.put(t);
			}
		} catch (InterruptedException e) {
			System.out.println("Toaster interrupted");
		}
		System.out.println("Toaster off");
	}
}

// Apply butter to toast:
class Butterer implements Runnable {
	private ToastQueue dryQueue, butteredQueue;

	// 抹黄油的消息队列需要两个，从其中一个中取出来，抹上黄油，然后放入另外一个队列中。
	public Butterer(ToastQueue dry, ToastQueue buttered) {
		dryQueue = dry;
		butteredQueue = buttered;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				// Blocks until next piece of toast is available:
				Toast t = dryQueue.take();
				t.butter();
				System.out.println(t);
				butteredQueue.put(t);
			}
		} catch (InterruptedException e) {
			System.out.println("Butterer interrupted");
		}
		System.out.println("Butterer off");
	}
}

// Apply jam to buttered toast:
class Jammer implements Runnable {
	private ToastQueue butteredQueue, finishedQueue;

	public Jammer(ToastQueue buttered, ToastQueue finished) {
		butteredQueue = buttered;
		finishedQueue = finished;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				// Blocks until next piece of toast is available:
				Toast t = butteredQueue.take();
				t.jam();
				System.out.println(t);
				finishedQueue.put(t);
			}
		} catch (InterruptedException e) {
			System.out.println("Jammer interrupted");
		}
		System.out.println("Jammer off");
	}
}

// Consume the toast:
class Eater implements Runnable {
	private ToastQueue finishedQueue;
	private int counter = 0;

	public Eater(ToastQueue finished) {
		finishedQueue = finished;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				// Blocks until next piece of toast is available:
				Toast t = finishedQueue.take();
				// Verify that the toast is coming in order,
				// and that all pieces are getting jammed:
				if (t.getId() != counter++
						|| t.getStatus() != Toast.Status.JAMMED) {
					System.out.println(">>>> Error: " + t);
					System.exit(1);
				} else
					System.out.println("Chomp! " + t);
			}
		} catch (InterruptedException e) {
			System.out.println("Eater interrupted");
		}
		System.out.println("Eater off");
	}
}

public class ToastOMatic {
	public static void main(String[] args) throws Exception {
		ToastQueue dryQueue = new ToastQueue(), butteredQueue = new ToastQueue(), finishedQueue = new ToastQueue();
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(new Toaster(dryQueue));
		exec.execute(new Butterer(dryQueue, butteredQueue));
		exec.execute(new Jammer(butteredQueue, finishedQueue));
		exec.execute(new Eater(finishedQueue));
		TimeUnit.SECONDS.sleep(5);
		exec.shutdownNow();
	}
} /* (Execute to see output) */// :~
