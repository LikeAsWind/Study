package cn.wolfcode._01_CompleatableFuture.Service;

import cn.wolfcode._01_CompleatableFuture.common.CommonUtils;
import cn.wolfcode._01_CompleatableFuture.model.PriceResult;
import cn.wolfcode._01_CompleatableFuture.send.HttpRequest;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @description:
 * @author: yangzhitong
 * @time: 2023/5/21 11:19
 */
public class ComparePriceService {

    // 串行
    public PriceResult getCheapestPlatformPrice(String productName) {
        PriceResult priceResult;
        int discount;
        // 获取淘宝
        priceResult = HttpRequest.getTaoBaoPrice(productName);
        discount = HttpRequest.getTaoBaoDiscount(productName);
        PriceResult tbPrice = this.computeRealPrice(priceResult, discount);

        // 京东
        priceResult = HttpRequest.getJingDongPrice(productName);
        discount = HttpRequest.getJingDongDiscount(productName);
        PriceResult jdPrice = this.computeRealPrice(priceResult, discount);

        // 拼多多
        priceResult = HttpRequest.getPddPrice(productName);
        discount = HttpRequest.getPddDiscount(productName);
        PriceResult pddPrice = this.computeRealPrice(priceResult, discount);

        // 计算最优
        /*Stream<PriceResult> stream = Stream.of(tbPrice, jdPrice, pddPrice);
        Optional<PriceResult> minOpt = stream.min(Comparator.comparing(priceRes -> {
            return priceRes.getRealPrice();
        }));
        PriceResult result = minOpt.get();*/

        // 获取最小值
        PriceResult result = Stream.of(tbPrice, jdPrice, pddPrice)
                .min(Comparator.comparing(PriceResult::getRealPrice))
                .get();

        return result;
    }

    // 并行
    public PriceResult getCheapestPlatformPrice2(String productName) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        // 获取淘宝
        Future<PriceResult> tabaoFuture = executorService.submit(() -> {
            PriceResult taoBaoPrice = HttpRequest.getTaoBaoPrice(productName);
            int discount = HttpRequest.getTaoBaoDiscount(productName);
            PriceResult result = this.computeRealPrice(taoBaoPrice, discount);
            return result;
        });

        // 京东Future + 线程池增强
        Future<PriceResult> jingDongFuture = executorService.submit(() -> {
            PriceResult taoBaoPrice = HttpRequest.getJingDongPrice(productName);
            int discount = HttpRequest.getJingDongDiscount(productName);
            PriceResult result = this.computeRealPrice(taoBaoPrice, discount);
            return result;
        });

        // 拼多多
        Future<PriceResult> pddFuture = executorService.submit(() -> {
            PriceResult taoBaoPrice = HttpRequest.getPddPrice(productName);
            int discount = HttpRequest.getPddDiscount(productName);
            PriceResult result = this.computeRealPrice(taoBaoPrice, discount);
            return result;
        });
        // 获取最小值
        PriceResult result = Stream.of(tabaoFuture, jingDongFuture, pddFuture).map(
                        future -> {
                            try {
                                return future.get(5, TimeUnit.SECONDS);
                            } catch (Exception e) {
                                e.printStackTrace();
                                return null;
                            } finally {
                                executorService.shutdown();
                            }
                        }
                )
                .filter(Objects::nonNull)
                .min(Comparator.comparing(PriceResult::getRealPrice)).get();
        return result;
    }


    // 并行
    public PriceResult getCheapestPlatformPrice3(String productName) {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        // 获取淘宝
        CompletableFuture<PriceResult> tbCf = CompletableFuture.supplyAsync(() -> HttpRequest.getTaoBaoPrice(productName), executorService)
                .thenCombine(CompletableFuture.supplyAsync(() -> HttpRequest.getTaoBaoDiscount(productName), executorService),
                        this::computeRealPrice);

        // 京东
        CompletableFuture<PriceResult> jdCf = CompletableFuture.supplyAsync(() -> HttpRequest.getJingDongPrice(productName), executorService)
                .thenCombine(CompletableFuture.supplyAsync(() -> HttpRequest.getJingDongDiscount(productName), executorService),
                        this::computeRealPrice);

        // 拼多多
        CompletableFuture<PriceResult> pddCf = CompletableFuture.supplyAsync(() -> HttpRequest.getPddPrice(productName), executorService)
                .thenCombine(CompletableFuture.supplyAsync(() -> HttpRequest.getPddDiscount(productName), executorService),
                        this::computeRealPrice);
        // 获取最小值
        PriceResult priceResult = Stream.of(tbCf, jdCf, pddCf).map(CompletableFuture::join).min(Comparator.comparing(PriceResult::getRealPrice)).get();
        return priceResult;
    }


    // 计算商品的最终价格
    public PriceResult computeRealPrice(PriceResult priceResult, int disCount) {
        priceResult.setRealPrice(priceResult.getPrice() - disCount);
        priceResult.setDiscount(disCount);
        CommonUtils.printThreadLog(priceResult.getPlatform() + "最终价格" + priceResult.getRealPrice());
        return priceResult;
    }


    public PriceResult batchCompletePrice(List<String> products) {

        // 遍历，根据商品名称开启异步任务获取最终价，最终结果归集
        List<CompletableFuture<PriceResult>> completableFutures = products.stream().map(product -> {
            return CompletableFuture.supplyAsync(() -> HttpRequest.getTaoBaoPrice(product))
                    .thenCombine(CompletableFuture.supplyAsync(() -> HttpRequest.getTaoBaoDiscount(product)),
                            this::computeRealPrice);
        }).collect(Collectors.toList());
        // 排序
        PriceResult priceResult = completableFutures.stream().map(CompletableFuture::join)
                .sorted(Comparator.comparing(PriceResult::getRealPrice))
                .findFirst().get();
        return priceResult;
    }
}
