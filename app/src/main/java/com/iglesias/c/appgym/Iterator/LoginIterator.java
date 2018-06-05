package com.iglesias.c.appgym.Iterator;

import android.content.Context;
import android.content.SharedPreferences;

import com.iglesias.c.appgym.Db.DbHandler;
import com.iglesias.c.appgym.Pojo.DeviceInfo;
import com.iglesias.c.appgym.Presenter.LoginPresenter;
import com.iglesias.c.appgym.RestApi.Adapter.RestApiAdapter;
import com.iglesias.c.appgym.RestApi.ConstantesRestApi;
import com.iglesias.c.appgym.RestApi.EndPoints;
import com.iglesias.c.appgym.RestApi.Model.ResultLogin;
import com.iglesias.c.appgym.Utils.ConstantsPreferences;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.iglesias.c.appgym.Utils.ConstantsPreferences.NAME_PREFERENCE_CONFIG_MAC;
import static com.iglesias.c.appgym.Utils.ConstantsPreferences.NAME_PREFERENCE_CONFIG_NOMBRE;

/**
 * Created by Ciglesias on 21/03/2018.
 */

public class LoginIterator {
    LoginPresenter presenter;
    DbHandler dbHandler;
    private SharedPreferences preferences;

    public LoginIterator(LoginPresenter presenter, Context context) {
        this.presenter = presenter;
        dbHandler = DbHandler.getInstance(context);
        preferences = context.getSharedPreferences(ConstantsPreferences.NAME_PREFERENCE_CONFIG, Context.MODE_PRIVATE);
    }

    public DeviceInfo getInfoDeviceConexion() {
        String nombre = "No configurado", mac = "No configurado";

        if (preferences != null) {
            nombre = preferences.getString(NAME_PREFERENCE_CONFIG_NOMBRE, "No configurado");
            mac = preferences.getString(NAME_PREFERENCE_CONFIG_MAC, "No configurado");
        }

        return new DeviceInfo(nombre, mac);
    }
    public void validateUser(String nro) {

        Retrofit retrofit = RestApiAdapter.provideRetrofit();

        retrofit.create(EndPoints.class).login(ConstantesRestApi.r,nro,ConstantesRestApi.idSucursal, ConstantesRestApi.idLicencia)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ResultLogin>() {
                               @Override
                               public void onCompleted() {

                               }

                               @Override
                               public void onError(Throwable e) {
                                   e.printStackTrace();
                                   presenter.onErrorLogin();

                               }

                               @Override
                               public void onNext(ResultLogin response) {

                                   if (response.getErrorCode() == ConstantesRestApi.CODE_ERROR) {
                                       presenter.onUserNotValid();
                                   } else {
                                       presenter.onSuccesLogin(response.getInfo());
                                   }

                               }
                           }
                );

    }

    public void validateUserDb(final String nro) {
        Observable.create(new Observable.OnSubscribe<ResultLogin>() {
            @Override
            public void call(Subscriber<? super ResultLogin> subscriber) {
                subscriber.onNext(dbHandler.verificaUsuarioDb(nro));
                subscriber.onCompleted();
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ResultLogin>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        presenter.onErrorLogin();
                    }

                    @Override
                    public void onNext(ResultLogin response) {
                        if (response.getErrorCode() == ConstantesRestApi.CODE_ERROR) {
                            presenter.onUserNotValid();
                        } else {
                            presenter.onSuccesLogin(response.getInfo());
                        }

                    }
                });


    }

}
