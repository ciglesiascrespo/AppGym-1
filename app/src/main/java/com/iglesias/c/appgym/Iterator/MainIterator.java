package com.iglesias.c.appgym.Iterator;

import com.iglesias.c.appgym.Activity.MainActivity;

import java.util.HashMap;

/**
 * Created by dell on 08/05/2018.
 */

public class MainIterator {
    private HashMap<String, String> mapMsj;

    public MainIterator() {
        mapMsj = new HashMap<>();

        mapMsj.put("N", "No se encontró coincidencia.");
    }

    public String getMsj(String msjR) {
        String msj = "";
        if (mapMsj.containsKey(msjR)) msj = mapMsj.get(msjR);
        return msj;
    }
}
