package cn.wolfcode._01_CompleatableFuture.common;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: yangzhitong
 * @time: 2023/5/21 10:54
 */
public class CommonUtils {


    public static void sleepSecond(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static String getCurrentTime() {
        LocalTime time = LocalTime.now();
        return time.format(DateTimeFormatter.ofPattern("[HH:mm:ss.SSS]"));
    }


    public static void printThreadLog(String message) {
        // 当前时间 | 线程ID | 线程名 | 日志信息
        String result = new StringJoiner("|")
                .add(getCurrentTime())
                .add(String.format("%2d", Thread.currentThread().getId()))
                .add(Thread.currentThread().getName())
                .add(message).toString();
        System.out.println(result);
    }
}
