package com.telusko.rewards.dto;

public class GiftCard
{
    private String name;
    private int points;
    private String couponCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    @Override
    public String toString() {
        return "GiftCard{" +
                "name='" + name + '\'' +
                ", points=" + points +
                ", couponCode='" + couponCode + '\'' +
                '}';
    }
}
