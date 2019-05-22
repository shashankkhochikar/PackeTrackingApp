package com.example.vinayak.pakingmaster.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BarcodeScanResponse implements Serializable {

    @SerializedName("Success")
    @Expose
    private Integer success;
    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("itemList")
    @Expose
    private List<ItemResponseByBarcode> itemList = null;
    private final static long serialVersionUID = 7757255754990086743L;

    @SerializedName("Error")
    @Expose
    private Error error;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
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

    public List<ItemResponseByBarcode> getItemList() {
        return itemList;
    }

    public void setItemList(List<ItemResponseByBarcode> itemList) {
        this.itemList = itemList;
    }



}
