package com.gyqstd.aiagent.demo.javaClass;

/**
 * @author GuYuqi
 * @version 1.0
 */
public class Circle extends Shape{

    private double radius;

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public void getArea() {
        System.out.println(Math.PI * radius * radius);
    }
}
