package com.iglesias.c.appgym.Presenter;

import android.widget.Toast;

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

    public void registrarUsuario(String nro, String id, Boolean flag) {
        if (nro.isEmpty()) {
            view.showErrorLoginDialog("Diligencia un número de documento", false);
        } else if (!flag) {
            view.showErrorLoginDialog("Debe registrar una huella", false);
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

        if (!msj.toLowerCase().contains("id:") && !msj.toLowerCase().contains("j")) {
            String msjResult = iterator.getMsj(msj.substring(0,1));
            if (!msjResult.isEmpty())
                view.showErrorLoginDialog(msjResult, false);
        } else if (msj.toLowerCase().contains("id:")) {
            view.setId(msj.split(":")[1]);
        } else {
            view.setFlagHuella(true);
        }
    }
}
