package com.iglesias.c.appgym.RestApi.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ciglesias on 25/03/2018.
 */

public class ResultLogin {
    @SerializedName("errorCode")
    @Expose
    private Integer errorCode;
    @SerializedName("info")
    @Expose
    private InfoLogin info;
    @SerializedName("mensaje")
    @Expose
    private String mensaje;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public InfoLogin getInfo() {
        return info;
    }

    public void setInfo(InfoLogin info) {
        this.info = info;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
