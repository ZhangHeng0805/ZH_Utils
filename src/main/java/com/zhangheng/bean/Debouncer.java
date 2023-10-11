package com.zhangheng.bean;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author: ZhangHeng
 * @email: zhangheng_0805@163.com
 * @date: 2023-10-09 11:39
 * @version: 1.0
 * @description: 防抖（Debouncing）通常用于处理事件触发的频率限制，以避免在短时间内多次触发相同的事件
 */
public class Debouncer {
    private Timer timer;
    private final long delayMillis;

    public Debouncer(long delayMillis) {
        this.timer = new Timer();
        this.delayMillis = delayMillis;
    }

    public void debounce(Runnable task) {
        debounce(task, delayMillis);
    }

    public void debounce(Runnable task, long customDelayMillis) {
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                task.run();
                timer.cancel(); // 任务执行完后取消Timer
            }
        }, customDelayMillis);
    }

    public void cancel() {
        if (timer != null) {
            timer.cancel();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Debouncer debouncer = new Debouncer(2000); // 默认延迟为1秒

        // 模拟事件触发
        // 模拟事件触发
        for (int i = 0; i < 5; i++) {
            final int n=i;
            debouncer.debounce(() -> {
                System.out.println("Event triggered!"+n);
            });
            if (i==3)
                Thread.sleep(1500);
        }

//        debouncer.debounce(() -> {
//            System.out.println("Event 2 triggered!");
//        }, 500); // 自定义延迟为0.5秒

        // 取消所有待定的任务
//        debouncer.cancel();
    }
}
