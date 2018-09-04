package com.iglesias.c.appgym.Activity;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iglesias.c.appgym.Presenter.MainPresenterImpl;
import com.iglesias.c.appgym.R;
import com.iglesias.c.appgym.Service.Bluetooth;
import com.iglesias.c.appgym.Ui.BaseActivity;
import com.iglesias.c.appgym.View.MainView;
import com.squareup.picasso.Picasso;

import rx.Single;
import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.iglesias.c.appgym.Activity.LoginActivity.EXTRA_DEVICE_MAC;
import static com.iglesias.c.appgym.Activity.LoginActivity.EXTRA_DIAS;
import static com.iglesias.c.appgym.Activity.LoginActivity.EXTRA_DOCUMENTO;
import static com.iglesias.c.appgym.Activity.LoginActivity.EXTRA_FLAG_SIN_HUELLA;
import static com.iglesias.c.appgym.Activity.LoginActivity.EXTRA_ID_HUELLA;
import static com.iglesias.c.appgym.Activity.LoginActivity.EXTRA_NOMBRE;
import static com.iglesias.c.appgym.Activity.LoginActivity.EXTRA_URL_IMAGEN;

public class MainActivity extends BaseActivity implements MainView {
    private final String TAG = getClass().getName();

    private String documento, id, mac;
    private TextView txtNombre, txtDias, txtDocumento;
    ImageView imgUsr;


    AlertDialog dialog;
    Bluetooth bt;
    MainPresenterImpl presenter;
    String nombre, urlImage;
    boolean flagSinHuella = false;
    int dias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();

        nombre = getIntent().getStringExtra(EXTRA_NOMBRE);
        dias = getIntent().getIntExtra(EXTRA_DIAS, 0);
        documento = getIntent().getStringExtra(EXTRA_DOCUMENTO);
        urlImage = getIntent().getStringExtra(EXTRA_URL_IMAGEN);
        id = getIntent().getStringExtra(EXTRA_ID_HUELLA);
        mac = getIntent().getStringExtra(EXTRA_DEVICE_MAC);
        flagSinHuella = getIntent().getBooleanExtra(EXTRA_FLAG_SIN_HUELLA, false);
        setupBt();
        txtNombre.setText(nombre);
        txtDocumento.setText(documento);
        txtDias.setText(dias + " dias.");

        Picasso.with(this).load(urlImage).into(imgUsr);

        presenter = new MainPresenterImpl(this);
        //HuellaTrigger - Comentar
        btnClick();

        //HuellaTrigger - Descomentar

        /*if (flagSinHuella) {
            if (id.isEmpty()) {
                showErrorLoginDialog("El usuario no cuenta con una huella registrada.");
            } else {
                activarSensor();
            }
        } else {
            btnClick();
        }*/

        //nombre
    }

    private void setupBt() {
        bt = Bluetooth.getInstance(this, mHandler);
    }

    public void activarSensor() {
        String dato = "2";
        bt.sendMessage(dato);

    }

    @Override
    public void goToLogin() {
        showErrorLoginDialog("Alcanzó limite de intentos, vuelva a loguearse.");
        finish();
    }

    private void setupViews() {
        txtDias = findViewById(R.id.id_txt_dias);
        txtDocumento = findViewById(R.id.id_txt_documento);
        txtNombre = findViewById(R.id.id_txt_nombre);
        imgUsr = findViewById(R.id.imagen_usr);

    }

    private boolean waitTime() {
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private boolean waitTimeArduino() {
        try {
            Thread.sleep(flagSinHuella ? 300 : 400);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private void btnClick() {
        showErrorLoginDialog("Bienvenido " + nombre);
        Single.create(new Single.OnSubscribe<Boolean>() {
            @Override
            public void call(SingleSubscriber<? super Boolean> singleSubscriber) {
                singleSubscriber.onSuccess(waitTimeArduino());
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        String dato = "3";
                        bt.sendMessage(dato);
                        Single.create(new Single.OnSubscribe<Boolean>() {
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
                                        bt.sendMessage(dato);
                                        finish();
                                    }
                                }, new Action1<Throwable>() {
                                    @Override
                                    public void call(Throwable throwable) {

                                    }
                                });
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }


    @Override
    public void showErrorLoginDialog(String msj) {
        Toast toast = Toast.makeText(this, msj, Toast.LENGTH_SHORT);
        ((TextView) ((ViewGroup) toast.getView()).getChildAt(0)).setTextSize(20);
        toast.show();

        //HuellaTrigger - Descomentar
        /*
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.myDialog);

        builder.setTitle(getResources().getString(R.string.str_menu_registrar));
        builder.setMessage(msj);
        builder.setCancelable(false);
        builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = builder.create();
        dialog.show();*/
    }

    @Override
    public void compareId(String id) {
        if (id.equals("0")) {
            btnClick();

        } else {
            showErrorLoginDialog("La huella obtenida no coincide con el usuario.");
            activarSensor();
        }
    }

    @Override
    public void sendId() {
        String arrayId = id + "}";
        bt.sendMessage(arrayId);
    }

    private final Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Bluetooth.MESSAGE_STATE_CHANGE:

                    Log.e(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                    break;
                case Bluetooth.MESSAGE_WRITE:
                    Log.e(TAG, "MESSAGE_WRITE: " + String.valueOf(msg.arg1));

                    break;
                case Bluetooth.MESSAGE_READ:

                    Log.e(TAG, "MESSAGE_READ: " + msg.obj);
                    String msj = String.valueOf(msg.obj);

                    presenter.receiveMsj(msj);

                    break;
                case Bluetooth.MESSAGE_DEVICE_NAME:
                    Log.d(TAG, "MESSAGE_DEVICE_NAME " + msg);
                    break;
                case Bluetooth.MESSAGE_TOAST:
                    if (msg.arg1 == -1) {
                        // bt.connectDevice(mac);
                    }
                    Log.d(TAG, "MESSAGE_TOAST " + msg);
                    break;
            }

        }
    };

}
