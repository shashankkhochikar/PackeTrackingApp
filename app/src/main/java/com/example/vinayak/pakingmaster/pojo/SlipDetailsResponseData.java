package com.example.vinayak.pakingmaster.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SlipDetailsResponseData implements Serializable {

    @SerializedName("Success")
    @Expose
    private Integer success;
    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("Message")
    @Expose
    private String message;
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
    @SerializedName("custid")
    @Expose
    private String custid;
    @SerializedName("downloaddate")
    @Expose
    private String downloaddate;
    @SerializedName("noofboxes")
    @Expose
    private String noofboxes;
    @SerializedName("enteryby")
    @Expose
    private String enteryby;
    @SerializedName("itemList")
    @Expose
    private List<Item> itemList = null;
    @SerializedName("Error")
    @Expose
    private Error error;
    private final static long serialVersionUID = 7947346447007442927L;


    public SlipDetailsResponseData(){

    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
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

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
