package com.example.vinayak.pakingmaster.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CustomerOrderListData implements Serializable {

    @SerializedName("slipno")
    @Expose
    private String slipno;
    @SerializedName("slipdate")
    @Expose
    private String slipdate;
    @SerializedName("orderno")
    @Expose
    private String orderno;
    @SerializedName("orderdate")
    @Expose
    private String orderdate;
    @SerializedName("submitdate")
    @Expose
    private String submitdate;
    private final static long serialVersionUID = 6765925792596527187L;

    public String getSlipno() {
        return slipno;
    }

    public void setSlipno(String slipno) {
        this.slipno = slipno;
    }

    public String getSlipdate() {
        return slipdate;
    }

    public void setSlipdate(String slipdate) {
        this.slipdate = slipdate;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public String getSubmitdate() {
        return submitdate;
    }

    public void setSubmitdate(String submitdate) {
        this.submitdate = submitdate;
    }

}
