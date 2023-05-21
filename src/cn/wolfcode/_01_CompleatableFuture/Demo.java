package cn.wolfcode._01_CompleatableFuture;

import cn.wolfcode._01_CompleatableFuture.Service.ComparePriceService;
import cn.wolfcode._01_CompleatableFuture.model.PriceResult;
import org.springframework.util.StopWatch;

import java.util.Arrays;
import java.util.List;

/**
 * @description:
 * @author: yangzhitong
 * @time: 2023/5/21 11:32
 */
public class Demo {


    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("串行");
        ComparePriceService comparePriceService = new ComparePriceService();
        PriceResult price = comparePriceService.getCheapestPlatformPrice("iphone");
        System.out.println(price);
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        System.out.println(stopWatch.getTotalTimeSeconds());


        stopWatch.start("future");
        price = comparePriceService.getCheapestPlatformPrice2("iphone");
        System.out.println(price);
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        System.out.println(stopWatch.getTotalTimeSeconds());


        stopWatch.start("cf");
        price = comparePriceService.getCheapestPlatformPrice3("iphone");
        System.out.println(price);
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        System.out.println(stopWatch.getTotalTimeSeconds());


        stopWatch.start("cfs");
        List<String> products = Arrays.asList("ip14黑", "ip14白", "ip14红");
        price = comparePriceService.batchCompletePrice(products);
        System.out.println(price);
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        System.out.println(stopWatch.getTotalTimeSeconds());
    }
}
