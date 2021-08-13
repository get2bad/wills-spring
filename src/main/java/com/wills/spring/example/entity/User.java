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

    private Double total;

    private Double tansfer;

    private Date transferDate;

    public User(Integer id, String name, Double total, Double tansfer, Date transferDate) {
        this.id = id;
        this.name = name;
        this.total = total;
        this.tansfer = tansfer;
        this.transferDate = transferDate;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getTansfer() {
        return tansfer;
    }

    public void setTansfer(Double tansfer) {
        this.tansfer = tansfer;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", total=" + total +
                ", tansfer=" + tansfer +
                ", transferDate=" + transferDate +
                '}';
    }
}
