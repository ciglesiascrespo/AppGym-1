package com.iglesias.c.appgym.RestApi;

import com.iglesias.c.appgym.RestApi.Model.ResultLogin;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Ciglesias-pc on 24/08/2017.
 */

public interface EndPoints {
    @GET(ConstantesRestApi.KEY_POST_LOGIN)
    Observable<ResultLogin> login( @Query("r") String r, @Query("identificacion") String identificacion, @Query("id_sucursal") int idSucursal, @Query("id_licencia")int idLicencia);

    @GET(ConstantesRestApi.t)
    Observable<ResultLogin> tiquet( @Query("r") String t, @Query("identificacion") String identificacion, @Query("id_sucursal") int idSucursal, @Query("id_licencia")int idLicencia);
}
