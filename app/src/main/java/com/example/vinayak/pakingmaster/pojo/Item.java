package com.example.vinayak.pakingmaster.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Item implements Serializable {

    @SerializedName("itemName")
    @Expose
    private String itemName;

    @SerializedName("itemBarcode")
    @Expose
    private String itemBarcode;

    @SerializedName("itemQty")
    @Expose
    private String itemQty;

    @SerializedName("itemBoxNo")

    @Expose
    private String itemBoxNo;

    public Item(String itemName,String itemBarcode,String itemQty,String itemBoxNo){
        this.itemName = itemName;
        this.itemBarcode = itemBarcode;
        this.itemQty = itemQty;
        this.itemBoxNo = itemBoxNo;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemBarcode() {
        return itemBarcode;
    }

    public void setItemBarcode(String itemBarcode) {
        this.itemBarcode = itemBarcode;
    }

    public String getItemQty() {
        return itemQty;
    }

    public void setItemQty(String itemQty) {
        this.itemQty = itemQty;
    }

    public String getItemBoxNo() {
        return itemBoxNo;
    }

    public void setItemBoxNo(String itemBoxNo) {
        this.itemBoxNo = itemBoxNo;
    }

}
