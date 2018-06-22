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
    @SerializedName("urlImage")
    @Expose
    private String urlImage;
    @SerializedName("dias")
    @Expose
    private Integer dias;

    private String idHuella;

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

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public Integer getDias() {
        return dias;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }

    public void setIdHuella(String idHuella) {
        this.idHuella = idHuella;
    }

    public String getIdHuella() {
        return idHuella;
    }

}
