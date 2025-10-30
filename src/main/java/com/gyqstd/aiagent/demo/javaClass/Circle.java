package com.gyqstd.aiagent.demo.javaClass;

/**
 * @author GuYuqi
 * @version 1.0
 */
public class Circle implements Area, Perimeter{

    private double radius;

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public double getArea() {
        double result = Math.PI * radius * radius;
        return ;
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }
}
