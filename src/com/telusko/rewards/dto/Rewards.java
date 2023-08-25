package com.telusko.rewards.dto;

import java.util.List;

public class Rewards
{
    private int id;
    private String name;
    List<Category> categoryList;

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

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    @Override
    public String toString() {
        return "Rewards{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", categoryList=" + categoryList +
                '}';
    }
}
