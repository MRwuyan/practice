package practice.threadLocal;

import java.util.concurrent.*;

public class RunAsyncDemo {

    public static void main(String[] args) {
        //对计算结果进行消费
        m6();
    }

    private static void m6() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 20, 1L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(50), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        //参数fn是一个函数，它将第一个CompletableFuture的结果作为输入，并返回一个CompletionStage（可能是CompletableFuture或其他实现）作为结果。
        //
        //使用thenCompose方法，你可以在第一个CompletableFuture完成后，将其结果传递给fn函数，然后获取返回的CompletionStage，并继续执行后续操作。
        System.out.println(CompletableFuture.supplyAsync(() -> 5).thenCompose(r -> CompletableFuture.supplyAsync(() -> "result" + r)).join());
        threadPoolExecutor.shutdown();
    }

    private static void m2() {
        //whenComplete:是执行当前任务的线程执行继续执行whenComplete的任务
        //whenCompleteAsync:是执行把whenCompleteAsync 这个任务继续提交给线程池来执行
        //thenApply: 计算结果存在依赖关系,当前走错不走下一步
        //handle: 即使报错也会继续往下执行
        //thenRun: 无输入无返回
        //thenAccept: 有输入无返回
        //thenApply: 有输入,有返回
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 20, 1L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(50), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        CompletableFuture.supplyAsync(() -> 1, threadPoolExecutor).handle((f, e) -> {
            System.out.println("hand:" + f);
            int a = 10 / 0;
            return f + 1;
        }).thenApply(e -> {
            System.out.println("thenApply");

            return 1;
        }).thenApply(e -> {
            System.out.println("thenApply2");
//            int a = 10 / 0;
            return 1;
        }).whenComplete((v, e) -> {
            System.out.println("whenComplete");
            if (e == null) {
                System.out.println("complete");
            }
        }).whenCompleteAsync((v, e) -> {
            System.out.println("whenCompleteAsync");
        }).exceptionally(e -> {
            e.printStackTrace();
            return null;
        }).join();
        threadPoolExecutor.shutdown();
    }

    /**
     * 获得结果和触发计算
     */
    private static void m1() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 20, 1L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(50), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 1;
        }, threadPoolExecutor);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //若异步已经执行完成则返回值1,没有完成则返回默认值-44
        System.out.println(completableFuture.complete(-44) + "\t" + completableFuture.join());
        threadPoolExecutor.shutdown();
    }

    /**
     * 两个 completionStage 任务都完成后,最终能把两个任务的结果一起交给thenCombine来处理,先完成的等着,等待其他分支任务
     */
    private static void m5() {
        System.out.println(CompletableFuture.supplyAsync(() -> 10).thenCombine(CompletableFuture.supplyAsync(() -> 20), (r1, r2) -> r1 + r2).join());
    }

    /**
     * 对计算速度进行选用
     */
    private static void m4() {
        //applyToEither 比多个执行方法,那个快就返回哪个方法
        CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 1;
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 2;
        }), r -> {
            System.out.println(r);
            return r;
        }).join();
    }

}
