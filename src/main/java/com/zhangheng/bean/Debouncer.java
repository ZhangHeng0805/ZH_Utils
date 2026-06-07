package com.zhangheng.bean;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author: ZhangHeng
 * @email: zhangheng_0805@163.com
 * @date: 2023-10-09 11:39
 * @version: 1.0
 * @description: 防抖（Debouncing）通常用于处理事件触发的频率限制，以避免在短时间内多次触发相同的事件
 */
public class Debouncer {
    // 原子引用保证线程安全，避免多线程下的竞态问题
    private final AtomicReference<TimerTask> currentTask = new AtomicReference<>();
    private final Timer timer = new Timer(true); // 守护线程，不阻塞JVM退出
    private final long defaultDelayMillis;

    /**
     * 构造方法
     *
     * @param defaultDelayMillis 默认延迟时间（毫秒）
     */
    public Debouncer(long defaultDelayMillis) {
        this.defaultDelayMillis = defaultDelayMillis;
    }

    /**
     * 使用默认延迟执行防抖
     *
     * @param task 要执行的任务
     */
    public void debounce(Runnable task) {
        debounce(task, defaultDelayMillis);
    }

    /**
     * 自定义延迟执行防抖
     *
     * @param task              任务
     * @param customDelayMillis 自定义延迟
     */
    public void debounce(Runnable task, long customDelayMillis) {
        // 原子方式取消上一个任务，线程安全
        TimerTask oldTask = currentTask.getAndSet(null);
        if (oldTask != null) {
            oldTask.cancel();
        }

        // 创建新任务
        TimerTask newTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    task.run();
                } finally {
                    currentTask.compareAndSet(this, null); // 执行完清空引用
                }
            }
        };

        // 保存当前任务
        currentTask.set(newTask);
        timer.schedule(newTask, customDelayMillis);
    }

    /**
     * 主动取消所有待执行任务
     */
    public void cancel() {
        TimerTask task = currentTask.getAndSet(null);
        if (task != null) {
            task.cancel();
        }
    }

    /**
     * 销毁定时器（完全释放资源）
     */
    public void destroy() {
        timer.cancel();
    }

    public static void main(String[] args) throws InterruptedException {
        Debouncer debouncer = new Debouncer(1000); // 默认延迟为1秒

        // 模拟事件触发
        // 模拟事件触发
        for (int i = 0; i < 5; i++) {
            final int n = i;
            debouncer.debounce(() -> {
                System.out.println("Event triggered! " + n);
            });
            Thread.sleep(500 * i);
        }

//        debouncer.debounce(() -> {
//            System.out.println("Event 2 triggered!");
//        }, 500); // 自定义延迟为0.5秒

        // 取消所有待定的任务
        debouncer.destroy();
    }
}
