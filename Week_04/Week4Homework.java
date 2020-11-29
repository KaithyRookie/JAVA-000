import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author kaithy.xu
 * @date 2020-11-29 15:16
 */
public class Week4Homework {

    public static int sum(int x) {
        if (x < 0) {
            return 0;
        }
        if(x < 2) {
            return 1;
        }
        int[] record = new int[x];
        record[0] = 1;
        record[1] = 1;

        for (int index = 2; index < x; index++) {
            record[index] = record[index-1] + record[index-2];
        }

        return record[x-1];
    }

    /**
     * 利用 FutureTask 阻塞获取线程的执行结果
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void getResultAsync_1() throws ExecutionException, InterruptedException {
        FutureTask<Integer> task = new FutureTask(() -> sum(36));

        new Thread(task).start();

        Integer result = task.get();

        System.out.println("异步计算结果："+result);
    }

    /**
     * 利用 CountDownLatch 来阻塞主线程，final 数组 保存线程执行的结果
     * @throws InterruptedException
     */
    public static void getResultAsync_2() throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(1);
        final int[] record = {0};
        Thread t = new Thread(() -> {
            record[0] = sum(36);
            latch.countDown();
        });
        t.start();
        latch.await();

        System.out.println("异步计算结果："+record[0]);
    }

    /**
     * 利用 ExecutorService 的 submit 方法来提交任务
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void getResultAsync_3() throws ExecutionException, InterruptedException {
        ExecutorService service = new ThreadPoolExecutor(1,1,15,TimeUnit.SECONDS,new ArrayBlockingQueue<>(5));

        Future result = service.submit(() -> sum(36));

        System.out.println("异步计算结果："+result.get());

        service.shutdown();
    }

    /**
     * 利用 completionService 来 提交任务
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void getResultAsync_4() throws ExecutionException, InterruptedException {
        ExecutorService service = new ThreadPoolExecutor(1,1,15,TimeUnit.SECONDS,new ArrayBlockingQueue<>(5));
        CompletionService completionService  = new ExecutorCompletionService(service);
        Future result = completionService.submit(() -> sum(36));

        System.out.println("异步计算结果："+result.get());

        service.shutdown();
    }

    /**
     * 利用sleep 以延后主线程获取信号量的操作， 后续利用 循环获取信号量 来阻塞主线程，
     */
    public static void getResultAsync_5() {
        Semaphore count = new Semaphore(1);
        final int[] record = {0};
        Thread t = new Thread(() -> {
            try {
                count.acquire();
                int r = sum(36);
                record[0] = r;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                count.release();
            }

        });
        t.start();
        do {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }while (!count.tryAcquire());

        System.out.println("异步计算结果："+record[0]);
    }

    /**
     * 利用 CyclicBarrier 来阻塞主线程
     * @throws BrokenBarrierException
     * @throws InterruptedException
     */
    public static void getResultAsync_6() throws BrokenBarrierException, InterruptedException {
        CyclicBarrier barrier = new CyclicBarrier(2);
        final int[] record = {0};
        Thread t = new Thread(() -> {
            try {

                int r = sum(36);
                record[0] = r;
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

        });
        t.start();

        barrier.await();

        System.out.println("异步计算结果："+record[0]);
    }




    public static void main(String[] args) throws Exception {

        getResultAsync_6();
    }
}
