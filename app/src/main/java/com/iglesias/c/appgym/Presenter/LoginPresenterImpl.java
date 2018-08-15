package com.iglesias.c.appgym.Presenter;

import android.util.Log;

import com.iglesias.c.appgym.Iterator.LoginIterator;
import com.iglesias.c.appgym.Pojo.DeviceInfo;
import com.iglesias.c.appgym.RestApi.Model.InfoLogin;
import com.iglesias.c.appgym.RestApi.Model.ResultLogin;
import com.iglesias.c.appgym.View.LoginView;

/**
 * Created by Ciglesias on 21/03/2018.
 */

public class LoginPresenterImpl implements LoginPresenter {
    LoginView view;
    LoginIterator iterator;

    public LoginPresenterImpl(LoginView view) {
        this.view = view;
        iterator = new LoginIterator(this, view.getContext());
    }

    public void validateUser(String nro, String sucursal) {
        if (nro.length() == 0) {
            view.showErrorLoginDialog("Digite un número de identificación");
        } else {
            view.showLoading();
            iterator.validateUser(nro, sucursal);
        }
    }

    public DeviceInfo getDeviceInfo() {
        return iterator.getInfoDeviceConexion();
    }


    @Override
    public void onSuccesLogin(ResultLogin resultLogin) {
        view.hideLoading();

        Log.e(getClass().getName(),"url: " + resultLogin.getInfo().getUrlImage());
        Log.e(getClass().getName(),resultLogin.getInfo().toString());

        if (resultLogin.getInfo().getDias() > 0 || resultLogin.getInfo().getTickets() > 0) {
            view.goToMainActivity(resultLogin);
            if(resultLogin.getInfo().getTickets() > 0)
                iterator.tiquet(resultLogin.getInfo().getNroDocumento());
        } else {
            view.showErrorLoginDialog("Su membresía ha vencido.");
        }
    }

    @Override
    public void onErrorLogin() {
        view.hideLoading();
        view.showErrorLoginDialog("Usuario sin planes asociados.");
    }

    @Override
    public void onUserNotValid(String msj) {
        view.hideLoading();
        view.showErrorLoginDialog(msj.isEmpty() ? "Usuario no válido." : msj);
    }

}
