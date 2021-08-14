package com.wills.spring.example.entity;

import java.util.Date;

/**
 * @ClassName User
 * @Date 2021/8/13 13:09
 * @Author 王帅
 * @Version 1.0
 * @Description
 */
public class User {

    private Integer id;

    private String name;

    private Integer total;

    private Date date;

    public User() {
    }

    public User(Integer id, String name, Integer total, Date date) {
        this.id = id;
        this.name = name;
        this.total = total;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", total=" + total +
                ", date=" + date +
                '}';
    }
}
