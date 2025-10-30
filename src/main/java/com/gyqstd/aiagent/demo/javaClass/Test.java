package com.gyqstd.aiagent.demo.javaClass;

/**
 * @author GuYuqi
 * @version 1.0
 */
public class Test {
    public static void main(String[] args) {
        Circle circle = new Circle();
        circle.setRadius(5);
        System.out.println("面积：" + circle.getArea());
        System.out.println("周长：" + circle.getPerimeter());
    }
}
