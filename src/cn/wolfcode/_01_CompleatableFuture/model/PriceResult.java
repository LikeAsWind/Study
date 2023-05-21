package cn.wolfcode._01_CompleatableFuture.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: yangzhitong
 * @time: 2023/5/21 10:48
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceResult {

    private int price;

    private int discount;

    private int realPrice;

    private String platform;


    public PriceResult(String platform) {
        this.platform = platform;
    }

    @Override
    public String toString() {
        return "PriceResult{" +
                "平台='" + platform + '\'' +
                ", 平台价=" + price +
                ", 优惠价=" + discount +
                ", 最终价=" + realPrice +
                '}';
    }
}
