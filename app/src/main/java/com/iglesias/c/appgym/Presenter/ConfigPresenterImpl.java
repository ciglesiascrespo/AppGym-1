package com.iglesias.c.appgym.Presenter;

import com.iglesias.c.appgym.Iterator.ConfigIterator;
import com.iglesias.c.appgym.Pojo.DeviceInfo;
import com.iglesias.c.appgym.View.ConfigView;

/**
 * Created by Ciglesias on 2/06/2018.
 */

public class ConfigPresenterImpl {
    private ConfigView view;
    private ConfigIterator iterator;

    public ConfigPresenterImpl(ConfigView view) {
        this.view = view;
        iterator = new ConfigIterator(view.getContext());
    }

    public void getInfoDevice() {
        DeviceInfo deviceInfo = iterator.getInfoDeviceConexion();
        view.setDispositivoConexion(deviceInfo);
        view.setMacDispositivoConexion();
        view.setNombreDispositivoConexion();
    }

    public void guardarInfoDevice(DeviceInfo deviceInfo) {
        iterator.saveInfoDevice(deviceInfo);
        view.onGuardar();
    }
}
