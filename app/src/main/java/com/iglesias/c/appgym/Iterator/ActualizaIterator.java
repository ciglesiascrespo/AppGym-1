package com.iglesias.c.appgym.Iterator;

import android.content.Context;

import com.iglesias.c.appgym.Db.DbHandler;
import com.iglesias.c.appgym.Presenter.ActualizaPresenter;
import com.iglesias.c.appgym.Presenter.RegistrarPresenter;

import java.util.HashMap;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Ciglesias on 6/05/2018.
 */

public class ActualizaIterator {
    private ActualizaPresenter presenter;
    private DbHandler dbHandler;
    private HashMap<String, String> mapMsj;

    public ActualizaIterator(ActualizaPresenter presenter, Context context) {
        this.presenter = presenter;
        dbHandler = DbHandler.getInstance(context);
        setupHash();
    }

    private void setupHash() {
        mapMsj = new HashMap<>();
        mapMsj.put("A", "Imagen tomada.");
        mapMsj.put("B", "Error de comunicación.");
        mapMsj.put("C", "Error de imagen.");
        mapMsj.put("D", "Error desconocido.");
        mapMsj.put("E", "Imagen convertida.");
        mapMsj.put("F", "Imagen demasiado sucia.");
        mapMsj.put("G", "No se pudieron encontrar caracteristicas de huellas dactilares.");
        mapMsj.put("H", "Retirar el dedo.");
        mapMsj.put("I", "Colque el mismo dedo nuevamente.");
        mapMsj.put("J", "Huella almacenada.");
        mapMsj.put("K", "No se pudo almacenar en esta ubicación.");
        mapMsj.put("L", "Error de escritura.");
        mapMsj.put("M", "Esperando dedo válido para inscripción.");
        mapMsj.put("X", "No se encontró sensor");
        mapMsj.put("Z", "Sensor encontrado");
    }

    public String getMsj(String msjR) {
        String msj = "";

        if (mapMsj.containsKey(msjR)) msj = mapMsj.get(msjR);

        return msj;
    }


    public void actualizarUsuario(final String nro, final String id) {
        Observable<Boolean> observable = Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                subscriber.onNext(dbHandler.verificaUsuario(nro, id));
                subscriber.onCompleted();
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());

        observable.subscribe(new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                presenter.onError();
            }

            @Override
            public void onNext(Boolean result) {


                if (!result) {
                    presenter.onUserNotExist();
                } else {
                    Observable.create(new Observable.OnSubscribe<Integer>() {
                        @Override
                        public void call(Subscriber<? super Integer> subscriber) {
                            subscriber.onNext(dbHandler.actualizaUsuario(nro, id));
                            subscriber.onCompleted();
                        }
                    }).observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .unsubscribeOn(Schedulers.io())
                            .subscribe(new Subscriber<Integer>() {
                                @Override
                                public void onCompleted() {
                                    presenter.onSuccess();
                                }

                                @Override
                                public void onError(Throwable e) {
                                    e.printStackTrace();
                                    presenter.onError();
                                }

                                @Override
                                public void onNext(Integer result) {

                                }
                            });
                }
            }
        });

    }
}
