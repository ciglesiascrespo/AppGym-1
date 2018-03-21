package com.iglesias.c.appgym.Presenter;

import com.iglesias.c.appgym.Iterator.LoginIterator;
import com.iglesias.c.appgym.View.LoginView;

/**
 * Created by Ciglesias on 21/03/2018.
 */

public class LoginPresenterImpl implements LoginPresenter {
    LoginView view;
    LoginIterator iterator;

    public LoginPresenterImpl(LoginView view) {
        this.view = view;
        iterator = new LoginIterator(this);
    }

    public void validateUser(String nro) {
        if (nro.length() == 0) {
            view.showErrorLoginDialog("Digite un numero de identificación");
        } else {
            iterator.validateUser(nro);
        }
    }

    @Override
    public void onSuccesLogin() {
        view.hideLoading();
        view.goToMainActivity();
    }

    @Override
    public void onErrorLogin() {
        view.hideLoading();
        view.showErrorLoginDialog("Error validando usuario, verifique su conexión a internet");
    }

    @Override
    public void onUserNotValid() {
        view.hideLoading();
        view.showErrorLoginDialog("Usuario no válido.");
    }
}
