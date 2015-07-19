package com.volshell.demos.pc1;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author volshell
 * 
 *         BlockingQueue阻塞队列方法
 *         BlockingQueue是JDK5.0的新增内容，它是一个已经在内部实现了同步的队列，实现方式采用的是我们第2种await() /
 *         signal()方法。它可以在生成对象时指定容量大小。它用于阻塞操作的是put()和take()方法。
 *         put()方法：类似于我们上面的生产者线程，容量达到最大时，自动阻塞。
 *         take()方法：类似于我们上面的消费者线程，容量为0时，自动阻塞。
 */
public class Storage2 {
	// 仓库最大存储量
	private final int MAX_SIZE = 100;

	// 仓库存储的载体---队列
	private LinkedBlockingQueue<Object> list = new LinkedBlockingQueue<Object>(
			100);

	// 生产num个产品
	public void produce(int num) {
		// 如果仓库剩余容量为0
		if (list.size() == MAX_SIZE) {
			System.out.println("【库存量】:" + MAX_SIZE + "/t暂时不能执行生产任务!");
		}

		// 生产条件满足情况下，生产num个产品
		for (int i = 1; i <= num; ++i) {
			try {
				// 放入产品，自动阻塞
				list.put(new Object());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("【现仓储量为】:" + list.size());
		}
	}

	// 消费num个产品
	public void consume(int num) {
		// 如果仓库存储量不足
		if (list.size() == 0) {
			System.out.println("【库存量】:0/t暂时不能执行生产任务!");
		}

		// 消费条件满足情况下，消费num个产品
		for (int i = 1; i <= num; ++i) {
			try {
				// 消费产品，自动阻塞
				list.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("【现仓储量为】:" + list.size());
	}

	// set/get方法
	public LinkedBlockingQueue<Object> getList() {
		return list;
	}

	public void setList(LinkedBlockingQueue<Object> list) {
		this.list = list;
	}

	public int getMAX_SIZE() {
		return MAX_SIZE;
	}
}