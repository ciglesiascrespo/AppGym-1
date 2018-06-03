package com.iglesias.c.appgym.View;

import android.content.Context;

import com.iglesias.c.appgym.Pojo.DeviceInfo;

/**
 * Created by Ciglesias on 2/06/2018.
 */

public interface ConfigView {
    void setNombreDispositivoConexion();
    void setMacDispositivoConexion();
    void setDispositivoConexion(DeviceInfo deviceInfo);
    Context getContext();
    void onGuardar();
}
