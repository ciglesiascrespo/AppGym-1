package com.iglesias.c.appgym.Presenter;

import com.iglesias.c.appgym.Iterator.RegistraIterator;
import com.iglesias.c.appgym.View.RegistrarView;

/**
 * Created by Ciglesias on 6/05/2018.
 */

public class RegistrarPresenterImpl implements RegistrarPresenter {
    private RegistrarView view;
    private RegistraIterator iterator;

    public RegistrarPresenterImpl(RegistrarView view) {
        this.view = view;
        iterator = new RegistraIterator(this, view.getContextApp());
    }

    public void registrarUsuario(String nro) {
        registrarUsuario(nro, "");
    }

    public void registrarUsuario(String nro, String id) {
        if (nro.isEmpty()) {
            view.showErrorLoginDialog("Diligencia un número de documento", false);
        } else {
            view.showLoading();
            iterator.registrarUsuario(nro, id);
        }

    }

    @Override
    public void onUserExist() {
        view.hideLoading();
        view.showErrorLoginDialog("El usuario ya se encuentra registrado.", true);
    }

    @Override
    public void onError() {
        view.hideLoading();
        view.showErrorLoginDialog("Error registrando usuario.", true);
    }

    @Override
    public void onSuccess() {
        view.hideLoading();
        view.showErrorLoginDialog("Usuario registrado con éxito.", true);

    }

    public void receiveMsj(String msj) {
        if (!msj.contains("id:")) {
            view.showErrorLoginDialog(msj, false);
        } else {
            view.setId(msj.split(":")[1]);
        }
    }
}
