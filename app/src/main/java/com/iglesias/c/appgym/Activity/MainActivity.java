package com.iglesias.c.appgym.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iglesias.c.appgym.R;
import com.iglesias.c.appgym.Service.UsbService;
import com.squareup.picasso.Picasso;

import rx.Single;
import rx.SingleSubscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.iglesias.c.appgym.Activity.LoginActivity.EXTRA_DIAS;
import static com.iglesias.c.appgym.Activity.LoginActivity.EXTRA_DOCUMENTO;
import static com.iglesias.c.appgym.Activity.LoginActivity.EXTRA_NOMBRE;
import static com.iglesias.c.appgym.Activity.LoginActivity.EXTRA_URL_IMAGEN;

public class MainActivity extends AppCompatActivity {
    private TextView txtNombre, txtDias, txtDocumento;
    ImageView imgUsr;
    ImageButton btnEntrar;
    MyHandler myHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();

        String nombre, documento, urlImage;
        int dias;

        nombre = getIntent().getStringExtra(EXTRA_NOMBRE);
        dias = getIntent().getIntExtra(EXTRA_DIAS, 0);
        documento = getIntent().getStringExtra(EXTRA_DOCUMENTO);
        urlImage = getIntent().getStringExtra(EXTRA_URL_IMAGEN);

        txtNombre.setText(nombre);
        txtDocumento.setText(documento);
        txtDias.setText(dias + " dias.");

        Picasso.with(this).load(urlImage).into(imgUsr);
        myHandler = new MyHandler();
        LoginActivity.usbService.setHandler(myHandler);
        //nombre =
    }

    private void setupViews() {
        txtDias = findViewById(R.id.id_txt_dias);
        txtDocumento = findViewById(R.id.id_txt_documento);
        txtNombre = findViewById(R.id.id_txt_nombre);
        imgUsr = findViewById(R.id.imagen_usr);
        btnEntrar = findViewById(R.id.id_btn_entrar);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dato = "3";
                btnEntrar.setEnabled(false);

                LoginActivity.usbService.write(dato.getBytes());
                //btnClick();
            }
        });
    }

    private boolean waitTime() {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private void btnClick() {
        Subscription subscription = Single.create(new Single.OnSubscribe<Boolean>() {
            @Override
            public void call(SingleSubscriber<? super Boolean> singleSubscriber) {
                singleSubscriber.onSuccess(waitTime());
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        String dato = "0";
                        LoginActivity.usbService.write(dato.getBytes());
                        btnEntrar.setEnabled(true);
                        finish();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }


    private class MyHandler extends Handler {

        public MyHandler() {

        }

        @Override
        public void handleMessage(Message msg) {
            Log.e("registra", "msj: " + msg.obj.toString());
            switch (msg.what) {
                case UsbService.MESSAGE_FROM_SERIAL_PORT:
                    String data = (String) msg.obj;
                    Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();

                    break;
            }
        }
    }

}
