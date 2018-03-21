package com.iglesias.c.appgym.Iterator;

import com.iglesias.c.appgym.Presenter.LoginPresenter;

/**
 * Created by Ciglesias on 21/03/2018.
 */

public class LoginIterator {
    LoginPresenter presenter;

    public LoginIterator(LoginPresenter presenter) {
        this.presenter = presenter;
    }

    public void validateUser(String nro) {
        if (nro.equals("123")) {
            presenter.onSuccesLogin();
        } else if (nro.equals("3")) {
            presenter.onErrorLogin();
        } else {
            presenter.onUserNotValid();
        }
    }
}
