package com.iglesias.c.appgym.Presenter;

import com.iglesias.c.appgym.RestApi.Model.ResultLogin;

/**
 * Created by Ciglesias on 21/03/2018.
 */

public interface LoginPresenter {
    void onSuccesLogin(ResultLogin resultLogin);

    void onErrorLogin();

    void onUserNotValid(String msj);
}
