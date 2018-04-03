package com.iglesias.c.appgym.RestApi.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ciglesias on 25/03/2018.
 */

public class InfoLogin {
    @SerializedName("nroDocumento")
    @Expose
    private String nroDocumento;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("dias")
    @Expose
    private Integer dias;
    @SerializedName("urlImg")
    @Expose
    private String urlImg;

    public String getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(String nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getDias() {
        return dias;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }
}
