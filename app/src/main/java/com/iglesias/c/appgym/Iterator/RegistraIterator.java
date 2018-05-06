package com.iglesias.c.appgym.Iterator;

import android.content.Context;
import android.util.Log;

import com.iglesias.c.appgym.Db.DbHandler;
import com.iglesias.c.appgym.Presenter.RegistrarPresenter;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Ciglesias on 6/05/2018.
 */

public class RegistraIterator {
    private RegistrarPresenter presenter;
    private DbHandler dbHandler;

    public RegistraIterator(RegistrarPresenter presenter, Context context) {
        this.presenter = presenter;
        dbHandler = DbHandler.getInstance(context);
    }

    public void registrarUsuario(final String nro, final String id) {
        Observable<Boolean> observable = Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                subscriber.onNext(dbHandler.verificaUsuario(nro));
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


                if (result) {
                    presenter.onUserExist();
                } else {
                    Observable.create(new Observable.OnSubscribe<Long>() {
                        @Override
                        public void call(Subscriber<? super Long> subscriber) {
                            subscriber.onNext(dbHandler.registraUsuario(nro, id));
                            subscriber.onCompleted();
                        }
                    }).observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .unsubscribeOn(Schedulers.io())
                            .subscribe(new Subscriber<Long>() {
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
                                public void onNext(Long result) {

                                }
                            });
                }
            }
        });

    }
}
