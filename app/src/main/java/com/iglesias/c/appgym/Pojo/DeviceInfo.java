package com.iglesias.c.appgym.Pojo;

/**
 * Created by Ciglesias on 2/06/2018.
 */

public class DeviceInfo {
    private String nombre, mac;

    public DeviceInfo(String nombre, String mac) {
        this.nombre = nombre;
        this.mac = mac;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
