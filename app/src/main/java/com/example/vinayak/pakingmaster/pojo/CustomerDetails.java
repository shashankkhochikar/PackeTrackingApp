package com.example.vinayak.pakingmaster.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CustomerDetails implements Serializable {

    @SerializedName("slipno")
    @Expose
    private String slipNumber;

    @SerializedName("slipdate")
    @Expose
    private String slipdate;

    @SerializedName("custid")
    @Expose
    private String custid;

    @SerializedName("submitdate")
    @Expose
    private String submitdate;

    @SerializedName("downloaddate")
    @Expose
    private String downloaddate;

    @SerializedName("noofboxes")
    @Expose
    private String noofboxes;

    @SerializedName("enteryby")
    @Expose
    private String enteryby;

    @SerializedName("orderdate")
    @Expose
    private String orderDate;

    @SerializedName("orderno")
    @Expose
    private String orderNumber;

    @SerializedName("itemList")
    @Expose
    private List<Item> itemList = null;

    public CustomerDetails() {

    }

    public CustomerDetails(String slipNo, String slipDate,String orderNo,String orderDate,String custid,String submitedDate,String downloaddate,String noofboxes,
    String enteryBy,List<Item> itemList) {

        this.slipNumber = slipNo;
        this.slipdate = slipDate;
        this.orderNumber = orderNo;
        this.orderDate = orderDate;
        this.custid = custid;
        this.submitdate = submitedDate;
        this.downloaddate = downloaddate;
        this.noofboxes = noofboxes;
        this.enteryby = enteryBy;
        this.itemList = itemList;

    }

    public String getSlipNumber() {
        return slipNumber;
    }

    public void setSlipNumber(String slipNumber) {
        this.slipNumber = slipNumber;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public String getSlipdate() {
        return slipdate;
    }

    public void setSlipdate(String slipdate) {
        this.slipdate = slipdate;
    }

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }

    public String getSubmitdate() {
        return submitdate;
    }

    public void setSubmitdate(String submitdate) {
        this.submitdate = submitdate;
    }

    public String getDownloaddate() {
        return downloaddate;
    }

    public void setDownloaddate(String downloaddate) {
        this.downloaddate = downloaddate;
    }

    public String getNoofboxes() {
        return noofboxes;
    }

    public void setNoofboxes(String noofboxes) {
        this.noofboxes = noofboxes;
    }

    public String getEnteryby() {
        return enteryby;
    }

    public void setEnteryby(String enteryby) {
        this.enteryby = enteryby;
    }
}
