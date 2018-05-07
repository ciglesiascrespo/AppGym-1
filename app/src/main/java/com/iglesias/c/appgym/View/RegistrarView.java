package com.iglesias.c.appgym.View;

import android.content.Context;

import com.iglesias.c.appgym.RestApi.Model.InfoLogin;

/**
 * Created by Ciglesias on 6/05/2018.
 */

public interface RegistrarView {
    void showLoading();
    void hideLoading();
    void showErrorLoginDialog(String msj, boolean finish);
    Context getContextApp();
    void setId(String id);
}
