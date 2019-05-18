package com.example.vinayak.pakingmaster.pojo;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Error implements Serializable
{

    @SerializedName("ErrorCode")
    @Expose
    private String errorCode;
    @SerializedName("ErrorMessage")
    @Expose
    private String errorMessage;
    private final static long serialVersionUID = 3692771586536530789L;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
