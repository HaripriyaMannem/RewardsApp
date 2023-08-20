package com.telusko.rewards.dto;

import java.util.Arrays;
import java.util.Base64;

public class User
{
    private int id;
    private String name;
    private String password;
    private int transAmount;
    private int points;
    private int redeemPoints;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(int transAmount) {
        this.transAmount = transAmount;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getRedeemPoints() {
        return redeemPoints;
    }

    public void setRedeemPoints(int redeemPoints) {
        this.redeemPoints = redeemPoints;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", transAmount=" + transAmount +
                ", points=" + points +
                ", redeemPoints=" + redeemPoints +
                '}';
    }
}
