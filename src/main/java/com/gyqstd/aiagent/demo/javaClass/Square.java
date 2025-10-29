package com.gyqstd.aiagent.demo.javaClass;

/**
 * @author GuYuqi
 * @version 1.0
 */
public class Square extends Shape{

    private double r;

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    @Override
    public void getArea() {
        System.out.println(r * r);
    }
}
