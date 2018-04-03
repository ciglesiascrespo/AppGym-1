package com.iglesias.c.appgym.Presenter;

import com.iglesias.c.appgym.RestApi.Model.InfoLogin;

/**
 * Created by Ciglesias on 21/03/2018.
 */

public interface LoginPresenter {
    void onSuccesLogin(InfoLogin infoLogin);

    void onErrorLogin();

    void onUserNotValid();
}
