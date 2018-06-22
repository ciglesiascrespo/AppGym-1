package com.iglesias.c.appgym.View;

/**
 * Created by dell on 08/05/2018.
 */

public interface MainView {
    void showErrorLoginDialog(String msj);
    void compareId(String id);
    void sendId();
    void activarSensor();
    void goToLogin();
}
