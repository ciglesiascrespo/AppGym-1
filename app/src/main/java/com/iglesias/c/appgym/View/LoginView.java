package com.iglesias.c.appgym.View;

import android.content.Context;

import com.iglesias.c.appgym.RestApi.Model.ResultLogin;

/**
 * Created by Ciglesias on 21/03/2018.
 */

public interface LoginView {
    void showLoading();
    void hideLoading();
    void goToMainActivity(ResultLogin resultLogin);
    void showErrorLoginDialog(String msj);
    Context getContext();

}
