package com.example.vinayak.pakingmaster.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Item implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("slipno")
    @Expose
    private String slipno;

    @SerializedName("itemname")
    @Expose
    private String itemName;

    @SerializedName("itembacode")
    @Expose
    private String itemBarcode;

    @SerializedName("qty")
    @Expose
    public String itemQty;

    @SerializedName("boxno")
    @Expose
    public String itemBoxNo;

    @SerializedName("uom")
    @Expose
    private String uom;

    public Item(){

    }

    public Item(String itemName, String itemBarcode, String itemQty, String itemBoxNo, String str_slipNo, String str_itemUmo){
        this.itemName = itemName;
        this.itemBarcode = itemBarcode;
        this.itemQty = itemQty;
        this.itemBoxNo = itemBoxNo;
        this.slipno = str_slipNo;
        this.uom = str_itemUmo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getSlipno() {
        return slipno;
    }

    public void setSlipno(String slipno) {
        this.slipno = slipno;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }
}
