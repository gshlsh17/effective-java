package effective.chapter10.item69;

import java.util.concurrent.*;

/**
 * 记录多线程运行时间
 *
 * @author ll
 * @create 2017-07-25
 */
public class StopWatchTime {
    /**
     * 计算执行action动作 concurrency次需要多少时间
     *  executor能够创建的线程数至少应该为concurrency个，否则会产生线程饥饿死锁
     * @param executor    执行器
     * @param concurrency 次数
     * @param action      动作
     * @return 耗时
     * @throws InterruptedException
     */
    public static long time(Executor executor, int concurrency, final Runnable action) throws InterruptedException {
        final CountDownLatch ready = new CountDownLatch(concurrency);
        final CountDownLatch start = new CountDownLatch(1);
        final CountDownLatch done = new CountDownLatch(concurrency);
        for (int i = 0; i < concurrency; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    ready.countDown();
                    try {
                        start.await();
                        action.run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        done.countDown();
                    }
                }
            });
        }
        ready.await(); //time计时线程在所有执行线程准备好之前一直处于等待状态
        long startNanos = System.nanoTime();
        start.countDown(); // 执行线程可以开始执行
        done.await();  // time计时线程在所有执行线程执行完之前一直处于等待状态
        return System.nanoTime() - startNanos;
    }

    public static long timeByCyclicBarrier(Executor executor, int concurrency, final Runnable action) throws
            BrokenBarrierException, InterruptedException {
        final CyclicBarrier door = new CyclicBarrier(concurrency+1);
        for (int i = 0; i < concurrency; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        door.await();
                        action.run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            door.await();
                        } catch (InterruptedException e) {
                            // 重新恢复中断状态
                            Thread.currentThread().interrupt();
                        } catch (BrokenBarrierException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
        door.await();
        long startNanos = System.nanoTime();
        door.reset();
        door.await();
        return System.nanoTime() - startNanos;

    }
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int result = 0;
                for (int i = 0; i < 1000; i++) {
                    result += Math.sqrt(i);
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("result:" + result);
            }
        });
        try {
            //如果将这里的concurrency改为10，会饥饿死锁，因为executor最多能创建5个线程。
            long time1 = StopWatchTime.time(executor, 5, thread);
            long time2 = StopWatchTime.timeByCyclicBarrier(executor, 5, thread);
            System.out.println(time1);
            System.out.println(time2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }
}
