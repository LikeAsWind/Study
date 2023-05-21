package cn.wolfcode._01_CompleatableFuture.send;

import cn.wolfcode._01_CompleatableFuture.common.CommonUtils;
import cn.wolfcode._01_CompleatableFuture.model.PriceResult;

/**
 * @description:
 * @author: yangzhitong
 * @time: 2023/5/21 11:01
 */
public class HttpRequest {

    private static void mockCostTimeOpearation() {
        CommonUtils.sleepSecond(1);
    }

    // 获取淘宝平台的商品价
    public static PriceResult getTaoBaoPrice(String productName) {
        CommonUtils.printThreadLog("获取淘宝上：" + productName + "价格");
        mockCostTimeOpearation();
        PriceResult priceResult = new PriceResult("淘宝");
        priceResult.setPrice(5199);
        CommonUtils.printThreadLog("获取淘宝上：" + productName + "完成：5199");
        return priceResult;
    }

    // 获取淘宝平台的商品价
    public static int getTaoBaoDiscount(String productName) {
        CommonUtils.printThreadLog("获取淘宝上：" + productName + "优惠");
        mockCostTimeOpearation();
        CommonUtils.printThreadLog("获取淘宝上：" + productName + "完成：-200");
        return 200;
    }

    // 获取京东平台的商品价
    public static PriceResult getJingDongPrice(String productName) {
        CommonUtils.printThreadLog("获取京东上：" + productName + "价格");
        mockCostTimeOpearation();
        PriceResult priceResult = new PriceResult("京东");
        priceResult.setPrice(5299);
        CommonUtils.printThreadLog("获取京东上：" + productName + "完成：5299");
        return priceResult;
    }

    // 获取京东平台的商品价
    public static int getJingDongDiscount(String productName) {
        CommonUtils.printThreadLog("获取京东上：" + productName + "优惠");
        mockCostTimeOpearation();
        CommonUtils.printThreadLog("获取京东上：" + productName + "完成：-150");
        return 150;
    }

    // 获取拼多多平台的商品价
    public static PriceResult getPddPrice(String productName) {
        CommonUtils.printThreadLog("获取拼多多上：" + productName + "价格");
        mockCostTimeOpearation();
        PriceResult priceResult = new PriceResult("拼多多");
        priceResult.setPrice(5399);
        CommonUtils.printThreadLog("获取拼多多上：" + productName + "完成：5399");
        return priceResult;
    }

    // 获取拼多多平台的商品价
    public static int getPddDiscount(String productName) {
        CommonUtils.printThreadLog("获取拼多多上：" + productName + "优惠");
        mockCostTimeOpearation();
        CommonUtils.printThreadLog("获取拼多多上：" + productName + "完成：-5300");
        return 5300;
    }
}
