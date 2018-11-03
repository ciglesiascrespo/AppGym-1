package com.iglesias.c.appgym.Presenter;

import com.iglesias.c.appgym.Iterator.ActualizaIterator;
import com.iglesias.c.appgym.Iterator.RegistraIterator;
import com.iglesias.c.appgym.View.RegistrarView;

/**
 * Created by Ciglesias on 6/05/2018.
 */

public class ActualizaPresenterImpl implements ActualizaPresenter {
    private RegistrarView view;
    private ActualizaIterator iterator;
    private String matHuella = "";
    private boolean flagQ = false;

    public ActualizaPresenterImpl(RegistrarView view) {
        this.view = view;
        iterator = new ActualizaIterator(this, view.getContextApp());
    }

    public void actualizaUsuario(String nro, String id, Boolean flag) {
        if (nro.isEmpty()) {
            view.showErrorLoginDialog("Diligencia un número de documento", false);
        } else if (!flag) {
            view.showErrorLoginDialog("Debe registrar una huella", false);
        } else {
            view.showLoading();
            iterator.actualizarUsuario(nro, id);
        }


    }


    @Override
    public void onUserNotExist() {
        view.hideLoading();
        view.showErrorLoginDialog("El usuario no se encuentra registrado.", true);
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

    public void receiveMsj(byte[] msj) {
            view.setFlagHuella(true);
            String template = "";
            for(byte b : msj) {
                template += b;
            }
            view.setId(template);
            //view.showErrorLoginDialog(matHuella.replace("Q", "").replace("}", "").replace("{",""),false);
            matHuella = "";
    }
}
