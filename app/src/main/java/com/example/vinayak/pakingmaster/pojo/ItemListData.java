package com.example.vinayak.pakingmaster.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ItemListData implements Serializable {

    @SerializedName("itembacode")
    @Expose
    private String itembacode;
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("boxno")
    @Expose
    private String boxno;
    @SerializedName("uom")
    @Expose
    private String uom;
    @SerializedName("itemname")
    @Expose
    private String itemname;
    private final static long serialVersionUID = -16176406284530971L;

    public String getItembacode() {
        return itembacode;
    }

    public void setItembacode(String itembacode) {
        this.itembacode = itembacode;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getBoxno() {
        return boxno;
    }

    public void setBoxno(String boxno) {
        this.boxno = boxno;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }
}
