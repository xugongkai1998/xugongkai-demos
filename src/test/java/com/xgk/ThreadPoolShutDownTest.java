package com.xgk;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author: xugongkai
 * @created: 2022-02-22 14:12
 */
public class ThreadPoolShutDownTest {

    private static final ExecutorService executor = Executors.newFixedThreadPool(1);

    public static void main(String[] args) throws InterruptedException {
        //testShutdown();
        testShutdownNow();
    }

    /*
        shutdown()之后，会拒绝新任务加入队列,如果在shutdown()后加入,直接会触发RejectHandler处理
        且池会在完全处理完队列现存任务之后，才会关闭。
     */
    private static void testShutdown() throws InterruptedException {
        executor.submit(() -> {
            while (true) { }
        });
        executor.shutdown();
        if (!executor.awaitTermination(3, TimeUnit.SECONDS)) {
            System.out.println("Still waiting after 3 seconds");
            System.exit(0);
        }
        System.out.println("Done");
    }

    /*
        res = shutdownNow();
        之后，会拒绝新任务加入队列, 如果在shutdown()后加入,直接会触发RejectHandler处理，该行为与shutdown()保持一致
        但是针对现存任务，如果从未运行过，这些任务会直接取消掉，并通过返回值给到res中。
        case1: 如果任务正在运行中，可通过Thread.currentThread().isInterrupted()确定外部是否发送了中断信号，进而采取取消任务的措施。
        case2: 如果任务正在运行，且正处于阻塞状态，那么会直接触发InterruptedException异常并被相关catch块捕捉到（InterruptedException必检异常，肯定有catch块捕捉）进而采取退出措施。

        中断只是一种协调机制，不是强制机制，外部只能传递中断的信号，但是线程具体怎么动作，还是由自己决定，甚至可以直接无视中断信息。
        在本例中，可以直接取消break;操作，即便发现了中断，还是继续运行。

        如果外部传递中断的时候，引发了InterruptedException（case1），那么后续`Thread.currentThread().isInterrupted()`获取到的结果是false,原因在于
        在触发Interrupted Exception异常的同时，JVM会同时把线程的中断标志位清除，所以，这个时候在run()方法中判断的current Thread.isInterrupted()会返回false。
        因此在本例中，如果不打算在catch块执行退出操作，那么就需要再次手动设置标识，让后续步骤能有途径继续感知到有发生中断。
    */
    private static void testShutdownNow() throws InterruptedException {
        executor.submit(() -> {
            while (true) {

                //可注释当前try-catch块, 此时外部发送中断时, Thread.currentThread().isInterrupted()可正常获取标识位.
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("catch interrupted exception");
                    // 也可单独设置到自定义的某个变量中,只要有地方能继续拿到该状态就行
                    Thread.currentThread().interrupt();
                }

                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("break loop");
                    break;
                }
            }
        });
        executor.shutdownNow();
        if (!executor.awaitTermination(3, TimeUnit.SECONDS)) {
            System.out.println("Still waiting after 3 seconds");
            System.exit(0);
        }
        System.out.println("Done");
    }


}
