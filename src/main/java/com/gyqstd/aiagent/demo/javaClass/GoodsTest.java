package com.gyqstd.aiagent.demo.javaClass;

/**
 * @author GuYuqi
 * @version 1.0
 */
public class GoodsTest {
    public static void main(String[] args) {
        Goods goods = new Goods();
        goods.setName("商品 1");
        goods.setPrice(100);
        goods.setStock(99);
        goods.setCategory("电子产品");
        System.out.println(goods.show());
    }
}
