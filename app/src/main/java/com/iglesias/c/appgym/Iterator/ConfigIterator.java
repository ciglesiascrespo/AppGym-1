package com.iglesias.c.appgym.Iterator;

import android.content.Context;
import android.content.SharedPreferences;

import com.iglesias.c.appgym.Activity.ConfiguracionActivity;
import com.iglesias.c.appgym.Pojo.DeviceInfo;
import com.iglesias.c.appgym.R;
import com.iglesias.c.appgym.Utils.ConstantsPreferences;

import static com.iglesias.c.appgym.Utils.ConstantsPreferences.NAME_PREFERENCE_CONFIG_MAC;
import static com.iglesias.c.appgym.Utils.ConstantsPreferences.NAME_PREFERENCE_CONFIG_NOMBRE;

/**
 * Created by Ciglesias on 2/06/2018.
 */

public class ConfigIterator {
    private SharedPreferences preferences;

    public ConfigIterator(Context context) {
        preferences = context.getSharedPreferences(ConstantsPreferences.NAME_PREFERENCE_CONFIG, Context.MODE_PRIVATE);

    }

    public DeviceInfo getInfoDeviceConexion() {
        String nombre = "No configurado", mac = "No configurado";

        if (preferences != null) {
            nombre = preferences.getString(NAME_PREFERENCE_CONFIG_NOMBRE, "No configurado");
            mac = preferences.getString(NAME_PREFERENCE_CONFIG_MAC, "No configurado");
        }

        return new DeviceInfo(nombre, mac);
    }

    public void saveInfoDevice(DeviceInfo deviceInfo){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(NAME_PREFERENCE_CONFIG_NOMBRE, deviceInfo.getNombre());
        editor.putString(NAME_PREFERENCE_CONFIG_MAC, deviceInfo.getMac());
        editor.apply();
    }
}
