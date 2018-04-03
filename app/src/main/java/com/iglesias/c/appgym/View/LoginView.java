package com.iglesias.c.appgym.View;

import android.content.Context;

import com.iglesias.c.appgym.RestApi.Model.InfoLogin;

/**
 * Created by Ciglesias on 21/03/2018.
 */

public interface LoginView {
    void showLoading();
    void hideLoading();
    void goToMainActivity(InfoLogin infoLogin);
    void showErrorLoginDialog(String msj);
    Context getContext();

}
