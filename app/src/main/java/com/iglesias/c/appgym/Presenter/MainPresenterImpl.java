package com.iglesias.c.appgym.Presenter;

import com.iglesias.c.appgym.Iterator.MainIterator;
import com.iglesias.c.appgym.View.MainView;

/**
 * Created by dell on 08/05/2018.
 */

public class MainPresenterImpl {
    MainView view;
    MainIterator iterator;
    public boolean flag = false;

    public MainPresenterImpl(MainView view) {
        this.view = view;
        iterator = new MainIterator();
    }

    public void receiveMsj(String msj) {

        if (!flag) {
            if (!msj.toLowerCase().contains("id:")) {
                if (msj.toLowerCase().contains("r")) {
                    //view.showErrorLoginDialog(msj);
                    view.sendId();
                } else {
                    String msjR = iterator.getMsj(msj.substring(0, 1));
                    if (!msjR.isEmpty())
                        view.showErrorLoginDialog(msjR);
                }

            } else {
                // view.showErrorLoginDialog(msj);
                view.compareId(msj.split(":")[1].substring(0, 1));
            }
        }else{
            view.showErrorLoginDialog(msj);
        }
    }
}
